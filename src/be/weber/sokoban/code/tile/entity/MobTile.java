package be.weber.sokoban.code.tile.entity;

import be.weber.sokoban.code.tile.Coord;
import be.weber.sokoban.code.tile.EntityTile;
import be.weber.sokoban.code.tile.TileID;

public class MobTile extends EntityTile {

    public MobTile(Coord coord){
        super(coord, new String[]{"mob_down.png", "mob_up.png", "mob_left.png", "mob_right.png"});
        super.setTILE_ID(TileID.MOB);
        super.setMortal(true);
    }

    @Override
    public String toString() {
        return "Mob{"+super.toString()+"}";
    }

}
