package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SymbolDigraph {
  private final ST<String, Integer> st;
  private final String[] keys;
  private final Digraph graph;

  public SymbolDigraph(String filename, String delimiter) {
    st = new ST<>();

    // First pass builds the index by reading strings to associate
    // distinct strings with an index
    In in = new In(filename);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(delimiter);
      for (String s : a) {
        if (!st.contains(s))
          st.put(s, st.size());
      }
    }

    // inverted index to get string keys in an array
    keys = new String[st.size()];
    for (String name : st.keys()) {
      keys[st.get(name)] = name;
    }

    // second pass builds the digraph by connecting first vertex on each
    // line to all others
    graph = new Digraph(st.size());
    in = new In(filename);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(delimiter);
      int v = st.get(a[0]);
      for (int i = 1; i < a.length; i++) {
        int w = st.get(a[i]);
        graph.addEdge(v, w);
      }
    }
  }

  public boolean contains(String s) {
    return st.contains(s);
  }

  public int indexOf(String s) {
    return st.get(s);
  }

  public String nameOf(int v) {
    validateVertex(v);
    return keys[v];
  }

  public Digraph digraph() {
    return graph;
  }

  private void validateVertex(int v) {
    int V = graph.V();
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
  }

  public static void main(String[] args) {
    String filename  = args[0];
    String delimiter = args[1];
    SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
    Digraph graph = sg.digraph();
    while (!StdIn.isEmpty()) {
      String t = StdIn.readLine();
      for (int v : graph.adj(sg.indexOf(t))) {
        StdOut.println("   " + sg.nameOf(v));
      }
    }
  }
}
