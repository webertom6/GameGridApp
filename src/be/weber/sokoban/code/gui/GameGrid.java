package be.weber.sokoban.code.gui;

import be.weber.sokoban.code.tool.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

public class GameGrid extends JPanel {
    private final int MAXIMAGES = 100000;
    private Image buffer;
    private Image workBuffer;
    private final int wPixels;
    private final int hPixels;
    private int cellSize;
    private Image[] imageArray;
    private int nbImages;

    private double ratio_h = 0.75;
    private double ratio_w = 0.5;

    public GameGrid(int w_pixels, int h_pixels, int cell_size) {
        setLayout(new GridBagLayout());

        this.wPixels = (w_pixels + 1) * cell_size;
        this.hPixels = (h_pixels + 1) * cell_size;
        this.cellSize = cell_size;
        this.setPreferredSize(new Dimension(this.wPixels, this.hPixels));

        this.setVisible(true);
    }

    public int getWPixels() {
        return wPixels;
    }
    public int getHPixels() {
        return hPixels;
    }

    public void setRatioW(double ratio_w) {
        this.ratio_w = ratio_w;
    }

    public void setRatioH(double ratio_h) {
        this.ratio_h = ratio_h;
    }

    /**
     * Initialize the grid AFTER it has been added to a VISIBLE frame
     */
    public void init() {
        this.buffer = this.createImage(this.wPixels, this.hPixels);
        this.workBuffer = this.createImage(this.wPixels, this.hPixels);
        this.imageArray = new Image[1000];
        this.nbImages = 0;
        this.initImage();
        this.updateBuffer();
    }

    public void paint(Graphics g) {
        g.drawImage(this.buffer, 0, 0, this);
    }

    /**
     * Put a rectangle of the size of the grid and instruction to start
     */
    public void initImage() {
        Graphics g = this.workBuffer.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0, 0, this.wPixels, this.hPixels);

        g2D.setPaint(Color.WHITE);
        g2D.setFont(new Font(Util.FONT_NAME_GAME, Font.BOLD, 45));
        g2D.drawString("Press", 60, 50);
        g2D.drawString("CTRL + 9", 60, 100);
        g2D.drawString("to start", 60, 150);
    }
    /**
     * Put a rectangle of the size of the grid and instruction to start
     */
    public void clearImage() {
        Graphics g = this.workBuffer.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLACK);
        g2D.fillRect(0, 0, this.wPixels, this.hPixels);
    }

    /**
     * Show the image loaded in the buffer
     */
    public void updateBuffer() {
        this.buffer.getGraphics().drawImage(this.workBuffer, 0, 0, this);
        this.paint(this.getGraphics());
    }

    /**
     * Download the image thanks to the file's path
     * -> use of InputStream bc need for a jar
     * @param file_path
     * @return nbImages++
     * @throws SokobanError
     */
    int loadImage(String file_path) throws SokobanError {
        if (this.nbImages >= MAXIMAGES) {
            throw new SokobanError("Too many loaded images");
        } else {
            BufferedImage buffered_image;
            try {
                InputStream is = getClass().getResourceAsStream(file_path);
                assert is != null;
                buffered_image = ImageIO.read(is);
            } catch (IOException | IllegalArgumentException e) {
                throw new SokobanError("Cannot read file [" + file_path + "]");
            }

            Image image = this.createImage(this.cellSize, this.cellSize);
            image.getGraphics().drawImage(buffered_image, 0, 0, (ImageObserver) null);
            Image resized_image = image.getScaledInstance((int) (this.cellSize * ratio_w), (int) (this.cellSize * ratio_h), Image.SCALE_SMOOTH);
            this.imageArray[this.nbImages] = resized_image;
            return this.nbImages++;
        }
    }

    /**
     * Place a cell with coordinates and the number of image give with .loadImage()
     * @param x
     * @param y
     * @param num_image
     * @throws SokobanError
     */
    void setCell(int x, int y, int num_image) throws SokobanError {
        if (num_image >= 0 && num_image < this.nbImages) {
            // this.imageArray[num_image] = image to display
            // x : width of image + distance with the edge rectangle on which is print
            // y : height of image + distance with the edge rectangle on which is print
            this.workBuffer.getGraphics().drawImage(this.imageArray[num_image],
                    (int) (x * this.cellSize * ratio_w  + (this.cellSize / 2) * ratio_w),
                    (int) (y * this.cellSize * ratio_h + (this.cellSize / 2) * ratio_h),
                                                    this);
        }
        else {
            throw new SokobanError("Invalid cell content");
        }
        updateBuffer();// to see each tile place one by one
    }
}
