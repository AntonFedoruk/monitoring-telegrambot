package com.github.antonfedoruk.mtb.quickpowerclient;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@PropertySource("classpath:application.properties ")
@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
class SeleniumScraperServiceImplTest {

    private final String login = ApplicationProperties.getProperty("quickpower.login.value");
    private final String password = ApplicationProperties.getProperty("quickpower.password.value");
    private final String url = ApplicationProperties.getProperty("quickpower.url");
    private final String chromeDriversLocaion = ApplicationProperties.getProperty("selenium.chromedriver.location");

    public static LoginPage loginPage;
//    public static StationsPage stationsPage;
    public static WebDriver driver;

    private ScraperService scraperService;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriversLocaion);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);

        SeleniumScraperServiceImpl seleniumScraperService = new SeleniumScraperServiceImpl((ChromeDriver) driver);
        seleniumScraperService.setQuickpowerUrl(url);
        seleniumScraperService.setLogin(login);
        seleniumScraperService.setPassword(password);
        scraperService = seleniumScraperService;
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("Should properly get station count")
    void shouldProperlyGetStationCount() {
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