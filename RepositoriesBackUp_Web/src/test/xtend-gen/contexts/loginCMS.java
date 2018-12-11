package contexts;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public class loginCMS {
  public String URL;
  
  public String email;
  
  public String password;
  
  public String excelPath;
  
  public int sheetNo;
  
  public int rowNo;
  
  @Pure
  public String getURL() {
    return this.URL;
  }
  
  public void setURL(final String URL) {
    this.URL = URL;
  }
  
  @Pure
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(final String email) {
    this.email = email;
  }
  
  @Pure
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(final String password) {
    this.password = password;
  }
  
  @Pure
  public String getExcelPath() {
    return this.excelPath;
  }
  
  public void setExcelPath(final String excelPath) {
    this.excelPath = excelPath;
  }
  
  @Pure
  public int getSheetNo() {
    return this.sheetNo;
  }
  
  public void setSheetNo(final int sheetNo) {
    this.sheetNo = sheetNo;
  }
  
  @Pure
  public int getRowNo() {
    return this.rowNo;
  }
  
  public void setRowNo(final int rowNo) {
    this.rowNo = rowNo;
  }
}
