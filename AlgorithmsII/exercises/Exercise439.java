import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class Exercise439 {
  private final int V;
  private int E;
  private Bag<Edge>[] adj;

  public Exercise439(int V) {
    this.V = V;
    this.E = 0;
    adj = (Bag<Edge>[]) new Bag[V];
    for (int v = 0; v < V; v++)
      adj[v] = new Bag<Edge>();
  }

  public Exercise439(In in) {
    this(in.readInt());
    int E = in.readInt();
    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      double weight = in.readDouble();
      addEdge(new Edge(v, w, weight));
    }
  }

  public int V() { return V; }
  public int E() { return E; }

  public void addEdge(Edge e) {
    int v = e.either(), w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public Iterable<Edge> adj(int v)
  { return adj[v]; }

  public Iterable<Edge> edges() {
    Bag<Edge> bag = new Bag<Edge>();
    for (int v = 0; v < V; v++)
      for (Edge e : adj[v])
        if (e.other(v) > v)
          bag.add(e);
    return bag;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Exercise439 G = new Exercise439(in);
    for (Edge e : G.edges())
      System.out.println(e);
  }
}
