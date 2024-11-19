package com.example.conways;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaver {
    // Method to write data to the file
    public void writeData() {
        try {
            File file = createFile();
            // Create a FileWriter pointing to the same file path as in createFile()
            FileWriter myWriter = new FileWriter(file, true); // Use append mode
            myWriter.write(ConwaysDataMenu.getInstance().convertTableViewToString()); // Add newline for readability
            myWriter.close(); // Close the writer
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing data.");
            e.printStackTrace();
        }
    }

    // Method to create the file and ensure the directory structure exists
    public File createFile() {
        File myObj = null;
        try {
            // Specify the path for the file
            myObj = new File("C:\\Users\\Schue\\Documents\\Intellij\\Intellij\\conways\\src\\main\\resources\\com\\example\\conways\\data\\SimulationData.txt"); // Use relative path here

            // Create parent directories if they do not exist
            if (myObj.getParentFile() != null) {
                myObj.getParentFile().mkdirs(); // This will create the directories
            }

            // Create the file if it does not exist
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getAbsolutePath());
            } else {
                System.out.println("File already exists: " + myObj.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
        return myObj; // Return the File object for writing
    }

    // Main method
    public static void main(String[] args) {}
}
