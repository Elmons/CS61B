package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;
public class WorldGenerator {
    private long seed;
    private int width, height;
    private Random random;
    private final TETile wall = Tileset.WALL;
    private final TETile floor = Tileset.FLOOR;

    private class Manager {
        public boolean validRoom(TETile[][] world, Position p, int w, int h) {
            if (p == null || world == null || w < 1 || h < 1) {
                return false;
            }
            int x = p.getX();
            int y = p.getY();
            if (x + w - 1 > width - 1 - MARGIN || y + h - 1 > height - MARGIN) { //检查是否在地图内
                return false;
            }

            //检查是否连通
            for (int i = x; i < x + w; i++) {
                if (world[i][y] == floor || world[i][y + h - 1] == floor) {
                    return true;
                }
            }
            for (int i = y; i < y + h; i++) {
                if (world[x][i] == floor || world[x + w - 1][i] == floor) {
                    return true;
                }
            }
            return false;
        }

        public boolean validHorizon(TETile[][] world, Position p, int w) {
            if (p == null || world == null || w < 2) {
                return false;
            }
            int x = p.getX();
            int y = p.getY();
            if (x + w - 1 > width - 1 - MARGIN || y + 1 > height - MARGIN || y - 1 < MARGIN - 1) {
                return false;
            }
            return true;
        }

        public boolean validVertical(TETile[][] world, Position p, int h) {
            if (p == null || world == null || h < 2) {
                return false;
            }
            int x = p.getX();
            int y = p.getY();
            if (y + h - 1 > height - 1 - MARGIN || x - 1 < MARGIN - 1 || x + 1 > width - MARGIN) {
                return false;
            }
            return true;
        }
    }

    private class Worker {
        public void buildRoom(TETile[][] world, Position p, int w, int h) {
            int x = p.getX();
            int y = p.getY();
            for (int i = x; i < x + w; i++) {
                for (int j = y; j < y + h; j++) {
                    if ((i == x || i == x + w - 1
                            || j == y || j == y + h - 1)
                            && (world[i][j] != floor)) {
                        world[i][j] = wall;
                    } else {
                        world[i][j] = floor;
                    }
                }
            }
        }

        public void buildHorizonRoad(TETile[][] world, Position p, int w) { // to right
            int x = p.getX();
            int y = p.getY();
            for (int i = x; i < x + w; i++) {
                if (world[i][y + 1] == Tileset.NOTHING) {
                    world[i][y + 1] = wall;
                }
                world[i][y] = floor;
                if (world[i][y - 1] == Tileset.NOTHING) {
                    world[i][y - 1] = wall;
                }
            }
        }

        public void buildVerticalRoad(TETile[][] world, Position p, int h) { // to up
            int x = p.getX();
            int y = p.getY();
            for (int i = y; i < y + h && i < width - 1; i++) {
                if (world[x - 1][i] == Tileset.NOTHING) {
                    world[x - 1][i] = wall;
                }
                if (i != height) {
                    world[x][i] = floor;
                }
                if (world[x + 1][i] == Tileset.NOTHING) {
                    world[x + 1][i] = wall;
                }
            }
        }

        public void fix(TETile[][] world, Position p) {
            int x = p.getX();
            int y = p.getY();
            if (world[x + 1][y + 1] != floor) {
                world[x + 1][y + 1] = wall;
            }
            if (world[x + 1][y - 1] != floor) {
                world[x + 1][y - 1] = wall;
            }
            if (world[x - 1][y + 1] != floor) {
                world[x - 1][y + 1] = wall;
            }
            if (world[x - 1][y - 1] != floor) {
                world[x - 1][y - 1] = wall;
            }
        }

        public void finish(TETile[][] world, Position p) {
            int x = p.getX();
            int y = p.getY();
            if (world[x + 1][y] == Tileset.NOTHING) {
                world[x + 1][y] = wall;
            } else if (world[x - 1][y] == Tileset.NOTHING) {
                world[x - 1][y] = wall;
            } else if (world[x][y + 1] == Tileset.NOTHING) {
                world[x][y + 1] = wall;
            } else if (world[x][y - 1] == Tileset.NOTHING) {
                world[x][y - 1] = wall;
            }
        }
    }
    private Manager manager;
    private Worker woker;

    private int maxRoom, maxRoad, maxRoomNum, maxRoadNum;

    private final int MARGIN = 4;

    public WorldGenerator(long seed, int width, int height) {
        this.seed = seed;
        this.width = width;
        this.height = height;
        manager = new Manager();
        woker = new Worker();
        random = new Random(this.seed);
        maxRoom = width / 8;
        maxRoad = width / 5;
        maxRoomNum = (height * width) / ((maxRoom * maxRoom));
        maxRoadNum = (width * height) / (maxRoad * 2);
    }

    private int generateInt(int begin, int end) {
        return random.nextInt(end - begin + 1) + begin;
    }

    public TETile[][] generateWorld() {
        TETile[][] world = new TETile[width][height];
        initial(world);
        generateRoad(world);
        generateRoom(world);
        return world;
    }
    public void initial(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    private void generateRoad(TETile[][] world) { //思路：从一个点开始，然后不断回执路线。
        Position beginP = new Position(10, 10);
        Position curP = beginP;
        Position buildP;
        int l;
        int roadNum = generateInt(maxRoadNum / 2, maxRoadNum);
        int lastdire = -1;
        while (roadNum > 0) {
            boolean valid;
            int dire;
            do {
                dire = generateInt(0, 3);
                l = generateInt(maxRoad / 4, maxRoad);
                buildP = getBuildPos(curP, dire, l);
                if (dire < 2) {
                    valid = manager.validHorizon(world, buildP, l);
                } else {
                    valid = manager.validVertical(world, buildP, l);
                }
            } while (!valid || dire == lastdire);
            if (dire < 2) {
                woker.buildHorizonRoad(world, buildP, l);
                lastdire = 1 - dire;
            } else {
                woker.buildVerticalRoad(world, buildP, l);
                lastdire = 3 - dire + 2;
            }
            curP = getNextPos(curP, dire, l);
            woker.fix(world, curP);
            roadNum--;
        }
        woker.finish(world, curP);
        woker.fix(world, beginP);
        woker.finish(world, beginP);
    }
    private void generateRoom(TETile[][] world) {
        Position p;
        int w, h;
        int roomNum = generateInt(maxRoomNum / 3, maxRoomNum / 2);
        while (roomNum > 0) {
            do {
                int x = generateInt(MARGIN, width - 1);
                int y = generateInt(MARGIN, height - 1);
                p = new Position(x, y);
                w = generateInt(maxRoom / 4 + 2, maxRoom);
                h = generateInt(maxRoom / 4 + 2, maxRoom);
            } while (!manager.validRoom(world, p, w, h));
            woker.buildRoom(world, p, w, h);
            roomNum--;
        }
    }

    private Position getBuildPos(Position curP, int dire, int l) {
        int x = curP.getX();
        int y = curP.getY();
        switch (dire) {
            case 1: { //draw to left
                x = x - l + 1;
                break;
            }
            case 3: { //draw to down
                y = y - l + 1;
                break;
            }
            default: {
                x = x;
                y = y;
            }
        }
        if (x < MARGIN || y < MARGIN) {
            return null;
        }
        return new Position(x, y);
    }

    private Position getNextPos(Position curP, int dire, int l) {
        int x = curP.getX();
        int y = curP.getY();
        switch (dire) {
            case 0: {
                x = x + l - 1;
                break;
            }
            case 1: {
                x = x - l + 1;
                break;
            }
            case 2: {
                y = y + l - 1;
                break;
            }
            case 3: {
                y = y - l + 1;
                break;
            }
            default: {
                x = x;
                y = y;
            }
        }
        return new Position(x, y);
    }
}

