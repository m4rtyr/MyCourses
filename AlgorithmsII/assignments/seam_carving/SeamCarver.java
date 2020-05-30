import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

  private Picture picture;

  private int computeDifferences(int x1, int y1, int x2, int y2) {
    Color A = picture.get(x1, y1);
    Color B = picture.get(x2, y2);

    int red = A.getRed() - B.getRed();
    int green = A.getGreen() - B.getGreen();
    int blue = A.getBlue() - B.getBlue();
    return red*red + green*green + blue*blue;
  }

  public SeamCarver(Picture picture) {
    this.picture = new Picture(picture);
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
    int deltaX = computeDifferences(x-1, y, x+1, y);
    int deltaY = computeDifferences(x, y-1, x, y+1);
    return Math.sqrt(deltaX + deltaY);
  }

  public int[] findHorizontalSeam() {
    return null;
  }

  public int[] findVerticalSeam() {
    return null;
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
    StdOut.printf("%.2f\n", s.energy(1, 1));
  }
}
