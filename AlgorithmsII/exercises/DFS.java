/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-03-25T22:03:10-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-03-25T22:31:50-05:00
 */

public class DFS {

  private int s, deg;
  private Graph g;
  private boolean[] visited;

  public DFS(Graph g, int s) {
    this.g = g;
    this.s = s;
    this.deg = g.adj(s).size();
    visited = new boolean[g.V()];
  }

  private void search(int v) {
    marked[v] = true;
    for (int w : g.adj(v))
      if (!marked[w])
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
}
