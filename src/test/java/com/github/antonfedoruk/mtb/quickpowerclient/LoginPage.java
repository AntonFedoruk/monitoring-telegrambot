package com.github.antonfedoruk.mtb.quickpowerclient;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *  Implementation of a Page Object pattern, that contains the location of login page elements and methods for interacting with those elements.
 * */
public class LoginPage {
    public WebDriver driver;

    //class constructor that initializes class fields
    public LoginPage(WebDriver driver) {
        //In order for the @FindBy annotation to work, we need to use the PageFactory class.
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"admin".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the login page");
        }
    }

    //defining login input field locator
    @FindBy(xpath = "//input[@placeholder='Enter login...']")
    private WebElement loginField;

    //defining password input field locator
    @FindBy(xpath = "//input[@placeholder='Enter password...']")
    private WebElement passwdField;

    //defining login button locator
    @FindBy(xpath = "//button[text()='Log In']")
    private WebElement loginBtn;

    //login method
    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    //password entry method
    public void inputPassword(String password) {
        passwdField.sendKeys(password);
    }

    //method for pressing the login button
    public void clickLoginBtn() {
        loginBtn.click();
    }
}