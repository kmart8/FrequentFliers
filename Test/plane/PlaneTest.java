package plane;

import airport.Airport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    @Test
    void isValidManufacturer() {
        assert Plane.isValidManufacturer("Boeing");
        assert !Plane.isValidManufacturer("");
    }

    @Test
    void isValidModel() {
        assert Plane.isValidModel("747");
        assert !Plane.isValidModel("");
    }

    @Test
    void isValidSeats() {
        assert Plane.isValidSeats(150);
        assert !Plane.isValidSeats(-1);
    }

    @Test
    void testEqualsPass() {
        Plane testPlane1 = new Plane();
        testPlane1.manufacturer("Airbus");
        testPlane1.model("A350");
        testPlane1.coachSeats(1);
        testPlane1.firstClassSeats(15);

        // Same model and manufacturer, but case is different and no seating
        Plane testPlane2 = new Plane();
        testPlane2.manufacturer("airbus");
        testPlane2.model("a350");

        assert testPlane1.equals(testPlane2);
    }
}