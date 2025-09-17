package robot;

import java.util.stream.IntStream;

import kareltherobot.*;

public class DemoRoomba implements Directions {

	static int worldSpeed = 0;
	private Robot roomba;

	public static void logInfo(int totalCells, int totalPiles, int totalBeepers, int largestPile, int[] cornerPos,
			int[] largestPileLoc, int dim1, int dim2) {

		int largestPileRelative1 = largestPileLoc[0] - cornerPos[0];
		int largestPileRelative2 = largestPileLoc[1] - cornerPos[1];

		System.out.println("-".repeat(10) + " RESULTS " + "-".repeat(10));

		System.out.println("Room area: " + totalCells);
		System.out.println("Number of piles: " + totalPiles);
		System.out.println("Total beeper count: " + totalBeepers);
		System.out.println("Largest pile size: " + largestPile);
		System.out.println(
				"Largest pile is at (relative to bottom left): " + largestPileRelative2 + " units right and "
						+ largestPileRelative1 + " units up.");

		System.out.println("Average pile size: " + Math.round((double) totalBeepers / totalPiles));
		System.out.println(
				"Percent area dirty: " + (double) Math.round(10000 * (double) totalPiles / totalCells) / 100 + "%");
		System.out.println("Dimensions: h" + dim1 + ", w" + dim2);

		System.out.println("-".repeat(29));

	}

	static int[] initialPos = { 26, 149 };
	// basicRoom.wld - start at 7, 6
	// TestWorld-1.wld - start at 25, 11
	// finalTestWorld2024.wld - 26, 149

	public static void main(String[] args) {
		String worldName = "robot/wld/finalTestWorld2024.wld";

		DemoRoomba cleaner = new DemoRoomba();
		cleaner.cleanRoom(worldName, initialPos[0], initialPos[1]);

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
			System.out.println("Found new largest pile: " + pileSize);
			largestPilePos[0] = roomba.street();
			largestPilePos[1] = roomba.avenue();

		}

	}

	public int cleanRoom(String worldName, int startX, int startY) {

		World.readWorld(worldName);
		World.setVisible(true);
		World.setDelay(worldSpeed);
		World.setTrace(false); // prevent logspam

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

		int ave = 0;
		int street = 0;

		// Define initial values of dims (i and j), then repeat in circles
		while (roomba.frontIsClear()) {
			ave++;
			pickAllAndUpdateLargest(roomba);
			roomba.move();
		}
		roomba.turnLeft();
		while (roomba.frontIsClear()) {
			street++;
			pickAllAndUpdateLargest(roomba);
			roomba.move();
		}
		roomba.turnLeft();
		int width = ave + 1;
		int height = street + 1;
		int area = width * height;

		// After defining dimensions, move around, decrementing one each time

		while (ave > 0 && street > 0) { // Stop after can't spiral
			for (int i = 0; i < ave; i++) {
				pickAllAndUpdateLargest(roomba);
				roomba.move();
			}
			ave--;

			roomba.turnLeft();
			for (int i = 0; i < street; i++) {
				pickAllAndUpdateLargest(roomba);
				roomba.move();
			}
			street--;
			roomba.turnLeft();

		}
		pickAllAndUpdateLargest(roomba); // Doesn't pick up last one - we must do it manually
		logInfo(area, piles, beepers, largestPileSize, bottomLeft,
				largestPilePos, height, width);

		return beepers;
	}
}
