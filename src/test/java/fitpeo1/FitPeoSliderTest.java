package fitpeo1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FitPeoSliderTest {

	WebDriver driver;

	@BeforeClass
	void setUp() {
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@AfterClass
	void tearDown() {
		driver.close();
	}

	@Test
	void testSliderMovement() {

		try {

			// fitpeo homepage
			driver.get("https://www.fitpeo.com/");

			// revenue calculator
			driver.findElement(By.xpath("//div[contains(text(),'Revenue Calculator')]")).click();

			// scroll to slider
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement sliderSection = driver.findElement(By.xpath("//div[@class='MuiBox-root css-79elbk']"));
			jse.executeScript("arguments[0].scrollIntoView(true);", sliderSection);

			// get slider and text box
			WebElement slider = driver.findElement(By.xpath("//span[starts-with(@class,'MuiSlider-thumb')]"));
			WebElement txtBox = driver.findElement(By.xpath("//input[@aria-invalid='false']"));

			// slider position and textbox value
//      System.out.println(slider.getLocation());                      //(x,y) = (800, 654)	
//		 System.out.println(textBox.getAttribute("value"));            //value = 200     range = 0-2000

			// move slider - xoffset=93, yoffset=0 to get value near 820 in txtBox
			Actions act = new Actions(driver);
			act.dragAndDropBy(slider, 93, 0).perform();

			// validation - expected value '817~820'
			Assert.assertEquals("817", txtBox.getAttribute("value"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
