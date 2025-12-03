package be.weber.sokoban.code.screen.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.net.URL;

public class SoundCheck extends JCheckBox {

    private ImageIcon Xicon;
    private ImageIcon Vicon;
    private ImageIcon void_icon;

    /**
     * Create a SoundCheck box with default image and component's parent at which it is added
     * @param parent Component or inheritor object that include the checkbox
     */
    public SoundCheck(Component parent){

        URL url_xicon = getClass().getResource("/be/weber/sokoban/resource/image/x_icon_ml.png");
        URL url_vicon = getClass().getResource("/be/weber/sokoban/resource/image/v_icon_ml.png");
        URL url_void = getClass().getResource("/be/weber/sokoban/resource/image/void.png");

        assert url_xicon != null;
        Xicon = new ImageIcon(url_xicon);
        assert url_vicon != null;
        Vicon = new ImageIcon(url_vicon);
        assert url_void != null;
        void_icon = new ImageIcon(url_void);

        this.addItemListener((ItemListener) parent);
        this.setFocusable(false);
        this.setIcon(void_icon);
        this.setSelectedIcon(void_icon);
        this.setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        int imageHeight;
        int imageWidth;

        if(this.isSelected()) {
            imageHeight = Vicon.getIconHeight();
            imageWidth = Vicon.getIconWidth();
        }
        else{
            imageHeight = Xicon.getIconHeight();
            imageWidth = Xicon.getIconWidth();
        }

        if (width * imageHeight < height * imageWidth) {
            height = width * imageHeight / imageWidth;
        }
        else {
            width = height * imageWidth / imageHeight;
        }
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;

        if(this.isSelected()) {
            g.drawImage(Vicon.getImage(), x, y, width, height, null);
        }
        else{
            g.drawImage(Xicon.getImage(), x, y, width, height, null);
        }
    }
}
