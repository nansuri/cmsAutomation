package core.constants;

import core.constants.HasValue;

@SuppressWarnings("all")
public final class Constant implements HasValue<String> {
  private final String value;
  
  private Constant(final String value) {
    this.value = value;
  }
  
  public static Constant valueOf(final String value) {
    return new Constant(value);
  }
  
  @Override
  public String value() {
    return this.value;
  }
}
