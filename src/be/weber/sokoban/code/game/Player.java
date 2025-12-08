package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.GameBoard;
import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.tile.*;
import be.weber.sokoban.code.tile.entity.PlayerTile;
import be.weber.sokoban.code.tile.plot.Target;

import java.io.IOException;
import java.util.ArrayList;

/*------------------------Inheritance----------------------------*
 *                            Tile
 *                            /   \
 *                         Plot   Item
 *                          /        \
 *     Wall,Target,Floor,Hole        Crate,RiskyFloor,Key,Coin
 *---------------------------------------------------------------*/


public class Player extends Entity{

    private PlayerTile player_tile;
    private Map map_reset;

    private int crate_solved;
    private int num_crate;
    private ArrayList<Integer> crate_done;// contains index of the crate already well positioned


    Player(String name, PlayerTile player_tile) {
        super(name, player_tile);

        this.player_tile = player_tile;

        this.crate_done = new ArrayList<Integer>();
    }

    /**
     * Initiate the player to the game after the level has been launch
     * @param game
     * @throws SokobanError
     * @throws IOException
     */
    public void init(GameBoard game)  throws SokobanError, IOException {
        super.init(game);

        this.map_reset = (Map) map.clone();

        this.num_crate = map.getTargetList().size();

        this.progress();
    }

    public Tile getPlayerTile(){
        return this.player_tile;
    }

    public int getNumCrate() {
        return this.num_crate;
    }

    public int getCrateSolved(){
        return this.crate_solved;
    }

    /**
     * Reset the game to the initiate state
     * @throws SokobanError
     */
    public void resetMap() throws SokobanError {

        player_tile.setCoord(this.coord_start);
        this.coord_start = (Coord) this.coord_start.clone();
        this.map = (Map) map_reset.clone();
        game.relaunch(map);

        this.step = 0;
        this.current_tile = map.getTile(coord_start.getX(), coord_start.getY());
        this.previous_tile = null;
        num_crate = map.getTargetList().size();
        crate_solved = 0;
        crate_done.clear();
        this.key = 0;
        this.coin = 0;
        this.win = false;
        this.lose = false;
        this.running = true;

        setReset(true);

        this.game.setTileUsed(player_tile, coord_start.getX(), coord_start.getY());

        this.progress();
    }

    /**
     * evaluate the tile on which the player will go
     * if the tile has special property or the player has to interact with it
     */
    protected void evaluate(Tile next_tile){
        if(next_tile.isLock()){
            if(key > 0) {
                Item next_item = (Item) next_tile;
                next_item.setState(true);
                key -= 1;
            }
        }
        if(next_tile.isCollectable()){
            Item next_item = (Item) next_tile;
            next_item.setState(true);

            if(next_item.getImagePath().contains("key")){
                key += 1;
            }

            if(next_item.getImagePath().contains("coin")){
                coin += 1;
            }
        }
        // order of evaluation is important
        if(next_tile.isMortal()){
            lose = true;
        }
        if(next_tile.isRisky()){
            Item next_item = (Item) next_tile;
            next_item.setState(true);
        }
    }

    /**
     * Compare the coord of all target and crate
     * -> increase the nb of crate solved
     * -> win=true if all crate are solved
     */
    protected void progress() {
        crate_solved = 0;
        crate_done.clear();

        for(int i = 0; i < map.getTargetList().size(); i++) {
            Target target = map.getTargetList().get(i);
            int x_target = target.getCoord().getX();
            int y_target = target.getCoord().getY();

            for (int j = 0; j < map.getCrateList().size(); j++) {

                // doesn't verify if the crate already associated to a target
                if(crate_done.contains(j)){
                    continue;
                }

                Item crate = map.getCrateList().get(j);
                int x_crate = crate.getCoord().getX();
                int y_crate = crate.getCoord().getY();

                boolean similar_coord = (x_target == x_crate) && (y_target == y_crate);

                if (!similar_coord) {
                    crate.setState(false);
                }
                if (similar_coord) {
                    crate.setState(true);
                    crate_done.add(j);
                    crate_solved++;
                }
                if (map.getTargetList().size() == crate_solved) {
                    win = true;
                }
            }
        }
    }
}