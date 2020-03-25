SOLVE_FILE=8puzzle/puzzle4x4-30.txt


all:
	javac-algs4 Board.java
	java-algs4 Board < $(SOLVE_FILE)
	javac-algs4 Solver.java
	java-algs4 Solver $(SOLVE_FILE) 

Board: Board.java
	javac-algs4 Board.java
	java-algs4 Board < $(SOLVE_FILE)

Solver: Solver.java
	javac-algs4 Solver.java
	java-algs4 Solver $(SOLVE_FILE)

clean:
	rm -rf *.class
