package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Stack;

public class AcyclicSP {
  private final double[] distTo;
  private final DirectedEdge[] edgeTo;

  public AcyclicSP(EdgeWeightedDigraph G, int s) {
    edgeTo = new DirectedEdge[G.V()];
    distTo = new double[G.V()];

    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    distTo[s] = 0.0;

    Topological topological = new Topological(G);

    for (int v : topological.order()) {
      for (DirectedEdge e : G.adj(v)) {
        relax(e);
      }
    }
  }

  private void relax(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    if (distTo[w] > distTo[v] + e.weight()) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
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


  public static void main(String[] args) {
    In in = new In(args[0]);
    int s = Integer.parseInt(args[1]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

    // find the shortest path from s to each other vertex in DAG
    AcyclicSP sp = new AcyclicSP(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (sp.hasPathTo(v)) {
        StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
        for (DirectedEdge e : sp.pathTo(v)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d         no path\n", s, v);
      }
    }
  }
}
