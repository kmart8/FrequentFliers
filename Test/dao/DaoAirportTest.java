package dao;

import airport.Airport;
import airport.Airports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DaoAirportTest {
    Airport testAirport1;
    Airport testAirport2;
    Airport testAirport3;
    String xmlIn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
            "<Airports>" +
            "<Airport Code=\"ATL\" Name=\"Hartsfield-Jackson Atlanta International\">" +
            "<Latitude>33.641045</Latitude>" +
            "<Longitude>-84.427764</Longitude>" +
            "</Airport>" +
            "<Airport Code=\"ANC\" Name=\"Ted Stevens Anchorage International Airport\">" +
            "<Latitude>61.176033</Latitude>" +
            "<Longitude>-149.990079</Longitude>" +
            "</Airport>" +
            "<Airport Code=\"BOS\" Name=\"Logan International\">" +
            "<Latitude>42.365855</Latitude>" +
            "<Longitude>-71.009624</Longitude>" +
            "</Airport>" +
            "</Airports>";

    @BeforeEach
    void setUp() {
        testAirport1 = new Airport();
        testAirport1.code("ATL");
        testAirport1.name("Hartsfield-Jackson Atlanta International");
        testAirport1.latitude(33.641045);
        testAirport1.longitude(-84.427764);

        testAirport2 = new Airport();
        testAirport2.code("ANC");
        testAirport2.name("Ted Stevens Anchorage International Airport");
        testAirport2.latitude(61.176033);
        testAirport2.longitude(-149.990079);

        testAirport3 = new Airport();
        testAirport3.code("BOS");
        testAirport3.name("Logan International");
        testAirport3.latitude(42.365855);
        testAirport3.longitude(-71.009624);
    }


    @Test
    void addAll() {
        Airports testAirports = DaoAirport.addAll(xmlIn);
        assert testAirports.contains(testAirport1);
        assert testAirports.contains(testAirport2);
        assert testAirports.contains(testAirport3);
        assert testAirports.size() == 3;
    }
}