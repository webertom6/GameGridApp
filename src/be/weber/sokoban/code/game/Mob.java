package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.GameBoard;
import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.tile.entity.MobTile;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tool.Algo;
import be.weber.sokoban.code.tool.BFSItem;
import be.weber.sokoban.code.tool.Util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Mob extends Entity{

    private MobTile mob_tile;

    public Queue<Integer> queue = new LinkedList<>();

    Mob(String name, MobTile mob_tile){
        super(name, mob_tile);
        this.mob_tile = mob_tile;
        queue.add(KeyChoice.LAUNCH);
    }

    public void init(GameBoard game)  throws SokobanError, IOException {
        super.init(game);
    }

    public void findPath(){
        long startTime = System.nanoTime();
// Call the method you want to time
        BFSItem bfs = Algo.APath(map, mob_tile.getCoord().getY(), mob_tile.getCoord().getX());
        long endTime = System.nanoTime();

// Calculate the duration in milliseconds
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Execution time: " + duration + " milliseconds");

        queue = bfs.getPath();
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
}
