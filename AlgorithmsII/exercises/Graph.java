/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-03-25T19:52:21-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
<<<<<<< HEAD
 * @Last modified time: 2020-03-25T21:30:05-05:00
=======
 * @Last modified time: 2020-03-25T21:39:45-05:00
>>>>>>> master
 */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {

  private Bag<Integer>[] graph;

  public Graph(int V) {
    if (V <= 0)
      throw new IllegalArgumentException();
    graph = (Bag<Integer>[]) new Bag[V];
    for (int i = 0; i < graph.length; i++) {
      graph[i] = new Bag<Integer>();
    }
  }

  public Graph(In in) {
    if (in == null)
      throw new IllegalArgumentException();
    int V = in.readInt();
    int E = in.readInt();
    graph = (Bag<Integer>[]) new Bag[V];
    for (int i = 0; i < E; i++) {
      int v = in.readInt(), w = in.readInt();
      if (graph[v] == null)
        graph[v] = new Bag<Integer>();
      if (graph[w] == null)
        graph[w] = new Bag<Integer>();
      addEdge(v, w);
    }
  }

  public int V() {
    return graph.length;
  }

  public int E() {
    int edges = 0;
    for (int i = 0; i < graph.length; i++) {
      edges += graph[i].size();
    }
    return edges/2;
  }

  public void addEdge(int v, int w) {
    if ((v < 0 || v >= graph.length) || (w < 0 || w >= graph.length))
      throw new IllegalArgumentException();
    graph[v].add(w);
    graph[w].add(v);
  }

  public Iterable<Integer> adj(int v) {
    if (v < 0 || v >= graph.length)
      throw new IllegalArgumentException();
    return graph[v];
  }

  public String toString() {
    String s = "";
    s += V() + " vertices, " + E() + " edges\n";
    for (int v = 0; v < graph.length; v++) {
      s += v + ": ";
      for (int w : graph[v]) {
        s += w + " ";
      }
      s += "\n";
    }
    return s;
  }

  /* Test Client */
  public static void main(String[] args) {
    Graph g = new Graph(new In(args[0]));
    System.out.println(g.toString());
  }

}
