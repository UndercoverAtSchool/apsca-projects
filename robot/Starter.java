package robot;
import kareltherobot.*;


public class Starter implements Directions{

  public static void main(String[] args) {

    World.setVisible(true);// allows us to see the run output
    // the bigger the street, the farther north
    World.setSize(20,20);


    // The line below creates a Robot that we will refer to as rob
    // Find out what the numbers and direction do!
    // Put rob in a better location for your initials.
    Robot rob = new Robot(15,2,South,9);

    // Want a second robot?  No prob.  They are cheap :)
    //Robot dude = new Robot(7,5,West,9);
    // examples of commands you can invoke on a Robot
    rob.move();// move one step in the direction it is facing


    // starting the letter R
    rob.putBeeper();
    rob.putBeeper();
    rob.putBeeper();
    rob.putBeeper();
    rob.move();
    rob.putBeeper();
    rob.move();
    rob.move();
    rob.move();
    rob.putBeeper();
    rob.move();
    rob.move();
    rob.putBeeper();

    // done with the line, now on the curve
    // rob.turnLeft();

  }
}
