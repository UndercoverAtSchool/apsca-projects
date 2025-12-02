package gol;

import java.util.Arrays;

public class TestSuite {
    public static void run()
    {
        System.out.println("Starting TestSuite");
        boolean pass = true;

        GameOfLife board = new GameOfLife(5, 5);

        // Test count neighbors
        int[][] data = {{ 1 }};

        // Set a single cell, then check that it has zero neighbors.
        board.set(2, 2, data);
        pass &= expect(board.countNeighbors(2,2), 0, "Single live cell with zero neighbors");

        // Set the middle top neighbor.
        board.set(1, 2, data);
        pass &= expect(board.countNeighbors(2,2), 1, "Single live cell with one neighbor");

        // Set the bottom middle neighbor.
        board.set(3, 2, data);
        pass &= expect(board.countNeighbors(2,2), 2, "Single live cell with two neighbors");

        // Test update state
        board.step();
        board.print();
        pass &= expect(board.get(2,3), 1, "Expect line to rotate");

        board.step();
        board.print();
        pass &= expect(board.get(1,2), 1, "Expect line to rotate");


        if (pass == true)
        {
            System.out.println("--- TEST PASSED! Congrats! ---");
        }
        else
        {
            System.out.println("--- TEST FAILED! :( ---");
        }
    }

    private static boolean expect(int input, int expected, String comment)
    {
        if (input == expected)
        {
            return true;
        }
        System.out.println(comment + ", value: " + input + " not equal to expected: " + expected);
        return false;
    }

    private static boolean testPattern(int[][] input, int[][] expected)
    {
        return true;
    }

}
