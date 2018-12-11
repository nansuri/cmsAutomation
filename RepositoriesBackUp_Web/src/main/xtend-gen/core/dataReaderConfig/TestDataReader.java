package core.dataReaderConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Properties;
import org.apache.maven.plugin.MojoExecutionException;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class TestDataReader {
  private static Properties property;
  
  private static FileInputStream propertyFile;
  
  /**
   * Get the suitable test data file by providing the file name along with its location
   * @param fileNameWithPath Provide the file name with exact location which holds the test data for required test script
   * @author Shameer
   */
  public static FileInputStream getTestDataFile(final String fileNameWithPath) {
    try {
      FileInputStream _xtrycatchfinallyexpression = null;
      try {
        FileInputStream _xblockexpression = null;
        {
          Properties _properties = new Properties();
          TestDataReader.property = _properties;
          FileInputStream _fileInputStream = new FileInputStream(fileNameWithPath);
          _xblockexpression = TestDataReader.propertyFile = _fileInputStream;
        }
        _xtrycatchfinallyexpression = _xblockexpression;
      } catch (final Throwable _t) {
        if (_t instanceof FileNotFoundException) {
          final FileNotFoundException e = (FileNotFoundException)_t;
          throw new MojoExecutionException("[ERROR] File not found ", e);
        } else if (_t instanceof IOException) {
          final IOException e_1 = (IOException)_t;
          throw new MojoExecutionException("[ERROR] Error found while reading properties ", e_1);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return _xtrycatchfinallyexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Read the test data value according to the test data key
   * @param testDataKey: This is a key which holds the value of test data
   * @author
   */
  public static String getTestData(final String testDataKey) throws Exception {
    String data = null;
    TestDataReader.property.load(TestDataReader.propertyFile);
    boolean _containsKey = TestDataReader.property.containsKey(testDataKey);
    if (_containsKey) {
      data = TestDataReader.property.getProperty(testDataKey);
    } else {
      String _format = MessageFormat.format("Missing value for key >>>>! : ", testDataKey);
      throw new InvalidParameterException(_format);
    }
    return data;
  }
}
