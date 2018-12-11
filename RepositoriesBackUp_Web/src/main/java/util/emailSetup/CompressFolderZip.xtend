package util.emailSetup

import constants.EmailSetup
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/** 
 * Send the report through email
 * @author mketheeswaran
 */
class CompressFolderZip {

	package List<String> fileList
	static final String SOURCE_FOLDER = EmailSetup.SOURCE_FOLDER.value

	public new() {
		fileList = new ArrayList<String>()
	}

	/** 
	 * Zip it
	 * @param zipFile output ZIP file location
	 */
	def void zipIt(String zipFile) {
		var byte[] buffer = newByteArrayOfSize(1024)
		try {
			var FileOutputStream fos = new FileOutputStream(zipFile)
			var ZipOutputStream zos = new ZipOutputStream(fos)
			System.out.println('''Output to Zip : «zipFile»''')
			for (String file : this.fileList) {
				System.out.println('''File Added : «file»''')
				var ZipEntry ze = new ZipEntry(file)
				zos.putNextEntry(ze)
				var FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file)
				var int len
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len)
				}
				in.close()
			}
			zos.closeEntry()
			// remember close it
			zos.close()
			System.out.println("Done")
		} catch (IOException ex) {
			ex.printStackTrace()
		}

	}

	/** 
	 * Traverse a directory and get all files,
	 * and add the file into fileList  
	 * @param node file or directory
	 */
	def void generateFileList(File node) {
		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()))
		}
		if (node.isDirectory()) {
			var String[] subNote = node.list()
			for (String filename : subNote) {
				generateFileList(new File(node, filename))
			}
		}
	}

	/** 
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	def public String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length())
	}

}
