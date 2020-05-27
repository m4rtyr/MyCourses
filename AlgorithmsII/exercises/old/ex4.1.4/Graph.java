/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-03-28T21:12:02-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-03-28T21:41:07-05:00
 */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {
  private final int V;
  private int E;
  private Bag<Integer>[] adj;

  public Graph(int V) {
    this.V = V;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int v = 0; v < V; v++)
      adj[v] = new Bag<Integer>();
  }

  public Graph(In in) {
    this(in.readInt());
    int E = in.readInt();
    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      addEdge(v, w);
    }
  }

  public Graph(Graph G) {
    this(G.V());
    for (int v = 0; v < V; v++) {
      for (int w : G.adj(v))
        adj[v].add(w);
    }
    this.E = G.E();
  }

  public int V() { return V; }
  public int E() { return E; }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    adj[w].add(v);
    E++;
  }

  public String toString() {
    String s = "";
    s += V() + " vertices, " + E() + " edges\n";
    for (int v = 0; v < V(); v++) {
      s += v + ": ";
      for (int w : adj(v)) {
        s += w + " ";
      }
      s += "\n";
    }
    return s;
  }

  public boolean hasEdge(int v, int w) {
    boolean contains = false;
    for (int x : adj[v]) {
      if (x == w) {
        contains = true;
        break;
      }
    }
    return contains;
  }

  public Iterable<Integer> adj(int v) { return adj[v]; }

  /* Test Client */
  public static void main(String[] args) {
    Graph g = new Graph(new In(args[0]));
    System.out.println("First graph: \n" + g.toString());
    Graph copy = new Graph(g);
    System.out.println("Copy graph: \n" + copy.toString());
    copy.addEdge(0, 7);
    System.out.println("Added edge 0-7");
    System.out.println("First graph: " + g.toString() +
        "\nCopy graph: \n" + copy.toString());
    System.out.println(g.hasEdge(0, 7) + " " + g.hasEdge(0, 6));
  }
}
