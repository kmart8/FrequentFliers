package flight;

import airport.Airport;
import dao.LocalFlightDatabase;
import leg.Leg;
import leg.Legs;
import ui.UIModel;
import utils.Saps;

import java.time.ZonedDateTime;
import java.util.LinkedList;

/**
 * Constructs and filters flights, caching the ones that are valid according to user input.
 *
 * Also stores a list of potentially completable flights so that the user can be notified of alternatives.
 *
 * @author Chris Collins
 * @version 1.0 2020-04-30
 * @since 2020-04-30
 *
 */
public class FlightManager {
    /** Queue of incomplete fights */
    private LinkedList<Flight> constructionQueue = new LinkedList<Flight>();
    /** List of completed fights which match the filter parameters */
    private Flights validFlights;
    /** List of completable fights which do not match the filter parameters */
    private Flights filteredFlights;
    /** Filter for checking the validity of completed flights */
    private UIModel flightFilter;

    /** Constructor initializes the lists and stores the UIModel for filtering invalid flights
     *
     * @param filter The UIModel used to compare against potential flights
     */
    public FlightManager(UIModel filter){
        flightFilter = filter;
        validFlights = new Flights();
        filteredFlights = new Flights();
    }
    /**
     * Get valid flights
     *
     * @return The list of valid completed flights
     */
    public Flights validFlights(){
        return validFlights;
    }

    /**
     * Check to see if other seating options were filtered out, but might be completable flights
     *
     * @return True if completable flights were filtered out for seating, but have other seating types available
     */
    public boolean isOtherSeatingPossible(){
        for (Flight thisFlight: filteredFlights) {
            if (thisFlight.filterReason().equals("seating"))
                return true;
        }
        return false;
    }

    /**
     * Set the filter for constructing flights
     *
     * @param flightFilter The UIModel produced by user input
     */
    public void flightFilter(UIModel flightFilter) {
        this.flightFilter = flightFilter;
    }

    /**
     * Iterates through the construction queue until no more flights remain on the queue. Each time a flight is removed
     * from the top of the queue, it is checked for validity and completeness against the flight filter. If is is
     * incomplete copies are added back onto the queue.
     *
     * @pre The flight filter is not empty
     * @post The construction queue is empty
     */
    public void completeQueue(){
        while (!constructionQueue.isEmpty()){
            Flight nextFlight = constructionQueue.removeFirst();
            nextFlight.isMatch(flightFilter);
            if (nextFlight.filterReason().isEmpty()){
                enqueueFlight(nextFlight);
            } else if (nextFlight.filterReason().equals("complete")){
                validFlights.add(nextFlight);
            } else if (nextFlight.filterReason().equals("invalid")){
                // Do nothing, do not requeue this flight or derivatives
            } else filteredFlights.add(nextFlight);
        }
    }

    /**
     * Adds copies of a flight back to the construction queue with an additional leg.
     *
     * @pre The flight filter is not empty
     * @post Any newly generated copies are added to the construction queue
     */
     public void enqueueFlight(Flight newFlight){
         // Get the next possible set of legs for the flight
         Legs newLegs = getValidLegs(newFlight);

        for (Leg thisLeg : newLegs) {
            // Create a new copy of the flight
            Flight copyFlight = new Flight();
            try{ copyFlight = newFlight.clone();}
            catch (CloneNotSupportedException E){}

            addNewLegToFlight(thisLeg,copyFlight);

            // Place the copy back on the queue for filtering and further additions
            constructionQueue.add(copyFlight);
        }
     }

    /**
     *  Modifies a flight to include a new leg.
     *  If the UIModel specifies a departure date/time window, then leg is added to the end of the flight.
     *  If the UIModel specifies an arrival date/time window, then leg is added to the beginning of the flight.
     *
     * @param newLeg Leg to be added to the flight
     * @param flight Flight to be modified
     * @return The modified flight
     * @pre The leg is a valid next addition to the flight and the flight filter is not empty
     * @post The leg has been added to the correct end of the flight
     */
     private Flight addNewLegToFlight(Leg newLeg, Flight flight){
         // If the time window is for departure, add the leg to the end of the copy
         if (flightFilter.timeType().equals(Saps.TIME_WINDOW_TYPES.get(0)))
             flight.addLegToEnd(newLeg);
             // If the time window is for arrival, add the leg to the beggining of the copy
         else
             flight.addLegToBeginning(newLeg);
         return flight;
     }

    /**
     *  Searches for candidate legs that can be added to a flight.
     *  If the UIModel specifies a departure date/time window, then boarding legs are vetted.
     *  If the UIModel specifies an arrival date/time window, then disembarking legs are vetted.
     *
     * @param flight Flight to use as validation for candidates
     * @return Legs that have the correct boarding or disembarking airport and the correct boarding or disembarking time
     * @pre The flight filter is not empty
     */
     private Legs getValidLegs(Flight flight){
         // If the time window is for departure, get legs based on boarding airport and boarding data
         if (flightFilter.timeType().equals(Saps.TIME_WINDOW_TYPES.get(0)))
             return getValidBoardingLegs(flight);
             // If the time window is for arrival, get legs based on disembarking airport and disembarking date
         else
             return getValidArrivingLegs(flight);
     }

    /**
     * Determines the next boarding airport and boarding dates, then gets all legs that match these parameters from the
     * LocalFlightDatabase. Also determines the time window for boarding and returns only legs inside this window.
     *
     * @pre the flight filter is not empty
     */
     private Legs getValidBoardingLegs(Flight newFlight){
         // Use the flight filter airport and time window by default
        Airport boardingAirport = flightFilter.departureAirport();
        ZonedDateTime startBoardingWindow = flightFilter.startFlightDateTime();
        ZonedDateTime endBoardingWindow = flightFilter.endFlightDateTime();

        // If there are already legs on the flight, use airport and time window based on the last leg of the flight
        if (newFlight.getArrivalAirport() != null) {
            boardingAirport = newFlight.getArrivalAirport();
            startBoardingWindow = newFlight.getArrivalTime().plus(Saps.MIN_LAYOVER_TIME);
            endBoardingWindow = startBoardingWindow.plus(Saps.MAX_LAYOVER_TIME.minus(Saps.MIN_LAYOVER_TIME));
        }

         // Get legs from the boarding airport and the start time date
        Legs potentialNewLegs = LocalFlightDatabase.getInstance().getBoardingLegList(boardingAirport,startBoardingWindow.toLocalDate(),false);
         // If the end time is on the next day get legs from that date too
         if (startBoardingWindow.toLocalDate().compareTo(endBoardingWindow.toLocalDate()) < 0) {
             Legs extraLegs = LocalFlightDatabase.getInstance().getBoardingLegList(boardingAirport, endBoardingWindow.toLocalDate(), false);
             if (extraLegs.size() > 0) potentialNewLegs.addAll(extraLegs);
         }

         // Save and then remove legs that are outside the valid time window
        Legs invalidNewLegs = new Legs();
         for (Leg thisLeg : potentialNewLegs) {
             if (thisLeg.boardingTime.isBefore(startBoardingWindow) || thisLeg.boardingTime.isAfter(endBoardingWindow))
                 invalidNewLegs.add(thisLeg);
         }
         if (invalidNewLegs.size() > 0)
                 for (Leg thisLeg : invalidNewLegs) potentialNewLegs.remove(thisLeg);

        return potentialNewLegs;
     }

    /**
     * Determines the next disembarking airport and disembarking dates, then gets all legs that match these parameters
     * from the LocalFlightDatabase. Also determines the time window for previous arrivals and returns only legs inside
     * this window.
     *
     * @pre the flight filter is not empty
     */
    private Legs getValidArrivingLegs(Flight newFlight) {
        // Use the flight filter airport and time window by default
        Airport disembarkingAirport = flightFilter.arrivalAirport();
        ZonedDateTime startDisembarkingWindow = flightFilter.startFlightDateTime();
        ZonedDateTime endDisembarkingWindow = flightFilter.endFlightDateTime();

        // If there are already legs on the flight, use airport and time window based on the first leg of the flight
        if (newFlight.getDepartureAirport() != null) {
            disembarkingAirport = newFlight.getDepartureAirport();
            endDisembarkingWindow = newFlight.getDepartureTime().minus(Saps.MIN_LAYOVER_TIME);
            startDisembarkingWindow = endDisembarkingWindow.minus(Saps.MAX_LAYOVER_TIME.minus(Saps.MIN_LAYOVER_TIME));
        }

        // Get legs from the disembarking airport and the start time date
        Legs potentialNewLegs = LocalFlightDatabase.getInstance().getDisembarkingLegList(disembarkingAirport, startDisembarkingWindow.toLocalDate(), false);
        // If the end time is on the next day get legs from that date too
        if (startDisembarkingWindow.toLocalDate().compareTo(endDisembarkingWindow.toLocalDate()) < 0) {
            Legs extraLegs = LocalFlightDatabase.getInstance().getDisembarkingLegList(disembarkingAirport, endDisembarkingWindow.toLocalDate(), false);
            if (extraLegs.size() > 0) potentialNewLegs.addAll(extraLegs);
        }

        // Save and then remove legs that are outside the valid time window
        Legs invalidNewLegs = new Legs();
        for (Leg thisLeg : potentialNewLegs) {
            if (thisLeg.disembarkingTime.isBefore(startDisembarkingWindow) || thisLeg.disembarkingTime.isAfter(endDisembarkingWindow))
                invalidNewLegs.add(thisLeg);
        }
        if (invalidNewLegs.size() > 0)
            for (Leg thisLeg : invalidNewLegs) potentialNewLegs.remove(thisLeg);

        return potentialNewLegs;
    }
}
