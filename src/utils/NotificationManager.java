package utils;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages popup windows for errors or a busy message.
 *
 * As a singleton, any other class can request to notify the user of an error or
 * remind the user that the program is busy.
 *
 * @author Chris Collins
 * @version 1.0 2020-05-05
 * @since 2020-05-04
 *
 */
public class NotificationManager {
    /** Handle to the error popup, so that only one is active at a time */
    private JFrame currentError;
    /** Handle to the busy popup, so that only one is active at a time */
    private JFrame busyWarning;
    /** Handle to the success popup, so that only one is active at a time */
    private JFrame successNotification;

    /** A running counter to ensure each timer has a unique key */
    private int timerIDCounter;

    /** Timers in a hashtable so that their ID can be used to find and cancel them */
    private final Hashtable<Integer, Timer> busyTimers = new Hashtable<>();

    /** Singleton variable */
    private static utils.NotificationManager single_instance = null;

    /** Static method to provide single point of access to the Singleton
     *
     * @return the active NotificationManager, or a new one if one is not created
     * @post a single instance of this class exists
     */
    public static utils.NotificationManager getInstance() {
        if (single_instance == null) {
            single_instance = new utils.NotificationManager();
        }
        return single_instance;
    }

    /** Create an error popup window in the center of the screen with a custom message
     *
     * @param m_errorMessage the custom error message String
     * @pre The main thread should be finished with all tasks
     * @post An error message popup GUI will be displayed in the center of the screen
     */
    public void popupError(String m_errorMessage){
        // Remove the old error if it is still open
        if (currentError != null) {
            currentError.dispose();
            currentError = null;
        }

        // Create the components
        currentError = new JFrame();
        JButton button = new JButton("OK");
        JLabel message = new JLabel(m_errorMessage);
        JLabel errorIcon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));

        // Add components to the window
        currentError.getContentPane().setLayout(new FlowLayout());
        currentError.getContentPane().add(errorIcon);
        currentError.getContentPane().add(message);
        currentError.getContentPane().add(button);

        // Display the popup
        currentError.pack();
        currentError.setLocationRelativeTo(null); // Puts the popup in the center of the screen
        currentError.setVisible(true);

        // Set the OK button to close the popup
        button.addActionListener(evt -> {
            currentError.dispose();
            currentError = null;
        });
    }

    /** Create an success popup window in the center of the screen with a custom message
     *
     * @param m_successMessage the custom success message String
     * @pre The main thread should be finished with all tasks
     * @post An success message popup GUI will be displayed in the center of the screen
     */
    public void popupSuccess(String m_successMessage){
        // Remove the old notification if it is still open
        if (successNotification != null) {
            successNotification.dispose();
            successNotification = null;
        }

        // Create the components
        successNotification = new JFrame();
        JButton button = new JButton("OK");
        JLabel message = new JLabel(m_successMessage);
        JLabel errorIcon = new JLabel(UIManager.getIcon("OptionPane.INFORMATION_MESSAGE"));

        // Add components to the window
        successNotification.getContentPane().setLayout(new FlowLayout());
        successNotification.getContentPane().add(errorIcon);
        successNotification.getContentPane().add(message);
        successNotification.getContentPane().add(button);

        // Display the popup
        successNotification.pack();
        successNotification.setLocationRelativeTo(null); // Puts the popup in the center of the screen
        successNotification.setVisible(true);

        // Set the OK button to close the popup
        button.addActionListener(evt -> {
            successNotification.dispose();
            successNotification = null;
        });
    }

    /** Create an busy notification popup window in the center of the screen
     * TODO: Multithread the rest of the software so the GUI window doesn't stay blank until the calculations are done
     * @pre The main thread should be finished with all tasks
     * @post An busy message popup GUI will be displayed in the center of the screen
     */
    public void popupBusy(){
        // Do not create a new one if one is already open
        if (busyWarning == null){
            // Create the components
            busyWarning = new JFrame();
            JButton button = new JButton("OK");
            JLabel message = new JLabel("Please wait: Program is working ... ");
            JLabel busyIcon = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));

            // Add components to the window
            busyWarning.getContentPane().setLayout(new FlowLayout());
            busyWarning.getContentPane().add(busyIcon);
            busyWarning.getContentPane().add(message);
            busyWarning.getContentPane().add(button);

            // Display the popup
            busyWarning.pack();
            busyWarning.setVisible(true); // Puts the popup in the center of the screen
            busyWarning.setLocationRelativeTo(null);

            // Also output a line to the console because the popup currently stays blank
            System.out.println("Please wait: Program is working ...");

            // Set the OK button to close the popup
            button.addActionListener(evt -> {
                busyWarning.dispose();
                busyWarning = null;
            });
        }
    }

    /** Adds a new timer to the hashtable that repeatedly alerts the user that the program is busy
     *
     * @return The ID number of the new timer as an Integer, which is needed to cancel it
     * @post A new timer is running a TimerTask to popup busy notifications
     */
    public int startBusyTimer(){
        // Create the task to repeat busy popups
        TimerTask alertUser = new TimerTask() {
            @Override
            public void run() {
                popupBusy();

            }
        };

        // Create the timer and schedule the task to occur repeatedly
        Timer t = new Timer();
        t.schedule(alertUser, Saps.BUSY_WAIT_TIME.toMillis(), Saps.BUSY_WAIT_TIME.toMillis());

        // Create new unique timerID and add it to the hashtable with the timer
        timerIDCounter += 1;
        busyTimers.put(timerIDCounter,t);
        return timerIDCounter;
    }

    /** Cancels a timer that has been started to notify the user of busy status
     *
     * @param timerID The ID number of the timer to cancel as an Integer
     * @post The matching timer has been canceled and will not continue to popup windows
     */
    public void stopBusyTimer(int timerID){
        // Make sure the key returns a valid timer
        Timer selectedTimer = busyTimers.get(timerID);
        if (selectedTimer!=null) {
            // Cancel the timer and remove it from the table
            selectedTimer.cancel();
            busyTimers.remove(timerID);
        }
    }
}
