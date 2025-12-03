package be.weber.sokoban.code.tile.item;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Item;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tile.TileID;

public class Crate extends Item {

    public Crate(Coord coord, Tile under){
        super(coord, "crate.png", "crate_solved.png", under,false,false, true);
        super.setTILE_ID(TileID.CRATE);
    }

    @Override
    public String toString() {
        return "Crate{"+super.toString()+"}";
    }

}
