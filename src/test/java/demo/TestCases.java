package demo;

import java.time.Duration;

// import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethods.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
// import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
// import java.util.*; 
import java.util.List;

import com.beust.ah.A;
import com.google.common.base.Equivalence.Wrapper;

// import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
     WebDriver driver;
    SeleniumWrapper wrapper;

@BeforeSuite(alwaysRun = true)
public void setup() {
    System.out.println("Constructor: Driver");
     WebDriverManager.chromedriver().timeout(30).setup();
    driver = new ChromeDriver();
    
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
    driver.manage().window().maximize();
    wrapper = new SeleniumWrapper(driver, Duration.ofSeconds(50));
    System.out.println("Sucessfully Created driver");
    
}
@Test
public void TestCases1(){
    System.out.println("Start test case: Testcase1");
    String url = "https://www.youtube.com/";

            driver.get(url);
            String currentURL = driver.getCurrentUrl();
            Assert.assertTrue(currentURL.contains("youtube"),"URL does not contain the expected substring!");
            WebElement scrollAbout = wrapper.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Test new features']")));
            wrapper.scrollToElement(scrollAbout);
            WebElement abouElement = wrapper.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About']")));
            abouElement.click();
            ((JavascriptExecutor) driver).executeScript("scroll(0, 500);");
            // WebElement message = wrapper.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Test new features']")));
            // wrapper.scrollToElement(message);
            WebElement aboutPrint = wrapper.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//main[@id='content']")));
           String messageString = aboutPrint.getText();
            System.out.println(messageString);
            System.out.println("End test case: Testcase1");
    }   
    @Test
    public void TestCases2(){
        System.out.println("Start test case: Testcase2");
        String url = "https://www.youtube.com/";
        driver.get(url);
        WebElement moviesElement = wrapper.wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Movies']")));
        moviesElement.click();
        WebElement actualTitle = wrapper.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Top selling']")));
        String expectedTitle = "Top selling";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualTitle, expectedTitle, "Title mismatch");
        // softAssert.assertAll();
        System.out.println("Title is" + actualTitle.getText());

        WebElement arrowClickElement = wrapper.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@aria-label='Next']")));
        wrapper.clickUntilNotVisible(arrowClickElement);
        List<WebElement> maturElement = driver.findElements(By.xpath("//div[@class='badge badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer']"));
        for(WebElement maturContent : maturElement){
            String mature = maturContent.getText();
            boolean isMature = mature.contains("A");
            softAssert.assertTrue(isMature,"Movie does not marked A for Mature content");
           System.out.println(mature);
            }
        List<WebElement> film = driver.findElements(By.xpath("//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
        for(WebElement films : film){
            String filmText = films.getText();
            boolean filmChategory = filmText.contains("Comedy") || filmText.contains("Animation");

            softAssert.assertTrue(filmChategory,"Movie genre is neither 'Comedy' nor 'Animation");
            System.out.println(filmText);
        }
        System.out.println("End test case: Testcase2");
    }
       @Test
        public void Testcase3(){
            System.out.println("Start test case: Testcase3");
            String url = "https://www.youtube.com/";
            driver.get(url);
            WebElement musicElement = wrapper.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Music']")));
            musicElement.click();

            WebElement scrollElement = driver.findElement(By.xpath("(//a[@class='yt-simple-endpoint style-scope ytd-shelf-renderer'])[3]"));

          wrapper.scrollToElement(scrollElement);

          WebElement playlistName = driver.findElement(By.xpath("(//h3[@class='style-scope ytd-compact-station-renderer'])[11]"));
          System.out.println("playlist name" + playlistName.getText());

          WebElement arrowClick = wrapper.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@aria-label='Next'])[1]")));
          wrapper.clickUntilNotVisible(arrowClick);

          WebElement trackElement = driver.findElement(By.xpath("(//p[@id='video-count-text'])[11]"));
          String track = trackElement.getText();
          System.out.println(track);
          String trackNumberOnly = track.replaceAll("[^0-9]", "");
          int numberOfTracks = Integer.parseInt(trackNumberOnly);
          SoftAssert softAssert = new SoftAssert();
          softAssert.assertTrue(numberOfTracks <=50, "Number of tracks is not less than or equal to 50.");
          System.out.println("End test case: Testcase3");
        }

        @Test
        public void Testcase4(){
            System.out.println("Start test case: Testcase4");
            String url = "https://www.youtube.com/";
            driver.get(url);
            WebElement news = driver.findElement(By.xpath("//a[@title='News']"));
            wrapper.scrollToElement(news);
            news.click();
            WebElement ScrolltoLatestnews = driver.findElement(By.xpath("//span[text()='Live now: News']"));
            wrapper.scrollToElement(ScrolltoLatestnews);

            List <WebElement> latestNews = driver.findElements(By.xpath("//yt-formatted-string[@id='home-content-text']"));
            List <WebElement> likes = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));
            for (int i = 0; i <= 3 && i < latestNews.size(); i++) {
                String newsText = latestNews.get(i).getText();
                String likesCount = "0";
                if (i < likes.size() && likes.get(i) != null) {
                    likesCount = likes.get(i).getText();
                }
                System.out.println(newsText + " Likes:" + likesCount);
            }
            System.out.println("End test case: Testcase4");
        }

        }
        
            
        
    



