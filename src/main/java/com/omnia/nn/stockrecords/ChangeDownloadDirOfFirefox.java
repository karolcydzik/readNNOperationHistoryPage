package com.omnia.nn.stockrecords;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class ChangeDownloadDirOfFirefox {

    private static String prefix ="/html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[";
    private static String suffix = "]/td[2]/a";


    public static void main(String[] args) throws IOException {

        // Setting Firefox driver path
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\48602\\IdeaProjects\\readNNOperationHistoryPage\\src\\main\\resources\\gecodriver\\geckodriver.exe");

        // Creating firefox profile
        FirefoxProfile profile = new FirefoxProfile();

        // Instructing firefox to use custom download location
        profile.setPreference("browser.download.folderList", 2);

        // Setting custom download directory
        profile.setPreference("browser.download.dir", "C:\\tmp");

        // Skipping Save As dialog box for types of files with their MIME
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");

        // Creating FirefoxOptions to set profile
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", "C:\\tmp");
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.download.viewableInternally.enabledTypes", "");
        //options.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");

        // Launching browser with desired capabilities
        FirefoxDriver driver = new FirefoxDriver(options);

        driver.get("https://www.nntfi.pl/notowania"); //Testing webpage
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //for Implicit wait
        String security = prefix + 1 + suffix;
        driver.findElement(By.xpath(security)).click();
        WebElement myDynamicElement = driver.findElement(By.linkText("Zapisz wycenę do pliku"));
        myDynamicElement.click();
        driver.close();
    }
}