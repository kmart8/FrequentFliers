package dao;

import flight.Flight;
import flight.Flights;
import leg.Leg;
import leg.Legs;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import utils.NotificationManager;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class ConvertFlightsToXML {

    public static String buildPostXML(Flights flights, int numberOfPassengers) {
        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();

        } catch (ParserConfigurationException parserException) {
            parserException.printStackTrace();
        }

        assert document != null;
        Element root = document.createElement("Flights");
        document.appendChild(root);

        for (Flight thisFlight : flights) {
            String seatingType = thisFlight.getSeatingType();
            Legs legList = thisFlight.legList();

            for (Leg leg : legList) {
                // add child element
                Node legNode = createLegNode(document, seatingType, leg);
                root.appendChild(legNode);
                for (int i = 0; i < numberOfPassengers - 1; i++){
                    Node copy = legNode.cloneNode(false);
                    root.appendChild(copy);
                }
            }
        }

        String xmlString = convertDOMToXML(document);
        if (xmlString == null) {
            NotificationManager.getInstance().popupError("Error making reservation, no reservations created!");
            return "";
        }
        return xmlString;
    }

    private static String convertDOMToXML(Document document){
        // convert document to string
        try {
            // create DOMSource for source XML document
            Source xmlSource = new DOMSource(document);

            // create Transformer for transformation
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter writer = new StringWriter();

            //transform document to string
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
