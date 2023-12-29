package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement blockedHeading = $("[data-test-id=blocked]");

    public void login(DataHelper.AuthInfo info) {
        loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), info.getLogin());
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }

    public void blockingLogin(DataHelper.AuthInfo info) {
        login(info);
        blockedHeading.shouldBe(visible).shouldHave(text("Система заблокирована после трёх неудачных попыток входа"));
    }
}