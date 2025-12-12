package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.GameGui;
import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.entity.MobTile;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tool.Algo;
import be.weber.sokoban.code.tool.BFSItem;
import be.weber.sokoban.code.tool.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Mob extends Entity{

    private MobTile mob_tile;
//    private Map map_reset;


    public LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

    Mob(String name, MobTile mob_tile){
        super(name, mob_tile);
        this.mob_tile = mob_tile;
        queue.add(KeyChoice.LAUNCH);
    }

    public void init(GameGui game)  throws SokobanError, IOException {
        super.init(game);

        this.map_reset = (Map) map.clone();
    }

    public void reset() throws SokobanError {
        mob_tile.setCoord(this.coord_start);
        this.coord_start = (Coord) this.coord_start.clone();
        // Get the fresh map from GameGui instead of using stale map_reset
        // This ensures the mob uses the same map instance as the player after reset
        this.map = this.game.getMap();
        // Update the backup map for future resets
        this.map_reset = (Map) this.map.clone();

        this.step = 0;
        this.current_tile = map.getTile(coord_start.getX(), coord_start.getY());
        this.previous_tile = null;

        this.key = 0;
        this.coin = 0;
        this.win = false;
        this.lose = false;
        this.running = true;


        this.game.setTileUsed(mob_tile, coord_start.getX(), coord_start.getY());

        this.progress();

        System.out.println("------ MOB MAP ----------");
        for (int i = 0; i < map.getCoordMap().length; i++) {
            for (int j = 0; j < map.getCoordMap()[0].length; j++) {
                System.out.print(Util.visualMap(map.getCoordMap()[i][j]));
            }
            System.out.println();
        }
    }

    public void findPath(){
        long startTime = System.nanoTime();
// Call the method you want to time
        BFSItem bfs = Algo.APath(this.map, mob_tile.getCoord().getY(), mob_tile.getCoord().getX());
        long endTime = System.nanoTime();

// Calculate the duration in milliseconds
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Execution time: " + duration + " milliseconds");

//        queue = bfs.getPath();
        transferQueueContents(bfs.getPath(), queue);
        for (Integer i: queue) {
            System.out.print(Util.KeyString(i)+",");
        }
    }

    @Override
    protected void evaluate(Tile next_tile) {

    }

    @Override
    protected void progress() {

    }

    /**
     * Transfers all elements from a source queue to a LinkedBlockingQueue.
     * @param source The source queue (must not be null)
     * @param target The target LinkedBlockingQueue (must not be null)
     * @param <T>    The type of elements in the queues
     */
    public static <T> void transferQueueContents(Queue<T> source, LinkedBlockingQueue<T> target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target queues must not be null.");
        }

        // Add all elements from source to target
        // This will throw IllegalStateException if capacity exceeded
        for (T item : source) {
            try {
                target.add(item); // add() throws if full
            } catch (IllegalStateException e) {
                System.err.println("Target queue is full. Could not add: " + item);
                break;
            }
        }
    }
}
