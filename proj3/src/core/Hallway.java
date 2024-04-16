package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

import static utils.RandomUtils.uniform;

public class Hallway {
    private Position start;
    private Position end;

    public Hallway(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public void drawHallway(TETile[][] world, Random r) {
        if (start.getX() == end.getX()) {
            drawVertHallway(world, start, end);
        } else if (start.getY() == end.getY()) {
            drawHorizHallway(world, start, end);
        } else {
            Position mid;
            if (uniform(r, 2) == 0) { // vert then horiz
                mid = new Position(start.getX(), end.getY());
                drawVertHallway(world, start, mid);
                drawCorner(world, mid);
                drawHorizHallway(world, mid, end);
            } else { // horiz then vert
                mid = new Position(end.getX(), start.getY());
                drawHorizHallway(world, start, mid);
                drawCorner(world, mid);
                drawVertHallway(world, mid, end);
            }
        }
    }

    public void drawVertHallway(TETile[][] world, Position s, Position e) {
        int minY = Math.min(s.getY(), e.getY());
        int maxY = Math.max(s.getY(), e.getY());
        for (int y = minY; y <= maxY + 1; y++) {
            if (world[s.getX() + 1][y] == Tileset.NOTHING) {
                world[s.getX() + 1][y] = Tileset.WALL;
            }
            if (y <= maxY) {
                world[s.getX()][y] = Tileset.FLOOR;
            }
            if (world[s.getX() - 1][y] == Tileset.NOTHING) {
                world[s.getX() - 1][y] = Tileset.WALL;
            }
        }
    }

    public void drawHorizHallway(TETile[][] world, Position s, Position e) {
        int minX = Math.min(s.getX(), e.getX());
        int maxX = Math.max(s.getX(), e.getX());
        for (int x = minX; x <= maxX + 1; x++) {
            if (world[x][s.getY() + 1] == Tileset.NOTHING) {
                world[x][s.getY() + 1] = Tileset.WALL;
            }
            if (x <= maxX) {
                world[x][s.getY()] = Tileset.FLOOR;
            }
            if (world[x][s.getY() - 1] == Tileset.NOTHING) {
                world[x][s.getY() - 1] = Tileset.WALL;
            }
        }
    }

    public void drawCorner(TETile[][] world, Position e) {
        int xPos = e.getX(), yPos = e.getY();
        for (int x = xPos - 1; x < xPos + 1; x++) {
            for (int y = yPos - 1; y < yPos + 1; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
        world[xPos][yPos] = Tileset.FLOOR;
    }
}
