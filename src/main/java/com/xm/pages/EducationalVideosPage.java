package com.xm.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.xm.utils.Const.BROWSER_SIZE_1024x768;
import static com.xm.utils.Const.BROWSER_SIZE_800x600;

public class EducationalVideosPage {

    private final SelenideElement frame = $(".sproutvideo-player");
    private final SelenideElement videoPlayer = $(".player");
    private final SelenideElement playBtn = $(".player-big-play-button");
    private final SelenideElement intoTheMarketsPnl = $("button[data-target='#js-collapse-trd-s1']");
    private final SelenideElement progressTime = $(".player-progress-time");

    @Step("Open lesson {0}")
    public void openLesson(String lesson) {
        switch (Configuration.browserSize) {
            case BROWSER_SIZE_800x600, BROWSER_SIZE_1024x768 -> {
                intoTheMarketsPnl.scrollTo().shouldBe(visible).hover().click();
                $(byText(lesson)).click();
            }
            default -> {
                intoTheMarketsPnl.hover().click();
                $(byText(lesson)).click();
            }
        }
    }

    public void switchToFrame() {
        switchTo().frame(frame);
    }

    @Step("Play video and check progress time")
    public void playVideo() {
        playBtn.shouldBe(visible).click();
        videoPlayer.shouldHave(cssClass("playing"));
        Selenide.sleep(5000);
        progressTime.shouldNot(text("00:00"));
    }
}
