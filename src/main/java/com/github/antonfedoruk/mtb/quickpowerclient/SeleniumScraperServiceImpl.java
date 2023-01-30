package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.StationStatus;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
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
    @Value("${quickpower.password.value}")
    private String password;

    private boolean isLogged = false;

    //WebDriver represents the browser
    private final RemoteWebDriver driver;

    private WebDriverWait wait; // Explicit wait(JS should load a response to the page)

    public SeleniumScraperServiceImpl(RemoteWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //get list of stations from site
    @Override
    public List<Station> extractStations() {
        System.out.println("Go to website.");
        driver.get(quickpowerUrl);

        System.out.println("Log in.");
        signIn(login, password);

        List<WebElement> stationsFromPage = getStationsFromPage();
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
        String address = getStationAddress(element);
        StationStatus stationStatus = getStationStatus(element);
        Station station = new Station();
        station.setAddress(address);
        station.setName(location);
        station.setId(Math.abs(location.hashCode()));
        station.setStationStatus(stationStatus);
        return station;
    }

//    private String getStationStatus(WebElement element) {
//        return element.findElement(By.cssSelector("span.location > span:first-child")).getAttribute("title");
//    }

    private String getStationAddress(WebElement element) {
        return element.findElement(By.cssSelector("span.location + span")).getText();
    }

    private String getStationLocation(WebElement element) {
        return element.findElement(By.cssSelector("span.location > span:last-child")).getText();
    }

    private StationStatus getStationStatus(WebElement element) {
        String statusFromElement = element.findElement(By.cssSelector("span.location > span:first-child")).getAttribute("title");

        for (StationStatus status : StationStatus.values()) {
            if (status.name().equalsIgnoreCase(statusFromElement)) {
                return status;
            }
        }
        return StationStatus.ERROR;
    }

    private List<WebElement> getStationsFromPage() {
        String stationListElementCssSelector = "ul.sc-kgoBCf.hOeolh";
        System.out.println("Trying to get stations.");
        try {
            wait.until(webDriver -> webDriver.findElement(By.cssSelector(stationListElementCssSelector))); //

            WebElement chargeListElement = driver.findElement(By.cssSelector(stationListElementCssSelector));
            System.out.println("Station list obtained");
            return chargeListElement.findElements(By.cssSelector("li.withCP"));
        } catch (Exception e) {
            System.out.println("Station loading failed.");
            e.printStackTrace();
            return null;
        }
    }

    private void signIn(String login, String password) {
        if (isLogged) {
            return;
        }

        String inputLoginXpath = "//input[@placeholder='Enter login...']";

        try {
            wait.until(webDriver -> webDriver.findElement(By.xpath(inputLoginXpath)));
            // fill login data on sign-in page
            System.out.println("fill login data on sign-in page...");
            driver.findElement(By.xpath(inputLoginXpath)).sendKeys(login);
            String inputPasswordXpath = "//input[@placeholder='Enter password...']";
            driver.findElement(By.xpath(inputPasswordXpath)).sendKeys(password);
            String buttonLoginXpath = "//button[text()='Log In']";
            driver.findElement(By.xpath(buttonLoginXpath)).click();
        } catch (Exception e) {
            System.out.println("Sign in failed!");
            e.printStackTrace();
        }
        isLogged = true;
    }
}
