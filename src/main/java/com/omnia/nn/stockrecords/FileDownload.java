package com.omnia.nn.stockrecords;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileDownload {

    private String prefix ="/html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[";
    private String suffix = "]/td[2]/a";

    /**
     * /html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[1]/td[2]/a
    /html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[1]/td[2]/a
    /html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[6]/td[2]/a
    /html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[7]/td[2]/a
    /html/body/div[2]/div[2]/div[1]/div[5]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[47]/td[2]/a
     /html/body/div[2]/div[2]/div[1]/div[4]/div/div[1]/div/div/div/div[1]/div/div[1]/div/p[2]/a
***/
    private List<String> securityList = Arrays.asList(
    "NN Akcji",
    "NN Polski Odpowiedzialnego Inwestowania",
    "NN Konserwatywny",
    "NN Krótkoterminowych Obligacji",
    "NN Obligacji",
    "NN Stabilnego Wzrostu",
    "NN Średnich i Małych Spółek",
    "NN Zrównoważony",
    "NN Indeks Obligacji",
    "NN Globalnej Dywersyfikacji",
    "NN Obligacji",
    "NN Indeks Odpowiedzialnego Inwestowania",
    "NN (L) Globalny Odpowiedzialnego Inwestowania",
    "NN (L) Konserwatywny Plus",
    "NN (L) Multi Factor",
    "NN (L) Europejski Spółek Dywidendowych",
    "NN (L) Globalny Długu Korporacyjnego",
    "NN (L) Globalny Spółek Dywidendowych",
    "NN (L) Japonia",
    "NN (L) Indeks Surowców (dawniej NN (L) Nowej Azji)",
    "NN (L) Obligacji Plus",
    "NN (L) Obligacji Rynków Wschodzących (Waluta Lokalna)",
    "NN (L) Stabilny Globalnej Dywersyfikacji (dawniej NN (L) Stabilny Globalnej Alokacji)",
    "NN (L) Stabilny Globalnej Alokacji",
    "NN (L) Spółek Dywidendowych Rynków Wschodzących",
    "NN (L) Spółek Dywidendowych USA",
    "NN (L) Total Return",
    "NN Perspektywa 2020",
    "NN Perspektywa 2025",
    "NN Perspektywa 2030",
    "NN Perspektywa 2035",
    "NN Perspektywa 2040",
    "NN Perspektywa 2045",
    "NN Perspektywa 2050",
    "NN Perspektywa 2055",
    "ING Pakiet Umiarkowany",
    "ING Pakiet Ostrożny",
    "ING Pakiet Dynamiczny",
    "NN Emerytura 2025",
    "NN Emerytura 2030",
    "NN Emerytura 2035",
    "NN Emerytura 2040",
    "NN Emerytura 2045",
    "NN Emerytura 2050",
    "NN Emerytura 2055",
    "NN Emerytura 2060",
    "NN Emerytura 2065");

    private WebDriver driver;

    public static void main(String[] args) throws IOException, InterruptedException {
        FileDownload fd = new FileDownload();
        fd.readAllFiles();
    }

    private void readAllFiles(){
        initDriver();
        for(int i=1; i<75; i++){
            String security = prefix+i+suffix;
            readTheFile(security);
        }
        System.out.println("Reading has been finished.");
        driver.close();
    }

    private void readTheFile(String security){
        driver.get("https://www.nntfi.pl/notowania"); //Testing webpage
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //for Implicit wait
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath(security)).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement myDynamicElement = driver.findElement(By.linkText("Zapisz wycenę do pliku"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDynamicElement.click();
    }

    private void initDriver(){
        Path path = FileSystems.getDefault().getPath("C:\\Users\\48602\\IdeaProjects\\readNNOperationHistoryPage\\src\\main\\resources\\gecodriver\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver",path.toString());

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", "C:\\tmp");
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.download.viewableInternally.enabledTypes", "");
        //options.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");

        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

}