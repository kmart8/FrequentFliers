package leg;

import airport.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plane.Plane;

import java.math.BigDecimal;
import java.time.*;

class LegTest {
    Leg testLeg1;
    LocalDate testDate = LocalDate.of(2020,5,17);
    LocalTime testTime1 = LocalTime.of(12,33,10);
    LocalTime testTime2 = LocalTime.of(14,51,22);

    @BeforeEach
    void setUp() {
        Airport testAirport1 = new Airport();
        testAirport1.code("SFO");
        Airport testAirport2 = new Airport();
        testAirport2.code("CLT");
        Plane testPlane = new Plane();
        testPlane.model("F20");
        testPlane.coachSeats(201);
        testPlane.firstClassSeats(9);
        ZonedDateTime time1 = ZonedDateTime.of(testDate, testTime1, ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
        ZonedDateTime time2 = ZonedDateTime.of(testDate, testTime2, ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));

        testLeg1 = new Leg();
        testLeg1.setFlightNumber(10101);
        testLeg1.setBoardingAirport(testAirport1);
        testLeg1.setDisembarkingAirport(testAirport2);
        testLeg1.setBoardingTime(time1);
        testLeg1.setDisembarkingTime(time2);
        testLeg1.setCoachPrice(BigDecimal.valueOf(7));
        testLeg1.setFirstClassPrice(BigDecimal.valueOf(11));
        testLeg1.setReservedCoachSeats(3);
        testLeg1.setReservedFirstClassSeats(8);
        testLeg1.setLegDuration(Duration.ofMinutes(2));
        testLeg1.setPlane(testPlane);
    }

    @Test
    void getLocalBoardingTime() {
        // SFO is GMT -7
        ZonedDateTime localTime = ZonedDateTime.of(testDate, testTime1.minusHours(7), ZoneId.of("America/Los_Angeles"));
        assert testLeg1.getLocalBoardingTime().equals(localTime);
    }

    @Test
    void getLocalDisembarkingTime() {
        // CLT is GMT -4
        ZonedDateTime localTime = ZonedDateTime.of(testDate, testTime2.minusHours(4), ZoneId.of("America/New_York"));
        assert testLeg1.getLocalDisembarkingTime().equals(localTime);
    }

    @Test
    void getRemainingSeats() {
        assert testLeg1.getRemainingSeats("Coach") == 198;
        assert testLeg1.getRemainingSeats("First Class") == 1;
    }

    @Test
    void testEquals() {
        // Same flight number should mean same leg
        Leg testLeg2 = new Leg();
        testLeg2.setFlightNumber(10101);

        assert testLeg1.equals(testLeg2);
    }
}