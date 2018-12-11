package core.customImageLocators.annotations;

import core.customImageLocators.annotations.Offset;
import core.customImageLocators.annotations.ScreenResolution;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface FindByImage {
  public String value();
  public Offset offset() default @Offset;
  public ScreenResolution resolution() default @ScreenResolution;
}
