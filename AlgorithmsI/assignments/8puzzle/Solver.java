/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2019-08-01T20:01:33-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2019-08-18T20:43:03-05:00
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private int totalMoves = -1;
  private final boolean solvable;
  private Stack<Board> solutionSet;

  private class SearchNode implements Comparable<SearchNode> {

    private int moves;
    private int manhattan;
    private SearchNode prev;
    private Board b;

    public SearchNode(Board b, int moves, SearchNode prev) {
      this.b = b;
      this.moves = moves;
      this.manhattan = b.manhattan();
      this.prev = prev;
    }

    public Board board() { return b; }

    public int compareTo(SearchNode b) {
      double thisDist = this.getMoves() + this.getManhattan();
      double bDist = b.getMoves() + b.getManhattan();

      if (thisDist > bDist) return 1;
      else if (thisDist < bDist) return -1;
      else return 0;
    }

    public int getMoves() { return this.moves; }
    public void setMoves(int moves) { this.moves = moves; }
    public SearchNode getPrev() { return this.prev; }
    public void setPrev(SearchNode prev) { this.prev = prev; }
    public int getManhattan() { return this.manhattan; }
  }

  private void constructSolutionSet(SearchNode current) {
    solutionSet = new Stack<Board>();
    while (current != null) {
      solutionSet.push(current.board());
      current = current.getPrev();
    }
  }

  private boolean aStarSearch(Board initial) {
    MinPQ<SearchNode> nodes = new MinPQ<SearchNode>();
    MinPQ<SearchNode> nodesTwin = new MinPQ<SearchNode>();

    /* 0 moves, first node, so no previous */
    SearchNode initialStart = new SearchNode(initial, 0, null);
    nodes.insert(initialStart);

    Board initialTwin = initial.twin();
    SearchNode initialStartTwin = new SearchNode(initialTwin, 0, null);
    nodesTwin.insert(initialStartTwin);

    while (!nodes.isEmpty() && !nodesTwin.isEmpty()) {
      SearchNode curr = nodes.delMin(); // Get the minimum.
      SearchNode currTwin = nodesTwin.delMin();
      if (curr.board().isGoal()) { // Check if we're at the goal.
        /* Create solution set and count moves. */
        constructSolutionSet(curr);
        totalMoves = solutionSet.size()-1;
        return true;
      }
      if (currTwin.board().isGoal())
        break;

      for (Board n : curr.board().neighbors()) { // Go through neighbors.
        if (curr.getPrev() != null && n.equals(curr.getPrev().board())) // Skip if visited.
          continue;
        /* Create Comparable neighbor. */
        SearchNode neighbor = new SearchNode(n, curr.getMoves() + 1, curr);
        nodes.insert(neighbor);
      }
      for (Board ntwin : currTwin.board().neighbors()) {
        if (currTwin.getPrev() != null && ntwin.equals(currTwin.getPrev().board()))
          continue;
        SearchNode neighborTwin = new SearchNode(ntwin, currTwin.getMoves() + 1, currTwin);
        nodesTwin.insert(neighborTwin);
      }
    }
    return false;
  }

  public Solver(Board initial) {
    if (initial == null)
      throw new IllegalArgumentException();
    solvable = aStarSearch(initial);
  }

  public boolean isSolvable() {
    return solvable;
  }

  public int moves() {
    return totalMoves;
  }

  public Iterable<Board> solution() {
    return solutionSet;
  }

  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    StdOut.println(solver.moves());
    if (!solver.isSolvable()) {
        StdOut.println("No solution possible");
    } else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}
