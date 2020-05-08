package driver;

import flight.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UIModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TripTest {
    Flight testFlight1 = mock(Flight.class);
    Flight testFlight2 = mock(Flight.class);
    Flight testFlight3 = mock(Flight.class);
    Flight testFlight4 = mock(Flight.class);

    @BeforeEach
    void setUp() {
        when(testFlight1.isAfterBy(any(), any())).thenReturn(true);
        when(testFlight1.getFilterReason()).thenReturn("complete");
        when(testFlight2.isAfterBy(any(), any())).thenReturn(true);
        when(testFlight2.getFilterReason()).thenReturn("complete");
        when(testFlight3.isAfterBy(any(), any())).thenReturn(true);
        when(testFlight3.getFilterReason()).thenReturn("invalid");
        when(testFlight4.isAfterBy(any(), any())).thenReturn(false);

        Trip.getInstance().resetTrip();
        Trip.getInstance().setTripType("One-Way");
    }

    @Test
    void addFlightToTrip() {
        Trip.getInstance().addFlightToTrip(testFlight1, new UIModel());
        assert Trip.getInstance().getTrip().size() == 1;
        Trip.getInstance().addFlightToTrip(testFlight2, new UIModel());
        assert Trip.getInstance().getTrip().size() == 1;
        Trip.getInstance().setTripType("Round-Trip");
        Trip.getInstance().addFlightToTrip(testFlight4, new UIModel());
        assert Trip.getInstance().getTrip().size() == 1;
        Trip.getInstance().addFlightToTrip(testFlight2, new UIModel());
        assert Trip.getInstance().getTrip().size() == 2;
        Trip.getInstance().addFlightToTrip(testFlight3, new UIModel());
        assert Trip.getInstance().getTrip().size() == 2;
    }

    @Test
    void isFull() {
        assert !Trip.getInstance().isFull();
        Trip.getInstance().addFlightToTrip(testFlight1, new UIModel());
        assert Trip.getInstance().isFull();
        Trip.getInstance().setTripType("Round-Trip");
        assert !Trip.getInstance().isFull();
        Trip.getInstance().addFlightToTrip(testFlight2, new UIModel());
        assert Trip.getInstance().isFull();
    }

    @Test
    void isTripValid() {
        Trip.getInstance().addFlightToTrip(testFlight1, new UIModel());
        assert Trip.getInstance().isTripValid();
        Trip.getInstance().setTripType("Round-Trip");
        assert !Trip.getInstance().isTripValid();
        Trip.getInstance().addFlightToTrip(testFlight2, new UIModel());
        assert Trip.getInstance().isTripValid();
        Trip.getInstance().resetTrip();
        Trip.getInstance().setTripType("Round-Trip");
        Trip.getInstance().addFlightToTrip(testFlight1, new UIModel());
        Trip.getInstance().addFlightToTrip(testFlight3, new UIModel());
        assert !Trip.getInstance().isTripValid();
    }
}