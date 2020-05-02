package driver;

import flight.Flight;
import flight.FlightManager;
import flight.Flights;
import leg.Legs;
import ui.UIModel;
import ui.UIController;
import dao.LocalFlightDatabase;
/**
 * This class handles initialization of the UIModel, UIController, and ReservationApp viewer. It also serves as
 * an additional controller to handle search functionality.
 *
 * @author Chris Collins
 * @version 1.0 2020-03-26
 * @since 2020-03-06
 *
 */
public class FlightBuilder {
    private UIController app;
    private FlightManager flightController;

    // Singleton variable
    private static FlightBuilder single_instance = null;

    /** static method to provide single point of access to the Singleton
     *
     * @return the active FlightBuilder, or a new one if one is not created
     */
    public static FlightBuilder getInstance() {
        if (single_instance == null) {
            single_instance = new FlightBuilder();
        }
        return single_instance;
    }

    /** displays the GUI to the user by activating the UIController
     */
    public void generateGUI(){
        app = new UIController();
    }

    /** searches for legs that match the departure date and airport specified by the user
     *  (does not currently store any legs, just assignes them to display in the viewer)
     *  (will eventually be moved, and this function will actually search for flights)
     */
    public Flights searchForFlights(){
        // Get the contents of the model
        UIModel userInput = app.getAcceptedInput();

        // Confirm that the user has supplied a departure airport and departure date
        if (userInput.departureAirport() != null && userInput.arrivalAirport() != null) {
            flightController = new FlightManager(userInput);
            Flight firstFlight = new Flight(new Legs(), userInput.seatingType());
            flightController.enqueueFlight(firstFlight);
            flightController.completeQueue();
            return flightController.validFlights();
            // Get legs which match the user input criteria, this will most likely be implemented for flights in later versions (not legs)
            //app.setDisplayList(LocalFlightDatabase.getInstance().getLegList(userInput.departureAirport(), userInput.startFlightDateTime().toLocalDate(), false));
        } else {
            System.out.println("Departure airport or arrival airport is empty, cannot search for flights");
            return new Flights();
        }
    }

}
