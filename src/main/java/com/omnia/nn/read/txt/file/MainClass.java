package com.omnia.nn.read.txt.file;

import java.util.Map;

public class MainClass {
    public static void main(String[] args) throws Exception {
        QuotesService qs = new QuotesService();
        Map ser = qs.getStockExchangeRates();

    }
}
