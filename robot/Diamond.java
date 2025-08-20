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

    public int thickness = 1; // keep ~0.5

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
                // TODO: thickness!
                if (dist < this.thickness) {
                    points.add(new Point(i, j));
                }
            }
        }
        // pointmap
        return points;
    }

    private void drawPoints(List<Point> pointList) {
        // technically not needed, but keeps things faster by removing potential
        // back-and-forth

        pointList.sort((a, b) -> {
            int da = Math.abs(a.x - pointList.get(0).x) + Math.abs(a.y -
                    pointList.get(0).y);
            int db = Math.abs(b.x - pointList.get(0).x) + Math.abs(b.y -
                    pointList.get(0).y);
            return Integer.compare(da, db);
        });

        Robot linerob = new Robot(pointList.get(0).y, pointList.get(0).x, South, pointList.size());
        // Direction[] dirs = new Direction[2];

        // if (pointList.getLast().x - pointList.getFirst().x > 0) {
        // dirs[0] = East;
        // } else {
        // dirs[0] = West;
        // }

        // if (pointList.getLast().y - pointList.getFirst().y > 0) {
        // dirs[1] = North;
        // } else {
        // dirs[1] = South;
        // }
        // linerob - spawn at first point, go through points, move vertical diff num
        // times, move horizontal diff num times

        Direction currentDir = South;
        Point currentPos = new Point(pointList.get(0).x, pointList.get(0).y);
        List<Point> pointless = pointList.subList(1, pointList.size());

        for (Point point : pointless) {
            Direction[] dirs = new Direction[2];
            if (point.x - currentPos.x > 0) {
                dirs[0] = East;
            } else {
                dirs[0] = West;
            }

            if (point.y - currentPos.y > 0) {
                dirs[1] = North;
            } else {
                dirs[1] = South;
            }

            while (!(dirs[0] == currentDir)) {
                linerob.turnLeft();
                currentDir = currentDir.rotate(-1);
            }
            // horizontally aligned
            while (!(currentPos.x == point.x)) {
                linerob.move();
                currentPos.x = currentPos.x + (currentDir == East ? 1 : -1);
            }
            while (!(dirs[1] == currentDir)) {
                linerob.turnLeft();
                currentDir = currentDir.rotate(-1);

            }
            while (!(currentPos.y == point.y)) {
                linerob.move();
                currentPos.y = currentPos.y + (currentDir == North ? 1 : -1);
            }
            linerob.putBeeper();
            // if (currentIndex == pointList.size()) {
            // break;
            // }
        }
        linerob.putBeeper();
        linerob.turnOff();
        // for (int i = 0; i < 100; i++) {

        // int prevdelay = World.delay();
        // World.setDelay(0);
        // linerob.move();
        // World.setDelay(prevdelay);
        // }

    }

    public static void main(String[] args) throws Exception {

        Diamond diamond = new Diamond();

        World.setVisible(true);
        int dimSquare = 50;
        World.setSize(dimSquare, dimSquare);
        World.setDelay(1);
        World.setTrace(true);

        List<Point> points = diamond.getLinePoints(new Point(1, 1), new Point(10, 10));
        // System.out.print(points.get(0).x);
        // diamond.drawLineFromPoints(points);

        // generate n-gon
        // essentially: anchor first right of center (at 0), generate next, snap next to
        // grid

        int sides = 8;
        int rad = 20; // must be < 1/2 dimsquare
        // make sure to check this.thickness at top
        if ((2 * rad) >= dimSquare) {
            throw new Exception("rad too large");
        }

        // center defined as half dimsquare
        Point center = new Point(dimSquare / 2, dimSquare / 2);
        // potential to screw up here a little?

        List<Point> ngonPoints = new ArrayList<>();

        // doublecount first point - that way, no special handling for last line
        for (int i = 0; i <= sides; i++) {
            // i = side# - 1
            double angle = (2 * Math.PI * i) / sides;
            double rawXDiff = Math.cos(angle) * rad;
            double rawYDiff = Math.sin(angle) * rad;
            int realX = center.x + (int) Math.round(rawXDiff);
            int realY = center.y + (int) Math.round(rawYDiff);

            ngonPoints.add(new Point(realX, realY));
        }

        // do not count last point (it is endpoint)
        for (int i = 0; i < sides; i++) {
            Point p1 = ngonPoints.get(i);
            Point p2 = ngonPoints.get(i + 1);
            System.out.println(p1.x);
            System.out.println(p1.y);
            System.out.println(p2.x);
            System.out.println(p2.y);
            diamond.drawPoints(diamond.getLinePoints(p1, p2));
        }

    }
}
