package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import heap.MinPQ;
import stacksQueues.Queue;

public class LazyPrimMST {
  private final boolean[] marked;
  private final Queue<Edge> mst;
  private final MinPQ<Edge> pq;
  private double weight;

  public LazyPrimMST(EdgeWeightedGraph G) {
    pq = new MinPQ<>();
    mst = new Queue<>();
    marked = new boolean[G.V()];
    visit(G, 0);

    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either();
      int w = e.other(v);
      if (marked[v] && marked[w]) continue;
      mst.enqueue(e);
      weight += e.weight();
      if (!marked[v]) visit(G, v);
      if (!marked[w]) visit(G, w);
    }

  }

  private void visit(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    for (Edge e : G.adj(v)) {
      if (!marked[e.other(v)]) {
        pq.insert(e);
      }
    }
  }


  public Iterable<Edge> mst() {
    return mst;
  }

  public double weight() {
    return weight;
  }


  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    LazyPrimMST mst = new LazyPrimMST(G);
    for (Edge e : mst.mst()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mst.weight());
  }

}
