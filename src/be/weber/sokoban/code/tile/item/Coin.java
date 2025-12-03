package be.weber.sokoban.code.tile.item;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.Item;
import be.weber.sokoban.code.tile.TileID;

public class Coin extends Item {

    public Coin(Coord coord){
        super(coord, "coin.png", "floor.png", null, false, true, false);
        super.setTILE_ID(TileID.COIN);
        super.setCollectable(true);
    }

    @Override
    public void setState(boolean state){
        if(state){
            super.setState(true);
            super.setCollectable(false);
        }
        else{
            super.setState(false);
        }
    }

    @Override
    public String toString() {
        return "Coin{"+super.toString()+"}";
    }
}
