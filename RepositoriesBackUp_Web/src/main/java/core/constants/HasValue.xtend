package core.constants

import com.google.common.collect.ImmutableList
import java.util.List

interface HasValue<T> {
	def T value()

	static class Builder {
		def static <T> List<T> build(HasValue<T>[] values) {
			var ImmutableList.Builder<T> ^def = ImmutableList.builder()
			for (HasValue<T> v : values) {
				^def.add(v.value())
			}
			return ^def.build()
		}
	}
}
