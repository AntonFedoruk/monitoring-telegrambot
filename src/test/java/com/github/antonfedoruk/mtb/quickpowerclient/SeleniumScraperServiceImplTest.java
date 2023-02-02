package com.github.antonfedoruk.mtb.quickpowerclient;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.PropertySource;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@PropertySource("classpath:application.properties ")
@DisplayName("Integration-level testing for SeleniumScraperServiceImpl")
class SeleniumScraperServiceImplTest {

    private final String login = ApplicationProperties.getProperty("quickpower.login.value");
    private final String password = ApplicationProperties.getProperty("quickpower.password.value");
    private final String url = ApplicationProperties.getProperty("quickpower.url");
    private final String chromedriverServerUrl = ApplicationProperties.getProperty("selenium.chromedriver.server.url");

    public static LoginPage loginPage;
    public static WebDriver driver;

    private ScraperService scraperService;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        try {
            driver = new RemoteWebDriver(new URL(chromedriverServerUrl), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);

        SeleniumScraperServiceImpl seleniumScraperService = new SeleniumScraperServiceImpl((RemoteWebDriver) driver);
        seleniumScraperService.setQuickpowerUrl(url);
        seleniumScraperService.setLogin(login);
        seleniumScraperService.setPassword(password);

        scraperService = seleniumScraperService;

        loginPage = new LoginPage(driver);
    }

//    @Test
//    @DisplayName("Verify the connection to the remote webdriver.")
//    void verifyTheConnectionToTheRemoteWebdriver(){
//        Assertions.assertDoesNotThrow(() -> driver = new RemoteWebDriver(new URL(chromedriverServerUrl), new ImmutableCapabilities("browserName", "chrome")));
//    }

    @Test
    @DisplayName("Check if ScraperService see all stations")
    void checkIfScraperServiceSeeAllStations() {
        // given when
        int stationCount = scraperService.extractStations().size();
        // then
        Assertions.assertEquals(8, stationCount);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}