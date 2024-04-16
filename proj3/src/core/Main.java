package core;

import tileengine.TERenderer;

public class Main {
    public static void main(String[] args) {
        World world = new World(6367);
        TERenderer ter = new TERenderer();
        ter.initialize(100, 50);
        ter.renderFrame(world.getTiles());
        /*
        if (args.length > 1) {
            System.out.println("Can only have one argument - the replay string");
            System.exit(0);
        } else if (args.length == 1) {
            World world = new World();
            TETile[][] worldState = world.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            World world = new World();
            world.playWithKeyBoard();
        }
         */
    }
}
