package com.github.antonfedoruk.mtb.quickpowerclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ScraperService} interface using Selenium.
 */
@Service
public class SeleniumScraperServiceImpl implements ScraperService {

    @Setter
    @Value("${quickpower.url}")
    private String quickpowerUrl;
    @Setter
    @Value("${quickpower.login.value}")
    private String login;
    @Setter
    @Value("${quickpower.password.val")
    private String password;

    public void setLogin(String login) {
        this.login = login;
    }

    //WebDriver represents the browser
    private final ChromeDriver driver;

    private WebDriverWait wait; // Explicit wait(JS should load a response to the page)

    public SeleniumScraperServiceImpl(ChromeDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Getter
    @AllArgsConstructor
    public class Station {
        private String location;
        private String address;
        private String status;
    }

    //get list of stations from site
    @Override
    public List<Station> extractStations() {
        driver.get(quickpowerUrl);

        signIn(login, password);

        List<WebElement> stationsFromPage = getStationsFromPage();
//        System.out.println("size : " + stationsFromPage.size()); //8
        return convertElementsListToStationsList(stationsFromPage);

    }

    private List<Station> convertElementsListToStationsList(List<WebElement> stationsFromPage) {
        List<Station> stations = new ArrayList<>();
        for (WebElement e : stationsFromPage) {
            stations.add(parseWebElementToStation(e));
        }
        return stations;
    }

    private Station parseWebElementToStation(WebElement element) {
        String location = getStationLocation(element);
        String address = getStationAddres(element);
        String status = getStationStatus(element);
        return new Station(location, address, status);
    }

    private String getStationStatus(WebElement element) {
        return element.findElement(By.cssSelector("span.location > span:first-child")).getAttribute("title");
    }

    private String getStationAddres(WebElement element) {
        return element.findElement(By.cssSelector("span.location + span")).getText();
    }

    private String getStationLocation(WebElement element) {
        return element.findElement(By.cssSelector("span.location > span:last-child")).getText();
    }

    private List<WebElement> getStationsFromPage() {
        String stationListElementCssSelector = "ul.sc-kgoBCf.hOeolh";
        try {
            wait.until(webDriver -> webDriver.findElement(By.cssSelector(stationListElementCssSelector))); //

            WebElement chargeListElement = driver.findElement(By.cssSelector(stationListElementCssSelector));

            System.out.println("*******************");
            System.out.println("Locations: ");

            return chargeListElement.findElements(By.cssSelector("li.withCP"));
        } catch (Exception e) {
            System.out.println("Station loading failed.");
            e.printStackTrace();
        }
        return null;
    }

    private void signIn(String login, String password) {
        String inputLoginXpath = "//input[@placeholder='Enter login...']";

        try {
            wait.until(webDriver -> webDriver.findElement(By.xpath(inputLoginXpath)));

            // fill login data on sign-in page
            driver.findElement(By.xpath(inputLoginXpath)).sendKeys(login);
            String inputPasswordXpath = "//input[@placeholder='Enter password...']";
            driver.findElement(By.xpath(inputPasswordXpath)).sendKeys(password);
            String buttonLoginXpath = "//button[text()='Log In']";
            driver.findElement(By.xpath(buttonLoginXpath)).click();
        } catch (Exception e) {
            System.out.println("Sign in failed!");
            e.printStackTrace();
        }
    }
}
