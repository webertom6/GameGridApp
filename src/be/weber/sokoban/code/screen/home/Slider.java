package be.weber.sokoban.code.screen.home;

import javax.swing.*;

public class Slider extends JSlider {

    /**
     * Create Slider object with default interval [0;100] horizontally
     */
    Slider(){

        this.setMinimum(0);
        this.setMaximum(100);
        this.setValue(10);
        this.setOrientation(SwingConstants.HORIZONTAL);
    }

    /**
     * Returns the slider's current value between [0.0 ; 1.0]
     * @return current value of slider
     */
    public float getVal() {
        return ((float) super.getValue() / (float) 100.0);
    }

}
