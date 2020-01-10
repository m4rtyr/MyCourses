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

## Event-Driven Simulation

