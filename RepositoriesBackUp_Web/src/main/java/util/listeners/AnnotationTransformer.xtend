package util.listeners

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import org.testng.IAnnotationTransformer
import org.testng.annotations.ITestAnnotation

class AnnotationTransformer implements IAnnotationTransformer {
	override void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor,
		Method testMethod) {
		annotation.setRetryAnalyzer(Retry)
	}
}
