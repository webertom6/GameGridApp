package be.weber.sokoban.code.tile.item;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Item;
import be.weber.sokoban.code.tile.TileID;

/**
 * Floor which turn into a Hole after you walk on it once
 */
public class RiskyFloor extends Item {

    public RiskyFloor(Coord coord) {
        super(coord, "risky_floor.png", "hole.png", null, false, true, false);
        super.setTILE_ID(TileID.RISKY);
        super.setRisky(true);
    }

    @Override
    public void setState(boolean state){
        if(state){
            super.setState(true);
            super.setRisky(false);
            super.setMortal(true);
        }
        else{
            super.setState(false);
        }
    }

    @Override
    public String toString() {
        return "RiskyFloor{"+super.toString()+"}";
    }
}
