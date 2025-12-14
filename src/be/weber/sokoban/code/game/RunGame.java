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

//        int difficulty = 0;
//        do {
//            difficulty = frame.getEvent();
//        } while (difficulty < KeyChoice.DIFFICULTY_CHOSEN);
//        difficulty = difficulty - KeyChoice.LEVEL_CHOSEN;

        int level = 0;
        do {
            level = frame.getEvent();
        } while (level < KeyChoice.LEVEL_CHOSEN || level > KeyChoice.DIFFICULTY_CHOSEN);
        level = level - KeyChoice.LEVEL_CHOSEN;

        // arbitrary time for animation
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

        // wait for the thread of the loading bar to end to show the game
        selecter.join();
        frame.setVisiblePanel("game");

        GameInstance game_player = new GameInstance(frame, player, map, soundtrack, true);

        Thread thread_player = new Thread(game_player, "player");
        thread_player.start();

        GameInstance game_mob = null;
        if (map.getEntity(true) != null && map.getEntity(true).getTILE_ID() != TileID.PLAYER) {
            game_mob = new GameInstance(frame, mob, map, soundtrack, false);

            Thread thread_mob = new Thread(game_mob, "mob");
            thread_mob.start();
        }

    }

}
