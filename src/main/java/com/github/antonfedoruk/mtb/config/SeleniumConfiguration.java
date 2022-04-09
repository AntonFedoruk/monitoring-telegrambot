package com.github.antonfedoruk.mtb.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
 * Basic Selenium configuration.
 */
@Configuration
public class SeleniumConfiguration {
    private final String chromedriverLocation;

    public SeleniumConfiguration(@Value("${selenium.chromedriver.location}") String chromedriverLocation) {
        this.chromedriverLocation = chromedriverLocation;
    }

    @PostConstruct
    void init() {
        System.setProperty("webdriver.chrome.driver", chromedriverLocation);
    }

    @PreDestroy
    void close() {
        driver().quit();
//        Quit will:
//        - Close all the windows and tabs associated with that WebDriver session
//        - Close the browser process
//        - Close the background driver process
    }

    @Bean
    public ChromeDriver driver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");// Bypass OS security model, MUST BE THE VERY FIRST OPTION
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
        chromeOptions.addArguments("--start-maximized"); //or: driver.manage().window().maximize();
        //create chrome instance
        return new ChromeDriver(chromeOptions);
    }
}