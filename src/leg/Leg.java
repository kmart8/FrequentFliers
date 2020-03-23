package leg;

public class Leg {

    // Fields
    public String disembarkingAirport;
    public String boardingAirport;
    public String disembarkingTime;
    public float boardingTime;
    public int flightNumber;
    public int reservedCoachSeats;
    public int reservedFirstClassSeats;
    public float legDuration;
    public String plane; // the plane model
    public float coachPrice;
    public float firstClassPrice;
    public String flightTime;

    // Default Constructor
    public Leg() {
        disembarkingAirport = "";
        boardingAirport = "";
        disembarkingTime = "";
        boardingTime = Float.MAX_VALUE;
        flightNumber = Integer.MAX_VALUE;
        reservedCoachSeats = Integer.MAX_VALUE;
        reservedFirstClassSeats = Integer.MAX_VALUE;
        legDuration = Float.MAX_VALUE;
        plane = "";
        coachPrice = Float.MAX_VALUE;
        firstClassPrice = Float.MAX_VALUE;
        flightTime = "";
    }
    // Constructor
    public Leg(String dAirport, String bAirport, String dTime, float bTime, int fNumber, int rCoach, int rFirstClass,
               float lDuration, String pNumber, float cPrice, float fCPrice) {

        disembarkingAirport = dAirport;
        boardingAirport = bAirport;
        disembarkingTime = dTime;
        boardingTime = bTime;
        flightNumber = fNumber;
        reservedCoachSeats = rCoach;
        reservedFirstClassSeats = rFirstClass;
        legDuration = lDuration;
        plane = pNumber;
        coachPrice = cPrice;
        firstClassPrice = fCPrice;
    }

    public String toString() {
        return disembarkingAirport+' '+boardingAirport+' '+disembarkingTime+' '+
                boardingTime+' '+flightNumber+' '+reservedCoachSeats+' '+
                reservedFirstClassSeats+' '+
                legDuration+' '+plane+' '+coachPrice+' '+firstClassPrice;

    }
    // get Methods
    public String getDisembarkingAirport() {
        return disembarkingAirport;
    }

    public String getBoardingAirport() {
        return boardingAirport;
    }

    public String getDisembarkingTime() {
        return disembarkingTime;
    }

    public float getBoardingTime() {
        return boardingTime;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getReservedCoachSeats() {
        return reservedCoachSeats;
    }

    public int getReservedFirstClassSeats() {
        return reservedFirstClassSeats;
    }

    public float getLegDuration() {
        return legDuration;
    }

    public String getPlane() {
        return plane;
    }

    public float getCoachPrice() {
        return coachPrice;
    }

    public float getFirstClassPrice() {
        return firstClassPrice;
    }

    // set Methods
    public void setDisembarkingTime(String newDisembarkingTime) {
        disembarkingTime = newDisembarkingTime;
    }

    public void setBoardingTime(float newBoardingTime) {
        boardingTime = newBoardingTime;
    }
}
