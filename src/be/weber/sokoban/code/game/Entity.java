package be.weber.sokoban.code.game;

import be.weber.sokoban.code.gui.GameGui;
import be.weber.sokoban.code.gui.SokobanError;
import be.weber.sokoban.code.tile.Coord;

import java.io.IOException;

import be.weber.sokoban.code.tile.*;

/*------------------------Inheritance----------------------------*
 *                            Tile
 *                            /   \
 *                         Plot   Item
 *                          /        \
 *     Wall,Target,Floor,Hole        Crate,RiskyFloor,Key,Coin
 *---------------------------------------------------------------*/


abstract public class Entity{

    private String name;
    private EntityTile entity_tile;
    protected Coord coord_start;
    protected Map map;
    protected GameGui game;

    protected static boolean reset;
    protected Map map_reset;



    protected int step;
    protected Tile current_tile;
    protected Tile previous_tile;

    protected int key;
    protected int coin;
    protected boolean win;
    protected boolean lose;
    protected boolean running;


    Entity (String name, EntityTile entity_tile) {
        this.name = name;
        this.entity_tile = entity_tile;

        this.step = 0;
        this.previous_tile = null;

        reset = false;

        this.key = 0;
        this.coin = 0;
        this.win = false;
        this.lose = false;
        this.running = false;
    }

    /**
     * Initiate the entity to the game after the level has been launch
     * @param game
     * @throws SokobanError
     * @throws IOException
     */
    public void init(GameGui game)  throws SokobanError, IOException {

        this.coord_start = (Coord) entity_tile.getCoord().clone();
        this.map = game.getMap();
        this.game = game;
        this.current_tile = map.getTile(entity_tile.getCoord().getX(), entity_tile.getCoord().getY());
    }

    public String getName(){
        return this.name;
    }

    public static boolean isReset() {
        return reset;
    }

    public static void setReset(boolean reset_key) {
        reset = reset_key;
    }

    public int getStep() {
        return this.step;
    }

    public Tile getEntityTile(){
        return this.entity_tile;
    }

    public Tile getCurrentTile(){
        return this.current_tile;
    }

    public Tile getPreviousTile(){
        return this.previous_tile;
    }

    public int getKey(){
        return this.key;
    }

    public int getCoin() {
        return this.coin;
    }

    public boolean isWin() {
        return this.win;
    }

    public boolean isLose(){
        return this.lose;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean state){
        this.running = state;
    }

    /**
     * Push the next tile and move the player by (dx,dy)
     */
    private void push(int dx, int dy) throws SokobanError {
        Tile after_next = map.getTile(entity_tile.getCoord().getX()+dx+dx, entity_tile.getCoord().getY()+dy+dy);
        Tile next_tile = map.getTile(entity_tile.getCoord().getX()+dx, entity_tile.getCoord().getY()+dy);

        map.translateTile(next_tile, dx, dy);
        map.translateTile(entity_tile, dx, dy);

        previous_tile = current_tile;
        current_tile = next_tile.getUnderTile();

        this.evaluate(next_tile.getUnderTile());

        next_tile.setUnder(after_next);// place the tile after under the item that will be placed above

        map.setTile(next_tile);
        map.setTile(entity_tile);

        // progress change the tile that should be
        this.progress();

        // then make the visual change
        game.setTileUsed(entity_tile, entity_tile.getCoord().getX(), entity_tile.getCoord().getY());
        game.setTileUsed(next_tile, next_tile.getCoord().getX(), next_tile.getCoord().getY());
    }

    /**
     * Player walk on the next tile in overwriting it.
     * Place the tile player is going to walk in current_tile
     */
    private void walk(int dx, int dy) throws SokobanError {
        Tile next_tile = map.getTile(entity_tile.getCoord().getX()+dx, entity_tile.getCoord().getY()+dy);

        previous_tile = current_tile;
        current_tile = next_tile;

        map.translateTile(entity_tile, dx, dy);

        map.setTile(entity_tile);
        game.setTileUsed(entity_tile, entity_tile.getCoord().getX(), entity_tile.getCoord().getY());
    }

    /**
     * Restore the tile on which the player has walked the step before
     */
    private void restorePrev() throws SokobanError {
        map.setTile(previous_tile);
        game.setTileUsed(previous_tile,previous_tile.getCoord().getX(), previous_tile.getCoord().getY());
    }


    /**
     * Move the player in selected direction if it's valid.
     * Otherwise, the player stay at the same play
     * @param direction SokobanGUI variable given by .getEvent()
     */
    public void move(int direction) throws SokobanError, IOException {
        Actions action_request = Actions.transformKeyChoice(direction);
        int action_valid = validAction(action_request);

        if (action_valid == Actions.NOT_VALID)
            return;

        entity_tile.setDirection(action_request);

        if (action_valid == Actions.PUSH) {
            push(action_request.getDx(), action_request.getDy());
        }
        if (action_valid == Actions.WALK) {
            walk(action_request.getDx(), action_request.getDy());
        }

        restorePrev();
        this.step += 1;

//        switch(direction_valid){
//            case Actions.PUSH_UP:
//                entity_tile.setDirection(Actions.PUSH_UP);
//                push(0,-1);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.PUSH_DOWN:
//                entity_tile.setDirection(Actions.PUSH_DOWN);
//                push(0,+1);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.PUSH_LEFT:
//                entity_tile.setDirection(Actions.PUSH_LEFT);
//                push(-1,0);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.PUSH_RIGHT:
//                entity_tile.setDirection(Actions.PUSH_RIGHT);
//                push(+1,0);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.WALK_UP:
//                entity_tile.setDirection(Actions.WALK_UP);
//                walk(0,-1);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.WALK_DOWN:
//                entity_tile.setDirection(Actions.WALK_DOWN);
//                walk(0,+1);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.WALK_LEFT:
//                entity_tile.setDirection(Actions.WALK_LEFT);
//                walk(-1,0);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.WALK_RIGHT:
//                entity_tile.setDirection(Actions.WALK_RIGHT);
//                walk(+1,0);
//                restorePrev();
//                this.step += 1;
//                break;
//
//            case Actions.NOT_VALID:
//                break;
//        }
    }

    /**
     * Return the valid direction otherwise direction is not valid
     * @param action SokobanGUI variable given by .getEvent()
     * @return int variable depending on the direction possible
     */
    protected int validAction(Actions action) throws IOException, SokobanError {
        int action_valid = Actions.NOT_VALID;

        switch (action){
            case EXIT:
                System.out.println("Game Stop");
                System.exit(0);

//            case KeyChoice.RESET:
//
//                break;

            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                Tile higher_tile = map.getTile(entity_tile.getCoord().getX() + action.getDx(), entity_tile.getCoord().getY() + action.getDy());

                evaluate(higher_tile);

                if(higher_tile.isPushable()){
                    Tile up_cond = map.getTile(entity_tile.getCoord().getX() + action.getDx()*2, entity_tile.getCoord().getY() + action.getDy()*2);

                    if((up_cond.isWalkable()) && (!up_cond.isPushable())) { // verify if the push can be executed for tile further
                        action_valid = Actions.PUSH; // valid push up
                    }
                }
                if(higher_tile.isWalkable()){
                    action_valid = Actions.WALK; // valid walk up
                }
                break;

            case STAND:
                break;
        }
        return action_valid;
    }

    /**
     * evaluate the tile on which the player will go
     * if the tile has special property or the player has to interact with it
     */
    protected abstract void evaluate(Tile next_tile);

    protected abstract void progress();
}
