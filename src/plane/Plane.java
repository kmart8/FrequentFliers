package plane;

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