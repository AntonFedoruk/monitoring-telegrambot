package com.github.antonfedoruk.mtb.quickpowerclient;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 *  Implementation of a Page Object pattern, that contains station elements and methods for interacting with those elements.
 */
public class StationsPage {
    public WebDriver driver;

    //class constructor that initializes class fields
    public StationsPage(WebDriver driver) {
        //In order for the @FindBy annotation to work, we need to use the PageFactory class.
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //defining stations list locator
    @FindBy(css = "ul.sc-kgoBCf.hOeolh")
    private WebElement stationsList;

    //method for getting stations from the list
    public List<WebElement> getStationsFromPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.sc-kgoBCf.hOeolh")));

        return stationsList.findElements(By.cssSelector("li.withCP"));
    }
}
