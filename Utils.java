package Project;

public class Utils {
	
	public static void waitFor(long time) {
		try {
			Thread.sleep(time*1000);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
