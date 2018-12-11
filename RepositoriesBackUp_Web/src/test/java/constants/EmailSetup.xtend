package constants

import core.constants.Constant

class EmailSetup {
	val public static MAIL_FROM = Constant.valueOf('mshameer701@gmail.com')
	val public static MAIL_FROM_PASSWORD = Constant.valueOf('from-email-password')
	val public static TO_EMAIL_ADDRESS = Constant.valueOf('mshameer701@gmail.com')
	
	
	val public static OUTPUT_ZIP_FILE = Constant.valueOf(System.getProperty("user.dir") + "\\TestReport\\TestReportAttachment.zip")
	val public static SOURCE_FOLDER  = Constant.valueOf(System.getProperty("user.dir") + "\\TestReport")
}