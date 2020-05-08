package flight;

import airport.Airport;
import leg.Leg;
import leg.Legs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UIModel;

import java.math.BigDecimal;
import java.time.*;

class FlightTest {
    Flight testFlight = new Flight();
    Leg testLeg1 = new Leg();
    Leg testLeg2 = new Leg();
    Leg testLeg3 = new Leg();
    Legs testLegs = new Legs();
    LocalDate testDate = LocalDate.of(2020,5,22);
    LocalTime testTime = LocalTime.of(18,1,14);
    ZonedDateTime testZDT1 = ZonedDateTime.of(testDate,testTime,ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
    Duration travelTime = Duration.ofHours(6).plusMinutes(49);
    ZonedDateTime testZDT2 = testZDT1.plus(travelTime);
    ZonedDateTime testZDT3 = testZDT1.minus(travelTime);
    Airport testAirport1 = new Airport();
    Airport testAirport2 = new Airport();

    @BeforeEach
    void setUp() {
        testFlight.setSeatingType("Coach");
        testLeg1 = new Leg();
        testLeg1.setFlightNumber(90);
        testLeg1.setBoardingTime(testZDT1);
        testAirport1.code("SEA");
        testLeg1.setBoardingAirport(testAirport1);
        testLeg2 = new Leg();
        testLeg2.setFlightNumber(61);
        testLeg2.setDisembarkingTime(testZDT2);
        testAirport2.code("PHX");
        testLeg2.setDisembarkingAirport(testAirport2);
        testLeg3 = new Leg();
        testLeg3.setFlightNumber(18);
        testLeg3.setDisembarkingTime(testZDT3);
        testLegs.add(testLeg1);
        testLegs.add(testLeg2);
        testFlight.legList(testLegs);
    }

    @Test
    void addLegToEnd() {
        testFlight.addLegToEnd(testLeg3);
        assert testFlight.legList().get(2).equals(testLeg3);
    }

    @Test
    void addLegToBeginning() {
        testFlight.addLegToBeginning(testLeg3);
        assert testFlight.legList().get(0).equals(testLeg3);
    }

    @Test
    void getDepartureAirport() {
        assert testFlight.getDepartureAirport().equals(testAirport1);
    }

    @Test
    void getArrivalAirport() {
        assert testFlight.getArrivalAirport().equals(testAirport2);
    }

    @Test
    void getDepartureTime() {
        assert testFlight.getDepartureTime().equals(testZDT1);
    }

    @Test
    void getLocalDepartureTime() {
        assert testFlight.getLocalDepartureTime().equals(testAirport1.convertGMTtoLocalTime(testZDT1));
    }

    @Test
    void getArrivalTime() {
        assert testFlight.getArrivalTime().equals(testZDT2);
    }

    @Test
    void getLocalArrivalTime() {
        assert testFlight.getLocalArrivalTime().equals(testAirport2.convertGMTtoLocalTime(testZDT2));
    }

    @Test
    void getTotalPrice() {
        testLeg1.setCoachPrice(BigDecimal.valueOf(22.55));
        testLeg2.setCoachPrice(BigDecimal.valueOf(150.03));
        assert testFlight.getTotalPrice().equals(BigDecimal.valueOf(172.58));
    }

    @Test
    void getNumberOfLayovers() {
        assert testFlight.getNumberOfLayovers() == 1;
        testFlight.legList(new Legs());
        assert testFlight.getNumberOfLayovers() == 0;
    }

    @Test
    void getTotalTravelTime() {
        assert testFlight.getTotalTravelTime().equals(travelTime);
    }

    @Test
    void isAfterBy() {
        Flight testFlight2 = new Flight();
        testFlight2.addLegToBeginning(testLeg2);
        Flight testFlight3 = new Flight();
        testFlight3.addLegToBeginning(testLeg3);
        assert !testFlight.isAfterBy(testFlight2, Duration.ofHours(1));
        assert testFlight.isAfterBy(testFlight3, Duration.ofHours(1));
        assert !testFlight.isAfterBy(testFlight3, Duration.ofHours(7));
    }
}