package be.weber.sokoban.code.tool;

import be.weber.sokoban.code.game.Map;
import be.weber.sokoban.code.screen.KeyChoice;
import be.weber.sokoban.code.tile.Tile;
import be.weber.sokoban.code.tile.TileID;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Algo {

    Algo() {}



    public static AItem APath(Map map, int start_row, int start_col){
        Tile[][] c_map = map.getCoordMap();

        Tile[][] grid = new Tile[c_map.length][c_map[0].length];

        for (int i = 0; i < c_map.length; i++) {
            for (int j = 0; j < c_map[0].length; j++) {
                System.out.print(Util.visualMap(c_map[i][j]));
                grid[i][j] = c_map[j][i];
            }
            System.out.println();
        }
        for (int i = 0; i < c_map.length; i++) {
            for (int j = 0; j < c_map[0].length; j++) {
                System.out.print(Util.visualMap(grid[i][j]));
            }
            System.out.println();
        }

        // To maintain location visit status, init at false by default
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        // applying BFS on matrix cells starting from source
        int[] src = {start_row, start_col, 0};
        LinkedList<Integer> path = new LinkedList<>();
//        Queue<AItem> queue = new LinkedList<>();
        PriorityQueue<AItem> queue = new PriorityQueue<>(new AItemComparator());

        queue.add(new AItem(src, path));
        visited[start_row][start_col] = true;

        while (!queue.isEmpty()){
            AItem q = queue.poll();

            src = q.getSrc();
            path = q.getPath();

            // destination found
            if (grid[src[0]][src[1]].getTILE_ID() == TileID.PLAYER){
                return q;
            }

            // moving up
            if (isValid(src[0] - 1, src[1], grid, visited)){
                LinkedList<Integer> p_add = (LinkedList<Integer>) path.clone();
                p_add.add(KeyChoice.UP);
                queue.add(new AItem(new int[]{src[0] - 1, src[1], src[2] + 1}, p_add));
                visited[src[0] - 1][src[1]] = true;
            }

            // moving down
            if (isValid(src[0] + 1, src[1], grid, visited)){
                LinkedList<Integer> p_add = (LinkedList<Integer>) path.clone();
                p_add.add(KeyChoice.DOWN);
                queue.add(new AItem(new int[]{src[0] + 1, src[1], src[2] + 1}, p_add));
                visited[src[0] + 1][src[1]] = true;
            }

            // moving left
            if (isValid(src[0], src[1] - 1, grid, visited)){
                LinkedList<Integer> p_add = (LinkedList<Integer>) path.clone();
                p_add.add(KeyChoice.LEFT);
                queue.add(new AItem(new int[]{src[0], src[1] - 1, src[2] + 1}, p_add));
                visited[src[0]][src[1] - 1] = true;
            }

            // moving right
            if (isValid(src[0], src[1] + 1, grid, visited)){
                LinkedList<Integer> p_add = (LinkedList<Integer>) path.clone();
                p_add.add(KeyChoice.RIGHT);
                queue.add(new AItem(new int[]{src[0], src[1] + 1, src[2] + 1}, p_add));
                visited[src[0]][src[1] + 1] = true;
            }


        }

        return new AItem(new int[]{start_col, start_col, 999}, new LinkedList<>());
    }

    public static BFSItem BFSPath(Map map, int start_row, int start_col){

        Tile[][] c_map = map.getCoordMap();

        Tile[][] grid = new Tile[c_map.length][c_map[0].length];

        for (int i = 0; i < c_map.length; i++) {
            for (int j = 0; j < c_map[0].length; j++) {
                System.out.print(Util.visualMap(c_map[i][j]));
                grid[i][j] = c_map[j][i];
            }
            System.out.println();
        }
        for (int i = 0; i < c_map.length; i++) {
            for (int j = 0; j < c_map[0].length; j++) {
                System.out.print(Util.visualMap(grid[i][j]));
            }
            System.out.println();
        }

        // To maintain location visit status, init at false by default
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        // applying BFS on matrix cells starting from source
        int[] src = {start_row, start_col, 0};
        LinkedList<Integer> path = new LinkedList<>();
        Queue<BFSItem> queue = new LinkedList<>();

        queue.add(new BFSItem(src, path));
        visited[start_row][start_col] = true;

        while (!queue.isEmpty()){
            BFSItem q = queue.poll();

            src = q.getSrc();
            path = q.getPath();

//            for (Integer i: path) {
//                System.out.print(Util.KeyString(i)+",");
//            }
//            System.out.println();
//            String s = "";
//            for (int i = 0; i < visited.length; i++) {
//                for (int j = 0; j < visited[i].length; j++) {
//                    s += Util.TFString(visited[i][j]);
//                }
//                s += "\n";
//            }
//            System.out.println(s);

            // destination found
            if (grid[src[0]][src[1]].getTILE_ID() == TileID.PLAYER){
                return q;
            }

            // moving up
            if (isValid(src[0] - 1, src[1], grid, visited)){
                LinkedList p_add = (LinkedList) path.clone();
                p_add.add(KeyChoice.UP);
                queue.add(new BFSItem(new int[]{src[0] - 1, src[1], src[2] + 1}, p_add));
                visited[src[0] - 1][src[1]] = true;
            }

            // moving down
            if (isValid(src[0] + 1, src[1], grid, visited)){
                LinkedList p_add = (LinkedList) path.clone();
                p_add.add(KeyChoice.DOWN);
                queue.add(new BFSItem(new int[]{src[0] + 1, src[1], src[2] + 1}, p_add));
                visited[src[0] + 1][src[1]] = true;
            }

            // moving left
            if (isValid(src[0], src[1] - 1, grid, visited)){
                LinkedList p_add = (LinkedList) path.clone();
                p_add.add(KeyChoice.LEFT);
                queue.add(new BFSItem(new int[]{src[0], src[1] - 1, src[2] + 1}, p_add));
                visited[src[0]][src[1] - 1] = true;
            }

            // moving right
            if (isValid(src[0], src[1] + 1, grid, visited)){
                LinkedList p_add = (LinkedList) path.clone();
                p_add.add(KeyChoice.RIGHT);
                queue.add(new BFSItem(new int[]{src[0], src[1] + 1, src[2] + 1}, p_add));
                visited[src[0]][src[1] + 1] = true;
            }


        }

        return new BFSItem(new int[]{start_col, start_col, 999}, new LinkedList<>());
    }

    private static boolean isValid(int row, int col, Tile[][] grid, boolean[][] visited){
        if ((row >= 0 && col >= 0) && (row < grid.length && col < grid[0].length)
             && (grid[row][col].getTILE_ID() != TileID.WALL && grid[row][col].getTILE_ID() != TileID.HOLE && grid[row][col].getTILE_ID() != TileID.CRATE)
                && (visited[row][col] == false)
           ){
            return true;
        }
        return false;
    }
}

class AItemComparator implements Comparator<AItem> {

    @Override
    // for ascending order of distance (so get the min distance)
    public int compare(AItem a1, AItem a2) {
        if (a1.getDist() > a2.getDist())
            return 1;
        else if (a1.getDist() < a2.getDist())
            return -1;
        return 0;
    }
}
