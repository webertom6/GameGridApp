package be.weber.sokoban.code.gui;

import be.weber.sokoban.code.screen.ResizableTextJPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SokobanChrono extends ResizableTextJPanel {

    private int elapsedTime = 0;// millisec after we start chrono
    private int milli_seconds = 0;
    private int seconds = 0;
    private int minutes = 0;
    
    // %02d -> if seconds = 3, print 03
    private String chrono;
    private String milli_seconds_string;
    private String seconds_string;
    private String minutes_string;

    // delay is how frequently you want to the timer to do sth/ to be updated
    private Timer timer = new Timer(1, new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            elapsedTime = elapsedTime + 1;
            if(elapsedTime == 100){
                elapsedTime = 0;
            }
            milli_seconds = elapsedTime;
            if(elapsedTime == 0) {
                seconds += 1;
                if (seconds == 60) {
                    seconds = 0;
                    minutes += 1;
                }
            }

            milli_seconds_string = String.format("%02d", milli_seconds);
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            chrono = minutes_string + ":" + seconds_string + ":" + milli_seconds_string;
            setChrono(chrono);

        }

    });

    public SokobanChrono(int font_size){
        super("00:00:00", font_size, true);

        milli_seconds_string = String.format("%02d", milli_seconds);
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        chrono = minutes_string + ":" + seconds_string + ":" + milli_seconds_string;

        //this.setBorder(BorderFactory.createBevelBorder(1));
        this.setVisible(true);
        //this.setOpaque(true);
    }

    private void setChrono(String text) {
        super.changeText(text);
    }

    /**
     * Start the chrono
     */
    public void start() {
        timer.start();
    }

    /**
     * Stop the chrono
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Reset chrono and stop it
     */
    public void reset() {
        timer.stop();
        elapsedTime = 0;
        milli_seconds = 0;
        seconds = 0;
        minutes = 0;
        milli_seconds_string = String.format("%02d", milli_seconds);
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        chrono = minutes_string + ":" + seconds_string + ":" + milli_seconds_string;
        super.changeText(chrono);
    }

    public String getChrono(){
        return chrono;
    }
}
