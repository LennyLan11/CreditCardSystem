package creditcardsystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Check if the command-line arguments for input and output files are provided
        if (args.length < 2) {
            System.out.println("Usage: java Main <input_file> <output_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            // Create an instance of the file processor
            CreditCardFileProcessor processor = new CreditCardFileProcessor();
            // Process the input file and produce an output file
            processor.processCards(inputFile, outputFile);
        } catch (IOException e) {
            System.err.println("An error occurred while processing the files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


