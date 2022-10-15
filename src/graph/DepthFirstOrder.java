package graph;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Queue;
import stacksQueues.Stack;

public class DepthFirstOrder {
  private final boolean[] marked;          // marked[v] = has v been marked in dfs?
  private final int[] pre;                 // pre[v]    = preorder  number of v
  private final int[] post;                // post[v]   = postorder number of v
  private final Queue<Integer> preorder;   // vertices in preorder
  private final Queue<Integer> postorder;  // vertices in postorder
  private int preCounter;            // counter or preorder numbering
  private int postCounter;

  public DepthFirstOrder(Digraph G) {
    pre = new int[G.V()];
    post = new int[G.V()];
    postorder = new Queue<>();
    preorder = new Queue<>();
    marked = new boolean[G.V()];
    for (int v = 0; v < G.V(); v++)
      if (!marked[v]) dfs(G, v);

    assert check();
  }

  /**
   * Determines a depth-first order for the edge-weighted digraph {@code G}.
   *
   * @param G the edge-weighted digraph
   */
  public DepthFirstOrder(EdgeWeightedDigraph G) {
    pre = new int[G.V()];
    post = new int[G.V()];
    postorder = new Queue<>();
    preorder = new Queue<>();
    marked = new boolean[G.V()];
    for (int v = 0; v < G.V(); v++)
      if (!marked[v]) dfs(G, v);
  }

  // run DFS in digraph G from vertex v and compute preorder/postorder
  private void dfs(Digraph G, int v) {
    marked[v] = true;
    pre[v] = preCounter++;
    preorder.enqueue(v);
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
    postorder.enqueue(v);
    post[v] = postCounter++;

  }

  // run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder
  private void dfs(EdgeWeightedDigraph G, int v) {
    marked[v] = true;
    pre[v] = preCounter++;
    preorder.enqueue(v);
    for (DirectedEdge e : G.adj(v)) {
      int w = e.to();
      if (!marked[w]) {
        dfs(G, w);
      }
    }
    postorder.enqueue(v);
    post[v] = postCounter++;
  }


  public int pre(int v) {
    validateVertex(v);
    return pre[v];
  }


  public int post(int v) {
    validateVertex(v);
    return post[v];
  }


  public Iterable<Integer> post() {
    return postorder;
  }


  public Iterable<Integer> pre() {
    return preorder;
  }


  public Iterable<Integer> reversePost() {
    Stack<Integer> reverse = new Stack<>();
    for (int v : postorder)
      reverse.push(v);
    return reverse;
  }

  private boolean check() {

    // check that post(v) is consistent with post()
    int r = 0;
    for (int v : post()) {
      if (post(v) != r) {
        StdOut.println("post(v) and post() inconsistent");
        return false;
      }
      r++;
    }

    // check that pre(v) is consistent with pre()
    r = 0;
    for (int v : pre()) {
      if (pre(v) != r) {
        StdOut.println("pre(v) and pre() inconsistent");
        return false;
      }
      r++;
    }

    return true;
  }

  private void validateVertex(int v) {
    int V = marked.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);

    DepthFirstOrder dfs = new DepthFirstOrder(G);
    StdOut.println("   v  pre post");
    StdOut.println("--------------");
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
    }

    StdOut.print("Preorder:  ");
    for (int v : dfs.pre()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Postorder: ");
    for (int v : dfs.post()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Reverse postorder: ");
    for (int v : dfs.reversePost()) {
      StdOut.print(v + " ");
    }
    StdOut.println();


  }

}
