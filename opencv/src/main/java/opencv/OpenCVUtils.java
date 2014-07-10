package opencv;

import org.opencv.core.Core;

public class OpenCVUtils {
	public static void loadLibrary() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
