class Solution {
    public static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int pos = start;
        for (int i = start; i < end; i++) {
            if (arr[i] <= pivot) { // Note the change from >= to <=
                int temp = arr[i];
                arr[i] = arr[pos];
                arr[pos] = temp;
                pos++;
            }
        }
        int temp = arr[pos];
        arr[pos] = arr[end];
        arr[end] = temp;
        return pos;
    }

    public static void quicksort(int[] arr, int start, int end) {
        if (start < end) { // Note the change from >= to <
            int pi = partition(arr, start, end);

            // left Side
            quicksort(arr, start, pi - 1);
            // Right side
            quicksort(arr, pi + 1, end);
        }
    }

    public int[] sortArray(int[] nums) {
        int n = nums.length - 1;
        quicksort(nums, 0, n);
        return nums;
    }
}
