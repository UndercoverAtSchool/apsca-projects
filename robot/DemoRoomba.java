package robot;

import java.util.stream.IntStream;

import kareltherobot.*;

public class DemoRoomba implements Directions {

	static int worldSpeed = 0;
	private Robot roomba;

	public static void logInfo(int totalCells, int totalPiles, int totalBeepers, int largestPile, int[] initialPosition,
			int[] largestPileLoc) {

		int largestPileRelative1 = largestPileLoc[0] - initialPosition[0];
		int largestPileRelative2 = largestPileLoc[1] - initialPosition[1];

		System.out.println("Room area: " + totalCells);
		System.out.println("Number of piles: " + totalPiles);
		System.out.println("Total beeper count: " + totalBeepers);
		System.out.println("Largest pile size: " + largestPile);
		System.out.println(
				"Largest pile is at (relative to bottom left): " + largestPileRelative2 + " units right and "
						+ largestPileRelative1 + " units up.");

		System.out.println("Average pile size: " + Math.round((double) totalBeepers / totalPiles));
		System.out.println("Percent area dirty: " + Math.round(100 * (double) totalPiles / totalCells) + "%");

	}

	static int[] initialPos = { 26, 149 };
	// basicRoom.wld - start at 7, 6
	// TestWorld-1.wld - start at 25, 11
	// finalTestWorld2024.wld - 26, 149

	public static void main(String[] args) {
		String worldName = "robot/wld/finalTestWorld2024.wld";

		DemoRoomba cleaner = new DemoRoomba();
		int totalBeepers = cleaner.cleanRoom(worldName, initialPos[0], initialPos[1]);
		System.out.println("Roomba cleaned up a total of " + totalBeepers + " beepers.");

	}

	private int beepers;
	private int piles;
	private int largestPileSize = 0;
	private int[] largestPilePos = { 0, 0 };

	public void pickAllAndUpdateLargest(Robot roomba) {

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

		// If the current pile is the largest, update the largest pile
		if (pileSize > largestPileSize) {
			largestPileSize = pileSize;
			System.out.println("updated to " + pileSize);
			largestPilePos[0] = roomba.street();
			largestPilePos[1] = roomba.avenue();

		}

	}

	public int cleanRoom(String worldName, int startX, int startY) {

		World.readWorld(worldName);
		World.setVisible(true);
		World.setDelay(worldSpeed);

		roomba = new Robot(startX, startY, West, 0); // Faces LEFT

		while (roomba.frontIsClear()) {
			roomba.move(); // First moves until hits left edge, then turns left
		}
		roomba.turnLeft();

		while (roomba.frontIsClear()) {
			roomba.move(); // Move until hitting bottom edge
		}
		roomba.turnLeft();

		int[] bottomLeft = { roomba.street(), roomba.avenue() };

		// Now faces RIGHT/EAST

		int[] dims = { 0, 0 }; // calculate area

		// Define initial values of dims (i and j), then repeat in circles
		while (roomba.frontIsClear()) {
			dims[0]++;
			pickAllAndUpdateLargest(roomba);
			roomba.move();
		}
		roomba.turnLeft();
		while (roomba.frontIsClear()) {
			dims[1]++;
			pickAllAndUpdateLargest(roomba);
			roomba.move();
		}
		roomba.turnLeft();
		int area = (dims[0] + 1) * (dims[1] + 1);

		// After defining dimensions, move around, decrementing one each time

		while (dims[0] > 0 && dims[1] > 0) { // Stop after can't spiral
			for (int i = 0; i < dims[0]; i++) {
				pickAllAndUpdateLargest(roomba);
				roomba.move();
			}
			dims[0]--;

			roomba.turnLeft();
			for (int i = 0; i < dims[1]; i++) {
				pickAllAndUpdateLargest(roomba);
				roomba.move();
			}
			dims[1]--;
			roomba.turnLeft();

		}
		pickAllAndUpdateLargest(roomba); // Doesn't pick up last one - we must do it manually
		logInfo(area, piles, beepers, largestPileSize, bottomLeft,
				largestPilePos);

		return beepers;
	}
}
