package string;

import edu.princeton.cs.algs4.StdOut;

public class KMP {

  int R;
  String pattern;
  int M;
  private final int[][] dfa;       // the KMP automoton


  public KMP(String pattern, int R) {
    this.R = R;
    this.pattern = pattern;
    this.M = pattern.length();
    this.dfa = new int[R][M];
    dfa[pattern.charAt(0)][0] = 1;

    for (int X = 0, j = 1; j < M; j++) {
      for (int c = 0; c < R; c++) {
        dfa[c][j] = dfa[c][X];
      }
      dfa[pattern.charAt(j)][j] = j + 1;
      X = dfa[pattern.charAt(j)][X];
    }
  }

  public int search(String text) {
    int i;
    int j;
    int N = text.length();

    for (i = 0, j = 0; i < N && j < M; i++) {
      j = dfa[text.charAt(i)][j];
    }
    if (j == M) return i - M;
    else return N;
  }

  public static void main(String[] args) {
    String pattern = "abracadabra";
    String text = "abacadabrabracabracadabrabrabracad";
    KMP kmp = new KMP(pattern, 256);
    int offset = kmp.search(text);

    StdOut.println("text:    " + text);
    StdOut.print("pattern: ");
    for (int i = 0; i < offset; i++)
      StdOut.print(" ");
    StdOut.println(pattern);
  }

}
