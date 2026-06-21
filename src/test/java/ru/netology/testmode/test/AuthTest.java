package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    // 1. ✅ Успешный вход с активным зарегистрированным пользователем
    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        // Вводим логин
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        // Вводим пароль
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        // Нажимаем кнопку "Войти"
        $("[data-test-id='action-login']").click();

        // Проверяем, что успешно вошли в личный кабинет
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    // 2. ❌ Ошибка при входе незарегистрированного пользователя
    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        // Вводим логин
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        // Вводим пароль
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        // Нажимаем кнопку "Войти"
        $("[data-test-id='action-login']").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    // 3. ❌ Ошибка при входе заблокированного пользователя
    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");

        // Вводим логин
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        // Вводим пароль
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        // Нажимаем кнопку "Войти"
        $("[data-test-id='action-login']").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    // 4. ❌ Ошибка при входе с неверным логином
    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        // Вводим НЕВЕРНЫЙ логин
        $("[data-test-id='login'] input").setValue(wrongLogin);
        // Вводим правильный пароль
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        // Нажимаем кнопку "Войти"
        $("[data-test-id='action-login']").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    // 5. ❌ Ошибка при входе с неверным паролем
    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        // Вводим правильный логин
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        // Вводим НЕВЕРНЫЙ пароль
        $("[data-test-id='password'] input").setValue(wrongPassword);
        // Нажимаем кнопку "Войти"
        $("[data-test-id='action-login']").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}