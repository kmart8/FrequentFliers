package ui;

import dao.ServerInterface;
import driver.TripBuilder;
import driver.Trip;
import flight.Flight;
import flight.Flights;
import leg.Leg;
import leg.Legs;
import us.dustinj.timezonemap.TimeZoneMap;
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

/**
 * This class serves as a viewer for the UIModel. It is controlled by the UIController. The form, components,
 * and constructor were all automatically generated by the GUI Designer (WYSIWYG). This class passes user input
 * to the controller, and retrieves display values from the controller, and activates the TripBuilder controller
 * when the user requests search functionality or trip related functionality.
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
    private JButton startOverButton;
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
    private JComboBox timeTypeComboBox;
    private JFormattedTextField endTimeFormattedTextField;
    private JTabbedPane TableViewer;
    private JComboBox tripTypeComboBox;
    private JComboBox sortTypeComboBox;
    private JComboBox sortDirectionComboBox;
    private JButton newSearchButton;
    private JFrame frameHandle;

    /** List of flights currently displayed in the flightDisplayTable */
    private Flights displayList = new Flights();
    /** List of legs currently displayed in the legDisplayTable */
    private Legs legsInCart = new Legs();

    /** List of UI components which should be active during flight search to enable user input */
    private List<JComponent> midSearchEnabledList = new ArrayList<>();
    /** List of UI components which should be active during before the flight search to enable user input */
    private List<JComponent> preSearchEnabledList = new ArrayList<>();

    /** Controller that parses and validates user input, and saves it in a model */
    private static UIController controller;

    /** Table model to modify and store the data of flights */
    private static DefaultTableModel flightDisplayData = new DefaultTableModel();
    /** Table model to modify and store the data of legs */
    private static DefaultTableModel legDisplayData = new DefaultTableModel();

    /** Selected sort method */
    private static String sortType = "Price";
    /** Selected sort direction, true if ascending and false if descending */
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

        // Create the list of JComponents that should be enabled/disabled during state transitions
        buildMidSearchEnabledList();
        buildPreSearchEnabledList();
        isPreSearchState(true);

        // User may only interact with the trip combobox at the very beginning
        tripTypeComboBox.setEnabled(true);
        numberOfPassengersFormattedTextField.setEnabled(true);

        // Set the default seating types
        String[] temp = Arrays.copyOf(Saps.SEATING_TYPES.toArray(), Saps.SEATING_TYPES.toArray().length, String[].class);
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
        legDisplayData.addColumn("Coach Seats Remaining");
        legDisplayData.addColumn("First Class Seats Remaining");
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

        // Initialize trip type
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
                System.out.println("Search Button User Interaction");
                displayList = TripBuilder.getInstance().searchForFlights();
                if (displayList.size() != 0) {
                    buildFlightTable();
                    // After the user has initiated search, disable all the search fields
                    isPreSearchState(true);
                }
            }
        });

        // When the reset button is pressed, reinitialize the GUI
        startOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trip.getInstance().resetTrip();
                legsInCart = new Legs();
                buildLegTable();
                displayList = new Flights();
                buildFlightTable();
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

        // When confirm reservation button is pressed, reserve seats on the server for each leg in the trip
        confirmReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Trip.getInstance().isFull())
                {
                    if (!Trip.getInstance().isBooked()) {
                        // Wait until the lock can be obtained
                        int timerID = NotificationManager.getInstance().startBusyTimer();
                        Trip.getInstance().bookTrip(controller.getAcceptedInput().numberOfPassengers());

                        // Notify the user of success or failure
                        if (Trip.getInstance().isBooked()) {
                            NotificationManager.getInstance().popupSuccess("Trip booking was successful!");
                            // Update the table to display the new number of remaining seats on the legs
                            Trip.getInstance().refreshTrip();
                            legsInCart = Trip.getInstance().getLegs();
                            buildLegTable();
                        } else
                            NotificationManager.getInstance().popupError("Error making reservation, no reservations created!");
                        NotificationManager.getInstance().stopBusyTimer(timerID);
                    } else
                        NotificationManager.getInstance().popupError("Trip is already booked!");
                } else
                    NotificationManager.getInstance().popupError("Error making reservation, not enough flights selected!");
            }
        });

        // When new search is pressed activate the mid search state, and disable user input for trip type
        newSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tripTypeComboBox.setEnabled(false);
                numberOfPassengersFormattedTextField.setEnabled(false);
                isPreSearchState(false);
                // clear the flight table
                displayList = new Flights();
                buildFlightTable();
                TableViewer.setSelectedIndex(0);
            }
        });
        
        addFlightToTrip.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  if (flightDisplayTable.getSelectedRow() != -1) {
                      Flight flightToAdd = displayList.get(flightDisplayTable.getSelectedRow());
                      if (!Trip.getInstance().isFull()) {
                          boolean success = Trip.getInstance().addFlightToTrip(flightToAdd,controller.getAcceptedInput());
                          if (success) {
                              legsInCart = Trip.getInstance().getLegs();
                              buildLegTable();
                              NotificationManager.getInstance().popupSuccess("Flight was successfully added to cart!");
                              isPreSearchState(true);

                              if (Trip.getInstance().isFull()) {
                                  TableViewer.setSelectedIndex(1);
                              } else if (Trip.getInstance().getTripType().equals("Round-Trip")) {
                                  String arrival = arrivalAirportFormattedTextField.getText();
                                  controller.setArrivalAirport(null);
                                  String departure = departureAirportFormattedTextField.getText();
                                  arrivalAirportFormattedTextField.setText(departure);
                                  departureAirportFormattedTextField.setText(arrival);
                                  isPreSearchState(false);
                              }
                          }
                      } else {
                          NotificationManager.getInstance().popupError("One-way trip is full, no new flights can be added!");
                      }
                  } else NotificationManager.getInstance().popupError("No flight selected!");
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
        endTimeFormattedTextField.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setEndTime(endTimeFormattedTextField.getText());
                endTimeFormattedTextField.setText(controller.getEndTime());
            }
        });

        // When any combobox is changed, overwrite the value in the model
        seatingTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setSeatingType(seatingTypeComboBox.getSelectedItem().toString());
            }
        });
        timeTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setTimeType(timeTypeComboBox.getSelectedItem().toString());
            }
        });
        tripTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Trip.getInstance().setTripType(tripTypeComboBox.getSelectedItem().toString());
                System.out.println("User changed the trip type to " + Trip.getInstance().getTripType());
            }
        });
        sortTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortType = sortTypeComboBox.getSelectedItem().toString();
                System.out.println("User changed the sort type to " + sortType);
                // Resort the flight table
                buildFlightTable();
            }
        });
        sortDirectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sortDirection = sortDirectionComboBox.getSelectedItem().toString();
                System.out.println("User changed the sort direction to " + sortDirection);
                // Resort the flight table
                isAscending = sortDirection.equals("Ascending");
                buildFlightTable();
            }
        });
    }

    /**
     * Creates the GUI JFrame and builds all of the JComponents.
     * All of the hidden source code is implemented during the 'setContentPane' function
     *
     * @post the application window is visible and located in the center of the screen
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
     * Creates a list of essential user interactions that should be available while editing search options.
     * Visually shows the user what they should be doing and prevents confusing input
     */
    private void buildMidSearchEnabledList() {
        midSearchEnabledList.add(seatingTypeComboBox);
        midSearchEnabledList.add(searchForFlightsButton);
        midSearchEnabledList.add(departureAirportFormattedTextField);
        midSearchEnabledList.add(arrivalAirportFormattedTextField);
        midSearchEnabledList.add(dateFormattedTextField);
        midSearchEnabledList.add(startTimeFormattedTextField);
        midSearchEnabledList.add(maximumLayoversFormattedTextField);
        midSearchEnabledList.add(timeTypeComboBox);
        midSearchEnabledList.add(endTimeFormattedTextField);
    }

    /**
     * Creates a list of essential user interactions that should be available before and after searching for flights.
     * Visually shows the user what they should be doing and prevents confusing input
     */
    private void buildPreSearchEnabledList() {
        preSearchEnabledList.add(newSearchButton);
        preSearchEnabledList.add(confirmReservationButton);
        preSearchEnabledList.add(addFlightToTrip);
    }

    /**
     * Iterates over the lists of important JComponents to disable/enable to transition states
     *
     * @param tf a boolean where true represents pre-search state and false represents mid-search state
     */
    private void isPreSearchState(boolean tf) {
        for (JComponent element : preSearchEnabledList) {
            element.setEnabled(tf);
        }
        for (JComponent element : midSearchEnabledList) {
            element.setEnabled(!tf);
        }
    }


    /**
     * Updates the table to display the current list of legs.
     *
     * @pre Legs should be sorted in ascesnding chronological order
     * @post Old values have been cleared from the table and any new values are now displayed.
     */
    private void buildLegTable(){
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
            String coachSeatsReserved = Integer.toString(leg.getRemainingSeats(Saps.SEATING_TYPES.get(0)));
            String firstClassSeatsReserved = Integer.toString(leg.getRemainingSeats(Saps.SEATING_TYPES.get(1)));
            String flightTime = flightTimeStyle.format(LocalTime.MIN.plus(leg.getLegDuration()));

            // Add these strings as a new row in the table (ORDER MATTERS)
            table.addRow(new Object[] {coachPrice, firstClassPrice, flightTime, departureAirport,departureDate,
                                        departureTime,arrivalAirport,arrivalDate,arrivalTime,flightNumber,plane,
                                        coachSeatsReserved, firstClassSeatsReserved});
            }
        System.out.println("Legs been displayed");
    }

    /**
     * Updates the table to display the current list [possibly empty] of flights.
     *
     * Also sorts the flights by one of 4 user specified attributes in either ascending or descending order.
     *
     * @post Old values have been cleared from the table and any new values have been sorted and displayed.
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
            String seating = flight.getSeatingType();

            // Add these strings as a new row in the table (ORDER MATTERS)
            table.addRow(new Object[] {price, departureAirport, departureDate, departureTime,arrivalAirport,
                    arrivalDate,arrivalTime,duration,layovers,seating});
        }
        System.out.println("Flights have been displayed");
    }
}
