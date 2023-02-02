package com.github.antonfedoruk.mtb.config;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Basic Selenium(WebDriver) configuration.
 */
@Configuration
public class SeleniumConfiguration {
    private final String chromeDriverServerUrl;

    public SeleniumConfiguration(@Value("${selenium.chromedriver.server.url}") String chromeDriverServerUrl) {
        this.chromeDriverServerUrl = chromeDriverServerUrl;
    }

    @PreDestroy
    void close() {
        driver().quit();
//        Quit will:
//        - Close all the windows and tabs associated with that WebDriver session
//        - Close the browser process
//        - Close the background driver process
    }

    /**
     * Create Been of {@link org.openqa.selenium.WebDriver} type.
     * <p>
     * It uses {@link URL} to the server. And browsers option {@link  ChromeOptions}
     * @return {@link RemoteWebDriver} object.
     */
    @Bean
    public RemoteWebDriver driver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");// Bypass OS security model, MUST BE THE VERY FIRST OPTION
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
        chromeOptions.addArguments("--start-maximized"); //or: driver.manage().window().maximize();
        //create chrome instance
        try {
            return new RemoteWebDriver(new URL(chromeDriverServerUrl), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}