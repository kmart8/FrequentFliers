/**
 * 
 */
package utils;

import airport.Airport;

import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author blake
 * @version 1.2
 * @since 2016-02-24
 *
 * Formats arguments into query strings that can be passed to HTTP URL to interact with the server
 */
public class QueryFactory {

	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 *
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirportsQuery() {
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=airports";
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of planes
	 *
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getPlanesQuery() {
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=airplanes";
	}

	/**
	 * Return query string that can be passed to HTTP URL to request list of legs
	 *
	 * @param boardingAirport is the airport
	 * @param boardingDate    is the the flight departure date of type ZonedDateTime
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getBoardingLegsQuery(Airport boardingAirport, LocalDate boardingDate) {
		// DateTimeFormatter
		DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("yyyy_MM_dd");

		// Converting ZonedDateTime to string query can understand
		String departureDateString = dateStyle.format(boardingDate);
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=departing&airport=" + boardingAirport.code() + "&day=" + departureDateString;
	}

	public static String getDisembarkingLegsQuery(Airport disembarkingAirport, LocalDate disembarkingDate) {
		//DateTimeFormatter
		DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("yyyy_MM_dd");

		// Converting ZonedDateTime to string query can understand
		String disembarkingDateString = dateStyle.format(disembarkingDate);
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=arriving&airport=" + disembarkingAirport.code() + "&day=" + disembarkingDateString;
	}

	/**
	 * Return query string that can be passed to HTTP URL to lock the server database so updates can be written
	 *
	 * @return the String written to HTTP POST to lock server database
	 */
	public static String lock() {
		return "team=" + Saps.TEAM_NAME + "&action=lockDB";
	}

	/**
	 * Return query string that can be passed to HTTP URL to unlock the server database after updates are written
	 *
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock() {
		return "team=" + Saps.TEAM_NAME + "&action=unlockDB";
	}

	public static String postLegReservation(String xmlFlights) { return "team=" + Saps.TEAM_NAME + "&action=buyTickets&flightData=" + xmlFlights; }

	public static String reset() { return "?team="+ Saps.TEAM_NAME +" &action=resetDB";}
}
