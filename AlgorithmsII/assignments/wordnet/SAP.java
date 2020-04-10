/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-07T15:27:01-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-10T11:27:36-05:00
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

  private Digraph G;

  private boolean inBounds(int v) {
    return v >= 0 && v < G.V();
  }

  private int[] sap(int v, int w) {
    int[] tuple = new int[2];
    int min = Integer.MAX_VALUE, ancestor = -1;
    BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(G, v);
    BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(G, w);
    for (int i = 0; i < G.V(); i++) {
      if (bv.hasPathTo(i) && bw.hasPathTo(i)) {
        int length = bv.distTo(i) + bw.distTo(i);
        if (min > length) {
          ancestor = i;
          min = length;
        }
      }
    }
    tuple[0] = ancestor;
    tuple[1] = (ancestor == -1) ? -1 : min;
    return tuple;
  }

  public SAP(Digraph G) {
    this.G = new Digraph(G);
  }

  public int length(int v, int w) {
    if (!inBounds(v) || !inBounds(w))
      throw new IllegalArgumentException();
    return sap(v, w)[1];
  }

  public int ancestor(int v, int w) {
    if (!inBounds(v) || !inBounds(w))
      throw new IllegalArgumentException();
    return sap(v, w)[0];
  }

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null)
      throw new IllegalArgumentException();
    for (int x : v)
      if (!inBounds(x))
        throw new IllegalArgumentException();
    for (int y : w)
      if (!inBounds(y))
        throw new IllegalArgumentException();
    int min = Integer.MAX_VALUE, ancestor = -1;
    BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(G, v);
    BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(G, w);
    for (int i = 0; i < G.V(); i++) {
      if (bv.hasPathTo(i) && bw.hasPathTo(i)) {
        int length = bv.distTo(i) + bw.distTo(i);
        if (min > length) {
          ancestor = -1;
          min = length;
        }
      }
    }
    return (ancestor == -1) ? -1 : min;
  }

  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null)
      throw new IllegalArgumentException();
    for (int x : v)
      if (!inBounds(x))
        throw new IllegalArgumentException();
    for (int y : w)
      if (!inBounds(y))
        throw new IllegalArgumentException();
    int minAn = -1, minLen = -1;
    for (int x : v) {
      for (int y : w) {
        int[] tuple = sap(x, y);
        if (minLen > tuple[1] || minLen == -1) {
          minAn = tuple[0];
          minLen = tuple[1];
        }
      }
    }
    return minAn;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }

    /* while (!StdIn.isEmpty()) {
      Queue<Integer> A = new Queue<Integer>();
      Queue<Integer> B = new Queue<Integer>();
      String[] list = StdIn.readLine().split(" ");
      for (String v : list[0].split(","))
        A.enqueue(Integer.parseInt(v));
      for (String w : list[1].split(","))
        B.enqueue(Integer.parseInt(w));
      int length   = sap.length(A, B);
      int ancestor = sap.ancestor(A, B);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    } */
  }
}
