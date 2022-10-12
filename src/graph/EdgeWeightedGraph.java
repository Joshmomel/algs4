package graph;

import stacksQueues.Bag;
import stacksQueues.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class EdgeWeightedGraph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int V;
  private int E;
  private final Bag<Edge>[] adj;


  public EdgeWeightedGraph(int V) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
    this.V = V;
    this.E = 0;
    adj = (Bag<Edge>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<>();
    }
  }


  public EdgeWeightedGraph(In in) {
    if (in == null) throw new IllegalArgumentException("argument is null");

    try {
      V = in.readInt();
      adj = (Bag<Edge>[]) new Bag[V];
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
        Edge e = new Edge(v, w, weight);
        addEdge(e);
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
    }

  }

  /**
   * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
   *
   * @param G the edge-weighted graph to copy
   */
  public EdgeWeightedGraph(EdgeWeightedGraph G) {
    this(G.V());
    this.E = G.E();
    for (int v = 0; v < G.V(); v++) {
      // reverse so that adjacency list is in same order as original
      Stack<Edge> reverse = new Stack<>();
      for (Edge e : G.adj[v]) {
        reverse.push(e);
      }
      for (Edge e : reverse) {
        adj[v].add(e);
      }
    }
  }


  public int V() {
    return V;
  }


  public int E() {
    return E;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  /**
   * Adds the undirected edge {@code e} to this edge-weighted graph.
   *
   * @param e the edge
   * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
   */
  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    validateVertex(v);
    validateVertex(w);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  public Iterable<Edge> adj(int v) {
    validateVertex(v);
    return adj[v];
  }


  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  /**
   * Returns all edges in this edge-weighted graph.
   * To iterate over the edges in this edge-weighted graph, use foreach notation:
   * {@code for (Edge e : G.edges())}.
   *
   * @return all edges in this edge-weighted graph, as an iterable
   */
  public Iterable<Edge> edges() {
    Bag<Edge> list = new Bag<>();
    for (int v = 0; v < V; v++) {
      int selfLoops = 0;
      for (Edge e : adj(v)) {
        if (e.other(v) > v) {
          list.add(e);
        }
        // add only one copy of each self loop (self loops will be consecutive)
        else if (e.other(v) == v) {
          if (selfLoops % 2 == 0) list.add(e);
          selfLoops++;
        }
      }
    }
    return list;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V).append(" ").append(E).append(NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v).append(": ");
      for (Edge e : adj[v]) {
        s.append(e).append("  ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    StdOut.println(G);
  }

}
