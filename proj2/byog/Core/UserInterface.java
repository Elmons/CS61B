package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class UserInterface {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;

    private static final TETile ENTITY = Tileset.PLAYER;
    private TETile[][] gameworld;

    private Position cur;
    public void game() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.enableDoubleBuffering();
        startGame();
        chooseMode();
        playgame();
    }

    private void startGame() {
        StdDraw.clear();
        int centralX = this.WIDTH / 2;
        int centralY = this.HEIGHT / 2;
        int offsetX = 0;
        Font font = new Font("TITLE", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        int offsetY = 10;
        String text = "CS61B: THE GAME";
        StdDraw.text(centralX + offsetX, centralY + offsetY, text);
        font = new Font("TITLE", Font.BOLD, 30);
        StdDraw.setFont(font);
        offsetY = 0;
        text = "New Game (N)";
        StdDraw.text(centralX + offsetX, centralY + offsetY, text);
        offsetY -= 2;
        text = "Load Game (L)";
        StdDraw.text(centralX + offsetX, centralY + offsetY, text);
        offsetY -= 2;
        text = "Quit (Q)";
        StdDraw.text(centralX + offsetX, centralY + offsetY, text);
        StdDraw.show();
    }
    private void chooseMode() {
        while (true){
            while (!StdDraw.hasNextKeyTyped()) ;
            String mode;
            mode = String.valueOf(StdDraw.nextKeyTyped());
            if (mode.equals("N") || mode.equals("n")) {
                initialgame();
                return;
            } else if (mode.equals("l") || mode.equals("L")) {
                loadgame();
                return;
            }
        }
    }

    private void initialgame() {
        int centralX = this.WIDTH / 2;
        int centralY = this.HEIGHT / 2;
        Font font = new Font("TITLE", Font.BOLD, 30);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        String text = "Please enter the seed :";
        StdDraw.text(centralX, centralY, text);
        StdDraw.show();
        long seed = readseed();
        WorldGenerator generator = new WorldGenerator(seed, WIDTH, HEIGHT);
        gameworld = generator.generateWorld();
        StdDraw.clear();
        Random rand = new Random(seed);
        Position start;
        do {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            start = new Position(x, y);
        } while (!validFloor(start));
        cur = start;
        moveTo(start);
    }

    private boolean validFloor(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (gameworld[x][y] == Tileset.FLOOR) {
            return true;
        }
        return false;
    }

    private void moveTo(Position pos) {
        if (!validFloor(pos)){
            return;
        }
        int curX = cur.getX();
        int curY = cur.getY();
        gameworld[curX][curY] = Tileset.FLOOR;
        int x = pos.getX();
        int y = pos.getY();
        gameworld[x][y] = ENTITY;
        cur = pos;
    }

    private long readseed() {
        String res = "";
        while (true) {
            while (!StdDraw.hasNextKeyTyped());
            char c = StdDraw.nextKeyTyped();
            if (c == 'S') {
                return Integer.parseInt(res);
            } else {
                res += String.valueOf(c);
                drawframe("Please enter the a number to be seed: " + res);
            }
        }
    }

    private void drawframe(String s) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("default", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        int centralX = WIDTH / 2;
        int centralY = HEIGHT / 2;
        if (s != null) { // draw text
            font = new Font("bold", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(centralX, centralY, s);
        }
        StdDraw.show();
    }
    private void loadgame() {
        return;
    }

    private void playgame() {
        char action;
        TERenderer te = new TERenderer();
        te.initialize(WIDTH, HEIGHT);
        te.renderFrame(gameworld);
        while (true) {
            while (!StdDraw.hasNextKeyTyped());
            action = StdDraw.nextKeyTyped();
            takeAction(action);
            te.renderFrame(gameworld);
        }
    }

    private void takeAction(char action) {
        if (action == 'w' || action == 'W') {
            Position pos = new Position(cur.getX(), cur.getY() + 1);
            moveTo(pos);
        } else if (action == 'A' || action == 'a') {
            Position pos = new Position(cur.getX() - 1, cur.getY());
            moveTo(pos);
        } else if (action == 'S' || action == 's') {
            Position pos = new Position(cur.getX(), cur.getY() - 1);
            moveTo(pos);
        } else if (action == 'D' || action == 'd') {
            Position pos = new Position(cur.getX() + 1, cur.getY());
            moveTo(pos);
        }
    }
}
