package core.dataReaderConfig

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.InvalidParameterException
import java.text.MessageFormat
import java.util.Properties
import org.apache.maven.plugin.MojoExecutionException

class TestDataReader {

	static Properties property
	static FileInputStream propertyFile

	/**
	 * Get the suitable test data file by providing the file name along with its location
	 * @param fileNameWithPath Provide the file name with exact location which holds the test data for required test script
	 * @author Shameer
	 */
	def public static getTestDataFile(String fileNameWithPath) {

		try {
			property = new Properties();
			propertyFile = new FileInputStream(fileNameWithPath);

		} catch (FileNotFoundException e) {
			throw new MojoExecutionException("[ERROR] File not found ", e);
		} catch (IOException e) {
			throw new MojoExecutionException("[ERROR] Error found while reading properties ", e);
		}

	}

	/**
	 * Read the test data value according to the test data key
	 * @param testDataKey: This is a key which holds the value of test data 
	 * @author 
	 */
	def public static getTestData(String testDataKey) throws Exception {

		var String data
		property.load(propertyFile);

		if (property.containsKey(testDataKey)) {
			data = property.getProperty(testDataKey);
		} else {
			throw new InvalidParameterException(MessageFormat.format("Missing value for key >>>>! : ", testDataKey));
		}

		return data;
	}

}
