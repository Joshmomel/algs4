package graph;

import stacksQueues.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstSearch {
  private final boolean[] marked;    // marked[v] = is there an s-v path?
  private final int[] edgeTo;        // edgeTo[v] = last edge on s-v path
  private final int s;         // source vertex


  public DepthFirstSearch(Graph G, int s) {
    this.s = s;
    edgeTo = new int[G.V()];
    marked = new boolean[G.V()];
    validateVertex(s);
    dfs(G, s);
  }

  // depth first search from v
  private void dfs(Graph G, int v) {
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
        dfs(G, w);
      }
    }
  }


  public boolean hasPathTo(int v) {
    validateVertex(v);
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {
    validateVertex(v);
    if (!hasPathTo(v)) return null;
    Stack<Integer> path = new Stack<>();
    for (int x = v; x != s; x = edgeTo[x])
      path.push(x);
    path.push(s);
    return path;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Graph G = new Graph(in);
    System.out.println(G);

    int s = 0;
    DepthFirstSearch dfs = new DepthFirstSearch(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (dfs.hasPathTo(v)) {
        StdOut.printf("%d to %d:  ", s, v);
        for (int x : dfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else StdOut.print("-" + x);
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d:  not connected\n", s, v);
      }
    }
  }
}
