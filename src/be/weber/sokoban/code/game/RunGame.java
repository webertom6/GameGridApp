package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.screen.GamePanel;
import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.screen.LayeredFrame;
import be.weber.sokoban.code.screen.halt.EndPanel;
import be.weber.sokoban.code.screen.halt.WinPanel;
import be.weber.sokoban.code.screen.home.LevelSelectPanel;
import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.TileID;
import be.weber.sokoban.code.tile.entity.MobTile;
import be.weber.sokoban.code.tile.entity.PlayerTile;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class RunGame {
    public static void main(String[] args) throws InterruptedException, IOException, LineUnavailableException, SokobanError {

        LayeredFrame frame = new LayeredFrame();

        SoundTrack soundtrack = new SoundTrack((float) 0.0);
        soundtrack.startSong("intro", soundtrack.LOOP);

        LevelSelectPanel selecter = new LevelSelectPanel(soundtrack);

        frame.addNPanel("selecter", selecter);
        frame.setVisiblePanel("selecter");

        frame.setVisible(true);

//        int difficulty = frame.getEvent() - KeyChoice.DIFFICULTY_CHOSEN;
//        System.out.println("difficulty: "+difficulty);

        int level = 0;
        do {
            level = frame.getEvent();
        } while (level < KeyChoice.LEVEL_CHOSEN || level > KeyChoice.DIFFICULTY_CHOSEN);
        level = level - KeyChoice.LEVEL_CHOSEN;
        System.out.println("level: " + level);


        selecter.loadLevel(7);

        int size;
        Levels levels = new Levels();
        Map map = null;

        PlayerTile p = null;
        MobTile m = null;
        Player player = null;
        Mob mob = null;

        switch (level) {
            case (1):
                size = 21;
                map = new Map(size, size);
                map.buildLevel(levels.getZoneTest());

                p = new PlayerTile(new Coord(10,10));
                m = new MobTile(new Coord(10,15));

                player = new Player("PLAYER", p);
                mob = new Mob("MOB", m);
                map.setEntity("PLAYER", p);
                map.setEntity("MOB", m);
                break;

            case (2):
                size = 9;
                map = new Map(size, size);
                map.buildLevel(levels.getLevel1());

                p = new PlayerTile(new Coord(2,2));

                player = new Player("PLAYER", p);
                map.setEntity("PLAYER", p);
                break;

            case (3):
                size = 20;
                map = new Map(size, size);
                map.buildLevel(levels.getLevel2());

                p = new PlayerTile(new Coord(2,16));
                m = new MobTile(new Coord(16,5));

                player = new Player("PLAYER", p);
                mob = new Mob("MOB", m);

                map.setEntity("PLAYER", p);
                map.setEntity("MOB", m);
                break;

            default:
                System.out.println("Level selected is not valid");
                System.exit(0);
                break;

        }
        GamePanel game = new GamePanel(map);
        EndPanel end = new EndPanel(selecter.getUserName());
        WinPanel win = new WinPanel(selecter.getUserName());

        frame.addNPanel("game", game);
        frame.addNPanel("end", end);
        frame.addNPanel("win", win);

        frame.setVisible(true);



        selecter.join();
        frame.setVisiblePanel("game");

        GameInstance game_player = new GameInstance(frame, player, map, soundtrack, true);

        System.out.println("thread player");

        Thread thread_player = new Thread(game_player, "player");
        thread_player.start();

        GameInstance game_mob = null;
        if (map.getEntity(true) != null && map.getEntity(true).getTILE_ID() != TileID.PLAYER) {
            game_mob = new GameInstance(frame, mob, map, soundtrack, false);
            System.out.println("thread mob start");

            Thread thread_mob = new Thread(game_mob, "mob");
            thread_mob.start();
        }

//        Game(frame, player, map, soundtrack);
    }

    /**
     * Execute the game depends on the level chosen
     * @throws SokobanError for SokobanGame
     * @throws InterruptedException for soundtrack
     * @throws LineUnavailableException for soundtrack
     * @throws IOException for soundtrack
     */
    /*
    static void Game(LayeredFrame frame, Player player, Map map, SoundTrack soundtrack) {

        try {

            GamePanel game = ((GamePanel) frame.getNPanel("game"));

            int direction = KeyChoice.NOTHING;

            while (true) {

                direction = frame.getEvent();

                if (direction == KeyChoice.LAUNCH){

                    double r_w = (double) game.getGrid().getWidth() / game.getGrid().getWPixels();
                    double r_h = (double) game.getGrid().getHeight() / game.getGrid().getHPixels();

                    System.out.println("ratio layer w: "+ r_w);
                    System.out.println("ratio layer h: "+ r_h);

//                    int fact = 100;
//
//                    r_w = Math.round(r_w * fact);
//                    r_w = r_w / fact;
//                    r_h = Math.round(r_h * fact);
//                    r_h = r_h / fact;

                    System.out.println("ratio layer w: "+ r_w);
                    System.out.println("ratio layer h: "+ r_h);

                    game.getGrid().setRatioW(r_w);
                    game.getGrid().setRatioH(r_h);

                    game.getGui().loadImageMap();

                    game.getGui().launch();

                    //System.out.println(game.getGrid().nbImages);

                    PlayerTile player_tile = new PlayerTile(map.getCoordEntity());

                    player.init(player_tile, game.getGui());

                    game.getGui().setTileEntity(player_tile, player_tile.getCoord().getX(), player_tile.getCoord().getY());

                    player.setRunning(true);

                    game.changeInfo(player);
                }

                if (direction == KeyChoice.RESET){

                    if(player.isWin() || player.isLose()){
                        frame.setVisiblePanel("game");
                    }

                    player.resetMap();
                    game.getPanelChrono().reset();
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

     */
}
