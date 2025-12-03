package be.weber.sokoban.code.game;

import be.weber.sokoban.code.screen.KeyChoice;

public enum Actions {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), STAND(0,0), EXIT(-1,-1);

    public static final int WALK = 9;
    public static final int PUSH = 10;


    public static final int NOT_VALID = 0;
    public static final int PUSH_UP = 1;
    public static final int WALK_UP = 2;
    public static final int PUSH_DOWN = 3;
    public static final int WALK_DOWN = 4;
    public static final int PUSH_LEFT = 5;
    public static final int WALK_LEFT = 6;
    public static final int PUSH_RIGHT = 7;
    public static final int WALK_RIGHT = 8;

    private final int dx;
    private final int dy;

    Actions(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Actions transformKeyChoice(int key_choice){
        switch (key_choice) {
            case KeyChoice.UP:
                return UP;
            case KeyChoice.DOWN:
                return DOWN;
            case KeyChoice.LEFT:
                return LEFT;
            case KeyChoice.RIGHT:
                return RIGHT;
            case KeyChoice.QUIT:
                return EXIT;
            default:
                return STAND;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getPushMove() {
        // Return the corresponding push direction for the current direction
        switch (this) {
            case UP:
                return PUSH_UP;
            case DOWN:
                return PUSH_DOWN;
            case LEFT:
                return PUSH_LEFT;
            case RIGHT:
                return PUSH_RIGHT;
            default:
                return NOT_VALID;
        }
    }

    public int getWalkMove() {
        // Return the corresponding walk direction for the current direction
        switch (this) {
            case UP:
                return WALK_UP;
            case DOWN:
                return WALK_DOWN;
            case LEFT:
                return WALK_LEFT;
            case RIGHT:
                return WALK_RIGHT;
            default:
                return NOT_VALID;
        }
    }
}



//public enum Direction {;
//    public static final int NOT_VALID = 0;
//    public static final int PUSH_UP = 1;
//    public static final int WALK_UP = 2;
//    public static final int PUSH_DOWN = 3;
//    public static final int WALK_DOWN = 4;
//    public static final int PUSH_LEFT = 5;
//    public static final int WALK_LEFT = 6;
//    public static final int PUSH_RIGHT = 7;
//    public static final int WALK_RIGHT = 8;
//}

/*
public static final int NOT_VALID = 0;
public static final int PUSH_UP = 1;
public static final int WALK_UP = 2;
public static final int PUSH_DOWN = 3;
public static final int WALK_DOWN = 4;
public static final int PUSH_LEFT = 5;
public static final int WALK_LEFT = 6;
public static final int PUSH_RIGHT = 7;
public static final int WALK_RIGHT = 8;
*/

/* regular writing of a class enum but not used in this case
you need to give a constant expression required for switch
NOT_VALID,
PUSH_UP,
WALK_UP,
PUSH_DOWN,
WALK_DOWN,
PUSH_LEFT,
WALK_LEFT,
PUSH_RIGHT,
WALK_RIGHT;
*/