package com.omnia.nn.stockrecords;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReFormatTxt {

    private Pattern patternFullName = Pattern.compile("[a-z/(/)]");

    public void reformat(String inDir, String outDir){
        List<File> inFiles = readFiles(inDir);
        for (File file:inFiles){
            String fileName = file.getName();
            try {
                String outPath = outDir+"\\"+fileName;
                PrintWriter writer = new PrintWriter(outPath, "UTF-8");
                reformatFile(file, writer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public List<File> readFiles(String dir) {
        List<File> filesInFolder = null;
        try {
            FileUtils.cleanDirectory(new File(NnUtil.getQuotesPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            filesInFolder = Files.walk(Paths.get(dir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filesInFolder;
    }

    private void reformatFile(File file, PrintWriter writer) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String prefix = getPrefix(br.readLine());
            String line;
            while ((line = br.readLine()) != null) {
                String resultStr = prefix + line;
                writer.println(resultStr);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPrefix(String line) {
        String[] elements = line.split(",");
        String fullName = elements[1].substring(13);
        String symbol = normalisePl(fullName);
        symbol = symbol.replaceAll("\\s", "");
        symbol = symbol.replaceFirst("20", "");
        Matcher matcher = patternFullName.matcher(symbol);
        symbol = matcher.replaceAll("");
        String result = fullName + "," + symbol + ",";
        return result;
    }

    private String normalisePl(String str) {
        String srcStr = "ąĄćĆęĘłŁńŃóÓśŚźŹżŻ";
        String desStr = "aAcCeElLnNoOsSzZzZ";
        String result = str;
        for (int i = 0; i < srcStr.length(); i++) {
            result = result.replaceAll(srcStr.substring(i, i + 1), desStr.substring(i, i + 1));
        }
        return result;
    }
}