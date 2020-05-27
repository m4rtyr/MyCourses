import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.UF;

public class KruskalMST {

  private double weight;
  private Queue<Edge> mst;
  private MinPQ<Edge> priorityQueue;
  private UF uf;

  public KruskalMST(EdgeWeightedGraph G) {
    mst = new Queue<Edge>();
    priorityQueue = new MinPQ<Edge>();
    uf = new UF(G.V());

    for (Edge e : G.edges())
      priorityQueue.insert(e);

    while (!priorityQueue.isEmpty()) {
      Edge e = priorityQueue.delMin();
      int v = e.either(), w = e.other(v);
      if (uf.find(v) == uf.find(w))
        continue;
      uf.union(v, w);
      mst.enqueue(e);
      weight += e.weight();
    }
  }

  public Iterable<Edge> edges() {
    return mst;
  }

  public double weight() {
    return weight;
  }
}
