Q: Prove that the height of a 2-3 tree with $n$ keys is between $\lfloor \log_3n\rfloor \approx 0.63 \lg N$ (for a tree that is all 3-nodes) and $\lfloor \lg n \rfloor$ (for a tree that is all 2-nodes).

A:

In a tree with all 3-nodes, the height of the tree will only change when $n = 3^k$. Therefore, if $3^k \le n < 3^{k+1}$, then the height of the tree will be $\lfloor\log_3 n\rfloor$.

A similar argument can be made for $\lfloor \lg N \rfloor$. 

A tree with 2-nodes and 3-nodes intermixed will have to be between these heights, since (a) for a 2-3 tree the maximum number of links for a node is 3, so if a key were to be inserted into a 3-node, it would have to be passed up to its parent, either increasing the height of the tree or being placed into a 2-node and (b) for a 2-3 tree the minimum number of links for a node is 0, so if a key were to be inserted into the tree, it would either become a child of an existing 2-node, becoming part of a 2-node itself (thus making it a 3-node and minimizing the height), or being added to the bottom of the tree, extending its height. 