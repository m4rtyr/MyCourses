import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

  private int width;
  private int height;
  private boolean switched;
  private double[][] energy;
  private int[][] pixels;

  private final int TOP;
  private final int BOTTOM;

  public SeamCarver(Picture picture) {
    if (picture == null)
      throw new IllegalArgumentException();
    this.width = picture.width();
    this.height = picture.height();
    switched = false;
    energy = new double[width][height];
    pixels = new int[width][height];
    TOP = width*height;
    BOTTOM = width*height+1;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        energy[x][y] = computeEnergy(picture, x, y);
        pixels[x][y] = picture.getRGB(x, y);
      }
    }
  }

  public Picture picture() {
    int W = width, H = height;
    for (int i = 0; i < pixels.length; i++) {
      if (pixels[i][0] == -1) {
        W = i+1;
        break;
      }
    }
    for (int i = 0; i < pixels.length; i++) {
      if (pixels[0][i] == -1) {
        H = i+1;
        break;
      }
    }
    Picture p = new Picture(W, H);
    for (int x = 0; x < W; x++) {
      for (int y = 0; y < H; y++) {
        p.setRGB(x, y, pixels[x][y]);
      }
    }
    return p;
  }

  public int width() { return width; }
  public int height() { return height; }

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
    switched = false;
    width = w;
    height = h;
    return seam;
  }

  public int[] findVerticalSeam() {
    int i = 0;
    int[] seam = new int[height];
    for (int v : computePath()) {
      int[] coords = convert2D(v);
      seam[i++] = coords[0];
    }
    return seam;
  }

  public void removeHorizontalSeam(int[] seam) {
    if (seam == null || seam.length != width || width < 2)
      throw new IllegalArgumentException();
    for (int i = 0; i < seam.length; i++) {
      if (!checkBounds(i, seam[i]))
        throw new IllegalArgumentException();
      if (i < seam.length-1 && Math.abs(seam[i] - seam[i+1]) > 1)
        throw new IllegalArgumentException();
    }

    for (int i = 0; i < seam.length; i++) {
      for (int k = i+1; k < width; k++) {
        pixels[k-1][seam[i]] = pixels[k][seam[i]];
      }
      pixels[width-1][seam[i]] = -1;
    }
  }

  public void removeVerticalSeam(int[] seam) {
    if (seam == null || seam.length != height || height < 2)
      throw new IllegalArgumentException();
    for (int i = 0; i < seam.length; i++) {
      if (!checkBounds(seam[i], i))
        throw new IllegalArgumentException();
      if (i < seam.length-1 && Math.abs(seam[i] - seam[i+1]) > 1)
        throw new IllegalArgumentException();
    }
    for (int i = 0; i < seam.length; i++) {
      for (int k = i+1; k < height; k++) {
        pixels[seam[i]][k-1] = pixels[seam[i]][k];
      }
      pixels[seam[i]][height-1] = -1;
    }
  }

  public static void main(String[] args) {
    SeamCarver s = new SeamCarver(new Picture(args[0]));
    StdOut.println(s.energy(1, 2));
  }

  private boolean checkBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  private int convert1D(int x, int y) {
    return y*width + x;
  }

  private int[] convert2D(int v) {
    int[] coords = new int[2];
    int y = v / width;
    int x = v - y*width;
    coords[0] = x;
    coords[1] = y;
    return coords;
  }

  private double computeDelta(Picture picture, int x1, int y1, int x2, int y2) {
    Color A = picture.get(x1, y1);
    Color B = picture.get(x2, y2);

    int red = A.getRed() - B.getRed();
    int green = A.getGreen() - B.getGreen();
    int blue = A.getBlue() - B.getBlue();
    return red*red + green*green + blue*blue;
  }

  private double computeEnergy(Picture picture, int x, int y) {
    if (x == 0 || x == width-1 || y == 0 || y == height-1)
      return 1000.0;
    double deltaX = computeDelta(picture, x-1, y, x+1, y);
    double deltaY = computeDelta(picture, x, y-1, x, y+1);
    return Math.sqrt(deltaX + deltaY);
  }

  private Stack<Integer> computePath() {
    double[] dist = new double[width*height+2];
    int[] edgeTo = new int[width*height+2];
    for (int i = 0; i < dist.length; i++)
      dist[i] = Double.POSITIVE_INFINITY;
    dist[TOP] = 0.0;
    for (int v : topologicalSort()) {
      for (int w : getNeighbors(v)) {
        if (dist[w] > dist[v] + getWeight(v, w)) {
          dist[w] = dist[v] + getWeight(v, w);
          edgeTo[w] = v;
        }
      }
    }

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

    double venergy = !switched ? energy[vcoords[0]][vcoords[1]] : energy[vcoords[1]][vcoords[0]];
    double wenergy = !switched ? energy[wcoords[0]][wcoords[1]] : energy[wcoords[1]][wcoords[0]];
    return venergy + wenergy;
  }

  private Stack<Integer> topologicalSort() {
    Stack<Integer> reversePost = new Stack<Integer>();
    boolean[] marked = new boolean[width*height+2];
    dfs(marked, reversePost, TOP);
    return reversePost;
  }

  private void dfs(boolean[] marked, Stack<Integer> s, int v) {
    marked[v] = true;
    for (int w : getNeighbors(v))
      if (!marked[w]) dfs(marked, s, w);
    s.push(v);
  }

  private Queue<Integer> getNeighbors(int v) {
    Queue<Integer> neighbors = new Queue<Integer>();
    if (v == TOP) {
      for (int x = 0; x < width; x++)
        neighbors.enqueue(convert1D(x, 0));
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

}
