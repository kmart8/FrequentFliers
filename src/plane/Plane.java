package plane;

/**
 * This class holds values pertaining to a single Plane.
 *
 * Class member attributes are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and 
 * setter methods.
 *
 * @author blake
 * @version 1.3 2019-01-21
 * @since 2016-02-24
 *
 */
public class Plane {
        /** Plane Manufacturer */
    private String mManufacturer;

    /** Plane Model */
    private String mModel;

    /** # of first class seats*/
    private int mFirstClassSeats;

    /** # of coach seats */
    private int mCoachSeats;

    /**
     * Default constructor.
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
     * Set the manufacturer for the plane.
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
     * get the manufacturer name.
     *
     * @return Plane manufacturer
     */
    public String manufacturer () {
        return mManufacturer;
    }

    /**
     * Set the model for the plane.
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
     * get the model.
     *
     * @return Plane model
     */
    public String model () { return mModel; }

    /**
     * Set the number of coach seats for the plane.
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

    /**
     * Get the number of coach seats on the plane.
     *
     * @return The total number of coach seats
     */
    public int coachSeats () {
        return mCoachSeats;
    }

    /**
     * Set the number of first class seats for the plane.
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

    /**
     * Get the number of first class seats on the plane.
     *
     * @return The total number of first class seats
     */
    public int firstClassSeats () {
        return mFirstClassSeats;
    }

    /**
     * Check for invalid plane manufacturer.
     *
     * @param manufacturer is the name of the manufacturer to validate
     * @return false if null or empty string, else assume valid and return true
     */
    public static boolean isValidManufacturer (String manufacturer) {
        // If the name is null or empty it can't be valid
        return (manufacturer != null) && (manufacturer != "");
    }

    /**
     * Check for invalid plane model.
     *
     * @param model is the model to validate
     * @return false if null or empty string, else assume valid and return true
     */
    public static boolean isValidModel(String model) {
        // If the name is null or empty it can't be valid
        return (model != null) && (model != "");
    }

    /**
     * Check if number of seats is valid.
     *
     * @param seats is the number of seats to validate
     * @return true if number of seats is positive
     */
    public static boolean isValidSeats(int seats) {
        // Verify seats is within valid range
        return seats >= 0;
    }

    /**
     * Determine if two plane objects are the same plane
     *
     * @param obj is the plane to compare against this plane
     * @return true if the planes are the same and false if not
     */
    @Override
    public boolean equals (Object obj) {
        // every object is equal to itself
        if (obj == this) return true;

        // null not equal to anything
        if (obj == null) return false;

        // can't be equal if obj is not an instance of Plane
        if (!(obj instanceof Plane)) return false;

        // if all fields are equal, the Planes are the same
        Plane rhs = (Plane) obj;
        return (rhs.mManufacturer.equalsIgnoreCase(mManufacturer)) &&
                (rhs.mModel.equalsIgnoreCase(mModel));
    }

}


