package be.weber.sokoban.code.screen.home;

import be.weber.sokoban.code.tool.Util;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LoadingBar extends JProgressBar implements Runnable{

    private int time;
    private final static Color GREEN_BAR = new Color(24,238,1);
    private final Random rand = new Random();

    /**
     * Create loading bar with default interval [0, 100]
     */
    public LoadingBar(){
        this.setMinimum(0);
        this.setMaximum(100);
        this.setValue(0);
        this.setStringPainted(true);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        this.setForeground(GREEN_BAR);
        this.setBackground(Color.WHITE);
        this.setFont(new Font(Util.FONT_NAME_GAME,Font.PLAIN,25));
        this.setOpaque(true);

        this.time = 55;
    }

    public void changeFontSize(int fontSize) {
        if(fontSize < 70) {
            this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(), fontSize));
        }
        else {
            this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(), 70));
        }
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Fill the progress bar from 0 to 100 in 5 sec
     */
    public void fill() {
        int counter = 0;

        while(counter <= 100) {

            this.setValue(counter);

            try {
                Thread.sleep(this.time * 5L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(counter <= 89) {
                counter += rand.nextInt(1, 10);
            }
            else if(counter == 100){
                counter += 1;
            }
            else{
                counter += (100 - counter);
            }
        }
        this.setString("COMPLETED");
    }

    @Override
    public void run() {
        fill();
    }
}
