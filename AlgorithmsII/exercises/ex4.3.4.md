Q: Consider the assertion that an edge-weighted graph has a unique MST *only* if its edge weights are distinct. Give a proof or a counterexample.

A: Applying the greedy algorithm on a graph with non-distinct edges, that means that there are cuts without black crossing edges where there are multiple minimum crossing edges. Therefore, there can be multiple edges that the MST can possess, giving the possibility for multiple MSTs to exist. Thus, a unique MST can be present only when the edge weights are distinct.

