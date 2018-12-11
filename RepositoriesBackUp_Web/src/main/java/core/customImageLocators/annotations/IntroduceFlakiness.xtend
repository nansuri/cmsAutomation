package core.customImageLocators.annotations

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public annotation IntroduceFlakiness {

	float by
	String reason
}
