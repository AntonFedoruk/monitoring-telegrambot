package com.github.antonfedoruk.mtb.quickpowerclient;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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

        WebElement chargeListElement = driver.findElement(By.cssSelector("ul.sc-kgoBCf.hOeolh"));

        System.out.println("*******************");
        System.out.println("Locations: ");

        List<WebElement> chargeLocations = chargeListElement.findElements(By.cssSelector("li.withCP"));

        System.out.println("size : " + chargeLocations.size()); //8

        System.out.println("following-sibling");
        WebElement testname = chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul")).findElement(By.cssSelector("span.station span"));
//        System.out.println("NAMEEE: " + testname.getDomProperty("innerText"));
//        System.out.println("NAMEEE2: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul[1]/li[contains(@class,'cp')]/span/span")).getDomProperty("innerText"));
//        System.out.println("MODEL: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[2]")).getDomProperty("innerText"));
//        System.out.println("connector TYPE: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')][1]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[5]/ul/li[1]")).getDomProperty("innerText"));
        System.out.println("connector TYPE CLASS_ATTR: " + chargeLocations.get(0).findElement(By.xpath("//li[contains(@class,'withCP')][1]/following-sibling::ul[1]/li[contains(@class,'cp')]/span[5]/ul/li[1]/span")).getAttribute("class"));

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
