package dao;

import airport.Airport;
import airport.Airports;
import leg.Leg;
import leg.Legs;
import plane.Plane;
import plane.Planes;

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
    /** List of Plane objects obtained from the server */
    private Planes planeList = new Planes();
    /** List of Airport objects obtained from the server */
    private Airports airportList = new Airports();
    /** List of leg objects obtained from requests the server using boarding info */
    private final Legs boardingLegList = new Legs();
    /** List of leg objects obtained from requests the server using disembarking info */
    private final Legs disembarkingLegList = new Legs();

    /** Container for saving previous requests for legs using boarding info */
    private class BoardingLegsRequest {
        private String boardingAirportCode;
        private String boardingDateString;
        public BoardingLegsRequest(Airport boardingAirport, LocalDate boardingDate){
            boardingAirportCode = boardingAirport.code();
            boardingDateString = boardingDate.toString();
        }

        @Override
        public boolean equals (Object obj) {
            // every object is equal to itself
            if (obj == this) return true;
            // null not equal to anything
            if (obj == null) return false;
            // can't be equal if obj is not an instance of BoardingLegsRequest
            if (!(obj instanceof BoardingLegsRequest)) return false;
            // if all fields are equal, the requests are the same
            BoardingLegsRequest rhs = (BoardingLegsRequest) obj;
            if ((rhs.boardingAirportCode.equalsIgnoreCase(boardingAirportCode)) &&
                    (rhs.boardingDateString.equalsIgnoreCase(boardingDateString))){
                return true;
            }
            return false;
        }
    }

    /** Container for saving previous requests for legs using disembarking info */
    private class DisembarkingLegsRequest {
        private String disembarkingAirportCode;
        private String disembarkingDateString;
        public DisembarkingLegsRequest(Airport disembarkingAirport, LocalDate disembarkingDate){
            disembarkingAirportCode = disembarkingAirport.code();
            disembarkingDateString = disembarkingDate.toString();
        }

        @Override
        public boolean equals (Object obj) {
            // every object is equal to itself
            if (obj == this) return true;
            // null not equal to anything
            if (obj == null) return false;
            // can't be equal if obj is not an instance of DisembarkingLegsRequest
            if (!(obj instanceof BoardingLegsRequest)) return false;
            // if all fields are equal, the requests are the same
            BoardingLegsRequest rhs = (BoardingLegsRequest) obj;
            if ((rhs.boardingAirportCode.equalsIgnoreCase(disembarkingAirportCode)) &&
                    (rhs.boardingDateString.equalsIgnoreCase(disembarkingDateString))){
                return true;
            }
            return false;
        }
    }

    /** List of previous leg requests from the server using boarding info */
    private final List<BoardingLegsRequest> previousLegRequests = new ArrayList<>();
    /** List of previous leg requests from the server using disembarking info */
    private final List<DisembarkingLegsRequest> previousDisembarkingLegRequests = new ArrayList<>();

    /** Singleton variable */
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
     * @return [possibly empty] the list of airports
     */
    public Airports getAirportList(boolean override){
        // If airports have not been built from the server or an override of the current airport list is requested,
        // get a new list of airports from the server
        if (airportList.size() == 0 || override)
                airportList = ServerInterface.INSTANCE.getAirports();
        return airportList;
    }

    /** get the full list of planes provided by the server
     *
     * @param override if true, obtains a new list of planes from the server even if one already exists
     * @return [possibly empty] the list of planes
     */
    public Planes getPlaneList(boolean override){
        // If planes have not been built from the server or an override of the current plane list is requested,
        // get a new list of planes from the server
        if (planeList.size() == 0 || override)
            planeList = ServerInterface.INSTANCE.getPlanes();
        return planeList;
    }

    /** get the full list of legs currently in storage from boarding requests
     *
     * @return [possibly empty]the list of legs
     */
    public Legs getBoardingLegList(){ return boardingLegList; }

    /** get the full list of legs currently in storage from disembarking requests
     *
     * @return [possibly empty]the list of legs
     */
    public Legs getDisembarkingLegList() { return disembarkingLegList;}

    /** get the list of legs with the specified boarding airport and boarding date.
     *
     * If the legs are not already in storage, obtain them from the server database.
     *
     * @param boardingAirport returned legs must have this boarding airport
     * @param boardingDate returned legs must have this boarding date
     * @param override if true, obtains a new list of legs from the server even if the same request has been previously made
     *
     * @return [possibly empty] only legs that match the specified boarding airport and boarding date
     */
    public Legs getBoardingLegList(Airport boardingAirport, LocalDate boardingDate, boolean override){
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
            for(Leg leg:boardingLegList) {
                // Verify that the boarding airport and boarding time match the requested ones
                if (leg.getBoardingAirport().equals(boardingAirport) && leg.getBoardingTime().toLocalDate().equals(boardingDate))
                    requestedLegs.add(leg);
            }
            return requestedLegs;
        }

        // If new legs have been provided by the server, add them to the leg list and return them
        addBoardingLegs(requestedLegs);
        return requestedLegs;
    }

    /** get the list of legs with the specified disembarking airport and disembarking date.
     *
     * If the legs are not already in storage, obtain them from the server database.
     *
     * @param disembarkingAirport returned legs must have this disembarking airport
     * @param disembarkingDate returned legs must have this disembarking date
     * @param override if true, obtains a new list of legs from the server even if the same request has been previously made
     *
     * @return [possibly empty] only legs that match the specified disembarking airport and disembarking date
     */
    public Legs getDisembarkingLegList(Airport disembarkingAirport, LocalDate disembarkingDate, boolean override){
        Legs requestedLegs = new Legs();

        // Initialize a request to check against previous requests
        DisembarkingLegsRequest newRequest = new DisembarkingLegsRequest(disembarkingAirport, disembarkingDate);

        // If no matching request has been made previously, store the request and add the requested legs to the legList
        if(!previousDisembarkingLegRequests.contains(newRequest)) {
            previousDisembarkingLegRequests.add(newRequest);
            requestedLegs = ServerInterface.INSTANCE.getDisembarkingLegs(disembarkingAirport, disembarkingDate);
        }
        // Even if a matching request has been made previously, if an ovverride is requested, add the requested legs to the legList
        else if(override)
            requestedLegs = ServerInterface.INSTANCE.getDisembarkingLegs(disembarkingAirport, disembarkingDate);
            // Otherwise, find and return matching legs from the leg list
        else{
            for(Leg leg:disembarkingLegList) {
                // Verify that the boarding airport and boarding time match the requested ones
                if (leg.getDisembarkingAirport().equals(disembarkingAirport) && leg.getDisembarkingTime().toLocalDate().equals(disembarkingDate))
                    requestedLegs.add(leg);
            }
            return requestedLegs;
        }

        // If new legs have been provided by the server, add them to the leg list and return them
        addDisembarkingLegs(requestedLegs);
        return requestedLegs;
    }

    /** get the airport object who's name or code matches the specified String
     *
     * @param airportString airport name or airport code (case insensitive)
     *
     * @return [possibly null] the airport object which matches the specified string, or null if no matches
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
     * @return [possibly null] the plane object which matches the specified string, or null if no matches
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
     * @param potentialLegs [possibly empty] list of new legs to add to the leg list
     */
    /// Can unique be implemented at the list side?
    private void addBoardingLegs(Legs potentialLegs){
        for(Leg leg: potentialLegs){
            // For each leg in the new list, make sure there is no old version on the current list
            //      Note: Equality is currently determined only by flight number (overriden in the Leg class, as number of reserved seats may change)
            boardingLegList.remove(leg);
            boardingLegList.add(leg);
        }

    }

    /** add new legs to the legList, while removing any duplicate old legs (probably should override .add in the Legs class instead)
     *
     * @param potentialLegs [possibly empty] list of new legs to add to the leg list
     */
    private void addDisembarkingLegs(Legs potentialLegs){
        for(Leg leg: potentialLegs){
            // For each leg in the new list, make sure there is no old version on the current list
            //      Note: Equality is currently determined only by flight number (overriden in the Leg class, as number of reserved seats may change)
            disembarkingLegList.remove(leg);
            disembarkingLegList.add(leg);
        }

    }
}
