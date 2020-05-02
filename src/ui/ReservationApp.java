package ui;

import driver.FlightBuilder;
import leg.Leg;
import leg.Legs;
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
import java.util.List;
import java.util.Locale;

import static java.lang.Math.floor;

/**
 * This class serves as a viewer for the UIModel. It is controlled by the UIController. The form, components,
 * and constructor were all automatically generated by the GUI Designer (WYSIWYG). This class passes user input
 * to the controller, and retrieves display values from the controller, and activates the FlightBuilder controller
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
    private JButton addFlightToCartButton;
    private JButton viewFullFlightDetailsButton;
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
    private JComboBox comboBox1;
    private JFormattedTextField endTimeFormattedTextField;
    private JFrame frameHandle;

    // List of UI components which should be inactive during long operations to prevent user input
    // and signal to the user that the program is busy
    private List<JComponent> busyList = new ArrayList<>();

    // Model that stores the current state of user input and validates new input
    private static UIController controller;

    // Create a table model to modify and store the data of the flightDisplayTable
    private static DefaultTableModel flightDisplayData = new DefaultTableModel();

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

        // Set the default seating types
        seatingTypeComboBox.setModel(new DefaultComboBoxModel<String>(Saps.SEATING_TYPES));

        // Set the columns of the table
        flightDisplayData.setColumnCount(0);
        flightDisplayData.addColumn("Coach Price");
        flightDisplayData.addColumn("First Class Price");
        flightDisplayData.addColumn("Flight Time");
        flightDisplayData.addColumn("Departure Airport");
        flightDisplayData.addColumn("Departure Date");
        flightDisplayData.addColumn("Departure Time");
        flightDisplayData.addColumn("Arrival Airport");
        flightDisplayData.addColumn("Arrival Date");
        flightDisplayData.addColumn("Arrival Time");
        flightDisplayData.addColumn("Flight Number");
        flightDisplayData.addColumn("Plane Model");
        flightDisplayData.addColumn("Coach Seats Reserved");
        flightDisplayData.addColumn("First Class Seats Reserved");
        flightDisplayTable.setModel(flightDisplayData);

        System.out.println("Finished Initialization");
    }

    /**
     * Constructor method is automatically generated by GUI Designer when adding listeners.
     * The contents of the overriden methods have been modified to respond appropriately to user interaction.
     * @param controller a reference to the controller, which will parse/validate input to store in the model
     */
    public ReservationApp(UIController controller) {
        // Store a reference to the controller
        this.controller = controller;
        initializeUIElements();
        // When the search button is pressed
        searchForFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the busy status to true, until the table has been built or the operation is canceled
                System.out.println("Search Button User Interaction");
                busy(true);
                FlightBuilder.getInstance().searchForFlights();
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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setTimeType(seatingTypeComboBox.getSelectedItem().toString());
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
        frameHandle.setVisible(true);
    }


    /**
     * Creates a list of essential user interactions that should be disabled during long operations.
     * Visually shows the user that the program is busy, and prevents confusing input
     */
    private void buildBusyList() {
        busyList.add(seatingTypeComboBox);
        busyList.add(addFlightToCartButton);
        busyList.add(viewFullFlightDetailsButton);
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
     * Updates the table to display the current list of legs stored in the model
     */
    private void buildFlightTable(){
        Legs displayList = controller.getDisplayList();
        if(displayList != null) {
            // Get the current model of the table and remove all previous entries
            DefaultTableModel table = (DefaultTableModel) flightDisplayTable.getModel();
            table.setRowCount(0);

            // Set the default formats for displaying dates, times, and prices
            DateTimeFormatter dateStyle = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter timeStyle = DateTimeFormatter.ofPattern("HH:mm a");
            DateTimeFormatter flightTimeStyle = DateTimeFormatter.ofPattern("HH:mm");
            NumberFormat priceStyle = NumberFormat.getCurrencyInstance(Locale.US);

            // Add a new row to the table for each leg on the list
            for (Leg leg : displayList) {
                // Generate correctly formatted strings from Leg attributes
                String coachPrice = priceStyle.format(leg.coachPrice);
                String firstClassPrice = priceStyle.format(leg.firstClassPrice);
                String departureAirport = leg.boardingAirport.code();
                String departureDate = dateStyle.format(leg.boardingTime);
                String departureTime = timeStyle.format(leg.boardingTime);
                String arrivalAirport = leg.disembarkingAirport.code();
                String arrivalDate = dateStyle.format(leg.disembarkingTime);
                String arrivalTime = timeStyle.format(leg.disembarkingTime);
                String flightNumber = Integer.toString(leg.flightNumber);
                String plane = leg.plane.model();
                String coachSeatsReserved = Integer.toString(leg.reservedCoachSeats);
                String firstClassSeatsReserved = Integer.toString(leg.reservedFirstClassSeats);
                String flightTime = flightTimeStyle.format(LocalTime.of((int) floor((double)leg.legDuration /60.0), leg.legDuration % 60));

                // Add these strings as a new row in the table (ORDER MATTERS)
                table.addRow(new Object[] {coachPrice, firstClassPrice, flightTime, departureAirport,departureDate,
                                            departureTime,arrivalAirport,arrivalDate,arrivalTime,flightNumber,plane,
                                            coachSeatsReserved, firstClassSeatsReserved});
            }
        }else
            System.out.println("No flights were found matching the provided criteria");
        System.out.println("Flights matching the provided criteria have been displayed");
    }

}