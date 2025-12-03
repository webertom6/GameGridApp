package be.weber.sokoban.code.tool;

import java.util.LinkedList;

public class BFSItem {

    private int[] src;
    private LinkedList<Integer> path;

    BFSItem(int[] src, LinkedList<Integer> path){
        this.src = src;
        this.path = path;
    }

    public int[] getSrc() {
        return src;
    }

    public int getDist() {
        return src[2];
    }

    public LinkedList<Integer> getPath() {
        return path;
    }
}
