package utils;
import java.io.*;
import java.nio.file.*;

public class FileIO {

    public static String readFile(String filePath) {
        try {
            // Read all text from the file and return it
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            // Catch any IOExceptions and return a message
            return "Error reading file: " + e.getMessage();
        }
    }

    public static String writeFile(String filePath, String content) {
        try {
            // Write the content to the file, creating it if it doesn't exist
            Files.write(Paths.get(filePath), content.getBytes());
            return "File written successfully to " + filePath;
        } catch (IOException e) {
            // Catch any IOExceptions and return a message
            return "Error writing to file: " + e.getMessage();
        }
    }

    public static void initOutFile(String fileName) {
        File file = new File(fileName);

        try {
            boolean fileExists = file.exists();

            if (!fileExists) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("Failed to create the file.");
                    return;
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                if (fileExists)
                {
                    writer.write("VARIANT_REG,MAKE,MODEL,YEAR");
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static String appendToFile(String filePath, String content) {
        try
        {
            content = System.lineSeparator() + content;
            Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
            return "Content appended successfully to " + filePath;
        } catch (IOException e) {
            // Catch any IOExceptions and return a message
            return "Error appending to file: " + e.getMessage();
        }
    }
}
