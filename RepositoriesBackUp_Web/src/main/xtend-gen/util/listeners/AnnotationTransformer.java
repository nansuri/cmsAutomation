package util.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import util.listeners.Retry;

@SuppressWarnings("all")
public class AnnotationTransformer implements IAnnotationTransformer {
  @Override
  public void transform(final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor, final Method testMethod) {
    annotation.setRetryAnalyzer(Retry.class);
  }
}
