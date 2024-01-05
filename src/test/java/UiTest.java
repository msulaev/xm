import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.xm.pages.EconomicCalendar;
import com.xm.pages.EducationalVideosPage;
import com.xm.pages.MainPage;
import com.xm.utils.DateUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Browsers.FIREFOX;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.xm.utils.Const.*;

public class UiTest {

    @BeforeMethod
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.headless = false;
        Configuration.browser = FIREFOX;
        Configuration.baseUrl = "https://www.xm.com/";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--enable-geolocation", "--geolocation=40.730610,-73.935242");
        Configuration.browserCapabilities = chromeOptions;

        Configuration.timeout = 10000;
    }

    @Test(dataProvider = "resolutionProvider")
    public void test(String resolution) {
        Configuration.browserSize = resolution;

        MainPage mainPage = new MainPage();
        EconomicCalendar economicCalendar = new EconomicCalendar();
        EducationalVideosPage educationalVideosPage = new EducationalVideosPage();

        mainPage.open();
        mainPage.acceptModal();
        mainPage.navigation(ECONOMIC_CALENDAR);
        economicCalendar.switchToFrame();
        economicCalendar.increaseSlider();
        economicCalendar.checkDate(DateUtils.currentDate());
        economicCalendar.increaseSlider();
        economicCalendar.checkDate(DateUtils.nextDate());
        economicCalendar.increaseSlider();
        economicCalendar.increaseSlider();
        economicCalendar.checkDate(DateUtils.nextMonday());
        switchTo().defaultContent();
        mainPage.navigation(EDUCATIONAL_VIDEOS);
        educationalVideosPage.openLesson(LESSON);
        educationalVideosPage.switchToFrame();
        educationalVideosPage.playVideo();
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @DataProvider(name = "resolutionProvider")
    public Object[] provideData() {
        return new Object[]{
                BROWSER_SIZE_800x600,
                BROWSER_SIZE_1024x768,
                BROWSER_SIZE_1920x1080
        };
    }
}
