// File: src/main/java/com/example/dp/DPAlgorithms.java
package com.example.dp;

import java.util.Arrays;

public class DPAlgorithms {

    // Fibonacci - iterative DP
    public static long fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        if (n == 0) return 0;
        if (n == 1) return 1;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // Coin change - returns dp array of size amount+1 where dp[a] = min coins or -1 if impossible
    public static int[] coinChangeTable(int[] coins, int amount) {
        int INF = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
            }
            if (dp[a] == INF) dp[a] = -1; // mark impossible
        }
        return dp;
    }

    // 0/1 Knapsack DP table (2D) returns dp table of size (n+1) x (capacity+1)
    public static int[][] knapsackDPTable(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weights[i - 1] <= w) dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                else dp[i][w] = dp[i - 1][w];
            }
        }
        return dp;
    }

    // helper: convert 2D to 1D flat for animation from main (not exported)
    public static int[] flatten2D(int[][] a) {
        int rows = a.length, cols = rows == 0 ? 0 : a[0].length;
        int[] flat = new int[rows * cols];
        int k = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) flat[k++] = a[i][j];
        return flat;
    }

    // LCS DP table
    public static int[][] lcsDPTable(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            char a = s1.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char b = s2.charAt(j - 1);
                if (a == b) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp;
    }

    // (Optional) simple 0/1 knapsack 1D solver (fast)
    public static int knapsack01(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
        return dp[capacity];
    }

    // ---------------- Matrix Chain Multiplication ----------------
    // p[] array of dimensions where matrix i is p[i-1] x p[i]
    // Returns m table (1..n x 1..n) where m[i][j] = min cost to multiply Ai..Aj
    public static int[][] matrixChainOrder(int[] p) {
        int n = p.length - 1;
        int[][] m = new int[n + 1][n + 1];
        // initialize with 0 for single matrices and INF for others
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) m[i][j] = 0;
                else m[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int L = 2; L <= n; L++) { // chain length
            for (int i = 1; i <= n - L + 1; i++) {
                int j = i + L - 1;
                for (int k = i; k < j; k++) {
                    if (m[i][k] == Integer.MAX_VALUE || m[k+1][j] == Integer.MAX_VALUE) continue;
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) m[i][j] = q;
                }
            }
        }
        return m;
    }
}
