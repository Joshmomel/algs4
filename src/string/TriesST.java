package string;

public class TriesST<Value> {
  private static final int R = 256;
  private Node root = new Node();

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

  public static void main(String[] args) {
    TriesST<String> triesST = new TriesST<>();
    triesST.put("h", "1");
    triesST.put("p", "2");

    System.out.println("h is " + triesST.get("h"));
    System.out.println("p is " + triesST.get("p"));
  }

}
