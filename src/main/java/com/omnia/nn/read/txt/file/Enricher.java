package com.omnia.nn.read.txt.file;

import com.omnia.nn.entities.NnRecord;

import java.util.List;
import java.util.Map;

public class Enricher {
    public static void enrichTransactions(List<NnRecord> transactions, Map<String, Map<String, Float>> quotes) {
        for (NnRecord nnRecord : transactions) {
            String nazwa = nnRecord.getNazwaFunduszu();
            nnRecord.setCenaDatyWyceny(getValue(nazwa, nnRecord.getDataWyceny(), quotes));
            nnRecord.setCenaDatyZlecenia(getValue(nazwa, nnRecord.getDataZlecenia(), quotes));
        }
    }

    private static Float getValue(String nazwa, String date, Map quotes) {
        Float result = null;
        Map fnQuotes = (Map) quotes.get(nazwa);
        if (fnQuotes == null) {
            return result;
        }
        result = (Float) fnQuotes.get(date);
        return result;
    }
}