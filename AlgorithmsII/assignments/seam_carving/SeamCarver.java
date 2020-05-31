import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

  private int width;
  private int height;
  private boolean switched;
  private int[][] pixels;
  private double[][] energy;
  private Picture picture;

  private final int TOP;
  private final int BOTTOM;

  /* Energy Helping Functions */

  private int computeDifferences(int x1, int y1, int x2, int y2) {
    Color A = picture.get(x1, y1);
    Color B = picture.get(x2, y2);

    int red = A.getRed() - B.getRed();
    int green = A.getGreen() - B.getGreen();
    int blue = A.getBlue() - B.getBlue();
    return red*red + green*green + blue*blue;
  }

  private double computeEnergy(int x, int y) {
    if (x == 0 || x == width-1 || y == 0 || y == height-1)
      return 1000.0;
    int deltaX = computeDifferences(x-1, y, x+1, y);
    int deltaY = computeDifferences(x, y-1, x, y+1);
    return Math.sqrt(deltaX + deltaY);
  }

  /* General Helper Functions */

  private boolean checkBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  private int convert1D(int x, int y) {
    return y*width + x;
  }

  private int[] convert2D(int c) {
    int[] coords = new int[2];

    int y = c / width;
    int x = c - y*width;

    coords[0] = x;
    coords[1] = y;
    return coords;
  }

  private Queue<Integer> getNeighbors(int v) {
    Queue<Integer> neighbors = new Queue<Integer>();
    if (v == TOP || v == BOTTOM) {
      if (v == TOP) {
        for (int x = 0; x < width; x++)
          neighbors.enqueue(convert1D(x, 0));
      }
      return neighbors;
    }
    int[] coords = convert2D(v);
    int x = coords[0], y = coords[1];
    if (y == height-1) {
      neighbors.enqueue(BOTTOM);
      return neighbors;
    }
    if (checkBounds(x, y+1))
      neighbors.enqueue(convert1D(x, y+1));
    if (checkBounds(x-1, y+1))
      neighbors.enqueue(convert1D(x-1, y+1));
    if (checkBounds(x+1, y+1))
      neighbors.enqueue(convert1D(x+1, y+1));
    return neighbors;
  }

  /* DFS/Topological implementation */

  private class LittleDFS {

    private boolean[] marked;
    private Stack<Integer> reversePost;

    public LittleDFS() {
      reversePost = new Stack<Integer>();
      marked = new boolean[width*height+2];
      dfs(TOP);
    }

    private void dfs(int v) {
      marked[v] = true;
      for (int w : getNeighbors(v)) {
        if (!marked[w]) dfs(w);
      }
      reversePost.push(v);
    }

    public Iterable<Integer> reversePostorder() {
      return reversePost;
    }
  }

  private class LittleAcyclicSP {

    private int[] edgeTo;
    private double[] distTo;

    public LittleAcyclicSP() {
      edgeTo = new int[width*height+2];
      distTo = new double[width*height+2];

      for (int v = 0; v < distTo.length; v++)
        distTo[v] = Double.POSITIVE_INFINITY;
      distTo[TOP] = 0.0;
      LittleDFS order = new LittleDFS();
      for (int v : order.reversePostorder()) {
        relax(v);
      }
    }

    public Iterable<Integer> pathToBottom() {
      Stack<Integer> path = new Stack<Integer>();
      for (int x = edgeTo[BOTTOM]; x != TOP; x = edgeTo[x])
        path.push(x);
      return path;
    }

    private double getWeight(int v, int w) {
      if (v == TOP || w == BOTTOM)
        return 1000.0;
      int[] vcoords = convert2D(v);
      int[] wcoords = convert2D(w);
      if (switched) {
        int tmp = vcoords[0];
        vcoords[0] = vcoords[1];
        vcoords[1] = tmp;
        tmp = wcoords[0];
        wcoords[0] = wcoords[1];
        wcoords[1] = tmp;
      }
      double venergy = energy[vcoords[0]][vcoords[1]];
      double wenergy = energy[wcoords[0]][wcoords[1]];
      return venergy + wenergy;
    }

    private void relax(int v) {
      for (int w : getNeighbors(v)) {
        double weight = distTo[v] + getWeight(v, w);
        if (distTo[w] > weight) {
          distTo[w] = weight;
          edgeTo[w] = v;
        }
      }
    }
  }

  /* API code */

  public SeamCarver(Picture picture) {
    if (picture == null)
      throw new IllegalArgumentException();
    this.picture = new Picture(picture);
    this.width = picture.width();
    this.height = picture.height();
    pixels = new int[width][height];
    energy = new double[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[x][y] = picture.getRGB(x, y);
        energy[x][y] = computeEnergy(x, y);
      }
    }
    switched = false;
    TOP = width*height;
    BOTTOM = width*height+1;
  }

  public Picture picture() {
    return picture;
  }

  public int width() {
    return width;
  }

  public int height() {
    return height;
  }

  public double energy(int x, int y) {
    if (!checkBounds(x, y))
      throw new IllegalArgumentException();
    return energy[x][y];
  }

  public int[] findHorizontalSeam() {

    int w = width;
    int h = height;

    width = height;
    height = w;
    switched = true;
    int[] seam = findVerticalSeam();
    width = w;
    height = h;
    switched = false;
    return seam;
  }

  public int[] findVerticalSeam() {
    int[] seam = new int[height];
    LittleAcyclicSP sp = new LittleAcyclicSP();
    Stack<Integer> s = (Stack<Integer>) sp.pathToBottom();

    int i = 0;
    for (int v : s) {
      int[] coords = convert2D(v);
      seam[i++] = coords[0];
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
