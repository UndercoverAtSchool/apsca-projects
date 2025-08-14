package robot;

import kareltherobot.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Starter implements Directions {

  public static boolean containsArray(int[][] multiArray, int[] targetArray) { // util because I ain't writing allat
                                                                               // later
    if (multiArray == null || targetArray == null) {
      return false;
    }

    for (int[] innerArray : multiArray) {
      if (Arrays.equals(innerArray, targetArray)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {

    World.setVisible(true);// allows us to see the run output
    // the bigger the street, the farther north
    World.setSize(20, 20);
    World.setDelay(1);

    // The line below creates a Robot that we will refer to as rob
    // Find out what the numbers and direction do!
    // Put rob in a better location for your initials.
    // Robot rob = new Robot(15,2,South,9); // why the hell is it inverted??

    int[][] posconstruct = new int[20][];
    for (int i = 0; i < posconstruct.length; i++) {
      posconstruct[i] = new int[0];
    }
    posconstruct[0] = new int[] { 1, 2, 3, 4 };
    posconstruct[5] = new int[] { 7, 8 };

    int[][] pos = { { 0, 0 }, { 1, 2 } };
    Robot[] bots = new Robot[20];

    for (int i = 0; i < 20; i++) {
      bots[i] = new Robot(1, 1 + i, North, 20);
      // for (int j = 0; j < 20; j++) {
      // bots[i].putBeeper();
      // bots[i].move();
      // }
    }
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 20; j++) {
        final int index = i;
        // j approx x, i approx y
        // if (containsArray(pos, new int[] { j, i })) {
        // bots[j].putBeeper();
        // }
        if (IntStream.of(posconstruct[j]).anyMatch(x -> x == index)) {
          bots[j].putBeeper();
        }

        bots[j].move();
      }
    }

    // Want a second robot? No prob. They are cheap :)
    // Robot dude = new Robot(7,5,West,9);
    // examples of commands you can invoke on a Robot
    // rob.move();// move one step in the direction it is facing

    // // starting the letter R
    // rob.putBeeper();
    // rob.putBeeper();
    // rob.putBeeper();
    // rob.putBeeper();
    // rob.move();
    // rob.putBeeper();
    // rob.move();
    // rob.move();
    // rob.move();
    // rob.putBeeper();
    // rob.move();
    // rob.move();
    // rob.putBeeper();

    // done with the line, now on the curve
    // rob.turnLeft();

  }
}
