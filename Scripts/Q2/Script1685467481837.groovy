import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import javax.swing.JOptionPane

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.globalsqa.com/angularJs-protractor/BankingProject/#/')

WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Customer Login'))

WebUI.selectOptionByValue(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/select_---Your Name---       Hermoine Grang_6e895b'), 
    '1', true)

WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Login'))

WebUI.selectOptionByValue(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/select_100110021003'), 
    'number:1003', true)

def INITIALBALANCE = WebUI.getText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/strong_0'), FailureHandling.STOP_ON_FAILURE).toInteger()

def transactions = [50000, -3000, -2000, 5000,-10000,-15000,1500]

def CURRENTBALANCE = INITIALBALANCE

for (def transactionAmount : transactions) {
    if (transactionAmount > 0) {
        // Debit transaction
		
        WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Deposit'))
//				def userInput = JOptionPane.showInputDialog(null, "Please Deposit:")
//				WebUI.comment("User input: ${userInput}")
        WebUI.setText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/input_Amount to be Deposited_form-control n_97f4dd'), 
            transactionAmount.toString())

        WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Deposit_1'))

        WebUI.verifyElementText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/span_Deposit Successful'), 
            'Deposit Successful')

        CURRENTBALANCE += transactionAmount
    } else {
        // Credit transaction
        WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Withdrawl'))
//				def userInput = JOptionPane.showInputDialog(null, "Withdraw Amount:")
//				WebUI.comment("User input: ${userInput}")
        WebUI.setText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/input_Amount to be Deposited_form-control n_97f4dd'), 
            (-transactionAmount).toString())

        WebUI.click(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/button_Withdraw'))

        CURRENTBALANCE -=(-transactionAmount)

        WebUI.verifyElementText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/Page_XYZ Bank/span_Transaction successful'), 
            'Transaction successful')
    }

    WebUI.verifyMatch(latestbalance =WebUI.getText(findTestObject('Object Repository/Q2/Page_XYZ Bank/Page_XYZ Bank/strong_0')), 
        CURRENTBALANCE.toString(), false, FailureHandling.CONTINUE_ON_FAILURE)
	println((latestbalance + '&') + CURRENTBALANCE)
	
}

WebUI.closeBrowser()
