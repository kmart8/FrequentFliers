package flight;

import airport.Airport;
import dao.LocalFlightDatabase;
import leg.Leg;
import leg.Legs;
import ui.UIModel;
import utils.Saps;

import java.time.ZonedDateTime;
import java.util.LinkedList;

public class FlightManager {
    /** Queue of incomplete fights */
    private LinkedList<Flight> constructionQueue = new LinkedList<Flight>();
    /** List of completed fights which match the filter parameters */
    private Flights validFlights;
    /** List of completed fights which do not match the filter parameters */
    private Flights filteredFlights;
    /** Filter for checking the validity of completed flights */
    private UIModel flightFilter;

    public FlightManager(UIModel filter){
        flightFilter = filter;
        validFlights = new Flights();
        filteredFlights = new Flights();
    }
    /**
     * get valid flights
     *
     * @return the list of valid completed flights
     */
    public Flights validFlights(){
        return validFlights;
    }

    /**
     * set the filter for constructing flights
     *
     * @param flightFilter The UIModel produced by user input
     */
    public void flightFilter(UIModel flightFilter) {
        this.flightFilter = flightFilter;
    }

    /**
     * Iterates through the construction queue until no more flights remain on the queue. Each time a flight is popped
     * from the queue, it is checked for validity and completeness against the flight filter. If is is incomplete
     * copies are enqueued back onto the queue.
     *
     * @pre the flight filter is not empty
     * @post the construction queue is empty
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
     * Adds copies of a flight back to the construction queue with an additional leg. If the UIModel specifies a
     * departure date/time window, then legs are added to the end of the flight. If the UIModel specifies an
     * arrival date/time window, then legs are added to the beginning of the flight.
     *
     * @pre the flight filter is not empty
     * @post any newly generated copies are added to the construction queue
     */
     public void enqueueFlight(Flight newFlight){
        if (flightFilter.timeType().equals("Departure")){
            Legs newLegs = getValidBoardingLegs(newFlight);
            for (Leg thisLeg : newLegs) {
                Flight copyFlight = new Flight();
                try{
                    copyFlight = newFlight.clone();
                } catch (CloneNotSupportedException E){

                }
                copyFlight.addLegToEnd(thisLeg);
                constructionQueue.add(copyFlight);
            }
        } else {
            Legs newLegs = getValidArrivingLegs(newFlight);
            for (Leg thisLeg : newLegs) {
                Flight copyFlight = new Flight();
                try{
                    copyFlight = newFlight.clone();
                } catch (CloneNotSupportedException E){

                }
                copyFlight.addLegToBeginning(thisLeg);
                constructionQueue.push(copyFlight);
            }
        }
     }

    /**
     * Determines the next boarding airport and boarding dates, then gets all legs that match these parameters from the
     * LocalFlightDatabase. Also determines the time window for boarding and returns only legs inside this window.
     *
     * @pre the flight filter is not empty and the departure airport of the flight filter is not empty
     */
     private Legs getValidBoardingLegs(Flight newFlight){
        Airport boardingAirport = flightFilter.departureAirport();
        ZonedDateTime startBoardingWindow = flightFilter.startFlightDateTime();
        ZonedDateTime endBoardingWindow = flightFilter.endFlightDateTime();
        if (newFlight.getArrivalAirport() != null) {
            boardingAirport = newFlight.getArrivalAirport();
            startBoardingWindow = newFlight.getArrivalTime().plus(Saps.MIN_LAYOVER_TIME);
            endBoardingWindow = startBoardingWindow.plus(Saps.MAX_LAYOVER_TIME.minus(Saps.MIN_LAYOVER_TIME));
        }

        Legs potentialNewLegs = LocalFlightDatabase.getInstance().getBoardingLegList(boardingAirport,startBoardingWindow.toLocalDate(),false);
        Legs invalidNewLegs = new Legs();

         for (Leg thisLeg : potentialNewLegs) {
             if (thisLeg.boardingTime.isBefore(startBoardingWindow) || thisLeg.boardingTime.isAfter(endBoardingWindow)){
                 invalidNewLegs.add(thisLeg);
             }
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
     * @pre the flight filter is not empty and the arrival airport of the flight filter is not empty
     */
    private Legs getValidArrivingLegs(Flight newFlight){
        Airport disembarkingAirport = flightFilter.arrivalAirport();
        ZonedDateTime startDisembarkingWindow = flightFilter.startFlightDateTime();
        ZonedDateTime endDisembarkingWindow = flightFilter.endFlightDateTime();
        if (newFlight.getDepartureAirport() != null) {
            disembarkingAirport = newFlight.getDepartureAirport();
            endDisembarkingWindow = newFlight.getDepartureTime().minus(Saps.MIN_LAYOVER_TIME);
            startDisembarkingWindow = endDisembarkingWindow.minus(Saps.MIN_LAYOVER_TIME.minus(Saps.MIN_LAYOVER_TIME));
        }

        Legs potentialNewLegs = LocalFlightDatabase.getInstance().getDisembarkingLegList(disembarkingAirport,startDisembarkingWindow.toLocalDate(),false);

        for (Leg thisLeg : potentialNewLegs) {
            if (thisLeg.disembarkingTime.isBefore(startDisembarkingWindow) || thisLeg.disembarkingTime.isAfter(endDisembarkingWindow)){
                potentialNewLegs.remove(thisLeg);
            }
        }
        return potentialNewLegs;
    }


}
