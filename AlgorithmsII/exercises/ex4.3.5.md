Q: Show that the greedy algorithm is valid even when edge weights are not distinct.

A: Even if edge weights are not distinct, a cut can always be made where the edge(s) present are distinct and uncolored. Once we color an edge $e$ which has the same weight as $f$ we can find a cut where $f$ is the minimum. Therefore, the algorithm still works.

