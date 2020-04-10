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

