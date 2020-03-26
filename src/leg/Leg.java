package leg;

import airport.Airport;
import utils.LocalFlightDatabase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

public class Leg {

    // Fields
    public Airport disembarkingAirport;
    public Airport boardingAirport;
    public ZonedDateTime disembarkingTime;
    public ZonedDateTime boardingTime;
    public int flightNumber;
    public int reservedCoachSeats;
    public int reservedFirstClassSeats;
    public int legDuration;
    public String plane; // the plane model
    public BigDecimal coachPrice;
    public BigDecimal firstClassPrice;

    // Default Constructor
    public Leg() {
        disembarkingAirport = new Airport();
        boardingAirport = new Airport();
        disembarkingTime = null;
        boardingTime = null;
        flightNumber = Integer.MAX_VALUE;
        reservedCoachSeats = Integer.MAX_VALUE;
        reservedFirstClassSeats = Integer.MAX_VALUE;
        legDuration = Integer.MAX_VALUE;
        plane = "";
        coachPrice = new BigDecimal(-1);
        firstClassPrice =  new BigDecimal(-1);
    }
    // Constructor
    public Leg(Airport dAirport, Airport bAirport, ZonedDateTime dTime, ZonedDateTime bTime, int fNumber, int rCoach, int rFirstClass,
               int lDuration, String pNumber, BigDecimal cPrice, BigDecimal fCPrice) {

        disembarkingAirport = dAirport;
        boardingAirport = bAirport;
        disembarkingTime = dTime;
        boardingTime = bTime;
        flightNumber = fNumber;
        reservedCoachSeats = rCoach;
        reservedFirstClassSeats = rFirstClass;
        legDuration = lDuration;
        plane = pNumber;
        coachPrice = cPrice;
        firstClassPrice = fCPrice;
    }

    public String toString() {
        return disembarkingAirport.code()+' '+boardingAirport.code()+' '+disembarkingTime.toString()+' '+
                boardingTime.toString()+' '+flightNumber+' '+reservedCoachSeats+' '+
                reservedFirstClassSeats+' '+
                legDuration+' '+plane+' '+coachPrice+' '+firstClassPrice;

    }
    // get Methods
    public Airport getDisembarkingAirport() {
        return disembarkingAirport;
    }

    public Airport getBoardingAirport() {
        return boardingAirport;
    }

    public ZonedDateTime getDisembarkingTime() {
        return disembarkingTime;
    }

    public ZonedDateTime getBoardingTime() {
        return boardingTime;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getReservedCoachSeats() {
        return reservedCoachSeats;
    }

    public int getReservedFirstClassSeats() {
        return reservedFirstClassSeats;
    }

    public float getLegDuration() {
        return legDuration;
    }

    public String getPlane() {
        return plane;
    }

    public BigDecimal getCoachPrice() {
        return coachPrice;
    }

    public BigDecimal getFirstClassPrice() {
        return firstClassPrice;
    }

    // set Methods
    public void setDisembarkingTime(ZonedDateTime newDisembarkingTime) {
        disembarkingTime = newDisembarkingTime;
    }

    public void setBoardingTime(ZonedDateTime newBoardingTime) {
        boardingTime = newBoardingTime;
    }

    /**
     * Determine if two leg objects are the same leg
     *
     * Compare another object to this leg and return true if the other
     * object specifies the same leg as this object.
     * Legs are the same if they have the same flight number.
     *
     * @param obj is the object to compare against this object
     * @return true if the param is the same leg as this, else false
     */
    @Override
    public boolean equals (Object obj) {
        // every object is equal to itself
        if (obj == this)
            return true;

        // null not equal to anything
        if (obj == null)
            return false;

        // can't be equal if obj is not an instance of Leg
        if (!(obj instanceof Leg))
            return false;

        // if the flight numbers are equal, the Legs are the same
        Leg rhs = (Leg) obj;
        if (rhs.flightNumber == flightNumber)
            return true;
        return false;
    }
}
