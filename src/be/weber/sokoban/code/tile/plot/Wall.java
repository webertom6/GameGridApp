package be.weber.sokoban.code.tile.plot;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Plot;
import be.weber.sokoban.code.tile.TileID;

public class Wall extends Plot {

    public Wall(Coord coord){
        super(coord, "wall.png" ,false, false);
        super.setTILE_ID(TileID.WALL);
    }

    @Override
    public String toString() {
        return "Wall{"+super.toString()+"}";
    }
}
