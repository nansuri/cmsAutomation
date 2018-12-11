package core

import org.openqa.selenium.Platform

class PlatformPathSetup {

	public static Platform platform;
	public static String macPath = "./src/main/resources/config/config.properties";
	public static String windowsPath = ".\\src\\main\\resources\\config\\config.properties";

	/**
	 * Get current platform/OS in order to set the 'config properties' path
	 * @author Shameer
	 */
	def public static Platform getCurrentPlatform() {
		if (platform === null) {
			var String operSys = System.getProperty("os.name").toLowerCase()
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC
			}
		}
		return platform
	}

	/**
	 * Select the 'config properties' location based on platform
	 * @param platform Current platform name
	 * @author Shameer
	 */
	def public static String getReportFileLocation(Platform platform) {
		var String reportFileLocation = null

		switch (platform) {
			case MAC: {
				reportFileLocation = macPath
				System.out.println("Config Properties file Path for MAC: " + macPath + "\n")
			}
			case WINDOWS: {
				reportFileLocation = windowsPath
				System.out.println("Config Properties file Path for WINDOWS: " + windowsPath + "\n")
			}
			default: {
				System.out.println("Config Properties file Path has not been set! There is a problem!\n")
			}
		}
		return reportFileLocation
	}

}
