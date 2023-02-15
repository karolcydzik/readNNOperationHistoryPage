package com.omnia.nn.stockrecords;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChangeDownloadDirOfFirefox {

    private String linkDownload = "Zapisz wycenę do pliku";
    private FirefoxDriver driver = null;
    private String mainWindowIndex = null;
    private String CTRL_RETURN = Keys.chord(Keys.CONTROL, Keys.RETURN);
    private String CTRL_HOME = Keys.chord(Keys.CONTROL, Keys.HOME);
    private static String prefix = "/html/body/div[2]/div[2]/div[1]/div[4]/div/div/div/div[1]/div/div[1]/div/div/div/div[4]/div/table/tbody/tr[";
    private static String suffix = "]/td[2]/a";
    private static String suffixAw = "]/td[4]/img";
    private static int nrOfShares = 47;

    public static void main(String[] args) throws IOException {
        ChangeDownloadDirOfFirefox downloadDirOfFirefox = new ChangeDownloadDirOfFirefox();
        downloadDirOfFirefox.downloadAllSecurities(nrOfShares);
    }

    public void downloadAllSecurities(int maxSec) {
        initDriver();
        if (!loadMainPage()) {
            return;
        }
        for (int i = 1; i <= maxSec; i++) {
            try {
                enterSecurityPage(i);
            } catch (Exception e) {
                System.out.println("The file nr=[" + i + "] has not been downloaded.");
                System.out.println(e.toString());
            }
        }
        downloadSecurities();
    }

    private void downloadSecurities() {
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        boolean isFirst = true;
        int counter = 0;
        for (String tabId : windowHandles) {
            counter++;
            if (isFirst) {
                isFirst = false;
                closeTab(tabId);
            } else {
                downloadSecurity(tabId, counter);
            }
        }
    }

    private boolean loadMainPage() {
        WebElement element = driver.findElement(By.linkText(linkDownload));
        for (int i = 0; i < 5; i++) {
            if (element.isEnabled() && element.isDisplayed()) {
                return true;
            }
            try {
                TimeUnit.SECONDS.sleep(2l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void closeTab(String tabId) {
        try {
            driver.switchTo().window(tabId);
        } catch (IndexOutOfBoundsException windowWithIndexNotFound) {
            return;
        }
        driver.close();
    }

    private void downloadSecurity(String tabId, int countGr) {
        try {
            driver.switchTo().window(tabId);
        } catch (IndexOutOfBoundsException windowWithIndexNotFound) {
            System.out.println("The current window has not been reached.");
            return;
        }
        int counter = 0;
        while (counter < 5) {
            new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
            try {
                try {
                    WebElement element = driver.findElement(By.linkText(linkDownload));
                    if (element.isEnabled() && element.isDisplayed()) {
                        element.click();
                        TimeUnit.SECONDS.sleep(1);
                        driver.close();
                        System.out.println("Counter =[" + countGr + "]. The file has been downloaded. TabId=[" + tabId + "]");
                        return;
                    } else {
                        if (counter == 0) element.sendKeys(Keys.PAGE_DOWN);
                    }
                } catch (Exception ex) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("!!!! System error. Counter =[], tabId=[" + tabId + "].");
                        System.out.println(e.toString());
                    }
                    new Actions(driver).sendKeys(CTRL_HOME).perform();
                }
                counter++;
            } catch (Exception ec) {
            }
        }
        System.out.println("The file has !!!!! Not !!!! been downloaded. TabId=[" + tabId + "], counter =[" + countGr + "]");
    }

    private void enterSecurityPage(int i) {
        try {
            driver.switchTo().window(mainWindowIndex);
        } catch (IndexOutOfBoundsException windowWithIndexNotFound) {
            System.out.println("The main window has not been reached.");
            return;
        }
        String security = prefix + i + suffix;
        String availableIndicator = prefix + i + suffixAw;
        new Actions(driver).sendKeys(CTRL_HOME).perform();
        for (int j = 0; j < 10; j++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            WebElement availableInd = driver.findElement(By.xpath(availableIndicator));
            if (availableInd.isEnabled() & availableInd.isDisplayed()) {
                String title = availableInd.getAttribute("title");
                if (!StringUtils.equals(title, "ING Konto Funduszowe SFIO (tylko w ING Bank)")) {
                    WebElement element = driver.findElement(By.xpath(security));
                    element.sendKeys(CTRL_RETURN);

                }
                return;
            } else {
                availableInd.sendKeys(Keys.PAGE_DOWN);
            }
        }
        return;
    }

    private void initDriver() {
        // Setting Firefox driver path
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\48602\\IdeaProjects\\readNNOperationHistoryPage\\src\\main\\resources\\gecodriver\\geckodriver.exe");

        // Creating FirefoxOptions to set profile
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", getQuotesPath());
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.download.viewableInternally.enabledTypes", "");
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");

        // Launching browser with desired capabilities
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //for Implicit wait
        driver.get("https://www.nntfi.pl/notowania");
        driver.manage().window().maximize();
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        mainWindowIndex = windowHandles.get(0);
    }

    private String getQuotesPath(){
        File resourcesDirectory = new File("src/main/resources/quotes");
        return resourcesDirectory.getAbsolutePath();
    }
}