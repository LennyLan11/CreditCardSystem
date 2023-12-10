package creditcardsystem;

import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class CreditCardFileProcessor {

    private Map<String, FileHandler> fileHandlers;

    public CreditCardFileProcessor() {
        fileHandlers = new HashMap<>();
        // Register file handlers for each supported file format
        fileHandlers.put("csv", new CSVFileHandler());
        fileHandlers.put("json", new JSONFileHandler());
        fileHandlers.put("xml", new XMLFileHandler());
    }

    public void processCards(String inputFile, String outputFile) throws IOException {
        Path inputPath = Paths.get(inputFile);
        Path outputPath = Paths.get(outputFile);

        String fileExtension = getFileExtension(inputFile);

        FileHandler handler = fileHandlers.get(fileExtension);
        if (handler == null) {
            throw new IOException("Unsupported file format: " + fileExtension);
        }

        handler.processFile(inputPath, outputPath);
    }


    private String getFileExtension(String fileName) {
        // Extract the file extension
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    // Interface for file handling
    private interface FileHandler {
        void processFile(Path inputPath, Path outputPath) throws IOException;
    }

    // Handlers for each file type
    /*
     * Read input CSV file. 
     */
    private class CSVFileHandler implements FileHandler {
        @Override
        public void processFile(Path inputPath, Path outputPath) throws IOException {
            List<String> lines = Files.readAllLines(inputPath);
            List<String> outputLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    outputLines.add(parts[0] + ",Invalid: incorrect format");
                    continue;
                }

                String cardNumber = parts[0].trim();
                String expirationDate = parts[1].trim();
                String cardHolderName = parts[2].trim();

                CreditCardStructure card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
                if (card != null) {
                    if (card.isValid()) {
                        outputLines.add(card.getCardNumber() + "," + card.getCardType());
                    } else {
                        String detailedError = card.getDetailedErrorMessage();
                        outputLines.add(card.getCardNumber() + "," + detailedError);
                    }
                } else {
                    outputLines.add(cardNumber + ",Invalid: unknown card type");
                }
            }

            Files.write(outputPath, outputLines);
        }

    }


    private class JSONFileHandler implements FileHandler {
        @Override
        public void processFile(Path inputPath, Path outputPath) throws IOException {
            String content = new String(Files.readAllBytes(inputPath), StandardCharsets.UTF_8);
            
            // Basic parsing logic - ensure it matches your JSON structure
            Pattern pattern = Pattern.compile("\\{\\s*\"cardNumber\":\\s*\"(.*?)\",\\s*\"expirationDate\":\\s*\"(.*?)\",\\s*\"cardHolderName\":\\s*\"(.*?)\"\\s*\\}");
            Matcher matcher = pattern.matcher(content);
            
            List<String> outputLines = new ArrayList<>();
            
            while (matcher.find()) {
                String cardNumber = matcher.group(1);
                String expirationDate = matcher.group(2);
                String cardHolderName = matcher.group(3);
                
                CreditCardStructure card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
                if (card != null && card.isValid()) {
                    outputLines.add(String.format("{\"cardNumber\": \"%s\", \"cardType\": \"%s\"}", card.getCardNumber(), card.getCardType()));
                } else {
                    String detailedError = card == null ? "Invalid: unknown card type" : card.getDetailedErrorMessage();
                    outputLines.add(String.format("{\"cardNumber\": \"%s\", \"cardType\": \"%s\"}", cardNumber, detailedError));
                }
            }
            
            // Join all the lines into a single string representing the JSON array
            String outputContent = "[" + String.join(", ", outputLines) + "]";
            
            Files.write(outputPath, outputContent.getBytes(StandardCharsets.UTF_8));
        }
    }




    private class XMLFileHandler implements FileHandler {
        @Override
        public void processFile(Path inputPath, Path outputPath) throws IOException {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(Files.newInputStream(inputPath));

                // Create a new XML document for output
                Document outputDoc = builder.newDocument();
                Element rootElement = outputDoc.createElement("CARDS");
                outputDoc.appendChild(rootElement);

                NodeList cardNodes = document.getElementsByTagName("CARD");
                for (int i = 0; i < cardNodes.getLength(); i++) {
                    Element card = (Element) cardNodes.item(i);
                    String cardNumber = card.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
                    String expirationDate = card.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent();
                    String cardHolderName = card.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();

                    CreditCardStructure creditCard = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
                    Element cardElement = outputDoc.createElement("CARD");
                    rootElement.appendChild(cardElement);

                    Element numberElement = outputDoc.createElement("CARD_NUMBER");
                    numberElement.appendChild(outputDoc.createTextNode(cardNumber));
                    cardElement.appendChild(numberElement);

                    Element typeElement = outputDoc.createElement("CARD_TYPE");
                    if (creditCard != null && creditCard.isValid()) {
                        typeElement.appendChild(outputDoc.createTextNode(creditCard.getCardType()));
                    } else {
                        // Assume getDetailedErrorMessage() is a method that returns the error message
                        String error = creditCard != null ? creditCard.getDetailedErrorMessage() : "Invalid: unknown card type";
                        typeElement.appendChild(outputDoc.createTextNode(error));
                    }
                    cardElement.appendChild(typeElement);
                }

                // Write the content into XML file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); // Properly indent the output
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set indent amount
                DOMSource source = new DOMSource(outputDoc);
                StreamResult result = new StreamResult(Files.newOutputStream(outputPath));
                transformer.transform(source, result);

            } catch (ParserConfigurationException | SAXException | TransformerException e) {
                throw new IOException();
            }
        }
    }

}