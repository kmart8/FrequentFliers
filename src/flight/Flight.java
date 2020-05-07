package flight;

import airport.Airport;
import leg.Leg;
import leg.Legs;
import ui.UIModel;
import utils.Saps;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * This class initializes Flight as a class and creates its attributes and methods.
 *
 * @author William Keenan
 * @version 1.0 2020-05-06
 * @since 2020-04-30
 *
 */
public class Flight implements Cloneable {

    /** List of legs that makeup the flight */
    private Legs legList;
    /** The seating type for the flight */
    private String seatingType;
    /** The validity of the flight as compared to a UIModel */
    private String filterReason;

    /**
     *  Constructor initializes default values
     *
     *  @post member attributes are initialized to default values
     */
    public Flight() {
        legList = new Legs();
        seatingType = Saps.SEATING_TYPES.get(0);
    }

    /**
     * Default Constructor
     *
     * Constructor initializes an empty leg list and sets initial value for seating type
     *
     * @param seatingType The seating type of the flight
     * @post member attributes are initialized
     */
    public Flight(String seatingType) {
        legList = new Legs();
        if (Saps.SEATING_TYPES.contains(seatingType)) this.seatingType = seatingType;
        else this.seatingType = Saps.SEATING_TYPES.get(0);
    }

    /**
     * Method for setting the leg list
     *
     * @param legs The new leg list
     */
    public void legList(Legs legs) { legList = legs;}

    /**
     * Method for getting the leg list
     *
     * @return The leg list
     */
    public Legs legList() { return legList;}

    /**
     * Method for adding Leg to end of array
     *
     * @param newLeg The new leg to be added
     */
    public void addLegToEnd(Leg newLeg) {
        legList.add(newLeg);
    }

    /**
     * Method for adding Leg to the beginning of array
     *
     * @param newLeg The new leg to be added
     */
    public void addLegToBeginning(Leg newLeg) {
        legList.add(0, newLeg);
    }

    /**
     * Method to set seating type
     *
     * @param seatingRequested The seating type requested
     */
    public void setSeatingType(String seatingRequested) {
        if (Saps.SEATING_TYPES.contains(seatingRequested)) seatingType = seatingRequested;
    }

    /**
     * Method to get Departure Airport of Flight
     *
     * @return [possibly null] The departure airport of flight
     */
    public Airport getDepartureAirport() {
        if (legList.size() > 0) {
            return legList.get(0).getBoardingAirport();
        }
        else return null;
    }

    /**
     * Method to get Arrival Airport of Flight
     *
     * @return [possibly null] The arrival airport of Flight
     */
    public Airport getArrivalAirport() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getDisembarkingAirport();
        }
        else return null;
    }

    /**
     * Method to get Time of First Leg Boarding Time
     *
     * @return [possibly null] The first leg boarding zonedatetime
     */
    public ZonedDateTime getDepartureTime() {
        if (legList.size() > 0) {
            return legList.get(0).getBoardingTime();
        }
        else return null;
    }

    /**
     * Method to get Local Time of First Leg Boarding Time
     *
     * @return [possibly null] The first leg local boarding zonedatetime
     */
    public ZonedDateTime getLocalDepartureTime() {
        if (legList.size() > 0) {
            return  legList.get(0).getLocalBoardingTime();
        }
        else return null;
    }

    /**
     * Method to get Time of Last Leg Disembarking Time
     *
     * @return [possibly null] The last leg disembarking zonedatetime
     */
    public ZonedDateTime getArrivalTime() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getDisembarkingTime();
        }
        else return null;
    }

    /**
     * Method to get Local Time of Last Leg Disembarking Time
     *
     * @return [possibly null] The last leg local disembarking zonedatetime
     */
    public ZonedDateTime getLocalArrivalTime() {
        if (legList.size() > 0) {
            return legList.get(legList.size()-1).getLocalDisembarkingTime();
        }
        else return null;
    }

    /**
     * Method to get Total Price
     *
     * @return [possibly null] The total price of all legs in the flight
     */
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

    /**
     * Method to get Number of Layovers
     *
     * @return The number of layovers
     */
    public int getNumberOfLayovers() {
        return Math.max(legList.size() - 1, 0);
    }

    /**
     * Method to get the total travel duration
     *
     * @return The travel duration
     */
    public Duration getTotalTravelTime(){
        return Duration.between(getDepartureTime(), getArrivalTime());
    }

    /**
     * Method to get the filter reason
     *
     * @return The filter reason
     */
    public String getFilterReason(){ return filterReason; }

    /**
     * Method to get the seating type
     *
     * @return The seating type
     */
    public String getSeatingType(){ return seatingType; }

    /**
     * Method to check if Flight is a match
     *
     * @param uIFilter The UI Filter
     */
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
            if (complete && thisLeg.getRemainingSeats(seatingType) < uIFilter.numberOfPassengers()) {
                filterReason = "seating";
                return;
            }

        if (complete) filterReason = "complete";

    }

    /**
     * Required Clone Method
     *
     * @return Copy of Flight
     * @throws CloneNotSupportedException If clone is not supported
     */
    public Flight clone() throws CloneNotSupportedException{
        Flight copy = (Flight)super.clone();
        copy.legList(legList.clone());
        return copy;
    }
}
