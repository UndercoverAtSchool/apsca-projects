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
		World.setDelay(2);

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

		// now - facing South in top left corner
		roomba.turnLeft(); // facing east-west
		// facing East in top left corner

		while (true) {
			while (roomba.nextToABeeper()) {
				roomba.pickBeeper();
				totalBeepers++;
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
			}
			roomba.move();
		}

		return totalBeepers;
	}
}
