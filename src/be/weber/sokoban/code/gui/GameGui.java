package be.weber.sokoban.code.gui;

import be.weber.sokoban.code.game.Map;
import be.weber.sokoban.code.tile.EntityTile;
import be.weber.sokoban.code.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * window that display the game
 */
public class GameGui extends JPanel {
    private volatile boolean first_launch;

    private final int HEIGHT;
    private final int WIDTH;
    private HashMap<String, Integer> dict_image = new HashMap<String, Integer>();
    private Map map;
    private GameGrid grid;
    private boolean window_resized = false;

    public GameGui(Map map){
        this.setLayout(new GridBagLayout());

        this.first_launch = false;

        this.HEIGHT = map.getHeight();
        this.WIDTH = map.getWidth();
        this.map = map;

        this.grid = new GameGrid(WIDTH, HEIGHT, 32);

        this.add(grid);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                window_resized = true;

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(window_resized){

            Dimension gridSize = grid.getPreferredSize();
            if(gridSize.width > getWidth() || gridSize.height > getHeight()){
                int newGridWidth = Math.min(gridSize.width, getWidth());
                int newGridHeight = Math.min(gridSize.height, getHeight());
                grid.setSize(new Dimension(newGridWidth-10, newGridHeight-10));

                int x = (getWidth() - grid.getWidth()) / 2;
                int y = (getHeight() - grid.getHeight()) / 2;
                grid.setLocation(x, y);
            }
        }
        super.paintComponent(g);
    }

    public boolean isFirstLaunched() {
        return first_launch;
    }

    public Map getMap() {
        return map;
    }

    public GameGrid getGrid() {
        return grid;
    }

    /**
     * Load every distinct image's tile in the map and store them
     */
    public void loadImageMap() {
        try {
            Set<Tile> distinct_tiles = map.getDistinctTile();

            // each different tile in the mpa
            for (Tile distc_tile : distinct_tiles) {

                if(distc_tile.getImage2Path() != null){
                    int num_im2 = grid.loadImage(distc_tile.getImage2Path());
                    dict_image.put(distc_tile.getImage2Path(), num_im2);
                }

                if(distc_tile.getUnderTile() != null){
                    Tile under = distc_tile.getUnderTile();

                    if(under.getImage2Path() != null){ // second image for the tile under another one
                        int under_image2 = grid.loadImage(under.getImage2Path());
                        dict_image.put(under.getImage2Path(), under_image2);
                    }

                    int under_image = grid.loadImage(under.getImagePath());
                    dict_image.put(under.getImagePath(), under_image);
                }
                int num_im = grid.loadImage(distc_tile.getImagePath());
                dict_image.put(distc_tile.getImagePath(), num_im);
            }
        }
        catch (SokobanError e){
            System.out.println("Error when loading the distinct tile of the map");
        }
    }

    /**
     * Set the different elements from the initialised map in the window
     */
    public void launch(){
        try {
            for(int i = 0; i < HEIGHT; i++){
                for(int j = 0; j < WIDTH; j++){
                    Tile current_tile  = map.getTile(i,j);

                    if(current_tile.getImage2Path() != null){
                        int tile_image2 = dict_image.get(current_tile.getImage2Path());
                        current_tile.setNumImage2(tile_image2);
                    }

                    if(current_tile.getUnderTile() != null){
                        Tile under = current_tile.getUnderTile();

                        if(under.getImage2Path() != null){ // second image for the tile under another one
                            int under_image2 = dict_image.get(under.getImage2Path());
                            under.setNumImage2(under_image2);
                        }

                        int under_image = dict_image.get(under.getImagePath());
                        under.setNumImage(under_image);
                    }
                    setTile(current_tile, i, j);
                }
            }
            first_launch = true;
        }
        catch (SokobanError e){
            System.err.println("SokobanError : "+ e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launch again the window if each Tile has already its image number load
     */
    public void relaunch(Map map_relaunch){
        this.map = map_relaunch;
        try {
            for(int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    Tile current_tile = map_relaunch.getTile(i,j);

                    setTileUsed(current_tile,i,j);
                }
            }
        }
        catch (SokobanError e){
            System.err.println("SokobanError : "+ e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Set Tile for player with different image
     */
    public void setTileEntity(EntityTile entity, int x, int y) throws SokobanError, IOException {

        map.setTile(entity);

        int tile_image = grid.loadImage(entity.getImagePath());
        entity.setNumImage(tile_image);
        entity.setNumImageDown(tile_image);

        grid.setCell(x, y, tile_image);

        int tile_image_up = grid.loadImage(entity.getImageUp());
        entity.setNumImageUp(tile_image_up);

        int tile_image_left = grid.loadImage(entity.getImageLeft());
        entity.setNumImageLeft(tile_image_left);

        int tile_image_right = grid.loadImage(entity.getImageRight());
        entity.setNumImageRight(tile_image_right);
    }

    /**
     * Set a Tile on the window, only for initialisation
     */
    public void setTile(Tile tile, int x, int y) throws SokobanError, IOException {
        int tile_image = dict_image.get(tile.getImagePath());
        tile.setNumImage(tile_image);
        grid.setCell(x, y, tile.getNumImage());
    }


    /**
     * Set a Tile on a window already initialised.
     * (to avoid too much loadImage, reuse the int when it has been created)
     */
    public void setTileUsed(Tile tile, int x, int y) throws SokobanError {
        map.setTile(tile);

        grid.setCell(x, y, tile.getNumImage());
    }
}