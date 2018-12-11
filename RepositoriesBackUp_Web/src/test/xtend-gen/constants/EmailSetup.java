package constants;

import core.constants.Constant;

@SuppressWarnings("all")
public class EmailSetup {
  public final static Constant MAIL_FROM = Constant.valueOf("mshameer701@gmail.com");
  
  public final static Constant MAIL_FROM_PASSWORD = Constant.valueOf("from-email-password");
  
  public final static Constant TO_EMAIL_ADDRESS = Constant.valueOf("mshameer701@gmail.com");
  
  public final static Constant OUTPUT_ZIP_FILE = Constant.valueOf((System.getProperty("user.dir") + "\\TestReport\\TestReportAttachment.zip"));
  
  public final static Constant SOURCE_FOLDER = Constant.valueOf((System.getProperty("user.dir") + "\\TestReport"));
}
