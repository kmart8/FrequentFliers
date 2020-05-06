package ui;

import dao.ServerInterface;
import driver.TripBuilder;
import driver.Trip;
import flight.Flight;
import flight.Flights;
import leg.Leg;
import leg.Legs;
import utils.NotificationManager;
import utils.Saps;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.floor;

/**
 * This class serves as a viewer for the UIModel. It is controlled by the UIController. The form, components,
 * and constructor were all automatically generated by the GUI Designer (WYSIWYG). This class passes user input
 * to the controller, and retrieves display values from the controller, and activates the TripBuilder controller
 * when the user requests search functionality.
 *
 * @author Chris Collins
 * @version 1.0 2020-03-26
 * @since 2020-03-06
 *
 */
public class ReservationApp {
    // Automatically generated variables for handling Swing components in the GUI Builder
    private JComboBox<String> seatingTypeComboBox;
    private JButton addFlightToTrip;
    private JButton confirmReservationButton;
    private JButton quitButton;
    private JButton resetButton;
    private JButton searchForFlightsButton;
    private JPanel mainPanel;
    private JFormattedTextField dateFormattedTextField;
    private JFormattedTextField startTimeFormattedTextField;
    private JFormattedTextField departureAirportFormattedTextField;
    private JFormattedTextField arrivalAirportFormattedTextField;
    private JFormattedTextField maximumLayoversFormattedTextField;
    private JFormattedTextField numberOfPassengersFormattedTextField;
    private JTable flightDisplayTable;
    private JTable legDisplayTable;
    private JComboBox TimeTypeComboBox;
    private JFormattedTextField endTimeFormattedTextField;
    private JTabbedPane tabbedPane1;
    private JComboBox tripTypeComboBox;
    private JComboBox sortTypeComboBox;
    private JComboBox sortDirectionComboBox;
    private JTabbedPane DisplayDetails;
    private JFrame frameHandle;
    private Flights displayList = new Flights();
    private Legs legsInCart = new Legs();

    // List of UI components which should be inactive during long operations to prevent user input
    // and signal to the user that the program is busy
    private List<JComponent> busyList = new ArrayList<>();

    // Model that stores the current state of user input and validates new input
    private static UIController controller;

    // Create a table model to modify and store the data of the display tables
    private static DefaultTableModel flightDisplayData = new DefaultTableModel();
    private static DefaultTableModel legDisplayData = new DefaultTableModel();

    // Currently selected sort method
    private static String sortType = "Price";
    // Currently selected sort direction, true if ascending and false if descending
    private static boolean isAscending = true;

    /**
     * Main method is required by JavaFX, which is used by the GUI Designer,
     * but is not needed for any other reason
     *
     * @param args String []
     */
    public static void main(String[] args) {

    }

    /**
     * Creates the GUI window, adds components, and populates default values.
     */
    public void initializeUIElements() {
        // Create the JFrame and JComponents
        initializeWindow();

        // Create the list of JComponents that should be disabled while the app is busy
        buildBusyList();
        String[] temp = Arrays.copyOf(Saps.SEATING_TYPES.toArray(), Saps.SEATING_TYPES.toArray().length, String[].class);
        // Set the default seating types
        seatingTypeComboBox.setModel(new DefaultComboBoxModel<String>(temp));

        // Set the columns of the leg table
        legDisplayData.setColumnCount(0);
        legDisplayData.addColumn("Coach Price");
        legDisplayData.addColumn("First Class Price");
        legDisplayData.addColumn("Flight Time");
        legDisplayData.addColumn("Departure Airport");
        legDisplayData.addColumn("Departure Date");
        legDisplayData.addColumn("Departure Time");
        legDisplayData.addColumn("Arrival Airport");
        legDisplayData.addColumn("Arrival Date");
        legDisplayData.addColumn("Arrival Time");
        legDisplayData.addColumn("Flight Number");
        legDisplayData.addColumn("Plane Model");
        legDisplayData.addColumn("Coach Seats Reserved");
        legDisplayData.addColumn("First Class Seats Reserved");
        legDisplayTable.setModel(legDisplayData);

        // Set the columns of the table
        flightDisplayData.setColumnCount(0);
        flightDisplayData.addColumn("Total Price");
        flightDisplayData.addColumn("Departure Airport");
        flightDisplayData.addColumn("Departure Date");
        flightDisplayData.addColumn("Departure Time");
        flightDisplayData.addColumn("Arrival Airport");
        flightDisplayData.addColumn("Arrival Date");
        flightDisplayData.addColumn("Arrival Time");
        flightDisplayData.addColumn("Flight Duration");
        flightDisplayData.addColumn("Number of Layovers");
        flightDisplayData.addColumn("Seating Type");
        flightDisplayTable.setModel(flightDisplayData);

        // Set trip type
        Trip.getInstance().setTripType(tripTypeComboBox.getSelectedItem().toString());

        System.out.println("Finished Initialization");
    }

    /**
     * Constructor method is automatically generated by GUI Designer when adding listeners.
     * The contents of the overriden methods have been modified to respond appropriately to user interaction.
     * @param controller a reference to the controller, which will parse/validate input to store in the model
     */
    public ReservationApp(UIController controller) {
        // Store a reference to the controller
        ReservationApp.controller = controller;
        initializeUIElements();
        // When the search button is pressed
        searchForFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the busy status to true, until the table has been built or the operation is canceled
                System.out.println("Search Button User Interaction");
                busy(true);
                displayList = TripBuilder.getInstance().searchForFlights();
                if (displayList.size() != 0)
                    buildFlightTable();

                busy(false);
            }
        });

        // When any text field is changed, attempt to overwrite the value in the model.
        // Then, get the validated value stored in the model to update the display of the component.
        departureAirportFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setDepartureAirport(departureAirportFormattedTextField.getText());
                departureAirportFormattedTextField.setText(controller.getDepartureAirport());
            }
        });
        arrivalAirportFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setArrivalAirport(arrivalAirportFormattedTextField.getText());
                arrivalAirportFormattedTextField.setText(controller.getArrivalAirport());
            }
        });
        dateFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setFlightDate(dateFormattedTextField.getText());
                dateFormattedTextField.setText(controller.getFlightDate());
            }
        });
        startTimeFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setStartTime(startTimeFormattedTextField.getText());
                startTimeFormattedTextField.setText(controller.getStartTime());
            }
        });
        numberOfPassengersFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setNumberOfPassengers(numberOfPassengersFormattedTextField.getText());
                numberOfPassengersFormattedTextField.setText(controller.getNumberOfPassengers());
            }
        });
        maximumLayoversFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setNumberOfLayovers(maximumLayoversFormattedTextField.getText());
                maximumLayoversFormattedTextField.setText(controller.getNumberOfLayovers());
            }
        });

        // When the reset button is pressed, reinitialize the GUI
        // This will force any disabled JComponents to re-enable
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trip.getInstance().resetTrip();
                if (legsInCart.size() > 0) { legsInCart.clear(); }
                System.out.println("Reset User Interaction");
                initializeUIElements();
            }
        });

        // When the quit button is pressed, exit the program
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        seatingTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setSeatingType(seatingTypeComboBox.getSelectedItem().toString());
            }
        });
        endTimeFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setEndTime(endTimeFormattedTextField.getText());
                endTimeFormattedTextField.setText(controller.getEndTime());
            }
        });
        TimeTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setTimeType(TimeTypeComboBox.getSelectedItem().toString());
            }
        });

        // adds the selected flight to the flightCart (Flights object in TripBuilder)
        addFlightToTrip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flightDisplayTable.getSelectedRow() != -1) {
                    legsInCart.clear();
                    switch (Trip.getInstance().getTripType()){
                        case "One-Way":
                            if (Trip.getInstance().getTrip().size() < 1) {
                                Trip.getInstance().addFlightToTrip(displayList.get(flightDisplayTable.getSelectedRow()));
                                for (Flight flight : Trip.getInstance().getTrip()){
                                    legsInCart.addAll(flight.legList());
                                }
                                buildLegTable(legsInCart);
                                System.out.println("User added flight to cart");
                            } else {
                                System.out.println("Trip is full for One-Way trip");
                            }
                            break;
                        case "Round-Trip":
                            if (Trip.getInstance().getTrip().size() < 1) {
                                Trip.getInstance().addFlightToTrip(displayList.get(flightDisplayTable.getSelectedRow()));
                                for (Flight flight : Trip.getInstance().getTrip()){
                                    legsInCart.addAll(flight.legList());
                                }
                                buildLegTable(legsInCart);
                                System.out.println("User added flight to cart");
                                String arrival = arrivalAirportFormattedTextField.getText();
                                controller.setArrivalAirport(null);
                                String departure = departureAirportFormattedTextField.getText();
                                arrivalAirportFormattedTextField.setText(departure);
                                departureAirportFormattedTextField.setText(arrival);
                                displayList = new Flights();
                                buildFlightTable();
                            } else if (Trip.getInstance().getTrip().size() < 2) {
                                Flight firstFlight = Trip.getInstance().getTrip().get(0);
                                // Testing if departure time is before arrival time, in which case an error is returned. else returning flight is booked
                                Boolean isBefore = displayList.get(flightDisplayTable.getSelectedRow()).getDepartureTime().isBefore(firstFlight.getArrivalTime());
                                if (isBefore) {
                                    System.out.println("Departure time of second flight must be after arrival time of first flight.");
                                } else {
                                    Trip.getInstance().addFlightToTrip(displayList.get(flightDisplayTable.getSelectedRow()));
                                    for (Flight flight : Trip.getInstance().getTrip()){
                                        legsInCart.addAll(flight.legList());
                                    }
                                    buildLegTable(legsInCart);
                                    System.out.println("User added flight to cart");
                                }
                            }
                            else {
                                System.out.println("Trip is full for Round-Trip");
                            }
                            break;
                    }
                }
                else NotificationManager.getInstance().popupError("No flight selected!");

            }
        });
        tripTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Trip.getInstance().setTripType(tripTypeComboBox.getSelectedItem().toString());
                System.out.println("User input updated trip type"); }
        });

        // Currently nested for loop (looping for all booked flights and all passengers on those flights,
        // could improve efficiency in postLegReservation 
        confirmReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int timerID = NotificationManager.getInstance().startBusyTimer();
                boolean obtainedLock = false;
                while (!obtainedLock) {
                    obtainedLock = ServerInterface.INSTANCE.lock();
                    try{wait(100);} catch (Exception exc) {};
                }
                Flights bookedFlights = Trip.getInstance().getTrip();
                boolean isSuccess = ServerInterface.INSTANCE.postLegReservation(bookedFlights, controller.getAcceptedInput().numberOfPassengers());
                ServerInterface.INSTANCE.unlock();
                if (isSuccess)
                    NotificationManager.getInstance().popupSuccess("Trip booking was successful!");
                NotificationManager.getInstance().stopBusyTimer(timerID);
            }
        });
        sortTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortType = sortTypeComboBox.getSelectedItem().toString();
                buildFlightTable();
            }
        });
        sortDirectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sortDirection = sortDirectionComboBox.getSelectedItem().toString();
                isAscending = sortDirection.equals("Ascending");
                buildFlightTable();
            }
        });
    }

    /**
     * Creates the GUI JFrame and builds all of the JComponents.
     * All of the hidden source code is implemented during the 'setContentPane' function
     */
    private void initializeWindow() {
        if (frameHandle != null)
            frameHandle.dispose();
        frameHandle = new JFrame("ReservationApp");
        frameHandle.setContentPane(mainPanel);
        frameHandle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHandle.pack();
        frameHandle.setLocationRelativeTo(null);
        frameHandle.setVisible(true);
    }


    /**
     * Creates a list of essential user interactions that should be disabled during long operations.
     * Visually shows the user that the program is busy, and prevents confusing input
     */
    private void buildBusyList() {
        busyList.add(seatingTypeComboBox);
        busyList.add(addFlightToTrip);
        busyList.add(confirmReservationButton);
        busyList.add(searchForFlightsButton);
        busyList.add(departureAirportFormattedTextField);
        busyList.add(arrivalAirportFormattedTextField);
        busyList.add(dateFormattedTextField);
        busyList.add(startTimeFormattedTextField);
        busyList.add(maximumLayoversFormattedTextField);
        busyList.add(numberOfPassengersFormattedTextField);
        busy(false);
    }

    /**
     * Iterates over the list of important JComponents to disable/enable them
     *
     * @param tf a boolean where true represents busy state (disabled) and false represents free (enabled)
     */
    private void busy(boolean tf) {
        for (JComponent element : busyList) {
            element.setEnabled(!tf);
        }
    }

    /**
     * Updates the table to display the current list of legs
     *
     */
private void buildLegTable(Legs legsInCart){
        // Get the current model of the table and remove all previous entries
        DefaultTableModel table = (DefaultTableModel) legDisplayTable.getModel();
        table.setRowCount(0);

        // Set the default formats for displaying dates, times, and prices
        DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeStyle = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter flightTimeStyle = DateTimeFormatter.ofPattern("HH:mm");
        NumberFormat priceStyle = NumberFormat.getCurrencyInstance(Locale.US);

        // Add a new row to the table for each leg on the list
        for (Leg leg : legsInCart) {
            // Generate correctly formatted strings from Leg attributes
            String coachPrice = priceStyle.format(leg.getCoachPrice());
            String firstClassPrice = priceStyle.format(leg.getFirstClassPrice());
            String departureAirport = leg.getBoardingAirport().code();
            String departureDate = dateStyle.format(leg.getLocalBoardingTime());
            String departureTime = timeStyle.format(leg.getLocalBoardingTime());
            String arrivalAirport = leg.getDisembarkingAirport().code();
            String arrivalDate = dateStyle.format(leg.getDisembarkingTime());
            String arrivalTime = timeStyle.format(leg.getLocalDisembarkingTime());
            String flightNumber = Integer.toString(leg.getFlightNumber());
            String plane = leg.getPlane().model();
            String coachSeatsReserved = Integer.toString(leg.getReservedCoachSeats());
            String firstClassSeatsReserved = Integer.toString(leg.getReservedFirstClassSeats());
            String flightTime = flightTimeStyle.format(LocalTime.MIN.plus(leg.getLegDuration()));

            // Add these strings as a new row in the table (ORDER MATTERS)
            table.addRow(new Object[] {coachPrice, firstClassPrice, flightTime, departureAirport,departureDate,
                                        departureTime,arrivalAirport,arrivalDate,arrivalTime,flightNumber,plane,
                                        coachSeatsReserved, firstClassSeatsReserved});
            }
        System.out.println("Legs been displayed");
    }

    /**
     * Updates the table to display the list of flights
     *
     */
private void buildFlightTable(){
        // Get the current model of the table and remove all previous entries
        DefaultTableModel table = (DefaultTableModel) flightDisplayTable.getModel();
        table.setRowCount(0);

        // Set the default formats for displaying dates, times, and prices
        DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeStyle = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter flightTimeStyle = DateTimeFormatter.ofPattern("HH:mm");
        NumberFormat priceStyle = NumberFormat.getCurrencyInstance(Locale.US);

        if (displayList.size() !=0)
            switch (sortType) {
                case "Duration":
                    displayList.sortByTravelDuration(isAscending);
                    break;
                case "Departure Time":
                    displayList.sortByDepatureTime(isAscending);
                    break;
                case "Arrival Time":
                    displayList.sortByArrivalTime(isAscending);
                    break;
                default:
                    displayList.sortByPrice(isAscending);
            }

        // Add a new row to the table for each leg on the list
        for (Flight flight : displayList) {
            // Generate correctly formatted strings from Leg attributes
            String price = priceStyle.format(flight.getTotalPrice());
            String departureAirport = flight.getDepartureAirport().code();
            String departureDate = dateStyle.format(flight.getLocalDepartureTime());
            String departureTime = timeStyle.format(flight.getLocalDepartureTime());
            String arrivalAirport = flight.getArrivalAirport().code();
            String arrivalDate = dateStyle.format(flight.getLocalArrivalTime());
            String arrivalTime = timeStyle.format(flight.getLocalArrivalTime());
            String duration = flightTimeStyle.format(LocalTime.MIN.plus(flight.getTotalTravelTime()));
            String layovers = Integer.toString(flight.getNumberOfLayovers());
            String seating = flight.seatingType();

            // Add these strings as a new row in the table (ORDER MATTERS)
            table.addRow(new Object[] {price, departureAirport, departureDate, departureTime,arrivalAirport,
                    arrivalDate,arrivalTime,duration,layovers,seating});
        }
        System.out.println("Flights have been displayed");
    }

}
