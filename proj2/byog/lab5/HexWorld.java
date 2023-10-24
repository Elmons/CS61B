package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random RANDOM = new Random();
    public static class Position {
        private int x, y;
        public Position(int a, int b) {
            x = a;
            y = b;
        }

        public void setPosition(int a, int b) {
            x = a;
            y = b;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            return;
        }
        Position beginP = p;
        int len = s;
        for (int i = 0; i < s; i++) {
            addLine(world, beginP, len, t);
            beginP = new Position(beginP.getX() - 1, beginP.getY() + 1);
            len = len + 2;
        }
        len -= 2;
        beginP = new Position(beginP.getX() + 1, beginP.getY());
        for (int i = 0; i < s; i++) {
            addLine(world, beginP, len, t);
            beginP = new Position(beginP.getX() + 1, beginP.getY() + 1);
            len = len - 2;
        }
    }

    private static void addLine(TETile[][] world, Position p, int len, TETile t) {
        int x = p.getX();
        int y = p.getY();
        for (int i = 0; i < len; i++, x++) {
            world[x][y] = t;
        }
    }

    public static void mutilHexagon(TETile[][] world, Position p, int len) {
        int nums = 3;
        Position beginP = p;
        for (int i = 0; i < 5; i++) {
            verticalHexagon(world, beginP, len, nums);
            if (i < 2) {
                nums += 1;
                beginP = bottomRight(beginP, len);
            }else {
                nums -= 1;
                beginP = topRight(beginP, len);
            }
        }
    }

    private static void verticalHexagon(TETile[][] world, Position p, int len, int nums){
        Position beginP = p;
        for (int i = 0; i < nums; i++) {
            TETile t = randomTile();
            addHexagon(world, beginP, len, t);
            beginP = new Position(beginP.getX(), beginP.getY() + 2 * len);
        }
    }

    private static Position bottomRight(Position p, int len){
        return new Position(p.getX() + 2 * len - 1, p.getY() - len);
    }

    private static Position topRight(Position p, int len){
        return new Position(p.getX() + 2 * len - 1, p.getY() + len);
    }
    @Test
    public void testVerticalHexagon(){
        return;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.TREE;
            case 4: return Tileset.SAND;
            case 5: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }
    public static void main(String[] args) {
        int width = 50, height = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        TETile[][] world = new TETile[width][height];
        Position p = new Position(5, 25);
        int s = 3;
        TETile t = Tileset.WALL;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        mutilHexagon(world, p, s);
        ter.renderFrame(world);
    }
}
