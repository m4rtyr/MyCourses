# Hash Tables

* Save items in a key-indexed table (index is a function of the key)
* **Hash function** — Method for computing array index from key
* Ex: `hash("it") = 3`
* Issues
  * Computing the hash function
  * Equality test: Method for checking whether two keys are equal
  * Collision resolution: Algorithm and data structure to handle two keys that hash to the same array index
* Classic Space-time tradeoff
  * No space limitation — trivial hash function with key as index (array)
  * No time limitation — trivial collision resolution with sequential search



* Idealistic goal: Scramble keys uniformly to produce a table index
  * Efficiently computable
  * Each table index equally likely for each key
* Ex. Phone Numbers
  * Bad: first three digits
  * Better: last three digits
* Ex2. Social security numbers
  * Bad: first three digits
  * Better: last three digits
* Practical challenge: need different approach for each key type
* String hashing
  * Horner's method to hash string of Length $L$: $L$ multiplies/adds
  * Equivalent to $h = s[0] * 31^{L-1} + ... + s[L - 3] * 31^2 + s[L - 2] * 31^1 + s[L-1] * 31^0$ 
  * Performance optimization
    * Cache the hash value in an instance variable
    * Return cached value
* Implementing hash code for user-defined type
  * Initialize hash to nonzero constant
  * $hash = p*hash + field.hashCode()$ for each field where $p$ is a small prime
* In theory, keys are bitstring; "universal" hash functions exist
* Modular hashing
  * Hash code (in Java) returns value between $-2^{31}$ and $2^{31} - 1$ 
  * Hash function: return integer between 0 and $M - 1$ for index array (modulo $M$) 
  *  Taking absolute value of hash-code in Java causes bug where $-2^{31}$ is returned, so taking first 31 bits is necessary.
* Uniform hashing assumption
  * Each key is equally likely to hash to an integer between 0 and $M - 1$
  * Birthday problem: Expect two items to appear in same index after ~ $\sqrt{\pi M / 2}$ tosses
  * Coupon collector: Expect every index has $\ge 1$ item after ~ $M \ln M$ tosses
  * Load balancing: After $M$ inserts into array, most loaded index has $\theta (\log M / \log \log M)$ items
* Dealing with Collisions
  * Separate chaining
    * Use an array of $M < N$ linked lists
      * Hash: map key to integer between $i$ between $0$ and $M - 1$
      * Insert: Put at front of $i^{th}$ chain (if not already there)
      * Search: need to search only $i^{th}$ chained 
    * Analysis
      * Under uniform hashing assumption, prob. that the number of keys in a list is within a constant factor of $N / M$ is extremely close to 1.
        * Proof: Distribution of list size obeys binomial distribution
        * Consequence: Number of probes for search/insert is proportional to $N / M$ ; $M$ too large $\rarr$ too many empty chains; $M$ too small $\rarr$ chains too long. Typical choice: $M \approx N / 5$ 
        * Preferable for short keys where ordered symbol table operations not needed. 
  * Linear Probing
    * Open addressing — When new key collides, find next empty slot, and put it there
    * Hash: Map key to integer between $i$ between $0$ and $M - 1$ 
    * Insert: Put at table index $i$ if free; if not try consecutive indices; wrap-around if run off end of list
    * Search: Check index $i$ , if not there check consecutive indices; wrap around if run off end of list
    * Array size $M$ must be greater than number of key-value pairs $N$ 
    * Good for if memory is not a problem
    * Clustering: New keys likely to hash into middle of big clusters.
    * Knuth's parking problem
      * Cars arrive at one-way street with $M$ parking spaces
      * Each desires a random space $i$ if space $i$ is taken, try consecutive indices
      * What is mean displacement of car?
        * Half-full: With $M / 2$ cars, mean displacement is ~ $ 3/ 2$ 
        * Full: With $M$ cars, mean displacement is ~ $\sqrt{\pi M / 8}$ 
    * Analysis
      * Under uniform hashing assumption, average # of probes in a linear probing hash table of size $M$ that contains $N = \alpha M$ is:
      * ~ $$\frac{1}{2}(1 + \frac{1}{1 - \alpha})$$  for search hit
      * ~ $$\frac{1}{2}\biggl(1 + \frac{1}{(1 - \alpha)^2}\biggl)$$ for search miss/ insert
      * As $\alpha$ closes in on (when $N$ becomes closer to $M$) the cost grows
      * Typical choice $\alpha = N / M$ ~ $\frac{1}{2}$ 