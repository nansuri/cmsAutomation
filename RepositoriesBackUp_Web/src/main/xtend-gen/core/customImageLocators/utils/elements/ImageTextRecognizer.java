package core.customImageLocators.utils.elements;

import core.customImageLocators.utils.elements.ImageElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import javax.imageio.ImageIO;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.sikuli.script.TextRecognizer;

@SuppressWarnings("all")
public class ImageTextRecognizer {
  private final TextRecognizer textRecognizer = TextRecognizer.getInstance();
  
  /**
   * Recognize the given text is present in the given screen region.
   * @param text the text to be recognized.
   * @param topLeftX Top left corner x coordinate of the region.
   * @param topLeftY Top left corner y coordinate of the region.
   * @param width the width of the region.
   * @param height the height of the region.
   * @return {@code true} if text is recognized in this given screen region.
   */
  public boolean recognize(final String text, final int topLeftX, final int topLeftY, final int width, final int height) {
    boolean isTextPresent = false;
    try {
      Region region = new Region(topLeftX, topLeftY, width, height);
      Screen screen = new Screen();
      ScreenImage screenImg = screen.capture(region);
      String reconizedText = this.textRecognizer.recognize(screenImg);
      isTextPresent = reconizedText.contains(text);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception reconnizError = (Exception)_t;
        System.err.println(reconnizError.getMessage());
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return isTextPresent;
  }
  
  /**
   * Recognize the text presents in the given image.
   * @param element the image element you want to extract the text.
   * @return the {@code String} present in the image.
   */
  public String recognize(final ImageElement element) {
    String imagePath = element.getImage();
    String reconizedText = "";
    try {
      imagePath = URLDecoder.decode(imagePath, "UTF-8");
      File _file = new File(imagePath);
      BufferedImage image = ImageIO.read(_file);
      reconizedText = this.textRecognizer.recognizeWord(image);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException ioException = (IOException)_t;
        System.err.println(ioException.getMessage());
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return reconizedText;
  }
}
