package dao;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import leg.Leg;
import leg.Legs;

public class DaoLeg {
    public static Legs addAll (String xmlLegs) throws NullPointerException {
        Legs legs = new Legs();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each Leg to our collection
        Document docLegs = buildDomDoc (xmlLegs);
        NodeList nodesLegs = docLegs.getElementsByTagName("Flight");

        for (int i = 0; i < nodesLegs.getLength(); i++) {
            Element elementLeg = (Element) nodesLegs.item(i);
            Leg leg = buildLeg (elementLeg);


            legs.add(leg);

        }

        return legs;
    }

    /**
     * Creates a Leg object from a DOM node
     *
     * Processes a DOM Node that describes a Leg and creates a Leg object from the information
     * @param nodeLeg is a DOM Node describing an Plane
     * @return Plane object created from the DOM Node representation of the Plane
     *
     * @pre nodePlane is of format specified by CS509 server API
     * @post plane object instantiated. Caller responsible for deallocating memory.
     */
    static private Leg buildLeg (Node nodeLeg) {
        String disembarkingAirport;
        String boardingAirport;
        String disembarkingTime;
        float boardingTime;
        int flightNumber;
        int reservedCoachSeats;
        int reservedFirstClassSeats;
        float legDuration;
        String plane; // the plane model
        float coachPrice;
        float firstClassPrice;
        String flightTime;

        // The plane element has attributes of model and manufacturer
        Element elementLeg = (Element) nodeLeg;
        plane = elementLeg.getAttributeNode("Airplane").getValue();
        flightTime = elementLeg.getAttributeNode("FlightTime").getValue();
        flightNumber = Integer.parseInt(elementLeg.getAttributeNode("Number").getValue());

        // The # of first class seats and # of coach seats are child elements
        Element elementDeparture;
        elementDeparture = (Element)elementLeg.getElementsByTagName("Departure").item(0);

        Element elementCdTm;
        elementCdTm = (Element)elementDeparture.getElementsByTagName("Code").item(0);
        disembarkingAirport = getCharacterDataFromElement(elementCdTm);

        elementCdTm = (Element)elementDeparture.getElementsByTagName("Time").item(0);
        disembarkingTime = getCharacterDataFromElement(elementCdTm);

        /**
         * Instantiate an empty Plane object and initialize with data from XML node
         */
        Leg leg = new Leg();

        leg.disembarkingAirport = disembarkingAirport;
        leg.disembarkingTime = disembarkingTime;
        leg.flightNumber = flightNumber;
        leg.plane = plane;
        leg.flightTime = flightTime;

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
        /**
         * load the xml string into a DOM document and return the Document
         */
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlString));

            return docBuilder.parse(inputSource);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (SAXException e) {
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
