package string;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
  // alphabet size of extended ASCII
  private static final int R = 256;

  private static class Node implements Comparable<Node> {
    private final char ch;
    private final int freq;
    private final Node left, right;

    Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    private boolean isLeaf() {
      return (left == null) && (right == null);
    }

    @Override
    public int compareTo(Node that) {
      return this.freq - that.freq;
    }
  }

  public static void compress() {
    String s = BinaryStdIn.readString();
    char[] input = s.toCharArray();

    int[] freq = new int[R];
    for (int i = 0; i < input.length; i++) {
      freq[input[i]] += 1;
    }

    Node root = buildTrie(freq);

    String[] st = new String[R];
    buildCode(st, root, "");

    writeTries(root);

    BinaryStdOut.write(input.length);

    for (int i = 0; i < input.length; i++) {
      String code = st[input[i]];
      for (int j = 0; j < code.length(); j++) {
        if (code.charAt(j) == '0') {
          BinaryStdOut.write(false);
        } else if (code.charAt(j) == '1') {
          BinaryStdOut.write(true);
        } else {
          throw new IllegalStateException();
        }
      }
    }

    BinaryStdOut.close();
  }

  private static Node buildTrie(int[] freq) {
    MinPQ<Node> pq = new MinPQ<>();

    for (char c = 0; c < R; c++) {
      if (freq[c] > 0) {
        pq.insert(new Node(c, freq[c], null, null));
      }
    }
    while (pq.size() > 1) {
      Node left = pq.delMin();
      Node right = pq.delMin();
      Node parent = new Node('\0', left.freq + right.freq, left, right);
      pq.insert(parent);
    }
    return pq.delMin();
  }

  private static void buildCode(String[] st, Node x, String s) {
    if (!x.isLeaf()) {
      buildCode(st, x.left, s + '0');
      buildCode(st, x.right, s + '1');
    } else {
      st[x.ch] = s;
    }
  }

  private static void writeTries(Node x) {
    if (x.isLeaf()) {
      BinaryStdOut.write(true);
      BinaryStdOut.write(x.ch, 8);
      return;
    }
    BinaryStdOut.write(false);
    writeTries(x.left);
    writeTries(x.right);
  }

  public static void expand() {
    Node root = readTrie();
    int length = BinaryStdIn.readInt();

    for (int i = 0; i < length; i++) {
      Node x = root;
      while (!x.isLeaf()) {
        boolean bit = BinaryStdIn.readBoolean();
        if (bit) {
          x = x.right;
        } else {
          x = x.left;
        }
      }
      BinaryStdOut.write(x.ch, 8);
    }
    BinaryStdOut.close();
  }

  private static Node readTrie() {
    boolean isLeaf = BinaryStdIn.readBoolean();
    if (isLeaf) {
      return new Node(BinaryStdIn.readChar(), -1, null, null);
    } else {
      return new Node('\0', -1, readTrie(), readTrie());
    }
  }

  public static void main(String[] args) {
      if      (args[0].equals("-")) compress();
      else if (args[0].equals("+")) expand();
      else throw new IllegalArgumentException("Illegal command line argument");
  }

}
