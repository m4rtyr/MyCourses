/**
 * @Author: S. Sharma <m4rtyr>
 * @Date:   2020-02-11T23:43:09-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-03-25T16:56:30-05:00
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

  private Node root;
  private int size;

  public KdTree() {
    this.root = null;
    this.size = 0;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (root == null) {
      root = new Node(p, new RectCoord(), true);
      this.size++;
    }
    else {
      insert(p, root, new RectCoord());
    }
  }

  private void insert(Point2D p, Node parent, RectCoord rc) {
    if (parent == null) return;
    if (p.equals(parent.point)) return;

    if (!parent.less(p)) {
      if (parent.useX) rc.xMax = parent.point.x();
      else rc.yMax = parent.point.y();

      if (parent.lbTree != null) insert(p, parent.lbTree, rc);
      else
      {
        parent.lbTree = new Node(p, rc, !parent.useX);
        this.size++;
      }
    } else {
      if (parent.useX) rc.xMin = parent.point.x();
      else rc.yMin = parent.point.y();

      if (parent.rtTree != null) insert(p, parent.rtTree, rc);
      else {
        parent.rtTree = new Node(p, rc, !parent.useX);
        this.size++;
      }
    }
  }

  public boolean contains(Point2D p)
  {
    return contains(p, root);
  }

  private boolean contains(Point2D p, Node n) {
    if (n == null) return false;
    else if (p.equals(n.point)) return true;

    else if (!n.less(p))
    return contains(p, n.lbTree);
    else return contains(p, n.rtTree);
  }

  public void draw() {
    draw(root);
  }

  private void draw(Node n) {
    if (n == null) return;

    draw(n.lbTree);
    draw(n.rtTree);
    n.point.draw();

  }

  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<Point2D>();
    range(q, rect, root);
    return q;
  }

  private void range(Queue<Point2D> q, RectHV rect, Node n)
  {
    if (n == null) return;
    if (rect.intersects(n.rect)) {
      if (rect.contains(n.point))
        q.enqueue(n.point);
      range(q, rect, n.lbTree);
      range(q, rect, n.rtTree);
    }
  }

  public Point2D nearest(Point2D p) {
    double minDist = Double.MAX_VALUE;
    Point2D neighbor = null;

    Stack<Node> s = new Stack<Node>();
    s.push(root);
    while (!s.isEmpty()) {
      Node n = s.pop();
      if (n == null) continue;
      if (minDist < n.rect.distanceSquaredTo(p)) continue;

      if (p.distanceSquaredTo(n.point) < minDist) {
        neighbor = n.point;
        minDist = p.distanceSquaredTo(n.point);
      }

      if (n.lbTree != null && n.lbTree.rect.contains(p)) {
        s.push(n.rtTree);
        s.push(n.lbTree);
      }
      else {
        s.push(n.lbTree);
        s.push(n.rtTree);
      }
    }
    return neighbor;

  }

  private static class Node {
    private Point2D point;
    private RectHV rect;
    private Node lbTree, rtTree;
    private boolean useX;


    public Node(Point2D point, RectCoord rc, boolean useXtoCompare) {
      this.point = point;
      this.rect = new RectHV(rc.xMin, rc.yMin, rc.xMax, rc.yMax);
      this.useX = useXtoCompare;

      this.lbTree = null;
      this.rtTree = null;
    }

    public boolean less(Point2D anotherPoint) {
      if (useX)
        return this.point.x() < anotherPoint.x();
      else
        return this.point.y() < anotherPoint.y();
    }

  }

  private static class RectCoord {
    private static final double X_MAX = 1.0;
    private static final double Y_MAX = 1.0;

    private double xMin, yMin, xMax, yMax;

    public RectCoord() {
      xMin = 0;
      yMin = 0;
      xMax = X_MAX;
      yMax = Y_MAX;
    }
  }
}
