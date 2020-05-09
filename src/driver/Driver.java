package driver;

/**
 * Entry point for the ReservationSystem
 *
 * @author Kevin Martin
 * @since 2020-04-30
 * @version 1.0
 *
 */
public class Driver {


	/**
	 * Entry point for the ReservationSystem
	 *
	 * @param args main thread initialization arguments
	 */
	public static void main(String[] args) {
		TripBuilder.getInstance().generateGUI();
	}
}

