package utils;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * This class
 *
 * @author Chris Collins
 * @version 1.0 2020-05-05
 * @since 2020-05-04
 *
 */
public class NotificationManager {
    private JFrame currentError;
    private JFrame busyWarning;
    private int timerID;
    private boolean busy = false;
    private Hashtable<Integer, Timer> busyTimers = new Hashtable<>();

    // Singleton variable
    private static utils.NotificationManager single_instance = null;

    /** static method to provide single point of access to the Singleton
     *
     * @return the active NotificationManager, or a new one if one is not created
     */
    public static utils.NotificationManager getInstance() {
        if (single_instance == null) {
            single_instance = new utils.NotificationManager();
        }
        return single_instance;
    }

    public void popupError(String m_errorMessage){
        if (currentError != null) {
            currentError.dispose();
            currentError = null;
        }

        currentError = new JFrame();
        JButton button = new JButton("OK");
        JLabel message = new JLabel(m_errorMessage);
        JLabel errorIcon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));

        currentError.getContentPane().setLayout(new FlowLayout());
        currentError.getContentPane().add(errorIcon);
        currentError.getContentPane().add(message);
        currentError.getContentPane().add(button);
        currentError.pack();
        currentError.setLocationRelativeTo(null);
        currentError.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentError.dispose();
                currentError = null;
            }
        });
    }

    public void popupBusy(){
        if (busyWarning == null){
            busyWarning = new JFrame();
            JButton button = new JButton("OK");
            JLabel message = new JLabel("Please wait: Program is working ... ");
            JLabel errorIcon = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));

            busyWarning.getContentPane().setLayout(new FlowLayout());
            busyWarning.getContentPane().add(errorIcon);
            busyWarning.getContentPane().add(message);
            busyWarning.getContentPane().add(button);
            busyWarning.pack();
            busyWarning.setLocationRelativeTo(null);
            button.addActionListener(evt -> {
                busyWarning.dispose();
                busyWarning = null;
            });
        }
    }

    public void busy(){
        if (busyWarning != null && busy) {
            busyWarning.setVisible(true);
            busy = false;
        }
    }

    public int startBusyTimer(){
        TimerTask alertUser = new TimerTask() {
            @Override
            public void run() {
                busy = true;

            }
        };

        Timer t = new Timer();
        t.schedule(alertUser, Saps.BUSY_WAIT_TIME.toMillis(), Saps.BUSY_WAIT_TIME.toMillis());
        timerID += 1;
        busyTimers.put(timerID,t);
        return timerID;
    }

    public void stopBusyTimer(int timerID){
        Timer selectedTimer = busyTimers.get(timerID);
        if (selectedTimer!=null) {
            selectedTimer.cancel();
            busyTimers.remove(timerID);
        }
    }
}
