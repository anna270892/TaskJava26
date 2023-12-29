package test;

import lombok.val;
import org.junit.jupiter.api.*;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static data.DbHelper.*;


public class loginTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tidyUp() {
        wipeCodes();
    }

    @AfterAll
    static void totalTidyUp() {
        wipeEverything();
    }

    @Test
    void shouldLogIn() {
        val verificationPage = new LoginPage().validLogin(getAuthInfo());
        verificationPage.validVerify(getCode());
    }

    @Test
    @Disabled
    void shouldBeBlocked() {
        LoginPage page = new LoginPage();
        page.login(getAnotherAuthInfo());
        page.login(getAnotherAuthInfo());
        page.blockingLogin(getAnotherAuthInfo());
    }
}