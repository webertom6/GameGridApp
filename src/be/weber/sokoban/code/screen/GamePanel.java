package be.weber.sokoban.code.screen;

import be.weber.sokoban.code.game.Map;
import be.weber.sokoban.code.game.Player;
import be.weber.sokoban.code.gui.GameBoard;
import be.weber.sokoban.code.gui.GameGrid;
import be.weber.sokoban.code.gui.SokobanChrono;
import be.weber.sokoban.code.gui.SokobanInfo;
import be.weber.sokoban.code.tool.Util;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends LayerPanel {

    private SokobanInfo panel_info;
    private SokobanChrono panel_chrono;
    private GameBoard game_gui;

    private boolean window_resized = false;

    /**
     * Create GamePanel object with default info's game, chrono, board, grid
     * @param map to start the GameGUI and GameGrid
     * @throws InterruptedException
     */
    public GamePanel(Map map) throws InterruptedException {

        super("game", "GAME", "/be/weber/sokoban/resource/image/SG.png");

        setBackground(Color.black);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        panel_info = new SokobanInfo(35);
        panel_info.changeForeground(Color.WHITE);
        panel_info.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        panel_info.changeBackground(Color.BLACK);
        panel_info.setBackground(Color.MAGENTA);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.67;
        constraints.weightx = 0.01;

        add(panel_info, constraints);


        panel_chrono = new SokobanChrono(35);
        panel_chrono.changeForeground(Color.WHITE);
        panel_chrono.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        panel_chrono.changeBackground(Color.BLACK);
        panel_chrono.setBackground(Color.GREEN);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = 0.33;
        constraints.weightx = 0.01;

        add(panel_chrono, constraints);


        game_gui = new GameBoard(map);
        game_gui.setBackground(Color.BLUE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.weightx = 0.99;
        constraints.gridheight = 2;

        add(game_gui, constraints);
        constraints.gridheight = 1;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;
                repaint();
            }
        });
    }

    public SokobanInfo getPanelInfo() {
        return panel_info;
    }

    public SokobanChrono getPanelChrono() {
        return panel_chrono;
    }

    public GameBoard getGui() {
        return game_gui;
    }

    public GameGrid getGrid(){
        return game_gui.getGrid();
    }

    /**
     * Change info on the panel
     * @param player entity which get info
     */
    public void changeInfo(Player player){
        panel_info.changeInfo(player.getPlayerTile().getCoord().getX(), player.getPlayerTile().getCoord().getY(),
                player.getCrateSolved(), player.getNumCrate(),
                player.getKey(), player.getCoin(), player.getStep());
    }


    @Override
    protected void paintComponent(Graphics g) {
        if (window_resized) {

            panel_info.changeFontSize(getWidth()/80);
            panel_chrono.changeFontSize(getWidth()/12);

            window_resized = false;
        }
        super.paintComponent(g);
    }
}

