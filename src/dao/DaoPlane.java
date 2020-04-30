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
 * @author Kevin Martin
 * @version 1.1 2019-01-21
 * @since 2020-04-30
 *
 */

public class DaoPlane {
    public static Planes addAll (String xmlPlanes) throws NullPointerException {
        Planes planes = new Planes();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each airport to our collection
        Document docPlanes = buildDomDoc (xmlPlanes);
        NodeList nodesPlanes = docPlanes.getElementsByTagName("Airplane");

        for (int i = 0; i < nodesPlanes.getLength(); i++) {
            Element elementPlane = (Element) nodesPlanes.item(i);
            Plane plane = buildPlane (elementPlane);


            planes.add(plane);

        }

        return planes;
    }

    /**
     * Creates an Airport object from a DOM node
     *
     * Processes a DOM Node that describes a Plane and creates a Plane object from the information
     * @param nodePlane is a DOM Node describing an Plane
     * @return Plane object created from the DOM Node representation of the Plane
     *
     * @pre nodePlane is of format specified by CS509 server API
     * @post plane object instantiated. Caller responsible for deallocating memory.
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

        /**
         * Instantiate an empty Plane object and initialize with data from XML node
         */
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

