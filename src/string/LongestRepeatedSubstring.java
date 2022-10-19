package string;

import java.util.Arrays;

public class LongestRepeatedSubstring {
  public static String lrs(String s) {
    int N = s.length();
    String[] suffixes = new String[N];

    for (int i = 0; i < N; i++) {
      suffixes[i] = s.substring(i, N);
    }

    Arrays.sort(suffixes);

    String lrs = "";
    for (int i = 0; i < N - 1; i++) {
      int len = lcp(suffixes[i], suffixes[i + 1]);
      if (len > lrs.length()) {
        lrs = suffixes[i].substring(0, len);
      }
    }

    return lrs;
  }

  public static int lcp(String s, String t) {
    int N = Math.min(s.length(), t.length());
    for (int i = 0; i < N; i++) {
      if (s.charAt(i) != t.charAt(i)) {
        return i;
      }
    }
    return N;
  }

  public static void main(String[] args) {
    String lrs = lrs("""
      i say a horse at a canter coming up joe
      i say a horse at a gallop tom returned the guard leaving
      his hold of the door and mounting nimbly to his place
      gentlemen  in the kings name all of you
      """);
    System.out.println("'" + lrs + "'");
  }
}
