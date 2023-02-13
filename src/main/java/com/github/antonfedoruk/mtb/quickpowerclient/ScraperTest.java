package com.github.antonfedoruk.mtb.quickpowerclient;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

public class ScraperTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/home/anton/Programs/chromedrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }

        driver.get("https://admin.qp.kiev.ua");

        String inputLoginXpath = "//input[@placeholder='Enter login...']";
        String inputPasswordXpath = "//input[@placeholder='Enter password...']";
        String buttonLoginXpath = "//button[text()='Log In']";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.xpath(inputLoginXpath)));

        WebElement inputLoginElement = driver.findElement(By.xpath(inputLoginXpath));
        WebElement inputPasswordElement = driver.findElement(By.xpath(inputPasswordXpath));
        WebElement buttonLoginElement = driver.findElement(By.xpath(buttonLoginXpath));

        inputLoginElement.sendKeys(" ");
        inputPasswordElement.sendKeys(" ");
        buttonLoginElement.click();

        wait.until(webDriver -> webDriver.findElement(By.cssSelector("ul.sc-kgoBCf.hOeolh"))); //







//        String pageSource = driver.getPageSource();
//
//        Document page = Jsoup.parse(pageSource);
//
//        Element chargeList = page.select("ul.sc-kgoBCf.hOeolh").first();
//
//        Elements chargeLocations = chargeList.select("li.withCP");
//
//        System.out.println("кількість: " + chargeLocations.size());
//        for (Element el : chargeLocations) {
//            System.out.println("назва станції: " +el.select("span.location > span:last-child").text());
//        }

        System.out.println("***START***");
        WebElement chargeListElement = driver.findElement(By.xpath("//ul[contains(@class,'sc-kgoBCf hOeolh')]"));

//        List<WebElement> chargeListElementElements = chargeListElement.findElements(By.xpath("//li[contains(@class,'withCP')]"));
        List<WebElement> chargeListElementElements = chargeListElement.findElements(By.cssSelector("li.withCP"));
        System.out.println("chargeListElementElements.size():" + chargeListElementElements.size()); // -> 8
        WebElement webElLocation = chargeListElementElements.get(0).findElement(By.xpath("//span/span[2]"));
        System.out.println("(0)stationLocation: " + webElLocation.getText());
        List<WebElement> subStations = chargeListElementElements.get(0).findElements(By.xpath("//li[contains(@class,'withCP')][1]/following-sibling::ul[1]/li[contains(@class,'cp')]"));
////        System.out.println("(0)subStations.size(): " + subStations.size());
//////        System.out.println("(0)subStations: " + subStations.get(0).findElement(By.xpath("//span[@class='station']/span")).getDomProperty("innerText"));
////        System.out.println("(0)subStations: " + subStations.get(0).findElement(By.cssSelector("span.station > span")).getDomProperty("innerText"));
//////        System.out.println("(1)subStations: " + subStations.get(1).findElement(By.xpath("//span[@class='station']/span")).getDomProperty("innerText"));
////        System.out.println("(1)subStations: " + subStations.get(1).findElement(By.cssSelector("span.station > span")).getDomProperty("innerText"));

        for (WebElement e : subStations) {
            System.out.println("SubST: " + e.findElement(By.cssSelector("span.station > span")).getDomProperty("innerText"));
        }


        for (int i = 0; i < subStations.size(); i++) {
            System.out.println("("+ i + ")subStations^-^: " + subStations.get(i).findElement(By.xpath("//li[contains(@class,'cp')][" + (i+1) + "]/span[@class='station']/span")).getDomProperty("innerText"));
        }

        for (WebElement stationLocation : chargeListElementElements) {
            System.out.println("stationLocation: " + stationLocation.findElement(By.cssSelector("span.location > span:last-child")).getText()); //correct
            List<WebElement> subStationsFromPage = stationLocation.findElements(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul[1]/li[contains(@class,'cp')]"));
//            List<WebElement> subStationsFromPage = stationLocation.findElement(By.cssSelector("*")).findElement(By.xpath("//following-sibling::ul[1]")).findElements(By.cssSelector("li.cp"));
            System.out.println("subStationsFromPage.size(): " +subStationsFromPage.size());
//            subStationsFromPage.stream()
//                    .forEach(subStationWebElement ->
////                            System.out.println("!!!getSubStationName: " + subStationWebElement.findElement(By.xpath("//span[@class='station']/span")).getDomProperty("innerText")));
//                            System.out.println("!!!getSubStationName: " + subStationWebElement.findElement(By.cssSelector("span.station > span")).getDomProperty("innerText")));
        }


//        for (WebElement stationLocation : chargeListElementElements) {
//            System.out.println("stationLocation: " + stationLocation.getText());
//            List<WebElement> subStationsFromPage = stationLocation.findElements(By.xpath("/li[contains(@class,'withCP')]/following-sibling::ul/li[contains(@class,'cp')]"));
//            System.out.println("subStationsFromPage.size(): " +subStationsFromPage.size());
//            subStationsFromPage.stream()
//                    .forEach(subStationWebElement ->
//                            System.out.println("!!!getSubStationName: " + subStationWebElement.findElement(By.xpath("//span/span")).getDomProperty("innerText")));
//        }
        
        System.out.println("*******FINISH*******");


/////        System.out.println("=======================");
/////        System.out.println("Locations: ");
/////
/////        List<WebElement> chargeLocations = chargeListElement.findElements(By.cssSelector("li.withCP"));
/////
/////        System.out.println("size : " + chargeLocations.size()); //8
/////
/////        System.out.println("following-sibling");
/////        WebElement testname = chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul")).findElement(By.cssSelector("span.station span"));


        //        System.out.println("NAMEEE: " + testname.getDomProperty("innerText"));
//        System.out.println("NAMEEE2: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul[1]/li[contains(@class,'cp')]/span/span")).getDomProperty("innerText"));
//        System.out.println("MODEL: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[2]")).getDomProperty("innerText"));
//        System.out.println("connector TYPE: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')][1]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[5]/ul/li[1]")).getDomProperty("innerText"));
//        System.out.println("connector TYPE CLASS_ATTR: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')][1]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[5]/ul/li[1]/span")).getAttribute("class"));

//        List<WebElement> chargeLocationSubStations = chargeListElement.findElements(By.cssSelector("ul.sc-kGXeez.fgVfqV span.station span"));
//        System.out.println("chargeLocationSubStations size: " + chargeLocationSubStations.size()); //12

//        String subStationName = chargeLocationSubStations.get(2).getDomProperty("innerText");
//        System.out.println("sub station name(kiv-indust): " + chargeLocationSubStations);

//
//        System.out.println("2nd: " + chargeLocations.get(2).getText());
//        System.out.println("2nd #s: " + chargeLocations.get(2).findElements(By.cssSelector("span")).size()); //14

//        total status
//        String stationStatusFromTitle = chargeLocations.get(2).findElement(By.cssSelector("span.location > span:first-child")).getAttribute("title");
//        System.out.println("STATUS: " + stationStatusFromTitle); //online
//        for (StationStatus status : StationStatus.values()) {
//            if (status.name().equalsIgnoreCase(stationStatusFromTitle)) {
//                System.out.println(status.name());
//            }
//        }


//        System.out.println("LOCATION: " + chargeLocations.get(2).findElement(By.cssSelector("span.location > span:last-child")).getText()); //Arkadia, Osokorky
//        System.out.println("ADDRESS: " + chargeLocations.get(2).findElement(By.cssSelector("span.location + span")).getText()); // 13, Sribnokil's'ka str.
//        System.out.println("AMOUNT OF substations: " + chargeLocations.get(2).findElements(By.cssSelector("span.stationsStatus > ul > li")).size());
        driver.quit();
    }
}
