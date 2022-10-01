package symbolTable;

//TODO: implement Queue

import edu.princeton.cs.algs4.Queue;

public class BST<Key extends Comparable<Key>, Value> {
  private Node root;

  private class Node {
    private Value value;
    private Key key;
    private Node left;
    private Node right;
    private int count;

    public Node(Key key, Value value, int size) {
      this.key = key;
      this.value = value;
      this.count = size;
    }
  }


  public void put(Key key, Value val) {
    root = put(root, key, val);
  }

  private Node put(Node node, Key key, Value value) {
    if (node == null) return new Node(key, value, 1);
    int compareTo = node.key.compareTo(key);
    if (compareTo == 0) node.value = value;
    if (compareTo < 0) {
      node.right = put(node.right, key, value);
    }
    if (compareTo > 0) {
      node.left = put(node.left, key, value);
    }
    node.count = 1 + size(node.left) + size(node.right);
    return node;
  }


  public Value get(Key key) {
    Node node = get(root, key);
    if (node == null) return null;
    return node.value;
  }

  private Node get(Node node, Key key) {
    if (node == null) return null;
    int compareTo = node.key.compareTo(key);
    if (compareTo == 0) return node;
    if (compareTo < 0) return node.right;
    return node.left;
  }

  public void delete(Key key) {
    delete(root, key);
  }

  private Node delete(Node x, Key key) {
    if (x == null) return null;
    int compareTo = key.compareTo(x.key);
    if (compareTo < 0) x.left = delete(x.left, key);
    else if (compareTo > 0) x.right = delete(x.right, key);
    else {
      if (x.right == null) return x.left;
      if (x.left == null) return x.right;

      Node t = x;
      x = min(t.right);
      x.right = deleteMin(t.right);
      x.left = t.left;
    }

    x.count = 1 + size(x.left) + size(x.right);
    return x;
  }

  public Key min() {
    Node min = min(root);
    return min.key;
  }

  private Node min(Node x) {
    if (x.left == null) return x;
    x = min(x.left);
    return x;
  }


  public void deleteMin() {
    root = deleteMin(root);
  }

  private Node deleteMin(Node x) {
    if (x.left == null) return x.right;
    x.left = deleteMin(x.left);
    x.count = 1 + size(x.left) + size(x.right);
    return x;
  }


  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) return 0;
    return x.count;
  }


  public Key floor(Key key) {
    Node x = floor(root, key);
    if (x == null) return null;
    return x.key;
  }

  // Find a max bst node value that is less than node pass in arg
  // case 1: k equals the key at root, the floor of k is k
  // case 2: k is less than the key at root, the floor of k is in the left subtree
  // case 3: k is greater than the key at root
  // if there is any key ≤ k in right subtree - The floor of k is in the right subtree
  // otherwise it is the key in the root
  private Node floor(Node node, Key key) {
    if (node == null) return null;

    int compareTo = key.compareTo(node.key);
    if (compareTo == 0) return node;
    if (compareTo < 0) return floor(node.left, key);
    Node t = floor(node.right, key);
    if (t != null) return t;
    return node;
  }


  public int rank(Key key) {
    return rank(key, root);
  }

  // how many keys < key
  private int rank(Key key, Node node) {
    if (node == null) return 0;

    int compareTo = key.compareTo(node.key);
    if (compareTo < 0) return rank(key, node.left);
    if (compareTo > 0) return 1 + size(node.left) + rank(key, node.right);
    return size(node.left);
  }

  public Iterable<Key> iterator() {
    Queue<Key> q = new Queue<>();
    inorder(root, q);
    return q;
  }

  private void inorder(Node node, Queue<Key> q) {
    if (node == null) return;
    inorder(node.left, q);
    q.enqueue(node.key);
    inorder(node.right, q);
  }


  public static void main(String[] args) {
    BST<String, String> bst = new BST<>();
    bst.put("s", "s_v");
    bst.put("e", "e_v");
    bst.put("x", "x_v");
    bst.put("a", "a_v");
    bst.put("r", "r_v");
    bst.put("c", "c_v");
    bst.put("h", "h_v");
    bst.put("m", "m_v");

    System.out.println("bst is");
    for (String s : bst.iterator()) {
      System.out.print(s + " ");
    }
    System.out.println();
    System.out.println("bst size is " + bst.size());

    System.out.println("bst floor(g) is " + bst.floor("g"));
    System.out.println("bst rank(e) is " + bst.rank("e"));
    System.out.println("bst rank(g) is " + bst.rank("g"));
    System.out.println("bst rank(t) is " + bst.rank("n"));


    System.out.println("min is " + bst.min());
    System.out.println("min key value is " + bst.get(bst.min()));

    System.out.println("delete e");
    bst.delete("e");
    for (String s : bst.iterator()) {
      System.out.print(s + " ");
    }
    System.out.println();
    System.out.println("bst size is " + bst.size());


    System.out.println("deleteMin");
    bst.deleteMin();
    System.out.println();
    for (String s : bst.iterator()) {
      System.out.print(s + " ");
    }
    System.out.println();
    System.out.println("bst size is " + bst.size());


    System.out.println("done");
  }
}
