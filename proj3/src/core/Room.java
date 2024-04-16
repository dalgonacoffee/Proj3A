package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

import static utils.RandomUtils.uniform;

public class Room {
    private int width;
    private int height;
    private List<Position> corners = new ArrayList<>();
    private Position connectionPoint;

    public Room(Random r) {
        int x, y;
        x = uniform(r, 88); // bottom left corner x
        y = uniform(r, 38); // bottom left corner y
        width = uniform(r, 7, 14); // dimensions
        height = uniform(r, 7, 14); // dimensions
        corners.add(new Position(x, y));
        corners.add(new Position(x + width, y));
        corners.add(new Position(x, y + height));
        corners.add(new Position(x + width, y + height));
        connectionPoint = new Position(uniform(r, x + 3, x + width - 3), uniform(r, y + 3, y + height - 3));
    }

    public void drawRoom(TETile[][] world, int x, int y) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                world[i][j] = Tileset.WALL;
            }
        }
        for (int i = x + 1; i < x + width - 1; i++) {
            for (int j = y + 1; j < y + height - 1; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
        //world[connectionPoint.getX()][connectionPoint.getY()] = Tileset.GRASS;
    }

    public boolean overlap(TETile[][] world, int x, int y) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (world[i][j] == Tileset.WALL || world[i][j] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Position> getCorners() {
        return corners;
    }

    public Position getConnectionPoint() {
        return connectionPoint;
    }
}
