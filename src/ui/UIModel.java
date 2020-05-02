package ui;

import airport.Airport;
import utils.Saps;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * This class serves as a model for the ReservationApp viewer. It is controlled by the UIController.
 *
 * @author Chris Collins
 * @version 2.0 2020-05-01
 * @since 2020-03-06
 *
 */
public class UIModel {
    // Initialize date type for specifying departure or arrival
    private String timeType;

    // Initialize empty date for departure or arrival
    private ZonedDateTime startFlightDateTime;
    private ZonedDateTime endFlightDateTime;

    // Initialize empty airports for departure and arrival
    private Airport departureAirport;
    private Airport arrivalAirport;

    // Initialize empty seating type
    private String seatingType;

    // Initialize the default number of passengers to 1
    private int numberOfPassengers;

    // Initialize the default number of layovers to 2
    private int numberOfLayovers;

    public UIModel(){
        timeType = "Departure";
        startFlightDateTime = Saps.EARLIEST_DATE;
        endFlightDateTime = Saps.EARLIEST_DATE.plus(Duration.ofHours(23).plus(Duration.ofMinutes(59).plus(Duration.ofSeconds(59))));
        numberOfPassengers = 1;
        numberOfLayovers = 2;
    }

    public ZonedDateTime startFlightDateTime() {
        return startFlightDateTime;
    }

    public void startFlightDateTime(ZonedDateTime date) {
        startFlightDateTime = date;
    }

    public ZonedDateTime endFlightDateTime() {
        return endFlightDateTime;
    }

    public void endFlightDateTime(ZonedDateTime date) {
        endFlightDateTime = date;
    }

    public String timeType() {
        return timeType;
    }

    public void timeType(String dateType) {
        this.timeType = dateType;
    }

    public Airport departureAirport() {
        return departureAirport;
    }

    public void departureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport arrivalAirport() {
        return arrivalAirport;
    }

    public void arrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String seatingType() {
        return seatingType;
    }

    public void seatingType(String seatingType) {
        this.seatingType = seatingType;
    }

    public int numberOfPassengers() {
        return numberOfPassengers;
    }

    public void numberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int numberOfLayovers() {
        return numberOfLayovers;
    }

    public void numberOfLayovers(int numberOfLayovers) {
        this.numberOfLayovers = numberOfLayovers;
    }
}
