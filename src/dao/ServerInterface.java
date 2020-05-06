package dao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;

import airport.Airport;
import airport.Airports;
import flight.Flights;
import plane.Planes;
import leg.Legs;
import utils.QueryFactory;
import utils.Saps;

/**
 * This class provides an interface to the CS509 server. It provides sample methods to perform
 * HTTP GET and HTTP POSTS
 *   
 * @author Blake Nelson
 * @version 1.0 2020-03-26
 * @since 2016-02-24
 *
 */
public enum ServerInterface {
	INSTANCE;

	/**
	 * Return a collection of all the airports from server
	 * 
	 * Retrieve the list of airports via HTTPGet of the server
	 *
	 * @return collection of Airports from server or null if error.
	 */
	public Airports getAirports () {

		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder result = new StringBuilder();
		
		String xmlAirports;
		Airports airports;

		try {
			// QueryFactory provides the parameter annotations for the HTTP GET query string
			url = new URL(Saps.SERVER_URL + QueryFactory.getAirportsQuery());

			// Create an HTTP connection to the server for a GET
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			// If response code of SUCCESS read the XML string returned
			// line by line to build the full return string
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlAirports = result.toString();
		airports = DaoAirport.addAll(xmlAirports);
		return airports;
	}

	/**
	 * Return a collection of all the planes from server
	 *
	 * Retrieve the list of planes via HTTPGet of the server
	 *
	 * @return collection of Planes from server or null if error.
	 */
	public Planes getPlanes () {

		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder result = new StringBuilder();

		String xmlPlanes;
		Planes planes;

		try {
			// QueryFactory provides the parameter annotations for the HTTP GET query string
			url = new URL(Saps.SERVER_URL + QueryFactory.getPlanesQuery());

			// Create an HTTP connection to the server for a GET
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			//If response code of SUCCESS read the XML string returned
			// line by line to build the full return string
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlPlanes = result.toString();
		planes = DaoPlane.addAll(xmlPlanes);
		return planes;
	}

	/**
	 * Return a collection of legs from server which board at a given airport and date
	 *
	 * Retrieve the list of legs via HTTPGet of the server
	 *
	 * @param boardingAirport the boarding Airport for requested legs
	 * @param boardingDate the date of boarding for requested legs
	 * @return collection of Legs from server or null if error.
	 */
	public Legs getBoardingLegs(Airport boardingAirport, LocalDate boardingDate) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder result = new StringBuilder();

		String xmlBoardingLegs;
		Legs boardingLegs;

		try {
			// QueryFactory provides the parameter annotations for the HTTP GET query string
			url = new URL(Saps.SERVER_URL + QueryFactory.getBoardingLegsQuery(boardingAirport, boardingDate));

			// Create an HTTP connection to the server for a GET
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			//If response code of SUCCESS read the XML string returned
			//line by line to build the full return string
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlBoardingLegs = result.toString();
		boardingLegs = DaoLeg.addAll(xmlBoardingLegs);
		return boardingLegs;
	}

	/**
	 * Return a collection of legs from server which disembark at a given airport and date
	 *
	 * Retrieve the list of legs via HTTPGet of the server
	 *
	 * @param disembarkingAirport the disembarking Airport for requested legs
	 * @param disembarkingDate the date of disembarking for requested legs
	 * @return collection of Legs from server or null if error.
	 */
	public Legs getDisembarkingLegs(Airport disembarkingAirport, LocalDate disembarkingDate) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuilder result = new StringBuilder();

		String xmlDisembarkingLegs;
		Legs disembarkingLegs;

		try {
			// QueryFactory provides the parameter annotations for the HTTP GET query string
			url = new URL(Saps.SERVER_URL + QueryFactory.getDisembarkingLegsQuery(disembarkingAirport, disembarkingDate));

			//Create an HTTP connection to the server for a GET
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			//If response code of SUCCESS read the XML string returned
			//line by line to build the full return string
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlDisembarkingLegs = result.toString();
		disembarkingLegs = DaoLeg.addAll(xmlDisembarkingLegs);
		return disembarkingLegs;
	}

	/**
	 * Reserves seats on the server database for a given flight.
	 *
	 * Retrieve the list of legs via HTTPGet of the server
	 *
	 * @param flights the flight on which seats should be reserved
	 * @param numberOfPassengers the number of seats that should be reserved
	 * @return true if the reservation was successful, false otherwise
	 */
	public boolean postLegReservation(Flights flights, int numberOfPassengers) {
		URL url;
		HttpURLConnection connection;

		try {
			//Create an HTTP connection to the server for a POST
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// ConvertFlightsToXML provides the flights parameter for the HTTP Post query string
			String xmlFlights = ConvertFlightsToXML.buildPostXML(flights, numberOfPassengers);
			// QueryFactory provides the parameter annotations for the HTTP Post query string
			String params = QueryFactory.postLegReservation(xmlFlights);

			// Send the HTTP POST query
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			// Get and display the server response
			int responseCode = connection.getResponseCode();
			System.out.println("Sending 'POST' to post reservation to database");
			System.out.println(("Response Code : " + responseCode));

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();

			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;

	}
	/**
	 * Lock the database for updating by the specified team. The operation will fail if the lock is held by another team.
	 * 
	 * @post database locked
	 *
	 * @return true if the server was locked successfully, else false
	 */
	public boolean lock () {
		URL url;
		HttpURLConnection connection;

		try {
			//Create an HTTP connection to the server for a POST
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// QueryFactory provides the parameter annotations for the HTTP Post query string
			String params = QueryFactory.lock();

			// Send the HTTP POST query
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			// Get and display the server response
			int responseCode = connection.getResponseCode();
			System.out.println("Sending 'POST' to lock database");
			System.out.println(("Response Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Unlock the database previous locked by specified team. The operation will succeed if the server lock is held by the specified
	 * team or if the server is not currently locked. If the lock is held be another team, the operation will fail.
	 * 
	 * The server interface to unlock the server interface uses HTTP POST protocol
	 * 
	 * @post database unlocked if specified teamName was previously holding lock
	 *
	 * @return true if the server was successfully unlocked.
	 */
	public boolean unlock () {
		URL url;
		HttpURLConnection connection;
		
		try {
			//Create an HTTP connection to the server for a POST
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			// QueryFactory provides the parameter annotations for the HTTP Post query string
			String params = QueryFactory.unlock();

			// Send the HTTP POST query
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			// Get and display the server response
			int responseCode = connection.getResponseCode();
			System.out.println("Sending 'POST' to unlock database");
			System.out.println(("Response Code : " + responseCode));

			if (responseCode >= HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Reset the database to its original state, removing reservations added during testing
	 *
	 * The server interface to reset the server interface uses HTTP POST protocol
	 *
	 * @post database is in its original state
	 *
	 * @return true if the server was successfully reset, false otherwise
	 * */
	public boolean reset () {
		URL url;
		HttpURLConnection connection;

		try {
			//Create an HTTP connection to the server for a POST
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			// QueryFactory provides the parameter annotations for the HTTP Post query string
			String params = QueryFactory.reset();

			// Send the HTTP POST query
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			// Get and display the server response
			int responseCode = connection.getResponseCode();
			System.out.println("Sending 'POST' to reset database");
			System.out.println(("\nResponse Code : " + responseCode));

			if (responseCode >= HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
