package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.pages.AuthorizationPage;
import ru.yandex.practicum.pages.MainPage;
import ru.yandex.practicum.pages.ProfilePage;
import ru.yandex.practicum.pages.RegistrationPage;
import ru.yandex.practicum.api.user.User;
import ru.yandex.practicum.api.user.UserSteps;

import java.util.Locale;

import static org.junit.Assert.*;
import static ru.yandex.practicum.config.RestConfig.HOST;

public class LoginTest extends BaseTest {

    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private AuthorizationPage authorizationPage;
    private ProfilePage profilePage;

    private User user;
    private final UserSteps userSteps = new UserSteps();

    @Before
    public void setUp() {
        super.setUp();
        Faker faker = new Faker();

        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(6, 12);

        user = new User(email, password, name, "");

        driver.get(HOST);
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя (через UI)")
    @Description("Регистрация через UI, логин через UI, проверка email в профиле, удаление через API")
    public void successfulRegistration() {
        // Перейти на форму регистрации
        mainPage.waitForEnterAccountButton();
        mainPage.clickEnterAccountButton();

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickRegistration();

        // Инициализируем страницу регистрации
        registrationPage = new RegistrationPage(driver);

        // Заполнить форму и зарегистрироваться
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());

        // После успешной регистрации ожидаем попасть на форму входа (AuthorizationPage)
        authorizationPage = new AuthorizationPage(driver);

        // Теперь логинимся через UI, чтобы убедиться, что регистрация действительно создала учётную запись
        authorizationPage.enterUserDetails(user.getEmail(), user.getPassword());
        authorizationPage.clickEnterButton();

        // Индикатор успешного входа — кнопка "Оформить заказ"
        mainPage.waitForCheckoutButton();

        // Переходим в профиль и проверяем email
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();

        assertEquals("Email в профиле не совпадает с зарегистрированным",
                user.getEmail().toLowerCase(Locale.ROOT), profilePage.getEmailText().toLowerCase(Locale.ROOT));
    }

    @Test
    @DisplayName("Регистрация с коротким паролем, показывает ошибку")
    @Description("Попытка регистраций с паролем короче 6 символов должна показать ошибку под полем 'Пароль'")
    public void registrationWithShortPasswordShowsError() {
        // Перейти на форму регистрации
        mainPage.waitForEnterAccountButton();
        mainPage.clickEnterAccountButton();

        authorizationPage = new AuthorizationPage(driver);
        authorizationPage.clickRegistration();

        registrationPage = new RegistrationPage(driver);

        // Создаём временные данные с коротким паролем (5 символов)
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();
        String shortPassword = "12345";

        // Пытаемся зарегистрировать
        registrationPage.register(name, email, shortPassword);

        // Читаем ошибку под полем пароля
        String err = registrationPage.getPasswordErrorMessage();
        assertNotNull("Ожидалось сообщение об ошибке под полем пароля", err);
        assertFalse("Ошибка под полем пароля не должна быть пустой", err.trim().isEmpty());

        String lower = err.toLowerCase(Locale.ROOT);
        assertTrue("Ожидалось сообщение, содержащее слово 'некоррект' (например: 'Некорректный пароль'). Текст ошибки: " + err,
                lower.contains("некоррект"));
    }

    @After
    public void tearDown() {
        if (user != null) {
            try {
                ValidatableResponse loginResp = userSteps.loginUser(user);
                int status = loginResp.extract().statusCode();

                if (status == 200) {
                    String token = userSteps.extractAccessToken(loginResp);
                    if (token != null && !token.isEmpty()) {
                        userSteps.deleteUser(token);
                    }
                } else {
                    System.out.println("User deletion skipped: login returned status " + status);
                }
            } catch (Exception e) {
                System.err.println("Failed to delete user in tearDown: " + e.getMessage());
            }
        }

        super.tearDown();
    }
}
