package be.weber.sokoban.code.screen.home;

import be.weber.sokoban.code.game.SoundTrack;
import be.weber.sokoban.code.screen.*;
import be.weber.sokoban.code.tool.Util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.LinkedBlockingQueue;

public class LevelSelectPanel extends LayerPanel implements ChangeListener, ItemListener {
    private SoundTrack soundtrack;
    private ParamPanel panel_param;
    private ResizableImage panel_sg;
    private LoadingBar bar;
    private Thread thread_bar;
    private UserField user;
    private TwoSelectPanel panel_select;
    private ResizableTextJPanel panel_choice;
    private ResizableTextJPanel panel_score;

    private LinkedBlockingQueue<Integer> ev_q;

    private final int level_choice = LevelChoice.NO_CHOICE;

    private boolean window_resized = false;


    /**
     * Create LevelSelectPanel with default parameter, score, bar, text field, combo box, text and image
     * @param soundtrack music volume set with slider
     */
    public LevelSelectPanel(SoundTrack soundtrack) {

        super("selecter", "HOME SCREEN", "/be/weber/sokoban/resource/image/SG.png");

        setBackground(Color.black);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        Insets normal_inset = new Insets(5, 5, 5, 5);
        constraints.insets = normal_inset;

        this.soundtrack = soundtrack;

        String str_player = "";

        str_player += "Thunder_Eye" + "\t" +
                      "00:11:80" + "\t" +
                      138 + " steps <br> <br> \n";
        str_player += "Kyurem2005" + "\t" +
                      "00:03:84" + "\t" +
                      34 + " steps <br> <br> \n";
        str_player += "TOMEMMA" + "\t" +
                      "00:05:52" + "\t" +
                      63 + " steps <br> <br> \n";

        String score = "<html> <br> " +
                "------------ SCOREBOARD ------------ <br> <br>" +
                str_player +
                "</html>";

        panel_score = new ResizableTextJPanel(score,1, true);
        panel_score.changeFont(Util.FONT_NAME_GAME, Font.BOLD);
        panel_score.changeForeground(Color.WHITE);
        panel_score.changeBackground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 6;
        constraints.weighty = 1.0;
        constraints.weightx = 0.33;
        add(panel_score, constraints);

        panel_param = new ParamPanel(this);
        panel_param.setBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.weighty = 0.20;
        constraints.weightx = 0.67;
        add(panel_param, constraints);

        panel_sg = new ResizableImage("/be/weber/sokoban/resource/image/sokoban_game_ml2.png");
        panel_sg.setBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.475;
        constraints.weightx = 0.67;
        add(panel_sg, constraints);

        bar = new LoadingBar();
        thread_bar = new Thread(bar);
        Insets bar_insets = new Insets(5, 30, 30, 30);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.weighty = 0.1;
        constraints.weightx = 0.67;
        constraints.insets = bar_insets;
        add(bar, constraints);

        user = new UserField(29);
        Insets user_insets = new Insets(5, 90, 30, 90);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.weighty = 0.075;
        constraints.weightx = 0.67;
        constraints.insets = user_insets;
        add(user, constraints);

        panel_select = new TwoSelectPanel(this);
        panel_select.setBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        constraints.weighty = 0.05;
        constraints.weightx = 0.67;
        constraints.insets = normal_inset;
        add(panel_select, constraints);

        panel_choice = new ResizableTextJPanel("[            ] selected", 75, true);
        panel_choice.changeFont(Util.FONT_NAME_GAME, Font.PLAIN);
        panel_choice.changeForeground(Color.RED);
        panel_choice.changeBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        constraints.weighty = 0.1;
        constraints.weightx = 0.67;
        add(panel_choice, constraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;
                repaint();
            }
        });
    }

    /**
     * Launch animation of the loading bar
     */
    public void loadLevel(int time){
        panel_select.getComboBox().setEnabled(false);
        user.setEditable(false);
        bar.setTime(time);
        thread_bar.start();
    }

    /**
     * Wait until the progress bar is filled
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        thread_bar.join();
    }

    /**
     * Give the username chosen by the player
     */
    public String getUserName(){
        return user.getText();
    }

    public void setEvQ(LinkedBlockingQueue<Integer> q){
        this.ev_q = q;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (window_resized) {

            panel_score.changeFontSize(getWidth()/64);

            panel_sg.revalidate();
            panel_sg.repaint();

            bar.changeFontSize(getWidth()/32);

            user.changeFontSize(getWidth()/24);

            panel_select.changeSize(getWidth()/32);

            panel_choice.changeFontSize(getWidth()/32);

            window_resized = false;
        }
        super.paintComponent(g);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getItemSelectable() instanceof JRadioButton r && e.getStateChange() == ItemEvent.SELECTED){

            int c = 1;

            if(r.getText() == "EASY"){
            }
            else if(r.getText() == "MEDIUM"){
                c = 2;
            }
            else if (r.getText() == "DIFFICULT") {
                c = 3;
            }

            try {
                ev_q.put(KeyChoice.DIFFICULTY_CHOSEN + c);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getItemSelectable() instanceof JComboBox<?> && e.getStateChange() == ItemEvent.SELECTED){

            panel_choice.changeText("["+panel_select.getComboBox().getSelectedItem()+"]");

            try {
                ev_q.put(KeyChoice.LEVEL_CHOSEN + panel_select.getComboBox().getSelectedIndex());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            panel_select.getComboBox().setEnabled(false);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        soundtrack.setVolume(panel_param.getParam().getSlider().getVal());
    }
}
