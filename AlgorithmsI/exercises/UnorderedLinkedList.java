/**
 * @Author: S. Sharma <silentcat>
 * @Date:   2020-01-12T13:29:23-06:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   silentcat
 * @Last modified time: 2020-01-12T13:51:48-06:00
 */

public class UnorderedLinkedList<Key extends Comparable<Key>, Value> {

  class Node {
    Key key;
    Value value;
    Node next;
  };

  private Node head;

  public UnorderedLinkedList() {
    head = null;
  }

  public void insert(Key key, Value value) {
    if (head == null) {
      head = new Node();
      head.key = key;
      head.value = value;
    } else {
      Node x;
      for (x = head; x.next != null; x = x.next) {
        if (key.compareTo(x.key) == 0) {
          x.value = value;
          break;
        }
      }
      x.next = new Node();
      x.next.key = key;
      x.next.value = value;
    }
  }

  public Key delMax() {
    if (head == null)
      return null;
    Key key = head.key;
    for (Node x = head; x != null; x = x.next) {
      if (key.compareTo(x.key) < 0)
        key = x.key;
    }
    Node prev = null, x;
    for (x = head; x != null; x = x.next) {
      if (key.compareTo(x.key) == 0) {
        if (prev != null)
          prev.next = x.next;
        else
          head = x.next;
        break;
      } else
        prev = x;
    }
    return x.key;
  }

}
