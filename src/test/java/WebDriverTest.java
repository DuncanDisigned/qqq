import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class WebDriverTest {

    private final static Logger logger = (Logger) LogManager.getLogger(WebDriverTest.class);
    private static WebDriver driver;

    @BeforeAll
    public static void driverSetup() {
        logger.trace("Скачивание вебдрайвера - начато");
        WebDriverManager.chromedriver().setup();
        logger.trace("Скачивание вебдрайвера - завершено");
    }

    @BeforeEach
    public void setUp() {
        logger.trace("Открытие браузера - начато");
        ChromeOptions headlessOptions = new ChromeOptions();
        headlessOptions.addArguments("headless");
        driver = new ChromeDriver(headlessOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Установить неявное ожидание на 5 секунд

    }


    @Test
    public void firstTest() {
        logger.trace("Открытие сайта - начато");
        driver.get("https://duckduckgo.com/");
        logger.trace("Открытие сайта - завершено");

        logger.trace("Первичный клик");
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='searchbox_input']"));
        searchBox.click();
        logger.trace("вторичный клик");
        searchBox.click();
        logger.trace("Ввод в поисковую строку");
        searchBox.sendKeys("ОТУС");
        searchBox.submit();
        logger.trace("Текст введён");
        logger.trace("Поиск...");
        // driver.findElement(By.xpath("//*[@id=\"search_button\"]"));
        logger.trace("Сравнение надписи ОТУС ");
        WebElement actualTitle = driver.findElement(By.xpath("//span[contains(text(), 'Онлайн‑курсы для профессионалов, дистанционное обучение современным ...')]"));
        String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным ...";
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Заголовок страницы соответствует ожидаемому");
        } else {
            System.out.println("Заголовок страницы не соответствует ожидаемому");
        }
    }


    @AfterAll
    public static void setDown() {
        logger.trace("Закрытие браузера - начато");
        if (driver != null) {
            driver.quit();
        }
        logger.trace("Закрытие браузера - завершено");
    }

  /*  Открыть Chrome в режиме киоска
    Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
    Нажать на любую картинку
    Проверить что картинка открылась в модальном окне
*/

    @Test
    public void testKiosk() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");

        driver = new ChromeDriver(options);

        logger.trace("Открытие браузера в режиме киоска");
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");

        WebElement image = driver.findElement(By.xpath("//div[@class='content-overlay'][1]"));
        image.click();
        logger.trace("Клик по картинке");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"pp_next\"]")));

        WebElement modalImageElement = driver.findElement(By.xpath("//a[@class=\"pp_next\"]"));
        logger.trace("Проверка,что картинка открылась в модальном окне");
        if (modalImageElement.isDisplayed()) {
            logger.trace("Картинка успешно открылась в модальном окне!");
        } else {
            logger.error("Что-то пошло не так. Картинка не отображается в модальном окне.");
        }

        driver.quit();
    }


   /* Открыть Chrome в режиме полного экрана
    Перейти на https://otus.ru
    Авторизоваться под каким-нибудь тестовым пользователем(можно создать нового)
    Вывести в лог все cookie

    */
   @Test
   public void testFullScreenChrome() {
       ChromeOptions options = new ChromeOptions();
       options.addArguments("--start-fullscreen");

       driver = new ChromeDriver(options);
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
       logger.trace("Открытие Chrome в режиме полного экрана");

       driver.get("https://otus.ru");
       logger.trace("Вход на сайт");

       // Клик на кнопку "Зарегистрироваться"
       WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__next']/div[1]/div[2]/div/section/button")));
       loginButton.click();
       logger.trace("Клик на кнопку Зарегистрироваться");
       // WebElement galka = driver.findElement(By.xpath("(//input[@type=\"radio\"])[1]"));
        //galka.submit();
       // Ввод логина
       driver.findElement(By.xpath("(//div[@class=\"sc-1ij08sq-0 fQxsKJ sc-rq8xzv-2 xkNdd\"])[2]")).click();
       WebElement usernameForm = driver.findElement(By.xpath("//input[@class=\"sc-rq8xzv-4 bFztFW\"]"));
       usernameForm.sendKeys("admin");
       logger.trace("Логин введен");
       // Ввод email
       driver.findElement(By.xpath("(//div[@class=\"sc-1ij08sq-0 fQxsKJ sc-rq8xzv-2 xkNdd\"])[3]")).click();
       WebElement emailForm = driver.findElement(By.xpath("//input[@class=\"sc-rq8xzv-4 bFztFW\"]"));
       emailForm.sendKeys("testEmail@gmail.ru");
       logger.trace("Email введен");

       // Клик по чекбоксу
       WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sc-szxmcj-2 chkvXv']")));
       checkbox.click();
       logger.trace("Клик по галочке");

       // Нажатие кнопки "Зарегистрироваться"
      // driver.findElement(By.xpath(String.valueOf(By.xpath("//button[contains(., 'Зарегистрироваться')]"))));
       driver.findElement(By.xpath("//button[@class=\"sc-9a4spb-0 eQlGvH\"]")).click();
       logger.trace("Подтверждение регистрации");

       // Проверка успешной регистрации - добавьте соответствующую проверку здесь

       // Вывод всех cookie в лог
       logger.trace("Вывод всех cookie в лог:");
       driver.manage().getCookies().forEach(cookie -> logger.trace(cookie.toString()));
   }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
