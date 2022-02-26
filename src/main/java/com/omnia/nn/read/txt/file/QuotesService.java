package com.omnia.nn.read.txt.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuotesService {

    private static final String COMMA_DELIMITER = "," ;
    private static String  QUOTES_PATH= "/home/karolcydzik/IdeaProjects/readNNOperationHistoryPage/src/main/resources/quotes";
    private static String NAME_REGEX = Pattern.quote(" dla ") + "(.*?)" + Pattern.quote(" (A)");
    private DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

    public Map<String, List<StockExchange>> getStockExchangeRates() throws Exception {
        List<File> fileList = redaFileList();
        Map<String, List<StockExchange>> result = new HashMap<String, List<StockExchange>>();
        for(File file : fileList){
            getStockExchangeList(file, result);
        }
        return  result;
    }

    private List<File> redaFileList() throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(QUOTES_PATH))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        return filesInFolder;
    }

    private void getStockExchangeList(File file, Map<String, List<StockExchange>> seMap) {
        Pattern patternName = Pattern.compile(NAME_REGEX);
        List<StockExchange> records = new ArrayList<>();
        String name = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Matcher matcherName = patternName.matcher(line);
                if (matcherName.find()) {
                    name = matcherName.group();
                } else {
                    String[] values = line.split(COMMA_DELIMITER);
                    StockExchange se = new StockExchange(formatterDate.parse(values[0]), new Float(values[1]));
                    records.add(se);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        seMap.put(name,records);
    }
}
