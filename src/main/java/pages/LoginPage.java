package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	WebDriver driver;
	HomePage homePage;
	By userImageIconGrayCss = By.cssSelector("img[class*='AppLoginView__personnelImage'][src*='demo_profile_pic']");
	
	By userImageIconBlueCss = By.cssSelector("img[class*='AppLoginView__personnelImage'][src*='user-photo-success']");
	
	@FindBy(css = "input[testid='userId']")
	WebElement userIdTextBox;

	@FindBy(css = "input[testid='password']")
	WebElement passwordTextBox;

	@FindBy(xpath = "//button[./*[text()='Sign In']]")
	WebElement signInButton;

	By signInErrorXpath = By.xpath("//*[contains(@class,'AppLoginView__errorContainer')][text()='Oops, Invalid user id & password combination']");
	
	public int TimeoutValue = 10;

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	
	public void enterUserIdAndTab(String userId) throws InterruptedException {
		userIdTextBox.sendKeys(userId);	 
		Thread.sleep(500);
		userIdTextBox.sendKeys(Keys.TAB);
	}

	public void enterPassword(String password) {
		passwordTextBox.sendKeys(password);	 
	}

	public void clickSignInButton() {
		signInButton.click();
	}
	
	public boolean isUserAvatarGrayed() {
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(userImageIconGrayCss));
			if (driver.findElements(userImageIconGrayCss).size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
		
	}
	public boolean isUserAvatarBlue() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(userImageIconBlueCss));
			if (driver.findElements(userImageIconBlueCss).size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
		
	}
	public boolean isSignInErrorDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(signInErrorXpath));
			if (driver.findElements(signInErrorXpath).size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}	
	

	
	
}
