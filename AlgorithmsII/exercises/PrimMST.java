/* Not implemented for time's sake */
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class PrimMST {

  private double weight;
  private boolean[] marked;
  private Queue<Edge> edgeTo;
  private MinPQ<Edge> priorityQueue;

  public PrimMST(EdgeWeightedGraph G) {
    marked = new boolean[G.V()];
    edgeTo = new Queue<Edge>();
    priorityQueue = new MinPQ<Edge>();

    for (Edge e : G.adj(0))
      priorityQueue.insert(e);
    marked[0] = true;
    while (!priorityQueue.isEmpty()) {
      Edge crossingEdge = priorityQueue.delMin();
      int v = crossingEdge.either();
      int w = crossingEdge.other(v);
      if (marked[v] && marked[w])
        continue;

      int p = !marked[v] ? v : w;
      marked[p] = true;
      for (Edge e : G.adj(p))
        priorityQueue.insert(e);
      edgeTo.enqueue(crossingEdge);
      weight += crossingEdge.weight();
    }
  }

  Iterable<Edge> edges() {
    return edgeTo;
  }

  double weight() {
    return weight;
  }
}
