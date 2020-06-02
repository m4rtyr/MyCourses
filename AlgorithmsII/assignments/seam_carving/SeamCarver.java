

import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.IndexMinPQ;

public class SeamCarver {
    private int[][] pixels;
    private int width, height;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        pixels = new int[width][height];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                pixels[x][y] = picture.get(x, y).getRGB();
            }
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                pic.set(x, y, new Color(pixels[x][y]));
            }
        return pic;
    }

    // width  of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y in current picture
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
            throw new IllegalArgumentException();

        if (x == 0 || x == width()-1 || y == 0 || y == height()-1)
            return 1000.0;
        return Math.sqrt(squareOfXGradient(x, y) + squareOfYGradient(x, y));
    }

    // sequence of indices for horizontal seam in current picture
    public int[] findHorizontalSeam() {
        // construct energy matrix by H x W
        double[][] energyMatrix = toEnergyMatrix(height, width, true);
        return findSeam(energyMatrix);
    }

    // sequence of indices for vertical   seam in current picture
    public int[] findVerticalSeam() {
        // construct energy matrix by W x H
        double[][] energyMatrix = toEnergyMatrix(width, height, false);
        return findSeam(energyMatrix);
    }

    private int[] findSeam(double[][] eMatrix) {
        int W = eMatrix.length;
        int H = eMatrix[0].length;

        // construst energyTo matrix
        double[][] energyTo = new double[W][H];
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++) {
                if (y == 0) energyTo[x][y] = 1000.0;
                else energyTo[x][y] = Double.POSITIVE_INFINITY;
            }

        int[] seam = new int[H];
        int[][] edgeTo = new int[W][H];
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(W);

        for (int y = 0; y < H - 1; y++)
            for (int x = 0; x < W; x++)
                for (int k = x-1; k <= x+1; k++)
                    if (k >= 0 && k < W)
                        if (energyTo[k][y+1] > energyTo[x][y] + eMatrix[k][y+1]) {
                            energyTo[k][y+1] = energyTo[x][y] + eMatrix[k][y+1];
                            edgeTo[k][y+1] = xyTo1D(x, y, eMatrix);
                        }
        for (int x = 0; x < W; x++)
            pq.insert(x, energyTo[x][H-1]);
        seam[H-1] = pq.minIndex();

        // back-track
        for (int y = H-1; y > 0; y--)
            seam[y-1] = edgeTo[seam[y]][y] % W;
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width || height <= 1)
            throw new java.lang.IllegalArgumentException();
        checkSeam(seam, false);

        int[][] copy = new int[width][height-1];

        for (int x = 0; x < width; x++) {
            System.arraycopy(pixels[x], 0, copy[x], 0, seam[x]);
            System.arraycopy(pixels[x], seam[x]+1, copy[x], seam[x], height-seam[x]-1);
        }

        height--;
        pixels = copy;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height || width <= 1)
            throw new java.lang.IllegalArgumentException();
        checkSeam(seam, true);
        int[][] copy = new int[width-1][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x < seam[y]) copy[x][y] = pixels[x][y];
                else if (x > seam[y]) copy[x-1][y] = pixels[x][y];
            }
        }

        width--;
        pixels = copy;
    }

    private void checkSeam(int[] a, boolean vertical) {
        for (int i = 1; i < a.length; i++) {
            if (Math.abs(a[i - 1] - a[i]) > 1)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < a.length; i++) {
          if (vertical && (a[i] < 0 || a[i] > width-1))
            throw new IllegalArgumentException();
          else if (!vertical && (a[i] < 0 || a[i] > height-1))
            throw new IllegalArgumentException();
        }
    }

    private double squareOfXGradient(int x, int y) {
        Color c1 = new Color(pixels[x-1][y]);
        Color c2 = new Color(pixels[x+1][y]);
        double r = Math.abs(c1.getRed() - c2.getRed());
        double g = Math.abs(c1.getGreen() - c2.getGreen());
        double b = Math.abs(c1.getBlue() - c2.getBlue());
        return r*r + g*g + b*b;
    }

    private double squareOfYGradient(int x, int y) {
        Color c1 = new Color(pixels[x][y-1]);
        Color c2 = new Color(pixels[x][y+1]);
        double r = Math.abs(c1.getRed() - c2.getRed());
        double g = Math.abs(c1.getGreen() - c2.getGreen());
        double b = Math.abs(c1.getBlue() - c2.getBlue());
        return r*r + g*g + b*b;
    }

    private double[][] toEnergyMatrix(int W, int H, boolean isTranspose)
    {
        double[][] result = new double[W][H];
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++) {
                if (isTranspose) result[x][y] = energy(y, x);
                else result[x][y] = energy(x, y);
            }

        return result;
    }

    private int xyTo1D(int x, int y, double[][] m) {
        return y * m.length + x;
    }
}
