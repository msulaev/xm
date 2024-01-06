package com.xm.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.xm.utils.Const.BROWSER_SIZE_1024x768;
import static com.xm.utils.Const.BROWSER_SIZE_800x600;


public class MainPage {
    private final SelenideElement acceptModal = $(byText("ACCEPT ALL"));
    private final SelenideElement menu = $(byText("Menu"));
    private final SelenideElement researchEducation = $(byText("RESEARCH & EDUCATION"));
    private final SelenideElement researchEducationSmallLeftMenu = $("[href='#researchMenu']");

    @Step("Open main page")
    public static void open() {
        Selenide.open("/");
    }

    @Step("Click accept modal")
    public void acceptModal() {
        acceptModal.shouldBe(visible).click();
    }

    @Step("Navigate to {0}")
    public void navigation(String option) {
        switch (Configuration.browserSize) {
            case BROWSER_SIZE_800x600 -> {
                menu.click();
                researchEducationSmallLeftMenu.click();
                $("[href='https://www.xm.com/" + option + "'] i").scrollTo().click();
            }
            case BROWSER_SIZE_1024x768 -> {
                researchEducation.click();
                $("[href='https://www.xm.com/" + option + "']").scrollTo().click();
            }
            default -> {
                researchEducation.click();
                $("[href='https://www.xm.com/" + option + "']").click();
            }
        }
    }
}
