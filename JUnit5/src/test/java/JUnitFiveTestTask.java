import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.Extension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * startSite() -                                start main page of site ITVDN
 * findInHomeSearchAndSelectJavaDeveloper() -   entering "Java" into search box and selecting second item in the dropdown list
 * closeNotification() -                        hide notification in top of page
 * registrationUserAccount() -                  press button "Начать обучение" and open page with registration form
 * openPageWithCatalogCourses() -               open page with catalogues with different courses
 * openCertainCourse() -                        opening course "Java starter" from all presented courses
 * playVideo() -                                start playing video from course page
 * openStudyingWithTrainer() -                  opening page studying with trainer
 * checkEmptyForm() -                           checking form not pass without entry
 */

public class JUnitFiveTestTask implements Extension {
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void termination() {
        driver.close();
    }

    @BeforeEach
    public void openMainPage() {
        driver.get("https://itvdn.com/ru");
    }

    @Tag("smoke")
    @Timeout(value = 3)
    @Test
    @DisplayName("Start site")
    public void startSite() {
        assertEquals(driver.getTitle(), "IT Курсы программирования онлайн - обучение программированию, видео уроки | ITVDN");
    }

    @Tag("smoke")
    @Test
    @DisplayName("Find and select item from proposed ones")
    public void findInHomeSearchAndSelectJavaDeveloper() {
        WebElement searchBox = driver.findElement(By.cssSelector("input#home-search"));
        searchBox.sendKeys("Java");
        WebElement searchResult = driver.findElement(By.cssSelector("a[data-index='1']"));
        searchResult.click();

        assertEquals(driver.getTitle(), "Специальность Java developer, обязанности Java разработчиков");
    }

    @Disabled
    @Test
    @DisplayName("Close notification")
    public void closeNotification() {
        WebElement closeNotification = driver.findElement(By.cssSelector("div .notification-block-x span"));
        closeNotification.click();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Registration account")
    public void registrationUserAccount() {
        WebElement btnFilledOrange = driver.findElement(By.cssSelector(".top-header .subscription-block a[href='/ru/account/register?jumpStart=true&returnUrl=/ru'"));
        btnFilledOrange.click();

        assertEquals(driver.getTitle(), "Регистрация учетной записи");
    }

    @Nested
    public class nestedClass {

        @Tag("smoke")
        @Test
        @DisplayName("Open page with suite courses")
        public void openPageWithCatalogCourses() {
            WebElement itvdniconVideoCatalogMenuItemIcon = driver.findElement(By.cssSelector(".itvdnicon-video-catalog.menu-item-icon"));
            itvdniconVideoCatalogMenuItemIcon.click();

            assertEquals(driver.getTitle(), "Курсы программирования: каталог курсов по сложности, стоимости и авторству");
        }

        @Tag("smoke")
        @Test
        @DisplayName("Select certain course")
        public void openCertainCourse() {
            WebElement itvdniconVideoCatalogMenuItemIcon = driver.findElement(By.cssSelector(".itvdnicon-video-catalog.menu-item-icon"));
            itvdniconVideoCatalogMenuItemIcon.click();

            WebElement courseJavaStarter = driver.findElement(By.cssSelector("a[href='/ru/video/java-starter'] div"));
            courseJavaStarter.click();

            assertEquals(driver.getTitle(), "Видео курс Java Starter, уроки Java для начинающих - онлайн обучение ITVDN");
        }
    }

    @Test
    @DisplayName("Play video")
    public void playVideo() {
        WebElement itvdniconVideoCatalogMenuItemIcon = driver.findElement(By.cssSelector(".itvdnicon-video-catalog.menu-item-icon"));
        itvdniconVideoCatalogMenuItemIcon.click();

        WebElement courseJavaStarter = driver.findElement(By.cssSelector("a[href='/ru/video/java-starter'] div"));
        courseJavaStarter.click();

        WebElement playVideo = driver.findElement(By.cssSelector("iframe[src='https://player.vimeo.com/video/171111471?app_id=122963']"));
        playVideo.click();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Open page \"Обучение с тренером\"")
    public void openStudyingWithTrainer() {
        WebElement buttonStudyingWithTrainer = driver.findElement(By.cssSelector("li.desktop.menu-liveonline-item a div span.menu-icon-EduWithTrainer.menu-item-icon"));
        buttonStudyingWithTrainer.click();

        WebElement buttonJavaDeveloper = driver.findElement(By.xpath("//div[@class='dropdown-block lo-specialities']//span[text()='Java Developer']"));
        buttonJavaDeveloper.click();

        assertEquals(driver.getTitle(), "Java Developer - Online обучение на ITVDN");
    }
    
    @Tag("smoke")
    @Test
    @DisplayName("Check empty form")
    public void checkEmptyForm() {
        WebElement buttonStudyingWithTrainer = driver.findElement(By.cssSelector("li.desktop.menu-liveonline-item a div span.menu-icon-EduWithTrainer.menu-item-icon"));        //html > body > div:nth-of-type(1) > div:nth-of-type(2) > div:nth-of-type(1) > ul > li:nth-of-type(3) > a
        buttonStudyingWithTrainer.click();

        WebElement buttonJavaDeveloper = driver.findElement(By.xpath("//div[@class='dropdown-block lo-specialities']//span[text()='Java Developer']"));        //html > body > div:nth-of-type(1) > div:nth-of-type(2) > div:nth-of-type(1) > ul > li:nth-of-type(3) > div > div:nth-of-type(3) > ul > li:nth-of-type(5) > a > div > span:nth-of-type(2)
        buttonJavaDeveloper.click();

        WebElement buttonSignUp = driver.findElement(By.cssSelector("button.face__button.btn-filled-orange.consultation-open-btn.consult-btn-js"));//.face__button btn-filled-orange consultation-open-btn consult-btn-js
        buttonSignUp.click();

        WebElement buttonSendRequest = driver.findElement(By.cssSelector("#consultation-form > div.submit-cont > button"));        //#consultation-form > div:nth-of-type(3) > button
        buttonSendRequest.click();

        assertEquals(driver.findElement(By.cssSelector("#Name-error")).getText(), "Пожалуйста, укажите Ваше имя");
    }
}
