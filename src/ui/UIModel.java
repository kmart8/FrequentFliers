package ui;

import airport.Airport;
import utils.Saps;

import java.time.*;

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

    // Initialize empty times for departure or arrival
    private LocalTime startFlightLocalTime;
    private LocalTime endFlightLocalTime;

    // Initialize empty date for the flight
    private LocalDate flightLocalDate;

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
        flightLocalDate = Saps.EARLIEST_DATE.toLocalDate();
        startFlightLocalTime = LocalTime.MIN;
        endFlightLocalTime = LocalTime.MAX;
        numberOfPassengers = 1;
        numberOfLayovers = 2;
    }

    public ZonedDateTime startFlightDateTime() {
        if (departureAirport != null)
            return departureAirport.convertLocalDateTimetoGMT(LocalDateTime.of(flightLocalDate, startFlightLocalTime));
        else
            return null;
    }

    public ZonedDateTime endFlightDateTime() {
        if (arrivalAirport != null)
            return arrivalAirport.convertLocalDateTimetoGMT(LocalDateTime.of(flightLocalDate, endFlightLocalTime));
        else
            return null;
    }

    public LocalTime startFlightLocalTime() { return startFlightLocalTime; }

    public LocalTime endFlightLocalTime() { return endFlightLocalTime; }

    public LocalDate flightLocalDate() { return flightLocalDate; }

    public void startFlightLocalTime(LocalTime time) { startFlightLocalTime = time; }

    public void endFlightLocalTime(LocalTime time) {
        endFlightLocalTime = time;
    }

    public void flightLocalDate(LocalDate date) {
        flightLocalDate = date;
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
