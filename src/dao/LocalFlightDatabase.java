package dao;

import airport.Airport;
import airport.Airports;
import leg.Leg;
import leg.Legs;
import plane.Plane;
import plane.Planes;
import utils.Saps;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores and retrieves objects built by the reservation app from server returned XML.
 * In order to reduce server load, this class will track previous requests to get objects
 * to prevent unnecessary calls. In the event that the request has not been made previously
 * (or is overriden) this class will get new and updated versions of the objects from the ServerInterface.
 *
 * @author Chris Collins
 * @version 1.0 2020-03-26
 * @since 2020-03-26
 *
 */

public class LocalFlightDatabase {
    // Storage for objects constructed by the ServerInterface
    private Planes planeList;
    private Airports airportList;
    private Legs legList;

    // Create a request container and list for storing which requests for departing legs have already been made
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

    /** static method to provide single point of access to the Singleton
     *
     * @return the active LocalFlightDatabase, or a new one if one is not created
     */
    public static LocalFlightDatabase getInstance() {
        if (single_instance == null) {
            single_instance = new LocalFlightDatabase();
        }
        return single_instance;
    }

    /** get the full list of airports provided by the server
     *
     * @param override if true, obtains a new list of airports from the server even if one already exists
     * @return the list of airports
     */
    public Airports getAirportList(boolean override){
        // If airports have not been built from the server or an override of the current airport list is requested,
        // get a new list of airports from the server
        if (airportList == null || override)
                airportList = ServerInterface.INSTANCE.getAirports(Saps.TEAM_NAME);
        return airportList;
    }

    /** get the full list of planes provided by the server
     *
     * @param override if true, obtains a new list of planes from the server even if one already exists
     * @return the list of planes
     */
    public Planes getPlaneList(boolean override){
        // If planes have not been built from the server or an override of the current plane list is requested,
        // get a new list of planes from the server
        if (planeList == null || override)
            planeList = ServerInterface.INSTANCE.getPlanes(Saps.TEAM_NAME);
        return planeList;
    }

    /** get the full list of legs currently in storage
     *
     * @return the list of legs (possibly null)
     */
    public Legs getLegList(){
        return legList;
    }

    /** get the list of legs currently in storage with the specified departure airport and departure date
     *
     * @param disembarkingAirport returned legs must have this disembarking airport
     * @param disembarkingDate returned legs must have this disembarking date
     * @param override if true, obtains a new list of legs from the server even if the same request has been previously made
     *
     * @return only legs that match the specified departure airport and departure date (possibly null)
     */
    public Legs getLegList(Airport disembarkingAirport, LocalDate disembarkingDate, boolean override){
        Legs requestedLegs = new Legs();

        // Initialize a request to check against previous requests
        DepartingLegsRequest newRequest = new DepartingLegsRequest(disembarkingAirport, disembarkingDate);

        // If no matching request has been made previously, store the request and add the requested legs to the legList
        if(!previousLegRequests.contains(newRequest)) {
            previousLegRequests.add(newRequest);
            requestedLegs = ServerInterface.INSTANCE.getDepartingLegs(disembarkingAirport, disembarkingDate);
        }
        // Even if a matching request has been made previously, if an ovverride is requested, add the requested legs to the legList
        else if(override)
            requestedLegs = ServerInterface.INSTANCE.getDepartingLegs(disembarkingAirport, disembarkingDate);

        // If nothing has been added to the requested list, the request has already been made previously
        if(requestedLegs.size() == 0){
            // Iterate through the legList and find legs matching the request
            for(Leg leg:legList){
                if (leg.boardingAirport.equals(disembarkingAirport) && leg.boardingTime.toLocalDate().equals(disembarkingDate))
                    requestedLegs.add(leg);
            }
        }

        // If no legs match the input, return null
        // Otherwise, add the legs to the leg list
        if (requestedLegs.size()==0)
            return null;
        else
            addLegs(requestedLegs);

        return requestedLegs;
    }

    /** get the airport object who's name or code matches the specified String
     *
     * @param airportString airport name or airport code (case insensitive)
     *
     * @return the airport object which matches the specified string, or null if no matches
     */
    public Airport getAirportByString(String airportString){
        for(Airport apt : getAirportList(false)){
            if(airportString.compareToIgnoreCase(apt.name()) == 0 || airportString.compareToIgnoreCase(apt.code()) == 0)
                return apt;
        }
        return null;
    }

    /** get the plane object who's model matches the specified String
     *
     * @param planeString plane model (case insensitive)
     *
     * @return the plane object which matches the specified string, or null if no matches
     */
    public Plane getPlaneByString(String planeString){
        for(Plane plane : getPlaneList(false)){
            if(planeString.compareToIgnoreCase(plane.model()) == 0)
                return plane;
        }
        return null;
    }

    /** add new legs to the legList, while removing any duplicate old legs (probably should override .add in the Legs class instead)
     *
     * @param potentialLegs list of new legs to add to the leg list
     */
    private void addLegs(Legs potentialLegs){
        if(potentialLegs != null)
            if(legList == null)
                legList = new Legs();
            for(Leg leg: potentialLegs){
                // For each leg in the new list, make sure there is no old version on the current list
                //      Note: Equality is currently determined only by flight number (ovverriden in the Leg class, as number of reserved seats may change)
                if(legList.contains(leg))
                    legList.remove(leg);
                legList.add(leg);
        }
    }
}
