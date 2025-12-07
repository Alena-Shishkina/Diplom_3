package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait defaultWait;
    private static final Duration DEFAULT = Duration.ofSeconds(10);

    private static final By REGISTER_FORM_HEADING = By.xpath(".//*[text()='Регистрация']");
    private static final By INPUT_NAME = By.xpath(".//fieldset[1]//input");
    private static final By INPUT_EMAIL = By.xpath(".//fieldset[2]//input");
    private static final By INPUT_PASSWORD = By.xpath(".//fieldset[3]//input");
    private static final By REGISTER_BUTTON = By.xpath(".//button[text()='Зарегистрироваться']");
    private static final By ERROR_PASSWORD = By.xpath(".//fieldset[3]//p");
    private static final By ALREADY_REGISTERED_LOGIN_LINK = By.xpath(".//*[text()='Уже зарегистрированы?']/a");

    public RegistrationPage(WebDriver driver) {
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

    @Step("Ожидание загрузки страницы «Регистрация»")
    public RegistrationPage waitForPageLoad() {
        waitVisible(REGISTER_FORM_HEADING);
        return this;
    }

    @Step("Заполнение формы регистрации: имя, email, пароль")
    public RegistrationPage inputRegistrationForm(String name, String email, String password) {
        fillField(INPUT_NAME, name);
        fillField(INPUT_EMAIL, email);
        fillField(INPUT_PASSWORD, password);
        return this;
    }

    @Step("Нажатие на кнопку «Зарегистрироваться»")
    public RegistrationPage clickRegisterButton() {
        click(REGISTER_BUTTON);
        return this;
    }

    @Step("Регистрация нового пользователя")
    public RegistrationPage register(String name, String email, String password) {
        inputRegistrationForm(name, email, password);
        clickRegisterButton();
        return this;
    }

    @Step("Получение текста ошибки для поля «Пароль»")
    public String getPasswordErrorMessage() {
        return waitVisible(ERROR_PASSWORD).getText();
    }

    @Step("Переход по ссылке «Войти»")
    public RegistrationPage clickAlreadyRegisteredLink() {
        click(ALREADY_REGISTERED_LOGIN_LINK);
        return this;
    }
}