package demo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SeleniumWrapper {
     private WebDriver driver;
    public WebDriverWait wait; 

    public  SeleniumWrapper(WebDriver driver, Duration timeout){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

   

    public void scrollToElement(WebElement element) {
        try {
            
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            
            System.err.println("Error while scrolling to element: " + e.getMessage());
        }
    }
    
    public void clickUntilNotVisible(WebElement element) {
       
        while (element.isDisplayed()) {
            try {
                element.click();
                // Wait for a short time to allow changes to take effect
                Thread.sleep(500); // Adjust the sleep time as needed
            } catch (StaleElementReferenceException e) {
                // Re-locate the element if it becomes stale
                element = driver.findElement(By.id(element.getAttribute("id")));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

