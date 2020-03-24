package UI;

import airport.Airport;
import driver.FlightBuilder;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UIData {
    // Stores the team name
    private String teamName = "FrequentFliers";

    // Create a ZoneId object that represents GMT
    private ZoneId gmt = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0));

    // Initialize a date format for displaying/parsing dates to/from the user
    private DateTimeFormatter dateStyle1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // Create ZonedDateTimes to limit user input
    private ZonedDateTime earliestDate = ZonedDateTime.of(LocalDateTime.of(2020,05,01,0,0), gmt);
    private ZonedDateTime latestDate = ZonedDateTime.of(LocalDateTime.of(2020,06,1,0,0), gmt);

    // Initialize empty dates for departure and arrival
    private ZonedDateTime departureDate = null;
    private ZonedDateTime arrivalDate = null;

    // Initialize empty airports for departure and arrival
    private Airport departureAirport = null;
    private Airport arrivalAirport = null;

    // Initialize empty seating type
    private String seatingType = null;

    // Create a minimum and maximum number of passengers to limit user input
    private int maximumPassengers = 15;
    private int minimumPassengers = 1;

    // Initialize the default number of passengers to 1
    private int numberOfPassengers = 1;

    // Create a minimum and maximum number of layovers to limit user input
    private int maximumLayovers = 2;
    private int minimumLayovers = 0;

    // Initialize the default number of layovers to 2
    private int numberOfLayovers = 2;

    // Initialize a list of seating types
    private String[] seatingPossibilities = {"Any","Coach","First Class"};

    /**
     * Returns the name of the development team
     * @return a string with the name of the development team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the number of passengers as a string
     * @return a string with the number of passengers
     */
    public String getNumberOfPassengers() {
        return Integer.toString(numberOfPassengers);
    }

    /**
     * Attempts to parse and validate the input string as a number of passengers
     * @param numberOfPassengers a non-empty String with the number of passengers
     */
    public void setNumberOfPassengers(String numberOfPassengers) {
        // Attempt to parse the input string, make no changes on failure
        int passengers;
        try {
            passengers = Integer.parseInt(numberOfPassengers);
        } catch (NumberFormatException ex) {
            System.out.println("Warning: User input of " + numberOfPassengers + " is invalid syntax for the number of passengers");
            return;
        }

        // If the number of layovers has not changed, make no changes and do not announce updates
        if (passengers == this.numberOfPassengers)
            return;

        // If the number of passengers is within the acceptable range, overwrite the stored value
        if (passengers >= minimumPassengers && passengers <= maximumPassengers) {
            this.numberOfPassengers = passengers;
            System.out.println("User input updated the number of passengers to " + numberOfPassengers);
        }else
            System.out.println("Warning: User input of " + numberOfPassengers + " is invalid for the number of passengers");
    }

    /**
     * Returns the number of layovers as a string
     * @return a string with the number of layovers
     */
    public String getNumberOfLayovers() {
        return Integer.toString(numberOfLayovers);
    }

    /**
     * Attempts to parse and validate the input string as a number of layovers
     * @param numberOfLayovers a non-empty String with the number of layovers
     */
    public void setNumberOfLayovers(String numberOfLayovers) {
        // Attempt to parse the input string, make no changes on failure
        int layovers;
        try {
            layovers = Integer.parseInt(numberOfLayovers);
        } catch (NumberFormatException ex) {
            System.out.println("Warning: User input of " + numberOfLayovers + " is invalid syntax for the number of layovers");
            return;
        }

        // If the number of layovers has not changed, make no changes and do not announce updates
        if (layovers == this.numberOfLayovers)
            return;

        // If the number of layovers is within the acceptable range, overwrite the stored value
        if (layovers >= minimumLayovers && layovers <= maximumLayovers) {
            this.numberOfLayovers = layovers;
            System.out.println("User input updated the number of layovers to " + numberOfLayovers);
        }else
            System.out.println("Warning: User input of " + numberOfLayovers + " is invalid for the number of layovers");
    }

    /**
     * Returns the departure date, formatted as a string
     * @return a string with the departure date
     */
    public String getSeatingType() {
        return seatingType;
    }

    /**
     * Returns the list of seating types
     * @return a String[] with the names of seating types
     */
    public String[] getSeatingPossibilities() {
        return seatingPossibilities;
    }

    /**
     * Stores the user selection for seating type
     * @param seatingType  a string with the selected seating type
     */
    public void setSeatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    /**
     * Returns the full name of the arrival airport
     * @return a string with the airport name
     */
    public String getArrivalAirport() {
        if (arrivalAirport == null)
            return "";
        else
            return arrivalAirport.name();
    }

    /**
     * Attempts to parse and validate the input string as an airport name/code
     * @param arrivalAirport a String with the arrival airport name or code
     */
    public void setArrivalAirport(String arrivalAirport) {
        // If the string is empty, then set the airport to null
        if (arrivalAirport.equals("")){
            // If the airport was already null, make no changes
            if (this.arrivalAirport != null) {
                this.arrivalAirport = null;
                System.out.println("User removed arrival airport");
            }
            return;
        }

        // If the new arrival airport is the same as the departure airport, make no changes
        if(this.departureAirport != null && (arrivalAirport.equals(departureAirport.name()) || arrivalAirport.equals(departureAirport.name()))) {
            System.out.println("Warning: User input of " + arrivalAirport + " cannot be the same as the departure airport");
            return;
        }

        // If the new airport is different from the previous one, validate the airport string and overwrite the stored value
        if(this.arrivalAirport == null || (!arrivalAirport.equals(this.arrivalAirport.name()) && !arrivalAirport.equals(this.arrivalAirport.code()))) {
            Airport airport = FlightBuilder.getAirportByString(arrivalAirport);
            if(airport != null) {
                this.arrivalAirport = airport;
                System.out.println("User input updated the arrival airport to " + airport.name());
            }else
                System.out.println("Warning: User input of " + arrivalAirport + " was not recognized as a valid airport");
        }
    }

    /**
     * Returns the full name of the departure airport
     * @return a string with the airport name
     */
    public String getDepartureAirport() {
        if (departureAirport == null)
            return "";
        else
            return departureAirport.name();
    }

    /**
     * Attempts to parse and validate the input string as an airport name/code
     * @param departureAirport a String with the departure airport name or code
     */
    public void setDepartureAirport(String departureAirport) {
        // If the string is empty, then set the airport to null
        if (departureAirport.equals("")){
            // If the airport was already null, make no changes
            if (this.departureAirport != null) {
                this.departureAirport = null;
                System.out.println("User removed departure airport");
            }
            return;
        }

        // If the new departure airport is the same as the arrival airport, make no changes
        if(this.arrivalAirport != null && (!departureAirport.equals(arrivalAirport.name()) || !departureAirport.equals(arrivalAirport.name()))) {
            System.out.println("Warning: User input of " + departureAirport + " cannot be the same as the arrival airport");
            return;
        }

        // If the new airport is different from the previous one, validate the airport string and overwrite the stored value
        if(this.departureAirport == null || (!departureAirport.equals(this.departureAirport.name()) && !departureAirport.equals(this.departureAirport.code()))) {
            Airport airport = FlightBuilder.getAirportByString(departureAirport);
            if(airport != null) {
                this.departureAirport = airport;
                System.out.println("User input updated the departure airport to " + airport.name());
            }else
                System.out.println("Warning: User input of " + departureAirport + " was not recognized as a valid airport");
        }
    }

    /**
     * Returns the arrival date, formatted as a string
     * @return a string with the arrival date
     */
    public String getArrivalDate() {
        if(arrivalDate != null)
            return dateStyle1.format(arrivalDate);
        else
            return "";
    }

    /**
     * Attempts to parse and validate the input arrival date string as a zoned date time
     * @param arrivalDate a String with the formatted date
     */
    public void setArrivalDate(String arrivalDate) {
        // If the string is empty, then set the date to null
        if (arrivalDate.equals("")){
            // If the date was already null, make no changes
            if (this.arrivalDate != null) {
                this.arrivalDate = null;
                System.out.println("User removed arrival date");
            }
            return;
        }

        // Attempt to parse the input string, make no changes on failure
        ZonedDateTime date;
        try {
            date = ZonedDateTime.of(LocalDate.parse(arrivalDate, dateStyle1), LocalTime.MIN, gmt);
        } catch (DateTimeParseException ex) {
            System.out.println("Warning: User input of " + arrivalDate + " is invalid syntax for the arrival date");
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (this.arrivalDate == null || date.compareTo(this.arrivalDate) != 0) {
            if(date.compareTo(earliestDate) >= 0 && date.compareTo(latestDate) < 0 && (departureDate == null || date.compareTo(departureDate) > 0)) {
                this.arrivalDate = date;
                System.out.println("User input updated the arrival date to " + arrivalDate);
            }else
                System.out.println("Warning: User input of " + arrivalDate + " is invalid for the arrival date");
        }
    }

    /**
     * Returns the departure date, formatted as a string
     * @return a string with the departure date
     */
    public String getDepartureDate() {
        if (departureDate != null)
            return dateStyle1.format(departureDate);
        else
            return "";
    }

    /**
     * Attempts to parse and validate the input departure date string as a zoned date time
     * @param departureDate a String with the formatted date
     */
    public void setDepartureDate(String departureDate) {
        // If the string is empty, then set the date to null
        if (departureDate.equals("")){
            // If the date was already null, make no changes
            if (this.departureDate != null) {
                this.departureDate = null;
                System.out.println("User removed departure date");
            }
            return;
        }

        // Attempt to parse the input string, make no changes on failure
        ZonedDateTime date;
        try {
            date = ZonedDateTime.of(LocalDate.parse(departureDate, dateStyle1), LocalTime.MIN, gmt);
        } catch (DateTimeParseException ex) {
            System.out.println("Warning: User input of " + departureDate + " is invalid syntax for the departure date");
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (this.departureDate == null || date.compareTo(this.departureDate) != 0) {
            if(date.compareTo(earliestDate) >= 0 && date.compareTo(latestDate) < 0 && (arrivalDate == null || date.compareTo(arrivalDate) < 0)) {
                this.departureDate = date;
                System.out.println("User input updated the departure date to " + departureDate);
            }else
                System.out.println("Warning: User input of " + departureDate + " is invalid for the departure date");
        }
    }
}
