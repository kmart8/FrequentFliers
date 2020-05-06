package flight;

import flight.Flight;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * This class extends ArrayList. It is an aggregate of Flight objects.
 *
 * @author William Keenan
 * @version 1.0 2020-05-03
 * @since 2020-05-03
 *
 */
public class Flights extends ArrayList<Flight> {
    private static final long serialVersionUID = 1L;

    /**
     * Method to sort by cheapest Flight
     *
     * @param isAscending Boolean to determine sorting order
     */
    public void sortByPrice(boolean isAscending) {
        Flights newFlights = new Flights();
        while (this.size() > 0) {
            Flight cheapestFlight = this.get(0);
            for (int i = this.size() - 1; i >= 0; i--) {
                if (this.get(i).getTotalPrice().compareTo(cheapestFlight.getTotalPrice()) < 0) {
                    cheapestFlight = this.get(i);
                }
            }
            this.remove(cheapestFlight);
            newFlights.add(cheapestFlight);
        }
        this.addAll(newFlights);
        if (!isAscending) Collections.reverse(this);
    }

    /**
     * Method to sort by shortest Flight
     *
     * @param isAscending Boolean to determine sorting order
     */
    public void sortByTravelDuration(boolean isAscending) {
        Flights newFlights = new Flights();
        while (this.size() > 0) {
            Flight shortestFlight = this.get(0);
            for (int i = this.size() - 1; i >= 0; i--) {
                if (this.get(i).getTotalTravelTime().minus(shortestFlight.getTotalTravelTime()).isNegative()){
                    shortestFlight = this.get(i);
                }
            }
            this.remove(shortestFlight);
            newFlights.add(shortestFlight);
        }
        this.addAll(newFlights);
        if (!isAscending) Collections.reverse(this);
    }

    /**
     * Method to sort by departure time
     *
     * @param isAscending Boolean to determine sorting order
     */
    public void sortByDepatureTime(boolean isAscending) {
        Flights newFlights = new Flights();
        while (this.size() > 0) {
            Flight earliestFlight = this.get(0);
            for (int i = this.size() - 1; i >=0; i--) {
                if (this.get(i).getDepartureTime().compareTo(earliestFlight.getDepartureTime()) < 0)
                {
                    earliestFlight = this.get(i);
                }
            }
            this.remove(earliestFlight);
            newFlights.add(earliestFlight);
        }
        this.addAll(newFlights);
        if (!isAscending) Collections.reverse(this);
    }

    /**
     * Method to sort by arrival time
     *
     * @param isAscending Boolean to determine sorting order
     */
    public void sortByArrivalTime(boolean isAscending) {
        Flights newFlights = new Flights();
        while (this.size() > 0) {
            Flight earliestFlight = this.get(0);
            for (int i = this.size() - 1; i >= 0; i--) {
                if (this.get(i).getArrivalTime().compareTo(earliestFlight.getArrivalTime()) < 0) {
                    earliestFlight = this.get(i);
                }
            }
            this.remove(earliestFlight);
            newFlights.add(earliestFlight);
        }
        this.addAll(newFlights);
        if (!isAscending) Collections.reverse(this);
    }
}