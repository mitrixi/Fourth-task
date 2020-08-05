
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * startSite() - start main page of site ITVDN
 * findInHomeSearchAndSelectJavaDeveloper() - entering "Java" into search box and selecting second item in the dropdown list
 * closeNotification() - hide notification in top of page
 * registrationUserAccount() - press button "Начать обучение" and open page with registration form
 * openPageWithCatalogCourses() - open page with catalogues with different courses
 * openCertainCourse() - opening course "Java starter" from all presented courses
 * playVideo() start playing video from course page
 * openStudyingWithTrainer() - opening page studying with trainer
 * checkEmptyForm() - checking form not pass without entry
 */

public class TestNGFourTaskTest {
    private static WebDriver driver;

    @BeforeSuite
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterSuite
    public static void termination() {
        driver.close();
    }

    @BeforeMethod
    public void openMainPage() {
        driver.get("https://itvdn.com/ru");
    }


    @Test(groups = "smoke", timeOut = 3000)
    public void startSite() {
        assertThat(driver.getTitle(), equalTo("IT Курсы программирования онлайн - обучение программированию, видео уроки | ITVDN"));
    }


    @Test
    public void findInHomeSearchAndSelectJavaDeveloper() {
        WebElement searchBox = driver.findElement(By.cssSelector("input#home-search"));
        searchBox.sendKeys("Java");
        WebElement searchResult = driver.findElement(By.cssSelector("a[data-index='1']"));
        searchResult.click();

        assertThat(driver.getTitle(), equalTo("Специальность Java developer, обязанности Java разработчиков"));
    }

    @Test(enabled = false)
    public void closeNotification() {
        WebElement closeNotification = driver.findElement(By.cssSelector("div .notification-block-x span"));
        closeNotification.click();
    }

    @Test
    public void registrationUserAccount() {
        WebElement btnFilledOrange = driver.findElement(By.cssSelector(".top-header .subscription-block a[href='/ru/account/register?jumpStart=true&returnUrl=/ru'"));
        btnFilledOrange.click();

        assertThat(driver.getTitle(), equalTo("Регистрация учетной записи"));
    }

    @Test
    public void openPageWithCatalogCourses() {
        WebElement itvdniconVideoCatalogMenuItemIcon = driver.findElement(By.cssSelector(".itvdnicon-video-catalog.menu-item-icon"));
        itvdniconVideoCatalogMenuItemIcon.click();

        assertThat(driver.getTitle(), equalTo("Курсы программирования: каталог курсов по сложности, стоимости и авторству"));
    }

    @Test
    public void openCertainCourse() {
        WebElement itvdniconVideoCatalogMenuItemIcon = driver.findElement(By.cssSelector(".itvdnicon-video-catalog.menu-item-icon"));
        itvdniconVideoCatalogMenuItemIcon.click();

        WebElement courseJavaStarter = driver.findElement(By.cssSelector("a[href='/ru/video/java-starter'] div"));
        courseJavaStarter.click();

        assertThat(driver.getTitle(), equalTo("Видео курс Java Starter, уроки Java для начинающих - онлайн обучение ITVDN"));
    }

    @Test(dependsOnMethods = "openCertainCourse")
    public void playVideo() {
        WebElement playVideo = driver.findElement(By.cssSelector("iframe[src='https://player.vimeo.com/video/171111471?app_id=122963']"));
        playVideo.click();
    }

    @Test
    public void openStudyingWithTrainer() {
        WebElement buttonStudyingWithTrainer = driver.findElement(By.cssSelector("li.desktop.menu-liveonline-item a div span.menu-icon-EduWithTrainer.menu-item-icon"));
        buttonStudyingWithTrainer.click();

        WebElement buttonJavaDeveloper = driver.findElement(By.xpath("//div[@class='dropdown-block lo-specialities']//span[text()='Java Developer']"));
        buttonJavaDeveloper.click();

        assertThat(driver.getTitle(), equalTo("Java Developer - Online обучение на ITVDN"));
    }

    @DataProvider(name = "dataProvider")
    public Object[] dataProviderMethod() {
        return new String[]{"Alex", "Bob", "Martin"};
    }

    @Test(dataProvider = "dataProvider")
    public void checkEmptyForm(String data) {

        WebElement buttonStudyingWithTrainer = driver.findElement(By.cssSelector("li.desktop.menu-liveonline-item a div span.menu-icon-EduWithTrainer.menu-item-icon"));
        buttonStudyingWithTrainer.click();

        WebElement buttonJavaDeveloper = driver.findElement(By.xpath("//div[@class='dropdown-block lo-specialities']//span[text()='Java Developer']"));
        buttonJavaDeveloper.click();

        WebElement buttonSignUp = driver.findElement(By.cssSelector("button.face__button.btn-filled-orange.consultation-open-btn.consult-btn-js"));//.face__button btn-filled-orange consultation-open-btn consult-btn-js
        buttonSignUp.click();

        WebElement inputName = driver.findElement(By.cssSelector("form#consultation-form input[placeholder='Фамилия*']"));
        inputName.sendKeys(data);

        WebElement buttonSendRequest = driver.findElement(By.cssSelector("#consultation-form > div.submit-cont > button"));        //#consultation-form > div:nth-of-type(3) > button
        buttonSendRequest.click();

        assertThat(driver.findElement(By.cssSelector("#Name-error")).getText(), equalTo("Пожалуйста, укажите Ваше имя"));
    }
}
