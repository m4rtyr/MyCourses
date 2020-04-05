Q: Provide priority-queue implementations that support *insert* and *remove
the maximum*, one for each of the following underlying data structures:
unordered array, ordered array, unordered linked list, and ordered linked list.
Give a table of the worst-case bounds for each operation for each of your 4
implementations.

A: 

| Implementation        | Insert | Remove the Maximum |
| --------------------- | ------ | ------------------ |
| Ordered Array         | $N$    | 1                  |
| Unordered Array       | $1$    | $N$                |
| Ordered Linked List   | $N$    | $N$                |
| Unordered Linked List | $N$    | $N$                |

