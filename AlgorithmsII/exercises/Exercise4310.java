import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;

public class Exercise4310 {

  private final int V;
  private int E;
  private Edge[][] edges;

  private boolean validateVertex(int v) {
    return v >= 0 && v < V;
  }

  public Exercise4310(int V) {
    this.V = V;
    this.E = 0;
    edges = new Edge[V][V];
  }

  public Exercise4310(In in) {
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
    edges[v][w] = e;
    edges[w][v] = e;
    E++;
  }

  public Iterable<Edge> adj(int v) {
    if (!validateVertex(v))
      throw new IllegalArgumentException();
    Queue<Edge> adjacent = new Queue<Edge>();
    for (Edge e : edges[v])
      adjacent.enqueue(e);
    return adjacent;
  }

  public Iterable<Edge> edges() {
    Queue<Edge> all = new Queue<Edge>();
    for (int v = 0; v < V; v++)
      for (Edge e : edges[v])
        if (e.other(v) > v)
          all.enqueue(e);
    return all;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Exercise439 G = new Exercise439(in);
    for (Edge e : G.edges())
      System.out.println(e);
  }
}
