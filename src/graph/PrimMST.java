package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Queue;

public class PrimMST {
  private final Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex

  public PrimMST(EdgeWeightedGraph G) {
    edgeTo = new Edge[G.V()];
    // distTo[v] = weight of shortest such edge
    double[] distTo = new double[G.V()];
    // marked[v] = true if v on tree, false otherwise
    boolean[] marked = new boolean[G.V()];
    IndexMinPQ<Double> pq = new IndexMinPQ<>(G.V());

    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }

    for (int s = 0; s < G.V(); s++) {
      if (!marked[s]) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
          int v = pq.delMin();
          marked[v] = true;
          for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            if (!marked[w]) {
              if (edge.weight() < distTo[w]) {
                distTo[w] = edge.weight();
                edgeTo[w] = edge;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
              }
            }
          }
        }
      }
    }
  }


  public Iterable<Edge> edges() {
    Queue<Edge> queue = new Queue<>();
    for (Edge edge : edgeTo) {
      if (edge != null) queue.enqueue(edge);
    }
    return queue;
  }

  public double weight() {
    double w = 0.0;
    for (Edge edge : edges()) {
      w += edge.weight();
    }
    return w;
  }


  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    PrimMST mst = new PrimMST(G);
    for (Edge e : mst.edges()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mst.weight());
  }

}
