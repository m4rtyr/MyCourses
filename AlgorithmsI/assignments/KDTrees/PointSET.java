/**
 * @Author: S. Sharma <m4rtyr>
 * @Date:   2020-02-11T23:43:12-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-03-25T16:39:41-05:00
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.TreeSet;

public class PointSET {

  private TreeSet<Point2D> tree;

  public PointSET() {
    tree = new TreeSet<Point2D>();
  }

  public boolean isEmpty() {
    return tree.size() == 0;
  }

  public int size() {
    return tree.size();
  }

  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    tree.add(p);
  }

  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    return tree.contains(p);
  }

  public void draw() {
    for (Point2D point : tree)
      point.draw();
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();
    Queue<Point2D> q = new Queue<Point2D>();
    for (Point2D point : tree)
      if (rect.contains(point))
        q.enqueue(point);
    return q;
  }

  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    double min = Double.MAX_VALUE;
    Point2D minPoint = null;
    for (Point2D point : tree) {
      if (p.equals(point)) {
        continue;
      }
      double distance = p.distanceTo(point);
      if (distance < min) {
        minPoint = p;
        min = distance;
      }
    }
    return minPoint;
  }
}
