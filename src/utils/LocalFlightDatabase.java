package utils;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import driver.FlightBuilder;
import leg.Legs;
import plane.Planes;

public class LocalFlightDatabase {
    private static Planes planeList;
    private static Airports airportList;
    private static Legs legList;

    // Singleton variable
    private static LocalFlightDatabase single_instance = null;

    // static method to create instance of Singleton
    public static LocalFlightDatabase getInstance() {
        if (single_instance == null) {
            single_instance = new LocalFlightDatabase();
        }
        return single_instance;
    }

    public static Airports getAirportList(boolean override){
        if (airportList == null || override)
                airportList = ServerInterface.INSTANCE.getAirports(FlightBuilder.teamName());
        return airportList;
    }

    public static Planes getPlaneList(boolean override){
        if (planeList == null || override)
            planeList = ServerInterface.INSTANCE.getPlanes(FlightBuilder.teamName());
        return planeList;
    }

    public static Legs getLegList(){
        return legList;
    }

    public static Airport getAirportByString(String airportString){
        for(Airport apt : getAirportList(false)){
            if(airportString.equals(apt.name()) || airportString.equals(apt.code()))
                return apt;
        }
        return null;
    }

}
