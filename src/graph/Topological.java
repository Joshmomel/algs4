package graph;

import edu.princeton.cs.algs4.StdOut;

public class Topological {
  private final Iterable<Integer> order;
  private final int[] rank;

  public Topological(Digraph G) {
    DepthFirstOrder dfs = new DepthFirstOrder(G);
    order = dfs.reversePost();
    rank = new int[G.V()];
    int i = 0;
    for (int k : order) {
      rank[k] = i++;
    }
  }

  public Topological(EdgeWeightedDigraph G) {
    DepthFirstOrder dfs = new DepthFirstOrder(G);
    order = dfs.reversePost();
    rank = new int[G.V()];
    int i = 0;
    for (int k : order) {
      rank[k] = i++;
    }
  }

  public Iterable<Integer> order() {
    return order;
  }

  public boolean hasOrder() {
    return order != null;
  }

  public int rank(int v) {
    validateVertex(v);
    if (hasOrder()) return rank[v];
    else return -1;
  }

  private void validateVertex(int v) {
    int V = rank.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    String filename = args[0];
    String delimiter = args[1];
    SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
    Topological topological = new Topological(sg.digraph());
    for (int v : topological.order()) {
      StdOut.println(sg.nameOf(v));
    }
  }
}
