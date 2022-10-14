package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Bag;

import java.util.NoSuchElementException;

public class EdgeWeightedDigraph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int V;                // number of vertices in this digraph
  private int E;                      // number of edges in this digraph
  private final Bag<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
  private final int[] indegree;             // indegree[v] = indegree of vertex v

  public EdgeWeightedDigraph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
    this.V = V;
    this.E = 0;
    this.indegree = new int[V];
    adj = (Bag<DirectedEdge>[]) new Bag[V];
    for (int v = 0; v < V; v++)
      adj[v] = new Bag<>();
  }

  public EdgeWeightedDigraph(In in) {
    if (in == null) throw new IllegalArgumentException("argument is null");
    try {
      this.V = in.readInt();
      if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
      indegree = new int[V];
      adj = (Bag<DirectedEdge>[]) new Bag[V];
      for (int v = 0; v < V; v++) {
        adj[v] = new Bag<>();
      }

      int E = in.readInt();
      if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
      for (int i = 0; i < E; i++) {
        int v = in.readInt();
        int w = in.readInt();
        validateVertex(v);
        validateVertex(w);
        double weight = in.readDouble();
        addEdge(new DirectedEdge(v, w, weight));
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
    }
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public void addEdge(DirectedEdge e) {
    int v = e.from();
    int w = e.to();
    validateVertex(v);
    validateVertex(w);
    adj[v].add(e);
    indegree[w] += 1;
    E += 1;
  }

  public Iterable<DirectedEdge> adj(int v) {
    validateVertex(v);
    return adj[v];
  }


  public int outdegree(int v) {
    validateVertex(v);
    return adj[v].size();
  }


  public int indegree(int v) {
    validateVertex(v);
    return indegree[v];
  }


  public Iterable<DirectedEdge> edges() {
    Bag<DirectedEdge> list = new Bag<>();
    for (int i = 0; i < V; i++) {
      for (DirectedEdge e : adj(i)) {
        list.add(e);
      }
    }
    return list;
  }


  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V).append(" ").append(E).append(NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v).append(": ");
      for (DirectedEdge e : adj[v]) {
        s.append(e).append("  ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }


  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
    StdOut.println(G);
  }


}
