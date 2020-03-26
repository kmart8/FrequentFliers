package driver;

import UI.ReservationApp;
import UI.UIData;
import UI.UIModel;
import dao.LocalFlightDatabase;
/**
 * This class serves as a controller for the UIModel and the ReservationApp viewer. It handles the initialization of
 * these other classes and any user requested functionality.
 *
 * @author Chris Collins
 * @version 1.0 2020-03-26
 * @since 2020-03-06
 *
 */
public class FlightBuilder {
    private static ReservationApp ui;
    private static UIModel model;

    // Singleton variable
    private static FlightBuilder single_instance = null;

    // static method to create instance of Singleton
    public static FlightBuilder getInstance() {
        if (single_instance == null) {
            single_instance = new FlightBuilder();
        }
        return single_instance;
    }

    public static void generateGUI() {
        System.out.println("New Application Requested");
        ui = new ReservationApp();
        model = new UIModel();
        ui.initializeUIElements(model);
    }

    public static void searchForFlights(){
        // Get the contents of the model
        UIData userInput = model.getAcceptedInput();

        // Confirm that the user has supplied a departure airport and departure date
        if (userInput.departureAirport() != null && userInput.departureDate() != null) {
            // Get legs which match the user input criteria, this will most likely be implemented for flights in later versions (not legs)
            model.setDisplayList(LocalFlightDatabase.getInstance().getLegList(userInput.departureAirport(), userInput.departureDate().toLocalDate(), false));
        } else
            System.out.println("Departure airport or departure date is empty, cannot search for flights");
    }

}
