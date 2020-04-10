/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-07T14:04:27-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-09T23:32:09-05:00
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

  private SAP sap;
  private ST<String, Queue<Integer>> nounTable;
  private ST<Integer, String> idTable;
  private Digraph dag;

  private void readWords(String words, int id) {
    String[] wordList = words.split(" "); // Split the synset by space
    for (String word : wordList) { // Go through the synset
      if (nounTable.get(word) != null) { // If there is not a list of synset IDs
        nounTable.get(word).enqueue(id); // Enqueue the id onto the queue
      } else {
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(id); // Enqueue the id into the new queue
        nounTable.put(word, q); // put the queue as the entry for word
      }
    }
  }

  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null)
      throw new IllegalArgumentException();
    int V = 0;
    In synset = new In(synsets);
    In hypernym = new In(hypernyms);
    nounTable = new ST<String, Queue<Integer>>();
    idTable = new ST<Integer, String>();
    /* Go through the synsets line-by-line */
    while (synset.hasNextLine()) {
      String[] line = synset.readLine().split(","); // Split each line by comma
      idTable.put(Integer.parseInt(line[0]), line[1]); // ID -> whole Synset
      readWords(line[1], Integer.parseInt(line[0])); // Noun -> List of IDs
      V++; // Each whole synset is a vertex
    }
    boolean rootFound = false;
    dag = new Digraph(V); // Create a new Digraph
    while (hypernym.hasNextLine()) { // Read Hypernyms
      String[] line = hypernym.readLine().split(","); // Read each line by comma
      int hyponym = Integer.parseInt(line[0]);
      for (int i = 1; i < line.length; i++) {
          dag.addEdge(hyponym, Integer.parseInt(line[i]));
      }
      if (line.length == 1)
        rootFound = true;
    }
    /* Check for unrooted DAGs */
    DirectedCycle c = new DirectedCycle(dag);
    if (c.hasCycle())
      throw new IllegalArgumentException();
    if (!rootFound)
      throw new IllegalArgumentException();
    sap = new SAP(dag); // Finds shortest ancestral paths in the DAG
  }

  public Iterable<String> nouns() {
    return nounTable.keys();
  }

  public boolean isNoun(String word) {
    return nounTable.contains(word);
  }

  public int distance(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB))
      throw new IllegalArgumentException();
    Queue<Integer> synsetA = nounTable.get(nounA); // Get the list of IDs for A
    Queue<Integer> synsetB = nounTable.get(nounB); // Get the list of IDs for B
    return sap.length(synsetA, synsetB); // Find the distance for each v to w
  }

  public String sap(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB))
      throw new IllegalArgumentException();
    Queue<Integer> synsetA = nounTable.get(nounA); // Get list of IDs for A
    Queue<Integer> synsetB = nounTable.get(nounB); // Get list of IDs for B
    int ancestor = sap.ancestor(synsetA, synsetB); // Get the ancestor of v & w
    return idTable.get(ancestor); // Get the ID corresponding to that synset
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    while (!StdIn.isEmpty()) {
      String v = StdIn.readString();
      String w = StdIn.readString();
      int length      = wordnet.distance(v, w);
      String ancestor = wordnet.sap(v, w);
      StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
    }
  }
}
