package flight;

import flight.Flight;

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
        if (this.size() > 0) {
            Flight cheapestFlight = this.get(0);
            for (Flight thisFlight : this) {
                if (thisFlight.getTotalPrice().compareTo(cheapestFlight.getTotalPrice()) < 0) {
                    cheapestFlight = thisFlight;
                }
                this.remove(cheapestFlight);
                newFlights.add(cheapestFlight);
            }
        }
        this.addAll(newFlights);
        if (!isAscending) Collections.reverse(this);
    }

    /** Method to sort by shortest Flight */
    public Flights sortByTravelDuration(Flights oldFlights) {
        Flights newFlights = new Flights();
        if (oldFlights.size() > 0) {
            Flight shortestFlight = oldFlights.get(0);
            for (Flight thisFlight : oldFlights) {
                if (thisFlight.getDepartureTime().until(thisFlight.getArrivalTime(), MINUTES)
                        < shortestFlight.getDepartureTime().until(shortestFlight.getArrivalTime(), MINUTES)){
                    shortestFlight = thisFlight;
                }
                oldFlights.remove(shortestFlight);
                newFlights.add(shortestFlight);
            }
        }
        return newFlights;
    }

    /** Method to sort by departure time */
    public Flights sortByDepatureTime(Flights oldFlights) {
        Flights newFlights = new Flights();
        if (oldFlights.size() > 0) {
            Flight earliestFlight = oldFlights.get(0);
            for (Flight thisFlight : oldFlights) {
                if (thisFlight.getDepartureTime().until(earliestFlight.getArrivalTime(), MINUTES) > 0) {
                    earliestFlight = thisFlight;
                }
                oldFlights.remove(earliestFlight);
                newFlights.add(earliestFlight);
            }
        }
        return newFlights;
    }

    /** Method to sort by arrival time */
    public Flights sortByArrivalTime(Flights oldFlights) {
        Flights newFlights = new Flights();
        if (oldFlights.size() > 0) {
            Flight earliestFlight = oldFlights.get(0);
            for (Flight thisFlight : oldFlights) {
                if (thisFlight.getArrivalTime().until(earliestFlight.getArrivalTime(), MINUTES) < 0) {
                    earliestFlight = thisFlight;
                }
                oldFlights.remove(earliestFlight);
                newFlights.add(earliestFlight);
            }
        }
        return newFlights;
    }
}


