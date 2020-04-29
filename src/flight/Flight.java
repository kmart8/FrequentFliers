package flight;

import airport.Airport;
import leg.Leg;
import leg.Legs;
import ui.UIModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

public class Flight implements Comparable<Flight>, Comparator<Flight>, Cloneable {
    // Placeholder class to work on flight manager
    private Legs legs;
    private String filterReason;

    public String getFilterReason () {
        return filterReason;
    }

    public Airport getDepartureAirport () {
        return new Airport();
    }
    public Airport getArrivalAirport () {
        return new Airport();
    }
    public ZonedDateTime getArrivalTime () {
        return ZonedDateTime.of(LocalDateTime.of(2020,5,1,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
    }
    public ZonedDateTime getDepartureTime () {
        return ZonedDateTime.of(LocalDateTime.of(2020,5,1,0,0), ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)));
    }


    public int getNumberOfLegs(){
        if (legs.isEmpty()) return 0;
        else return legs.size();
    }

    public int getNumberOfLayovers(){
        return Math.max(0, getNumberOfLegs()-1);
    }

    public void checkMatch(UIModel filter){
       filterReason = "hello world";
    }

    public void addLegPrevious(Leg newLeg){
        legs.add(0, newLeg);
    }

    public void addLegAfter(Leg newLeg){
        legs.add(newLeg);
    }

    /**
     * Compare
     *
     *
     * @return
     */
    public int compareTo(Flight other)    {
        return 1;
    }

    /**
     * Compare two flights for sorting, ordering
     *
     *
     *
     * @param
     * @param
     * @return
     */
    public int compare(Flight flight1, Flight flight2) {
        return 1;
    }


    /**
     * Determine if two flight objects are the same flight
     *
     * @param obj is the object to compare against this object
     * @return true if the
     */
    @Override
    public boolean equals (Object obj) {
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
