package leg;

import airport.Airport;
import plane.Plane;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * This class initializes Leg as a class and creates its attributes and methods.
 *
 * @author Kevin Martin, William Keenan
 * @version 1.1 2020-05-06
 * @since 2020-03-23
 *
 */

public class Leg {

    /**
     * Creates the attributes of the Leg class
     */
    private Airport disembarkingAirport;
    private Airport boardingAirport;
    private ZonedDateTime disembarkingTime;
    private ZonedDateTime boardingTime;
    private int flightNumber;
    private int reservedCoachSeats;
    private int reservedFirstClassSeats;
    private Duration legDuration;
    private Plane plane; // the plane model
    private BigDecimal coachPrice;
    private BigDecimal firstClassPrice;

    /**
     * Default Constructor
     *
     * The constructor for Leg Objects
     *
     * @post member attributes are initialized to default values
     */
    public Leg() {
        disembarkingAirport = new Airport();
        boardingAirport = new Airport();
        disembarkingTime = null;
        boardingTime = null;
        flightNumber = Integer.MAX_VALUE;
        reservedCoachSeats = Integer.MAX_VALUE;
        reservedFirstClassSeats = Integer.MAX_VALUE;
        legDuration = Duration.ZERO;
        plane = new Plane();
        coachPrice = null;
        firstClassPrice = null;
    }

    /**
     * Method for getting disembarking Airport object
     *
     * @return Disembarking airport object
     */
    public Airport getDisembarkingAirport() {
        return disembarkingAirport;
    }

    /**
     * Method for getting boarding Airport object
     *
     * @return Boarding airport object
     */
    public Airport getBoardingAirport() {
        return boardingAirport;
    }

    /**
     * Method for getting the disembarking time
     *
     * @return Disembarking time
     */
    public ZonedDateTime getDisembarkingTime() {
        return disembarkingTime;
    }

    /**
     * Method for getting the boarding time
     *
     * @return Boarding time
     */
    public ZonedDateTime getBoardingTime() {
        return boardingTime;
    }

    /**
     * Method for getting flight number
     *
     * @return Flight number
     */
    public int getFlightNumber() {
        return flightNumber;
    }

    /**
     * Method for getting reserved coach seats
     *
     * @return Number of reserved coach seats
     */
    public int getReservedCoachSeats() {
        return reservedCoachSeats;
    }

    /**
     * Method for getting reserved first class seats
     *
     * @return Number of reserved first class seats
     */
    public int getReservedFirstClassSeats() {
        return reservedFirstClassSeats;
    }

    /**
     * Method for getting legDuration
     *
     * @return Leg duration
     */
    public Duration getLegDuration() {
        return legDuration;
    }

    /**
     * Method for getting plane object
     *
     * @return Plane object
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * Method for getting coach price
     *
     * @return Coach price
     */
    public BigDecimal getCoachPrice() {
        return coachPrice;
    }

    /**
     * Method for getting first class price
     *
     * @return First class price
     */
    public BigDecimal getFirstClassPrice() {
        return firstClassPrice;
    }

    /**
     * Method to return the local boarding time
     *
     * @return Local boarding time
     */
    public ZonedDateTime getLocalBoardingTime() {
        return boardingAirport.convertGMTtoLocalTime(boardingTime);
    }

    /**
     * Method to return the local disembarking time
     *
     * @return Local disembarking time
     */
    public ZonedDateTime getLocalDisembarkingTime() {
        return disembarkingAirport.convertGMTtoLocalTime(disembarkingTime);
    }

    /**
     * Method to return the number of remaining seats on a leg
     *
     * @param seatType Type of seating
     * @return Remaining seats on leg
     */
    public int getRemainingSeats(String seatType) {
        if (seatType.equals("First Class")) return plane.firstClassSeats() - reservedFirstClassSeats;
        else return plane.coachSeats() - reservedCoachSeats;
    }

    /**
     * Method to set the disembarking airport
     *
     * @param newDisembarkingAirport The new disembarking airport object
     */
    public void setDisembarkingAirport(Airport newDisembarkingAirport) {
        disembarkingAirport = newDisembarkingAirport;
    }

    /**
     * Method to set the boarding airport
     *
     * @param newBoardingAirport The new boarding airport object
     */
    public void setBoardingAirport(Airport newBoardingAirport) {
        boardingAirport = newBoardingAirport;
    }

    /**
     * Method to set the disembarking time
     *
     * @param newDisembarkingTime The new disembarking time
     */
    public void setDisembarkingTime(ZonedDateTime newDisembarkingTime) {
        disembarkingTime = newDisembarkingTime;
    }

    /**
     * Method to set the boarding time
     *
     * @param newBoardingTime The new boarding time
     */
    public void setBoardingTime(ZonedDateTime newBoardingTime) {
        boardingTime = newBoardingTime;
    }

    /**
     * Method to set the flight number
     *
     * @param newFlightNumber The new flight number
     */
    public void setFlightNumber(int newFlightNumber) {
        flightNumber = newFlightNumber;
    }

    /**
     * Method to set the number of reserved coach seats
     *
     * @param newReservedCoachSeats The new number of reserved coach seats
     */
    public void setReservedCoachSeats(int newReservedCoachSeats) {
        reservedCoachSeats = newReservedCoachSeats;
    }

    /**
     * Method to set the number of reserved first class seats
     *
     * @param newReservedFirstClassSeats The new number of reserved first class seats
     */
    public void setReservedFirstClassSeats(int newReservedFirstClassSeats) {
        reservedFirstClassSeats = newReservedFirstClassSeats;
    }

    /**
     * Method to set the duration of the leg
     *
     * @param newLegDuration The new leg duration
     */
    public void setLegDuration(Duration newLegDuration) {
        legDuration = newLegDuration;
    }

    /**
     * Method to set the plane
     *
     * @param newPlane The new plane object
     */
    public void setPlane(Plane newPlane) {
        plane = newPlane;
    }

    /**
     * Method to set the coach price
     *
     * @param newCoachPrice The new coach price
     */
    public void setCoachPrice(BigDecimal newCoachPrice) {
        coachPrice = newCoachPrice;
    }

    /**
     * Method to set the first class price
     *
     * @param newFirstClassPrice The new first class price
     */
    public void setFirstClassPrice(BigDecimal newFirstClassPrice) {
        firstClassPrice = newFirstClassPrice;
    }

    /**
     * Determine if two leg objects are the same Leg
     *
     * Compare another object to this Leg and return true if the other
     * object specifies the same Leg as this object.
     *
     * @param obj is the object to compare against this object
     * @return true if the param is the same Leg as this, else false
     */
    @Override
    public boolean equals (Object obj) {
        // every object is equal to itself
        if (obj == this) return true;
        // null not equal to anything
        if (obj == null) return false;
        // can't be equal if obj is not an instance of Leg
        if (!(obj instanceof Leg)) return false;

        // if the flight numbers are equal, the legs are the same
        Leg rhs = (Leg) obj;
        return rhs.flightNumber == flightNumber;
    }
}