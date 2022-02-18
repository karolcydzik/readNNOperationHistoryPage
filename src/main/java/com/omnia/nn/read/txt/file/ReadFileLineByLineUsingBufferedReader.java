package com.omnia.nn.read.txt.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ReadFileLineByLineUsingBufferedReader {

    private static final String FILE_HEADER ="Data zlecenia\tData wyceny\tNazwa funduszu\tTyp transakcji\tStatus\tKwota\tKwota jednostka\tWartosc po transakcji\tWpt jednostka";
    private static final String TAB_DELIMITER = "\t";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private List<NnRecord> transactions = new ArrayList<>();
    private String fileNameA = "/home/karolcydzik/IdeaProjects/readNNOperationHistoryPage/src/main/resources/NNTFI24A.html";
    private String fileNameB = "/home/karolcydzik/IdeaProjects/readNNOperationHistoryPage/src/main/resources/NNTFI24B.html";
    private String outputFileName = "outputFile.tsv";

    public static void main(String[] args) {
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        if(args.length<2){
            System.out.print("Parameters are required. Please add files names\n");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            Path path = Paths.get(args[i]);
            if (Files.notExists(path)) {
                System.out.print("File:["+args[i]+"] do not exists\n");
                return;
            }
        }
        ReadFileLineByLineUsingBufferedReader reader = new ReadFileLineByLineUsingBufferedReader();
        for (int i = 0; i < args.length; i++) {
            reader.redFile(args[i]);
            System.out.print("The file:["+args[i]+"] has been loaded.\n");
        }
        reader.writeToTsv(reader.outputFileName);
        System.out.print("The output file:["+reader.outputFileName+"] has been created.\n");
    }

    private void writeToTsv(String outputFileName) {
        try {
            FileWriter fw = new FileWriter(outputFileName);
            fw.append(FILE_HEADER.toString());
            fw.append(NEW_LINE_SEPARATOR);
            Iterator<NnRecord> iter = transactions.iterator();
            while (iter.hasNext()) {
                NnRecord nnRecord = iter.next();
                StringBuilder sb = new StringBuilder();
                sb.append(nnRecord.getDataZlecenia());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getDataWyceny());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getNazwaFunduszu());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getTypTransakcji());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getStatus());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getKwota());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getKwotaJednostka());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getWartoscRejestru());
                sb.append(TAB_DELIMITER);
                sb.append(nnRecord.getWartoscRejestruJednostka());
                sb.append(NEW_LINE_SEPARATOR);
                try {
                    fw.append(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    iter.remove();
                }
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redFile(String fileName) {
        BufferedReader reader;
        Pattern numberPattern = Pattern.compile("\\d+");
        boolean started = false;
        NnRecord nnRecord = new NnRecord();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null && line.indexOf("<nav aria-label=\"...\" class=\"text-right\">") < 0) {
                if (!started) {
                    if (line.indexOf("rejestru<br>po transakcji") > 0) {
                        started = true;
                    }
                } else {
                    if (line.indexOf("break-date") > 0) {
                        Matcher dateMatcher = numberPattern.matcher(line);
                        List<String> dtLs = new ArrayList(3);
                        while (dateMatcher.find()) {
                            dtLs.add(dateMatcher.group());
                        }
                        String stDate = dtLs.get(2) + "-" + dtLs.get(1) + "-" + dtLs.get(0);
                        if (nnRecord.getDataZlecenia() == null) {
                            nnRecord.setDataZlecenia(stDate);
                        } else {
                            nnRecord.setDataWyceny(stDate);
                        }
                    } else if (line.indexOf("<p class=\"name\">") > 0) {
                        String name = StringUtils.substringBetween(line, ">", "<");
                        nnRecord.setNazwaFunduszu(name);
                    } else if (line.indexOf("margin-xs text-center text-left-xs") > 0) {
                        String typTransakcji = StringUtils.substringBetween(line, ">", "<");
                        nnRecord.setTypTransakcji(typTransakcji);
                    } else if (line.indexOf("<p class=\"text-center\">") > 0) {
                        String status = StringUtils.substringBetween(line, ">", "<");
                        nnRecord.setStatus(status);
                    } else if (line.indexOf("value margin-xs text-center text-right-xs") > 0) {
                        String kwota = StringUtils.substringBetween(line, "</span>", "</p>");
                        kwota = kwota.replaceAll("&nbsp;","");
                        kwota = kwota.replaceAll(",",".");
                        int space = kwota.indexOf(" ");
                        nnRecord.setKwota(kwota.substring(0,space));
                        nnRecord.setKwotaJednostka(kwota.substring(space+1));
                    } else if (line.indexOf("registry-value") > 0) {
                        String kwota = StringUtils.substringBetween(line, ">", "<");
                        kwota = kwota.replaceAll("&nbsp;","");
                        kwota = kwota.replaceAll(",",".");
                        int space = kwota.indexOf(" ");
                        nnRecord.setWartoscRejestru(kwota.substring(0, space));
                        nnRecord.setWartoscRejestruJednostka(kwota.substring(space+1));
                        transactions.add(nnRecord);
                        nnRecord = new NnRecord();
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
