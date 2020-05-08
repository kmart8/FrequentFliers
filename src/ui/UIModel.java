package ui;

import airport.Airport;
import flight.Flight;
import utils.Saps;

import java.time.*;

/**
 * This class serves as a model for the ReservationApp viewer. It saves user input for future use.
 * Note that input must be validated before submission to this model.
 * TODO: Move the validation logic to this model
 *
 * @author Chris Collins
 * @version 2.0 2020-05-01
 * @since 2020-03-06
 *
 */
public class UIModel implements Cloneable{
    /** Type for specifying departure or arrival time window */
    private String timeType;
    /** Beginning of the time window for departure or arrival */
    private LocalTime startFlightLocalTime;
    /** End of the time window for departure or arrival */
    private LocalTime endFlightLocalTime;
    /** Date for the flight */
    private LocalDate flightLocalDate;

    /** Requested departure airport */
    private Airport departureAirport;
    /** Requested arrival airport */
    private Airport arrivalAirport;

    /** Type for specifying seating */
    private String seatingType;
    /** Requested number of passengers */
    private int numberOfPassengers;

    /** Maximum number of layovers */
    private int numberOfLayovers;

    /**
     * Initializes the UIModel with default values for all fields except airports
     */
    public UIModel(){
        timeType = "Departure";
        flightLocalDate = Saps.EARLIEST_DATE.toLocalDate();
        startFlightLocalTime = LocalTime.MIN;
        endFlightLocalTime = LocalTime.MAX;
        numberOfPassengers = 1;
        numberOfLayovers = 2;
        seatingType = Saps.SEATING_TYPES.get(0);
    }

    /**
     * Get the beginning of the time window for the arrival or departure of the flight as a DateTime
     *
     * @return  The beginning of the time window as a ZonedDateTime in the GMT time zone, or null if arrival airport is empty
     */
    public ZonedDateTime startFlightDateTime() {
        if (departureAirport != null)
            return departureAirport.convertLocalDateTimetoGMT(LocalDateTime.of(flightLocalDate, startFlightLocalTime));
        else
            return null;
    }

    /**
     * Get the end of the time window for the arrival or departure of the flight as a DateTime
     *
     * @return  The end of the time window as a ZonedDateTime in the GMT time zone, or null if arrival airport is empty
     */
    public ZonedDateTime endFlightDateTime() {
        if (arrivalAirport != null)
            return arrivalAirport.convertLocalDateTimetoGMT(LocalDateTime.of(flightLocalDate, endFlightLocalTime));
        else
            return null;
    }

    /**
     * Get the beginning of the time window for the arrival or departure of the flight
     *
     * @return  The beginning of the time window as a LocalTime
     */
    public LocalTime startFlightLocalTime() { return startFlightLocalTime; }

    /**
     * Set the beginning of the time window for the arrival or departure of the flight
     *
     * @param time The beginning of the time window as a LocalTime
     */
    public void startFlightLocalTime(LocalTime time) { startFlightLocalTime = time; }

    /**
     * Get the end of the time window for the arrival or departure of the flight
     *
     * @return  The end of the time window as a LocalTime
     */
    public LocalTime endFlightLocalTime() { return endFlightLocalTime; }

    /**
     * Set the end of the time window for the arrival or departure of the flight
     *
     * @param time The end of the time window as a LocalTime
     */
    public void endFlightLocalTime(LocalTime time) {
        endFlightLocalTime = time;
    }

    /**
     * Get the date for the flight
     *
     * @return  The date as a LocalDate
     */
    public LocalDate flightLocalDate() { return flightLocalDate; }

    /**
     * Set the date for the flight
     *
     * @param date The date as a LocalDate
     */
    public void flightLocalDate(LocalDate date) {
        flightLocalDate = date;
    }

    /**
     * Get the type of time window the user is requesting
     *
     * @return The time type as a String
     */
    public String timeType() {
        return timeType;
    }

    /**
     * Set the type of time window requested by the user
     *
     * @param dateType  The time type as a String
     */
    public void timeType(String dateType) {
        this.timeType = dateType;
    }

    /**
     * Get the departure airport
     *
     * @return The departure Airport
     */
    public Airport departureAirport() {
        return departureAirport;
    }

    /**
     * Set the departure airport
     *
     * @param departureAirport The departure Airport
     */
    public void departureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * Get the arrival airport
     *
     * @return The arrival Airport
     */
    public Airport arrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Set the arrival airport
     *
     * @param arrivalAirport The arrival Airport
     */
    public void arrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * Get the seating type
     *
     * @return The type seating as a String
     */
    public String seatingType() {
        return seatingType;
    }

    /**
     * Set the seating type
     *
     * @param seatingType The type seating as a String
     */
    public void seatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    /**
     * Get the number of passengers
     *
     * @return The number of passengers as an Integer
     */
    public int numberOfPassengers() {
        return numberOfPassengers;
    }

    /**
     * Set the number of passengers
     *
     * @param numberOfPassengers The number of passengers as an Integer
     */
    public void numberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    /**
     * Get the maximum number of layovers
     *
     * @return The maximum number of layovers as an Integer
     */
    public int numberOfLayovers() {
        return numberOfLayovers;
    }

    /**
     * Set the maximum number of layovers
     *
     * @param numberOfLayovers The maximum number of layovers as an Integer
     */
    public void numberOfLayovers(int numberOfLayovers) {
        this.numberOfLayovers = numberOfLayovers;
    }

    /**
     * Required Clone Method
     *
     * @return Copy of UIModel
     * @throws CloneNotSupportedException If clone is not supported
     */
    public UIModel clone() throws CloneNotSupportedException{
        return  (UIModel) super.clone();
    }
}
