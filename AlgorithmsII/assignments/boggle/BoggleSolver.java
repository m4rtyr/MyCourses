import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

  private TrieSET dictionary;

  public BoggleSolver(String[] dictionary) {
    this.dictionary = new TrieSET();
    for (String word : dictionary)
      this.dictionary.add(word);
  }

  public Iterable<String> getAllValidWords(BoggleBoard board) {
    return dfs(board);
  }

  public int scoreOf(String word) {
    return 0;
  }

  public static void main(String[] args) {
      In in = new In(args[0]);
      String[] dictionary = in.readAllStrings();
      BoggleSolver solver = new BoggleSolver(dictionary);
      BoggleBoard board = new BoggleBoard(args[1]);
      int score = 0;
      for (String word : solver.getAllValidWords(board)) {
          StdOut.println(word);
          score += solver.scoreOf(word);
      }
      StdOut.println("Score = " + score);
  }

  private int convert1D(BoggleBoard board, int x, int y) {
    return y*board.rows() + x;
  }

  private boolean checkBounds(BoggleBoard board, int x, int y) {
    return x >= 0 && x < board.cols() && y >= 0 && y < board.rows();
  }

  private boolean isValidWord(String word) {
    return word.length() >= 3 && dictionary.contains(word);
  }

  private Queue<String> dfs(BoggleBoard board) {
    boolean[] marked;
    Queue<String> words = new Queue<String>();
    for (int x = 0; x < board.cols(); x++) {
      for (int y = 0; y < board.rows(); y++) {
        String prefix = "" + board.getLetter(x, y);
        marked = new boolean[board.rows()*board.cols()];
        dfs(marked, board, x, y, prefix, words);
      }
    }
    return words;
  }

  private void dfs(boolean[] marked, BoggleBoard board, int x, int y, String prefix, Queue<String> validWords) {
    marked[convert1D(board, x, y)] = true;
    for (int i = x-1; i <= x+1; i++) {
      for (int k = y-1; k <= y+1; k++) {
        if (i == x && k == y) continue;
        if (checkBounds(board, i, k) && !marked[convert1D(board, i, k)]) {
          String newprefix = prefix + board.getLetter(i, k);
          Queue<String> prefixes = (Queue<String>) dictionary.keysWithPrefix(newprefix);
          if (isValidWord(newprefix))
            validWords.enqueue(newprefix);
          if (!prefixes.isEmpty())
            dfs(marked, board, i, k, newprefix, validWords);
        }
      }
    }
  }

}
