package airport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class AirportTest {
    Airport testAirport1;
    Airport testAirport2;

    @BeforeEach
    void setUp() {
        testAirport1 = new Airport();
        testAirport1.code("ATL");
        testAirport1.name("Hartsfield-Jackson Atlanta International Airport");
        testAirport1.latitude(33.6407);
        testAirport1.longitude(84.4277);

        testAirport2 = new Airport();
        testAirport2.code("ATL");
        testAirport2.name("Hartsfield-Jackson Atlanta International Airport");
        testAirport2.latitude(33.6407);
        testAirport2.longitude(84.4277);
    }

    @Test
    void testEqualsPass() {
        assert testAirport1.equals(testAirport2);
    }

    @Test
    void testEqualsFailCode() {
        testAirport2.code("PHL");
        assert !testAirport1.equals(testAirport2);
    }

    @Test
    void testEqualsFailName() {
        testAirport2.name("Philadelphia International Airport");
        assert !testAirport1.equals(testAirport2);
    }

    @Test
    void testEqualsFailLatitude() {
        testAirport2.latitude(33);
        assert !testAirport1.equals(testAirport2);
    }

    @Test
    void testEqualsFailLongitude() {
        testAirport2.longitude(-84.4277);
        assert !testAirport1.equals(testAirport2);
    }

    @Test
    void isValidCode() {
        assert Airport.isValidCode("DEN");
        assert !Airport.isValidCode("");
    }

    @Test
    void isValidName() {
        assert Airport.isValidName("Logan International Airport");
        assert !Airport.isValidName("");
    }

    @Test
    void isValidLatitude() {
        assert Airport.isValidLatitude(10);
        assert Airport.isValidLatitude(-64);
        assert !Airport.isValidLatitude(-90.01);
        assert !Airport.isValidLatitude(91);
    }

    @Test
    void isValidLongitude() {
        assert Airport.isValidLongitude(152);
        assert Airport.isValidLongitude(-33);
        assert !Airport.isValidLongitude(-180.01);
        assert !Airport.isValidLongitude(181);
    }

    @Test
    void convertGMTtoLocalTime() {
        LocalDate d1 = LocalDate.of(2020,5,14);
        LocalTime t1 = LocalTime.of(6,0);
        ZonedDateTime testTime1 = ZonedDateTime.of(d1,t1, ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
        Airport testAirport = new Airport();
        testAirport.code("HOU");
        // HOU is GMT - 5
        ZonedDateTime testTime2 = ZonedDateTime.of(d1,t1.minusHours(5), ZoneId.of("America/Chicago"));
        assert testAirport.convertGMTtoLocalTime(testTime1).equals(testTime2);
    }

    @Test
    void convertLocalDateTimetoGMT() {
        LocalDate d1 = LocalDate.of(2020,5,18);
        LocalTime t1 = LocalTime.of(11,0);
        ZonedDateTime testTime2 = ZonedDateTime.of(d1,t1, ZoneId.of("America/Anchorage"));
        Airport testAirport = new Airport();
        testAirport.code("ANC");
        // ANC is GMT - 8
        ZonedDateTime testTime1 = ZonedDateTime.of(d1,t1.plusHours(8), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
        assert testAirport.convertGMTtoLocalTime(testTime1).equals(testTime2);
    }
}