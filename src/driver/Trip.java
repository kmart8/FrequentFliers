package driver;

import flight.Flight;
import flight.Flights;

/**
 * This class handles the user's selected flights
 *
 * @author Kevin Martin
 * @version 1.0 2020-05-04
 * @since 2020-05-04
 *
 */

public class Trip {

    private String tripType;
    private Flights flightCart = new Flights();

    // Singleton variable
    private static Trip single_instance = null;

    public static Trip getInstance() {
        if (single_instance == null) {
            single_instance = new Trip();
        }
        return single_instance;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getTripType() { return tripType; }

    public void addFlightToTrip(Flight flight) {
        flightCart.add(flight);
        /**
        if (tripType.equals("One-Way")) {
            if (flightCart.size() < 1) {
                flightCart.add(flight);
            }
            else {
                System.out.println("Cart is full for one-way trip");
            }
            // TODO: implement logic where flight 1 departure airport == flight 2 arrival airport and flight 1 arrival airport == flight 2 departure airport
        } else if (tripType.equals("Round-Trip")) {
            if (flightCart.size() < 2) {
                flightCart.add(flight);
            }
            else {
                System.out.println("Cart is full for round-trip");
            }
        }*/
    }

    public Flights getTrip() {
        if (tripType.equals("One-Way") && flightCart.size() == 1) {
            return flightCart;
        } else if (tripType.equals("Round-Trip") && flightCart.size() == 2) {
            return flightCart;

            // TODO: improve this error handling
        } else {
            System.out.println("WARNING: Flight cart empty");
            return flightCart;
        }
    }

    public void resetTrip() {
        if (flightCart.size() > 0) { flightCart.clear();}
    }

}
