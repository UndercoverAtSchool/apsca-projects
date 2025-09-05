package robot;

import java.util.stream.IntStream;

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
	private int beepers;
	private int piles;

	public static void logInfo(int totalCells, int totalPiles, int totalBeepers, int largestPile, int[] initialPosition,
			int[] largestPileLoc) {

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

	}

	public void turnTo(Robot roomba, Direction dir) {
		while (roomba.direction() != dir) {
			roomba.turnLeft();
		}
	}

	public void jumpTopLeft(Robot roomba) {
		// must be aligned North!
		turnTo(roomba, North);
		for (int i = 0; i < 2; i++) { // up, then left
			while (roomba.frontIsClear()) {
				roomba.move();
			}
			roomba.turnLeft();
		}
	}

	public int[] countDims(Robot roomba) {
		jumpTopLeft(roomba);

		int[] dims = { 1, 1 };

		turnTo(roomba, South);
		for (int i = 0; i < 2; i++) { // down then right
			while (roomba.frontIsClear()) {
				roomba.move();
				dims[i]++;
			}
			roomba.turnLeft();
		}

		return dims;

	}

	public void pickAll(Robot roomba) {
		int pileSize = 0;
		while (roomba.nextToABeeper()) {
			roomba.pickBeeper();
			pileSize++;
		}
		if (pileSize == 0) {
			return;
		}
		beepers += pileSize;
		piles++;

	}

	public void moveX(Robot roomba, int count) {
		IntStream.range(0, count).forEach(i -> {
			pickAll(roomba);
			if (roomba.frontIsClear()) { // soft fail
				roomba.move();
			}

		});
		pickAll(roomba);
	}

	public int cleanRoom(String worldName, int startX, int startY) {
		World.readWorld(worldName);
		World.setVisible(true);
		World.setDelay(5);

		return cleanRoomSpiral(worldName, startX, startY);
	}

	public int cleanRoomRectangle(String worldName, int startX, int startY) {

		roomba = new Robot(startX, startY, North, 0);

		jumpTopLeft(roomba);

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

		logInfo(totalCells, totalPiles, totalBeepers, largestPile, initialPosition, largestPileLoc);

		return totalBeepers;
	}

	public int cleanRoomSpiral(String worldName, int startX, int startY) {

		roomba = new Robot(startX, startY, North, 0);
		int[] dims = countDims(roomba);
		System.out.println(dims[0] + " " + dims[1]);
		int x = dims[0];
		int y = dims[1] - 1;
		jumpTopLeft(roomba);
		turnTo(roomba, South);

		System.out.println("starting pickup");

		while (x != 0 & y != 0) {
			moveX(roomba, x);
			roomba.turnLeft();
			moveX(roomba, y);
			roomba.turnLeft();
			x -= 1;
			y -= 1;
			System.out.println(x);
		}

		return 0;
	}
}
