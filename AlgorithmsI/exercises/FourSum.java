/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-13T15:30:41-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-13T21:01:43-06:00
 */

import edu.princeton.cs.algs4.Queue;

public class FourSum {

  class Node {
    int a, b;
    Node next;
  }

  private int M;
  private Node[] table;
  private int[] array;

  public FourSum(int[] array) {
    this.array = new int[array.length];
    for (int i = 0; i < array.length; i++)
      this.array[i] = array[i];
    M = 20000;
    table = new Node[M];
  }

  private int hash(int i) {
    Integer t = i;
    return (t.hashCode() & 0x7fffffff) % M;
  }

  public void put(int a, int b) {
    int h = hash(a + b);
    Node head = table[h], x = null;
    for (x = head; x != null && x.next != null; x = x.next)
      x = x.next;
    Node n = new Node();
    n.a = a;
    n.b = b;
    if (x == null)
      table[h] = n;
    else
      x.next = n;
  }

  public void sum() {
    for (int i = 0; i < array.length; i++)
      for (int k = i+1; k < array.length; k++)
        put(array[i], array[k]);
  }

  public void print() {
    for (int i = 0; i < table.length; i++) {
      if (table[i] == null || table[i].next == null)
        continue;
      for (Node x = table[i]; x != null; x = x.next)
        System.out.print("(" + x.a + ", " + x.b + ") ");
      System.out.println();
    }
  }

  public static void main(String[] args) {
    int[] arr = {1, 2, 0, 3, 7, 10, 23, -32, -15, -17};
    FourSum fs = new FourSum(arr);
    fs.sum();
    fs.print();
  }

}
