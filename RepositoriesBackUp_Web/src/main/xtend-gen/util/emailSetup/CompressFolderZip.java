package util.emailSetup;

import constants.EmailSetup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Send the report through email
 * @author mketheeswaran
 */
@SuppressWarnings("all")
public class CompressFolderZip {
  List<String> fileList;
  
  private final static String SOURCE_FOLDER = EmailSetup.SOURCE_FOLDER.value();
  
  public CompressFolderZip() {
    ArrayList<String> _arrayList = new ArrayList<String>();
    this.fileList = _arrayList;
  }
  
  /**
   * Zip it
   * @param zipFile output ZIP file location
   */
  public void zipIt(final String zipFile) {
    byte[] buffer = new byte[1024];
    try {
      FileOutputStream fos = new FileOutputStream(zipFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Output to Zip : ");
      _builder.append(zipFile);
      System.out.println(_builder);
      for (final String file : this.fileList) {
        {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("File Added : ");
          _builder_1.append(file);
          System.out.println(_builder_1);
          ZipEntry ze = new ZipEntry(file);
          zos.putNextEntry(ze);
          FileInputStream in = new FileInputStream(((CompressFolderZip.SOURCE_FOLDER + File.separator) + file));
          int len = 0;
          while (((len = in.read(buffer)) > 0)) {
            zos.write(buffer, 0, len);
          }
          in.close();
        }
      }
      zos.closeEntry();
      zos.close();
      System.out.println("Done");
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException ex = (IOException)_t;
        ex.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Traverse a directory and get all files,
   * and add the file into fileList
   * @param node file or directory
   */
  public void generateFileList(final File node) {
    boolean _isFile = node.isFile();
    if (_isFile) {
      this.fileList.add(this.generateZipEntry(node.getAbsoluteFile().toString()));
    }
    boolean _isDirectory = node.isDirectory();
    if (_isDirectory) {
      String[] subNote = node.list();
      for (final String filename : subNote) {
        File _file = new File(node, filename);
        this.generateFileList(_file);
      }
    }
  }
  
  /**
   * Format the file path for zip
   * @param file file path
   * @return Formatted file path
   */
  public String generateZipEntry(final String file) {
    int _length = CompressFolderZip.SOURCE_FOLDER.length();
    int _plus = (_length + 1);
    return file.substring(_plus, file.length());
  }
}
