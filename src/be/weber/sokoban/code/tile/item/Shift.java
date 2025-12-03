package be.weber.sokoban.code.tile.item;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Item;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tile.TileID;

/**
 * Act like a Crate but can't be used to solve the level
 */
public class Shift extends Item {

    public Shift(Coord coord, Tile under){
        super(coord, "shift.png", null, under,false,false, true);
        super.setTILE_ID(TileID.SHIFT);
    }

    @Override
    public String toString() {
        return "Shift{"+super.toString()+"}";
    }
}
