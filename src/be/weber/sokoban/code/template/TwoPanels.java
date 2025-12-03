package be.weber.sokoban.code.template;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;


/**
 * ######################
 * #     1   #    2     #
 * #         #          #
 * ######################
 */
public class TwoPanels extends JPanel {

    private JPanel panel1;
    private JPanel panel2;

    public TwoPanels() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel1 = new JPanel();
        panel1.setBackground(Color.CYAN);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.5;
        add(panel1, constraints);

        panel2 = new JPanel();
        panel2.setBackground(Color.ORANGE);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.5;
        add(panel2, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }
}

