package dao;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import airport.Airport;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import leg.Leg;
import leg.Legs;
import plane.Plane;

/**
 * Builds a collection of legs from legs described in XML.
 *
 * Parses an XML string to read each of the legs and adds each valid leg
 * to the collection. The class uses Java DOM (Document Object Model) to convert
 * from XML to Java primitives.
 *
 * @author Kevin Martin
 * @version 1.0 2019-01-21
 * @since 2020-03-23
 *
 */
public class DaoLeg {
    /**
     *  Creates Leg objects from XML.
     *
     * Method iterates over the set of Leg nodes in the XML string and builds
     * a Leg object from the XML node string and adds the Leg object instance to
     * the Leg collection.
     *
     * @param xmlLegs XML string containing set of legs
     * @return [possibly empty] collection of Legs in the xml string
     * @throws NullPointerException included to keep signature consistent with other addAll methods
     *
     * @pre the xmlAirports string adheres to the format specified by the server API
     * @post the [possibly empty] set of Planes in the XML string are added to collection
     */
    public static Legs addAll (String xmlLegs) throws NullPointerException {
        Legs legs = new Legs();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each Leg to our collection
        Document docLegs = buildDomDoc(xmlLegs);
        if (docLegs != null){
            NodeList nodesLegs = docLegs.getElementsByTagName("Flight");

            for (int i = 0; i < nodesLegs.getLength(); i++) {
                Element elementLeg = (Element) nodesLegs.item(i);
                Leg leg = buildLeg(elementLeg);

                legs.add(leg);
            }
        }

        return legs;
    }

    /**
     * Creates a Leg object from a DOM node
     *
     * Processes a DOM Node that describes a leg and creates a Leg object from the information
     * @param nodeLeg is a DOM Node describing a leg
     * @return Leg object created from the DOM Node representation of the leg
     *
     * @pre nodeLeg is of format specified by CS509 server API
     * @post Leg object instantiated. Caller responsible for deallocating memory.
     */
    static private Leg buildLeg (Node nodeLeg) {
        DateTimeFormatter serverDateTimeStyle = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm zzz");
        ZoneId gmt = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0));
        NumberFormat serverPriceStyle = NumberFormat.getCurrencyInstance(Locale.US);
        Airport disembarkingAirport;
        Airport boardingAirport;
        ZonedDateTime disembarkingTime;
        ZonedDateTime boardingTime;
        int flightNumber;
        int reservedCoachSeats;
        int reservedFirstClassSeats;
        Duration legDuration;
        Plane plane;
        BigDecimal coachPrice;
        BigDecimal firstClassPrice;

        // The leg element has attributes of plane model, flight time, and flight number
        Element elementLeg = (Element) nodeLeg;
        plane = LocalFlightDatabase.getInstance().getPlaneFromModel(elementLeg.getAttributeNode("Airplane").getValue());
        legDuration = Duration.ofMinutes(Integer.parseInt(elementLeg.getAttributeNode("FlightTime").getValue()));
        flightNumber = Integer.parseInt(elementLeg.getAttributeNode("Number").getValue());

        // The arrival element has child elements code and time
        Element elementDisembarking = (Element)elementLeg.getElementsByTagName("Arrival").item(0);
        Element elementDisembarkingAirportCode = (Element)elementDisembarking.getElementsByTagName("Code").item(0);
        Element elementDisembarkingTime = (Element)elementDisembarking.getElementsByTagName("Time").item(0);
        disembarkingAirport = LocalFlightDatabase.getInstance().getAirportFromString(getCharacterDataFromElement(elementDisembarkingAirportCode));
        disembarkingTime = ZonedDateTime.of(LocalDateTime.parse(getCharacterDataFromElement(elementDisembarkingTime), serverDateTimeStyle), gmt);

        // The departure element has child elements code and time
        Element elementBoarding = (Element)elementLeg.getElementsByTagName("Departure").item(0);
        Element elementBoardingAirportCode = (Element)elementBoarding.getElementsByTagName("Code").item(0);
        Element elementBoardingTime = (Element)elementBoarding.getElementsByTagName("Time").item(0);
        boardingAirport = LocalFlightDatabase.getInstance().getAirportFromString(getCharacterDataFromElement(elementBoardingAirportCode));
        boardingTime = ZonedDateTime.of(LocalDateTime.parse(getCharacterDataFromElement(elementBoardingTime), serverDateTimeStyle), gmt);

        // The seating element has child elements coach and first class
        Element elementSeating = (Element)elementLeg.getElementsByTagName("Seating").item(0);
        Element elementCoach = (Element)elementSeating.getElementsByTagName("Coach").item(0);
        Element elementFirstClass = (Element)elementSeating.getElementsByTagName("FirstClass").item(0);
        reservedCoachSeats = Integer.parseInt(getCharacterDataFromElement(elementCoach));
        reservedFirstClassSeats = Integer.parseInt(getCharacterDataFromElement(elementFirstClass));

        // The coach element has attribute price
        try{
            coachPrice = new BigDecimal(serverPriceStyle.parse(elementCoach.getAttributeNode("Price").getValue()).toString());
        }
        catch(ParseException pe){ coachPrice = new BigDecimal(0); }

        // The first class element has attribute price
        try{
            firstClassPrice = new BigDecimal(serverPriceStyle.parse(elementFirstClass.getAttributeNode("Price").getValue()).toString());
        }
        catch(ParseException pe){ firstClassPrice = new BigDecimal(0); }

        // Instantiate an empty Plane object and initialize with data from XML node
        Leg leg = new Leg();

        leg.setDisembarkingAirport(disembarkingAirport);
        leg.setBoardingAirport(boardingAirport);
        leg.setDisembarkingTime(disembarkingTime);
        leg.setBoardingTime(boardingTime);
        leg.setFlightNumber(flightNumber);
        leg.setPlane(plane);
        leg.setLegDuration(legDuration);
        leg.setCoachPrice(coachPrice);
        leg.setFirstClassPrice(firstClassPrice);
        leg.setReservedCoachSeats(reservedCoachSeats);
        leg.setReservedFirstClassSeats(reservedFirstClassSeats);

        return leg;
    }

    /**
     * Builds a DOM tree from an XML string
     *
     * Parses the XML file and returns a DOM tree that can be processed
     *
     * @param xmlString XML String containing set of objects
     * @return DOM tree from parsed XML or null if exception is caught
     */
    static private Document buildDomDoc (String xmlString) {
        // load the xml string into a DOM document and return the Document
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlString));

            return docBuilder.parse(inputSource);
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve character data from an element if it exists
     *
     * @param e is the DOM Element to retrieve character data from
     * @return the character data as String [possibly empty String]
     */
    private static String getCharacterDataFromElement (Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
