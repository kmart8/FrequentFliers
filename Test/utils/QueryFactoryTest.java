package utils;

import airport.Airport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class QueryFactoryTest {

    @Test
    void getAirportsQuery() {
        // Copied from server API documentation
        String validAirportsQuery = "?team=FrequentFliers&action=list&list_type=airports";
        assert validAirportsQuery.equals(QueryFactory.getAirportsQuery());
    }

    @Test
    void getPlanesQuery() {
        // Copied from server API documentation
        String validPlanesQuery = "?team=FrequentFliers&action=list&list_type=airplanes";
        assert validPlanesQuery.equals(QueryFactory.getPlanesQuery());
    }

    @Test
    void getBoardingLegsQuery() {
        // Copied from server API documentation
        String validBoardingLegsQuery = "?team=FrequentFliers&action=list&list_type=departing&airport=BOS&day=2020_05_10";
        Airport testAirport = new Airport();
        testAirport.code("BOS");
        LocalDate testDate = LocalDate.of(2020,5, 10);
        assert validBoardingLegsQuery.equals(QueryFactory.getBoardingLegsQuery(testAirport, testDate));
    }

    @Test
    void getDisembarkingLegsQuery() {
        // Copied from server API documentation
        String validDisembarkingLegsQuery = "?team=FrequentFliers&action=list&list_type=arriving&airport=MEM&day=2020_05_30";
        Airport testAirport = new Airport();
        testAirport.code("MEM");
        LocalDate testDate = LocalDate.of(2020,5, 30);
        assert validDisembarkingLegsQuery.equals(QueryFactory.getDisembarkingLegsQuery(testAirport, testDate));
    }

    @Test
    void lock() {
        // Copied from server API documentation
        String validLockQuery = "team=FrequentFliers&action=lockDB";
        assert validLockQuery.equals(QueryFactory.lock());
    }

    @Test
    void unlock() {
        // Copied from server API documentation
        String validLockQuery = "team=FrequentFliers&action=unlockDB";
        assert validLockQuery.equals(QueryFactory.unlock());
    }

    @Test
    void postLegReservation() {
        // Copied from server API documentation
        String validPostLegsQuery = "team=FrequentFliers&action=buyTickets&flightData=<Flights><Flight number=\"1\" seating=\"Coach\"/><Flight number=\"2\" seating=\"FirstClass\"/></Flights>";
        String testXML = "<Flights><Flight number=\"1\" seating=\"Coach\"/><Flight number=\"2\" seating=\"FirstClass\"/></Flights>";
        assert validPostLegsQuery.equals(QueryFactory.postLegReservation(testXML));
    }

    @Test
    void reset() {
        // Copied from server API documentation
        String validResetQuery = "?team=FrequentFliers&action=resetDB";
        assert validResetQuery.equals(QueryFactory.reset());
    }
}