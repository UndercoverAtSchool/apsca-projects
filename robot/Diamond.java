package robot;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import kareltherobot.*;

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Diamond implements Directions {

    private List<Point> getLinePoints(Point p1, Point p2) {
        // construct range of points to search
        // for each point - if dist is <0.5 treat point as part of line
        int xMin = Math.min(p1.x, p2.x);
        int xMax = Math.max(p1.x, p2.x);
        int yMin = Math.min(p1.y, p2.y);
        int yMax = Math.max(p1.y, p2.y);

        int a = p1.y - p2.y;
        int b = p2.x - p1.x;
        int c = p1.x * p2.y - p2.x * p1.y;
        double denom = Math.sqrt(a * a + b * b);
        // d = |ax₁ + by₁ + c| / √(a² + b²)
        // ^^ formula for distance to line of form ax + by + c = 0

        List<Point> points = new ArrayList<>();
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                double dist = (denom == 0) ? 0 : (Math.abs(a * i + b * j + c) / denom);
                if (dist < 0.5) {
                    points.add(new Point(i, j));
                }
            }
        }
        // pointmap
        return points;
    }

    private void drawLineFromPoints(List<Point> pointList) {
        Robot linerob = new Robot(pointList.get(0).y, pointList.get(0).x, South, 100);
        Direction[] dirs = new Direction[2];

        if (pointList.getLast().x - pointList.getFirst().x > 0) {
            dirs[0] = East;
        } else {
            dirs[0] = West;
        }

        if (pointList.getLast().y - pointList.getFirst().y > 0) {
            dirs[0] = North;
        } else {
            dirs[0] = South;
        }
        // linerob - spawn at first point, go through points, move vertical diff num
        // times, move horizontal diff num times

        Direction currentDir = South;
        Point currentPos = new Point(pointList.get(0).y, pointList.get(0).x);

        int currentIndex = 1;
        for (Point point : pointList) {
            while (!(dirs[0] == currentDir)) {
                linerob.turnLeft();
            }
            // horizontally aligned
            // while (!(currentPos.x) ==

        }

    }

    public static void main(String[] args) {

        Diamond diamond = new Diamond();

        World.setVisible(true);
        World.setSize(20, 20);

        Robot rob = new Robot(15, 2, South, 9);

        // draw from (2, 3) to (5, 8) to test
        List<Point> points = diamond.getLinePoints(new Point(2, 3), new Point(4, 6));
        System.out.print(points.get(0).x);

    }
}
