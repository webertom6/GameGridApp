package be.weber.sokoban.code.screen.home;

import be.weber.sokoban.code.game.SoundTrack;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.VetoableChangeListener;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ParamPanel extends JPanel implements ItemListener {

    private TwoParam param;
    private SoundCheck check;

    private boolean window_resized = false;

    /**
     * Create ParamPanel object with default check box and panel containing 2 panels
     */
    public ParamPanel(Component parent) {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        param = new TwoParam(parent);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.9;
        add(param, constraints);

        check = new SoundCheck(this);
        check.setBackground(Color.MAGENTA);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.1;
        constraints.gridheight = 2;
        constraints.insets = new Insets(5,25,5,5);
        add(check, constraints);
        constraints.gridheight = 1;

        param.setVisibleParam(false);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;
                repaint();
            }
        });
    }

    public TwoParam getParam() {
        return param;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (window_resized) {

            check.revalidate();
            check.repaint();

            window_resized = false;
        }
        super.paintComponent(g);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(!check.isSelected()){
            param.setVisibleParam(false);
        }
        else{
            param.setVisibleParam(true);
        }
    }
}
