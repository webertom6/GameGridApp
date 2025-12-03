package be.weber.sokoban.code.tile;

/**
 * a piece that contains an element of the map
 * @coord coord of the tile
 * @image string of the file path for .loadImage()
 * @pushable tile can be pushed or not
 * @walkable tile can be walked on or not
 * @num_image nb of image loaded associated to the tile
 */
public abstract class Tile implements Cloneable {
    protected String PATH_RESSOURCE = "/be/weber/sokoban/resource/tiles/";
    /** variables common to every Tile */
    private int TILE_ID;
    private Coord coord;
    private String image_path; // file_path of the Tile image
    private boolean pushable;
    private boolean walkable;
    private int num_image;

    /** these variables are false by default but can be changed depending on the child class which need it*/
    private boolean risky;
    private boolean mortal;
    private boolean collectable;
    private boolean lock;

    Tile(Coord coord, String file_path, boolean pushable, boolean walkable){
        this.coord = coord;
        this.image_path = PATH_RESSOURCE +file_path;
        this.pushable = pushable;
        this.walkable = walkable;

        this.risky = false;
        this.mortal =  false;
        this.collectable = false;
        this.lock = false;
    }

    public int getTILE_ID() {
        return TILE_ID;
    }

    public void setTILE_ID(int TILE_ID) {
        this.TILE_ID = TILE_ID;
    }

    public void setCoord(Coord coord){
        this.coord = coord;
    }
    public void translate(int dx, int dy){
        coord.translate(dx, dy);
    }

    // set property of a tile
    public void setPushable(boolean pushable){
        this.pushable = pushable;
    }
    public void setWalkable(boolean walkable){
        this.walkable = walkable;
    }
    public void setRisky(boolean risky){
        this.risky = risky;
    }
    public void setMortal(boolean mortal){
        this.mortal = mortal;
    }
    public void setCollectable(boolean collectable){
        this.collectable = collectable;
    }
    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void setNumImage(int num_image){
        this.num_image = num_image;
    }

    /** Set a Tile under another one */
    public abstract void setUnder(Tile under);

    /** Set a second nb of image for a same Tile */
    public abstract void setNumImage2(int num_image2);


    public Coord getCoord(){
        return this.coord;
    }
    public String getImagePath(){
        return this.image_path;
    }
    public boolean isPushable(){
        return this.pushable;
    }
    public boolean isWalkable(){
        return this.walkable;
    }
    public int getNumImage(){
        return this.num_image;
    }


    public boolean isMortal(){
        return this.mortal;
    }
    public boolean isRisky(){
        return this.risky;
    }
    public boolean isCollectable(){
        return this.collectable;
    }
    public boolean isLock() {
        return this.lock;
    }

    /**
     * Get the Tile under another one
     * @return Tile under if there is one, otherwise return null
     */
    public abstract Tile getUnderTile();

    /**
     * @return the file path of the second image if there is one, otherwise return null
     */
    public abstract String getImage2Path();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tile)) {
            return false;
        }
        Tile bis = (Tile) obj;

        return bis.image_path.equals(image_path);
    }

    @Override
    public int hashCode() {
        return image_path.hashCode();
    }

    public Object clone(){
        Tile clone = null;
        try{
            clone = (Tile) super.clone();
            clone.coord = (Coord) coord.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError("Unable to clone Tile");
        }
        return clone;
    }

    @Override
    public String toString() {
        return "coord:" + coord +
                ", image:'" + image_path + '\'' +
                ", pushable:" + pushable +
                ", walkable:" + walkable +
                ", num_image:" + num_image +
                ", risky:" + risky +
                ", mortal:" + mortal +
                ", collectable:" + collectable +
                ", lock:" + lock;
    }
}
