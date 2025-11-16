class Solution {
    public int kthSmallest(int[][] arr, int k) {
        int n = arr.length;
        int low = arr[0][0];
        int high = arr[n - 1][n - 1];

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (count_less(arr, mid) < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

       private int count_less(int[][] arr, int x) {
        int n = arr.length;
        int row = n - 1, col = 0;
        int count = 0;

        while (row >= 0 && col < n) {
            if (arr[row][col] <= x) {
                count += row + 1;
                col++;
            } else row--;
        }
        return count;
    }
}
