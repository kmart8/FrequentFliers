package driver;

import UI.ReservationApp;
import UI.UIData;
import UI.UIModel;

public class FlightBuilder {
    private static String teamName = "FrequentFliers";
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
        ui = new ReservationApp();
        model = new UIModel();
        ui.initializeUIElements(model);
        System.out.println("New Application Created");
    }

    public static String teamName(){
        return teamName;
    }

}
