package be.weber.sokoban.code.game;

import be.weber.sokoban.code.tile.*;
import be.weber.sokoban.code.tile.entity.PlayerTile;
import be.weber.sokoban.code.tile.item.*;
import be.weber.sokoban.code.tile.plot.Floor;
import be.weber.sokoban.code.tile.plot.Hole;
import be.weber.sokoban.code.tile.plot.Target;
import be.weber.sokoban.code.tile.plot.Wall;
import be.weber.sokoban.code.tool.ListTile;
import be.weber.sokoban.code.tool.Util;

import java.util.*;


/**
 * 2-dimensional Tile table that represent the map of the game
 */
public class Map implements Cloneable{
    private final int height;
    private final int width;
    private Tile[][] coordMap;
    public Set<Tile> set_distinct_tile = new HashSet<Tile>();

    private ArrayList<Target> target_list = new ArrayList<Target>();
    private ArrayList<Item> crate_list = new ArrayList<Item>();
    private ArrayList<EntityTile> entity_list = new ArrayList<>();

    Map(int height, int width){
        this.height = height;
        this.width = width;
        this.coordMap = new Tile[height][width];
    }

    public void setEntity(String name, EntityTile entity) {
        if(name.equals("PLAYER"))
            this.entity_list.add(0, entity);
        else if (name.equals("MOB")) {
            if (this.entity_list.isEmpty()) {
                this.entity_list.add(1, entity);
            }
            else {
                this.entity_list.add(entity);
            }
        }
    }

    public Tile[][] getCoordMap() {
        return coordMap;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Tile getTile(int x, int y){
        return this.coordMap[x][y];
    }

    public Set<Tile> getDistinctTile() {
        return set_distinct_tile;
    }

    public ArrayList<Target> getTargetList() {
        return this.target_list;
    }

    public ArrayList<Item> getCrateList() {
        return this.crate_list;
    }

    public EntityTile getEntity(boolean peek) {
        // pop first "mob" then pl
        if(!this.entity_list.isEmpty()) {
            EntityTile e = this.entity_list.get(entity_list.size()-1);
            if(!peek)
                this.entity_list.remove(entity_list.size()-1);
            return e;
        }

        return null;
    }

    /**
     * Build a level from a String :
     * # = wall       O = shift     R = risky floor
     * 0 = crate      H = hole      S = secret key
     * X = target     K = key       C = coin
     * _ = floor      D = door      Q = crate solved
     * @param level String that represents the level
     */
    public void buildLevel(String level) {
        int x = -1;
        int y = 0;
        for (int i = 0; i < level.length(); i++) {

            char element = level.charAt(i);
            x += 1;

            switch (element) {

                case '\n':
                    y += 1;
                    x = -1;
                    break;

                case '#':
                    Wall wall = new Wall(new Coord(x,y));
                    coordMap[x][y] = wall;
                    set_distinct_tile.add(wall);
                    break;

                case '0':
                    Floor floor_crate = new Floor(new Coord(x,y));
                    Crate crate = new Crate(new Coord(x,y), floor_crate);
                    coordMap[x][y] = crate;
                    crate_list.add(crate);
                    set_distinct_tile.add(crate);
                    break;

                case 'Q':
                    Target under_target = new Target(new Coord(x,y));
                    Crate crate_solved = new Crate(new Coord(x,y), under_target);
                    crate_solved.setState(true);
                    coordMap[x][y] = crate_solved;
                    target_list.add(under_target);
                    crate_list.add(crate_solved);
                    set_distinct_tile.add(under_target);
                    set_distinct_tile.add(crate_solved);
                    break;

                case 'O':
                    Floor floor_shift = new Floor(new Coord(x,y));
                    Shift shift = new Shift(new Coord(x,y), floor_shift);
                    coordMap[x][y] = shift;
                    set_distinct_tile.add(floor_shift);
                    set_distinct_tile.add(shift);
                    break;

                case 'X':
                    Target target = new Target(new Coord(x,y));
                    coordMap[x][y] = target;
                    target_list.add(target);
                    set_distinct_tile.add(target);
                    break;

                case '_':
                    Floor floor = new Floor(new Coord(x,y));
                    coordMap[x][y] = floor;
                    set_distinct_tile.add(floor);
                    break;

                case 'H':
                    Hole hole = new Hole(new Coord(x,y));
                    coordMap[x][y] = hole;
                    set_distinct_tile.add(hole);
                    break;

                case 'R':
                    RiskyFloor risky =  new RiskyFloor(new Coord(x,y));
                    coordMap[x][y] = risky;
                    set_distinct_tile.add(risky);
                    break;

                case 'K':
                    Key key = new Key(new Coord(x,y));
                    coordMap[x][y] = key;
                    set_distinct_tile.add(key);
                    break;

                case 'D':
                    Door door = new Door(new Coord(x,y));
                    coordMap[x][y] = door;
                    set_distinct_tile.add(door);
                    break;

                case 'S':
                    Key key_hidden = new Key(new Coord(x,y));
                    Shift shift_key = new Shift(new Coord(x,y), key_hidden);
                    coordMap[x][y] = shift_key;
                    set_distinct_tile.add(key_hidden);
                    set_distinct_tile.add(shift_key);
                    break;

                case 'C':
                    Coin coin = new Coin(new Coord(x,y));
                    coordMap[x][y] = coin;
                    set_distinct_tile.add(coin);
                    break;

                default:
                    break;
            }

        }
    }

    public void setTile(Tile tile){
        int x_tile = tile.getCoord().getX();
        int y_tile = tile.getCoord().getY();
        coordMap[x_tile][y_tile] = tile;
    }

    public void translateTile(Tile tile, int dx, int dy){
        tile.translate(dx, dy);
    }

    public Object clone(){
        Map clone = null;
        try {
            clone = (Map) super.clone();

            clone.coordMap = new Tile[coordMap.length][];
            clone.crate_list = new ArrayList<Item>();
            clone.target_list = new ArrayList<Target>();
            clone.entity_list = new ArrayList<EntityTile>();

            for(int i = 0; i < coordMap.length; i++){
                clone.coordMap[i] = new Tile[coordMap[i].length];
                for(int j = 0; j < coordMap[i].length; j++){
                    Tile tile_clone = (Tile) coordMap[i][j].clone();

                    if(tile_clone.getImagePath().contains("target")){
                        clone.coordMap[i][j] = tile_clone;
                        clone.target_list.add((Target) tile_clone);
                    }

                    else if(tile_clone.getImagePath().contains("crate")){
                        Crate crate = (Crate) tile_clone;

                        if(crate.getUnderTile().getImagePath().contains("target")){
                            clone.coordMap[i][j] = tile_clone;
                            clone.crate_list.add((Item) tile_clone);
                            clone.target_list.add((Target) crate.getUnderTile().clone());
                        }
                        else {
                            clone.coordMap[i][j] = tile_clone;
                            clone.crate_list.add((Item) tile_clone);
                        }
                    }

                    else if (tile_clone.getTILE_ID() == TileID.MOB || tile_clone.getTILE_ID() == TileID.PLAYER){
                        EntityTile e = (EntityTile) tile_clone;
                        clone.coordMap[i][j] = e;

                        if(e.getTILE_ID() == TileID.PLAYER)
                            this.entity_list.add(0, e);

                        else if (e.getTILE_ID() == TileID.MOB) {
                            if (this.entity_list.isEmpty()) {
                                this.entity_list.add(1, e);
                            }
                            else {
                                this.entity_list.add(e);
                            }
                        }
                    }
                    else{
                        clone.coordMap[i][j] = tile_clone;
                    }
                }
            }

        }
        catch(CloneNotSupportedException e){
            throw new RuntimeException("Unable to clone Map");
        }

        return clone;
    }

    @Override
    public String toString() {
        String str = "\n";
        for(int i = 0; i < coordMap.length; i++){
            for(int j  = 0; j < coordMap[i].length; j++) {
                str += Util.visualMap(coordMap[j][i]);
            }
            str += "\n";
        }
        return "Map{" +
                "height:"+height+
                ", width:"+width+
                ", coordMap:"+str+
                '}';
    }


}