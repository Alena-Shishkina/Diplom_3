package ru.yandex.practicum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    private static final Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/browser.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла browser.properties", e);
        }
    }

    public static WebDriver getNewDriver() {

        String browserFromSystem = System.getProperty("browser");
        String browser;
        if (browserFromSystem != null && !browserFromSystem.trim().isEmpty()) {
            browser = browserFromSystem.trim().toLowerCase();
        } else {
            browser = properties.getProperty("browser", "chrome").trim().toLowerCase();
        }

        String headlessProp = System.getProperty("headless");
        boolean headless = headlessProp != null && (headlessProp.equalsIgnoreCase("true") || headlessProp.equals("1"));

        switch (browser) {
            case "chrome":
                return createChromeDriver(headless);
            case "yandex":
                return createYandexDriver(headless);
            default:
                throw new IllegalArgumentException(
                        "Указан неподдерживаемый браузер: " + browser +
                                ". Допустимые значения: chrome, yandex. Пример запуска: mvn test -Dbrowser=chrome"
                );
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-features=BatterySaver,BackgroundTimerThrottling,RendererCodeIntegrity");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createYandexDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        String yandexDriverPath = properties.getProperty("yandex.driver.path", "./src/test/resources/yandexdriver.exe");
        if (yandexDriverPath == null || yandexDriverPath.trim().isEmpty()) {
            throw new IllegalArgumentException("В browser.properties необходимо указать параметр yandex.driver.path");
        }
        System.setProperty("webdriver.chrome.driver", yandexDriverPath);

        String binaryPath = properties.getProperty("yandex.browser.path");
        if (binaryPath == null || binaryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Нужно указать путь к бинарнику Yandex Browser в 'yandex.browser.path'");
        }
        options.setBinary(binaryPath);

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        return driver;
    }
}