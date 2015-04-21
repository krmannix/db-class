package photoshare;

import java.util.UUID;

public class SmallHelpers {
	public static String genID() {
		return UUID.randomUUID().toString();
	}
}