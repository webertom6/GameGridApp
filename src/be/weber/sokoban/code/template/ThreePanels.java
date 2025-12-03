package be.weber.sokoban.code.template;


import be.weber.sokoban.code.screen.ResizableImage;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class ThreePanels extends JPanel {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    public ThreePanels() {

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.25;
        constraints.weightx = 1.0;

        JLabel comment = new JLabel("Common L");
        comment.setFont(new Font("OCR A EXTENDED",Font.PLAIN,25));

        panel1.add(comment);
        add(panel1, constraints);

        panel2 = new ResizableImage("/be/weber/sokoban/resource/image/game_over-cut_ml.jpg");
        panel2.setBackground(Color.GREEN);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.5;
        constraints.weightx = 1.0;
        add(panel2, constraints);

        panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.25;
        constraints.weightx = 1.0;

        JLabel retry = new JLabel("Type 'R' to retry");
        retry.setFont(new Font("OCR A EXTENDED",Font.PLAIN,25));

        panel3.add(retry);

        add(panel3, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }
}
