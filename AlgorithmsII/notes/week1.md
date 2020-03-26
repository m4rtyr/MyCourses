# Graphs

* Graph Theory — area of mathematics that studies graphs, a series of vertices connected by edges
* Used for maps, web content, social networks, scheduling, computer networks, etc.
* Four Types of Graphs
  * Undirected — simple connections
  * Digraphs — Direction of connections is considered
  * Edge-weighted — Each connection has associated weight
  * Edge-weighted digraph — Connection has weight and direction

## Undirected Graphs

* Graph — Set of vertices and a collection of edges that each connect a pair of vertices

  * Anomalies in definition
    * Self-loop — edge that connects a vertex to itself
    * Two edges that connect the same vertex (sometimes called multigraphs)

* Glossary

  * Two vertices are *adjacent* if connected by an edge

  * An edge is *incident* to its vertices

  * *Degree* of a vertex is # of edges incident to it

  * *Subgraph* is a subset of graph's edges

  * *Path* — sequence of vertices connected by edges

    * *simple path* — path w/ no repeated vertices
    * *cycle* — path with at least one edge whose first and last vertices are same
    * *simple cycle* — cycle w/ no repeated vertices
    * *length of path* — # of edges

  * Vertices are *connected* if there is a path from one to another

  * *Connected* graph is a graph where there's a path from every vertex to every other vertex

  * *Connected components* are maximal connected subgraphs in unconnected graph.

  * *Acyclic* graph is a graph with no cycles

  * *Tree* — acyclic connected graph

  * *Forest* — Disjoint set of trees

  * *spanning tree* of connected graph is subgraph that contains all of that graph's vertices and is a single tree

  * *spanning forest* is union of spanning trees of its connected components

  * *Density* — proportion of possible pairs of vertices that are connected by edges

    * *sparse graph* — relatively few of possible edges present
    * *dense graph* — relatively few of possible edges missing
    * Rule of thumb: sparse if number of edges proportional to $V$ and dense if proportional to $V^2$

  * *Bipartite graph* — graph whose vertices can be divided into two sets such that all edges connect a vertex in one set with a vertex in another set

  
## Undirected Graph Data Type

  ```java
  public class Graph 
    Graph(int V)								 // create a V-vertex graph w/ no edges
    Graph(In in)								 // create a graph from input stream in
    int V()											 // number of vertices
    int E()											 // number of edges
    void addEdge(int v, int w)   // add edge v-w to this graph
    Iterable<Integer> adj(int v) // vertices adjacent to vertex v
    String toString()						 // string representation
  ```

  The second constructor assumes input format containing $2E + 2$ integer values: $V$, then $E$, then $E$ pairs of values between $0$ and $V-1$, each pair denoting an edge.

  ### Representation 

  Need to balance space to store graph implementation and time-efficiency to ensure graph processing.

  Three types of representations:

  * *Adjacency matrix* — $V$ by $V$ boolean array w/ entry in row $v$ and column $w$ defined as true if there is an edge connecting vertex $v$ to $w$. This fails on space efficiency ($V^2$)
  * *Array of Edges* — Using Edge class w/ two instance variables of type `int` (implementing `adj()` method would require examining all edges, so it's time inefficient).
  * *Array of Adjacency Lists* — Maintain vertex-indexed array of lists of vertices adjacent to each vertex (ex. Vertex 0, 1, 2, ... would contain a list of vertices adjacent to it). This is the ideal since it satisfies both requirements.

  Using the third option, space used is proportional to $E + V$ and time is constant when adding an edge. 

## Design Pattern for Graph Processing

Generally, when writing implementations for Graphs, we try to decouple the algorithm from the Graph class itself.