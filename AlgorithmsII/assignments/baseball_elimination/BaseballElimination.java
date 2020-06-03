import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Queue;


public class BaseballElimination {

  private int N;
  private int[] w;
  private int[] l;
  private int[] r;
  private int[][] g;
  private boolean[] eliminated;
  private HashMap<String, ArrayList<String>> cuts;
  private HashMap<String, Integer> teams;
  private HashMap<Integer, String> teamsReverse;
  private Queue<String> list;

  public BaseballElimination(String filename) {

    teams = new HashMap<String, Integer>();
    teamsReverse = new HashMap<Integer, String>();
    list = new Queue<String>();
    In in = new In(filename);
    N = in.readInt();

    w = new int[N];
    l = new int[N];
    r = new int[N];
    g = new int[N][N];
    eliminated = new boolean[N];
    cuts = new HashMap<String, ArrayList<String>>();

    for (int i = 0; !in.isEmpty() && i < N; i++) {
      String team = in.readString();
      teams.put(team, i);
      teamsReverse.put(i, team);
      list.enqueue(team);
      w[i] = in.readInt();
      l[i] = in.readInt();
      r[i] = in.readInt();
      for (int k = 0; k < N; k++)
        g[i][k] = in.readInt();
    }

    for (int i = 0; i < N; i++)
        eliminated[i] = !trivialElimination(i) ? computeMaxFlow(i) : true;
  }

  public int numberOfTeams() {
    return N;
  }

  public Iterable<String> teams() {
    return list;
  }

  public int wins(String team) {
    Integer i = teams.get(team);
    if (i == null)
      throw new IllegalArgumentException();
    return w[i];
  }

  public int losses(String team) {
    Integer i = teams.get(team);
    if (i == null)
      throw new IllegalArgumentException();
    return l[i];
  }

  public int remaining(String team) {
    Integer i = teams.get(team);
    if (i == null)
      throw new IllegalArgumentException();
    return r[i];
  }

  public int against(String team1, String team2) {
    Integer i = teams.get(team1);
    Integer k = teams.get(team2);
    if (i == null || k == null)
      throw new IllegalArgumentException();
    return g[i][k];
  }

  public boolean isEliminated(String team) {
    if (teams.get(team) == null)
      throw new IllegalArgumentException();
    int x = teams.get(team);
    return eliminated[x];
  }

  public Iterable<String> certificateOfElimination(String team) {
    if (teams.get(team) == null)
      throw new IllegalArgumentException();
    return cuts.get(team);
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team)) {
                StdOut.print(t + " ");
            }
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
  }

  private boolean trivialElimination(int x) {
    for (int i = 0; i < N; i++) {
      if (i == x) continue;
      if (w[x] + r[x] < w[i]) {
        String team = teamsReverse.get(i);
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(team);
        cuts.put(teamsReverse.get(x), arr);
        return true;
      }
    }
    return false;
  }

  private boolean computeMaxFlow(int x) {
    int V = (N)*(N-1) / 2 + 2;
    int s = V-1, t = V-2;
    HashMap<Integer, Integer> correspond = new HashMap<Integer, Integer>();
    FlowNetwork F = new FlowNetwork(V);

    /* Connect the Sink side using vertex numbers 0->N-1.
      Avoid x by creating a mapping between the vertices in the graph
      versus the numbers of the teams. */

    for (int i = 0; i < N-1; i++) {
      if (i < x) {
        correspond.put(i, i);
        F.addEdge(new FlowEdge(i, t, Math.abs(w[x] + r[x] - w[i])));
      }
      else if (i >= x) {
        correspond.put(i+1, i); // Team number -> Team Number on the graph
        F.addEdge(new FlowEdge(i, t, Math.abs(w[x] + r[x] - w[i+1])));
      }
    }

    /* Connect the Source side */
    int i = N-1;
    for (int v = 0; v < N; v++) {
      if (v == x) continue;
      for (int w = v+1; w < N; w++) {
        if (w == x) continue;
        F.addEdge(new FlowEdge(s, i, g[v][w]));
        ++i;
      }
    }

    /* Connect the source and sink side together using remaining vertices */
    i = N-1;
    for (int v = 0; v < N; v++) {
      if (v == x) continue;
      for (int w = v+1; w < N; w++) {
        if (w == x) continue;
        F.addEdge(new FlowEdge(i, correspond.get(v), Double.POSITIVE_INFINITY));
        F.addEdge(new FlowEdge(i, correspond.get(w), Double.POSITIVE_INFINITY));
        ++i;
      }
    }

    FordFulkerson f = new FordFulkerson(F, s, t);
    for (FlowEdge e : F.adj(s)) {
      if (e.residualCapacityTo(e.other(s)) != 0) {
        ArrayList<String> arr = new ArrayList<String>();
        for (int v = 0; v < N; v++)
            if (v != x && f.inCut(correspond.get(v)))
              arr.add(teamsReverse.get(v));
        cuts.put(teamsReverse.get(x), arr);
        return true;
      }
    }
    return false;
  }
}
