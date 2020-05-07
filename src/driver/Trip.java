package driver;

import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import leg.Legs;
import ui.UIModel;
import utils.NotificationManager;

import java.util.ArrayList;

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
    private ArrayList<UIModel> flightsDetails = new ArrayList<>();
    private boolean isBooked = false;

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

    public boolean isBooked() { return isBooked; }

    /**
     * Method to add flight to the current trip
     *
     * @param flight the flight to be added to the trip
     */
    public boolean addFlightToTrip(Flight flight, UIModel filter) {
        if (trip.size() > 0 && trip.get(trip.size()-1).getArrivalTime().plusHours(2).isAfter(flight.getDepartureTime())) {
            NotificationManager.getInstance().popupError("Departure time of second flight must be after arrival time of first flight.");
            return false;
        } else {
            trip.add(flight);
            flightsDetails.add(filter);
            return true;
        }
    }

    /**
     * Method to get the current trip as a flight list
     *
     * @return the trip
     */
    public Flights getTrip() {return trip; }

    /**
     * Method to get the current trip as a leg list
     *
     * @return the legs on the trip
     */
    public Legs getLegs() {
        Legs legsOnTrip = new Legs();
        for (Flight thisFlight : trip)
            legsOnTrip.addAll(thisFlight.legList());
        return legsOnTrip;
    }

    public boolean isFull(){
        switch (tripType) {
            case "One-Way":
                return (trip.size() >= 1);
            case "Round-Trip":
                return (trip.size() >= 2);
        }
        return true;
    }

    public void refreshTrip(){
        for (int i = 0; i < trip.size(); i++) {
            trip.get(i).refreshLegs();
            trip.get(i).isMatch(flightsDetails.get(i));
        }
    }

    public boolean isTripValid(){
        refreshTrip();
        if (isFull())
            for (Flight thisFlight : trip){
                if (!thisFlight.getFilterReason().equals("complete"))
                    return false;
            }
        return true;
    }

    /**
     * Method to clear all flights from the trip
     *
     */
    public void resetTrip() {
        if (trip.size() > 0) { trip.clear();}
        isBooked = false;
    }

    public void bookTrip(int numberOfPassengers){
        boolean obtainedLock = false;
        while (!obtainedLock) {
            obtainedLock = ServerInterface.INSTANCE.lock();
            try { wait(100); } catch (Exception exc) {};
        }

        if (!isTripValid()) {
            NotificationManager.getInstance().popupError("One of the selected flights is no longer available, please create a new trip!");
            ServerInterface.INSTANCE.unlock();
        }

        // Attempt to reserve seats
        boolean isSuccess = ServerInterface.INSTANCE.postLegReservation(trip, numberOfPassengers);
        ServerInterface.INSTANCE.unlock();
        isBooked = isSuccess;
    }

}
