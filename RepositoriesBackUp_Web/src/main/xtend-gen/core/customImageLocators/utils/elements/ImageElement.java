package core.customImageLocators.utils.elements;

import com.google.common.base.Optional;
import java.lang.reflect.Field;
import org.sikuli.script.Pattern;

@SuppressWarnings("all")
public interface ImageElement {
  public abstract boolean click();
  
  public abstract boolean rightclick();
  
  public abstract boolean doubleclick();
  
  public abstract boolean type(final String input);
  
  public abstract boolean type(final String input, final int modifiers);
  
  public abstract boolean dragAndDrop(final ImageElement destination);
  
  public abstract boolean mouseMove();
  
  public abstract boolean mouseMove(final int x, final int y);
  
  public abstract void mouseDown(final int mouseButton);
  
  public abstract void mouseUp(final int mouseButton);
  
  public abstract ImageElement getElement();
  
  public abstract void waitFor(final long time);
  
  public abstract boolean waitVanish(final long timeout);
  
  public abstract String getImage();
  
  public abstract Pattern getPattern();
  
  public abstract Optional<Field> getField();
  
  public abstract boolean exists();
  
  public abstract boolean exists(final double timeout);
}
