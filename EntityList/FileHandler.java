package EntityList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void saveListToFile(List<?> dataList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object data : dataList) {
                writer.append(data.toString()).append(System.lineSeparator());
                writer.append("===========").append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> loadListFromFile() {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> attributes = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("===========")) {
                    String[] attributesArray = attributes.toArray(new String[0]);
                    dataList.add(attributesArray);
                    attributes.clear();
                } else {
                    String[] attributePairs = line.split(", ");
                    for (String attributePair : attributePairs) {
                        String[] parts = attributePair.split(": ");
                        if (parts.length == 2) {
                            String attributeValue = parts[1].trim(); // Trim whitespace
                            attributes.add(attributeValue);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
