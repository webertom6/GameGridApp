package be.weber.sokoban.code.tool;

import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tile.TileID;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Util {

    public final static String FONT_NAME_GAME = "OCR A EXTENDED";

    Util() {}

    public static ImageIcon loadIcon(String icon_path) {
        URL url = Util.class.getResource(icon_path);
        assert url != null;

        return new ImageIcon(url);
    }

    public static BufferedImage loadImage(String file_path) throws IOException {
        BufferedImage buffered_image = null;
        try {
            InputStream is = Util.class.getResourceAsStream(file_path);
            assert is != null;
            buffered_image = ImageIO.read(is);

            return buffered_image;
        }
        catch (IOException | IllegalArgumentException e) {
            throw new IOException("Cannot read file [" + file_path + "]");
        }
    }

    public static String visualMap(Tile tile){
        switch (tile.getTILE_ID()){
            case TileID.WALL:
                return "#";
            case TileID.CRATE:
                return "0";
            case TileID.TARGET:
                return "X";
            case TileID.FLOOR:
                return "_";
            case TileID.SHIFT:
                return "O";
            case TileID.HOLE:
                return "H";
            case TileID.KEY:
                return "K";
            case TileID.DOOR:
                return "D";
            case TileID.RISKY:
                return "R";
            case TileID.COIN:
                return "C";
            case TileID.MOB:
                return "M";
            case TileID.PLAYER:
                return "$";
        }
        return "_";
    }

    public static String KeyString(int k){
        switch (k){
            case KeyChoice.NOTHING:
                return "NOTHING";
            case KeyChoice.UP:
                return "UP";
            case KeyChoice.DOWN:
                return "DOWN";
            case KeyChoice.LEFT:
                return "LEFT";
            case KeyChoice.RIGHT:
                return "RIGHT";
            default:
                return " ";
        }
    }

    public static String TFString(boolean b){
        if(b)
            return "T";
        return "F";
    }
}
