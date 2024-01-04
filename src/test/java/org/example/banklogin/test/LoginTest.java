package org.example.banklogin.test;

import org.example.banklogin.data.DataHelper;
import org.example.banklogin.data.SQLHelper;
import org.example.banklogin.page.LoginPage;
import org.junit.jupiter.api.*;


import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static org.example.banklogin.data.SQLHelper.cleanAuthCodes;
import static org.example.banklogin.data.SQLHelper.cleanDatabase;


public class LoginTest {

    LoginPage loginPage;

    @BeforeAll
    static void startApplication() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "./artifacts/app-deadline.jar");
            Process process = processBuilder.start();
            Thread.sleep(5000); // Дайте приложению время запуститься (может потребоваться настройка)
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterAll
    static void stopApplication() {
    }

    //успешная авторизация
    @Test
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    //неверный логин и пароль
    @Test
    void shouldGetErrorNotificationUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    //неверный код
    @Test
    void shouldGetErrorNotificationCode() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }
}
