package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.StationStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class ScraperTest {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "/home/anton/Programs/chromedrivers/chromedriver");
//        WebDriver driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver;
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver.get("https://admin.qp.kiev.ua");

        String inputLoginXpath = "//input[@placeholder='Enter login...']";
        String inputPasswordXpath = "//input[@placeholder='Enter password...']";
        String buttonLoginXpath = "//button[text()='Log In']";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> webDriver.findElement(By.xpath(inputLoginXpath)));

        WebElement inputLoginElement = driver.findElement(By.xpath(inputLoginXpath));
        WebElement inputPasswordElement = driver.findElement(By.xpath(inputPasswordXpath));
        WebElement buttonLoginElement = driver.findElement(By.xpath(buttonLoginXpath));

        inputLoginElement.sendKeys("service");
        inputPasswordElement.sendKeys("2ELErmptgu");
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

        //todo елементарна перевірка статуса

//        for (WebElement location : chargeLocations) {
//            System.out.println(location.findElement(By.xpath("(//span)[2]")).getText());
//        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.quit();
    }
}
