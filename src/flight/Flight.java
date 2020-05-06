package flight;

import airport.Airport;
import leg.Leg;
import leg.Legs;
import ui.UIModel;
import utils.Saps;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * This class holds values pertaining to a single Flight.
 *
 * @author William Keenan
 * @version 1.0 2020-04-30
 * @since 2020-04-30
 *
 */
public class Flight implements Comparable<Flight>, Comparator<Flight>, Cloneable {

    /** List of legs that makeup the flight */
    private Legs legList;
    /** The seating type for the flight */
    private String seatingType;
    /** The validity of the flight as compared to a UIModel */
    private String filterReason;

    /**
     *  Constructor initializes default values
     */
    public Flight() {
        legList = new Legs();
        seatingType = Saps.SEATING_TYPES.get(0);
    }

    /**
     * Constructor initializes an empty leg list and sets initial value for seating type
     *
     * @param seatingType The seating type of the flight
     */
    public Flight(String seatingType) {
        legList = new Legs();
        if (Saps.SEATING_TYPES.contains(seatingType)) this.seatingType = seatingType;
        else this.seatingType = Saps.SEATING_TYPES.get(0);
    }

    public void legList(Legs legs) { legList = legs;}

    public Legs legList() { return legList;}

    /** Add Leg to end of array */
    public void addLegToEnd(Leg newLeg) {
        legList.add(newLeg);
    }

    /** Add Leg to the beginning of array */
    public void addLegToBeginning(Leg newLeg) {
        legList.add(0, newLeg);
    }

    /** Method to set seating type */
    public void setSeatingType(String seatingRequested) {
        if (Saps.SEATING_TYPES.contains(seatingRequested)) seatingType = seatingRequested;
    }

    /** Method to get Departure Airport of Flight */
    public Airport getDepartureAirport() {
        if (legList.size() > 0) {
            return legList.get(0).getBoardingAirport();
        }
        else return null;
    }

    /** Method to get Arrival Airport of Flight */
    public Airport getArrivalAirport() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getDisembarkingAirport();
        }
        else return null;
    }

    /** Method to get Time of First Leg Boarding Time */
    public ZonedDateTime getDepartureTime() {
        if (legList.size() > 0) {
            return legList.get(0).getBoardingTime();
        }
        else return null;
    }

    /** Method to get Local Time of First Leg Boarding Time */
    public ZonedDateTime getLocalDepartureTime() {
        if (legList.size() > 0) {
            return  legList.get(0).getLocalBoardingTime();
        }
        else return null;
    }

    /** Method to get Time of Last Leg Disembarking Time */
    public ZonedDateTime getArrivalTime() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getDisembarkingTime();
        }
        else return null;
    }

    /** Method to get Local Time of Last Leg Disembarking Time */
    public ZonedDateTime getLocalArrivalTime() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getLocalDisembarkingTime();
        }
        else return null;
    }

    /** Method to get Total Price */
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal("0");
        if (legList.size() > 0) {
            if (seatingType.equals("First Class")) {
                for (Leg thisLeg : legList) {
                    totalPrice = totalPrice.add(thisLeg.getFirstClassPrice());
                }
            } else {
                for (Leg thisLeg : legList) {
                    totalPrice = totalPrice.add(thisLeg.getCoachPrice());
                }
            }
            return totalPrice;
        } else return null;
    }

    /** Method to get Number of Layovers */
    public int getNumberOfLayovers() {
        return Math.max(legList.size() - 1, 0);
    }

    public Duration getTotalTravelTime(){
        return Duration.between(getDepartureTime(), getArrivalTime());
    }
    /** Method to access the filter reason */
    public String filterReason(){ return filterReason; }

    /** Method to access the seating type */
    public String seatingType(){ return seatingType; }

    /** Method to check if Flight is a match */
    public void isMatch(UIModel uIFilter) {
        filterReason = "";
        boolean full = false;
        boolean complete = false;

        if (uIFilter.numberOfLayovers() <= getNumberOfLayovers()) {
            full = true;
        }
        if (uIFilter.departureAirport() == getDepartureAirport()
                && uIFilter.arrivalAirport() == getArrivalAirport()) {
            complete = true;
        }
        if (!complete && full) {
            if (getNumberOfLayovers() == 2) filterReason = "invalid";
            else filterReason = "layovers";
            return;
        }

        for (Leg thisLeg : legList)
            if (thisLeg.getRemainingSeats("Coach") < uIFilter.numberOfPassengers() &&
                    thisLeg.getRemainingSeats("First Class") < uIFilter.numberOfPassengers()) {
                filterReason = "invalid";
                return;
            }

        for (Leg thisLeg : legList)
            if (thisLeg.getRemainingSeats(seatingType) < uIFilter.numberOfPassengers()) {
                filterReason = "seating";
                return;
            }

        if (complete) filterReason = "complete";

    }

    /** Required Compare To Method */
    public int compareTo(Flight other) {
        return getTotalPrice().compareTo(other.getTotalPrice());
    }

    /** Required Compare Method */
    public int compare(Flight flight1, Flight flight2){
        return flight1.compareTo(flight2);
    }

    /** Required Clone Method*/
    public Flight clone() throws CloneNotSupportedException{
        Flight copy = (Flight)super.clone();
        copy.legList(legList.clone());
        return copy;
    }
}
