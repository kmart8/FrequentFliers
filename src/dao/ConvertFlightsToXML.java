package dao;

import flight.Flight;
import flight.Flights;
import leg.Leg;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import utils.NotificationManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Builds an XML string from a collection of flights to reserve a seat on each flight for each passenger.
 *
 * Creates an empty document and adds a Flights element, where each node of the element contains
 * a flight number and seating type according to the server API format. A node is added for each passenger on
 * each flight. The document is then transformed to an XML formatted String.
 *
 * @author Kevin Martin
 * @version 1.0 2019-05-01
 * @since 2016-05-01
 *
 */
public class ConvertFlightsToXML {
    /**
     * Creates an XML formatted string from a Flights collection.
     *
     * Method iterates over the collection of legs in each flight to build a DOM.
     * It attempts to convert the DOM to XML, notifying the user on failure and returning an empty string.
     *
     * @param flights The collection of flights that need reservations
     * @param numberOfPassengers the number of seats to reserve on each flight
     * @return [possibly empty] an xml formatted string for posting flight reservations to the server
     *
     * @pre the number of passengers should be greater than 0
     */
    public static String buildPostXML(Flights flights, int numberOfPassengers) {
        // Generate a new empty document
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();

        } catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        }

        if (document != null) {
            // Create the root
            Element root = document.createElement("Flights");
            document.appendChild(root);

            // Iterate through each flight
            for (Flight thisFlight : flights) {
                // Get the seating type
                String seatingType = thisFlight.getSeatingType();
                // Add child elements for each leg
                for (Leg leg : thisFlight.legList()) {
                    Node legNode = createLegNode(document, seatingType, leg);
                    root.appendChild(legNode);
                    // Copy and add another matching child node for each passenger above 1
                    for (int i = 1; i < numberOfPassengers ; i++) {
                        Node copy = legNode.cloneNode(false);
                        root.appendChild(copy);
                    }
                }
            }

            // Attempt to convert the document to XML
            String xmlString = convertDOMToXML(document);
            if (xmlString == null) {
                NotificationManager.getInstance().popupError("Error making reservation, no reservations created!");
                return "";
            }
            return xmlString;
        }
        else return "";
    }

    /**
     * Converts a DOM to an XML formatted string.
     *
     * Processes a Document object and transforms it to an XML formatted string.
     *
     * @param document Document to be converted to XML
     * @return XML formatted String
     */
    private static String convertDOMToXML(Document document){
        // attempts to convert document to string
        try {
            // create Transformer for transformation
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            // create stringwriter to store transformation output
            StringWriter writer = new StringWriter();

            // transform document to string
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.getBuffer().toString();
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

    /**
     * Creates a DOM node from a Leg object.
     *
     * Processes a Leg object and creates a DOM node that contains the flight number for the leg and the seating type.
     *
     * @param document Add the new node to this document
     * @param seatingType Seating type to use in the node attribute
     * @param leg Leg used to obtain the flight number used in the node attribute
     * @return Node object created for the specified document
     *
     * @pre seatingType Document is not null, String conforms to the specified server API, and Leg contains
     *  a valid flight number
     * @post Node is initialized. Caller responsible for adding it to the root.
     */
    private static Node createLegNode(Document document, String seatingType, Leg leg) {
        // create leg node
        Element legNode = document.createElement("Flight");

        // create attribute
        Attr flightNumberAttribute = document.createAttribute("number");
        Attr seatingAttribute = document.createAttribute("seating");
        flightNumberAttribute.setValue(Integer.toString(leg.getFlightNumber()));
        seatingAttribute.setValue(seatingType);


        // append attribute to leg node
        legNode.setAttributeNode(flightNumberAttribute);
        legNode.setAttributeNode(seatingAttribute);

        return legNode;
    }
}
