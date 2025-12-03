package be.weber.sokoban.code.gui;

import be.weber.sokoban.code.screen.ResizableTextJPanel;
import be.weber.sokoban.code.tool.Util;

import java.awt.*;

public class SokobanInfo extends ResizableTextJPanel {

    public SokobanInfo(int font_size){
        super("init_info", font_size, true);
        String init_info = "<html>--------------------INFO PLAYER--------------------- <br> <br>" +
                "You're in: (" + 0 + "," + 0 + ") " + " <br> <br> " +
                "You have solved " + 0 + " crate(s) out of " + 0 + "<br> <br>" +
                "You have " + 0 + " key(s) <br> <br>" +
                "You have " + 0 + " coin(s) <br> <br>" +
                "Nb of steps: " + 0 + "<br> <br>" +
                "---------------------------------------------------- </html>";
        super.changeText(init_info);
        super.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        super.changeForeground(Color.WHITE);

        this.setVisible(true);
        this.setOpaque(true);
    }

    public void changeInfo(int x, int y, int crate_solved, int num_crate, int key, int coin, int step){
        super.changeText(
                "<html>--------------------INFO PLAYER--------------------- <br> <br>" +
                "You're in: ("+x+","+y+")" + "<br> <br> " +
                "You have solved " + crate_solved + " crate(s) out of " + num_crate +"<br> <br>" +
                "You have " + key + " key(s) <br> <br>" +
                "You have " + coin + " coin(s) <br> <br>" +
                "Nb of steps: " + step +"<br> <br>" +
                "---------------------------------------------------- </html>");
    }

}
