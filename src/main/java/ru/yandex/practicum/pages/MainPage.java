package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait shortWait;
    private final WebDriverWait defaultWait;
    private final WebDriverWait longWait;
    public static final Duration SHORT = Duration.ofSeconds(5);
    public static final Duration DEFAULT = Duration.ofSeconds(30);
    public static final Duration LONG = Duration.ofSeconds(15);

    public static final String ACTIVE_TAB_CLASS_FRAGMENT = "tab_tab_type_current__2BEPc";
    private static final By ENTER_ACCOUNT_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By PERSONAL_ACCOUNT_BUTTON = By.xpath(".//p[contains(@class, 'AppHeader_header__linkText') and contains(@class, 'ml-2') and text()='Личный Кабинет']");
    private static final By CHECKOUT_BUTTON = By.xpath(".//button[text()='Оформить заказ']");
    public static final By BUNS_TAB = By.xpath(".//span[text()='Булки']/parent::div");
    public static final By SAUCES_TAB = By.xpath(".//span[text()='Соусы']/parent::div");
    public static final By FILLINGS_TAB = By.xpath(".//*[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.shortWait = new WebDriverWait(driver, SHORT);
        this.defaultWait = new WebDriverWait(driver, DEFAULT);
        this.longWait = new WebDriverWait(driver, LONG);
    }

    private WebElement waitForVisible(By locator, WebDriverWait wait) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForClickable(By locator, WebDriverWait wait) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    private MainPage click(By locator, WebDriverWait wait) {
        WebElement el = waitForClickable(locator, wait);
        el.click();
        return this;
    }

    private String getAttribute(By locator, String attribute, WebDriverWait wait) {
        return waitForVisible(locator, wait).getAttribute(attribute);
    }

    @Step("Ожидать видимость кнопки «Войти в аккаунт»")
    public MainPage waitForEnterAccountButton() {
        waitForVisible(ENTER_ACCOUNT_BUTTON, defaultWait);
        return this;
    }

    @Step("Нажать кнопку «Войти в аккаунт»")
    public MainPage clickEnterAccountButton() {
        return click(ENTER_ACCOUNT_BUTTON, defaultWait);
    }

    @Step("Ожидать видимость кнопки «Личный Кабинет»")
    public MainPage waitForPersonalAccountButton() {
        waitForVisible(PERSONAL_ACCOUNT_BUTTON, shortWait);
        return this;
    }

    @Step("Нажать «Личный Кабинет»")
    public MainPage clickPersonalAccountButton() {
        return click(PERSONAL_ACCOUNT_BUTTON, defaultWait);
    }

    @Step("Ожидать видимость кнопки «Оформить заказ»")
    public MainPage waitForCheckoutButton() {
        waitForVisible(CHECKOUT_BUTTON, longWait);
        return this;
    }

    @Step("Нажать вкладку «Булки»")
    public MainPage clickBunsLink() {
        return click(BUNS_TAB, defaultWait);
    }

    @Step("Нажать вкладку «Соусы»")
    public MainPage clickSaucesLink() {
        return click(SAUCES_TAB, defaultWait);
    }

    @Step("Нажать вкладку «Начинки»")
    public MainPage clickFillingsLink() {
        return click(FILLINGS_TAB, defaultWait);
    }

    @Step("Получить атрибут класса вкладки «Булки»")
    public String getClassNameBuns() {
        return getAttribute(BUNS_TAB, "class", shortWait);
    }

    @Step("Получить атрибут класса вкладки «Соусы»")
    public String getClassNameSauces() {
        return getAttribute(SAUCES_TAB, "class", shortWait);
    }

    @Step("Получить атрибут класса вкладки «Начинки»")
    public String getClassNameFillings() {
        return getAttribute(FILLINGS_TAB, "class", shortWait);
    }

    @Step("Ожидать, что вкладка «Булки» станет активной (таймаут: {timeoutSeconds}s)")
    public MainPage waitForBunsActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(BUNS_TAB, "class", ACTIVE_TAB_CLASS_FRAGMENT));
        return this;
    }

    @Step("Ожидать, что вкладка «Соусы» станет активной (таймаут: {timeoutSeconds}s)")
    public MainPage waitForSaucesActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(SAUCES_TAB, "class", ACTIVE_TAB_CLASS_FRAGMENT));
        return this;
    }

    @Step("Ожидать, что вкладка «Начинки» станет активной (таймаут: {timeoutSeconds}s)")
    public MainPage waitForFillingsActive(long timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(FILLINGS_TAB, "class", ACTIVE_TAB_CLASS_FRAGMENT));
        return this;
    }


    @Step("Кликнуть вкладку и дождаться, что она станет активной")
    public MainPage clickBunsAndWaitActive() {
        clickBunsLink();
        waitForBunsActive(DEFAULT.getSeconds());
        return this;
    }

    @Step("Кликнуть вкладку и дождаться, что она станет активной")
    public MainPage clickSaucesAndWaitActive() {
        clickSaucesLink();
        waitForSaucesActive(DEFAULT.getSeconds());
        return this;
    }

    @Step("Кликнуть вкладку и дождаться, что она станет активной")
    public MainPage clickFillingsAndWaitActive() {
        clickFillingsLink();
        waitForFillingsActive(DEFAULT.getSeconds());
        return this;
    }

    @Step("Кликнуть вкладку и дождаться, что она станет стабильно активной")
    public MainPage clickTabAndWaitStable(By tabLocator, long timeoutSeconds) {

        WebElement el = defaultWait.until(ExpectedConditions.elementToBeClickable(tabLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", el);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.attributeContains(tabLocator, "class", ACTIVE_TAB_CLASS_FRAGMENT));

        final int requiredConsecutiveChecks = 2;
        final long stabilityTimeoutSeconds = 5;
        final long pollMillis = 150;

        long deadline = System.currentTimeMillis() + stabilityTimeoutSeconds * 1000L;
        int consecutive = 0;

        while (System.currentTimeMillis() <= deadline) {
            String cls;
            try {
                cls = driver.findElement(tabLocator).getAttribute("class");
            } catch (NoSuchElementException e) {
                cls = null;
            }

            boolean has = cls != null && cls.contains(ACTIVE_TAB_CLASS_FRAGMENT);
            System.out.println("DEBUG stable check: class='" + cls + "' hasActive=" + has);

            if (has) {
                consecutive++;
                if (consecutive >= requiredConsecutiveChecks) {

                    return this;
                }
            } else {
                consecutive = 0;
            }

            try {
                Thread.sleep(pollMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }


        String finalClass = "";
        try {
            finalClass = driver.findElement(tabLocator).getAttribute("class");
        } catch (Exception ignored) {
        }
        throw new IllegalStateException("Вкладка не стала стабильной с active-class: " + tabLocator + ", actual class='" + finalClass + "'");
    }

}