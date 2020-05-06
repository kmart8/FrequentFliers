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
    private Flights trip = new Flights();

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
        trip.add(flight);
    }

    public Flights getTrip() {return trip; }

    public void resetTrip() {
        if (trip.size() > 0) { trip.clear();}
    }

}
