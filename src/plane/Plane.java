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
    public String mManufacturer;

    /** Plane Model */
    public String mModel;

    /** # of first class seats*/
    public int mFirstClassSeats;

    /** # of coach seats */
    public int mCoachSeats;

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
}


//}