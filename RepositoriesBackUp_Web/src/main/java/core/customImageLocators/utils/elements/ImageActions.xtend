package core.customImageLocators.utils.elements

class ImageActions {
		
		

		/**
	 * Recognize the given text is present in the given screen region.
	 * 
	 * @param text the text to be recognized.
	 * @param topLeftX Top left corner x coordinate of the region.
	 * @param topLeftY Top left corner y coordinate of the region.
	 * @param width the width of the region.
	 * @param height the height of the region.
	 * @return {@code true} if text is recognized in this given screen region.
	 */
	def public static boolean assertText(String text, int topLeftX, int topLeftY, int width, int height) {
		return new ImageTextRecognizer().recognize(text, topLeftX, topLeftY, width, height);
	}

	/**
	 * Recognize the text presents in the given image.
	 * 
	 * @param imageElement the image element you want to extract the text.
	 * @return the {@code String} present in the image.
	 */
	def public static String recognizeTextFrom(ImageElement imageElement) {
		return new ImageTextRecognizer().recognize(imageElement);
	}
	
}