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

### Undirected Graph Data Type
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

### Design Pattern for Graph Processing

Generally, when writing implementations for Graphs, we try to decouple the algorithm from the Graph class itself.

### Depth First Search

DFS is a classic algorithm for searching a graph. The algorithm is as follows:

* Visit Vertex V
* Visit all Vertices that are adjacent to it.
* Stop if graph is completely visited or destination vertex found.



**Prove: DFS marks all vertices connected to a source in time proportional to the sum of their degrees.**

*Proof*:

Suppose we start at source vertex $s$. From there, the algorithm marks $s$ and its neighbors. Now, assume there's some unmarked vertex $w$ that $s$ is connected to. Since $s$ itself is marked, any path from $s$ to $w$ must have at least one edge from the set of marked vertices to the set of unmarked vertices, say $v \text{-}x$. However, this path cannot exist, since if $v$ was marked by the algorithm, $x$ and all connected, unmarked vertices would be marked, too. By *reductio ad absurdum* the algorithm does mark all vertices starting from $s$. The time bound follows from the fact that the algorithm must check each vertex's neighbors and whether they're marked. 

#### Finding Paths

Computing the path from $s$ to $v$ involves using Depth First Search and finding the path from $s$ to every other vertex in the graph, storing the edges in an array.

**Prove: DFS allows us to provide clients with a path from a given source to any marked vertex in time proportional to its length**.

*Proof*:

Through induction, the array used to store each edge connecting the vertices together is a tree rooted at the source. To build the path requires following the tree from the source to the vertex.

### Breadth First Search

Generally interested in problem of single source shortest paths: given a graph and source vertex $s$, support queries of form *is there a path from $s$ to a given target vertex $v$?* If so, find a shortest such path.

Using a FIFO queue, we start from $s$ and check its edges for vertex $v$. If not found, we then go out two edges and check for vertex $v$ again. We do this until we find vertex $v$.

**Prove: For any vertex $v$ reachable from $s$, BFS computes a shortest path from $s$ to $v$ (not path from $s$ to $v$ has fewer edges)** 

*Proof*:

By induction, the vertices enter and leave the queue in order of their distance from $s$. Therefore, when a vertex enters a queue, there is no shorter path to it.

*Corollary*:

BFS takes time proportional to $E + V$ in the worst case because it marks all vertices (and so the time is the sum of their degrees). 

<<<<<<< HEAD
*Note*: An algorithm is *online* if it doesn't require preprocessing (union-find is online whereas DFS requires the construction of an entire graph).

## Symbol Graphs

Vertices are strings. The API for Symbol Graphs is the following:

```java
public class SymbolGraph {
  public SymbolGraph(String filename, String delimiter) // build graph
  boolean contains(String key)													// Is key a vertex
  int indexOf(String key)																// index of key
  String nameOf(int v)																	// key w/ index v
  Graph graph()																					// the graph
}	
```

## Directed Graphs

In directed graphs, edges are one-way. Directed graphs are also called digraphs. A directed graph is a set of vertices and a collection of directed edges. Each directed edge connects an ordered pair of vertices. 

### Definitions

* Outdegree — number of edges leaving the vertex
* Indegree — number of edges entering vertex
* Tail — first vertex in directed edge
* Head — 2nd vertex in directed edge
* Directed path — sequence of vertices in which there is a directed edge pointing from each vertex in the sequence to its successor in the sequence with no repeated edges. 
* Simple directed path — directed path with no repeated vertices
* Directed cycle — directed path with at least one edge whose first and last vertices are the same
* Simple directed cycle — directed cycle with no repeated vertices (except requisite repetition of first and last vertices)
* Vertex $w$ is reachable from $v$ if there is a directed path from $v$ to $w$.

### Digraph Data Type

API:

```java
public class Digraph
  Digraph(int V)															// create a V-vertex digraph
  Digraph(In in)															// create a digraph from in
  int V()																			// # of vertices
  int E()																			// # of edges
  void addEdge(int v, int w)									// add edge v->w
  Iterable<Integer> adj(int w)								// vertices left from v
  Digraph reverse()														// reverse of digraph
  String toString()														// String representation
```

### Representation

Adjacency list 

#### Reverse of Digraph

Returns a copy of the digraph with all edges reversed (allows clients to find edges that point to each vertex while `adj()` gives just vertices connected by edges that point *from* each vertex.

### Reachability in Digraphs

Depth First Search can still be used. Takes time proportional to sum of outdegree of all vertices.



### Applications

Mark and sweep garbage collection — Using DFS, we can mark the set of objects that are accessible. Objects are are unmarked can be returned to memory.

Finding paths in Digraphs — shortest path can be found with Breadth-First search and paths in general can be found with DFS

### Cycles and DAGs

Scheduling problems often apply Digraphs. This involves arranging for the completion of set of jobs under a set of constraints. The most important type of constraint is precedence constraints, where certain jobs must be performed before others. Precedence-constrained scheduling — given a set of jobs to be completed, with precedence constraints that specify that certain jobs have to be completed before certain other jobs are begun, how can we schedule jobs such that all are completed with the required constraints?

Solution? Topological sort — given a digraph put vertices in order such that all of its directed edges point from a vertex earlier in the order to a vertex later in the order (or report that it's not possible).

#### Cycles in Digraphs

If a cycle exists in a Directed Graph, we need to check for it in order for precedence constraint scheduling to be possible. That is, the graph must be a directed acyclic graph (DAG). 

