package teqtestCases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Arrays;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import teqPages.*;
import teqUtil.*;
import org.openqa.selenium.support.ui.Select;
public class TeqBase {
	
	public static WebDriver driver;
	public static Properties prop;
	 public NewRequestPage newrequest;
	 public static String browsername;
	 public static String Adress;
	 public static Logger logger;
	 
	 @Parameters("browser") 
	 @BeforeTest
		public static void GetBrowserName(String br) {
			browsername=br;
		}
	 @BeforeClass
	public static void initialization() {
		FileInputStream ip = null;
		final String ProjectPath= System.getProperty("user.dir");
		String os= System.getProperty("os.name").toLowerCase();
		//System.out.println(ProjectPath);
		//System.out.println(os);
		
		try {
			prop = new Properties();
			if(os.contains("windows")) {
			  ip = new FileInputStream(ProjectPath+TeqUtil.ConfigFileLocationforWindows);
			}
			else if(os.contains("mac")) {
			  ip = new FileInputStream(ProjectPath+TeqUtil.ConfigFileLocationforMac);
			}
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		//String browsername= prop.getProperty("browser");
		
		if(browsername.equals("chrome") & os.contains("windows")) {
		System.setProperty("webdriver.chrome.driver",ProjectPath+TeqUtil.ChromeDriverForWindowsLocation);
		driver= new ChromeDriver();
		}
		else if(browsername.equals("chrome") & os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver",ProjectPath+TeqUtil.ChromeDriverForMacLocation);
			driver= new ChromeDriver();
			}
		else if(browsername.equals("firefox") & os.contains("windows")) {
			System.setProperty("webdriver.gecko.driver",ProjectPath+TeqUtil.FirefoxDriverForWindowsLocation);
			driver= new FirefoxDriver();
			}
		else if(browsername.equals("firefox") & os.contains("mac")) {
			System.setProperty("webdriver.gecko.driver",ProjectPath+TeqUtil.FirefoxDriverForMacLocation);
			driver= new FirefoxDriver();
			}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TeqUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TeqUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		Adress=prop.getProperty("url");
		driver.get(Adress);
		
		
		logger =Logger.getLogger("teqTestAutomation");
		PropertyConfigurator.configure("log4j.properties");
		}

	
	 public void wait30sec() throws Exception {
	       // new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(element));

	        //wait.until(ExpectedConditions.presenceOfElementLocated(element));

	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	    }
	 
	 public void entervalue(WebDriver driver, By element, String value) throws Exception {

	       driver.findElement(element).sendKeys(new String[] { value });


//	        List<MobileElement> continueBtn = driver.findElements(element);
//	        for (MobileElement btn : continueBtn) {
	//
//	            Thread.sleep(5000);
//	            btn.sendKeys(new String[] { value });
//	            //btn.sendKeys(value);
	//
//	        }

	    }
	 
	 public static void verifyElementExists(WebDriver driver, By element) throws Exception
	    {   
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        boolean elementResult=driver.findElement(element).isDisplayed();


	        if(elementResult==true)
	        {
	            System.out.println("The ID: " +element+ "element is visible");
	        }
	        else
	        {
	            System.out.println("The ID: " +element+ "element is not visible");
	        }



	    }
	 public static void clickElement (By element) throws  Exception{


//       List<MobileElement> continueBtn = driver.findElements(element);
//       for (MobileElement btn : continueBtn) {
//
//           btn.click();
//
//
//       }
		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait webDriverWait = new WebDriverWait(driver,60);
		 webDriverWait.until(ExpectedConditions.elementToBeClickable (element));


       driver.findElement(element).click();
       Thread.sleep(10000);

   }

 public static void verifyElementAndDataEntry(WebDriver driver2, By element,String value) throws Exception {
	 
	 boolean elementResult=driver2.findElement(element).isDisplayed();
	 clickElement(element);
	 driver2.findElement(element).clear();
     if(elementResult==true)
     {
         System.out.println("The ID: " +element+ "element is visible");
     }
     else
     {
         System.out.println("The ID: " +element+ "element is not visible");
     }
     driver2.findElement(element).sendKeys(new String[] { value });

	 
 }
 
 public void SelectionFromDropdown(WebDriver driver, By element1,By element2,String value)
 {
	 driver.findElement(element1).click();
	 List<WebElement> list=driver.findElements(element2);
	 for (int i=0; i<list.size(); i++)
	 {
		 if(list.get(i).getText().contains(value))
		 {
			 list.get(i).click();
			 break;
			 
		 }
	 }
	 
 }
	
 public void EnterPastOrFutureDateTime(WebDriver driver, By DateField,By MonthValField, By btn,String DateValue,String MonthValue, String TimeValue) throws InterruptedException {
	 
	 driver.findElement(DateField).click();
	 Thread.sleep(2000);
	  while(true) {
		  
	 String text=driver.findElement(MonthValField).getText();
	 if (text.equals(MonthValue))
	 {
		 break;
	 
	 }
	 else
	 {
		 driver.findElement(btn).click();
	 }
	 
	  }
	  driver.findElement(By.xpath("/html/body/div[11]/div[1]/div[2]/table/tbody/tr/td/div[contains(text(),"+DateValue+")]")).click();
	 driver.findElement(By.xpath("/html/body/div[11]/div[2]/div/div[1]/div[contains(text(),"+TimeValue+")]")).click();
 }
 
 
		
 
 public void waitForElementVisibility(By by) {
	 WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
	webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
	
	}
 
 public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		File target = new File(System.getProperty("user.dir") + "//Screenshots//" + tname + timeStamp+ ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	



//	@AfterTest
    public void closeTheApp( )  {
        driver.close();
    }	
	
//
}

