package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

import static utils.RandomUtils.uniform;

public class World {
    private TETile[][] world;
    private int width;
    private int height;
    private List<Room> rooms = new ArrayList<>();

    // build your own world!
    public World(long seed) {
        width = 100;
        height = 50;
        world = new TETile[width][height];
        Random r1 = new Random(seed); // change seed
        int n = 0, c = 0, numRooms;
        Room r;
        numRooms = uniform(r1, 15, 20);
        setNothing(); // set background to Tileset.NOTHING
        while (n < numRooms && c < 2000) { // draw rooms
            r = new Room(r1);
            if (!r.overlap(world, r.getCorners().get(0).getX(), r.getCorners().get(0).getY())) {
                r.drawRoom(world, r.getCorners().get(0).getX(), r.getCorners().get(0).getY());
                rooms.add(r);
                n++;
            }
            c++;
        }
        List<Room> roomsNotVisited = new ArrayList<>(rooms);
        Position cp1, cp2;
        Hallway h;
        Room near;
        int conn = 0, jump = 0;
        for (int i = 1; i < 3; i++) { // drawing hallways
            while (conn < Math.floorDiv(rooms.size(), i)) {
                Room currRoom = rooms.get(jump);
                cp1 = currRoom.getConnectionPoint();
                near = nearestRoom(currRoom, roomsNotVisited, i);
                jump = rooms.indexOf(near);
                cp2 = near.getConnectionPoint();
                roomsNotVisited.remove(near);
                h = new Hallway(cp1, cp2);
                h.drawHallway(world, r1);
                conn++;
            }
            roomsNotVisited = new ArrayList<>(rooms);
            conn = 0;
            jump = 0;
        }
    }

    public double distance(Position p1, Position p2) {
        int deltaX = p2.getX() - p1.getX();
        int deltaY = p2.getY() - p1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Room nearestRoom(Room r, List<Room> rms, int flag) {
        Position cp = r.getConnectionPoint();
        List<Room> temp = new ArrayList<>(rms);
        temp.remove(r);
        Room nearest = temp.get(0), secondNearest = temp.get(0);
        double minDistance = distance(cp, nearest.getConnectionPoint());
        double secondMinDistance = distance(cp, secondNearest.getConnectionPoint());
        for (Room rm : temp) {
            double currDistance = distance(cp, rm.getConnectionPoint());
            if (currDistance < minDistance) {
                minDistance = currDistance;
                nearest = rm;
            } else if (currDistance < secondMinDistance && !rm.equals(nearest)) {
                secondNearest = rm;
                secondMinDistance = currDistance;
            }
        }
        if (flag == 2) {
            return secondNearest;
        } else {
            return nearest;
        }
    }

    public void setNothing() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getTiles() {
        return world;
    }
}
