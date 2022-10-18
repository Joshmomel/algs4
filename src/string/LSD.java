package string;


import java.util.Arrays;

public class LSD {
  public static void sort(String[] a, int W) {
    int R = 256;
    int N = a.length;
    String[] aux = new String[N];

    for (int d = W - 1; d >= 0; d--) {
      int[] count = new int[R + 1];
      for (int i = 0; i < N; i++) {
        count[a[i].charAt(d) + 1]++;
      }
      for (int r = 0; r < R; r++) {
        count[r + 1] += count[r];
      }
      for (int i = 0; i < N; i++) {
        aux[count[a[i].charAt(d)]++] = a[i];
      }
      for (int i = 0; i < N; i++) {
        a[i] = aux[i];
      }
    }
  }

  public static void main(String[] args) {
    String[] a = {"bed", "bug", "dad", "yes", "zoo",
      "now", "for", "tip", "ilk", "dim",
      "tag", "jot", "sob", "nob", "sky",
      "hut", "men", "egg", "few", "jay",
      "owl", "joy", "rap", "gig", "wee",
      "was", "wad", "fee", "tap", "tar",
      "dug", "jam", "all", "bad", "yet"};

    sort(a, 3);

    System.out.println(Arrays.toString(a));

  }

}
