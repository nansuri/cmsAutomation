package core.customImageLocators.utils.elements

import com.google.common.base.Optional
import java.lang.reflect.Field
import org.sikuli.script.Pattern

interface ImageElement {
	def boolean click()

	def boolean rightclick()

	def boolean doubleclick()

	def boolean type(String input)

	def boolean type(String input, int modifiers)

	def boolean dragAndDrop(ImageElement destination)

	def boolean mouseMove()

	def boolean mouseMove(int x, int y)

	def void mouseDown(int mouseButton)

	def void mouseUp(int mouseButton)

	def ImageElement getElement()

	def void waitFor(long time)

	def boolean waitVanish(long timeout)

	def String getImage()

	def Pattern getPattern()

	def Optional<Field> getField()

	def boolean exists()

	def boolean exists(double timeout)

}
