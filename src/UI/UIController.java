package UI;

import airport.Airport;
import leg.Legs;
import dao.LocalFlightDatabase;
import utils.Saps;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a controller for the ReservationApp viewer. Stored values are
 * assigned to a UIModel object which can be loaded or exported. This class parses and validates user input.
 *
 * @author Chris Collins
 * @version 1.0 2020-03-26
 * @since 2020-03-06
 *
 */
public class UIController {
    // Reference to the viewer
    private ReservationApp ui;

    // Reference to the model
    private UIModel savedInput;

    // Initialize a date format list for displaying/parsing dates to/from the user
    private List<DateTimeFormatter> acceptedDateFormats = new ArrayList<>(){{
        add(DateTimeFormatter.ofPattern ("MM/dd/yyyy"));
        add(DateTimeFormatter.ofPattern ("MM/d/yyyy"));
        add(DateTimeFormatter.ofPattern("M/dd/yyyy"));
        add(DateTimeFormatter.ofPattern ("M/d/yyyy"));
        add(DateTimeFormatter.ofPattern("MM/dd/yy"));
        add(DateTimeFormatter.ofPattern ("MM/d/yy"));
        add(DateTimeFormatter.ofPattern("M/dd/yy"));
        add(DateTimeFormatter.ofPattern ("M/d/yy"));}};


    // List of legs currently being displayed to the user
    private Legs displayList;

    /**
     * Constructor initializes a new data container and sets default values
     */
    public UIController(){
        savedInput = new UIModel();
        savedInput.numberOfLayovers(2);
        savedInput.numberOfPassengers(1);

        ui = new ReservationApp(this);
    }

    /**
     * Constructor loads a data container to set the initial state of the UIModel
     * @param loadedData UIData to load into the UIModel
     */
    public UIController(UIModel loadedData){
        savedInput = loadedData;
    }

    /**
     * Returns the UIData object used to store valid input
     * @return valid input stored in a UIData object
     */
    public UIModel getAcceptedInput(){
        return savedInput;
    }

    /**
     * Returns the number of passengers as a string
     * @return a String with the number of passengers
     */
    public String getNumberOfPassengers() {
        return Integer.toString(savedInput.numberOfPassengers());
    }

    /**
     * Attempts to update the stored number of passengers according to user input
     * @param numberOfPassengers a String with the number of passengers
     */
    public void setNumberOfPassengers(String numberOfPassengers) {
        // Attempt to parse the input string, make no changes on failure
        if (isValidPassengers(numberOfPassengers)) {
            int passengers = Integer.parseInt(numberOfPassengers);

            // If the number of passengers has not changed, make no changes and do not announce updates
            if (passengers == savedInput.numberOfPassengers())
                return;
            else {
                savedInput.numberOfPassengers(passengers);
                System.out.println("User input updated the number of passengers to " + numberOfPassengers);
            }
        }
    }

    /**
     * Returns the number of layovers as a string
     * @return a String with the number of layovers
     */
    public String getNumberOfLayovers() {
        return Integer.toString(savedInput.numberOfLayovers());
    }

    /**
     * Attempts to update the stored number of layovers according to user input
     * @param numberOfLayovers a String with the maximum number of layovers
     */
    public void setNumberOfLayovers(String numberOfLayovers) {
        // Attempt to parse the input string, make no changes on failure
        if (isValidLayovers(numberOfLayovers)) {
            int layovers = Integer.parseInt(numberOfLayovers);

            // If the number of layovers has not changed, make no changes and do not announce updates
            if (layovers == savedInput.numberOfLayovers())
                return;
            else {
                savedInput.numberOfLayovers(layovers);
                System.out.println("User input updated the number of layovers to " + numberOfLayovers);
            }
        }
    }

    /**
     * Returns the departure date, formatted as a string
     * @return a string with the departure date
     */
    public String getSeatingType() {
        return savedInput.seatingType();
    }

    /**
     * Stores the user selection for seating type
     * @param seatingType  a string with the selected seating type
     */
    public void setSeatingType(String seatingType) {
        savedInput.seatingType(seatingType);
    }

    /**
     * Returns the full name of the arrival airport
     * @return a string with the airport name
     */
    public String getArrivalAirport() {
        if (savedInput.arrivalAirport() == null)
            return "";
        else
            return savedInput.arrivalAirport().name();
    }

    /**
     * Attempts to update the stored arrival airport according to user input
     * @param arrivalAirport a String with the airport code or name
     */
    public void setArrivalAirport(String arrivalAirport) {
        // Attempt to parse the input string, make no changes on failure
        Airport airport = validateAirport(arrivalAirport);

        // If the date was already null, make no changes
        if(airport == null) {
            if (savedInput.arrivalAirport() != null) {
                savedInput.arrivalAirport(null);
                System.out.println("Departure airport removed");
            }
            return;
        }

        // If the new arrival airport is the same as the departure airport, make no changes
        if(airport.equals(savedInput.departureAirport())) {
            System.out.println("Warning: User input of " + arrivalAirport + " cannot be the same as the arrival airport");
            return;
        }

        // If the new airport is different from the previous one, overwrite the stored value
        if(!airport.equals(savedInput.arrivalAirport())) {
            savedInput.arrivalAirport(airport);
            System.out.println("User input updated the arrival airport to " + airport.name());
        }
    }

    /**
     * Returns the full name of the departure airport
     * @return a string with the airport name
     */
    public String getDepartureAirport() {
        if (savedInput.departureAirport() == null)
            return "";
        else
            return savedInput.departureAirport().name();
    }

    /**
     * Attempts to update the stored departure airport according to user input
     * @param departureAirport a String with the airport code or name
     */
    public void setDepartureAirport(String departureAirport) {
        // Attempt to parse the input string, make no changes on failure
        Airport airport = validateAirport(departureAirport);

        // If the date was already null, make no changes
        if(airport == null) {
            if (savedInput.departureAirport() != null) {
                savedInput.departureAirport(null);
                System.out.println("Departure airport removed");
            }
            return;
        }

        // If the new departure airport is the same as the arrival airport, make no changes
        if(airport.equals(savedInput.arrivalAirport())) {
            System.out.println("Warning: User input of " + departureAirport + " cannot be the same as the arrival airport");
            return;
        }

        // If the new airport is different from the previous one, overwrite the stored value
        if(!airport.equals(savedInput.departureAirport())) {
            savedInput.departureAirport(airport);
            System.out.println("User input updated the departure airport to " + airport.name());
        }
    }

    /**
     * Returns the arrival date, formatted as a string
     * @return a string with the arrival date
     */
    public String getArrivalDate() {
        if(savedInput.arrivalDate() != null)
            return acceptedDateFormats.get(0).format(savedInput.arrivalDate());
        else
            return "";
    }

    /**
     * Attempts to update the stored arrival date according to user input
     * @param arrivalDate a String with the formatted date
     */
    public void setArrivalDate(String arrivalDate) {
        // Attempt to parse the input string, make no changes on failure
        ZonedDateTime date = validateDate(arrivalDate);

        // If the date was already null, make no changes
        if(date == null) {
            if (savedInput.arrivalDate() != null) {
                savedInput.arrivalDate(null);
                System.out.println("Arrival date removed");
            }
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (savedInput.arrivalDate()== null || !date.equals(savedInput.arrivalDate())) {
            // Make sure the new arrival date is after the departure date
            if(savedInput.departureDate() == null || date.compareTo(savedInput.departureDate()) >= 0) {
                savedInput.arrivalDate(date);
                System.out.println("User input updated the arrival date to " + arrivalDate);
            }else
                System.out.println("Warning: User input of " + arrivalDate + " is before the departure date");
        }
    }

    /**
     * Returns the departure date, formatted as a string
     * @return a string with the departure date
     */
    public String getDepartureDate() {
        if (savedInput.departureDate() != null)
            return acceptedDateFormats.get(0).format(savedInput.departureDate());
        else
            return "";
    }

    /**
     * Attempts to update the stored departure date according to user input
     * @param departureDate a String with the formatted date
     */
    public void setDepartureDate(String departureDate) {
        // Attempt to parse the input string
        ZonedDateTime date = validateDate(departureDate);

        // If the date was already null, make no changes
        if(date == null) {
            if (savedInput.departureDate() != null) {
                savedInput.departureDate(null);
                System.out.println("Arrival date removed");
            }
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (savedInput.departureDate()== null || !date.equals(savedInput.departureDate())) {
            // Make sure the new departure date is before the arrival date
            if(savedInput.arrivalDate() == null || date.compareTo(savedInput.arrivalDate()) <= 0) {
                savedInput.departureDate(date);
                System.out.println("User input updated the departure date to " + departureDate);
            }else
                System.out.println("Warning: User input of " + departureDate + " is after the arrival date");
        }
    }

    public void setDisplayList(Legs displayList){
        this.displayList = displayList;
    }

    public Legs getDisplayList(){
        return displayList;
    }

    /**
     * Attempts to parse and validate a date String as a ZonedDateTime
     * @param date a String with the formatted date
     *
     * @return a valid ZonedDateTime, or null if the String could not be parsed or the date is outside the valid range
     */
    private ZonedDateTime validateDate(String date){
        ZonedDateTime parsedDate = null;
        // If the string is empty, then set the date to null
        if (date == null || date.equals(""))
            return null;

        // Try to parse the date string according to accepted input formats for date values
        for(DateTimeFormatter formatter : acceptedDateFormats){
            try {
                parsedDate = ZonedDateTime.of(LocalDate.parse(date, formatter), LocalTime.MIN, ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
                break;
            } catch (DateTimeParseException ex1) {}
        }

        // If the parsed date was never assigned, then none of the parses succeeded and the input format was invalid
        if (parsedDate == null) {
            System.out.println("Warning: User input of " + date + " is invalid syntax for the date, should be: MM/dd/yyyy");
            return null;
        }

        // Validate the range of the parsed date
        if (parsedDate.compareTo(Saps.EARLIEST_DATE) >= 0 && parsedDate.compareTo(Saps.LATEST_DATE) < 0){
            return parsedDate;
        } else{
            System.out.println("Warning: User input of " + date + " is outside the valid range of 05/01/2020 through 5/31/2020");
            return null;
        }
    }

    /**
     * Attempts to parse and validate an airport String as an Airport object
     * @param airport a String containing the name or code of an airport
     *
     * @return a valid Airport, or null if the String did not match any known airports
     */
    private Airport validateAirport(String airport){
        Airport parsedAirport = null;
        // If the string is empty, then set the airport to null
        if (airport == null || airport.equals(""))
            return null;

        // Try to match the provided string with possible airports
        parsedAirport = LocalFlightDatabase.getInstance().getAirportByString(airport);

        // If no matching airport is returned, then the user input was not a valid airport
        if(parsedAirport == null) {
            System.out.println("Warning: User input of " + airport + " was not recognized as a valid airport, should be 3 letter airport code");
            return null;
        }

        return  parsedAirport;
    }

    /**
     * Attempts to parse and validate an number of layovers from a String
     * @param layovers a String containing the maximum number of layovers
     *
     * @return false if the String could not be parsed as an Integer or the number of layovers is outside the valid range, otherwise return true
     */
    private boolean isValidLayovers(String layovers){
        // Attempt to parse the input string
        int parsedLayovers;
        try {
            parsedLayovers = Integer.parseInt(layovers);
        } catch (NumberFormatException ex) {
            System.out.println("Warning: User input of " + layovers + " is invalid syntax for the number of layovers");
            return false;
        }

        // Confirm the number of layovers is within the acceptable range
        if (parsedLayovers >= Saps.MIN_LAYOVERS && parsedLayovers <= Saps.MAX_LAYOVERS) {
            return true;
        }else {
            System.out.println("Warning: User input of " + layovers + " is outside the valid range of 0-2");
            return false;
        }
    }

    /**
     * Attempts to parse and validate an number of passengers from a String
     * @param passengers a String containing the maximum number of passengers
     *
     * @return false if the String could not be parsed as an Integer or the number of passengers is outside the valid range, otherwise return true
     */
    private boolean isValidPassengers(String passengers){
        // Attempt to parse the input string
        int parsedLayovers;
        try {
            parsedLayovers = Integer.parseInt(passengers);
        } catch (NumberFormatException ex) {
            System.out.println("Warning: User input of " + passengers + " is invalid syntax for the number of passengers");
            return false;
        }

        // Confirm the number of layovers is within the acceptable range
        if (parsedLayovers >= Saps.MIN_PASSENGERS && parsedLayovers <= Saps.MAX_PASSENGERS) {
            return true;
        }else {
            System.out.println("Warning: User input of " + passengers + " is outside the valid range of 1-15");
            return false;
        }
    }

}
