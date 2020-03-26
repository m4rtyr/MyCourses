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

  