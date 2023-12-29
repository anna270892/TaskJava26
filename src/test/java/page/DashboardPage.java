package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private static SelenideElement heading = $("[data-test-id=dashboard]");

    public DashboardPage() {
        heading.shouldBe(visible).shouldHave(text("Личный кабинет"));
    }
}