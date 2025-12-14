package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.screen.GamePanel;
import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.screen.LayeredFrame;
import be.weber.sokoban.code.screen.halt.WinPanel;
import be.weber.sokoban.code.tile.EntityTile;
import be.weber.sokoban.code.tile.entity.MobTile;
import be.weber.sokoban.code.tile.entity.PlayerTile;
import be.weber.sokoban.code.tool.Algo;
import be.weber.sokoban.code.tool.BFSItem;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class GameInstance implements Runnable{

    private LayeredFrame frame;

    private Entity entity;

    private Map map;

    private SoundTrack soundtrack;

    private boolean player_enable;

    /**
     * Execute the game depends on the level chosen
     * @throws SokobanError for SokobanGame
     * @throws InterruptedException for soundtrack
     * @throws LineUnavailableException for soundtrack
     * @throws IOException for soundtrack
     */
    GameInstance(LayeredFrame frame, Entity entity, Map map, SoundTrack soundtrack, boolean player_enable) {
        this.frame = frame;
        this.entity = entity;
        this.map = map;
        this.soundtrack = soundtrack;
        this.player_enable = player_enable;
    }

    private void PlayerInstance(){
        try {

            Player player = (Player) entity;

            GamePanel game = ((GamePanel) frame.getNPanel("game"));

            int direction = KeyChoice.NOTHING;

            while (true) {

                direction = frame.getEvent();

                if (direction == KeyChoice.LAUNCH && !player.isRunning() && !player.isLose() && !player.isWin()){

                    double r_w = (double) game.getGrid().getWidth() / game.getGrid().getWPixels();
                    double r_h = (double) game.getGrid().getHeight() / game.getGrid().getHPixels();

                    game.getGrid().setRatioW(r_w);
                    game.getGrid().setRatioH(r_h);

                    // load every distinct image's tile in the map and store them
                    game.getGui().loadImageMap();

                    // set tile on the game board gui initially (first_launched = true)
                    game.getGui().launch();

                    player.init(game.getGui());

                    game.getGui().setTileEntity((EntityTile) player.getEntityTile(),
                                                player.getEntityTile().getCoord().getX(),
                                                player.getEntityTile().getCoord().getY());

                    player.setRunning(true);

                    game.changeInfo(player);
                }

                if (direction == KeyChoice.RESET){

                    if(player.isWin() || player.isLose()){
                        frame.setVisiblePanel("game");
                    }

                    player.resetMap();
                    game.getPanelChrono().reset();
                    Entity.setReset(false);
                }

                if (player.isRunning()) {
                    player.move(direction);

                    game.changeInfo(player);

                    if(player.getStep() == 1){
                        game.getPanelChrono().start();
                    }
                }

                if (player.isLose() && player.isRunning()) {
                    player.setRunning(false);

                    game.getPanelChrono().stop();

                    frame.setVisiblePanel("end");
                }

                if (player.isWin() && player.isRunning()) {
                    player.setRunning(false);

                    game.getPanelChrono().stop();

                    ((WinPanel) frame.getNPanel("win")).setScore(game.getPanelChrono().getChrono(), player.getStep());

                    frame.setVisiblePanel("win");
                }
            }
        }
        catch (SokobanError e1) {
            System.err.println("SokobanError : " + e1.getMessage());
            System.exit(1);
        }
        catch (IOException e3){
            System.err.println("IOException : "+ e3.getMessage());
            System.exit(1);
        }
    }

    private void MobInstance(){
        try {

            Mob mob = (Mob) entity;

            GamePanel game = ((GamePanel) frame.getNPanel("game"));

            int direction = KeyChoice.NOTHING;

            boolean a = false;

            while (true) {

                if (game.getGui().isFirstLaunched()){
                    if (!mob.queue.isEmpty())
                        direction = mob.queue.poll();
                    else {
                        direction = KeyChoice.NOTHING;
                        a = true;
                    }
                    if (a){
                        mob.findPath();
                        a = false;
                    }

                    Thread.sleep(750);
                }

                if (direction == KeyChoice.LAUNCH){

                    mob.init(game.getGui());

                    game.getGui().setTileEntity((EntityTile) mob.getEntityTile(),
                                                mob.getEntityTile().getCoord().getX(),
                                                mob.getEntityTile().getCoord().getY());

                    mob.setRunning(true);
                }

                if (Entity.isReset()) {
                    mob.reset();
                }

                if (mob.isRunning()) {
                    mob.move(direction);
                }

            }
        }
        catch (SokobanError e1) {
            System.err.println("SokobanError : " + e1.getMessage());
            System.exit(1);
        }
        catch (IOException e3){
            System.err.println("IOException : "+ e3.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        if (player_enable){
            PlayerInstance();
        }
        else {
            MobInstance();
        }
    }
}
