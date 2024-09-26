package listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.Base;
import utilities.ExtentReportGenerator;

public class MyListeners extends Base implements ITestListener{
	
	ExtentReports report = ExtentReportGenerator.getExtentReport();
	ExtentTest eTest;

	public void onTestStart(ITestResult result) {
		
		String testName = result.getName();
		eTest = report.createTest(testName);
		eTest.log(Status.INFO,testName+" execution started");
		
	}

	public void onTestSuccess(ITestResult result) {
		
		String testName = result.getName();
		eTest.log(Status.PASS,testName+" got successfully executed");
		
	}

	public void onTestFailure(ITestResult result) {
		
		String testName = result.getName();
		eTest.log(Status.FAIL,testName+" got failed");
		
		WebDriver driver = null;
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eTest.addScreenCaptureFromPath(takeScreenshot(testName,driver),testName);
		//eTest.addScreenCaptureFromBase64String(takeScreenshot(testName, driver));
		eTest.log(Status.INFO,result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {
		
		String testName = result.getName();
		eTest.log(Status.SKIP,testName+" got skipped");
		eTest.log(Status.INFO,result.getThrowable());
		
	}

	public void onFinish(ITestContext context) {
		
		report.flush();
		
		File eReportFile = new File(System.getProperty("user.dir")+"\\ExtentReports\\eReport.html");
		
		try {
			Desktop.getDesktop().browse(eReportFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
