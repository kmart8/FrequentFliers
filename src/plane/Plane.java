/**
 *
 */
package plane;

import java.util.Comparator;

import utils.Saps;

/**
 * This class holds values pertaining to a single Plane. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and 
 * setter methods.
 *
 * @author blake
 * @version 1.3 2019-01-21
 * @since 2016-02-24
 *
 */
public class Plane {

    /**
     * Plane attributes as defined by the CS509 server interface XML
     */
    /** Plane Manufacturer */
    private String mManufacturer;

    /** Plane Model */
    private String mModel;

    /** # of first class seats*/
    private int mFirstClassSeats;

    /** # of coach seats */
    private int mCoachSeats;

    /**
     * Default constructor
     *
     * Constructor without params. Requires object fields to be explicitly
     * set using setter methods
     *
     * @pre None
     * @post member attributes are initialized to invalid default values
     */
    public Plane () {
        mManufacturer = "";
        mModel = "";
        mFirstClassSeats = Integer.MAX_VALUE;
        mCoachSeats = Integer.MAX_VALUE;
    }

    /**
     * Initializing constructor.
     *
     * All attributes are initialized with specified input values following validation for reasonableness.
     *
     * @param manufacturer The human readable name of the airport
     * @param model The 3 letter code for the airport
     * @param firstClassSeats The north/south coordinate of the airport
     * @param coachSeats The east/west coordinate of the airport
     *
     * @pre code is a 3 character string, name is not empty, latitude and longitude are valid values
     * @post member attributes are initialized with input parameter values
     * @throws IllegalArgumentException if any parameter is determined invalid
     */
    public Plane (String manufacturer, String model, int firstClassSeats, int coachSeats) {
        /**if (!isValidName(model))
            throw new IllegalArgumentException(manufacturer);
        if (!isValidName(model))
            throw new IllegalArgumentException(model);
        //if (!isValidLatitude(latitude))
            //throw new IllegalArgumentException(Double.toString(latitude));
        //if (!isValidLongitude(longitude))
            //throw new IllegalArgumentException(Double.toString(longitude));**/

        mManufacturer = manufacturer;
        mModel = model;
        mFirstClassSeats = firstClassSeats;
        mCoachSeats = coachSeats;
    }

    /**
     * Initializing constructor with all params as type String. Converts latitude and longitude
     * values to required double format before delegating to ctor.
     *
     * @param manufacturer The human readable name of the airport
     * @param model The 3 letter code for the airport
     * @param firstClassSeats is the string representation of latitude in decimal format
     * @param coachSeats is the String representation of the longitude in decimal format
     *
     * @pre the latitude and longitude are valid String representations of valid lat/lon values
     * @post member attributes are initialized with input parameter values
     * @throws IllegalArgumentException is any parameter is invalid
     */
    public Plane (String manufacturer, String model, String firstClassSeats, String coachSeats) {
        int tmpFirstClassSeats, tmpCoachSeats;
        try {
            tmpFirstClassSeats = Integer.parseInt(firstClassSeats);
        } catch (NullPointerException | NumberFormatException ex) {
            throw new IllegalArgumentException("Latitude must be between -90.0 and +90.0", ex);
        }

        try {
            tmpCoachSeats = Integer.parseInt(coachSeats);
        } catch (NullPointerException | NumberFormatException ex) {
            throw new IllegalArgumentException("Longitude must be between -180.0 and +180.0", ex);
        }

        // validate converted values
        /**if (!isValidName(name))
         throw new IllegalArgumentException(name);
         if (!isValidCode(code))
         throw new IllegalArgumentException(code);
         if (!isValidLatitude(tmpLatitude))
         throw new IllegalArgumentException(latitude);
         if (!isValidLongitude(tmpLongitude))
         throw new IllegalArgumentException(longitude);**/

        mManufacturer = manufacturer;
        mModel = model;
        mFirstClassSeats = tmpFirstClassSeats;
        mCoachSeats = tmpCoachSeats;
    }

    public String toString() {
        return mManufacturer+" "+mModel+" "+mFirstClassSeats+" "+mCoachSeats;
    }

    /**
     * Set the manufacturer for the plane
     *
     * @param manufacturer The manufacturer name
     * @throws IllegalArgumentException if manufacturer is invalid
     */
    public void manufacturer (String manufacturer) {
        if (isValidManufacturer(manufacturer))
            mManufacturer = manufacturer;
        else
            throw new IllegalArgumentException (manufacturer);
    }

    /**
     * get the manufacturer name
     *
     * @return Plane manufacturer
     */
    public String manufacturer () {
        return mManufacturer;
    }

    /**
     * Set the model for the plane
     *
     * @param model The plane model
     * @throws IllegalArgumentException if model is invalid
     */
    public void model (String model) {
        if (isValidModel(model))
            mModel = model;
        else
            throw new IllegalArgumentException (model);
    }

    /**
     * get the model
     *
     * @return Plane model
     */
    public String model () { return mModel; }

    /**
     * Set the number of coach seats for the plane
     *
     * @param seats The number of coach seats
     * @throws IllegalArgumentException if number of seats invalid
     */
    public void coachSeats (int seats) {
        if (isValidSeats(seats))
            mCoachSeats = seats;
        else
            throw new IllegalArgumentException (Integer.toString(seats));
    }

    public void coachSeats (String seats) {
        if (isValidSeats(seats))
            mCoachSeats = Integer.parseInt(seats);
        else
            throw new IllegalArgumentException (seats);
    }

    /**
     * Get the number of coach seats on the plane
     *
     * @return The total number of coach seats
     */
    public double coachSeats () {
        return mCoachSeats;
    }

    /**
     * Set the number of first class seats for the plane
     *
     * @param seats The number of first class seats
     * @throws IllegalArgumentException if number of seats invalid
     */
    public void firstClassSeats (int seats) {
        if (isValidSeats(seats))
            mFirstClassSeats = seats;
        else
            throw new IllegalArgumentException (Integer.toString(seats));
    }

    public void firstClassSeats (String seats) {
        if (isValidSeats(seats))
            mFirstClassSeats = Integer.parseInt(seats);
        else
            throw new IllegalArgumentException (seats);
    }

    /**
     * Get the number of first class seats on the plane
     *
     * @return The total number of first class seats
     */
    public double firstClassSeats () {
        return mFirstClassSeats;
    }

    /**
     * Check for invalid plane manufacturer.
     *
     * @param manufacturer is the name of the manufacturer to validate
     * @return false if null or empty string, else assume valid and return true
     */
    public boolean isValidManufacturer (String manufacturer) {
        // If the name is null or empty it can't be valid
        return (manufacturer != null) && (manufacturer != "");
    }

    /**
     * Check for invalid plane model.
     *
     * @param model is the model to validate
     * @return false if null or empty string, else assume valid and return true
     */
    public boolean isValidModel (String model) {
        // If the name is null or empty it can't be valid
        return (model != null) && (model != "");
    }

    /**
     * Check if number of seats is valid
     *
     * @param seats is the number of seats to validate
     * @return true if number of seats is positive
     */
    public boolean isValidSeats (int seats) {
        // Verify seats is within valid range
        return seats >= 0;
    }

    /**
     * Check if number of seats is valid
     *
     * @param seats is the number of seats represented as a String
     * @return true if number of seats is positive
     */
    public boolean isValidSeats (String seats) {
        int parsedSeats;
        try {
            parsedSeats = Integer.parseInt(seats);
        } catch (NullPointerException | NumberFormatException ex) {
            return false;
        }
        return isValidSeats (parsedSeats);
    }
}


//}