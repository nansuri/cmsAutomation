package core.customImageLocators.utils.elements

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.net.URLDecoder
import javax.imageio.ImageIO
import net.sourceforge.tess4j.Tesseract
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.sikuli.script.Region
import org.sikuli.script.Screen
import org.sikuli.script.ScreenImage
import org.sikuli.script.TextRecognizer
import org.testng.annotations.Test

class ImageTextRecognizer {
	 final TextRecognizer textRecognizer = TextRecognizer.getInstance()

	/** 
	 * Recognize the given text is present in the given screen region. 
	 * @param text the text to be recognized.
	 * @param topLeftX Top left corner x coordinate of the region.
	 * @param topLeftY Top left corner y coordinate of the region.
	 * @param width the width of the region.
	 * @param height the height of the region.
	 * @return {@code true} if text is recognized in this given screen region.
	 */
	def boolean recognize(String text, int topLeftX, int topLeftY, int width, int height) {
		var boolean isTextPresent = false
		try {
			var Region region = new Region(topLeftX, topLeftY, width, height)
			var Screen screen = new Screen()
			var ScreenImage screenImg = screen.capture(region)
			var String reconizedText = textRecognizer.recognize(screenImg)
			isTextPresent = reconizedText.contains(text)
		} catch (Exception reconnizError) {
			System.err.println(reconnizError.getMessage())

		}

		return isTextPresent
	}

	/** 
	 * Recognize the text presents in the given image.
	 * @param element the image element you want to extract the text.
	 * @return the {@code String} present in the image.
	 */
	def String recognize(ImageElement element) {
		var String imagePath = element.getImage()
		var String reconizedText = ""
		try {
			imagePath = URLDecoder.decode(imagePath, "UTF-8")
			var BufferedImage image = ImageIO.read(new File(imagePath))
			reconizedText = textRecognizer.recognizeWord(image)
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage())

		}

		return reconizedText
	}
	

}
