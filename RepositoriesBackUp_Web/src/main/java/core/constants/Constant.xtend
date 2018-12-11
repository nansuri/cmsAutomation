package core.constants

final class Constant implements HasValue<String>{
	final String value
	private  new(String value) {
		this.value=value 
	}
	def static Constant valueOf(String value) {
		return new Constant(value) 
	}
	override String value() {
		return value 
	}
}