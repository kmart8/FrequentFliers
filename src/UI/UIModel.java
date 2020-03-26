package UI;

import airport.Airport;
import leg.Legs;
import utils.LocalFlightDatabase;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class UIModel{
    //
    private UIData savedInput;

    // Create a ZoneId object that represents GMT
    private ZoneId gmt = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0));

    // Initialize a date format list for displaying/parsing dates to/from the user
    private List<DateTimeFormatter> acceptedDateFormats = new ArrayList<>();

    // Create ZonedDateTimes to limit user input
    private ZonedDateTime earliestDate = ZonedDateTime.of(LocalDateTime.of(2020,5,1,0,0), gmt);
    private ZonedDateTime latestDate = ZonedDateTime.of(LocalDateTime.of(2020,6,1,0,0), gmt);

    // Create a minimum and maximum number of passengers to limit user input
    private int maximumPassengers = 15;
    private int minimumPassengers = 1;


    // Create a minimum and maximum number of layovers to limit user input
    private int maximumLayovers = 2;
    private int minimumLayovers = 0;

    // Initialize a list of seating types
    private String[] seatingPossibilities = {"Any","Coach","First Class"};

    //
    private Legs displayList;

    /**
     * Constructor initializes a new data container and sets default values
     */
    public UIModel(){
        savedInput = new UIData();
        savedInput.numberOfLayovers(2);
        savedInput.numberOfPassengers(1);
        acceptedDateFormats.add(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        acceptedDateFormats.add(DateTimeFormatter.ofPattern("M/dd/yyyy"));
        acceptedDateFormats.add(DateTimeFormatter.ofPattern("MM/dd/yy"));
        acceptedDateFormats.add(DateTimeFormatter.ofPattern("M/dd/yy"));
    }

    /**
     * Constructor loads a data container to set the initial state of the UIModel
     * @param loadedData UIData to load into the UIModel
     */
    public UIModel(UIData loadedData){
        savedInput = loadedData;
    }

    public UIData getAcceptedInput(){
        return savedInput;
    }


    /**
     * Returns the number of passengers as a string
     * @return a string with the number of passengers
     */
    public String getNumberOfPassengers() {
        return Integer.toString(savedInput.numberOfPassengers());
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
        if (passengers == savedInput.numberOfPassengers())
            return;

        // If the number of passengers is within the acceptable range, overwrite the stored value
        if (passengers >= minimumPassengers && passengers <= maximumPassengers) {
            savedInput.numberOfPassengers(passengers);
            System.out.println("User input updated the number of passengers to " + numberOfPassengers);
        }else
            System.out.println("Warning: User input of " + numberOfPassengers + " is invalid for the number of passengers");
    }

    /**
     * Returns the number of layovers as a string
     * @return a string with the number of layovers
     */
    public String getNumberOfLayovers() {
        return Integer.toString(savedInput.numberOfLayovers());
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
        if (layovers == savedInput.numberOfLayovers())
            return;

        // If the number of layovers is within the acceptable range, overwrite the stored value
        if (layovers >= minimumLayovers && layovers <= maximumLayovers) {
            savedInput.numberOfLayovers(layovers);
            System.out.println("User input updated the number of layovers to " + numberOfLayovers);
        }else
            System.out.println("Warning: User input of " + numberOfLayovers + " is invalid for the number of layovers");
    }

    /**
     * Returns the departure date, formatted as a string
     * @return a string with the departure date
     */
    public String getSeatingType() {
        return savedInput.seatingType();
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
     * Attempts to parse and validate the input string as an airport name/code
     * @param arrivalAirport a String with the arrival airport name or code
     */
    public void setArrivalAirport(String arrivalAirport) {
        // If the string is empty, then set the airport to null
        if (arrivalAirport.equals("")){
            // If the airport was already null, make no changes
            if (savedInput.arrivalAirport() != null) {
                savedInput.arrivalAirport(null);
                System.out.println("User removed arrival airport");
            }
            return;
        }

        // If the new arrival airport is the same as the departure airport, make no changes
        if(savedInput.departureAirport() != null && (arrivalAirport.equals(savedInput.departureAirport().name()) || arrivalAirport.equals(savedInput.departureAirport().name()))) {
            System.out.println("Warning: User input of " + arrivalAirport + " cannot be the same as the departure airport");
            return;
        }

        // If the new airport is different from the previous one, validate the airport string and overwrite the stored value
        if(savedInput.arrivalAirport() == null || (!arrivalAirport.equals(savedInput.arrivalAirport().name()) && !arrivalAirport.equals(savedInput.arrivalAirport().code()))) {
            Airport airport = LocalFlightDatabase.getInstance().getAirportByString(arrivalAirport);
            if(airport != null) {
                savedInput.arrivalAirport(airport);
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
        if (savedInput.departureAirport() == null)
            return "";
        else
            return savedInput.departureAirport().name();
    }

    /**
     * Attempts to parse and validate the input string as an airport name/code
     * @param departureAirport a String with the departure airport name or code
     */
    public void setDepartureAirport(String departureAirport) {
        // If the string is empty, then set the airport to null
        if (departureAirport.equals("")){
            // If the airport was already null, make no changes
            if (savedInput.departureAirport() != null) {
                savedInput.departureAirport(null);
                System.out.println("User removed departure airport");
            }
            return;
        }

        // If the new departure airport is the same as the arrival airport, make no changes
        if(savedInput.arrivalAirport() != null && (!departureAirport.equals(savedInput.arrivalAirport().name()) || !departureAirport.equals(savedInput.arrivalAirport().name()))) {
            System.out.println("Warning: User input of " + departureAirport + " cannot be the same as the arrival airport");
            return;
        }

        // If the new airport is different from the previous one, validate the airport string and overwrite the stored value
        if(savedInput.departureAirport() == null || (!departureAirport.equals(savedInput.departureAirport().name()) && !departureAirport.equals(savedInput.departureAirport().code()))) {
            Airport airport = LocalFlightDatabase.getInstance().getAirportByString(departureAirport);
            if(airport != null) {
                savedInput.departureAirport(airport);
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
        if(savedInput.arrivalDate() != null)
            return acceptedDateFormats.get(0).format(savedInput.arrivalDate());
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
            if (savedInput.arrivalDate() != null) {
                savedInput.arrivalDate(null);
                System.out.println("User removed arrival date");
            }
            return;
        }

        // Attempt to parse the input string, make no changes on failure
        ZonedDateTime date = null;
        for(DateTimeFormatter formatter : acceptedDateFormats){
            try {
                date = ZonedDateTime.of(LocalDate.parse(arrivalDate, formatter), LocalTime.MIN, gmt);
                break;
            } catch (DateTimeParseException ex1) {}
        }
        if (date == null) {
            System.out.println("Warning: User input of " + arrivalDate + " is invalid syntax for the arrival date");
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (savedInput.arrivalDate()== null || date.compareTo(savedInput.arrivalDate()) != 0) {
            if(date.compareTo(earliestDate) >= 0 && date.compareTo(latestDate) < 0 && (savedInput.departureDate() == null || date.compareTo(savedInput.departureDate()) > 0)) {
                savedInput.arrivalDate(date);
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
        if (savedInput.departureDate() != null)
            return acceptedDateFormats.get(0).format(savedInput.departureDate());
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
            if (savedInput.departureDate() != null) {
                savedInput.departureDate(null);
                System.out.println("User removed departure date");
            }
            return;
        }

        // Attempt to parse the input string, make no changes on failure
        ZonedDateTime date = null;
        for(DateTimeFormatter formatter : acceptedDateFormats){
            try {
                date = ZonedDateTime.of(LocalDate.parse(departureDate, formatter), LocalTime.MIN, gmt);
                break;
            } catch (DateTimeParseException ex1) {}
        }
        if (date == null) {
            System.out.println("Warning: User input of " + departureDate + " is invalid syntax for the departure date");
            return;
        }

        // If the old date was null or different from the new one, validate the new date and overwrite the stored value
        if (savedInput.departureDate() == null || date.compareTo(savedInput.departureDate()) != 0) {
            if(date.compareTo(earliestDate) >= 0 && date.compareTo(latestDate) < 0 && (savedInput.arrivalDate() == null || date.compareTo(savedInput.arrivalDate()) < 0)) {
                savedInput.departureDate(date);
                System.out.println("User input updated the departure date to " + departureDate);
            }else
                System.out.println("Warning: User input of " + departureDate + " is invalid for the departure date");
        }
    }

    public void setDisplayList(Legs displayList){
        this.displayList = displayList;
    }

    public Legs getDisplayList(){
        return displayList;
    }
}
