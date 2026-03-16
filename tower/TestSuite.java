package tower;

public class TestSuite {

    private boolean pass = false;
    private int score = 0;
    private int maxScore = 0;

    public TestSuite()
    {
    }

    public void run()
    {
        System.out.println("Starting TestSuite");
        pass = true;
        score = 0;
        maxScore = 0;

        // Part 1: Model
        TowerModel tiny = new TowerModel(2);
        // Check height
        expectEqual(tiny.getHeight(), 2, "height should be 1");

        IntegerStack[] towers = tiny.getTowers();
        // Check initial state
        expectEqual(towers[0].get(0), 2, "tower0 should contain 2");

        // Move a disk
        tiny.move(0, 2);

        // Check that move succeeded.
        expectEqual(towers[2].get(0), 1, "disk 1 should move to tower2");

        // Illegal move
        tiny.move(0, 2);

        // Check that move failed.
        expectEqual(towers[2].get(1), 0, "disk 2 should not have moved");

        // Part 2: Solver
        TowerModel hanoi = new TowerModel(3);
        towers = hanoi.getTowers();
        // Check initial state
        expectEqual(towers[0].get(0), 3, "tower0 should have disk 3 on bottom");
        expectEqual(towers[2].get(0), 0, "tower2 should have zero disks");

        // Construct a solver and solve the tower
        TowerSolver solver = new TowerSolver();
        solver.solve(hanoi);

        // Check results
        expectEqual(towers[0].get(0), 0, "tower0 should have zero disks");
        expectEqual(towers[2].get(0), 3, "tower2 should have disk 3 on bottom");

        if (pass == true)
        {
            System.out.println("--- TEST PASSED! Congrats! Score: " + score + "/" + maxScore + " ---");
        }
        else
        {
            System.out.println("--- TEST FAILED! :( Score: " + score + "/" + maxScore + " ---");
        }
    }

    private void expectEqual(int value, int expected, String note)
    {
        maxScore += 1;
        if (value != expected)
        {
            System.out.println("Value: " + value + " != " + expected + ", " + note);
            pass = false;
        }
        else
        {
            score += 1;
        }
    }
}
