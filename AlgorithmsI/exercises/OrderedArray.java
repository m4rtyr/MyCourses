/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-12T18:55:05-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-12T19:19:12-06:00
 */


public class OrderedArray<Key extends Comparable<Key>, Value> {

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

  public OrderedArray() {
    n = 0;
    keys = (Key[]) new Comparable[1];
    values = (Value[]) new Object[1];
  }

  public void insert(Key key, Value value) {
    if (n >= keys.length)
      expandArray(2*keys.length);
    for (int i = 0; i < n; i++) {
      int cmp = keys[i].compareTo(key);
      if (cmp > 0) {
        for (int k = n; k > i; k--) {
          keys[k] = keys[k-1];
          values[k] = values[k-1];
        }
        keys[i] = key;
        values[i] = value;
        ++n;
        return;
      }
    }
    keys[n] = key;
    values[n++] = value;
  }

  public Key delMax() {
    if (n == 0)
      return null;
    if (n > 0 && n <= keys.length/4)
      expandArray(keys.length/4);
    Key item = keys[n-1];
    keys[--n] = null;
    values[n] = null;
    return item;
  }
}
