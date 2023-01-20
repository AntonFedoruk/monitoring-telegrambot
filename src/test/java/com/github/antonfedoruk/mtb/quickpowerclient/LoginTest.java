package com.github.antonfedoruk.mtb.quickpowerclient;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;
import java.util.List;

@PropertySource("classpath:application.properties ")
@DisplayName("Unit-level testing for successful login.")
public class LoginTest {
    private final String login = ApplicationProperties.getProperty("quickpower.login.value");
    private final String password = ApplicationProperties.getProperty("quickpower.password.value");
    private final String url = ApplicationProperties.getProperty("quickpower.url");
    private final String chromeDriversLocaion = ApplicationProperties.getProperty("selenium.chromedriver.location");

    public static LoginPage loginPage;
    public static StationsPage stationsPage;
    public static WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriversLocaion);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        loginPage = new LoginPage(driver);
        stationsPage = new StationsPage(driver);
    }

    @Test
    @DisplayName("Should successfully log in")
    public void shouldSuccessfullyLogInUsingCredentialsFromPropertiesAndShowStationList() {
        loginPage.inputLogin(login);
        loginPage.inputPassword(password);
        loginPage.clickLoginBtn();
        List<WebElement> stationsFromPage = stationsPage.getStationsFromPage();
        Assertions.assertTrue(stationsFromPage.size()>0);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
