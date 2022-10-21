package string;

import stacksQueues.Queue;

public class TriesST<Value> {
  private static final int R = 256;
  private Node root = new Node();
  private int n;

  private static class Node {
    private Object value;
    private final Node[] next = new Node[R];
  }

  public void put(String key, Value value) {
    root = put(root, key, value, 0);
  }

  private Node put(Node x, String key, Value value, int d) {
    if (x == null) x = new Node();
    if (d == key.length()) {
      n += 1;
      x.value = value;
      return x;
    }
    char c = key.charAt(d);
    x.next[c] = put(x.next[c], key, value, d + 1);
    return x;
  }

  public Value get(String key) {
    Node x = get(root, key, 0);
    if (x == null) return null;
    return (Value) x.value;
  }

  private Node get(Node x, String key, int d) {
    if (x == null) return null;
    if (d == key.length()) return x;
    char c = key.charAt(d);
    return get(x.next[c], key, d + 1);
  }

  public int size() {
    return n;
  }

  public Iterable<String> keys() {
    return keysWithPrefix("");
  }

  public Iterable<String> keysWithPrefix(String prefix) {
    Queue<String> q = new Queue<>();
    Node x = get(root, prefix, 0);
    collect(x, new StringBuilder(prefix), q);
    return q;
  }

  private void collect(Node x, StringBuilder prefix, Queue<String> q) {
    if (x == null) return;
    if (x.value != null) q.enqueue(prefix.toString());
    for (char c = 0; c < R; c++) {
      prefix.append(c);
      collect(x.next[c], prefix, q);
      prefix.deleteCharAt(prefix.length() - 1);
    }
  }


  public static void main(String[] args) {
    TriesST<String> triesST = new TriesST<>();
    triesST.put("p", "2");
    triesST.put("h", "1");
    triesST.put("pac", "3");
    triesST.put("pa", "3");


    System.out.println("h is " + triesST.get("h"));
    System.out.println("pac is " + triesST.get("pac"));
    System.out.println("size is " + triesST.size());

    System.out.println("longest prefix for key p is " + triesST.keysWithPrefix("p"));
    for (String key : triesST.keys()) {
      System.out.println("key " + key);
    }
  }

}
