package be.weber.sokoban.code.screen;

import be.weber.sokoban.code.tool.Util;

import javax.swing.*;
import java.awt.*;

public abstract class LayerPanel extends JPanel {

    private String name;
    private String title;
    private ImageIcon icon;

    /**
     * Create LayerPanel with name, tile, icon
     * @param name "id" of panel
     * @param title text to display on top of frame
     * @param icon_path used to icon to display on top of the frame
     */
    protected LayerPanel(String name, String title, String icon_path){
        this.name = name;
        this.title = title;
        this.icon = Util.loadIcon(icon_path);
    }

    @Override
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
    public ImageIcon getIcon() {
        return icon;
    }

}
