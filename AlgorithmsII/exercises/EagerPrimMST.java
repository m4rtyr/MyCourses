import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class EagerPrimMST {

  private double weight;
  private boolean[] marked;
  private Edge[] edgeTo;
  private double[] distTo;
  private IndexMinPQ<Edge> priorityQueue;

  public EagerPrimMST(EdgeWeightedGraph G) {
    marked = new boolean[G.V()];
    edgeTo = new Edge[G.V()];
    distTo = new double[G.V()];
    for (int i = 0; i < G.V(); i++)
      distTo[i] = Double.POSITIVE_INFINITY;
    priorityQueue = new IndexMinPQ<Edge>(G.V());
    distTo[0] = 0;
    visit(G, 0);

    while (!priorityQueue.isEmpty()) {
      int v = priorityQueue.delMin();
      visit(G, v);
    }

    for (double weight : distTo)
      this.weight += weight;
  }

  private void visit(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (Edge e : G.adj(v)) {
      int w = e.other(v);
      if (marked[w])
        continue;
      if (!priorityQueue.contains(w)) {
        priorityQueue.insert(w, e);
        edgeTo[w] = e;
        distTo[w] = e.weight();
      } else if (e.weight() < distTo[w]) {
        priorityQueue.changeKey(w, e);
        edgeTo[w] = e;
        distTo[w] = e.weight();
      }
    }
  }

  public Iterable<Edge> edges() {
    Queue<Edge> queue = new Queue<Edge>();
    for (Edge e : edgeTo) {
      if (e == null)
        continue;
      queue.enqueue(e);
    }
    return queue;
  }

  public double weight() {
    return weight;
  }
}
