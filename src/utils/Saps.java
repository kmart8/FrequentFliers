/**
 * 
 */
package utils;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

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
	 * Identification used for server access
	 */
	public static final String TEAM_NAME = "FrequentFliers";

	/**
	 * Universal Resource Locator (web address) of the CS509 reservation server
	 */
	public static final String SERVER_URL =  "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";

	/**
	 * Upper limit used for latitude validation
	 */
	public static final double MAX_LATITUDE = 90.0;
	/**
	 * Lower limit used for latitude validation
	 */
	public static final double MIN_LATITUDE = -90.0;
	/**
	 * Upper limit used for longitude validation
	 */
	public static final double MAX_LONGITUDE = 180.0;
	/**
	 * Lower limit used for longitude validation
	 */
	public static final double MIN_LONGITUDE = -180.0;

	/**
	 * Upper limit used for passenger validation
	 */
	public static final int MAX_PASSENGERS = 15;
	/**
	 * Lower limit used for passenger validation
	 */
	public static final int MIN_PASSENGERS = 1;
	/**
	 * Upper limit used for layover validation
	 */
	public static final double MAX_LAYOVERS = 2;
	/**
	 * Lower limit used for layover validation
	 */
	public static final double MIN_LAYOVERS = 0;

	/**
	 * Names used for seating selection
	 */
	public static final ArrayList<String> SEATING_TYPES = new ArrayList<>(Arrays.asList("Coach", "First Class"));

	/**
	 * Lower limit used for date range validation
	 */
	public static final ZonedDateTime EARLIEST_DATE = ZonedDateTime.of(LocalDateTime.of(2020,5,10,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
	/**
	 * Upper limit used for date range validation
	 */
	public static final ZonedDateTime LATEST_DATE = ZonedDateTime.of(LocalDateTime.of(2020,6,1,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
	/**
	 * Lower limit used for layover time
	 */
	public static final Duration MIN_LAYOVER_TIME = Duration.ofMinutes(15);
	/**
	 * Upper limit used for layover time
	 */
	public static final Duration MAX_LAYOVER_TIME = Duration.ofHours(4);
	/**
	 * The duration to wait before alerting the user that the program is busy
	 */
	public static final Duration BUSY_WAIT_TIME = Duration.ofSeconds(3);
}
