package com.ITSM.Scenarios;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.tavant.base.DriverFactory;
import com.tavant.base.WebPage;
import com.tavant.kwutils.PageObjectLoader;
import com.tavant.utils.TwfException;

import jxl.read.biff.BiffException;

public class ITSM extends WebPage{


	WebDriver driver;
	Set<String> windowHandles;
	JavaScriptExecutor jse;
	static int checkedOutItems=0;
	String fileName = "C:\\ITSM\\TestAttachement.txt";
	//------------------------------------------------------------------------------------------//
	/*@Description-Switch to last window
	 *@Author-Nitish.Nayak
	 */
	//------------------------------------------------------------------------------------------//	

	public void switchToLastWindow(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		for (String windowHandle : windowHandles) {

			driver.switchTo().window(windowHandle);
		}

	}



	//-------------------------------------------------------------------------------------------//
	/*@Select drop down by value
	 *@Author-Nitish.Nayak
	 */
	//-------------------------------------------------------------------------------------------//	

	public void selectOptionByValue(String value) throws TwfException, BiffException, IOException{

		driver=DriverFactory.getDriver();
		WebElement ele=getElementByUsing(value.split(",")[0]);

		Select dropDown=new Select(ele);
		dropDown.selectByValue(value.split(",")[1]);

	}


	//--------------------------------------------------------------------------------------------//
	/*@Select drop down by index
	 * @Author-Nitish.Nayak
	 */
	//--------------------------------------------------------------------------------------------//

	public void selectOptionByIndex(String value)throws TwfException, BiffException, IOException{

		driver=DriverFactory.getDriver();
		WebElement ele=getElementByUsing(value.split(",")[0]);

		Select dropDown=new Select(ele);
		int index=Integer.parseInt(value.split(",")[1]);
		dropDown.selectByIndex(index);



	}

	//----------------------------------------------------------------------------------------------//
	/*@Choose the business units options
	 * @Author-Nitish.Nayak
	 */
	//---------------------------------------------------------------------------------------------//

	public void chooseBusinessUnitOptions(String k) throws BiffException, IOException, TwfException, InterruptedException{


		//scrolling to the first option in business unit and clicking it also clicking the sub option.
		driver=DriverFactory.getDriver();
		JavascriptExecutor jse;
		jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", getElementByUsing("BusinessUnits_FirstOption")); 

		getElementByUsing("BusinessUnits_FirstOption").click();
		waitForElement(getElementByUsing("BusinessUnits_FirstSubOption"), 20);
		getElementByUsing("BusinessUnits_FirstSubOption").click();

		//clicking the second business unit option and also clicking the sub option

		getElementByUsing("BusinessUnits_SecondOption").click();
		waitForElement(getElementByUsing("BusinessUnits_SecondSubOption"), 20);
		jse.executeScript("arguments[0].scrollIntoView()", getElementByUsing("BusinessUnits_SecondSubOption")); 
		getElementByUsing("BusinessUnits_SecondSubOption").click();

		//clicking the third business unit option and also clicking the sub option

		jse.executeScript("arguments[0].click();", getElementByUsing("BusinessUnits_ThirdOption"));
		waitForElement(getElementByUsing("BusinessUnits_ThirdSubOption"), 20);
		jse.executeScript("arguments[0].scrollIntoView()", getElementByUsing("BusinessUnits_ThirdSubOption")); 
		getElementByUsing("BusinessUnits_ThirdSubOption").click();

		//clicking the fourth business unit option and also clicking the sub option

		jse.executeScript("arguments[0].click();", getElementByUsing("BusinessUnits_FourthOption"));
		waitForElement(getElementByUsing("BusinessUnits_FourthSubOption"), 20);
		jse.executeScript("arguments[0].scrollIntoView()", getElementByUsing("BusinessUnits_FourthSubOption")); 
		getElementByUsing("BusinessUnits_FourthSubOption").click();

		//moving to the choose Option
		jse.executeScript("arguments[0].scrollIntoView()", getElementByUsing("ChooseOption_Button")); 
		getElementByUsing("ChooseOption_Button").click();

		Thread.sleep(5000);


	}


	//-------------------------------------------------------------------------------------------------//
	/*@CheckOut and Validate Order placed successfully
	 * @Author-Nitish.Nayak
	 */
	//------------------------------------------------------------------------------------------------//

	public void checkOutAndValidateOrderPlaced(String k) throws BiffException, TwfException, IOException, InvalidFormatException{

		driver=DriverFactory.getDriver();
		waitForElement(getElementByUsing("CheckOut_Button"), 20);
		getElementByUsing("CheckOut_Button").click();
		//get the count of the checked out items..
		List<WebElement> items=driver.findElements(By.xpath(PageObjectLoader.getPageObject("CheckOutItemsNumber").getTargetId()));
		checkedOutItems=items.size();

		waitForElement(getElementByUsing("CheckOutItems"), 25);
		getElementByUsing("CheckOut1_Button").click();

		waitForElement(getElementByUsing("RequestSubmittedMessage"), 25);

		getElementByUsing("RequestNumber").click();

		waitForElement(getElementByUsing("RequestPageTotalItems"), 25);
		items=driver.findElements(By.xpath(PageObjectLoader.getPageObject("RequestPageTotalItems").getTargetId()));

		if(!(items.size()==checkedOutItems)){

			addExceptionToReport("There is a mismatch in the number of checked out items..", "", "");

		}

	}



	//-------------------------------------------------------------------------------------------------------//
	/*@Chekout and validate ManageFile Transfer request
	 * @Author-Nitish.Nayak
	 */
	//-----------------------------------------------------------------------------------------------------//

	public void validateManageFileTransferRequest(String k) throws BiffException, TwfException, IOException, InvalidFormatException{

		driver=DriverFactory.getDriver();
		waitForElement(getElementByUsing("RequestSubmittedMessage"), 25);

		List<WebElement> items=driver.findElements(By.xpath(PageObjectLoader.getPageObject("CheckOutItemsNumber").getTargetId()));
		checkedOutItems=items.size();

		getElementByUsing("RequestNumber").click();

		waitForElement(getElementByUsing("RequestPageTotalItems"), 25);
		items=driver.findElements(By.xpath(PageObjectLoader.getPageObject("RequestPageTotalItems").getTargetId()));

		if(!(items.size()==checkedOutItems)){

			addExceptionToReport("There is a mismatch in the number of checked out items..", "", "");

		}




	}


	//----------------------------------------------------------------------------------------------------------//
	/*@Send the current date
	 * @Author-Devi
	 */
	//----------------------------------------------------------------------------------------------------------//


	public void currentDate(String k) throws BiffException, IOException, TwfException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String date1= dateFormat.format(date); 
		getElementByUsing(k).clear();
		getElementByUsing(k).sendKeys(date1);

	}



	//--------------------------------------------------------------------------------------------------------------//
	/*@Add attachement
	 * @Author-Devi
	 */
	//---------------------------------------------------------------------------------------------------------------//

	public void addAttachment(String value) throws BiffException, TwfException, IOException, AWTException, InterruptedException {

		driver=DriverFactory.getDriver();

		WebElement ele=getElementByUsing(value.split(",")[0]);
		ele.click();

		WebElement ele1=getElementByUsing(value.split(",")[1]);
		ele1.click();

		WebElement ele2=getElementByUsing(value.split(",")[2]);


		StringSelection sel = new StringSelection(fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
		System.out.println("selection" + sel);
		Thread.sleep(3000);

		Robot robot = new Robot();
		Thread.sleep(1000);

		robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);

		robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyRelease(java.awt.event.KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		robot.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(2000);

		ele2.click();

	}



	@Override
	public void checkPage() {
		// TODO Auto-generated method stub

	}
	
	//----I am adding this change.....from checkout1....//
	



}
