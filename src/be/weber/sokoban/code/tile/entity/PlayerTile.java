package be.weber.sokoban.code.tile.entity;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.EntityTile;
import be.weber.sokoban.code.tile.TileID;

public class PlayerTile extends EntityTile {

    public PlayerTile(Coord coord){
        super(coord, new String[]{"player_down.png", "player_up.png", "player_left.png", "player_right.png"});
        super.setTILE_ID(TileID.PLAYER);
    }

    @Override
    public String toString() {
        return "Player{"+super.toString()+"}";
    }
}
