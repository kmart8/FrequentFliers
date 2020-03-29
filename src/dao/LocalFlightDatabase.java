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
    private Planes planeList = new Planes();
    private Airports airportList = new Airports();
    private Legs legList = new Legs();

    // Create a request container and list for storing which requests for departing legs have already been made
    private class BoardingLegsRequest {
        private String boardingAirportCode;
        private String boardingDateString;
        public BoardingLegsRequest(Airport boardingAirport, LocalDate boardingDate){
            boardingAirportCode = boardingAirport.code();
            boardingDateString = boardingDate.toString();
        }
    }
    private List<BoardingLegsRequest> previousLegRequests = new ArrayList<>();

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
     * @return the list of airports (possibly empty)
     */
    public Airports getAirportList(boolean override){
        // If airports have not been built from the server or an override of the current airport list is requested,
        // get a new list of airports from the server
        if (airportList.size() == 0 || override)
                airportList = ServerInterface.INSTANCE.getAirports(Saps.TEAM_NAME);
        return airportList;
    }

    /** get the full list of planes provided by the server
     *
     * @param override if true, obtains a new list of planes from the server even if one already exists
     * @return the list of planes (possibly empty)
     */
    public Planes getPlaneList(boolean override){
        // If planes have not been built from the server or an override of the current plane list is requested,
        // get a new list of planes from the server
        if (planeList.size() == 0 || override)
            planeList = ServerInterface.INSTANCE.getPlanes(Saps.TEAM_NAME);
        return planeList;
    }

    /** get the full list of legs currently in storage
     *
     * @return the list of legs (possibly empty)
     */
    public Legs getLegList(){
        return legList;
    }

    /** get the list of legs currently in storage with the specified departure airport and departure date
     *
     * @param boardingAirport returned legs must have this boarding airport
     * @param boardingDate returned legs must have this boarding date
     * @param override if true, obtains a new list of legs from the server even if the same request has been previously made
     *
     * @return only legs that match the specified boarding airport and boarding date (possibly empty)
     */
    public Legs getLegList(Airport boardingAirport, LocalDate boardingDate, boolean override){
        Legs requestedLegs = new Legs();

        // Initialize a request to check against previous requests
        BoardingLegsRequest newRequest = new BoardingLegsRequest(boardingAirport, boardingDate);

        // If no matching request has been made previously, store the request and add the requested legs to the legList
        if(!previousLegRequests.contains(newRequest)) {
            previousLegRequests.add(newRequest);
            requestedLegs = ServerInterface.INSTANCE.getBoardingLegs(boardingAirport, boardingDate);
        }
        // Even if a matching request has been made previously, if an ovverride is requested, add the requested legs to the legList
        else if(override)
            requestedLegs = ServerInterface.INSTANCE.getBoardingLegs(boardingAirport, boardingDate);
            // Otherwise, find and return matching legs from the leg list
        else{
            for(Leg leg:legList) {
                // Verify that the boarding airport and boarding time match the requested ones
                if (leg.boardingAirport.equals(boardingAirport) && leg.boardingTime.toLocalDate().equals(boardingDate))
                    requestedLegs.add(leg);
            }
            return requestedLegs;
        }

        // If new legs have been provided by the server, add them to the leg list and return them
        addLegs(requestedLegs);
        return requestedLegs;
    }

    /** get the airport object who's name or code matches the specified String
     *
     * @param airportString airport name or airport code (case insensitive)
     *
     * @return the airport object which matches the specified string, or null if no matches
     */
    public Airport getAirportFromString(String airportString){
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
    public Plane getPlaneFromModel(String planeString){
        for(Plane plane : getPlaneList(false)){
            if(planeString.compareToIgnoreCase(plane.model()) == 0)
                return plane;
        }
        return null;
    }

    /** add new legs to the legList, while removing any duplicate old legs (probably should override .add in the Legs class instead)
     *
     * @param potentialLegs list of new legs to add to the leg list (possibly empty)
     */
    /// Can unique be implemented at the list side?
    private void addLegs(Legs potentialLegs){
        for(Leg leg: potentialLegs){
            // For each leg in the new list, make sure there is no old version on the current list
            //      Note: Equality is currently determined only by flight number (overriden in the Leg class, as number of reserved seats may change)
            if(legList.contains(leg))
                legList.remove(leg);
            legList.add(leg);
        }
    }
}
