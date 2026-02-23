package polygon;

import java.awt.geom.*; // for Point2D.Double
import java.util.ArrayList; // for ArrayList
import gpdraw.*; // for DrawingTool

public class IrregularPolygon {
    private ArrayList<Point2D.Double> myPolygon = new ArrayList<Point2D.Double>();

    // constructor
    public IrregularPolygon() {
    }

    // public methods
    public void add(Point2D.Double aPoint) {
        myPolygon.add(aPoint);
    }

    public double perimeter() {
        double p = 0;
        for (int i = 0; i < myPolygon.size(); i++) {
            p += Math.sqrt(
                    Math.pow((myPolygon.get(i).x - myPolygon.get((i + 1) % myPolygon.size()).x), 2) +
                            Math.pow((myPolygon.get(i).y - myPolygon.get((i + 1) % myPolygon.size()).y), 2));
        }
        return p;
    }

    public double area() {
        Double intermediate = 0.0;
        for (int i = 0; i < myPolygon.size(); i++) {
            intermediate += (myPolygon.get(i).x * myPolygon.get((i + 1) % myPolygon.size()).y);
            intermediate -= (myPolygon.get(i).y * myPolygon.get((i + 1) % myPolygon.size()).x);
        }
        return Math.abs(intermediate / 2);
    }

    public void draw() {
        // Wrap the DrawingTool in a try/catch to allow development without need for
        // graphics.
        try {
            // Documents: https://pavao.org/compsci/gpdraw/html/gpdraw/DrawingTool.html
            DrawingTool myDrawingTool = new DrawingTool(new SketchPad(500, 500));
            if (myPolygon.isEmpty())
                return;
            myDrawingTool.up();
            myDrawingTool.move(myPolygon.get(0).x, myPolygon.get(0).y);
            myDrawingTool.down();
            for (int i = 1; i < myPolygon.size(); i++) {
                myDrawingTool.move(myPolygon.get(i).x, myPolygon.get(i).y);
            }
            myDrawingTool.move(myPolygon.get(0).x, myPolygon.get(0).y);
        } catch (java.awt.HeadlessException e) {
            System.out.println("Exception: No graphics support available.");
        }
    }

}
