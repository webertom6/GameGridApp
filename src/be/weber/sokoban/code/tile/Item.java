package be.weber.sokoban.code.tile;

/**
 * Tile that can evolve or move during the game
 * @state each Item can have 2 state
 * @under Tile under Tile item ex: tile floor under tile crate
 * @file_path_state2 file path for the second state of the item
 * @num_image2 nb of image of the second image
 * PS: if a tile have to just evolve -> put under to null
 *     if a tile have to just move -> put file_path2 to null
 */
abstract public class Item extends Tile implements Cloneable{

    private boolean state;
    private Tile under_tile;
    private final String image_state2_path;
    private int num_image2;


    public Item(Coord coord, String image_path, String image_state2_path, Tile under_tile, boolean state,boolean walkable, boolean pushable){
        super(coord, image_path, pushable, walkable);

        if(image_state2_path != null) {
            this.image_state2_path = super.PATH_RESSOURCE + image_state2_path;
        }
        else{
            this.image_state2_path = image_state2_path;
        }
        setUnder(under_tile);
        setState(state);
    }

    public void setState(boolean state){
        this.state = state;
    }
    @Override
    public void setUnder(Tile new_under_tile){
        this.under_tile = new_under_tile;
    }
    @Override
    public void setNumImage2(int num_image2){
        this.num_image2 = num_image2;
    }


    public boolean getState(){
        return this.state;
    }
    @Override
    public Tile getUnderTile(){
        return this.under_tile;
    }
    @Override
    public String getImage2Path(){
        return this.image_state2_path;
    }

    /**
     * @return num_image or num_image2 depends on the state of the item
     */
    @Override
    public int getNumImage(){
        if(this.getState() == true){
            // second num image
            return this.num_image2;
        }
        else{
            // original num image
            return super.getNumImage();
        }
    }

    @Override
    public Object clone(){
        Item clone = null;

        clone = (Item) super.clone();
        clone.state = state;
        clone.num_image2 = num_image2;
        if(under_tile == null){
            clone.under_tile = null;
        }
        else {
            clone.under_tile = (Tile) under_tile.clone();
        }

        return clone;
    }

    @Override
    public String toString() {
        return super.toString()+
                ", state: "+state+
                ", file_path2: "+ image_state2_path +
                ", num_image2: "+num_image2;

    }
}