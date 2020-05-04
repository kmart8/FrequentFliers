/**
 * 
 */
package dao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;

import airport.Airport;
import airport.Airports;
import flight.Flight;
import leg.Leg;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import plane.Planes;
import leg.Legs;
import utils.QueryFactory;
import utils.Saps;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class provides an interface to the CS509 server. It provides sample methods to perform
 * HTTP GET and HTTP POSTS
 *   
 * @author Kevin Martin
 * @version 1.0
 * @since 2020-05-01
 *
 */
public enum ServerInterface {
	INSTANCE;

	/**
	 * Return a collection of all the airports from server
	 * 
	 * Retrieve the list of airports available to the specified teamName via HTTPGet of the server
	 *
	 * @return collection of Airports from server or null if error.
	 */
	public Airports getAirports () {

		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
		String xmlAirports;
		Airports airports;

		try {
			/**
			 * Create an HTTP connection to the server for a GET 
			 * QueryFactory provides the parameter annotations for the HTTP GET query string
			 */
			url = new URL(Saps.SERVER_URL + QueryFactory.getAirportsQuery());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlAirports = result.toString();
		airports = DaoAirport.addAll(xmlAirports);
		return airports;
		
	}

	public Planes getPlanes () {

		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlPlanes;
		Planes planes;

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 * QueryFactory provides the parameter annotations for the HTTP GET query string
			 */
			url = new URL(Saps.SERVER_URL + QueryFactory.getPlanesQuery());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlPlanes = result.toString();
		planes = DaoPlane.addAll(xmlPlanes);
		return planes;

	}

	public Legs getBoardingLegs(Airport boardingAirport, LocalDate boardingDate) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlBoardingLegs;
		Legs boardingLegs;

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 * QueryFactory provides the parameter annotations for the HTTP GET query string
			 */

			url = new URL(Saps.SERVER_URL + QueryFactory.getBoardingLegsQuery(boardingAirport, boardingDate));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlBoardingLegs = result.toString();
		boardingLegs = DaoLeg.addAll(xmlBoardingLegs);
		return boardingLegs;
	}

	public Legs getDisembarkingLegs(Airport disembarkingAirport, LocalDate disembarkingDate) {
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		String xmlDisembarkingLegs;
		Legs disembarkingLegs;

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 * QueryFactory provides the parameter annotations for the HTTP GET query string
			 */

			url = new URL(Saps.SERVER_URL + QueryFactory.getDisembarkingLegsQuery(disembarkingAirport, disembarkingDate));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		xmlDisembarkingLegs = result.toString();
		disembarkingLegs = DaoLeg.addAll(xmlDisembarkingLegs);
		return disembarkingLegs;
	}


	public boolean postLegReservation(Flight flight) {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String xmlFlights = buildPostXML(flight);
			String params = QueryFactory.postLegReservation(xmlFlights);

			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to post reservation to database");
			System.out.println(("\nResponse Code : " + responseCode));

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();

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
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.lock();
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to lock database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
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
			url = new URL(Saps.SERVER_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			
			String params = QueryFactory.unlock();
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
		    
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to unlock database");
			System.out.println(("\nResponse Code : " + responseCode));

			if (responseCode >= HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public String buildPostXML(Flight flight) {

		Document document = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		}catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}

		assert document != null;
		Element root = document.createElement("Flights");
		document.appendChild(root);

		String seatingType = flight.seatingType();
		Legs legList = flight.getLegList();

		for (Leg leg : legList) {
			// add child element
			Node legNode = createLegNode(document,seatingType,leg);
			root.appendChild(legNode);
		}
		// convert document to string
		try {

			// create DOMSource for source XML document
			Source xmlSource = new DOMSource(document);

			// create StreamResult for transformation result
			//Result result = new StreamResult(new FileOutputStream("post.xml"));

			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			//transformer.setOutputProperty("indent", "yes");

			StringWriter writer = new StringWriter();

			//transform document to string
			transformer.transform(new DOMSource(document), new StreamResult(writer));

			String xmlString = writer.getBuffer().toString();
			System.out.println(xmlString);

			return xmlString;
		}

		// handle exception creating TransformerFactory
		catch (TransformerFactoryConfigurationError factoryError) {
			System.err.println("Error creating " + "TransformerFactory");
			factoryError.printStackTrace();
		}catch (TransformerException transformerError) {
			System.err.println("Error transforming document");
			transformerError.printStackTrace();
		}
		return null;
	}

	public Node createLegNode(Document document, String seatingType, Leg leg) {

		// create contact element
		Element legNode = document.createElement("Flight");

		// create attribute
		Attr flightNumberAttribute = document.createAttribute("number");
		Attr seatingAttribute = document.createAttribute("seating");
		flightNumberAttribute.setValue(Integer.toString(leg.getFlightNumber()));
		seatingAttribute.setValue(seatingType);


		// append attribute to contact element
		legNode.setAttributeNode(flightNumberAttribute);
		legNode.setAttributeNode(seatingAttribute);

		return legNode;
	}
}
