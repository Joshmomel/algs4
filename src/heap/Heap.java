package heap;

import java.util.Arrays;

public class Heap {
  public static void sort(Comparable[] a) {
    int n = a.length;

    //First pass
    //Build heap using bottom-up method
    for (int i = n / 2; i >= 1; i--) {
      sink(a, i, n);
    }

    //Second pass
    // Remove the maximum, one at a time
    // Leave in array, instead of nulling out
    while (n > 1) {
      exch(a, 1, n);
      n -= 1;
      sink(a, 1, n);
    }

  }

  private static void sink(Comparable[] a, int k, int n) {
    while (2 * k <= n) {
      int j = 2 * k;
      if (j < n && less(a, j, j + 1)) j += 1;
      if (!less(a, k, j)) break;
      exch(a, k, j);
      k = j;
    }
  }

  private static boolean less(Comparable[] a, int i, int j) {
    // i - 1 as array start from 0, pq start from 1
    return a[i - 1].compareTo(a[j - 1]) < 0;
  }

  private static void exch(Comparable[] a, int i, int j) {
    // i - 1 as array start from 0, pq start from 1
    Comparable temp = a[i - 1];
    a[i - 1] = a[j - 1];
    a[j - 1] = temp;
  }

  public static void main(String[] args) {
    Integer[] a = {1, 2, 3, 6, 5, 7};
    System.out.println(Arrays.toString(a));

    Heap.sort(a);
    System.out.println(Arrays.toString(a));
  }

}
