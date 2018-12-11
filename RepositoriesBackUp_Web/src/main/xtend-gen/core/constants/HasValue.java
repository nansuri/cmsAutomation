package core.constants;

import com.google.common.collect.ImmutableList;
import java.util.List;

@SuppressWarnings("all")
public interface HasValue<T extends Object> {
  public static class Builder {
    public static <T extends Object> List<T> build(final HasValue<T>[] values) {
      ImmutableList.Builder<T> def = ImmutableList.<T>builder();
      for (final HasValue<T> v : values) {
        def.add(v.value());
      }
      return def.build();
    }
  }
  
  public abstract T value();
}
