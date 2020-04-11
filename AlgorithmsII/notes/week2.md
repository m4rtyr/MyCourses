# Minimum Spanning Trees

Edge-weighted graph — graph model where weights/costs associated w/ each edge

Spanning Tree — Connected subgraph with no cycles that includes all vertices. Minimum spanning tree of edge-weighted graph is spanning tree whose weight (sum of weight of its edges) is no larger than weight of any other spanning tree.

## Assumptions 

* Graph is connected; if not connected, algorithms can compute MSTs of forests to compute minimum spanning forest.
* Edge weights are not necessarily distances
* Edge weights may be negative or zero.
* Edge weights are all different; if edges have equal weights, minimum spanning tree may not be unique (though algorithms are still correct even with multiple MSTs).

## Underlying Principles

Defining properties of trees:

* Adding edge connecting two vertices in tree creates a unique cycle.
* Removing an edge from a tree breaks it into two separate subtrees.

Cut property of MSTs — *cut* of a graph is a partition of its vertices into two nonempty disjoint sets. A *crossing edge* of a cut is an edge that connects a vertex in one set with a vertex in the other. Given any cut in an edge-weighted graph, the crossing edge of minimum weight is in the MST of the graph.

*Proof*

Let *e* be the crossing edge of minimum weight and let *T* be the MST. Suppose that *T* does not contain *e*. This graph has a cycle that contains *e* (since it's a tree and we have connected two vertices). That cycle must contain at least one other crossing edge ($f$) which has higher weight than $e$. We can get a spanning tree of strictly lower weight by deleting $f$ and adding $e$, contradicting the hypothesis.

### Greedy Algorithm

A greedy algorithm for finding an MST is to apply the cut property to accept an edge as an MST edge, continuing until finding all of the MST edges. 

In an edge-weighted graph with $V$ vertices, the following algorithm colors the edges of the MST black: start by coloring all edges gray, find a cut with no black crossing edges, color its minimum weight crossing edge black, and continue until $V-1$ have been colored black.

*Proof*

If all edge weights are different, by the cut property, any edge that is colored black is in the MST. If fewer than $V-1$ edges are black, a cut with no black crossing edges exists (the graph is assumed to be connected). Once the $V-1$ edges are black, the black edges form a spanning tree.

## Edge-weighted Graph Data Type

The following creates a data type that explicitly represents edges:

```java
public class Edge implements Comparable<Edge>
  Edge(int v, int w, double weight)	// Initializing constructor
  double weight()										// Weight of this edge
  int either()											// Either of this edge's vertices
  int other(int v)									// The other vertex
  int compareTo(Edge that)					// compare this edge to that
  String toString()									// string representation
```

```java
public class EdgeWeightedGraph
  EdgeWeightedGraph(int V)					// create a V-vertex graph w/ no edges
  EdgeWeightedGraph(In in)					// Create graph from input in
  int V()														// number of vertices
  int E()														// number of edges
  void addEdge(Edge e)							// add edge e to this graph
  Iterable<Edge> adj(int v)					// Return all edges incident to v
  Iterable<Edge> edges()						// All edges in this graph
  String toString()									// string representation
```

## MST API and Test Client

The following is an API for MSTs:

```java
public class MST
  MST(EdgeWeightedGraph G)			// constructor
  Iterable<Edge> edges()				// all of the MST edges
  double weight()								// weight of MST
```



## Prim's Algorithm

Prim's algorithm: attaches a new edge to a single edge growing tree at each step. Start w/ any vertex as a single-vertex tree. Then add $V-1$ edges to it, always taking next the minimum weight edge that connects a vertex on the tree to one that is not on the tree. In accordance with the greedy algorithm, Prim's algorithm takes the crossing edge of minimal weight.

The main problem: how to efficiently find crossing edge of minimal weight? Crossing edges are placed in a minimal priority queue that compares edges by weight.

### Maintaining set of crossing edges

Each time an edge is added to the tree, the vertex that is connected by that edge is added. To maintain the set of crossing edges, we need to add priority queue all edges from that vertex to any non-tree vertex (marked array to identify whether vertex is in tree or not). Any edge connecting the vertex just added to a tree vertex that is already on the priority queue becomes ineligible because it connects two tree vertices. 

There are two ways to deal with the eligibility: a lazy implementation vs an eager implementation. 

### Performance 

Prim's algorithm takes time proportional to $E \log E$ in the worst case where $E$ is the # of edges (insertion/deletion cost is proportional to $\log E$ (for the lazy version).

Prim's algorithm takes time proportional to $E \log V$ in the worst case where $E$ is the # of edges and $V$ is the number of vertices.

## Kruskal's Algorithm

Kruskal's algorithm: Takes edges in order of weight value, taking for MST each edge that doesn't form a cycle with edges previously added, stopping after adding $V-1$ edges. This computes the MST since the edges added are crossing edges, and they are the crossing edges of minimal weight.

### Performance

Kruskal's algorithm uses space proportional to $E$ and time proportional to $E \log E$ to compute the MST.

# Shortest Paths

A shortest path in an edge-weighted digraph is a directed path between two vertices with the lowest weight of all alternate paths.

## Properties of Shortest Paths

* Paths are directed
* Weights are not necessarily distances
* Unreachable vertices might exist
* Paths can repeat vertices and edges
* Shortest paths are normally simple
* Shortest paths are not necessarily unique
* Parallel edges and self-loops may be present
* Negative weights introduce complications

## Shortest Path Tree

Single-source shortest paths problem — given a source vertex, the result of the computation is a tree known as the shortest-paths tree, which gives a shortest path from $s$ to every other vertex reachable from $s$.

## Edge-weighted Digraph Data Types

```java
public class DirectedEdge
	DirectedEdge(int v, int w, double weight)
  double weight()														// Weight of edge
  int from()																// Vertex edge points from
  int to()																	// Vertex edge points to
  String toString()													// String representation
  
public class EdgeWeightedDigraph
  EdgeWeightedDigraph(int V)			 	// create a h w/ 0 edges and V vertex
  EdgeWeightedDigraph(In in)			  // create a digraph from input in
  int V()													  // Number of vertices
  int E()													  // Number of edges
  void addEdge(DirectedEdge e)		  // add edge e to this digraph
  Iterable<DirectedEdge> adj(int v) // all edges leaving vertex v
  Iterable<DirectedEdge> edges()		// all edges in this digraph
  String toString()									// string representation
```

## Shortest paths API

Same paradigm as DFS and BFS API.

Data structures for shortest paths:

* Edges on the shortest-paths tree — edgeTo array
* Distance to Source represented with distTo array

### Edge Relaxation

To *relax* an edge `v->w` means to test whether the best known way from $s$ to $w$ is to go from $s$ to $v$, then take the edge from $v$ to $w$. Vertex relaxation is the same principle where we iterate through a vertex's edges and determine if the path to some other vertex $w$ can be relaxed.

## Theoretical Basis for Shortest Path Algorithms

Optimality conditions — The values of distTo[v] for some vertex v in an edge-weighted digraph G is the shorest path iff `distTo[s] = 0`, `distTo[w] <= distTo[v].+ e.weight()` for each edge $e$ from $v$ to $w$.

See book for Proof.

The optimality conditions lead to a generic algorithm: initialize `distTo[s] = 0` and all other `distTo[]` values to infinity and relax any edge in $G$ continuing until no edge is eligible. For any vertex $w$ reachable from $s$, some edge on the shortest path to $w$ is eligible as long as `distTo[w]` remains infinite, so the algorithm continues until the `distTo[]` value of each vertex reachable from $s$ is the length of some path to that vertex. For any vertex $v$ for which the shorest path is well-defined, the algorithm causes `distTo[v]` is the length of some simple path from $s$ to $v$ and is strictly monotonically decreasing.

## Dijkstra's Algorithm

`distTo[s]` is initialized to zero and other entries are initialized to positive infinity. We relax and add to the tree a non-tree vertex with the lowest `distTo[]` value continuing until all vertices are on the tree or no non-tree vertex has a finite `distTo[]` value.

If $v$ is reachable from the source, every `v->w` edge is relaxed exactly once. When $v$ is relaxed, it leaves `dist[w] <= distTo[v] + e.weight()` This inequality holds until the algorithm completes since `distTo[w]` can only decrease and `distTo[v]` never changes. Therefore, the optimality conditions are maintained. An indexed priority queue allows for priorities of keys (edge weights) to be changed.

Dijkstra's algorithm can also be used for edge-weighted undirected graphs (convert the graph to a directed graph by having a directed edge pointing from $v$ to $w$ and another edge that points from $w$ to $v$).

## Acyclic Edge-Weighted Digraphs

By relaxing vertices in **topological order**, we can solve the single-source shorest paths problem for edge-weighted DAGs in time proportional to $E+V$.

*Proof*

Every edge `v->w` is relaxed exactly once. The inequality `distTo[w] = distTo[v] + e.weight()` until the algorithm completes since `distTo[v]` never changes (the topological order means that no edge entering $v$ will be processed after $v$ is relaxed). `distTo[w]` can only decrease. So, when all vertices reachable from $s$ have been added to tree, the optimality conditions hold. Topological sorting takes time proportional to $E + V$ and the relaxation of each edge takes time proportional to $E + V$.

Finding the longest path can be solved. Make a copy of the DAG and negate all edge weights. This causes the shortest path in the copy to be the longest path in the original. This is also done in time proportional to $E + V$.

