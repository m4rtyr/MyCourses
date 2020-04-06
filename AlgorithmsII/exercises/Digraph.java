/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-05T11:59:43-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-05T21:23:12-05:00
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class Digraph {

  private int V;
  private int E;
  private Bag<Integer>[] adj;

  public Digraph(int V) {
    this.V = V;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int i = 0; i < V; i++)
      adj[i] = new Bag<Integer>();
  }

  public Digraph(In in) {
    this(in.readInt());
    int E = in.readInt();
    for (int i = 0; i < E; i++) {
      int v = in.readInt(), w = in.readInt();
      addEdge(v, w);
    }
  }

  public int V() { return V; }
  public int E() { return E; }

  public void addEdge(int v, int w) {
    adj[v].add(w);
    E++;
  }

  Iterable<Integer> adj(int v) {
    return adj[v];
  }

  public Digraph reverse() {
    Digraph d = new Digraph(V);
    for (int v = 0; v < V; v++) {
      for (int w : adj[v]) {
        d.addEdge(w, v);
      }
    }
    return d;
  }

  public String toString() {
    String s = "";
    s += V() + " vertices, " + E() + " edges\n";
    for (int v = 0; v < V; v++) {
      s += v + ": ";
      for (int w : adj[v]) {
        s += w + " ";
      }
      s += "\n";
    }
    return s;
  }

  public static void main(String[] args) {
    Digraph G = new Digraph(new In(args[0]));
    System.out.println(G);
    System.out.println(G.reverse());
  }
}
