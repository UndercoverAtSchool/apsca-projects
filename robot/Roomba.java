package robot;

import java.util.concurrent.TimeoutException;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import kareltherobot.*;

public class Roomba implements Directions {

	static int[] initialPos = { 25, 11 };
	static int worldSpeed = 1;

	// Main method to make this self-contained
	public static void main(String[] args) {
		// LEAVE THIS ALONE!!!!!!
		// basic - start at 7, 6
		// test 1 - start at 25, 11
		String worldName = "robot/TestWorld-1.wld";

		Roomba cleaner = new Roomba();
		int totalBeepers = cleaner.cleanRoom(worldName, initialPos[0], initialPos[1]);
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
	private int[] largestPilePos = { 0, 0 };
	private int largestPileSize;
	private int totalCellCount;

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
		World.setDelay(worldSpeed);

		return cleanRoomSpiral2(worldName, startX, startY);
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

	// bottom is self sufficient

	public int pickAll(Robot roomba) {
		int pileSize = 0;
		while (roomba.nextToABeeper()) {
			roomba.pickBeeper();
			pileSize++;
		}
		if (pileSize == 0) {
			return 0;
		}
		beepers += pileSize;
		piles++;
		return beepers;

	}

	public void updateLocIfNeeded(int num) {
		if (num > largestPileSize) {
			largestPileSize = num;
			System.out.println("updated to " + num);
			largestPilePos[0] = roomba.avenue();
			largestPilePos[1] = roomba.street();
		}
	}

	public int cleanRoomSpiral2(String worldName, int startX, int startY) {

		// only two methods used here for demonstration purposes:
		// updateLocIfNeeded and pickAll
		roomba = new Robot(startX, startY, West, 0);
		for (int n = 0; n < 2; n++) { // left, then down
			while (roomba.frontIsClear()) {
				roomba.move();
				totalCellCount++;
			}
			roomba.turnLeft();
		}
		// facing EAST, in bottom left corner - this is where it starts regardless, but
		// this is to ensure in all rectangles

		int[] dims = { 0, 0 };

		// define initial values of dims (i and j), then repeat in circles
		while (roomba.frontIsClear()) {
			dims[0]++;
			updateLocIfNeeded(pickAll(roomba));
			roomba.move();
			totalCellCount++;
		}
		roomba.turnLeft();
		while (roomba.frontIsClear()) {
			dims[1]++;
			updateLocIfNeeded(pickAll(roomba));
			roomba.move();
			totalCellCount++;
		}
		roomba.turnLeft();

		// stop once can no longer spiral

		while (dims[0] != 0 && dims[1] != 0) {
			for (int i = 0; i < dims[0]; i++) {
				updateLocIfNeeded(pickAll(roomba));
				roomba.move();
				totalCellCount++;
			}
			dims[0]--;

			roomba.turnLeft();
			for (int i = 0; i < dims[1]; i++) {
				updateLocIfNeeded(pickAll(roomba));
				roomba.move();
				totalCellCount++;
			}
			dims[1]--;
			roomba.turnLeft();

		}
		updateLocIfNeeded(pickAll(roomba));
		// pick all up, since parity issues on last one
		logInfo(totalCellCount, piles, beepers, largestPileSize, initialPos, largestPilePos);

		return beepers;
	}
}
