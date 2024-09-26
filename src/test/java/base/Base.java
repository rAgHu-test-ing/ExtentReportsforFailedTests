package base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Base {
	
	public String takeScreenshot(String testName, WebDriver driver){
		
		File sourceScreenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destinationScreenshotFile = new File(System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png");
		
		try {
			FileUtils.copyFile(sourceScreenshotFile,destinationScreenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return destinationScreenshotFile.getAbsolutePath();
	}

}
