package be.weber.sokoban.code.screen.home;

import javax.swing.*;
import java.awt.*;

public class UserField extends JTextField {

    private float font_size;
    private final float MIN_FONT_SIZE = 8.0f;

    /**
     * Create UserField object with default text field
     * @param font_size size's font of the text field
     */
    public UserField(float font_size){

        this.font_size = font_size;

        this.setFont(new Font("OCR A EXTENDED",Font.PLAIN, (int) font_size));
        //this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        //this.setBorder(new LineBorder(Color.blue, 2, true));
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        this.setForeground(Color.blue);
        this.setBackground(Color.BLACK);
        this.setCaretColor(Color.white);
        this.setEditable(true);
        this.setText("Username");

        this.setOpaque(true);
    }

    public void changeFontSize(int fontSize) {
        if(fontSize < 70) {
            this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(), fontSize));
        }
        else {
            this.setFont(new Font(this.getFont().getFontName(), this.getFont().getStyle(), 70));
        }
    }

    public void changeFontSize(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        float newFontSize;
        if (this.getPreferredSize().width != 0 && this.getPreferredSize().height != 0) {
            newFontSize = font_size * Math.min(width, height) / Math.max(this.getPreferredSize().width, this.getPreferredSize().height);
            newFontSize = Math.max(MIN_FONT_SIZE, newFontSize);
        }
        else {
            newFontSize = MIN_FONT_SIZE;
        }

        this.setFont(this.getFont().deriveFont(newFontSize));
        this.setSize(new Dimension(width, height));
    }
}
