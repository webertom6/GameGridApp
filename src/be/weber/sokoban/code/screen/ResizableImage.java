package be.weber.sokoban.code.screen;

import be.weber.sokoban.code.tool.Util;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ResizableImage extends JPanel {

    private Image image;

    /**
     * Create ResizableImage object with image to load
     * @param file_path path to load the image
     */
    public ResizableImage(String file_path) {
        try {
            image = Util.loadImage(file_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    public Image getImage() {
        return image;
    }

    public void changeImage(Image image){
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        if (width * imageHeight < height * imageWidth) {
            height = width * imageHeight / imageWidth;
        }
        else {
            width = height * imageWidth / imageHeight;
        }
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        g.drawImage(image, x, y, width, height, null);
    }

}
