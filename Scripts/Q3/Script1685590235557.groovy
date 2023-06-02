import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import groovy.json.JsonSlurper as JsonSlurper
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

// -	Create a new user
def createNewUserRequest = findTestObject('Create New User')
def createNewUserResponse = WS.sendRequest(createNewUserRequest)
WS.verifyResponseStatusCode(createNewUserResponse, 200)
def slurper = new JsonSlurper()
def createNewUserJson = slurper.parseText(createNewUserResponse.getResponseBodyContent())
def username1 = createNewUserJson.username
println(username1)


// -	Read the created user

def readCreatedUserResponse = WS.sendRequest(findTestObject('Read created user', [('username') : username1]))
def readCreatedUserJson = slurper.parseText(readCreatedUserResponse.getResponseBodyContent())
def username2 = readCreatedUserJson.username
println(readCreatedUserJson)


//update username
def newusername1 = 'JamesBlunt'
WS.sendRequest(findTestObject('Update Username', [('username') : username2, ('newusername') : newusername1]))

//read updated username
def readCreatedUserResponseupdated = WS.sendRequest(findTestObject('Read created user', [('username') : newusername1]))
def readCreatedUserJsonafterupdated = slurper.parseText(readCreatedUserResponseupdated.getResponseBodyContent())
println(readCreatedUserJsonafterupdated)


//delete user
WS.sendRequest(findTestObject('Delete User', [('username') : newusername1]))


//verify deleted user using read created user api which equal should be 404 status then passed.
def readCreatedUserResponsedeleted = WS.sendRequest(findTestObject('Read created user', [('username') : newusername1]))
WS.verifyResponseStatusCode(readCreatedUserResponsedeleted, 404)





