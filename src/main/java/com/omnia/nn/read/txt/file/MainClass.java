package com.omnia.nn.read.txt.file;

import com.omnia.nn.entities.NnRecord;

import java.util.List;
import java.util.Map;

public class MainClass {
    public static void main(String[] args) throws Exception {
        QuotesService qs = new QuotesService();
        Map ser = qs.getStockExchangeRates();
        TransactionHistoryService ths = new TransactionHistoryService();
        List<NnRecord> transactionsList = ths.readTransactions();
        Enricher.enrichTransactions(transactionsList, ser);
        ths.writeToTsv("src/main/resources/results/outputFile01.tsv",transactionsList);
    }
}
