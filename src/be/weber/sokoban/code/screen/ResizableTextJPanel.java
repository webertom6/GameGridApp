package be.weber.sokoban.code.screen;

import javax.swing.*;
import java.awt.*;

public class ResizableTextJPanel extends JPanel {
    private JLabel label;
    private float font_size;
    private final float MIN_FONT_SIZE = 8.0f;

    /**
     * Create ResizableTextJPanel with text, font size and if it has to be center
     * @param text string to display
     * @param font_size size of text
     * @param centering option to center the text
     */
    public ResizableTextJPanel(String text, float font_size, boolean centering) {
        label = new JLabel(text);
        label.setOpaque(true);

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        this.font_size = font_size;
        if(centering) {
            this.setLayout(new GridBagLayout());
        }
        this.add(label);
    }

    public void changeBackground(Color color){
        this.setBackground(color);
        label.setBackground(color);
    }

    public void changeText(String text){
        label.setText(text);
    }

    public void changeForeground(Color color){
        label.setForeground(color);
    }

    public void changeFont(String name_font, int style){
        label.setFont(new Font(name_font, style, label.getFont().getSize()));
    }

    public void changeFontSize(int font_size) {
        if(font_size < 70) {
            label.setFont(new Font(label.getFont().getFontName(), label.getFont().getStyle(), font_size));
        }
        else {
            label.setFont(new Font(label.getFont().getFontName(), label.getFont().getStyle(), 70));
        }
    }

    public void changeFontSize(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        //System.out.println(label.getFont().getFontName()+","+label.getFont().getStyle()+","+label.getFont().getSize2D());
        //System.out.println("w: "+width+"/////   h:"+height );
        //System.out.println("w_lab: "+label.getPreferredSize().width+"/////   h_lab:"+label.getPreferredSize().height);

        float newFontSize;
        if (label.getPreferredSize().width != 0 && label.getPreferredSize().height != 0) {
            newFontSize = font_size * Math.min(width, height) / Math.max(label.getPreferredSize().width, label.getPreferredSize().height);
            newFontSize = Math.max(MIN_FONT_SIZE, newFontSize);
        } else {
            newFontSize = MIN_FONT_SIZE;
        }

        label.setFont(label.getFont().deriveFont(newFontSize));
        label.setSize(new Dimension(width, height));
    }
}




