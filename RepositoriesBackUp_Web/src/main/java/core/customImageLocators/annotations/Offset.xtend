package core.customImageLocators.annotations

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public annotation Offset {
	int x = 0
	int y = 0
}
