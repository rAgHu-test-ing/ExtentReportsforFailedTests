package tests;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {
	
	public WebDriver driver;
	
	@AfterMethod
	public void tearDown() {
		
		driver.quit();
		
	}
	
	@BeforeMethod
	public void setUp() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://tutorialsninja.com/demo/");
		
	}
	
	@Test(priority=1)
	public void loginWithValidCredentials() {
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("input-email")).sendKeys("abc@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information abc")).isDisplayed()); //failing test intentionally		
	}
	
	@Test(priority=2)
	public void loginWithInvalidCredentials() {
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("input-email")).sendKeys("amotooricap"+generateTimeStamp()+"@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("1234567890");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText().contains("Warning: No match for E-Mail Address and/or Password."));
		
	}
	
	@Test(priority=3,dependsOnMethods={"loginWithValidCredentials"})
	public void loginWithoutCredentials() {
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("input-email")).sendKeys("");
		driver.findElement(By.id("input-password")).sendKeys("");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText().contains("Warning: No match for E-Mail Address and/or Password."));
		
	}
	
	public String generateTimeStamp(){
		
		Date date = new Date();
		return date.toString().replace(" ","_").replace(":","_");
		
	}
}
