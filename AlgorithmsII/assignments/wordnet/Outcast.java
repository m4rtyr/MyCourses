/**
 * @Author: S. Sharma <simulacr4m>
 * @Date:   2020-04-07T15:29:49-05:00
 * @Email:  silentcat@protonmail.com
 * @Last modified by:   simulacr4m
 * @Last modified time: 2020-04-09T23:30:21-05:00
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

  private WordNet wordnet;

  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  public String outcast(String[] nouns) {
    String noun = null;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nouns.length; i++) {
      int di = 0;
      for (int k = 0; k < nouns.length; k++) {
        di += wordnet.distance(nouns[i], nouns[k]);
      }
      if (di > max) {
        max = di;
        noun = nouns[i];
      }
    }
    return noun;
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
