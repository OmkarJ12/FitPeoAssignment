package fitpeo1;

import java.time.Duration;
import java.util.List;

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

public class FitPeoCheckBoxTest {

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
			WebElement chkBoxSection = driver.findElement(By.xpath("//div[@class='MuiBox-root css-1p19z09']"));
			WebElement txtResultSection = driver.findElement(By.xpath("//div[@class='MuiBox-root css-19zjbfs']"));
			jse.executeScript("arguments[0].scrollIntoView(true);", chkBoxSection);

			// get txt box and total reimbursement
			WebElement txtBox = driver.findElement(By.xpath("//input[@aria-invalid='false']"));
			WebElement totalReimbursement = driver.findElement(By.xpath(
					"//p[contains(text(),'Total Recurring Reimbursement for all Patients Per')]/following-sibling::p"));

			// getting checkboxes
			List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
//     		System.out.println(checkBoxes.size());            //size = 12

			// Iterating checkboxes to select correct ones
			for (int i = 0; i < checkBoxes.size(); i++) {
				WebElement headerNumber = driver.findElement(By.xpath(
						"//body/div[@class='MuiBox-root css-3f59le']/div[@class='MuiBox-root css-rfiegf']/div[@class='MuiBox-root css-1p19z09']/div["
								+ (i + 1) + "]/p[1]"));
				String cptNum = headerNumber.getText();
				if (checkBoxes.get(i).isSelected())
					continue;
				else if (cptNum.equalsIgnoreCase("CPT-99091") || cptNum.equalsIgnoreCase("CPT-99453")
						|| cptNum.equalsIgnoreCase("CPT-99454") || cptNum.equalsIgnoreCase("CPT-99474"))
					checkBoxes.get(i).click();
			}

			// scrolling back to txtbox
			jse.executeScript("arguments[0].scrollIntoView(true);", txtResultSection);

			// put "820" in txt box
			Actions actions = new Actions(driver);
			actions.click(txtBox).keyDown(Keys.BACK_SPACE).keyDown(Keys.BACK_SPACE).keyDown(Keys.BACK_SPACE)
					.keyUp(Keys.BACK_SPACE).sendKeys("8").sendKeys("2").sendKeys("0").perform();

			// validation - value should be $110700 when txtbox = 820
			Assert.assertEquals(totalReimbursement.getText(), "$110700");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
