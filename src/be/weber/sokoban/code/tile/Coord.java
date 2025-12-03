package be.weber.sokoban.code.tile;

public class Coord implements Cloneable{
    private int x, y;

    public Coord(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public Object clone(){
        Coord clone = null;
        try {
            clone = (Coord) super.clone();
            clone.x = this.x;
            clone.y = this.y;
        }
        catch (CloneNotSupportedException e){
            throw new RuntimeException("Unable to clone Coord");
        }

        return clone;
    }

    @Override
    public String toString()
    {
        return "("+x+","+y+")";
    }
}
