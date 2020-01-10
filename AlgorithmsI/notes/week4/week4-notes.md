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

| Implementation  | Insert | Del Max/Min | Min/Max |
| --------------- | ------ | ----------- | ------- |
| Unordered Array | 1      | N           | N       |
| Ordered Array   | N      | 1           | 1       |
| Goal            | log N  | log N       | log N   |

Above is good for small priority queue implementations, but not for most applications with large data sets.

## Binary Heaps

## Heapsort

## Event-Driven Simulation

