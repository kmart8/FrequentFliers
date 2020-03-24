package driver;

import UI.ReservationApp;
import UI.UIData;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import plane.Planes;

public class FlightBuilder {
    private static ReservationApp ui;
    private static UIData model;
    private static Airports airportList;
    private static Planes planeList;

    // Singleton variable
    private static FlightBuilder INSTANCE = null;

    // static method to create instance of Singleton
    public static FlightBuilder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FlightBuilder();
        }
        return INSTANCE;
    }


    public static void generateGUI() {
        ui = new ReservationApp();
        model = new UIData();
        buildAirportsAndPlanes();
        ui.initializeUIElements(model);
        System.out.println("New Application Created");
    }

    public static Airport getAirportByString(String airportString){
        for(Airport apt : airportList){
            if(airportString.equals(apt.name()) || airportString.equals(apt.code()))
                return apt;
        }
        return null;
    }

    private static void buildAirportsAndPlanes(){
        airportList = ServerInterface.INSTANCE.getAirports(model.getTeamName());
        planeList = ServerInterface.INSTANCE.getPlanes(model.getTeamName());
    }
}
