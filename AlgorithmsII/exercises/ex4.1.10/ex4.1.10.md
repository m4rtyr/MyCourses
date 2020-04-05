Q: Prove that every connected graph has a vertex whose removal (including all incident edges) will not disconnect the graph, and write a DFS method
that finds such a vertex.
A: In a connected graph, every vertex has a path to every other vertex by definition. Now, assume that there is a connected graph where the removal of any
vertex disconnects the graph. However, that means that every path  between any two vertices requires us to go through every single verequires us to go through every single vertex. However, this is impossible, since adjacent vertices don't need to go through every single vertex. Therefore, by reductio ad absurdum, the proposition is proven.
