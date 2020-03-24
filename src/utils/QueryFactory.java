/**
 * 
 */
package utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author blake
 * @version 1.2
 * @since 2016-02-24
 *
 */
public class QueryFactory {

	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 * 
	 * @param teamName is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirports(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airports";
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of planes
	 * @param teamName
	 * @return
	 */
	public static String getPlanes(String teamName) { return "?team=" + teamName + "&action=list&list_type=airplanes";}

	/**
	 * Return query string that can be passed to HTTP URL to request list of legs
	 * @param teamName
	 * @param code is the airport code
	 * @param zdtDisembarkingTime is the the flight departure date of type ZonedDateTime
	 * @return
	 */

	public static String getLegs (String teamName, String code, ZonedDateTime zdtDisembarkingTime) {

		// DateTimeFormatter
		DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("yyyy_MM_dd");

		// Converting ZonedDateTime to string query can understand
		String disembarkingTime = dateStyle.format(zdtDisembarkingTime);
		System.out.println("?team=" + teamName + "&action=list&list_type=departing&airport="+code+"&day="+disembarkingTime);
		return "?team=" + teamName + "&action=list&list_type=departing&airport="+code+"&day="+disembarkingTime;

	}
	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database 
	 */
	public static String lock (String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}
	
	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock (String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}
	

}
