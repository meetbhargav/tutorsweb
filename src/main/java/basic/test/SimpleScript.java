package basic.test;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SimpleScript {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		String url = "http://webdriveruniversity.com/index.html";
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		driver.get(url);

		// WebDriver Waits - Implicit Wait 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		// WebDriver Wait - Explicit wait 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//Action Class object for performing mouse actions
		Actions act = new Actions(driver);

		WebElement contactUsPage = driver.findElement(By.id("contact-us"));
		System.out.println(contactUsPage.getText());
		contactUsPage.click();
		
		// Switching between the web pages 

		// getWindowHandle - store main window opened using browser
		String MainWindow = driver.getWindowHandle();
		
		// getWindowHandles - stores the ids of all the windows opened in the browser
		Set<String> windows1 = driver.getWindowHandles();

		Iterator<String> i1 = windows1.iterator();

		while (i1.hasNext()) {

			String child_window = i1.next();

			if (!MainWindow.equals(child_window)) {
				driver.switchTo().window(child_window);

				driver.findElement(By.name("first_name")).sendKeys("Bhargav");

				driver.close();
			}
		}
		
		// switchTo method is used to shift the control from child to main window
		driver.switchTo().window(MainWindow);
		WebElement loginPortal = driver.findElement(By.id("login-portal"));
		System.out.println(loginPortal.getText());
		loginPortal.click();
		
		Set<String> windows2 = driver.getWindowHandles();
		
		Iterator<String> i2 = windows2.iterator();
		
		while(i2.hasNext()) {
			String child_window = i2.next();
			
			if(!MainWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
				
				driver.findElement(By.id("login-button")).click();
				
				System.out.println(driver.switchTo().alert().getText());
				
				driver.switchTo().alert().accept();
				
				driver.close();
			}
		}
		
		driver.switchTo().window(MainWindow);
		WebElement buttonClicks = driver.findElement(By.id("button-clicks"));
		System.out.println(buttonClicks.getText());
		buttonClicks.click();
		
		Set<String> windows3 = driver.getWindowHandles();
		Iterator<String> i3 = windows3.iterator();
		
		while(i3.hasNext()) {
			String child_window = i3.next();
			
			if(!MainWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
				
				driver.findElement(By.id("button1")).click();
				
				driver.findElement(By.xpath("(//div[@class=\"modal-content\"])[1]//button[text()=\"Close\"]")).click();
				
				WebElement javaScriptButton = driver.findElement(By.cssSelector("span#button2"));
				
				// JavaScript Executor - Implementation for button click
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", javaScriptButton);
				
				driver.findElement(By.xpath("(//div[@class=\"modal-content\"])[2]//button[text()=\"Close\"]")).click();

				//Implementation of the Explicit Wait
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
					    By.xpath("(//div[@class='modal-content'])[2]//button[text()=\"Close\"]")
					));
				
				WebElement actionButton = driver.findElement(By.id("button3"));
				
			//	wait.until(ExpectedConditions.elementToBeClickable(actionButton));
				
			//	Actions act = new Actions(driver);

			//	act.moveToElement(actionButton).pause(Duration.ofMillis(200)).build().perform();
				
				js.executeScript("arguments[0].scrollIntoView(true);", actionButton);
				
				wait.until(ExpectedConditions.elementToBeClickable(actionButton));

				js.executeScript("arguments[0].click();", actionButton);
				
				driver.findElement(By.xpath("(//div[@class=\"modal-content\"])[3]//button[text()=\"Close\"]")).click();
				
				System.out.println("All buttons closed");
				
				driver.close();
				
			}
		}
		
		driver.switchTo().window(MainWindow);
		WebElement toDoList = driver.findElement(By.id("to-do-list"));
		toDoList.click();
		
		Set<String> windows4 = driver.getWindowHandles();
		Iterator<String> i4 = windows4.iterator();
		
		while(i4.hasNext()) {
			String child_window = i4.next();
			
			if(!MainWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
				
				
				WebElement x = driver.findElement(By.cssSelector("input[placeholder=\"Add new todo\"]"));
				
				// Implementation of the Action Class
				act.moveToElement(x).click()
				.sendKeys("Play Games")
				.sendKeys(Keys.ENTER)
				.build()
				.perform();
				
				WebElement firstTask = driver.findElement(By.xpath("(//div[@id=\"container\"]//ul//li)[1]"));
				System.out.println(firstTask.getText());
				act.moveToElement(firstTask)
				.moveToElement(driver.findElement(By.xpath("(//i[@class=\"fa fa-trash\"])[1]")))
				.click()
				.build()
				.perform();
			}
		}
		driver.quit();
	}

}
