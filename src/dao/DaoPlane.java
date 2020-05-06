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

import plane.Plane;
import plane.Planes;

/**
 * Builds a collection of planes from planes described in XML
 *
 * Parses an XML string to read each of the planes and adds each valid plane
 * to the collection. The class uses Java DOM (Document Object Model) to convert
 * from XML to Java primitives.
 *
 * @author Kevin Martin
 * @version 1.0 2020-03-23
 * @since 2020-03-23
 *
 */

public class DaoPlane {
    /**
     *  Creates Plane objects from XML.
     *
     * Method iterates over the set of Plane nodes in the XML string and builds
     * a Plane object from the XML node string and adds the Plane object instance to
     * the Plane collection.
     *
     * @param xmlPlanes XML string containing set of planes
     * @return [possibly empty] collection of Planes in the xml string
     * @throws NullPointerException included to keep signature consistent with other addAll methods
     *
     * @pre the xmlAirports string adheres to the format specified by the server API
     * @post the [possibly empty] set of Planes in the XML string are added to collection
     */
    public static Planes addAll (String xmlPlanes) throws NullPointerException {
        Planes planes = new Planes();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each plane to our collection
        Document docPlanes = buildDomDoc (xmlPlanes);
        if (docPlanes != null) {
            NodeList nodesPlanes = docPlanes.getElementsByTagName("Airplane");

            for (int i = 0; i < nodesPlanes.getLength(); i++) {
                Element elementPlane = (Element) nodesPlanes.item(i);
                Plane plane = buildPlane(elementPlane);

                planes.add(plane);
            }
        }

        return planes;
    }

    /**
     * Creates a Plane object from a DOM node
     *
     * Processes a DOM Node that describes a Plane and creates a Plane object from the information
     * @param nodePlane is a DOM Node describing a plane
     * @return Plane object created from the DOM Node representation of the plane
     *
     * @pre nodePlane is of format specified by CS509 server API
     * @post Plane object instantiated. Caller responsible for deallocating memory.
     */
    static private Plane buildPlane (Node nodePlane) {
        String model;
        String manufacturer;
        int coachSeats;
        int firstClassSeats;

        // The plane element has attributes of model and manufacturer
        Element elementPlane = (Element) nodePlane;
        model = elementPlane.getAttributeNode("Model").getValue();
        manufacturer = elementPlane.getAttributeNode("Manufacturer").getValue();

        // The # of first class seats and # of coach seats are child elements
        Element elementSeat;
        elementSeat = (Element)elementPlane.getElementsByTagName("CoachSeats").item(0);
        coachSeats =  Integer.parseInt(getCharacterDataFromElement(elementSeat));

        elementSeat = (Element)elementPlane.getElementsByTagName("FirstClassSeats").item(0);
        firstClassSeats = Integer.parseInt(getCharacterDataFromElement(elementSeat));

        //Instantiate an empty Plane object and initialize with data from XML node
        Plane plane = new Plane();

        plane.model(model);
        plane.manufacturer(manufacturer);
        plane.coachSeats(coachSeats);
        plane.firstClassSeats(firstClassSeats);

        return plane;
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
        catch (ParserConfigurationException | IOException | SAXException e) {
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

