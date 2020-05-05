package flight;

import flight.Flight;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * This class aggregates a number of Flight. The aggregate is implemented as an ArrayList.
 * Flights can be added to the aggregate using the ArrayList interface. Objects can
 * be removed from the collection using the ArrayList interface.
 *
 * @author blake
 * @version 1.0
 * @since 2016-02-24
 *
 */
public class Flights extends ArrayList<Flight> {
    private static final long serialVersionUID = 1L;

    /** Method to sort by cheapest Flight */
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

    /** Method to sort by shortest Flight */
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

    /** Method to sort by departure time */
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

    /** Method to sort by arrival time */
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