import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import java.util.HashMap;

public class BaseballElimination {

  private int N;
  private int[] w;
  private int[] l;
  private int[] r;
  private int[][] g;
  private HashMap<String, Integer> teams;

  public BaseballElimination(String filename) {

    teams = new HashMap<String, Integer>();
    In in = new In(filename);
    N = in.readInt();

    w = new int[N];
    l = new int[N];
    r = new int[N];
    g = new int[N][N];

    for (int i = 0; !in.isEmpty() && i < N; i++) {
      teams.insert(in.readString(), i);
      w[i] = in.readInt();
      l[i] = in.readInt();
      r[i] = in.readInt();
      for (int k = 0; k < N; k++)
        g[i][k] = in.readInt();
    }
  }

  public int numberOfTeams() {
    return N;
  }

  public Iterable<String> teams() {
    return teams;
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
    Integer i = teams.get(team);
    Integer k = teams.get(team2);
    if (i == null || k == null)
      throw new IllegalArgumentException();
    return g[i][k];
  }

  public boolean isEliminated(String team) {
    if (teams.get(team) == null)
      throw new IllegalArgumentException();
  }

  public Iterable<String> certificateOfElimination(String team) {

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
}
