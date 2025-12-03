package be.weber.sokoban.code.tile.item;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Item;
import be.weber.sokoban.code.tile.TileID;

public class Door extends Item {

    public Door(Coord coord) {
        super(coord, "door_close.png", "door_open.png", null, false, false, false);
        super.setTILE_ID(TileID.DOOR);
        super.setLock(true);
    }

    @Override
    public void setState(boolean state){
        if(state){
            super.setState(true);
            super.setWalkable(true);
            super.setLock(false);
        }
        else{
            super.setState(false);
        }
    }

    @Override
    public String toString() {
        return "Door{"+super.toString()+"}";
    }
}
