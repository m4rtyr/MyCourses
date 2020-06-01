%.class: %.java
	javac-algs4 $<
	java-algs4 $(patsubst %.java,%,$<) seam/6x5.png

all: SeamCarver.class

clean:
	rm -rf *.class
