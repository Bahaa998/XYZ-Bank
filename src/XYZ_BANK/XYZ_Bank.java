package XYZ_BANK;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XYZ_Bank {

	public WebDriver driver;
	SoftAssert softAssertProccess = new SoftAssert();
	String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
	String ExpectedTitle = "XYZ Bank";

//	//Add customer	
//	String fName = "Bahaa " + RandomStringUtils.randomAlphanumeric(2);
//	String lName = "Algemzawi " + RandomStringUtils.randomAlphanumeric(2);
//	int PostalCode = (int) (Math.random() * 10000);

	@BeforeTest
	public void pre_Test() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(URL);
	}

	@Test(priority = 1, enabled = false)
	public void Check_The_Title() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		String ActualTitle = driver.getTitle();
		softAssertProccess.assertEquals(ActualTitle, ExpectedTitle, "Check the actual title : ");
		softAssertProccess.assertAll();
	}

//******************************* Bank Manage Login - Add Customer - open account **************************************

	@Test(priority = 2, enabled = true, invocationCount = 1)
	public void Add_Customer() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//		driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

		// Random number & switch random number to string
		int userID = (int) (Math.random() * 500);
		StringBuilder userName = new StringBuilder();
		userName.append(userID);
		String userIDAssString = userName.toString();

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();

		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input")).sendKeys("fName"
				+ userIDAssString + Keys.TAB + "lName" + userIDAssString + Keys.TAB + userIDAssString + Keys.ENTER);

		String TextAlert1 = driver.switchTo().alert().getText();
		boolean CheckSuccessfullyAdded = TextAlert1.contains("successfully");
		softAssertProccess.assertEquals(CheckSuccessfullyAdded, true, "Customer added successfully with customer : ");

		driver.switchTo().alert().accept();
		Thread.sleep(500);

		driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input"))
				.sendKeys("fName" + userIDAssString);
		Thread.sleep(500);

		List<WebElement> myUsers = driver.findElements(By.xpath("//tbody/tr"));
		int myUsersFound = myUsers.size();
		Thread.sleep(700);

		softAssertProccess.assertEquals(myUsersFound, 1, "Found customer added : ");

		// =========== Open Account =============
		driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/openAccount");
		WebElement customerMenue = driver.findElement(By.id("userSelect"));
		WebElement currencyMenue = driver.findElement(By.id("currency"));

		String FirsName = "fName" + userIDAssString;
		String LastName = "lName" + userIDAssString;

		Select selectCustomerMenue = new Select(customerMenue);
		selectCustomerMenue.selectByVisibleText(FirsName + " " + LastName);

		Select selectCurrencyMenue = new Select(currencyMenue);
		selectCurrencyMenue.selectByIndex(1);

		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();

		String TextAlert2 = driver.switchTo().alert().getText();
		boolean CheckSuccessfullyOpen = TextAlert2.contains("successfully");
		softAssertProccess.assertEquals(CheckSuccessfullyOpen, true,
				"Account created successfully with account Number : ");
		driver.switchTo().alert().accept();

		softAssertProccess.assertAll();
	}

//***************************** Customer login - Deposit $ Withdraw *****************************
	
	@Test(priority = 3, enabled = true)
	public void bop() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.navigate().to("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
		WebElement nameMenue = driver.findElement(By.id("userSelect"));

		Select nameSelect = new Select(nameMenue);
		nameSelect.selectByIndex(2);
//		mySelect.selectByVisibleText("Harry Potter");
		
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();
		Thread.sleep(1000);
		
		String myBiggestAmount = "5000";
		String mySmallestAmount = "1500";

		int Biggest = Integer.parseInt(myBiggestAmount);
		int Smallest = Integer.parseInt(mySmallestAmount);
		
		WebElement priceMenue = driver.findElement(By.id("accountSelect"));
		for(int i = 0; i < 3; i++) {
			Select priceSelect = new Select(priceMenue);
			priceSelect.selectByIndex(i);
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input"))
					.sendKeys(myBiggestAmount);
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
			Thread.sleep(1000);

			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input"))
					.sendKeys(mySmallestAmount);
			driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();

			String myBalance = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText();
			int ActualBalance = Integer.parseInt(myBalance);
			int ExpectedBalance = Biggest - Smallest;
			
			Thread.sleep(1000);
			softAssertProccess.assertEquals(ActualBalance, ExpectedBalance, "check withdrawl process : ");
			softAssertProccess.assertAll();
		}

	}
	
	
	@AfterTest
	public void afterMyTest() throws InterruptedException {
//		Thread.sleep(3000);
//		driver.quit();
	}

}
