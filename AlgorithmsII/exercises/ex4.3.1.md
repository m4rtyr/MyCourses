Q: Prove that you can rescale the weights by adding a positive
constant to all of them or by multiplying them all by a positive constant
without affecting the MST.

A: Let's assume we add a positive constant $\epsilon$ to each edge in the graph. Let

the set of edges on the MST be $E_m$ and the rest of the edges be in a separate

set called $\bar{E_m}$. Let's define the $<$ operator to mean that $\forall x, y \in E_m, \bar{E_m} x < y$. Therefore, if we add a positive constant to each edge, then the inequality becomes $x + \epsilon < y + \epsilon$ and if we were to multiply by a positive constant $k$ then the inequality is preserved as $kx < ky$.