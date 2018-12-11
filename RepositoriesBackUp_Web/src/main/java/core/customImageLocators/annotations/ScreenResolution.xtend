package core.customImageLocators.annotations

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public annotation ScreenResolution {
	int width = 0
	int height = 0
}
