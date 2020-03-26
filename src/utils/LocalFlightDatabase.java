package utils;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import driver.FlightBuilder;
import leg.Leg;
import leg.Legs;
import plane.Planes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocalFlightDatabase {
    private Planes planeList;
    private Airports airportList;
    private Legs legList;

    private class DepartingLegsRequest{
        private String departureCode;
        private String departureDateString;
        public DepartingLegsRequest(Airport departureAirport, LocalDate departureDate){
            departureCode = departureAirport.code();
            departureDateString = departureDate.toString();
        }
    }

    private List<DepartingLegsRequest> previousLegRequests = new ArrayList<>();

    // Singleton variable
    private static LocalFlightDatabase single_instance = null;

    // static method to create instance of Singleton
    public static LocalFlightDatabase getInstance() {
        if (single_instance == null) {
            single_instance = new LocalFlightDatabase();
        }
        return single_instance;
    }

    public Airports getAirportList(boolean override){
        if (airportList == null || override)
                airportList = ServerInterface.INSTANCE.getAirports(FlightBuilder.teamName());
        return airportList;
    }

    public Planes getPlaneList(boolean override){
        if (planeList == null || override)
            planeList = ServerInterface.INSTANCE.getPlanes(FlightBuilder.teamName());
        return planeList;
    }

    public Legs getLegList(){
        return legList;
    }

    public Legs getLegList(Airport departureAirport, LocalDate departureDate, boolean override){
        DepartingLegsRequest newRequest = new DepartingLegsRequest(departureAirport, departureDate);
        if(!previousLegRequests.contains(newRequest)) {
            previousLegRequests.add(newRequest);
            addLegs(departureAirport,departureDate);
        }
        else if(override)
            addLegs(departureAirport,departureDate);

        if(legList == null)
            return null;

        Legs requestedLegs = new Legs();
        for(Leg leg:legList){
            if (leg.boardingAirport.equals(departureAirport) && leg.boardingTime.toLocalDate().equals(departureDate))
                requestedLegs.add(leg);
        }
        return requestedLegs;
    }

    public Airport getAirportByString(String airportString){
        for(Airport apt : getAirportList(false)){
            if(airportString.compareToIgnoreCase(apt.name()) == 0 || airportString.compareToIgnoreCase(apt.code()) == 0)
                return apt;
        }
        return null;
    }

    private void addLegs(Airport departureAirport, LocalDate departureDate){
        Legs newLegs = ServerInterface.INSTANCE.getDepartingLegs(departureAirport, departureDate);
        if(newLegs != null)
            if(legList == null)
                legList = new Legs();
            for(Leg leg: newLegs){
                if(!legList.contains(leg))
                    legList.add(leg);
        }
    }
}
