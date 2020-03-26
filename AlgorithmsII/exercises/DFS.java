/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-03-25T22:03:10-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
<<<<<<< HEAD
 * @Last modified time: 2020-03-26T16:20:58-05:00
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

=======
 * @Last modified time: 2020-03-25T22:31:50-05:00
 */

>>>>>>> master
public class DFS {

  private int s, deg;
  private Graph g;
  private boolean[] visited;

  public DFS(Graph g, int s) {
<<<<<<< HEAD
    Bag<Integer> adj = (Bag<Integer>) g.adj(s);
    this.g = g;
    this.s = s;
    this.deg = adj.size();
=======
    this.g = g;
    this.s = s;
    this.deg = g.adj(s).size();
>>>>>>> master
    visited = new boolean[g.V()];
  }

  private void search(int v) {
<<<<<<< HEAD
    visited[v] = true;
    for (int w : g.adj(v))
      if (!visited[w])
=======
    marked[v] = true;
    for (int w : g.adj(v))
      if (!marked[w])
>>>>>>> master
        search(w);
  }

  public void search() {
    search(s);
  }

  public int count() {
    return deg;
  }

  public boolean marked(int v) {
    return visited[v];
  }
<<<<<<< HEAD

  public static void main(String[] args) {
    Graph G = new Graph(new In(args[0]));
    int s = Integer.parseInt(args[1]);
    DFS search = new DFS(G, s);
    search.search();
    for (int v = 0; v < G.V(); v++)
      if (search.marked(v))
        StdOut.print(v + " ");
    StdOut.println();
    if (search.count() != G.V())
      StdOut.printf("not ");
    StdOut.println("connected");
  }
=======
>>>>>>> master
}
