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

    /**
     * Static method to provide single point of access to the Singleton
     *
     * @return the active Trip, or a new one if one is not created
     */
    public static Trip getInstance() {
        if (single_instance == null) {
            single_instance = new Trip();
        }
        return single_instance;
    }

    /**
     * Method to set the tripType private variable of the Singleton
     *
     * @param tripType the tripType of the Singleton
     */
    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    /**
     * Method to get the tripType private variable of the Singleton
     *
     * @return the tripType
     */
    public String getTripType() { return tripType; }

    /**
     * Method to add flight to the current trip
     *
     * @param flight the flight to be added to the trip
     */
    public void addFlightToTrip(Flight flight) {
        trip.add(flight);
    }

    /**
     * Method to get the current trip
     *
     * @return the trip
     */
    public Flights getTrip() {return trip; }

    /**
     * Method to clear all flights from the trip
     *
     */
    public void resetTrip() {
        if (trip.size() > 0) { trip.clear();}
    }

}
