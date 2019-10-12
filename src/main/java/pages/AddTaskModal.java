package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author preethi
 *
 *         POM class for
 */
public class AddTaskModal {

	WebDriver driver;
	final String proceedButtonXpath = "//button[./*[text()='Proceed']]";

	@FindBy(css = "[class*='TaskSetup__taskInitalDialog']")
	WebElement addTaskModal;

	@FindBy(css = "input[class*='TaskSetup__inputElement']")
	WebElement taskIdTextBox;

	@FindBy(css = "[class*='TaskSetup__valueContainer']")
	WebElement teamDropDown;

	@FindBy(css = "[class*='largeSizeSelectMenu']")
	WebElement teamDropDownValues;

	@FindBy(xpath = proceedButtonXpath)
	WebElement proceedButton;

	@FindBy(css = "[class*='TaskSetup__taskVisitsDialog']")
	WebElement taskDetailModal;

	@FindBy(xpath = "//*[contains(@class,' VisitTypeSelector__title')][text()='Service']")
	WebElement serviceTaskType;

	@FindBy(xpath = "//*[contains(@class,' EditableVisitCard__inputElement')][@placeholder='Please enter address']")
	WebElement addressTextBox;

	@FindBy(xpath = "//button[contains(@class,' SlotCard')]")
	WebElement chooseSlotButton;

	@FindBy(xpath = "//*[contains(@class,' EditableVisitCard__inputElement')][@placeholder='Enter SLA (min)']")
	WebElement slaTextBox;

	@FindBy(xpath = "//button[./*[text()='Save']]")
	WebElement saveButton;

	@FindBy(xpath = "//button[./*[text()='Create Task']]")
	WebElement createTaskButton;

	@FindBy(xpath = "//*[text()='Task Created']")
	WebElement taskCreatedText;
	
	@FindBy(xpath = "//button[./*[text()='close']]")
	WebElement closeModalButton;

	String editButtonCss = "[class*='SelectWithButton__editBtn']";
	public int TimeoutValue = 10;

	public AddTaskModal(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}

	public boolean isAddTaskDisplayed() {
		return addTaskModal.isDisplayed();
	}

	public void enterTaskId(String taskId) {
		taskIdTextBox.sendKeys(taskId);
	}

	public void selectFirstTeam() {
		teamDropDown.click();
		teamDropDownValues.findElements(By.xpath("./*")).get(0).click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(teamDropDown, "SELECT TEAM")));
	
	}

	public boolean isProceedButtonDisabled() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(proceedButtonXpath + "[contains(@class,'disabled')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}

	}
	
	

	public void clickProceedButton() {
		proceedButton.click();
	}

	public boolean isTaskDetailModalDisplayed() {
		return taskDetailModal.isDisplayed();
	}

	public void clickEditButton() {
		taskDetailModal.findElement(By.cssSelector(editButtonCss)).click();
	}

	public void clickServiceTask() {
		serviceTaskType.click();
	}

	public void enterAddress(String address) {
		addressTextBox.sendKeys(address);
	}

	public void clickchooseSlot() {
		chooseSlotButton.click();
	}

	public void enterSLAMin(String minutes) {
		slaTextBox.sendKeys(minutes);
	}

	public void clickSaveButton() {
		saveButton.click();
	}

	public void clickCreateTaskButton() {
		createTaskButton.click();
	}

	public boolean isTaskCreated() {
//		try {
//			WebDriverWait wait = new WebDriverWait(driver, 5);
//			wait.until(ExpectedConditions
//					.visibilityOfElementLocated(By.xpath(proceedButtonXpath + "[contains(@class,'disabled')]")));
//			return true;
//		} catch (TimeoutException e) {
//			return false;
//		}
		return taskCreatedText.isDisplayed();
	}
	
	public void closeTaskDetailModal() {
		closeModalButton.click();
	}
}
