package core.customLocators.annotations

import core.customLocators.enums.How
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target(#[ElementType.FIELD, ElementType.TYPE])
public annotation SearchBy {
	How searchBy
	String value
}
