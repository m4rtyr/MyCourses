/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-03-26T16:02:16-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-03-26T16:32:06-05:00
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BFS {

  private int s;
  private int count;
  private int[] edgeTo;
  private boolean[] marked;
  private Queue<Integer> tomark;

  public BFS(Graph G, int s) {
    this.s = s;
    edgeTo = new int[G.V()];
    marked = new boolean[G.V()];
    tomark = new Queue<Integer>();
    bfs(G, s);
  }

  private void bfs(Graph G, int s) {
    tomark.enqueue(s);
    while (!tomark.isEmpty()) {
      int v = tomark.dequeue();
      if (marked[v])
        continue;
      marked[v] = true;
      ++count;
      for (int w : G.adj(v)) {
        if (!marked[w]) {
          tomark.enqueue(w);
          edgeTo[w] = v;
        }
      }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v))
      return null;
    Stack<Integer> path = new Stack<Integer>();
    for (int x = v; x != s; x = edgeTo[x])
      path.push(x);
    path.push(s);
    return path;
  }

  public int count() {
    return count;
  }

  public static void main(String[] args) {
    Graph G = new Graph(new In(args[0]));
    int s = Integer.parseInt(args[1]);
    BFS search = new BFS(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.hasPathTo(v)) {
        StdOut.print(s + " to " + v + ": ");
        StdOut.println(search.pathTo(v));
      }
    }
    StdOut.println();
    if (search.count() != G.V())
      StdOut.printf("not ");
    StdOut.println("connected");
  }
}
