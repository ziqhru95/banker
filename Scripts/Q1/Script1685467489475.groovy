import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

WebUI.openBrowser('https://www.globalsqa.com/angularJs-protractor/BankingProject/#/')
WebUI.maximizeWindow()

def testData = [
    ['Christopher', 'Connely', 'L789C349'],
    ['Frank', 'Christopher', 'A897N450'],
    ['Christopher', 'Minka', 'M098Q585'],
    ['Connely', 'Jackson', 'L789C349'],
    ['Jackson', 'Frank', 'L789C349'],
    ['Minka', 'Jackson', 'A897N450'],
    ['Jackson', 'Connely', 'L789C349']
]

WebUI.click(findTestObject('Object Repository/Page_XYZ Bank/button_Bank Manager Login'))

WebUI.click(findTestObject('Object Repository/Page_XYZ Bank/Page_XYZ Bank/button_Add Customer'))

testData.each { data ->
    def FIRSTNAME = data[0]
    def LASTNAME = data[1]
    def POSTCODE = data[2]

    WebUI.delay(2)

    WebUI.setText(findTestObject('Object Repository/Page_XYZ Bank/Page_XYZ Bank/input_First Name_form-control ng-pristine n_693e51'), FIRSTNAME)
    WebUI.setText(findTestObject('Object Repository/Page_XYZ Bank/Page_XYZ Bank/input_Last Name_form-control ng-pristine ng_64913d'), LASTNAME)
    WebUI.setText(findTestObject('Object Repository/Page_XYZ Bank/Page_XYZ Bank/input_Post Code_form-control ng-pristine ng_b8fd27'), POSTCODE)

    WebUI.click(findTestObject('Object Repository/Page_XYZ Bank/Page_XYZ Bank/button_Add Customer_submit'))

    Alert alert = DriverFactory.getWebDriver().switchTo().alert()
    String alertText = alert.getText()
    println(alertText)
}

WebUI.click(findTestObject('Object Repository/Page_XYZ Bank/button_Customers'))

def allDataInserted = true

testData.each { data ->
    def FIRSTNAME = data[0]
    def LASTNAME = data[1]
    def POSTCODE = data[2]

    def fullName = FIRSTNAME + " " + LASTNAME + " " + POSTCODE

    WebUI.setText(findTestObject('Object Repository/Page_XYZ Bank/input_Customers_form-control ng-pristine ng-untouched ng-valid'), FIRSTNAME)

    // Find the table element and get the rows
    WebElement table = DriverFactory.getWebDriver().findElement(By.xpath("//table/tbody"))
    List<WebElement> rows_table = table.findElements(By.tagName("tr"))
    int rows_count = rows_table.size()

    boolean dataFound = false


    for (int row = 0; row < rows_count; row++) {
        List<WebElement> columns_row = rows_table[row].findElements(By.tagName("td"))
        int columns_count = Math.min(columns_row.size(), 3) // Limit the number of columns to check to 3

      
        def concatenatedCellText = columns_row[0].getText() + " " + columns_row[1].getText() + " " + columns_row[2].getText()

        
        if (fullName.equals(concatenatedCellText)) {
            System.out.println(fullName + " matches " + concatenatedCellText)
            dataFound = true
            break 
        }
    }

    
    if (!dataFound) {
        allDataInserted = false
    }
}


if (allDataInserted) {
    System.out.println("All data has been successfully inserted.")
} else {
    System.out.println("Some Data is missing!!!")
}

def deletedata = [
    ['Christopher', 'Connely', 'L789C349'],
    ['Jackson', 'Frank', 'L789C349']
]

def allDataDeleted = true


deletedata.each { data ->
    def FIRSTNAME = data[0]
    def LASTNAME = data[1]
    def POSTCODE = data[2]

    def fullName = FIRSTNAME + " " + LASTNAME + " " + POSTCODE

    
    WebUI.setText(findTestObject('Object Repository/Page_XYZ Bank/input_Customers_form-control ng-pristine ng-untouched ng-valid'), FIRSTNAME)

  
    WebElement table = DriverFactory.getWebDriver().findElement(By.xpath("//table/tbody"))
    List<WebElement> rows_table = table.findElements(By.tagName("tr"))
    int rows_count = rows_table.size()

   
    for (int row = 0; row < rows_count; row++) {
        List<WebElement> columns_row = rows_table[row].findElements(By.tagName("td"))
        int columns_count = Math.min(columns_row.size(), 3) // Limit the number of columns to check to 3

       
        def concatenatedCellText = columns_row[0].getText() + " " + columns_row[1].getText() + " " + columns_row[2].getText()

        
        if (fullName.equals(concatenatedCellText)) {
            System.out.println(fullName + " matches " + concatenatedCellText)
            WebElement deleteButton = rows_table[row].findElement(By.tagName("button"))
            deleteButton.click()
   
            allDataDeleted = true
            break
        }
    }
}


if (allDataDeleted) {
    System.out.println("All data has been successfully deleted.")
} else {
    System.out.println("Not all data has been deleted.")
}
