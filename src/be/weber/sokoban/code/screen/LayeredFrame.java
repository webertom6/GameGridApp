package be.weber.sokoban.code.screen;

import be.weber.sokoban.code.screen.home.LevelSelectPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class LayeredFrame extends JFrame implements WindowListener {

    private LinkedBlockingQueue<Integer> event_queue = new LinkedBlockingQueue<Integer>();

    private final JPanel main_panel;
    private HashMap<String, LayerPanel> panel_catalog = new HashMap<String, LayerPanel>();

    private final CardLayout cardLayout;

    private final Action up_action;
    private final Action down_action;
    private final Action left_action;
    private final Action right_action;
    private final Action reset_action;

    private final Action panel1_action;
    private final Action panel2_action;
    private final Action panel3_action;
    private final Action panel4_action;

    private final Action launch_action;


    /**
     * Create LayeredFrame object with default panel, card layout, event_queue, action key
     */
    public LayeredFrame() {

        /** -------------   PANEL FRAME  ------------- */
        cardLayout = new CardLayout();
        main_panel = new JPanel(cardLayout);

        event_queue = new LinkedBlockingQueue<>();
        /** ----------------------------------------- */

        /** -------------   KEY FRAME  ------------- */
        up_action = new UpAction();
        down_action = new DownAction();
        left_action = new LeftAction();
        right_action = new RightAction();
        reset_action = new ResetAction();

        panel1_action = new Panel1Action();
        panel2_action = new Panel2Action();
        panel3_action = new Panel3Action();
        panel4_action = new Panel4Action();

        launch_action = new LaunchAction();

        main_panel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up_action");
        main_panel.getActionMap().put("up_action", up_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down_action");
        main_panel.getActionMap().put("down_action", down_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left_action");
        main_panel.getActionMap().put("left_action", left_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right_action");
        main_panel.getActionMap().put("right_action", right_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke("R"), "reset_action");
        main_panel.getActionMap().put("reset_action", reset_action);


        main_panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, InputEvent.CTRL_MASK), "panel1_action");
        main_panel.getActionMap().put("panel1_action", panel1_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, InputEvent.CTRL_MASK), "panel2_action");
        main_panel.getActionMap().put("panel2_action", panel2_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, InputEvent.CTRL_MASK), "launch_action");
        main_panel.getActionMap().put("launch_action", launch_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, InputEvent.CTRL_MASK), "panel3_action");
        main_panel.getActionMap().put("panel3_action", panel3_action);
        main_panel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, InputEvent.CTRL_MASK), "panel4_action");
        main_panel.getActionMap().put("panel4_action", panel4_action);
        /** ---------------------------------------- */

        /** -------------   PARAM FRAME  ------------- */
        setTitle("Layered Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        setLocationRelativeTo(null);
        add(main_panel);
        setVisible(false);
        /** ----------------------------------------- */
    }

    /**
     * Add a sub-panel to frame with name to identify it
     * @param name "id" of panel
     * @param p panel to add
     */
    public void addNPanel(String name, LayerPanel p){
        panel_catalog.put(name, p);
        main_panel.add(p, name);

        if(p.getName().equals("selecter")) {
            ((LevelSelectPanel) p).setEvQ(event_queue);
        }
        if(p.getName().equals("game")) {
            ((GamePanel) p).getGrid().init();
        }
    }

    /**
     * Give the corresponding panel for the specified name
     * @param name_panel "id" of panel
     * @return the panel specified by the name, or null if there is no mapping to the name
     */
    public LayerPanel getNPanel(String name_panel) {
        return panel_catalog.get(name_panel);
    }

    /**
     * Show one panel and hide others
     * @param name_panel "id" of panel
     */
    public void setVisiblePanel(String name_panel) {
        cardLayout.show(main_panel, name_panel);
        setTitle(panel_catalog.get(name_panel).getTitle());
        setIconImage(panel_catalog.get(name_panel).getIcon().getImage());
    }


     /** Give the action chose by the user
     * -> direction arrow or ZQSD key
     * -> R for RESET
     * @return a KeyChoice number corresponding to the key pressed by user
     */
    public int getEvent() {
        Integer action;
        try {
            action = (Integer)this.event_queue.take();
        } catch (InterruptedException e) {
            action = 0;
        }

        return action;
    }


    /** -------------   KEY_BINDING FRAME  ------------- */
    public class Panel1Action extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisiblePanel("select");
        }
    }
    public class Panel2Action extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisiblePanel("game");
        }
    }
    public class LaunchAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.LAUNCH));
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public class Panel3Action extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisiblePanel("end");
        }
    }
    public class Panel4Action extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisiblePanel("win");
        }
    }

    public class UpAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.UP));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class DownAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.DOWN));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class LeftAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.LEFT));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class RightAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.RIGHT));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class ResetAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                event_queue.put(Integer.valueOf(KeyChoice.RESET));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    /** ------------------------------------------------ */

    /** -------------   WINDOW_LISTENER FRAME  ------------- */
    @Override
    /**
     * Trigger when window is made visible for the first time
     */
    public void windowOpened(WindowEvent e) {

    }

    @Override
    /**
     * Trigger when the user attempts to close the window from the system menu of the window.
     * (User close the window by any way, so while running not in the code)
     */
    public void windowClosing(WindowEvent e) {
        this.dispose();
        TrayIcon[] array_icons = SystemTray.getSystemTray().getTrayIcons();

        for(int i = 0; i < array_icons.length; i++) {
            TrayIcon icon_i = array_icons[i];
            SystemTray.getSystemTray().remove(icon_i);
        }

        try {
            this.event_queue.put(1);
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    /**
     * Trigger when a window has been closed as the result of calling dispose on the window.
     * (when you call .dispose() on the window)
     */
    public void windowClosed(WindowEvent e) {

    }

    @Override
    /**
     * Trigger when a window is changed from a normal to a minimized state.
     * Trigger also windowActivated()
     */
    public void windowIconified(WindowEvent e) {

    }

    @Override
    /**
     * Trigger when a window is changed from a minimized to a normal state.
     * Trigger also windowDeactivated()
     */
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    /**
     * Trigger when the window appears and when you click after on it
     * So the window that the user is using currently
     */
    public void windowActivated(WindowEvent e) {

    }

    @Override
    /**
     * Trigger when the user click somewhere else that on the window (not active)
     * Also when iconified
     */
    public void windowDeactivated(WindowEvent e) {

    }
    /** ---------------------------------------------------- */
}
