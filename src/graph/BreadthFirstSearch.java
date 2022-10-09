package graph;

import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Stack;

public class BreadthFirstSearch {
  private static final int INFINITY = Integer.MAX_VALUE;
  private boolean[] marked;  // marked[v] = is there an s-v path
  private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
  private int[] distTo;      // distTo[v] = number of edges shortest s-v path

  /**
   * Computes the shortest path between the source vertex {@code s}
   * and every other vertex in the graph {@code G}.
   */
  public BreadthFirstSearch(Graph G, int s) {
    marked = new boolean[G.V()];
    distTo = new int[G.V()];
    edgeTo = new int[G.V()];
    validateVertex(s);
    bfs(G, s);

    assert check(G, s);
  }

  private void bfs(Graph G, int s) {

  }

  public boolean hasPathTo(int v) {
    validateVertex(v);
    return marked[v];
  }

  public int distTo(int v) {
    validateVertex(v);
    return distTo[v];
  }

  public Iterable<Integer> pathTo(int v) {
    validateVertex(v);
    if (!hasPathTo(v)) return null;
    Stack<Integer> path = new Stack<>();
    int x;
    for (x = v; distTo[x] != 0; x = edgeTo[x])
      path.push(x);
    path.push(x);
    return path;
  }

  private boolean check(Graph G, int s) {

    // check that the distance of s = 0
    if (distTo[s] != 0) {
      StdOut.println("distance of source " + s + " to itself = " + distTo[s]);
      return false;
    }

    // check that for each edge v-w dist[w] <= dist[v] + 1
    // provided v is reachable from s
    for (int v = 0; v < G.V(); v++) {
      for (int w : G.adj(v)) {
        if (hasPathTo(v) != hasPathTo(w)) {
          StdOut.println("edge " + v + "-" + w);
          StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
          StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
          return false;
        }
        if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
          StdOut.println("edge " + v + "-" + w);
          StdOut.println("distTo[" + v + "] = " + distTo[v]);
          StdOut.println("distTo[" + w + "] = " + distTo[w]);
          return false;
        }
      }
    }

    // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
    // provided v is reachable from s
    for (int w = 0; w < G.V(); w++) {
      if (!hasPathTo(w) || w == s) continue;
      int v = edgeTo[w];
      if (distTo[w] != distTo[v] + 1) {
        StdOut.println("shortest path edge " + v + "-" + w);
        StdOut.println("distTo[" + v + "] = " + distTo[v]);
        StdOut.println("distTo[" + w + "] = " + distTo[w]);
        return false;
      }
    }

    return true;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }
}
