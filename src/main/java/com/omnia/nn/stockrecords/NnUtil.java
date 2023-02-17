package com.omnia.nn.stockrecords;

import java.io.File;

public class NnUtil {

    public static String getQuotesPath(){
        File resourcesDirectory = new File("src/main/resources/quotes");
        return resourcesDirectory.getAbsolutePath();
    }

    public static String getResuldPath(){
        File resourcesDirectory = new File("src/main/resources/results");
        return resourcesDirectory.getAbsolutePath();
    }

}