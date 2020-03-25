package UI;

import airport.Airport;
import java.time.ZonedDateTime;

public class UIData {
    // Initialize empty dates for departure and arrival
    private ZonedDateTime departureDate;
    private ZonedDateTime arrivalDate;

    // Initialize empty airports for departure and arrival
    private Airport departureAirport;
    private Airport arrivalAirport;

    // Initialize empty seating type
    private String seatingType;

    // Initialize the default number of passengers to 1
    private int numberOfPassengers;

    // Initialize the default number of layovers to 2
    private int numberOfLayovers;

    public ZonedDateTime departureDate() {
        return departureDate;
    }

    public void departureDate(ZonedDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public ZonedDateTime arrivalDate() {
        return arrivalDate;
    }

    public void arrivalDate(ZonedDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
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
