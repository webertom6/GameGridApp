package be.weber.sokoban.code.template;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

public class OneTwo extends JPanel {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    public OneTwo() {
        setBackground(Color.black);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.67;
        constraints.weightx = 0.5;
        add(panel1, constraints);

        panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = 0.33;
        constraints.weightx = 0.5;
        add(panel2, constraints);

        panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.5;
        constraints.gridheight = 2;
        add(panel3, constraints);
        constraints.gridheight = 1;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }
}
