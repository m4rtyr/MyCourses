/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-02T17:12:20-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-05T11:38:14-05:00
 */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DFS {

  private boolean[] marked;
  private int[] edgeTo;
  private final int s;

  public DFS(Graph G, int s) {
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];
    this.s = s;
    dfs(G, s);
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
        dfs(G, w);
      }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public int findRemovableVertex() {
    for (int i = 0; i < marked.length; i++) {
      if (hasPathTo(i)) {
        Stack<Integer> path = pathTo(i);
        if path.size() >= 2 {
          while (path.size() != 1)
            path.pop();
          return path.pop();
        }
      }
    }
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

  public static void main(String[] args) {
    Graph G = new Graph(new In(args[0]));
    int s = Integer.parseInt(args[1]);
    DFS search = new DFS(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.hasPathTo(v)) {
        StdOut.print(s + " to " + v + ": ");
        StdOut.println(search.pathTo(v));
      }
    }
  }
}
