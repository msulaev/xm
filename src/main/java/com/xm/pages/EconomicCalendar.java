package com.xm.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.openqa.selenium.Keys.ARROW_RIGHT;

public class EconomicCalendar {
    private final SelenideElement passEvent = $(".tc-past-events-load-button");
    private final SelenideElement slider = $(".mat-slider");
    private final SelenideElement dateTitle = $(".tc-economic-calendar-item-header-left-title");

    public void switchToFrame() {
        switchTo().frame("iFrameResizer0");
        passEvent.shouldBe(exist);
    }

    @Step("Increase slider")
    public void increaseSlider() {
        slider.sendKeys(ARROW_RIGHT);
    }

    @Step("Check date is {0}")
    public void checkDate(String date) {
        dateTitle.shouldHave(text(date));
    }
}
