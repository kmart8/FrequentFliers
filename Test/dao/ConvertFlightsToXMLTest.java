package dao;

import airport.Airport;
import flight.Flight;
import flight.Flights;
import leg.Leg;
import leg.Legs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class ConvertFlightsToXMLTest {
    Flight testFlight1 = new Flight();
    Flight testFlight2 = new Flight();
    Leg testLeg1 = new Leg();
    Leg testLeg2 = new Leg();
    Leg testLeg3 = new Leg();
    String xml = "<Flights><Flight number=\"90\" seating=\"Coach\"/><Flight number=\"90\" seating=\"Coach\"/>" +
            "<Flight number=\"90\" seating=\"Coach\"/><Flight number=\"61\" seating=\"First Class\"/><Flight number=\"61\" seating=\"First Class\"/>" +
            "<Flight number=\"61\" seating=\"First Class\"/><Flight number=\"18\" seating=\"First Class\"/>" +
            "<Flight number=\"18\" seating=\"First Class\"/><Flight number=\"18\" seating=\"First Class\"/></Flights>";


    @BeforeEach
    void setUp() {
        testLeg1.setFlightNumber(90);
        testLeg2.setFlightNumber(61);
        testLeg3.setFlightNumber(18);
        Legs testLegs1 = new Legs();
        testLegs1.add(testLeg1);
        testFlight1.legList(testLegs1);
        Legs testLegs2 = new Legs();
        testLegs2.add(testLeg2);
        testLegs2.add(testLeg3);
        testFlight2.legList(testLegs2);
        testFlight2.setSeatingType("First Class");
    }

    @Test
    void buildPostXML() {
        Flights testFlights = new Flights();
        testFlights.add(testFlight1);
        testFlights.add(testFlight2);
        String xmlAnswer =ConvertFlightsToXML.buildPostXML(testFlights, 3);
        assert xmlAnswer.equals(xml);
    }
}