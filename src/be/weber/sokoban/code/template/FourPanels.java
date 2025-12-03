package be.weber.sokoban.code.template;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class FourPanels extends JPanel {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;

    private boolean window_resized = false;

    public FourPanels(JPanel panel1, JPanel panel2, JPanel panel3, JPanel panel4, JPanel panel5, JPanel panel6) {

        setBackground(Color.black);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel6 = panel6;
        panel6.setBackground(Color.YELLOW);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        constraints.weighty = 1.0;
        constraints.weightx = 0.33;
        add(panel6, constraints);

        panel1 = panel1;
        panel1.setBackground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.weighty = 0.2;
        constraints.weightx = 0.67;
        add(panel1, constraints);

        panel2 = panel2;
        panel2.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.45;
        constraints.weightx = 0.67;
        add(panel2, constraints);

        panel3 = panel3;
        panel3.setBackground(Color.gray);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.weighty = 0.1;
        constraints.weightx = 0.67;
        add(panel3, constraints);

        panel4 = panel4;
        panel4.setBackground(Color.BLUE);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.weighty = 0.125;
        constraints.weightx = 0.67;
        add(panel4, constraints);

        panel5 = panel5;
        panel5.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 0.125;
        constraints.weightx = 0.67;
        add(panel5, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;
                repaint();
            }
        });
    }

    public FourPanels() {

        setBackground(Color.black);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel6 = new JPanel();
        panel6.setBackground(Color.YELLOW);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        constraints.weighty = 1.0;
        constraints.weightx = 0.33;
        add(panel6, constraints);

        panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.weighty = 0.2;
        constraints.weightx = 0.67;
        add(panel1, constraints);

        panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.45;
        constraints.weightx = 0.67;
        add(panel2, constraints);

        panel3 = new JPanel();
        panel3.setBackground(Color.gray);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.weighty = 0.1;
        constraints.weightx = 0.67;
        add(panel3, constraints);

        panel4 = new JPanel();
        panel4.setBackground(Color.BLUE);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.weighty = 0.125;
        constraints.weightx = 0.67;
        add(panel4, constraints);

        panel5 = new JPanel();
        panel5.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 0.125;
        constraints.weightx = 0.67;
        add(panel5, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;
                repaint();
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (window_resized) {

            window_resized = false;
        }
        super.paintComponent(g);
    }
}

