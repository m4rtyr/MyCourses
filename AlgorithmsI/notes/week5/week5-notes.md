# Balanced Search Trees

## 2-3 Search Trees 

* Allow 1 or 2 keys per node
  * 2-node: one key, two children
  * 3-node: two keys, three children
* Perfect balance — Every path from root to null link has same length
* Symmetric order: Inorder traversal yields keys in ascending order 
* Search
  * Compare search key against keys in node
  * Find interval containing search key (less than, middle, or greater than)
  * Follow associated link.
* Inserting
  * Insert into 2 node at bottom
    * Search for key as usual
    * Replace 2 node with 3 node
  * Insert 3 node to create temporary 4 node
    * Search for key
    * Move middle key in 4-node into parent (split 4-node into 2 2-nodes)
    * If parent was a 3 node, the process would repeat
    * **Height of tree only occurs when root becomes temporary 4 node**
  * Above *local transformation* —constant number of operations — maintain symmetry
* Global properties
  * Maintains symmetric order and perfect balance
  * Worst case and best case are proportional to tree height
    * Worst case (all 2-nodes) —$\lg N$ 
    * Best case: $\log_3 N$ 
    * Between 12 and 20 operations for million nodes
    * Between 18 and 30 operations for billion nodes
* Direct implementation is complicated to maintain multiple node types, tracking links, etc

## Left-leaning Red-Black BSTs

* Represent 2-3 trees as a BST
* Use "internal" left-leaning links as glue for 3 nodes.
  * Create two types of links
    * Black links are ordinary links in a binary tree
    * Red links refer to part of a 3-node
  * Properties
    * No node has two red links connecting it
    * Every path from root to null link has same number of black links
    * Red links lean to the left
  * Search is same for elementary BST (ignoring the color); most other ops are also identical
* Elementary BST Operations
  * Maintain symmetric order and perfect black balance
  * Left-rotation
    * Orient a temporarily right-leaning red link to lean left
  * Right rotation
    * Orient a left-leaning red link to temporarily lean right
  * Color flip
    * Recolor to split a (temporary) 4 node
* Insertion into LLRB
  * Insert into a single 2-node
    * If new key is smaller, then the link to it is red
    * If new key is bigger, then make the link red and right rotate
  * Insert into a 3-node
    * If key inserted is larger, then link colors flipped
    * if key inserted is smaller, then root is rotated and reduced to above case
    * If key inserted in between, then rotate the key left, reducing it to the above case
  * Insert into a 3-node at bottom
    * Do standard BST insert
    * Rotate to balance the 4-node
    * Flip colors to pass red link up one level
    * Rotate to make lean left
    * Repeat case 1 or case 2 up the tree
* Deletion in LLRB
  * Top Down 2-3-4 trees
    * Insertion algorithm for 2-3-4 trees where temporary 4-nodes are allowed
      * Insertion algorithm is based on doing transformations on the way down the path to maintain the invariant that the current node is not a 4-node; transformations on the way up the path to balance any 4-nodes that may have been created
      * Transformations on the way down are same as those used for splitting 4-nodes in 2-3 trees
      * If root is 4 node, split it into three 2-nodes, increasing the height of the tree by one. On the way down the tree, if we encounter a 4-node with a 2-node has parent, split 4-node into 2 2-nodes and pass middle key to parent making it 3-node; if we encounter 4-node w/ 3-node as parent, split 4-node into two 2-nodes and pass middle key to the parent, making it a 4-node
      * 4-node parent to 4-node child is not possible because of above invariant
      * summary: 
        * Represent 4-nodes as balanced subtree of 3 2-nodes w/ both left and right child connected to parent w/ red link
        * Split 4-nodes on the way down the tree w/ color flips
        * Balance 4-nodes on the way up with tree with rotations as for insertion
    * Delete the minimum
      * Cannot delete at 2-node because then we would have null link, but that would violate perfect balance
      * instead, adopt approach to ensure we do not end up w/ 2-node, only 3-node or temporary 4-node
      * At root
        * **Root isn't 2-node** : do nothing
        * **Root is 2-node and children are 2-nodes**: convert the three nodes to a 4-node
        * **Root is 2-node and at least one child is not 2-node**: Borrow from right sibling if necessary to ensure that left child of root isn't 2-node
        * Above means that on the way down the tree, current node is always 2- or 3- node and one of following cases must hold:
          * Left child of current node is not a 2-node, nothing to do
          * Left child is a 2-node and immediate sibling is not a 2-node, move smallest key from sibling to parent and smallest key from parent to left-child
          * If left child and its immediate siblings are 2-nodes, combine them w/ parent to make a 4-node, changing the parent from a 3-node to a 2-node or from a 4-node to a 3-node
        * Following above process we wind up on a 3-node or a 4-node with the smallest key, so we can remove it, converting the 3-node to a 2-node or the 4-node to a 3-node
    * Delete
      * Same transformations along the search path
        * If search key at bottom, remove it
        * If key is not at bottom, exchange it with its successor as in regular BSTs. Since current node is not a 2-node, problem is reduced to deleting the minimum in a subtree whose root is not a 2-node, and we can use the procedure described for that subtree. After deletion, split any remaining 4-nodes on search path on way up from tree.
* Height of tree is $\le 2 \lg N$ in the worst case
  * Proof: Every path from root to `null` link has same number of black links
  * Never two red links in a row
  * Property: Height of tree is ~ $1.00 \lg N$ in average case

# Geometric Applications of BSTs

## Range Search

* Intersections from geometric objects

* Problem: Points on a plane with a rectangle on vertical axis

  * Question: How many points inside rectangles?
  * Another question: How many rectangles on plane intersect?

* 1D Range Search

  * Extension of ordered symbol-table API
    * Insert key-value pair
    * Search for key $k$ 
    * Delete key $k$ 
    * Range search: find all keys between $k_1$ and $k_2$ 
    * Range count: Number of keys between $k_1$ and $k_2$ 
  * Geometric Interpretation
    * Keys are points on a line
    * Find/count points in a given 1D interval
  * Unordered Array Solution
    * Range count and range search are both linear time operations
  * Ordered Array Solution
    * Insert is linear time, range count is $\log N$ , and range search is $R + \log N$ where $R$ is the number of keys that match
  * Binary Search Tree to find keys between `lo` and `hi` 
    * Use `rank()` function to find keys less than a certain key.
    * Nodes examined = search path to `lo` + search path to `hi`  (logarithmic performance)
    * Recursively find all keys in left subtree, check key in current node, recursively find all keys in right subtree.

* Orthogonal Line segment Intersection Search

  * Given $N$ horizontal and vertical line segments, find all intersections
  * Quadratic algorithm: check all pairs of line segments for intersection
  * Nondegeneracy assumption: $x$ and $y$ coordinates are distinct
  * Sweep-line algorithm
    * Sweep vertical line from left-to-right
    * $x$ coordinates define events
    * When line start point is found, insert $y$ coordinate in BST
    * If line end point is found, remove the $y$ coordinate
    * If vertical line segment found, range search for interval of $y$ endpoints
    * Running time is $N \log N$ + $R$ to find $R$ intersections

* Kd Search Trees

  * Find all keys that lie in a 2D Range?

  * Number of keys that lie in a 2D Range?

  * Geometric Interpretation

    * Keys are points in the plane
    * Find/count points in a given axis-aligned rectangle

  * Grid implementation

    * Divide space into $M$ by $M$ grid of squares
    * Create list of points contained in each square
    * Use 2D array to directly index relevant squares
    * Insert: add $(x, y)$ to list for corresponding square
    * Range search: examine only squares that intersect the 2D range query
    * Space-time tradeoff
      * Space: $M^2 + N$
      * Time: $1 + N / M^2$ per square, on average
    * Choose grid square size to tune performance
      * Rule of thumb: $\sqrt{N}$ by $\sqrt{N}$ 
    * Running time (for evenly distributed points)
      * Insert: 1
      * Range search: 1 per point in range
    * Problem:
      * Clustering is well-known phenomenon, so guarantee of performance is broken

  * Space-partitioning trees

    * Use a tree to represent a recursive subdivision of 2d space (divide into 2 halfplanes)
    * 2D tree construction
      * Recursively partition plane into two halfplanes
      * Draw vertical line through point to divide plane
        * All points on left/down of point are on left part of tree and on right/up of point on right part of tree
        * Alternate between vertical and horizontal comparison of points on each level
      * Goal: find all points in a query axis-aligned rectangle
        * Check if point in node lies in given rectangle
        * Recursively search left/bottom if any could fall in rectangle
        * Recursively search right/top if any could fall in rectangle
        * Typical case: $R + \log N$ and in worst-case (balanced tree): $R + \sqrt{N}$ 
      * Goal: Find closeset point to query point (Nearest neighbor search)
        * Recursively search left/bottom if it contains closer point
        * Recursively search right/top if it could contain a closer point
        * Organize method so that it begins by searching for a query point
        * Typical case: $\log N$ 
        * Worst case (for balanced tree): $N$ 
    * KD Tree
      * Recursively partition k-dimensional space into 2 half spaces

  * Interval Search Trees

    * 1D interval search — Data structure to hold set of overlapping intervals

    * Insert interval $(lo, hi)$

    * Search for an interval $(lo, hi)$ 

    * Interval intersection query: Given an interval $(lo, hi)$ find all intervals in data structure that intersect that interval $(lo, hi)$ 

    * Create BST, where each node stores an interval

      * Use left endpoint of interval as key
      * Store max endpoint in subtree rooted at node

    * How to search for intersection?

      * If interval in node intersects query interval return
      * Else if left subtree is `null` go right
      * Else if max endpoint in left subtree is less than $lo$ go right
      * Else go left 

    * Analysis

      * Case 1: If search goes right, no intersection in left
        * Left subtree is empty no intersection
        * Max endpoint in left subtree is less than $lo$ for any interval $(a, b)$ in left subtree, $b \le max < lo$ 

      * Case 2: If search goes left, intersection or none
        * Suppose no intersection in left
        * Then for interval $(a, b)$ in right subtree of $x$, $hi < c \le a$ where $c$ is endpoint of $(c, max)$ meaning no intersection

      * If red-black BST to guarantee performance, then $\log N$ performance for insert, find, delete, and intersection

      

      



