package symbolTable;

import edu.princeton.cs.algs4.StdOut;
import stacksQueues.Queue;

import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private Node root;     // root of the BST

  // BST helper node data type
  private class Node {
    private Key key;           // key
    private Value val;         // associated data
    private Node left, right;  // links to left and right subtrees
    private boolean color;     // color of parent link
    private int size;          // subtree count

    public Node(Key key, Value val, boolean color, int size) {
      this.key = key;
      this.val = val;
      this.color = color;
      this.size = size;
    }
  }

  /**
   * Initializes an empty symbol table.
   */
  public RedBlackBST() {
  }

  /***************************************************************************
   *  Node helper methods.
   ***************************************************************************/
  // is node x red; false if x is null ?
  private boolean isRed(Node x) {
    if (x == null) return false;
    return x.color == RED;
  }

  // number of node in subtree rooted at x; 0 if x is null
  private int size(Node x) {
    if (x == null) return 0;
    return x.size;
  }


  /**
   * Returns the number of key-value pairs in this symbol table.
   *
   * @return the number of key-value pairs in this symbol table
   */
  public int size() {
    return size(root);
  }

  /**
   * Is this symbol table empty?
   *
   * @return {@code true} if this symbol table is empty and {@code false} otherwise
   */
  public boolean isEmpty() {
    return root == null;
  }


  /***************************************************************************
   *  Standard BST search.
   ***************************************************************************/

  /**
   * Returns the value associated with the given key.
   *
   * @param key the key
   * @return the value associated with the given key if the key is in the symbol table
   * and {@code null} if the key is not in the symbol table
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Value get(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");
    return get(root, key);
  }

  // value associated with the given key in subtree rooted at x; null if no such key
  private Value get(Node x, Key key) {
    while (x != null) {
      int compareTo = key.compareTo(x.key);
      if (compareTo < 0) x = x.left;
      else if (compareTo > 0) x = x.right;
      else return x.val;
    }
    return null;
  }

  /**
   * Does this symbol table contain the given key?
   *
   * @param key the key
   * @return {@code true} if this symbol table contains {@code key} and
   * {@code false} otherwise
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public boolean contains(Key key) {
    return get(key) != null;
  }

  /***************************************************************************
   *  Red-black tree insertion.
   ***************************************************************************/

  /**
   * Inserts the specified key-value pair into the symbol table, overwriting the old
   * value with the new value if the symbol table already contains the specified key.
   * Deletes the specified key (and its associated value) from this symbol table
   * if the specified value is {@code null}.
   *
   * @param key the key
   * @param val the value
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public void put(Key key, Value val) {
    if (key == null) throw new IllegalArgumentException("first argument to put() is null");
    if (val == null) {
      delete(key);
      return;
    }

    root = put(root, key, val);
    root.color = BLACK;
    assert check();
  }

  // insert the key-value pair in the subtree rooted at h
  private Node put(Node h, Key key, Value val) {
    if (h == null) return new Node(key, val, RED, 1);

    int compareTo = key.compareTo(h.key);
    if (compareTo == 0) h.val = val;
    if (compareTo < 0) h.left = put(h.left, key, val);
    if (compareTo > 0) h.right = put(h.right, key, val);

    if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right)) flipColors(h);

    h.size = 1 + size(h.left) + size(h.right);

    return h;
  }

  /***************************************************************************
   *  Red-black tree deletion.
   ***************************************************************************/

  /**
   * Removes the smallest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
  }

  // delete the key-value pair with the minimum key rooted at h
  private Node deleteMin(Node h) {
    return null;
  }


  /**
   * Removes the largest key and associated value from the symbol table.
   *
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMax() {

  }

  // delete the key-value pair with the maximum key rooted at h
  private Node deleteMax(Node h) {
    return null;
  }

  /**
   * Removes the specified key and its associated value from this symbol table
   * (if the key is in this symbol table).
   *
   * @param key the key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public void delete(Key key) {
  }

  // delete the key-value pair with the given key rooted at h
  private Node delete(Node h, Key key) {
    return null;
  }

  /***************************************************************************
   *  Red-black tree helper functions.
   ***************************************************************************/

  // make a left-leaning link lean to the right
  private Node rotateRight(Node h) {
    assert isRed(h.left);

    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    x.size = h.size;
    h.size = size(h.left) + size(h.right) + 1;
    return x;
  }

  // make a right-leaning link lean to the left
  private Node rotateLeft(Node h) {
    assert isRed(h.right);
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    x.size = h.size;
    h.size = size(h.left) + size(h.right) + 1;
    return x;
  }

  // flip the colors of a node and its two children
  private void flipColors(Node h) {
    assert !isRed(h);
    assert isRed(h.left);
    assert isRed(h.right);

    h.color = !h.color;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
  }

  // Assuming that h is red and both h.left and h.left.left
  // are black, make h.left or one of its children red.
  private Node moveRedLeft(Node h) {
    return null;
  }

  // Assuming that h is red and both h.right and h.right.left
  // are black, make h.right or one of its children red.
  private Node moveRedRight(Node h) {
    return null;
  }

  // restore red-black tree invariant
  private Node balance(Node h) {
    return null;
  }


  /***************************************************************************
   *  Utility functions.
   ***************************************************************************/

  /**
   * Returns the height of the BST (for debugging).
   *
   * @return the height of the BST (a 1-node tree has height 0)
   */
  public int height() {
    return height(root);
  }

  private int height(Node x) {
    return 0;
  }

  /***************************************************************************
   *  Ordered symbol table methods.
   ***************************************************************************/

  /**
   * Returns the smallest key in the symbol table.
   *
   * @return the smallest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key min() {
    Node min = min(root);
    return min.key;
  }

  // the smallest key in subtree rooted at x; null if no such key
  private Node min(Node x) {
    if (x.left == null) return x;
    x = min(x.left);
    return x;
  }

  /**
   * Returns the largest key in the symbol table.
   *
   * @return the largest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key max() {
    Node max = max(root);
    return max.key;
  }

  // the largest key in the subtree rooted at x; null if no such key
  private Node max(Node x) {
    if (x.right == null) return x;
    x = max(x.right);
    return x;
  }


  /**
   * Returns the largest key in the symbol table less than or equal to {@code key}.
   *
   * @param key the key
   * @return the largest key in the symbol table less than or equal to {@code key}
   * @throws NoSuchElementException   if there is no such key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Key floor(Key key) {
    return null;
  }

  // the largest key in the subtree rooted at x less than or equal to the given key
  private Node floor(Node x, Key key) {
    return null;
  }

  /**
   * Returns the smallest key in the symbol table greater than or equal to {@code key}.
   *
   * @param key the key
   * @return the smallest key in the symbol table greater than or equal to {@code key}
   * @throws NoSuchElementException   if there is no such key
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public Key ceiling(Key key) {
    return null;
  }

  // the smallest key in the subtree rooted at x greater than or equal to the given key
  private Node ceiling(Node x, Key key) {
    return null;
  }

  /**
   * Return the key in the symbol table of a given {@code rank}.
   * This key has the property that there are {@code rank} keys in
   * the symbol table that are smaller. In other words, this key is the
   * ({@code rank}+1)st smallest key in the symbol table.
   *
   * @param rank the order statistic
   * @return the key in the symbol table of given {@code rank}
   * @throws IllegalArgumentException unless {@code rank} is between 0 and
   *                                  <em>n</em>–1
   */
  public Key select(int rank) {
    if (rank < 0 || rank >= size()) {
      throw new IllegalArgumentException("argument to select() is invalid: " + rank);
    }
    return select(root, rank);
  }

  // Return key in BST rooted at x of given rank.
  // Precondition: rank is in legal range.
  private Key select(Node x, int rank) {
    if (x == null) return null;
    int leftSize = size(x.left);
    if (leftSize > rank) return select(x.left, rank);
    else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
    else return x.key;
  }

  /**
   * Return the number of keys in the symbol table strictly less than {@code key}.
   *
   * @param key the key
   * @return the number of keys in the symbol table strictly less than {@code key}
   * @throws IllegalArgumentException if {@code key} is {@code null}
   */
  public int rank(Key key) {
    if (key == null) throw new IllegalArgumentException();
    return rank(key, root);
  }

  // number of keys less than key in the subtree rooted at x
  private int rank(Key key, Node x) {
    if (x == null) return 0;

    int compareTo = key.compareTo(x.key);
    if (compareTo < 0) return rank(key, x.left);
    else if (compareTo > 0) return 1 + size(x.left) + rank(key, x.right);
    else return size(x.left);
  }

  /***************************************************************************
   *  Range count and range search.
   ***************************************************************************/

  /**
   * Returns all keys in the symbol table in ascending order as an {@code Iterable}.
   * To iterate over all of the keys in the symbol table named {@code st},
   * use the foreach notation: {@code for (Key key : st.keys())}.
   *
   * @return all keys in the symbol table in ascending order
   */
  public Iterable<Key> keys() {
    if (isEmpty()) return new Queue<Key>();
    return keys(min(), max());
  }

  /**
   * Returns all keys in the symbol table in the given range in ascending order,
   * as an {@code Iterable}.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return all keys in the symbol table between {@code lo}
   * (inclusive) and {@code hi} (inclusive) in ascending order
   * @throws IllegalArgumentException if either {@code lo} or {@code hi}
   *                                  is {@code null}
   */
  public Iterable<Key> keys(Key lo, Key hi) {
    if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
    if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

    Queue<Key> queue = new Queue<>();
    keys(root, queue, lo, hi);

    return queue;
  }

  // add the keys between lo and hi in the subtree rooted at x
  // to the queue
  private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
    if (x == null) return;
    int loCmp = lo.compareTo(x.key);
    int hiCmp = hi.compareTo(x.key);
    if (loCmp < 0) keys(x.left, queue, lo, hi);
    if (loCmp <= 0 && hiCmp >= 0) queue.enqueue(x.key);
    if (hiCmp > 0) keys(x.right, queue, lo, hi);
  }

  /**
   * Returns the number of keys in the symbol table in the given range.
   *
   * @param lo minimum endpoint
   * @param hi maximum endpoint
   * @return the number of keys in the symbol table between {@code lo}
   * (inclusive) and {@code hi} (inclusive)
   * @throws IllegalArgumentException if either {@code lo} or {@code hi}
   *                                  is {@code null}
   */
  public int size(Key lo, Key hi) {
    return 0;
  }


  /***************************************************************************
   *  Check integrity of red-black tree data structure.
   ***************************************************************************/
  private boolean check() {
    if (!isBST()) StdOut.println("Not in symmetric order");
    if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
    if (!isRankConsistent()) StdOut.println("Ranks not consistent");
    if (!is23()) StdOut.println("Not a 2-3 tree");
    if (!isBalanced()) StdOut.println("Not balanced");
    return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
  }

  // does this binary tree satisfy symmetric order?
  // Note: this test also ensures that data structure is a binary tree since order is strict
  private boolean isBST() {
    return isBST(root, null, null);
  }

  // is the tree rooted at x a BST with all keys strictly between min and max
  // (if min or max is null, treat as empty constraint)
  // Credit: Bob Dondero's elegant solution
  private boolean isBST(Node x, Key min, Key max) {
    if (x == null) return true;
    if (min != null && x.key.compareTo(min) <= 0) return false;
    if (max != null && x.key.compareTo(max) >= 0) return false;
    return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
  }

  // are the size fields correct?
  private boolean isSizeConsistent() {
    return isSizeConsistent(root);
  }

  private boolean isSizeConsistent(Node x) {
    if (x == null) return true;
    if (x.size != size(x.left) + size(x.right) + 1) return false;
    return isSizeConsistent(x.left) && isSizeConsistent(x.right);
  }

  // check that ranks are consistent
  private boolean isRankConsistent() {
    for (int i = 0; i < size(); i++)
      if (i != rank(select(i))) return false;
    for (Key key : keys())
      if (key.compareTo(select(rank(key))) != 0) return false;
    return true;
  }

  // Does the tree have no red right links, and at most one (left)
  // red links in a row on any path?
  private boolean is23() {
    return is23(root);
  }

  private boolean is23(Node x) {
    if (x == null) return true;
    if (isRed(x.right)) return false;
    if (x != root && isRed(x) && isRed(x.left))
      return false;
    return is23(x.left) && is23(x.right);
  }

  // do all paths from root to leaf have same number of black edges?
  private boolean isBalanced() {
    int black = 0;     // number of black links on path from root to min
    Node x = root;
    while (x != null) {
      if (!isRed(x)) black++;
      x = x.left;
    }
    return isBalanced(root, black);
  }

  // does every path from the root to a leaf have the given number of black links?
  private boolean isBalanced(Node x, int black) {
    if (x == null) return black == 0;
    if (!isRed(x)) black--;
    return isBalanced(x.left, black) && isBalanced(x.right, black);
  }


  /**
   * Unit tests the {@code RedBlackBST} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    RedBlackBST<String, String> bst = new RedBlackBST<>();
    bst.put("s", "s_v");
    bst.put("e", "e_v");
    bst.put("x", "x_v");
    bst.put("a", "a_v");
    bst.put("r", "r_v");
    bst.put("c", "c_v");
    bst.put("h", "h_v");
    bst.put("m", "m_v");

    System.out.println(bst.get("m"));


    for (String key : bst.keys()) {
      System.out.print(key + " ");
    }
    System.out.println();

    System.out.println("done");
  }
}
