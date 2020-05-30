import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.AcyclicSP;

public class SeamCarver {

  private Picture picture;
  private EdgeWeightedDigraph VERTICAL;
  private EdgeWeightedDigraph HORIZONTAL;

  private final int TOP;
  private final int BOTTOM;
  private final int LEFT;
  private final int RIGHT;

  private int computeDifferences(int x1, int y1, int x2, int y2) {
    Color A = picture.get(x1, y1);
    Color B = picture.get(x2, y2);

    int red = A.getRed() - B.getRed();
    int green = A.getGreen() - B.getGreen();
    int blue = A.getBlue() - B.getBlue();
    return red*red + green*green + blue*blue;
  }

  private boolean checkBounds(int x, int y) {
    return x >= 0 && x < picture.width() && y >= 0 && y < picture.height();
  }

  private int convert1D(int x, int y) {
    return y*picture.width() + x;
  }

  private int[] convert2D(int c) {
    int[] coords = new int[2];

    int y = c / picture.width();
    int x = c - y*picture.width();

    coords[0] = x;
    coords[1] = y;
    return coords;
  }

  private double[][] getNeighborsVertical(int x, int y) {
    int i = 0;
    double[][] neighbors = new double[3][2];
    for (int k = 0; k < neighbors.length; k++)
      neighbors[k][0] = -1;
    if (checkBounds(x, y+1)) {
      neighbors[i][0] = (double) convert1D(x, y+1);
      neighbors[i++][1] = energy(x, y+1);
    }
    if (checkBounds(x-1, y+1)) {
      neighbors[i][0] = (double) convert1D(x-1, y+1);
      neighbors[i++][1] = energy(x-1, y+1);
    }
    if (checkBounds(x+1, y+1)) {
      neighbors[i][0] = (double) convert1D(x+1, y+1);
      neighbors[i++][1] = energy(x+1, y+1);
    }
    return neighbors;
  }

  private double[][] getNeighborsHorizontal(int x, int y) {
    int i = 0;
    double[][] neighbors = new double[3][2];
    for (int k = 0; k < neighbors.length; k++)
      neighbors[k][0] = -1;
    if (checkBounds(x+1, y)) {
      neighbors[i][0] = (double) convert1D(x+1, y);
      neighbors[i++][1] = energy(x+1, y);
    }
    if (checkBounds(x+1, y-1)) {
      neighbors[i][0] = (double) convert1D(x+1, y-1);
      neighbors[i++][1] = energy(x+1, y-1);
    }
    if (checkBounds(x+1, y+1)) {
      neighbors[i][0] = (double) convert1D(x+1, y+1);
      neighbors[i++][1] = energy(x+1, y+1);
    }
    return neighbors;
  }

  public SeamCarver(Picture picture) {
    if (picture == null)
      throw new IllegalArgumentException();
    this.picture = new Picture(picture);
    VERTICAL = new EdgeWeightedDigraph(picture.width()*picture.height()+2);
    HORIZONTAL = new EdgeWeightedDigraph(picture.width()*picture.height()+2);

    int lastPixel = convert1D(picture.width()-1, picture.height()-1);
    TOP = lastPixel+1;
    BOTTOM = lastPixel+2;
    LEFT = TOP;
    RIGHT = BOTTOM;

    for (int y = 0; y < picture.height(); y++) {
      for (int x = 0; x < picture.width(); x++) {
        int root = convert1D(x, y);
        double rootEnergy = energy(x, y);
        double[][] vertNeighbors = getNeighborsVertical(x, y);
        double[][] horNeighbors = getNeighborsHorizontal(x, y);
        for (int i = 0; i < vertNeighbors.length; i++) {
          if (vertNeighbors[i][0] >= 0) {
            DirectedEdge e = new DirectedEdge(root, (int)vertNeighbors[i][0], rootEnergy + vertNeighbors[i][1]);
            VERTICAL.addEdge(e);
          }
        }

        for (int i = 0; i < horNeighbors.length; i++) {
          if (horNeighbors[i][0] >= 0) {
            DirectedEdge e = new DirectedEdge(root, (int)horNeighbors[i][0], rootEnergy + horNeighbors[i][1]);
            HORIZONTAL.addEdge(e);
          }
        }
      }
    }

    for (int x = 0; x < picture.width(); x++) {
      VERTICAL.addEdge(new DirectedEdge(TOP, convert1D(x, 0), 0.0));
      VERTICAL.addEdge(new DirectedEdge(convert1D(x, picture.height()-1), BOTTOM, 0.0));
    }

    for (int y = 0; y < picture.height(); y++) {
      HORIZONTAL.addEdge(new DirectedEdge(LEFT, convert1D(0, y), 0.0));
      HORIZONTAL.addEdge(new DirectedEdge(convert1D(picture.width()-1, y), RIGHT, 0.0));
    }
  }

  public Picture picture() {
    return picture;
  }

  public int width() {
    return picture.width();
  }

  public int height() {
    return picture.height();
  }

  public double energy(int x, int y) {
    if (!checkBounds(x, y))
      throw new IllegalArgumentException();
    if (x == 0 || x == picture.width()-1 || y == 0 || y == picture.height()-1)
      return 1000.0;
    int deltaX = computeDifferences(x-1, y, x+1, y);
    int deltaY = computeDifferences(x, y-1, x, y+1);
    return Math.sqrt(deltaX + deltaY);
  }

  public int[] findHorizontalSeam() {
    int[] seam = new int[picture.width()];
    AcyclicSP sp = new AcyclicSP(HORIZONTAL, LEFT);
    int i = 0;
    for (DirectedEdge e : sp.pathTo(RIGHT)) {
      if (e.to() == RIGHT)
        break;
      int[] coords = convert2D(e.to());
      seam[i++] = coords[1]; // the y coordinate
    }
    return seam;
  }

  public int[] findVerticalSeam() {
    int[] seam = new int[picture.height()];
    AcyclicSP sp = new AcyclicSP(VERTICAL, TOP);

    int i = 0;
    for (DirectedEdge e : sp.pathTo(BOTTOM)) {
      if (e.to() == BOTTOM)
        break;
      int[] coords = convert2D(e.to());
      seam[i++] = coords[0]; // the x coordinate
    }
    return seam;
  }

  public void removeHorizontalSeam(int[] seam) {
    return;
  }

  public void removeVerticalSeam(int[] seam) {
    return;
  }

  public static void main(String[] args) {
    Picture pic = new Picture(args[0]);
    SeamCarver s = new SeamCarver(pic);
    int i = 0;
    for (int x : s.findVerticalSeam())
      StdOut.printf("%d, %d\n", x, i++);

    i = 0;
    for (int y : s.findHorizontalSeam())
      StdOut.printf("%d, %d\n", i++, y);
  }
}
