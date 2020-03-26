/**
 * 
 */
package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author blake
 * 
 * System Adaptive Parameters (SAPS)
 * 
 * Constants and values for limits, bounds and configuration of system
 *
 */
public class Saps {
	/**
	 * Constant value used for server access
	 */
	public static final String TEAM_NAME = "FrequentFliers";

	/**
	 * Constant values used for latitude and longitude range validation
	 */
	public static final double MAX_LATITUDE = 90.0;
	public static final double MIN_LATITUDE = -90.0;
	public static final double MAX_LONGITUDE = 180.0;
	public static final double MIN_LONGITUDE = -180.0;

	/**
	 * Constant values used for passenger and layover range validation
	 */
	public static final int MAX_PASSENGERS = 15;
	public static final int MIN_PASSENGERS = 1;
	public static final double MAX_LAYOVERS = 2;
	public static final double MIN_LAYOVERS = 0;

	/**
	 * Constant values used for seating selection
	 */
	public static final  String[] SEATING_TYPES = {"Any","Coach","First Class"};

	/**
	 * Constant values used for date range validation
	 */
	public static final ZonedDateTime EARLIEST_DATE = ZonedDateTime.of(LocalDateTime.of(2020,5,1,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
	public static final ZonedDateTime LATEST_DATE = ZonedDateTime.of(LocalDateTime.of(2020,6,1,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
}
