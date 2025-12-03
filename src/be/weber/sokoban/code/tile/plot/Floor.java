package be.weber.sokoban.code.tile.plot;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Plot;
import be.weber.sokoban.code.tile.TileID;

public class Floor extends Plot {

    public Floor(Coord coord){
        super(coord,"floor.png" ,true, false);
        super.setTILE_ID(TileID.FLOOR);
    }

    @Override
    public String toString() {
        return "Floor{"+super.toString()+"}";
    }
}
