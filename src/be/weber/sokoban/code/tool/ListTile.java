package be.weber.sokoban.code.tool;

import be.weber.sokoban.code.tile.*;
import be.weber.sokoban.code.tile.entity.MobTile;
import be.weber.sokoban.code.tile.entity.PlayerTile;
import be.weber.sokoban.code.tile.item.*;
import be.weber.sokoban.code.tile.plot.Floor;
import be.weber.sokoban.code.tile.plot.Hole;
import be.weber.sokoban.code.tile.plot.Target;
import be.weber.sokoban.code.tile.plot.Wall;

import java.util.HashMap;

public class ListTile {

    private final int ROW = 0;
    private final int COL = 0;
    private final Coord c = new Coord(ROW, COL);

    public static HashMap<String, Tile> dict = new HashMap<>();

    ListTile() {
        // entity
        dict.put("player", new PlayerTile(c));
        dict.put("mob", new MobTile(c));

        // item
        dict.put("wall", new Wall(c));
        dict.put("coin", new Coin(c));
        dict.put("door", new Door(c));
        dict.put("key", new Key(c));
        dict.put("risky_floor", new RiskyFloor(c));
        
        // plot
        dict.put("floor", new Floor(c));
        dict.put("crate", new Crate(c, null));
        dict.put("shift", new Shift(c, null));
        dict.put("target", new Target(c));
        dict.put("hole", new Hole(c));
    }
}
