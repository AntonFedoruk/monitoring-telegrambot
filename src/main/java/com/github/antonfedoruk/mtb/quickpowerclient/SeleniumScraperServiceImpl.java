package com.github.antonfedoruk.mtb.quickpowerclient;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.*;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    private final WebDriverWait wait; // Explicit wait(JS should load a response to the page)

    public SeleniumScraperServiceImpl(RemoteWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //get list of stations from site
    @Override
    public List<Station> extractStations() {
        System.out.println("Go to website. ");
        driver.get(quickpowerUrl);

        System.out.println("Log in.");
        signIn(login, password);

        String stationListTableCssSelector = "ul.sc-kgoBCf.hOeolh";
        wait.until(webDriver -> webDriver.findElement(By.cssSelector(stationListTableCssSelector)));
        WebElement stationTableFromPage = driver.findElement(By.cssSelector(stationListTableCssSelector));
        List<WebElement> stationsFromPage = getStationsFromElement(stationTableFromPage);
        List<WebElement> substationsFromPage = getSubstationsFromElement(stationTableFromPage);
        return combineStationsWithSubstationsAndConvertToStationsList(stationsFromPage, substationsFromPage);
    }

    private List<WebElement> getStationsFromElement(WebElement initialWebElement) {
        String stationListElementCssSelector = "li.withCP";
        return getListByCssSelector(initialWebElement, stationListElementCssSelector, "Substations");
    }

    private List<WebElement> getSubstationsFromElement(WebElement initialWebElement) {
        String stationListElementCssSelector = "ul.sc-kGXeez.fgVfqV li.cp";
        return getListByCssSelector(initialWebElement, stationListElementCssSelector, "Stations");
//        String stationListElementCssSelector = "ul.sc-kgoBCf.hOeolh";
//        System.out.println("Trying to get stations.");
//        try {
//            wait.until(webDriver -> webDriver.findElement(By.cssSelector(stationListElementCssSelector))); //
//
//            WebElement chargeListElement = driver.findElement(By.cssSelector(stationListElementCssSelector));
//            System.out.println("Station list obtained");
//            return chargeListElement.findElements(By.cssSelector("li.withCP"));
//        } catch (Exception e) {
//            System.out.println("Station loading failed.");
//            e.printStackTrace();
//            return null;
//        }
    }

    private List<WebElement> getListByCssSelector(WebElement initialWebElement, String cssSelector, String logs) {
        System.out.println("Trying to get list of " + logs +".");
        try {
            List<WebElement> webElementList = initialWebElement.findElements(By.cssSelector(cssSelector));
            System.out.println(logs + " list obtained.");
            return webElementList;
        } catch (Exception e) {
            System.out.println(logs + " loading failed.");
            e.printStackTrace();
            return null;
        }
    }

    private List<Station> combineStationsWithSubstationsAndConvertToStationsList(List<WebElement> stationsFromPage, List<WebElement> substationsFromPage) {
        List<Station> stations = new ArrayList<>();

        int substationCount = 0;
        for (WebElement stationWebElement : stationsFromPage) {
            Station station = new Station();
            String stationLocation = getStationLocation(stationWebElement);
            station.setId((Math.abs(stationLocation.hashCode())));
            station.setName(stationLocation);
            station.setAddress(getStationAddress(stationWebElement));
            station.setStationStatus(getStationStatus(stationWebElement));

            List<SubStation> stationSubstations = new ArrayList<>();
            int numberOfSubstations = stationWebElement.findElements(By.cssSelector("span.stationsStatus ul li")).size();
            for (int substationNumber = 0; substationNumber < numberOfSubstations; substationNumber++) {
                SubStation subStation = new SubStation();
                WebElement subStationWebElement = substationsFromPage.get(substationCount);
                substationCount++;
                subStation.setName(getSubStationName(subStationWebElement));
                subStation.setModel(getSubStationModel(subStationWebElement));
                subStation.setFirmware(getSubStationFirmware(subStationWebElement));
                subStation.setSubStationStatus(getSubStationStatus(subStationWebElement));
                subStation.setConnectors(getConnectors(subStationWebElement));

                stationSubstations.add(subStation);
            }
            station.setSubStations(stationSubstations);
            stations.add(station);
        }
        return stations;
    }

    private List<Connector> getConnectors(WebElement subStationWebElement) {
//        List<WebElement> connectorsFromPage = subStationWebElement.findElements(By.xpath("//span[5]/ul"));
        List<WebElement> connectorsFromPage = subStationWebElement.findElements(By.cssSelector("span:nth-child(5) > ul > li"));
        return connectorsFromPage.stream().map(connectorWebElement -> new Connector(
                getConnectorsType(connectorWebElement),
                getConnectorsStatus(connectorWebElement)
        )).collect(Collectors.toList());
    }

    private ConnectorsStatus getConnectorsStatus(WebElement connectorWebElement) {
//        String connectorsClassesFromPage = connectorWebElement.findElement(By.xpath("//li/span")).getAttribute("class");
        String connectorsClassesFromPage = connectorWebElement.findElement(By.cssSelector("span")).getAttribute("class");

        String connectorStatusFromPage = connectorsClassesFromPage.substring(0, connectorsClassesFromPage.trim().indexOf(" "));
        for (ConnectorsStatus connectorStatus : ConnectorsStatus.values()) {
            if (connectorStatus.name().equalsIgnoreCase(connectorStatusFromPage)) {
                return connectorStatus;
            }
        }
        return ConnectorsStatus.UNKNOWN;
    }

    private ConnectorsType getConnectorsType(WebElement connectorWebElement) {
//        String connectorsTypeFromPage = connectorWebElement.findElement(By.xpath("//li")).getDomProperty("innerText");
        String connectorsTypeFromPage = connectorWebElement.getDomProperty("innerText");
        String specialCharacters = "[ #'().:{|}-]";
        Pattern p = Pattern.compile(specialCharacters);
        for (ConnectorsType connectorType : ConnectorsType.values()) {
            if (connectorType.name().equalsIgnoreCase(p.matcher(connectorsTypeFromPage).replaceAll(""))) {
                return connectorType;
            }
        }
        return ConnectorsType.UNKOWN;
    }

    private SubStationStatus getSubStationStatus(WebElement subStationWebElement) {
//        String statusFromPage = subStationWebElement.findElement(By.xpath("//span[4]")).getDomProperty("innerText");
        String statusFromPage = subStationWebElement.findElement(By.cssSelector("span:nth-child(4)")).getDomProperty("innerText");
        for (SubStationStatus status : SubStationStatus.values()) {
            if (status.name().equalsIgnoreCase(statusFromPage)) {
                return status;
            }
        }
        return SubStationStatus.ERROR;
    }

    private StationFirmware getSubStationFirmware(WebElement subStationWebElement) {
        String specialCharacters = "[ #'().:{|}-]";
        Pattern p = Pattern.compile(specialCharacters);
//        String firmwareFromPage = subStationWebElement.findElement(By.xpath("//span[3]")).getDomProperty("innerText");
        String firmwareFromPage = subStationWebElement.findElement(By.cssSelector("span:nth-child(3)")).getDomProperty("innerText");
        for (StationFirmware firmware : StationFirmware.values()) {
            if (firmware.name().equalsIgnoreCase(p.matcher(firmwareFromPage).replaceAll(""))) {
                return firmware;
            }
        }
        return StationFirmware.UNKNOWN;
    }

    private static String getSubStationName(WebElement subStationWebElement) {
//        return subStationWebElement.findElement(By.xpath("//span/span")).getDomProperty("innerText");
        return subStationWebElement.findElement(By.cssSelector("span.station > span")).getDomProperty("innerText");
    }

    private static StationModel getSubStationModel(WebElement subStationWebElement) {
        String specialCharacters = "[ #'().:{|}-]";
        Pattern p = Pattern.compile(specialCharacters);
//        String modelFromPage = subStationWebElement.findElement(By.xpath("//span[2]")).getDomProperty("innerText");
        String modelFromPage = subStationWebElement.findElement(By.cssSelector("span:nth-child(2)")).getDomProperty("innerText");
        for (StationModel model : StationModel.values()) {
            if (model.name().equalsIgnoreCase(p.matcher(modelFromPage).replaceAll(""))) {
                return model;
            }
        }
        return StationModel.UNKNOWN;
    }

    private String getStationAddress(WebElement stationFromPage) {
        return stationFromPage.findElement(By.cssSelector("span.location + span")).getText();
    }

    private String getStationLocation(WebElement stationFromPage) {
        return stationFromPage.findElement(By.cssSelector("span.location > span:last-child")).getText();
    }

    private StationStatus getStationStatus(WebElement stationFromPage) {
        String statusFromElement = stationFromPage.findElement(By.cssSelector("span.location > span:first-child")).getAttribute("title");

        for (StationStatus status : StationStatus.values()) {
            if (status.name().equalsIgnoreCase(statusFromElement)) {
                return status;
            }
        }
        return StationStatus.ERROR;
    }

    private void signIn(String login, String password) {
        if (isLogged) {
            return;
        }

        String inputLoginXpath = "//input[@placeholder='Enter login...']";

        try {
            wait.until(webDriver -> webDriver.findElement(By.xpath(inputLoginXpath)));
            // fill login data on sign-in page
            driver.findElement(By.xpath(inputLoginXpath)).sendKeys(login);
            String inputPasswordXpath = "//input[@placeholder='Enter password...']";
            driver.findElement(By.xpath(inputPasswordXpath)).sendKeys(password);
            String buttonLoginXpath = "//button[text()='Log In']";
            driver.findElement(By.xpath(buttonLoginXpath)).click();

            String stationListTableCssSelector = "ul.sc-kgoBCf.hOeolh";
            wait.until(webDriver -> webDriver.findElement(By.cssSelector(stationListTableCssSelector)));
        } catch (Exception e) {
            System.out.println("Login failed!");
            e.printStackTrace();
        }
        isLogged = true;
    }
}
