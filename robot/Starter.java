package robot;

import kareltherobot.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// https://repo1.maven.org/maven2/com/twelvemonkeys/imageio/imageio-pnm/3.12.0/imageio-pnm-3.12.0.jar

public class Starter implements Directions {

  private int[][] data;

  public int dimX;
  public int dimY;

  public void readPbm(String filename) throws IOException {
    Scanner sc = new Scanner(new File(filename));
    String format = sc.nextLine();
    if (!format.equals("P2")) {
      sc.close();
      throw new IOException("Format not supported");
    }

    // comments mess with me
    // String line = sc.nextLine();
    // while (line.startsWith("#")) {
    // line = sc.nextLine();
    // }
    String line = sc.nextLine();
    String[] dims = line.trim().split("\\s+"); // trim space
    int width, height;
    if (dims.length == 2) {
      width = Integer.parseInt(dims[0]);
      height = Integer.parseInt(dims[1]);
    } else {
      width = Integer.parseInt(dims[0]);
      height = Integer.parseInt(sc.nextLine().trim());
    }
    dimX = width;
    dimY = height;

    int maxval = Integer.parseInt(sc.nextLine().trim());

    data = new int[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        data[y][x] = sc.nextInt();
      }
    }
    sc.close();

    // pad to square
    int dimSquare = Math.max(dimX, dimY);
    if (dimX != dimY) {
      int[][] padded = new int[dimSquare][dimSquare];
      for (int y = 0; y < dimY; y++) {
        for (int x = 0; x < dimX; x++) {
          padded[y][x] = data[y][x];
        }
      }
      data = padded;
      dimX = dimSquare;
      dimY = dimSquare;
    }
  }

  // uses all 12 monkeys
  public void readPbmP4(String filename) throws IOException {
    BufferedImage img = ImageIO.read(new File(filename));
    if (img == null) {
      throw new IOException("Could not read file");
    }
    dimX = img.getWidth();
    dimY = img.getHeight();
    data = new int[dimY][dimX];
    for (int y = 0; y < dimY; y++) {
      for (int x = 0; x < dimX; x++) {
        int rgb = img.getRGB(x, y) & 0xFFFFFF;
        data[y][x] = (rgb == 0x000000) ? 1 : 0;
      }
    }
    // pad to square
    int dimSquare = Math.max(dimX, dimY);
    if (dimX != dimY) {
      int[][] padded = new int[dimSquare][dimSquare];
      for (int y = 0; y < dimY; y++) {
        for (int x = 0; x < dimX; x++) {
          padded[y][x] = data[y][x];
        }
      }
      data = padded;
      dimX = dimSquare;
      dimY = dimSquare;
    }
  }

  public static boolean containsArray(int[][] multiArray, int[] targetArray) {
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

  public static boolean containsInt(int[] miniArray, int target) {
    for (int item : miniArray) {
      if (item == target) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {

    Starter starter = new Starter();
    try {
      starter.readPbmP4("robot/data/1f928.pbm");
    } catch (IOException e) {
      System.err.println("reading error (probably file): " + e.getMessage());
      e.printStackTrace();
      return;
    } catch (Exception e) {
      System.err.println("unexpected error: " + e.getMessage());
      e.printStackTrace();
      return;
    }

    int dimSquare = Math.max(starter.dimX, starter.dimY);
    System.out.println(dimSquare);

    World.setVisible(true);
    World.setSize(dimSquare, dimSquare);
    World.setDelay(0);

    int[][] posconstruct = starter.data;

    Robot[] bots = new Robot[dimSquare];

    for (int i = 0; i < dimSquare; i++) {
      bots[i] = new Robot(1, 1 + i, North, dimSquare);
    }

    for (int j = 0; j < dimSquare; j++) {
      for (int i = 0; i < dimSquare; i++) {
        if (posconstruct[dimSquare - 1 - j][i] == 1) {
          bots[i].putBeeper();
        }
        bots[i].move();
      }
    }
  }
}
