package be.weber.sokoban.code.tile.plot;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Plot;
import be.weber.sokoban.code.tile.TileID;

/**
 * Where the Player have to place the Crate
 */
public class Target extends Plot {

    public Target(Coord coord){
        super(coord,"target.png" ,true, false);
        super.setTILE_ID(TileID.TARGET);
    }

    @Override
    public String toString() {
        return "Target{"+super.toString()+"}";
    }
}
