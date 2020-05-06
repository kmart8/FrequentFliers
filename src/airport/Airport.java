package airport;

import utils.Saps;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * This class holds values pertaining to a single Airport. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and 
 * setter methods.
 * 
 * @author Kevin Martin
 * @version 1.0 2020-04-30
 * @since 2020-04-30
 * 
 */
public class Airport implements Comparable<Airport>, Comparator<Airport> {
	
	/**
	 * Airport attributes as defined by the CS509 server interface XML
	 */
	/** Full name of the airport */
	private String mName;              
	
	/** Three character code of the airport */
	private String mCode;              
	
	/** Latitude of airport in decimal format */
	private double mLatitude;          
	
	/** Longitude of the airport in decimal format */
	private double mLongitude;
	
	/**
	 * Default constructor
	 * 
	 * Constructor without params. Requires object fields to be explicitly
	 * set using setter methods
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */	
	public Airport () {
		mName = "";
		mCode = "";
		mLatitude = Double.MAX_VALUE;
		mLongitude = Double.MAX_VALUE;
	}
	
	/**
	 * Set the airport name
	 * 
	 * @param name The human readable name of the airport
	 * @throws IllegalArgumentException if name is invalid
	 */
	public void name (String name) {
		if (isValidName (name))
			mName = name;
		else
			throw new IllegalArgumentException (name);
	}
	
	/**
	 * get the airport name
	 * 
	 * @return Airport name
	 */
	public String name () {
		return mName;
	}
	
	/**
	 * set the airport 3 letter code
	 * 
	 * @param code The 3 letter code for the airport
	 * @throws IllegalArgumentException if code is invalid
	 */
	public void code (String code) {
		if (isValidCode(code))
			mCode = code;
		else
			throw new IllegalArgumentException (code);
	}
	
	/**
	 * Get the 3 letter airport code
	 * 
	 * @return The 3 letter airport code
	 */
	public String code () {
		return mCode;
	}
	
	/**
	 * Set the latitude for the airport
	 * 
	 * @param latitude The north/south coordinate of the airport 
	 * @throws IllegalArgumentException if latitude is invalid
	 */
	public void latitude (double latitude) {
		if (isValidLatitude(latitude))
			mLatitude = latitude;
		else
			throw new IllegalArgumentException (Double.toString(latitude));
	}
	
	public void latitude (String latitude) {
		if (isValidLatitude(latitude))
			mLatitude = Double.parseDouble(latitude);
		else
			throw new IllegalArgumentException (latitude);
	}
	
	/**
	 * Get the latitude for the airport
	 * 
	 * @return The north/south coordinate of the airport 
	 */
	public double latitude () {
		return mLatitude;
	}
	
	/**
	 * Set the longitude for the airport
	 * 
	 * @param longitude The east/west coordinate of the airport
	 * @throws IllegalArgumentException if longitude is invalid
	 */
	public void longitude (double longitude) {
		if (isValidLongitude(longitude))
			mLongitude = longitude;
		else
			throw new IllegalArgumentException (Double.toString(longitude));
	}
	
	public void longitude (String longitude) {
		if (isValidLongitude(longitude))
			mLongitude = Double.parseDouble(longitude);
		else
			throw new IllegalArgumentException (longitude);
	}
	
	/**
	 * get the longitude for the airport
	 * 
	 * @return The east/west coordinate of the airport
	 */
	public double longitude () {
		return mLongitude;
	}

	/**
	 * Compare two airports based on 3 character code
	 * 
	 * This implementation delegates to the case insensitive version of string compareTo
	 * @return results of String.compareToIgnoreCase
	 */
	public int compareTo(Airport other) {
		return this.mCode.compareToIgnoreCase(other.mCode);
	}
	
	/**
	 * Compare two airports alphabetically for sorting, ordering
	 * 
	 * Delegates to airport1.compareTo for ordering by 3 character code
	 * 
	 * @param airport1 the first airport for comparison
	 * @param airport2 the second / other airport for comparison
	 * @return -1 if airport1  less than airport2, +1 if airport1 greater than airport2, zero ==
	 */
	public int compare(Airport airport1, Airport airport2) {
		return airport1.compareTo(airport2);
	}
	
	
	/**
	 * Determine if two airport objects are the same airport
	 * 
	 * Compare another object to this airport and return true if the other 
	 * object specifies the same airport as this object. String comparisons are
	 * case insensitive BOS is same as bos
	 * 
	 * @param obj is the object to compare against this object
	 * @return true if the param is the same airport as this, else false
	 */
	@Override
	public boolean equals (Object obj) {
		// every object is equal to itself
		if (obj == this)
			return true;
		
		// null not equal to anything
		if (obj == null)
			return false;
		
		// can't be equal if obj is not an instance of Airport
		if (!(obj instanceof Airport)) 
			return false;
		
		// if all fields are equal, the Airports are the same
		Airport rhs = (Airport) obj;
        return (rhs.mName.equalsIgnoreCase(mName)) &&
                (rhs.mCode.equalsIgnoreCase(mCode)) &&
                (rhs.mLatitude == mLatitude) &&
                (rhs.mLongitude == mLongitude);
    }
	
	/**
	 * Determine if object instance has valid attribute data
	 * 
	 * Verifies the name is not null and not an empty string. 
	 * Verifies code is 3 characters in length.
	 * Verifies latitude is between +90.0 north pole and -90.0 south pole.
	 * Verifies longitude is between +180.0 east prime meridian and -180.0 west prime meridian.
	 * 
	 * @return true if object passes above validation checks
	 * 
	 */
	public boolean isValid() {
		
		// If the name isn't valid, the object isn't valid
		if ((mName == null) || (mName == ""))
			return false;
		
		// If we don't have a 3 character code, object isn't valid
		if ((mCode == null) || (mCode.length() != 3))
			return false;
		
		// Verify latitude and longitude are within range
        return (!(mLatitude > Saps.MAX_LATITUDE)) && (!(mLatitude < Saps.MIN_LATITUDE)) &&
                (!(mLongitude > Saps.MAX_LONGITUDE)) && (!(mLongitude < Saps.MIN_LONGITUDE));
    }
	
	/**
	 * Check for invalid 3 character airport code
	 * 
	 * @param code is the airport code to validate
	 * @return false if null or not 3 characters in length, else assume valid and return true
	 */
	public boolean isValidCode (String code) {
		// If we don't have a 3 character code it can't be valid valid
		return (code != null) && (code.length() == 3);
	}
	
	/**
	 * Check for invalid airport name.
	 * 
	 * @param name is the name of the airport to validate
	 * @return false if null or empty string, else assume valid and return true
	 */
	public boolean isValidName (String name) {
		// If the name is null or empty it can't be valid
		return (name != null) && (name != "");
	}
	
	/**
	 * Check if latitude is valid
	 * 
	 * @param latitude is the latitude to validate
	 * @return true if within valid range for latitude
	 */
	public boolean isValidLatitude (double latitude) {
		// Verify latitude is within valid range
		return (!(latitude > Saps.MAX_LATITUDE)) && (!(latitude < Saps.MIN_LATITUDE));
	}
	
	/**
	 * Check if latitude is valid. 
	 * 
	 * @param latitude is the latitude to validate represented as a String
	 * @return true if within valid range for latitude
	 */
	public boolean isValidLatitude (String latitude) {
		double lat;
		try {
			lat = Double.parseDouble(latitude);
		} catch (NullPointerException | NumberFormatException ex) {
			return false;
		}
		return isValidLatitude (lat);
	}

	/**
	 * Check if longitude is valid
	 * 
	 * @param longitude is the longitude to validate
	 * @return true if within valid range for longitude
	 */
	public boolean isValidLongitude (double longitude) {
		// Verify longitude is within valid range
		return (!(longitude > Saps.MAX_LONGITUDE)) && (!(longitude < Saps.MIN_LONGITUDE));
	}
	
	/**
	 * Check if longitude is valid
	 * 
	 * @param longitude is the longitude to validate represented as a String
	 * @return true if within valid range for longitude
	 */
	public boolean isValidLongitude (String longitude) {
		double lon;
		try {
			lon = Double.parseDouble(longitude);
		} catch (NullPointerException | NumberFormatException ex) {
			return false;
		}
		return isValidLongitude (lon);
	}

	public ZonedDateTime convertGMTtoLocalTime(ZonedDateTime GMTZonedDateTime) {

		// Requires that the input time is GMT, or else the calculation will not be accurate
		// input a GMT ZonedDatetime
		// outputs a local ZonedDateTime for the respective airport object

		String zone = Saps.AIRPORT_TIMEZONES.get(mCode);

		//return ZonedDateTime.of(GMTZonedDateTime.toLocalDateTime(), TimeZone.getTimeZone(zone).toZoneId());
		// this displays the input time and the offset but doesn't explicitly show the converted time

		return ZonedDateTime.ofInstant(GMTZonedDateTime.toInstant(), ZoneId.of(zone));
	}

	public ZonedDateTime convertLocalDateTimetoGMT(LocalDateTime dateTime) {
		String zone = Saps.AIRPORT_TIMEZONES.get(mCode);
		ZonedDateTime localZonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of(zone));
		return ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
	}

}
