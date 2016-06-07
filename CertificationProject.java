package Project;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CertificationProject {

	
	
		WebDriver oBrowser;
		
		String sUrl = "http://demo.opensourcecms.com/wordpress/wp-login.php";
		
		
		@Parameters ({"browser"})
		@BeforeMethod
		public void setup(String sBrowserType){
			
			if(sBrowserType.equalsIgnoreCase("ff")){
				oBrowser = new FirefoxDriver();
			} else if(sBrowserType.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", "/Users/navyananenkala/Desktop/Selenium/Workspace/chromedriver");
				oBrowser = new ChromeDriver();
			} else if(sBrowserType.equalsIgnoreCase("safari")){
				oBrowser = new SafariDriver();
			}else {
				System.err.println("Invalid Browser type.. setting default browser as Firefox Driver");
				oBrowser = new FirefoxDriver();
			}
			
			Dimension d = new Dimension(1024, 720);
			// To set size of window
			oBrowser.manage().window().setSize(d);
			
			// To Maximize the window
		   oBrowser.manage().window().maximize();
			
			oBrowser.manage().deleteAllCookies();
			
			oBrowser.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			
			oBrowser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			oBrowser.get(sUrl);
			
			String sTitle;
			
			sTitle = oBrowser.getTitle();
			
			System.out.println("Title of the page is : "+ sTitle);
			
			Assert.assertEquals(oBrowser.getTitle(),"WordPress Demo Install › Log In");
			
			String sCurrentUrl = oBrowser.getCurrentUrl();
			
			System.out.println("Current Url is : "+ sCurrentUrl);
			
		}
		
		
		
		@Test
		public void login(){
			try {
			
			//Login Verify
			
			oBrowser.findElement(By.name("log")).sendKeys("admin");
			
			Assert.assertEquals(oBrowser.getPageSource().contains("log"), true);
			//Change Password below to test
			oBrowser.findElement(By.id("user_pass")).sendKeys("*******");
			
			Assert.assertEquals(oBrowser.getPageSource().contains("user_pass"), true);
				
			oBrowser.findElement(By.xpath("//input[@id='wp-submit']")).click();
			
			getScreenShot();
			
			oBrowser.navigate().back();
			
			//Lost Password
			
			oBrowser.findElement(By.linkText("Lost your password?")).click();
				
			oBrowser.findElement(By.name("user_login"));
				
			Assert.assertEquals(oBrowser.getPageSource().contains("user_login"), true);
				
			oBrowser.findElement(By.name("wp-submit")).isEnabled();
				
			oBrowser.findElement(By.linkText("Log in")).click();
				
			WebElement sUsername = oBrowser.findElement(By.name("log"));
			
			sUsername.clear();
			
			sUsername.sendKeys("admin");
			
			//Change Password
			oBrowser.findElement(By.id("user_pass")).sendKeys("********");
				
			oBrowser.findElement(By.xpath("//input[@id='wp-submit']")).click();
			
			//Adding Post
			
			//Click on Posts
			oBrowser.findElement(By.linkText("Posts")).click();
			
			//Click for Add new post
			oBrowser.findElement(By.xpath("//*[@id='wpbody-content']/div[4]/h2/a")).click();
			
			WebElement title = oBrowser.findElement(By.id("title"));
			
			title.click();
			
			title.sendKeys("My First Post");
			
			oBrowser.switchTo().frame("content_ifr");
			
			WebElement postBody = oBrowser.findElement(By.id("tinymce"));
			
			postBody.sendKeys("This new post is for testing");
				
			oBrowser.switchTo().defaultContent();
				
			Utils.waitFor(11);
		
			//Preview1	
			
			String sParentWindow, sChildWindow;
			
			sParentWindow = oBrowser.getWindowHandle();
			
			System.out.println("Session Id of Parent Window is  : " +sParentWindow);
				
			oBrowser.findElement(By.linkText("Preview")).click();
			
			sChildWindow = oBrowser.getWindowHandles().toArray()[1].toString();
			
			System.out.println("Session Id of Child Window is :" +sChildWindow);

			oBrowser.switchTo().window(sChildWindow);
			
			System.out.println("Title of Child Window  is : "+ oBrowser.getTitle());
			
			Utils.waitFor(21);

			oBrowser.close();
			
			oBrowser.switchTo().window(sParentWindow);
			
			System.out.println("Title of Parent Window  is : "+ oBrowser.getTitle());
			
			//Add Tags
			
			WebElement tagPosts = oBrowser.findElement(By.name("newtag[post_tag]"));
			
			Utils.waitFor(11);
			
			tagPosts.sendKeys("Photography, Awesome Tag, Edureka, Selenium");
			
			oBrowser.findElement(By.xpath("//*[@id='post_tag']/div[1]/div[2]/p/input[2]")).click();
			
			//Post Preview
			
			String sParentWindow1, sChildWindow1;
			
			sParentWindow1 = oBrowser.getWindowHandle();
			
			System.out.println("Session Id of Parent Window1 is : " +sParentWindow1);
				
			oBrowser.findElement(By.linkText("Preview")).click();
			
			sChildWindow1 = oBrowser.getWindowHandles().toArray()[1].toString();
			
			System.out.println("Session Id of Child Window1 is : " +sChildWindow1);

			oBrowser.switchTo().window(sChildWindow1);
			
			Utils.waitFor(21);
			
			System.out.println("Title of Child Window 1  is : "+ oBrowser.getTitle());
			
			oBrowser.findElement(By.linkText("Awesome Tag"));
			
			Assert.assertEquals(oBrowser.getPageSource().contains("Awesome Tag"), true);
			
			oBrowser.findElement(By.linkText("Edureka"));
			
			Assert.assertEquals(oBrowser.getPageSource().contains("Edureka"), true);
			
			oBrowser.findElement(By.linkText("Photography"));
			
			Assert.assertEquals(oBrowser.getPageSource().contains("Photography"), true);
			
			oBrowser.findElement(By.linkText("Selenium"));

			Assert.assertEquals(oBrowser.getPageSource().contains("Selenium"), true);
			
			oBrowser.close();
			
			oBrowser.switchTo().window(sParentWindow1);
			
			System.out.println("Title of Parent Window 1  is : "+ oBrowser.getTitle());
			
			//Format widget
			
			WebElement radioButton = oBrowser.findElement(By.xpath("//*[@id='post-format-aside']"));
			
			radioButton.click();
			
			Utils.waitFor(21);
			
			//Screen Options
			
			oBrowser.findElement(By.linkText("Screen Options")).click();
			
			Utils.waitFor(11);
			
			List allCheckBoxList = oBrowser.findElements(By.className("hide-postbox-tog"));
			
			int chkBoxListSize = allCheckBoxList.size();
			
			
			for ( int i = 0; i <chkBoxListSize; i++)
			{
				
				String chkboxeachvalue = ((WebElement) allCheckBoxList.get(0)).getAttribute("value");
				
				
				
				if(chkboxeachvalue.equalsIgnoreCase("formatdiv"))
				{
					i++;
				}
				
				if(chkboxeachvalue.equalsIgnoreCase("tagsdiv-post_tag"))
				{
					i++;
				}

				if(chkboxeachvalue.equalsIgnoreCase("postimagediv"))
				{
					i++;
				}
				
				((WebElement) allCheckBoxList.get(i)).click();
			}
			
			oBrowser.switchTo().defaultContent();
			
			//Preview
			
			String sParentWindow2, sChildWindow2;
			
			sParentWindow2 = oBrowser.getWindowHandle();
			
			System.out.println("Session Id of Parent Window 2 is : " +sParentWindow2);
				
			oBrowser.findElement(By.linkText("Preview")).click();
			
			sChildWindow2 = oBrowser.getWindowHandles().toArray()[1].toString();
			
			System.out.println("Session Id of Child Window 2 is : " +sChildWindow2);

			oBrowser.switchTo().window(sChildWindow2);
			
			System.out.println("Title of Child Window 2 is : "+ oBrowser.getTitle());

			Utils.waitFor(21);
			
			//Hover to logout 
			Actions oAction = new Actions(oBrowser);
			
			WebElement oAdmin = oBrowser.findElement(By.xpath("//*[@id='wp-admin-bar-my-account']/a"));
			
			oAction.moveToElement(oAdmin).build().perform();
			
			Utils.waitFor(5);
			
			WebElement oLogout = oBrowser.findElement(By.linkText("Log Out"));
			
			oAction.moveToElement(oLogout).click().build().perform();
			
				
		
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
			
			
			
		
		public void getScreenShot() throws Exception {
			File scrFile = ((TakesScreenshot)oBrowser).getScreenshotAs(OutputType.FILE);
			
			FileUtils.copyFile(scrFile, new File("/Users/navyananenkala/Desktop/Selenium/Workspace/Framework Project/test-output/screenshot.png"));
		}
		
		
		
		@AfterMethod
		public void teardown(){
			
			oBrowser.close();
			
		}
			
			
			
	}			
			



