/**
 * 
 */
package driver;

import java.text.ParseException;

/**
 * @author Kevin Martin
 * @since 2020-04-30
 * @version 1.0
 *
 */
public class Driver {


	/**
	 * Entry point for the ReservationSystem
	 *
	 */
	public static void main(String[] args) throws ParseException {
		/**if (args.length != 1) {
			System.err.println("usage: CS509.sample teamName");
			System.exit(-1);
			return;
		}
		// XML PARSE TESTS //
		String teamName = args[0];
		// Try to get a list of airports
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}
		// Try to get a list of planes
		//Planes planes = ServerInterface.INSTANCE.getPlanes(teamName);
		//for (Plane plane : planes) {
		//System.out.println(plane.toString());
	    //}
		// Try to get a list of legs departing from Boston on May 20 2020
		Legs legs = ServerInterface.INSTANCE.getDepartingLegs(teamName);
		for (Leg leg : legs) {
			System.out.println(leg.toString());
		}
		 */
		TripBuilder.getInstance().generateGUI();

		// GMT TO LOCAL TIME CONVERSION TEST //
		// arbitrary GMT time input
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 12, 3, 12, 20, 59, 90000, ZoneId.of("GMT"));
//		Airports airports = ServerInterface.INSTANCE.getAirports();
//		Collections.sort(airports);
//		for (Airport airport : airports) {
//			System.out.println(airport.toString());
//			System.out.println(airport.convertGMTtoLocalTime(zonedDateTime));
//		}
	}
}

