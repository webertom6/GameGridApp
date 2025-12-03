package be.weber.sokoban.code.tile;

import be.weber.sokoban.code.game.Actions;

abstract public class EntityTile extends Tile{
    private final String file_path_up;
    private final String file_path_down;
    private final String file_path_left;
    private final String file_path_right;

    private int num_image_up;
    private int num_image_down;
    private int num_image_left;
    private int num_image_right;

    private Actions PLAYER_DIRECTION;

    public EntityTile(Coord coord, String[] file_path){
        super(coord, file_path[0], false, false);
        file_path_down = super.PATH_RESSOURCE + file_path[0];
        file_path_up = super.PATH_RESSOURCE + file_path[1];
        file_path_left = super.PATH_RESSOURCE + file_path[2];
        file_path_right = super.PATH_RESSOURCE + file_path[3];

        PLAYER_DIRECTION = Actions.DOWN;
    }

    public void setDirection(Actions direction){
        this.PLAYER_DIRECTION = direction;
    }

    public String getImageUp(){
        return this.file_path_up;
    }

    public String getImageDown() {
        return this.file_path_down;
    }

    public String getImageLeft() {
        return this.file_path_left;
    }

    public String getImageRight() {
        return this.file_path_right;
    }

    public void setNumImageUp(int num_image_up){
        this.num_image_up = num_image_up;
    }
    public void setNumImageDown(int num_image_down){
        this.num_image_down = num_image_down;
    }
    public void setNumImageLeft(int num_image_left){
        this.num_image_left = num_image_left;
    }
    public void setNumImageRight(int num_image_right){
        this.num_image_right = num_image_right;
    }

    @Override
    public int getNumImage(){
        switch(PLAYER_DIRECTION){

            case UP:
                setNumImage(num_image_up);
                break;

            case DOWN:
                setNumImage(num_image_down);
                break;

            case LEFT:
                setNumImage(num_image_left);
                break;

            case RIGHT:
                setNumImage(num_image_right);
                break;
        }
        return super.getNumImage();
    }

    @Override
    public void setUnder(Tile new_under){ }
    @Override
    public void setNumImage2(int num_image2){ }

    @Override
    public Tile getUnderTile(){
        return null;
    }
    @Override
    public String getImage2Path(){
        return null;
    }

    @Override
    public String toString() {
        return "Entity{"+super.toString()+"}";
    }
}
