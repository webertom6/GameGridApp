package be.weber.sokoban.code.screen.home;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TwoParam extends JPanel implements ChangeListener{

    private CardLayout card_layout;

    private JPanel normal;
    private JPanel param;
    private Slider slider;
    private JPanel button;

    /**
     * Create a TwoParam object with 2 default panels (slider and ...) and component's parent at which it is added
     * @param parent Component or inheritor object that include the two param
     */
    TwoParam(Component parent){
        card_layout = new CardLayout();
        setLayout(card_layout);

        normal = new JPanel();
        normal.setBackground(Color.cyan);

        add(normal, "normal");

        param = new JPanel(new GridBagLayout());
        param.setBackground(Color.ORANGE);

        add(param, "param");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        slider = new Slider();
        slider.setBackground(Color.RED);
        slider.addChangeListener((ChangeListener) parent);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.5;
        constraints.weightx = 1.0;
        param.add(slider, constraints);

        button = new ChoicesButton(parent);
        button.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.5;
        constraints.weightx = 1.0;
        param.add(button, constraints);

    }

    public Slider getSlider() {
        return slider;
    }

    public void setVisibleParam(boolean flag){
        if(flag){
            card_layout.show(this, "param");
        }
        else{
            card_layout.show(this, "normal");
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}












