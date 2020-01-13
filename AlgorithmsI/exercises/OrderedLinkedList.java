/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-12T13:53:42-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-12T17:14:36-06:00
 */

public class OrderedLinkedList<Key extends Comparable<Key>, Value> {

  class Node {
    Key key;
    Value value;
    Node next;
  };

  private Node head;

  public OrderedLinkedList() {
     head = null;
  }

  public void insert(Key key, Value value)  {
    if (head == null) {
      head = new Node();
      head.key = key;
      head.value = value;
      return;
    }
    Node x = head, prev = null;
    while (x != null) {
      int cmp = key.compareTo(x.key);
      if (cmp == 0) {
        x.value = value;
        return;
      } else if (cmp < 0) {
        Node n = new Node();
        n.key = key;
        n.value = value;
        if (prev != null)
          prev.next = n;
        else
          head = n;
        n.next = x;
        return;
      }
      prev = x;
      x = x.next;
    }
    prev.next = new Node();
    prev.next.key = key;
    prev.next.value = value;
  }

  public Key delMax() {
    if (head == null)
      return null;
    Node x = head, prev = null;
    while (x.next != null) {
      prev = x;
      x = x.next;
    }
    Key item = x.key;
    if (prev != null)
      prev.next = null;
    else
      head = null;
    return item;
  }
}
