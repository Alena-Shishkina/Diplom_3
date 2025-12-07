package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationPage {

    private final WebDriver driver;
    private final WebDriverWait defaultWait;
    private static final Duration DEFAULT = Duration.ofSeconds(10);

    private static final By ENTER_PROFILE_BUTTON = By.xpath(".//button[text()='Войти']");
    private static final By ENTER_FORM_HEAD = By.xpath("//*[contains(text(), 'Вход')]");
    private static final By INPUT_EMAIL = By.xpath(".//label[text()='Email']/../input");
    private static final By INPUT_PASSWORD = By.xpath(".//label[text()='Пароль']/../input");
    private static final By REGISTRATION_LINK = By.className("Auth_link__1fOlj");
    private static final By RECOVER_PASSWORD_LINK = By.xpath(".//a[text()='Восстановить пароль']");


    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
        this.defaultWait = new WebDriverWait(driver, DEFAULT);

        waitForPageLoad();
    }

    private WebElement waitVisible(By locator) {
        return defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitClickable(By locator) {
        return defaultWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void click(By locator) {
        waitClickable(locator).click();
    }

    private void fillField(By locator, String value) {
        WebElement field = waitVisible(locator);
        field.clear();
        field.sendKeys(value);
    }

    @Step("Ожидание загрузки страницы авторизации")
    private void waitForPageLoad() {
        waitVisible(ENTER_FORM_HEAD);
    }

    @Step("Ввод данных пользователя: email, пароль")
    public AuthorizationPage enterUserDetails(String email, String password) {
        fillField(INPUT_EMAIL, email);
        fillField(INPUT_PASSWORD, password);
        return this;
    }

    @Step("Нажатие на кнопку «Войти»")
    public AuthorizationPage clickEnterButton() {
        click(ENTER_PROFILE_BUTTON);
        return this;
    }

    @Step("Переход по ссылке «Зарегистрироваться»")
    public AuthorizationPage clickRegistration() {
        click(REGISTRATION_LINK);
        return this;
    }

    @Step("Переход по ссылке «Восстановить пароль»")
    public AuthorizationPage clickRecoverPassword() {
        click(RECOVER_PASSWORD_LINK);
        return this;
    }
}