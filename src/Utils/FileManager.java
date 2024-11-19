package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static boolean fileExist(String filePath){
        File myObj = new File(filePath);
        return  myObj.exists();
    }

    public static  void createFile(String filePath){
        try {
            File myObj = new File(filePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        if(! fileExist(filePath)){
            createFile(filePath);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLines(String filePath, List<String> lines) {
        if(! fileExist(filePath)){
            createFile(filePath);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
