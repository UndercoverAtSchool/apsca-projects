package robot;

import kareltherobot.*;

public class DemoRoomba implements Directions {

	static int worldSpeed = 0;
	private Robot roomba;

	public static void main(String[] args) {
		String worldName = "robot/wld/finalTestWorld2024.wld";

		DemoRoomba cleaner = new DemoRoomba();
		// basicRoom.wld - start at 7, 6
		// TestWorld-1.wld - start at 25, 11
		// finalTestWorld2024.wld - 26, 149

		cleaner.cleanRoom(worldName, 26, 149);

	}

	private int beepers;
	private int piles;
	private int largestPileSize = 0;
	private int largestPilePosAve = 0; // horizontal (avenue)
	private int largestPilePosStreet = 0; // vertical (street)

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
			largestPilePosStreet = roomba.street();
			largestPilePosAve = roomba.avenue();
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

		int bottomLeftStreet = roomba.street(); // vertical (street)
		int bottomLeftAve = roomba.avenue(); // horizontal (avenue)

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

		int largestPileRelativeStreet = largestPilePosStreet - bottomLeftStreet; // vertical (up)
		int largestPileRelativeAve = largestPilePosAve - bottomLeftAve; // horizontal (right)

		System.out.println("-".repeat(10) + " RESULTS " + "-".repeat(10));
		System.out.println("Room area: " + area);
		System.out.println("Number of piles: " + piles);
		System.out.println("Total beeper count: " + beepers);
		System.out.println("Largest pile size: " + largestPileSize);
		System.out.println(
				"Largest pile is at (relative to bottom left): " + largestPileRelativeAve + " units right and "
						+ largestPileRelativeStreet + " units up.");
		System.out.println("Average pile size: " + Math.round((double) beepers / piles));
		System.out.println(
				"Percent area dirty: " + (double) Math.round(10000 * (double) piles / area) / 100 + "%");
		System.out.println("Dimensions: h" + height + ", w" + width);
		System.out.println("-".repeat(29));

		return beepers;
	}
}
