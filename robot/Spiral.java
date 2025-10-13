package robot;

import kareltherobot.*;

public class Spiral implements Directions {

    private Robot roomba;

    public static void main(String[] args) {
        String worldName = "robot/wld/basicRoom.wld"; // update if necessary
        Spiral cleaner = new Spiral();
        cleaner.cleanRoom(worldName, 7, 6);
    }

    int beepers;
    int piles;
    int largest = 0;
    int largestX = 0;
    int largestY = 0;

    public void pickAll() {
        int pileSize = 0;
        while (roomba.nextToABeeper()) {
            pileSize++;
            roomba.pickBeeper();
        }
        beepers += pileSize;
        piles++;
        if (pileSize > largest) {
            largest = pileSize;
            largestX = roomba.avenue();
            largestY = roomba.street();
        }
    }

    public void cleanRoom(String worldName, int startX, int startY) {

        World.readWorld(worldName);
        World.setVisible(true);
        World.setDelay(1);
        // World.setTrace(false);
        // ^^ enable later if you want to disable logs

        roomba = new Robot(startX, startY, West, 0);

        while (roomba.frontIsClear()) {
            roomba.move(); // First moves until hits left edge, then turns left
        }
        roomba.turnLeft();

        while (roomba.frontIsClear()) {
            roomba.move(); // Move until hitting bottom edge
        }
        roomba.turnLeft();

        // bottom left

        int x = 0;
        int y = 0;

        while (roomba.frontIsClear()) {
            x++;
            roomba.move();
        }

        roomba.turnLeft();

        while (roomba.frontIsClear()) {
            y++;
            roomba.move();
        }

        roomba.turnLeft();

        pickAll();

        while (x > 0 || y > 0) {
            for (int i = 0; i < x; i++) {
                roomba.move();
                pickAll();
            }
            x--;
            roomba.turnLeft();
            for (int i = 0; i < y; i++) {
                roomba.move();
                pickAll();
            }
            y--;
            roomba.turnLeft();
        }

        System.out.println("Largest pile: " + largest);
        System.out.println("Largest pile location: " + largestX + ", " + largestY);
    }

}
