package heap;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<Key> implements Iterable<Key> {

  private static final int INIT_CAPACITY = 2;

  private Key[] pq;
  private int n;


  public MaxPQ() {
    pq = (Key[]) new Object[INIT_CAPACITY];
    n = 0;
  }

  public MaxPQ(int size) {
    pq = (Key[]) new Object[size + 1];
    this.n = 0;
  }

  public void insert(Key v) {
    if (n == this.pq.length - 1) resize(2 * pq.length);
    pq[++n] = v;
    swin(n);

    assert isMaxHeap();
  }


  public boolean isEmpty() {
    return n == 0;
  }

  public Key max() {
    return pq[1];
  }

  public Key delMax() {
    Key maxKey = max();
    exch(1, n);
    n -= 1;
    sink(1);
    pq[n + 1] = null;

    if (n > 0 && (n == (pq.length - 1) / 4)) resize(pq.length / 2);

    assert isMaxHeap();
    return maxKey;
  }

  public int size() {
    return n;
  }


  private void resize(int capacity) {
    assert capacity >= n;
    pq = java.util.Arrays.copyOf(pq, capacity);
  }

  // if Key[i] < Key[j]
  private boolean less(int i, int j) {
    return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
  }

  private void exch(int i, int j) {
    Key temp = pq[i];
    pq[i] = pq[j];
    pq[j] = temp;
  }

  private void swin(int k) {
    // parent < children k/x < k
    while (k > 1 && less(k / 2, k)) {
      exch(k / 2, k);
      k = k / 2;
    }
  }

  private void sink(int k) {
    while (2 * k <= n) {
      int j = 2 * k;
      if (j < n && less(j, j + 1)) j++;
      if (!less(k, j)) break;
      exch(k, j);
      k = j;
    }
  }

  // is subtree of pq[1..n] rooted at k a max heap?
  private boolean isMaxHeapOrdered(int k) {
    if (k > n) return true;
    int left = 2 * k;
    int right = 2 * k + 1;
    if (left <= n && less(k, left)) return false;
    if (right <= n && less(k, right)) return false;
    return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
  }

  // is pq[1..n] a max heap?
  private boolean isMaxHeap() {
    for (int i = 1; i <= n; i++) {
      if (pq[i] == null) return false;
    }
    for (int i = n + 1; i < pq.length; i++) {
      if (pq[i] != null) return false;
    }
    if (pq[0] != null) return false;
    return isMaxHeapOrdered(1);
  }

  @Override
  public Iterator<Key> iterator() {
    return new HeapIterator();
  }

  private class HeapIterator implements Iterator<Key> {

    private final MaxPQ<Key> copy;

    public HeapIterator() {
      copy = new MaxPQ<>(size());
      for (int i = 1; i <= n; i++) {
        copy.insert(pq[i]);
      }
    }

    @Override
    public boolean hasNext() {
      return copy.size() != 0;
    }

    @Override
    public Key next() {
      if (!hasNext()) throw new NoSuchElementException();

      return copy.delMax();
    }
  }

  public static void main(String[] args) {
    MaxPQ<Integer> pq = new MaxPQ<>();
    pq.insert(1);
    pq.insert(3);
    pq.insert(4);
    pq.insert(5);
    pq.insert(2);

    System.out.println("1 pq max key is " + pq.max());
    pq.delMax();
    System.out.println("2 pq max key is " + pq.max());
    pq.delMax();


    System.out.println("size is " + pq.size());

    for (Integer key : pq) {
      System.out.println("key is " + key);
    }

    pq.insert(6);
    System.out.println("max is " + pq.max());
    pq.delMax();
    pq.delMax();
    pq.delMax();
    pq.delMax();

    System.out.println("is Empty is " + pq.isEmpty());
  }

}
