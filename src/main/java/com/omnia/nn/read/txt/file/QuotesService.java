package com.omnia.nn.read.txt.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuotesService {

    private static final String COMMA_DELIMITER = ",";
    private static String QUOTES_PATH = "./src/main/resources/quotes";
    private static String NAME_REGEX = Pattern.quote(" dla ") + "(.*?)" + Pattern.quote(" (A)");

    public Map<String, Map<String, Float>> getStockExchangeRates() throws Exception {
        List<File> fileList = redaFileList();
        Map<String, Map<String, Float>> result = new HashMap<String, Map<String, Float>>();
        for (File file : fileList) {
            getStockExchangeList(file, result);
        }
        return result;
    }

    private List<File> redaFileList() throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(QUOTES_PATH))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        return filesInFolder;
    }

    private void getStockExchangeList(File file, Map<String, Map<String, Float>> seMap) {
        Pattern patternName = Pattern.compile(NAME_REGEX);
        Map<String, Float> records = new HashMap<>();
        String name = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Matcher matcherName = patternName.matcher(line);
                if (matcherName.find()) {
                    name = matcherName.group();
                    name = name.substring(5, name.length() - 4);
                } else {
                    String[] values = line.split(COMMA_DELIMITER);
                    records.put(values[0], new Float(values[1]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        seMap.put(name, records);
    }
}
