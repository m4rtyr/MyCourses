Q: Determine the amount of memory used by `EdgeWeightedGraph` to represent a graph of $V$ vertices and $E$ edges, using the memory-cost model of Section 1.4.



A: 



Overhead: 16 bytes.

Padding: 4 bytes.

Instance Variables:

`V` — integer with 4 bytes of memory.

`E` — integer with 4 bytes of memory.

`adj` — `Bag` array data type; (16 bytes + 4 bytes + 4 bytes)