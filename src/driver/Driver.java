/**
 * 
 */
package driver;

import java.util.Collections;

import airport.Airport;
import airport.Airports;
import plane.Plane;
import plane.Planes;
import leg.Leg;
import leg.Legs;
import dao.ServerInterface;

/**
 * @author blake
 * @since 2016-02-24
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
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: CS509.sample teamName");
			System.exit(-1);
			return;
		}

		String teamName = args[0];
		// Try to get a list of airports
		//Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		//Collections.sort(airports);
		//for (Airport airport : airports) {
		//	System.out.println(airport.toString());
		//}
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
	}
}

