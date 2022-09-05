package ru.netology.delivery.test;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.Data;

import static com.codeborne.selenide.Selenide.*;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    //зарегистрированный пользователь имеет доступ к личному кабинету
    @Test
    void registeredActive() {
        var validUser = Data.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    //зарегистрированный пользователь заблокирован
    @Test
    void registeredBlocked() {
        var validUser = Data.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }


    //Зарегистрированный пользователь пустое поле логина
    @Test
    void withoutlogin() {
        var validUser = Data.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    //зарегистрированный пользователь пустое поле пароля
    @Test
    void withoutPassword() {
        var validUser = Data.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }


}