package be.weber.sokoban.code.screen.home;

import be.weber.sokoban.code.screen.ResizableTextJPanel;
import be.weber.sokoban.code.tool.Util;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class TwoSelectPanel extends JPanel {

    private ResizableTextJPanel panel_txt;
    private JComboBox<String> panel_combo;

    /**
     * Create a TwoSelectPanel with default text, combo box and component's parent at which it is added
     * @param parent Component or inheritor object that include the two select panel
     */public TwoSelectPanel(Component parent) {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel_txt = new ResizableTextJPanel("Select level: ", 65, true);
        panel_txt.changeFont(Util.FONT_NAME_GAME, Font.PLAIN);
        panel_txt.changeForeground(Color.RED);
        panel_txt.changeBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.5;
        add(panel_txt, constraints);

        String[] levels = {"No Level","ZoneTest", "Level n°1", "Level n°2"};
        panel_combo = new JComboBox<String>(levels);
        panel_combo.setFont(new Font(Util.FONT_NAME_GAME, Font.BOLD, 20));
        panel_combo.setForeground(Color.RED);
        panel_combo.setBackground(Color.BLACK);
        panel_combo.addItemListener((ItemListener) parent);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.5;
        add(panel_combo, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }

    public JComboBox<String> getComboBox() {
        return panel_combo;
    }

    public void changeSize(int font_size){
        panel_txt.changeFontSize(font_size);
        panel_combo.setFont(new Font(Util.FONT_NAME_GAME, Font.BOLD, font_size));
    }
}
