package string;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Bag;
import stacksQueues.Stack;

public class NFA {
  private final char[] re;
  private final Digraph graph;
  private final int m;

  public NFA(String regexp) {
    m = regexp.length();
    re = regexp.toCharArray();
    graph = buildEpsilonTransitionDigraph();
  }

  public boolean recognizes(String txt) {
    Bag<Integer> pc = new Bag<>();
    DirectedDFS dfs = new DirectedDFS(graph, 0);
    for (int i = 0; i < graph.V(); i++) {
      if (dfs.marked(i)) {
        pc.add(i);
      }
    }

    for (int i = 0; i < txt.length(); i++) {
      Bag<Integer> states = new Bag<>();
      for (int v : pc) {
        if (v == m) continue;
        if ((re[v] == txt.charAt(i))) {
          states.add(v + 1);
        }
      }

      if (states.isEmpty()) continue;

      dfs = new DirectedDFS(graph, states);
      pc = new Bag<>();
      for (int v = 0; v < graph.V(); v++) {
        if (dfs.marked(v)) pc.add(v);
      }
      if (pc.size() == 0) return false;
    }

    for (int v : pc) {
      if (v == m) {
        return true;
      }
    }
    return false;
  }

  private Digraph buildEpsilonTransitionDigraph() {
    Digraph G = new Digraph(m + 1);
    Stack<Integer> ops = new Stack<>();
    for (int i = 0; i < m; i++) {
      int lp = i;
      if (re[i] == '(' || re[i] == '|') {
        ops.push(i);
      } else if (re[i] == ')') {
        int or = ops.pop();
        if (re[or] == '|') {
          lp = ops.pop();
          G.addEdge(lp, or + 1);
          G.addEdge(or, i);
        } else {
          lp = or;
        }
      }

      if (i < m - 1 && re[i + 1] == '*') {
        G.addEdge(lp, i + 1);
        G.addEdge(i + 1, lp);
      }
      if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
        G.addEdge(i, i + 1);
      }
    }

    return G;
  }

  public static void main(String[] args) {
    String regexp = "(A*B|AC)D";
    String txt = "AAAABD";
    NFA nfa = new NFA(regexp);
    StdOut.println(nfa.recognizes(txt));
  }

}
