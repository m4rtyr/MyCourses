/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-02T16:59:17-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-02T17:23:34-05:00
 */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class DFSNonRecursive {

  private int s;
  private int[] edgeTo;
  private boolean[] marked;

  public DFSNonRecursive(Graph G, int s) {
    this.s = s;
    edgeTo = new int[G.V()];
    marked = new boolean[G.V()];
    dfs(G);
  }

  private void dfs(Graph G) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(s);
    while (!stack.isEmpty()) {
      int v = stack.pop();
      marked[v] = true;
      for (int w : G.adj(v)) {
        if (!marked[w]) {
          stack.push(w);
          edgeTo[w] = v;
        }
      }
    }
  }

  public Iterable<Integer> pathTo(int v) {
    if (!marked[v])
      return null;
    Stack<Integer> path = new Stack<Integer>();
    for (int x = v; x != s; x = edgeTo[x])
      path.push(x);
    path.push(s);
    return path;
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public static void main(String[] args) {
    Graph G = new Graph(new In(args[0]));
    int s = Integer.parseInt(args[1]);
    DFSNonRecursive search = new DFSNonRecursive(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (search.hasPathTo(v)) {
        StdOut.print(s + " to " + v + ": ");
        StdOut.println(search.pathTo(v));
      }
    }
  }
}
