/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-12T13:45:07-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-12T19:20:04-06:00
 */

import edu.princeton.cs.algs4.StdRandom;

public class STClient {
  public static void main(String[] args) {
    OrderedArray<Integer, Integer> p = new OrderedArray<Integer, Integer>();
    for (int i = 0; i < 50; i++) {
      int choice = StdRandom.uniform(0, 2);
      if (choice == 0) {
        int value = StdRandom.uniform(0, 100);
        p.insert(value, i);
        System.out.println("Inserted " + value);
      } else
        System.out.println("Maximum: " + p.delMax());
    }
  }
}
