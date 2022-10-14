package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Stack;

public class DijkstraSP {
  private final double[] distTo;          // distTo[v] = distance  of shortest s->v path
  private final DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
  private final IndexMinPQ<Double> pq;

  public DijkstraSP(EdgeWeightedDigraph G, int s) {
    edgeTo = new DirectedEdge[G.V()];
    distTo = new double[G.V()];
    pq = new IndexMinPQ<>(G.V());


    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    distTo[s] = 0.0;

    pq.insert(s,  distTo[s]);
    while (!pq.isEmpty()) {
      int v = pq.delMin();
      for (DirectedEdge e : G.adj(v)) {
        relax(e);
      }
    }

    assert check(G, s);
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] > distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
      else pq.insert(w, distTo[w]);
    }
  }

  private Iterable<DirectedEdge> pathTo(int t) {
   Stack<DirectedEdge> path = new Stack<>();
    for (DirectedEdge e = edgeTo[t]; e != null; e = edgeTo[e.from()]) {
      path.push(e);
    }
    return path;
  }

  private boolean hasPathTo(int t) {
    return distTo[t] < Double.POSITIVE_INFINITY;
  }

  private double distTo(int t) {
    return distTo[t];
  }

  private boolean check(EdgeWeightedDigraph G, int s) {

    // check that edge weights are non-negative
    for (DirectedEdge e : G.edges()) {
      if (e.weight() < 0) {
        System.err.println("negative edge weight detected");
        return false;
      }
    }

    // check that distTo[v] and edgeTo[v] are consistent
    if (distTo[s] != 0.0 || edgeTo[s] != null) {
      System.err.println("distTo[s] and edgeTo[s] inconsistent");
      return false;
    }
    for (int v = 0; v < G.V(); v++) {
      if (v == s) continue;
      if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
        System.err.println("distTo[] and edgeTo[] inconsistent");
        return false;
      }
    }

    // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
    for (int v = 0; v < G.V(); v++) {
      for (DirectedEdge e : G.adj(v)) {
        int w = e.to();
        if (distTo[v] + e.weight() < distTo[w]) {
          System.err.println("edge " + e + " not relaxed");
          return false;
        }
      }
    }

    // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
    for (int w = 0; w < G.V(); w++) {
      if (edgeTo[w] == null) continue;
      DirectedEdge e = edgeTo[w];
      int v = e.from();
      if (w != e.to()) return false;
      if (distTo[v] + e.weight() != distTo[w]) {
        System.err.println("edge " + e + " on shortest path not tight");
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
    int s = Integer.parseInt(args[1]);

    // compute shortest paths
    DijkstraSP sp = new DijkstraSP(G, s);


    // print shortest path
    for (int t = 0; t < G.V(); t++) {
      if (sp.hasPathTo(t)) {
        StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
        for (DirectedEdge e : sp.pathTo(t)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d         no path\n", s, t);
      }
    }
  }


}
