Q: Criticize the following idea: To implement *find the maximum* in constant
time, why not use a stack or a queue, but keep track of the maximum value
inserted so far, then return that value for *find the maximum*?

A: The problem is that if we remove this maximum value, then we have no way
of finding the next maximum value without iterating through the stack or queue.
