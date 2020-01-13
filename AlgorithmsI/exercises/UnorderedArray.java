/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-12T17:15:28-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-12T19:00:00-06:00
 */

public class UnorderedArray<Key extends Comparable<Key>, Value> {

  private int n;
  private Key[] keys;
  private Value[] values;

  private void expandArray(int newSize) {
    Key[] keysCopy = (Key[]) new Comparable[newSize];
    Value[] valuesCopy = (Value[]) new Object[newSize];
    for (int i = 0; i < n; i++) {
      keysCopy[i] = keys[i];
      valuesCopy[i] = values[i];
    }
    keys = keysCopy;
    values = valuesCopy;
  }

  public UnorderedArray() {
    n = 0;
    keys = (Key[]) new Comparable[1];
    values = (Value[]) new Object[1];
  }

  public void insert(Key key, Value value) {
    if (n == keys.length)
      expandArray(2*keys.length);
    keys[n] = key;
    values[n++] = value;
  }

  public Key delMax() {
    if (n == 0)
      return null;
    if (n > 0 && n <= keys.length/4)
      expandArray(keys.length/4);
    int maxIndex = 0;
    Key max = keys[0];
    for (int i = 0; i < n; i++) {
      if (max.compareTo(keys[i]) < 0) {
        max = keys[i];
        maxIndex = i;
      }
    }
    keys[maxIndex] = keys[n-1];
    keys[--n] = null;
    return max;
  }
}
