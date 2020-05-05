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

public class FlightCart {

    private String tripType;
    private Flights flightCart;

    // Singleton variable
    private static FlightCart single_instance = null;

    public static FlightCart getInstance() {
        if (single_instance == null) {
            single_instance = new FlightCart();
        }
        return single_instance;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getTripType() {return tripType;}

    public void addFlightToCart(Flight flight) {
        if (tripType.equals("One-Way")) {
            if (flightCart.size() < 1) {
                this.flightCart.add(flight);
            }
            else {
                System.out.println("Cart is full for one-way trip");
            }
            // TODO: implement logic where flight 1 departure airport == flight 2 arrival airport and flight 1 arrival airport == flight 2 departure airport
        } else if (tripType.equals("Round-Trip")) {
            if (flightCart.size() < 2) {
                this.flightCart.add(flight);
            }
            else {
                System.out.println("Cart is full for round-trip");
            }
        }
        this.flightCart.add(flight);
    }

    public Flights getFlightCart() {
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

}
