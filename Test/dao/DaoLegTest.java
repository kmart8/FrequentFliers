package dao;

import airport.Airport;
import airport.Airports;
import leg.Leg;
import leg.Legs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoLegTest {
    Leg testLeg1 = new Leg();
    Leg testLeg2 = new Leg();

    String xmlIn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<Flights>" +
            "<Flight Airplane=\"767\" FlightTime=\"1001\" Number=\"1380\">" +
            "<Departure>" +
            "<Code>AUS</Code>" +
            "<Time>2019 May 04 19:24 GMT</Time>" +
            "</Departure>" +
            "<Arrival>" +
            "<Code>BOS</Code>" +
            "<Time>2019 May 05 12:05 GMT</Time>" +
            "</Arrival>" +
            "<Seating>" +
            "<FirstClass Price=\"$728.76\">65</FirstClass>" +
            "<Coach Price=\"$378.96\">189</Coach>" +
            "</Seating>" +
            "</Flight>" +
            "<Flight Airplane=\"757\" FlightTime=\"167\" Number=\"2751\">" +
            "<Departure>" +
            "<Code>BOS</Code>" +
            "<Time>2019 May 05 00:28 GMT</Time>" +
            "</Departure>" +
            "<Arrival>" +
            "<Code>FLL</Code>" +
            "<Time>2019 May 05 03:15 GMT</Time>" +
            "</Arrival>" +
            "<Seating>" +
            "<FirstClass Price=\"$396.33\">20</FirstClass>" +
            "<Coach Price=\"$74.60\">22</Coach>" +
            "</Seating>" +
            "</Flight>" +
            "</Flights>";

    @BeforeEach
    void setUp() {
        testLeg1.setFlightNumber(1380);
        testLeg1.setFlightNumber(2751);
    }

    @Test
    void addAll() {
        Legs testLegs = DaoLeg.addAll(xmlIn);
        assert testLegs.contains(testLeg1);
        assert testLegs.contains(testLeg1);
        assert testLegs.size() == 2;
    }
}