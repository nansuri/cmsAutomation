package core.customLocators.utils.common

final class StringUtils {
	private new() {
	}

	def static String replaceWithValues(String target, String replaceToken, String... values) {
		return String.format(target.replace("%", "%%").replace(replaceToken, "%s"), (values as Object[]))
	}
}
