package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    private final WebDriver driver;
    private final WebDriverWait defaultWait;

    private static final Duration DEFAULT = Duration.ofSeconds(10);
    private static final By PROFILE_TEXT = By.xpath(".//*[contains(text(), 'В этом разделе вы можете изменить свои персональные данные')]");
    private static final By NAME_INPUT = By.xpath(".//li[1]//input");
    private static final By EMAIL_INPUT = By.xpath(".//li[2]//input");


    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.defaultWait = new WebDriverWait(driver, DEFAULT);
        waitProfilePageLoad();
    }

    private WebElement waitVisible(By locator) {
        return defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private String getValue(By locator) {
        return waitVisible(locator).getAttribute("value");
    }

    @Step("Ожидание загрузки страницы профиля")
    public ProfilePage waitProfilePageLoad() {
        waitVisible(PROFILE_TEXT);
        return this;
    }

    @Step("Получить значение поля Имя")
    public String getNameText() {
        return getValue(NAME_INPUT);
    }

    @Step("Получить значение поля Email")
    public String getEmailText() {
        return getValue(EMAIL_INPUT);
    }
}