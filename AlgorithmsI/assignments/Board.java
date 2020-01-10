/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2019-07-31T15:21:38-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2019-08-18T20:37:55-05:00
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;

public class Board {

  private int blankTileRow;
  private int blankTileCol;
  private final int n;
  private int[][] board;

  private int distanceToPosition(int x, int y) {
    int a = board[x][y], xp = 0, yp = 0, pos = 0;
    if (a == 0) {
      xp = n;
      yp = n;
    } else {
      while (pos < a) {
        pos += n;
        ++xp;
      }
      yp = Math.abs(a - n*(xp-1));
    }
    return Math.abs(x+1 - xp) + Math.abs(y+1 - yp);
  }

  private void swapTiles(int x, int y, int x2, int y2) {
    int tmp = board[x][y];
    board[x][y] = board[x2][y2];
    board[x2][y2] = tmp;
  }

  private int[] convertTileNum(int num) {
    int[] tuple = new int[2];
    if (num == 0) {
      tuple[0] = n-1;
      tuple[1] = n-1;
    } else {
      int pos = 0, xp = 0, yp = 0;
      while (pos < num) {
        pos += n;
        ++xp;
      }
      yp = Math.abs(num - n*(xp-1));
      tuple[0] = --xp;
      tuple[1] = --yp;
    }
    return tuple;
  }

  public Board(int[][] tiles) {
    board = new int[tiles.length][tiles.length];
    n = tiles.length;
    for (int i = 0; i < n; i++) {
      for (int k = 0; k < n; k++) {
        board[i][k] = tiles[i][k];
        if (board[i][k] == 0) {
          blankTileRow = i;
          blankTileCol = k;
        }
      }
    }
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  public int dimension() {
    return n;
  }

  public int hamming() {
    int hamming = 0;
    for (int i = 0; i < n; i++)
      for (int k = 0; k < n; k++)
        if (board[i][k] != 0 && board[i][k] != n*i + k+1)
          ++hamming;
    return hamming;
  }

  public int manhattan() {
    int manhattan = 0;
    for (int i = 0; i < n; i++)
      for (int k = 0; k < n; k++)
        if (board[i][k] != 0)
          manhattan += distanceToPosition(i, k);
    return manhattan;
  }

  public boolean isGoal() {
    return hamming() == 0;
  }

  public boolean equals(Object y) {
    if (y == this) return true;

    if (y == null) return false;

    if (y.getClass() != this.getClass())
      return false;

    Board b = (Board) y;
    if (b.dimension() != this.dimension())
      return false;

    int bn = b.dimension();
    boolean equal = true;
    for (int i = 0; i < bn; i++) {
      for (int k = 0; k < bn; k++) {
        if (this.board[i][k] != b.board[i][k]) {
          equal = false;
          break;
        }
      }
    }
    return equal;
  }

  public Iterable<Board> neighbors() {
    Queue<Board> q = new Queue<Board>();
    int i = 0, k = 0;
    boolean exit = false;
    for (i = 0; i < n; i++) {
      for (k = 0; k < n; k++) {
        if (board[i][k] == 0) {
          exit = true;
          break;
        }
      }
      if (exit)
        break;
    }
    if (i+1 <= n-1) {
      swapTiles(i+1, k, i, k);
      q.enqueue(new Board(board));
      swapTiles(i+1, k, i, k);
    } if (i-1 >= 0) {
      swapTiles(i-1, k, i, k);
      q.enqueue(new Board(board));
      swapTiles(i-1, k, i, k);
    } if (k+1 <= n-1) {
      swapTiles(i, k+1, i, k);
      q.enqueue(new Board(board));
      swapTiles(i, k+1, i, k);
    } if (k-1 >= 0) {
      swapTiles(i, k-1, i, k);
      q.enqueue(new Board(board));
      swapTiles(i, k-1, i, k);
    }
    return q;
  }

  public Board twin() {
    int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    if (blankTileRow != 0) {
      swapTiles(0, 0, 0, 1);
      x1 = 0;
      y1 = 0;
      x2 = 0;
      y2 = 1;
    } else {
      swapTiles(1, 0, 1, 1);
      x1 = 1;
      y1 = 0;
      x2 = 1;
      y2 = 1;
    }
    Board b = new Board(board);
    swapTiles(x1, y1, x2, y2);
    return b;
  }

  public static void main(String[] args) {
    int n = StdIn.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int k = 0; k < n; k++) {
        int x = StdIn.readInt();
        tiles[i][k] = x;
      }
    }

    int n1 = 0;
    int[][] tiles2 = null;
    while (!StdIn.isEmpty()) {
      if (n1 == 0) {
        n1 = StdIn.readInt();
        tiles2 = new int[n1][n1];
      }
      for (int i = 0; i < n1; i++) {
        for (int k = 0; k < n1; k++) {
          int x = StdIn.readInt();
          tiles2[i][k] = x;
        }
      }
    }

    Board b = new Board(tiles);
    Board b2 = (tiles2 == null) ? null : new Board(tiles2);
    StdOut.println("Testing toString()");
    StdOut.println(b.toString());
    StdOut.println("Testing dimension()");
    StdOut.println(b.dimension());
    StdOut.println("Testing hamming()");
    StdOut.println(b.hamming());
    StdOut.println("Testing manhattan()");
    StdOut.println(b.manhattan());
    StdOut.println("Testing isGoal()");
    StdOut.println(b.isGoal());
    StdOut.println("Testing equals()");
    if (b2 != null)
      StdOut.println(b.equals(b2));
    else
      StdOut.println("Nevermind, skipping...");
    StdOut.println("Testing twin()");
    StdOut.println(b.twin());
    StdOut.println("Testing neighbors()");
    Iterable<Board> q = b.neighbors();
    for (Board r : q)
      StdOut.println(r);
  }
}
