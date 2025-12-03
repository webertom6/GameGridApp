package be.weber.sokoban.code.screen.halt;

import be.weber.sokoban.code.screen.LayerPanel;
import be.weber.sokoban.code.screen.ResizableImage;
import be.weber.sokoban.code.screen.ResizableTextJPanel;
import be.weber.sokoban.code.tool.Util;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class EndPanel extends LayerPanel {

    private ResizableTextJPanel comment;
    private ResizableImage go;
    private ResizableTextJPanel retry;

    private boolean window_resized = false;

    /**
     * Create EndPanel object with default image and text
     * @param username name added to panel
     */
    public EndPanel(String username) {

        super("end", "YOU LOSE", "/be/weber/sokoban/resource/image/skull.png");

        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        comment = new ResizableTextJPanel("Common L "+username, 85, true);
        comment.changeForeground(Color.WHITE);
        comment.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        comment.changeBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.1;
        constraints.weightx = 1.0;
        add(comment, constraints);

        go = new ResizableImage("/be/weber/sokoban/resource/image/game_over-cut_ml.png");
        go.setBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.8;
        constraints.weightx = 1.0;
        add(go, constraints);

        retry = new ResizableTextJPanel("Type 'R' to retry",85, true);
        retry.changeForeground(Color.WHITE);
        retry.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        retry.changeBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        constraints.weightx = 1.0;
        add(retry, constraints);

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

            comment.changeFontSize(getWidth()/12);

            retry.changeFontSize(getWidth()/12);

            window_resized = false;
        }
        super.paintComponent(g);
    }
}
