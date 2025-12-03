package be.weber.sokoban.code.screen.halt;

import be.weber.sokoban.code.screen.LayerPanel;
import be.weber.sokoban.code.screen.ResizableImage;
import be.weber.sokoban.code.screen.ResizableTextJPanel;
import be.weber.sokoban.code.tool.Util;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WinPanel extends LayerPanel {

    private final Color brown_back;
    private final Color gold;

    private ResizableTextJPanel comment;
    private ResizableImage w;
    private ResizableTextJPanel retry;

    private boolean window_resized = false;

    /**
     * Create WinPanel object with default image and text
     * @param username name added to panel
     */
    public WinPanel(String username) {

        super("win", "YOU WIN", "/be/weber/sokoban/resource/image/trophy.png");

        brown_back = new Color(33,22,0);
        gold = new Color(255,208,1);

        setBackground(brown_back);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        comment = new ResizableTextJPanel("Congrats "+username, 85, true);
        comment.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        comment.changeForeground(gold);
        comment.changeBackground(brown_back);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.1;
        constraints.weightx = 1.0;
        add(comment, constraints);

        w = new ResizableImage("/be/weber/sokoban/resource/image/you_win_ml.png");
        w.setBackground(brown_back);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.7;
        constraints.weightx = 1.0;
        add(w, constraints);

        retry = new ResizableTextJPanel("<html> You finish in " + "66:66:66" + " and "+ 999 +" steps <br> &nbsp; &nbsp; &nbsp; &nbsp;  Type 'R' to retry</html>",85, true);
        retry.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        retry.changeForeground(gold);
        retry.changeBackground(brown_back);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.2;
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

    public void setScore(String chrono, int steps){
        retry.changeText("<html> You finish in " + chrono + " and "+ steps +" steps <br> &nbsp; &nbsp; &nbsp; &nbsp;  Type 'R' to retry</html>");
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (window_resized) {

            comment.changeFontSize(getWidth()/12);

            w.revalidate();
            w.repaint();

            retry.changeFontSize(getWidth()/24);

            window_resized = false;
        }
        super.paintComponent(g);
    }
}
