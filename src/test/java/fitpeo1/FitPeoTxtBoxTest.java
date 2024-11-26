package fitpeo1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FitPeoTxtBoxTest {
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
	void testTxtBox() {

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
			WebElement txtResult = driver.findElement(
					By.xpath("//p[normalize-space()='Total Individual Patient/Month']/following-sibling::p"));

			// textbox value and slider position - before
//		 System.out.println(txtBox.getAttribute("value"));            //value = 200          range = 0-2000
//		 System.out.println(slider.getLocation().getX());			  //x = 800

			// put "560" in txt box
			Actions actions = new Actions(driver);
			actions.click(txtBox).keyDown(Keys.BACK_SPACE).keyDown(Keys.BACK_SPACE).keyDown(Keys.BACK_SPACE)
					.keyUp(Keys.BACK_SPACE).sendKeys("5").sendKeys("6").sendKeys("0").perform();

			// textbox value and slider position - after
//		 System.out.println(txtBox.getAttribute("value"));           //value = 560  
//		 System.out.println(slider.getLocation().getX());            // x = 791      keeps changing so not able to put validation

			Assert.assertEquals(txtResult.getText(), txtBox.getAttribute("value"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
