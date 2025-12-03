package be.weber.sokoban.code.tile;

/**
 * Tile that does not evolve or move during the game
 */
abstract public class Plot extends Tile implements Cloneable{

    public Plot(Coord coord, String image_path, boolean walkable, boolean pushable){
        super(coord, image_path, pushable, walkable);
    }

    @Override
    public void setUnder(Tile new_under){    }
    @Override
    public void setNumImage2(int num_image2){    }


    @Override
    public Tile getUnderTile(){
        return null;
    }
    @Override
    public String getImage2Path(){
        return null;
    }

    // avoid change coord of a plot
    @Override
    public void setCoord(Coord coord){   }

    @Override
    public Plot clone() {
        return (Plot) super.clone();
    }
}
