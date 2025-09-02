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

	// You will need to add many variables!!

	public int cleanRoom(String worldName, int startX, int startY) {

		// A new Robot should be constructed and assigned to the global (instance)
		// variable named roomba that is declared above.
		// Make sure it starts at startX and startY location.

		World.readWorld(worldName);
		World.setVisible(true);
		World.setDelay(2);

		/**
		 * This section will have all the logic that takes the Robot to every location
		 * and cleans up all piles of beepers. Think about ways you can break this
		 * large, complex task into smaller, easier to solve problems.
		 */

		// the line below causes a null pointer exception
		// what is that and why are we getting it?
		roomba = new Robot(startX, startY, North, 0);
		roomba.move();

		// first move to top left of box to normalize position
		for (int i = 0; i < 2; i++) { // up, then left
			while (true) {
				if (!roomba.frontIsClear()) {
					roomba.turnLeft();
					break;
				}
				roomba.move();
			}

		}

		int totalBeepers = 0;

		// now - facing South in top left corner
		roomba.turnLeft(); // facing east-west
		// facing East in top left corner

		while (true) {
			while (true) {
				if (!roomba.nextToABeeper()) {
					break;
				}
				roomba.pickBeeper();
				totalBeepers++;
			}

			if (!roomba.frontIsClear()) {
				System.out.println("Detected wall collision");
				if (roomba.facingEast()) {
					System.out.println("ran into a wall facing EAST");
					turnRight(roomba);
					if (!roomba.frontIsClear()) {
						break;
					}
					roomba.move();
					turnRight(roomba);
					System.out.println(roomba.direction());
				} else if (roomba.facingWest()) {
					System.out.println("ran into a wall facing WEST");
					roomba.turnLeft();
					if (!roomba.frontIsClear()) {
						break;
					}
					roomba.move();
					roomba.turnLeft();
					System.out.println(roomba.direction());
				}
			}
			roomba.move();
		}

		return totalBeepers;
	}
}
