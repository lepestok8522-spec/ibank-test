package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    // Отправка запроса на регистрацию пользователя через API
    private static void sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    // Генерация случайного логина
    public static String getRandomLogin() {
        return faker.name().username();
    }

    // Генерация случайного пароля
    public static String getRandomPassword() {
        return faker.internet().password(6, 12);
    }

    // Класс для регистрации пользователей
    public static class Registration {
        private Registration() {
        }

        // Создание пользователя с указанным статусом (без регистрации)
        public static RegistrationDto getUser(String status) {
            String login = getRandomLogin();
            String password = getRandomPassword();
            return new RegistrationDto(login, password, status);
        }

        // Создание и регистрация пользователя через API
        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    // Data-класс для хранения данных пользователя
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}