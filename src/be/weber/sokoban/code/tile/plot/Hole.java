package be.weber.sokoban.code.tile.plot;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Plot;
import be.weber.sokoban.code.tile.TileID;

/**
 * When you walk on it, you die
 * You can place a Crate/Shift on it to block it
 */
public class Hole extends Plot {

    public Hole(Coord coord){
        super(coord,"hole.png" ,true, false);
        super.setTILE_ID(TileID.HOLE);
        super.setMortal(true);
    }

    @Override
    public String toString() {
        return "Hole{"+super.toString()+"}";
    }
}
