# 2.4 Priority Queues

## API and Elementary Implementations

* Priority Queues — Removes the largest or smallest item

* Want Generic Items that are Comparable meaning keys must be comparable and a compareTo() method should be there to compare to another key

* Only difference between stack/queue data structure is delMax() (or delMin()) function.

* Generalizes the stack and queue

* Priority Queue client example

  * Find largest M items in a stream of N items but not enough memory to store N items

  * Ex Code:

    ```java
    MinPQ<Transaction> pq = new MinPQ<Transaction>();
    
    while (StdIn.hasNextLine()) // This is the N factor
    {
      String line = StdIn.readLine();
      Transaction item = new Transaction(line);
      pq.insert(item);
      if (pq.size() > M) // Only store M items
        pe.delMin();
    }
    ```

    | Implementation |  Time   | Space |
    | :------------: | :-----: | :---: |
    |      Sort      | N log N |   N   |
    | Elementary PQ  |   MN    |   M   |
    |  Binary Heap   | N log M |   M   |
    | Best in Theory |    N    |   M   |

* Sorting is out of question because we can't store N items
* Elementary PQ will be fine for small numbers, but will become large
* Binary Heap is most practical and closest to best in theory

### Unordered and Ordered Array Implementation

* Unordered
  * Use a linked list or resizing array, going through entire list to find maximum/minimum
* Ordered
  * Use a linked list or resizing array, insert each element into the list to keep array/linked list sorted.

| Implementation  | Insert  | Del Max/Min | Min/Max |
| --------------- | ------- | ----------- | ------- |
| Unordered Array | 1       | N           | N       |
| Ordered Array   | N       | 1           | 1       |
| Binary Heap     | $\lg N$ | $\lg N$     | 1       |
| Goal            | log N   | log N       | log N   |

Above is good for small priority queue implementations, but not for most applications with large data sets.

## Binary Heaps

* Based on Binary Tree — links to left and right or empty
* Complete Binary Tree — Perfectly balanced (except for bottom level perhaps)
  * Properties
    * Height of complete tree with $N$ nodes is $\lfloor \lg(n) \rfloor$
      * Proof: Height only increases when $N$ is power of 2
* Binary heap — Array representation of a heap-ordered complete binary tree
  * Heap-ordered binary tree
    * Keys in nodes
    * Parent's key no smaller than children's key
  * Array representation
    * Indices start at 1
    * Take nodes in **level** order
    * No explicit links are needed, only manipulation of indices
  * Properties
    * Largest key is a[1] which is the root of the tree
    * Can use array indices to move through the tree
      * Parent of node at $k$ is $\frac{k}{2}$ (integer division)
      * Children of node at $k$ are $2k$ and $2k + 1$ 
  * Promotion in a heap
    * Scenario: Child's key > parent's key
    * Eliminate violation
      * Exchange key in child with key in parent
      * Repeat until heap order restored
    * Peter principle: Node promoted to level of incompetence
    * **swim** operation
  * Insertion in a heap
    * Add node at end, perform **swim** operation
    * Cost $1 + \lg N$ compares
  * Demotion in a heap
    * Scenario: Parent's key < one or both children's key
    * Eliminate violation
      * Exchange key in parent with key in larger child
      * Repeat until heap ordered restored
      * **sink** operation
  * Delete the maximum in a heap
    * Delete max: Exchange root with node at end, then sink it down.
    * At most $2 \lg N$ compares
* Considerations
  * Immutability of keys – So client can't change keys which could violate heap order
  * Underflow and overflow
  * Minimum-oriented priority queue
  * Other operations
    * Remove an arbitrary item
    * Change the priority of an item

## Heapsort

* Basic plan for in-place sort

  * Create a max-heap with all $N$ keys
  * Repeatedly remove the maximum key.

*  Heap construction

  * Build max heap using bottom-up method

  * ```java
    for (int k = N/2; k >= 1; k--)
      sink(a, k, N);
    ```

  * The bottom-most nodes (those after $\frac{N}{2}$) are only single elements. Therefore, to build the heap, we start in the middle and keep going backwards up the level, performing the sink operation on the $kth$ node until we're at the root.

* Remove the maximum each time and place it at the end of the array.

* Mathematical Analysis

  * Heap constructions uses $\le 2N$ compares and exchanges
  * Heapsort uses $\le 2 N \lg N$ compares and exchanges
  * **In-place sorting algorithm with $ N \log N$ worst case**  
    * Mergesort — in-place merge, not possible
    * Quicksort — worst-case quicksort possible ($N^2$) ; not practical
  * Bottom line
    * Heapsort optimal for time and space
    * Inner loop longer than quicksort's
    * Make poor use of cache memory
    * Not stable

## Symbol Tables

* Key-value pair abstraction

  * Insert a value with specified key
  * Given a key, search for the corresponding value

* Basic symbol table API

  * ```java
    public class ST<Key, Value>
    	ST() // Create a symbol table
      void put(Key, key, Value val) // Put key value pair into table
      Value get(Key, key) // Value paired with key
      void delete(Key, key) // Remove key & value from table
      boolean contains(Key key) // Is there a value paired with key?
      boolean isEmpty() // Is the table empty?
      int size() // Number of key-value pairs
      Iterable<Key> keys() // all keys in the table
    ```

* Conventions

  * Values aren't `null` 

  * Method `get()` returns `null` if no key present

  * Method `put()` overwrites old value with new value

  * Intended Consequences

    * Easy to implement `contains()`

      ```java
      public boolean contains(Key, key)
      { return get(key) != null; }
      ```

    * Lazy version of `delete()`

      ```java
      public void delete(Key, key)
      { put(key, null); }
      ```

* Keys and values

  * Value type — Any generic type
  * Key type
    * Assume keys are `Comparable` use `compareTo()` 
    * Assume keys are any generic type use `equals()` to test equality
    * Assume keys are any geneirc type use `equals()` to test equality; use `hashCode()` to scramble key.
  * Best practices — Use immutable type for symbol table key

* Ordered operations

  * Symbol table API usually much larger, including `put()`, `get()`, `delete()`, `isEmpty()`, `rank()`, `select()`, etc.

## Binary Search Trees

* BST — Binary tree in symmetric order
* Symmetric Order — All nodes to parent's **left are smaller**; all nodes to parent's **right are bigger**
* Different from Binary Heap where **parent is larger than both children**
* Tree Shape
  * Many BSTs correspond to same set of keys; best case is a complete binary tree; worst case is when nodes are all insert in order
* Quicksort partitioning and binary search tree correspondance (when no duplicate keys); allows us to analyze BST performance to be ~ $~ 2 \ln N$ compares
* Ordered Operations
  * Floor/Ceiling implemented by storing subcounts in each disjoint binary tree
  * Inorder traversal — Traverse left subtree, enqueue key, traverse right subtree.
  * **All ordered operations (except iteration) are proportional to height of BST ($\log N$ if inserted in random order)**
* BST Deletion
  * Lazy approach — Set value to `null` but leave key in tree to guide search (don't consider it equal in search)
    * Cost: ~ $2 \ln N'$ per insert, search, and delete where $N'$ is # of key-value pairs ever inserted into BST
    * Problem: Memory overload
  * Deleting the minimum
    * Go left until finding a node with `null` left link
    * Replace node by its right link
    * Update subtree count
  * Hibbard deletion
    * Delete node `t` containing key `k`
    * Case 0 (no children): Delete `t` by setting parent link to `null`
    * Case 1 (1 child): Delete `t` by replacing parent link to it (like deleting minimum) with its child
    * Case 2 (2 children):
      * Find successor `x` of `t`
      * Delete minimum in `t's` rights subtree 
      * Put `x` in `t's` spot
      * Unsatisfactory solution: Not symmetric
        * Trees not random $\rarr$ $\sqrt{N}$ per op.
        * Other operations also become $\sqrt{N}$ is deletions allowed



## ST Summary

| ST Implementation                  | Worst-case cost (after N inserts)  | Average Case (after N random inserts)              | Ordered Iteration? | Key interface |
| ---------------------------------- | ---------------------------------- | -------------------------------------------------- | ------------------ | ------------- |
| Sequential Search (unordered list) | Search — $N$ Insert — $N$          | Search hit — $N / 2$ Insert — $N$                  | No                 | `equals()`    |
| Binary Search (ordered array)      | Search — $\log N$ <br>Insert — $N$ | Search hit — $\log N$<br> Insert — $N/2$           | Yes                | `compareTo()` |
| BST                                | Search — $N$ <br>Insert — $N$      | Search hit — $1.39 \lg N$<br>Insert — $1.39 \lg N$ | Yes                | `compareTo()` |

