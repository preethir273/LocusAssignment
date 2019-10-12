package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author preethi
 *
 *         POM class for Locus HomePage
 */
public class HomePage {
	WebDriver driver;

	@FindBy(xpath = "//button[./*[text()='Add Task']]")
	WebElement addTaskButton;

	@FindBy(css = "[class*='BusinessUnitSelectionModal__buContainer']")
	WebElement bUSelectionModel;

	@FindBy(xpath = "//*[contains(@class,'BusinessUnitChip__chipInner')][.//*[text()='Distribution']]")
	WebElement bUDistribution;

	@FindBy(css = "[class*='LeftBar__logoWrap']")
	WebElement logo;

	@FindBy(css = "[class*='UserMenuItem__accountIconWrap']")
	WebElement userIcon;

	@FindBy(css = "[class*='UserMenuItem__verticalMenu']")
	WebElement userProfileDropdown;

	@FindBy(xpath = "//*[contains(@class,'UserMenuItem__verticalMenu')]//*[contains(@class,'UserMenuItem__title')]")
	WebElement userProfileUserId;

	@FindBy(xpath = "//button[contains(@class,'TaskSearchInput__rightIconButton')]")
	WebElement clearSearchModalButton;

	By searchButtonXpath = By.xpath("//button[contains(@class,'Search')]/parent::*");

	@FindBy(css = "[class*='TaskSearchView__overlay']")
	WebElement searchModal;

	@FindBy(css = "input[class*='TaskSearchInput']")
	WebElement searchInputTextBox;

	@FindBy(xpath = "//*[@class='fixedDataTableCellGroupLayout_cellGroupWrapper']/*[@class='fixedDataTableCellGroupLayout_cellGroup']//*[contains(@class,'TaskSearchResults__taskId___2k3qr')]")
	List<WebElement> searchResultTaskId;

	public int TimeoutValue = 5;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public boolean isUserIconDisplayed() {
		return userIcon.isDisplayed();
	}

	public void hoverOnUserIcon() throws AWTException, InterruptedException {
		Robot r = new Robot();
		r.mouseMove(userIcon.getLocation().x, userIcon.getLocation().y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(1000);
	}

	public boolean isUserProfileDropDownDisplayed() {
		return userProfileDropdown.isDisplayed();
	}

	public String getFirstUserProfileField() {
		return userProfileUserId.getText();
	}

	public void selectDistributionBU() {
		if (bUSelectionModel.isDisplayed()) {
			bUDistribution.click();
			try {
				WebDriverWait wait = new WebDriverWait(driver, 3);
				wait.until(ExpectedConditions.invisibilityOf(bUSelectionModel));
			}
			catch(TimeoutException e) {	
			}
		}
	}

	public void clickLogo() {
		logo.click();
	}

	public void clickAddTask() {
		addTaskButton.click();
	}

	public void clickSearchButtonInHome() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchButtonXpath));

		driver.findElement(searchButtonXpath).click();
	}

	public boolean isSearchModalDisplayed() {
		return searchModal.isDisplayed();
	}

	public void enterSearchTextInModal(String searchText) throws InterruptedException {
		try {
			if (clearSearchModalButton.isDisplayed())
				clearSearchModalButton.click();
		} catch (NoSuchElementException e) {

		}

		searchInputTextBox.sendKeys(searchText);
		Thread.sleep(500);
		searchInputTextBox.sendKeys(Keys.ENTER);
	}

	public int getSearchResultCountInModalForTaskId(String TaskId) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<WebElement> iter = searchResultTaskId.iterator();
		int count = 0;
		while (iter.hasNext()) {
			WebElement we = iter.next();
			if (we.getText().equals(TaskId)) {
				count++;
			}
		}

		return count;
	}
}
