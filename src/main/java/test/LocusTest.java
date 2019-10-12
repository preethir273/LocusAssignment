package test;

import java.awt.AWTException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AddTaskModal;
import pages.HomePage;
import pages.LoginPage;

public class LocusTest {

	WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	AddTaskModal addTaskModal;
	
	
	String testUserId = "candidate";
	String taskName = "tesst";;
	String LoginURL = "https://test4.locus-dashboard.com/#/login";
	String ExpectedHomePageTitle = "Login - Locus Dashboard";
	String validPassword = "5615-4b16";
	String invalidPassword = "blah-blah";
	String searchForTask = "2019-02-06-kanj-6";
	int searchResultCount =2;
	String address = "sony signal, koramangala, bangalore";
	String slaMin = "120";
	
	@BeforeTest
	public void setup() {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-fullscreen");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		addTaskModal = new AddTaskModal(driver);
	}


	@Test(priority = 1)
	public void invalidLogin() throws InterruptedException {

		driver.get(LoginURL);
		// verify login page title
		Assert.assertEquals(driver.getTitle(), ExpectedHomePageTitle);
		Assert.assertTrue(loginPage.isUserAvatarGrayed());

		// enter userid
		loginPage.enterUserIdAndTab(testUserId);
		Assert.assertTrue(loginPage.isUserAvatarBlue());

		// enter password
		loginPage.enterPassword(invalidPassword);

		loginPage.clickSignInButton();
		Assert.assertTrue(loginPage.isSignInErrorDisplayed());
	}

	@Test(priority = 2)
	public void validLogin() throws Exception {

		if (driver.getCurrentUrl().equals(LoginURL))
			driver.navigate().refresh();
		driver.get(LoginURL);

		// verify login page title
		Assert.assertEquals(driver.getTitle(), ExpectedHomePageTitle);
		Assert.assertTrue(loginPage.isUserAvatarGrayed());

		// enter userid
		loginPage.enterUserIdAndTab(testUserId);
		Assert.assertTrue(loginPage.isUserAvatarBlue());

		// enter password
		loginPage.enterPassword(validPassword);

		loginPage.clickSignInButton();
		Assert.assertTrue(homePage.isUserIconDisplayed());
		homePage.selectDistributionBU();
	}

	@Test(priority = 3, dependsOnMethods = { "validLogin" })
	public void checkPersonalProfile() throws InterruptedException, AWTException {

		Assert.assertFalse(homePage.isUserProfileDropDownDisplayed());
		homePage.hoverOnUserIcon();
		Assert.assertTrue(homePage.isUserProfileDropDownDisplayed());
		Assert.assertEquals(homePage.getFirstUserProfileField().toLowerCase(), testUserId.toLowerCase());
		homePage.clickLogo();

	}

	@Test(priority = 4, dependsOnMethods = { "validLogin" })
	public void searchTask() throws InterruptedException {

		homePage.clickSearchButtonInHome();
		Assert.assertTrue(homePage.isSearchModalDisplayed());
		homePage.enterSearchTextInModal(searchForTask);
		Assert.assertEquals(homePage.getSearchResultCountInModalForTaskId(searchForTask), searchResultCount);
		homePage.clickLogo();

	}

	@Test(priority = 5, dependsOnMethods = { "validLogin" })
	public void createServiceTask() throws InterruptedException {

		homePage.clickAddTask();
		Assert.assertTrue(addTaskModal.isAddTaskDisplayed());
		Assert.assertTrue(addTaskModal.isProceedButtonDisabled());
		addTaskModal.enterTaskId(taskName);
		addTaskModal.selectFirstTeam();
		Thread.sleep(2000);
		Assert.assertFalse(addTaskModal.isProceedButtonDisabled());
		addTaskModal.clickProceedButton();
		Assert.assertTrue(addTaskModal.isTaskDetailModalDisplayed());
		addTaskModal.clickEditButton();
		addTaskModal.clickServiceTask();
		addTaskModal.enterAddress(address);
		addTaskModal.clickchooseSlot();
		addTaskModal.enterSLAMin(slaMin);
		addTaskModal.clickSaveButton();
		addTaskModal.clickCreateTaskButton();
		Assert.assertTrue(addTaskModal.isTaskCreated());
		addTaskModal.closeTaskDetailModal();

	}

	@Test(priority = 6, dependsOnMethods = { "createServiceTask" })
	public void searchForCreatedTask() throws InterruptedException {

		homePage.clickSearchButtonInHome();
		Assert.assertTrue(homePage.isSearchModalDisplayed());
		homePage.enterSearchTextInModal(taskName);
		Assert.assertEquals(homePage.getSearchResultCountInModalForTaskId(taskName), 1);
		homePage.clickLogo();

	}

	/**
	 * closes the browser tabs
	 */
	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
