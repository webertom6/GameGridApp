package be.weber.sokoban.code.screen.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class ChoicesButton extends JPanel {

    JRadioButton easy;
    JRadioButton medium;
    JRadioButton difficult;

    ChoicesButton(Component parent){

        easy = new JRadioButton("EASY", true);
        medium = new JRadioButton("MEDIUM", false);
        difficult = new JRadioButton("DIFFICULT", false);

        // group is used to select only one button
        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(medium);
        group.add(difficult);

        easy.addItemListener((ItemListener) parent);
        medium.addItemListener((ItemListener) parent);
        difficult.addItemListener((ItemListener) parent);

        this.add(easy);
        this.add(medium);
        this.add(difficult);
        this.setVisible(true);
    }
}
