package org.example.banklogin.test;

import org.example.banklogin.data.DataHelper;
import org.example.banklogin.data.SQLHelper;
import org.example.banklogin.page.LoginPage;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Selenide.open;
import static org.example.banklogin.data.SQLHelper.cleanAuthCodes;
import static org.example.banklogin.data.SQLHelper.cleanDatabase;


public class LoginTest {

    LoginPage loginPage;

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

    @Test
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorNotificationUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorNotificationCode() {
            var authInfo = DataHelper.getAuthInfoWithTestData();
            var verificationPage = loginPage.validLogin(authInfo);
            verificationPage.verifyVerificationPageVisiblity();
            var verificationCode = DataHelper.generateRandomVerificationCode();
            verificationPage.verify(verificationCode.getCode());
            verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте еще раз.");
        }
    }
