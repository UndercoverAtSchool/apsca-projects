package robot;

import kareltherobot.*;

public class Roomba implements Directions {

	// Main method to make this self-contained
	public static void main(String[] args) {
		// LEAVE THIS ALONE!!!!!!
		String worldName = "robot/basicRoom.wld";

		Roomba cleaner = new Roomba();
		int totalBeepers = cleaner.cleanRoom(worldName, 7, 6);
		System.out.println("Roomba cleaned up a total of " + totalBeepers + " beepers.");

	}

	public static void turnRight(Robot rob) {
		rob.turnLeft();
		rob.turnLeft();
		rob.turnLeft();
	}

	// declared here so it is visible in all the methods!
	private Robot roomba;

	public int cleanRoom(String worldName, int startX, int startY) {

		World.readWorld(worldName);
		World.setVisible(true);
		World.setDelay(1);

		roomba = new Robot(startX, startY, North, 0);
		roomba.move();

		// first move to top left of box to normalize position
		for (int i = 0; i < 2; i++) { // up, then left
			while (roomba.frontIsClear()) {
				roomba.move();
			}
			roomba.turnLeft();

		}

		int totalBeepers = 0;
		int totalPiles = 0;
		int largestPile = 0;
		int[] largestPileLoc = new int[2];
		int totalCells = 0;
		int[] initialPosition = { roomba.avenue(), roomba.street() };

		// now - facing South in top left corner
		roomba.turnLeft(); // facing east-west
		// facing East in top left corner

		while (true) {
			int pileSize = 0;
			totalCells++;
			while (roomba.nextToABeeper()) {
				pileSize++;
				roomba.pickBeeper();
				totalBeepers++;
			}

			if (pileSize != 0) {
				totalPiles++;
			}
			if (pileSize > largestPile) {
				largestPile = pileSize;
				largestPileLoc[0] = roomba.avenue();
				largestPileLoc[1] = roomba.street();
			}

			if (!roomba.frontIsClear()) {
				if (roomba.facingEast()) {
					turnRight(roomba);
					if (!roomba.frontIsClear()) {
						break;
					}
					roomba.move();
					turnRight(roomba);
				} else if (roomba.facingWest()) {
					roomba.turnLeft();
					if (!roomba.frontIsClear()) {
						break;
					}
					roomba.move();
					roomba.turnLeft();
				}
				totalCells++;
			}
			roomba.move();
		}

		int largestPileRelative1 = initialPosition[0] - largestPileLoc[0];
		int largestPileRelative2 = initialPosition[1] - largestPileLoc[1];

		System.out.println("Room area: " + totalCells);
		System.out.println("Number of piles: " + totalPiles);
		System.out.println("Total beeper count: " + totalBeepers);
		System.out.println("Largest pile size: " + largestPile);
		System.out.println("Largest pile is at (relative to top left): (" + -1 * largestPileRelative1 + ", "
				+ largestPileRelative2 + ")");
		System.out.println("Average pile size: " + Math.round((double) totalBeepers / totalPiles));
		System.out.println("Percent area dirty: " + Math.round(100 * (double) totalPiles / totalCells) + "%");

		return totalBeepers;
	}

	public int cleanRoomSpiral(String worldName, int startX, int startY) {
		return 0;
	}
}
