/**
 * 
 */
package driver;

import dao.ServerInterface;
import airport.Airports;
import airport.Airport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

import java.util.Hashtable;
import java.util.TimeZone;

/**
 * @author Kevin Martin
 * @since 2020-04-30
 * @version 1.0
 *
 */
public class Driver {


	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and print the list 
	 * to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
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
		FlightBuilder.getInstance().generateGUI();

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

