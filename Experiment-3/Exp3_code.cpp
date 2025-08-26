import java.util.*;

class Solution {
    public ArrayList<ArrayList<Integer>> countFreq(int[] arr) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        HashMap<Integer, Integer> hm = new HashMap<>();
        int n = arr.length;

        // count frequency
        for (int i = 0; i < n; i++) {
            int x = arr[i];
            if (hm.containsKey(x)) {
                hm.put(x, hm.get(x) + 1);
            } else {
                hm.put(x, 1);
            }
        }

        List<Integer> keys = new ArrayList<>(hm.keySet());
        for (int i = 0; i < keys.size(); i++) {
            int key = keys.get(i);
            int freq = hm.get(key);

            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(key);
            temp.add(freq);
            ans.add(temp);
        }

        return ans;
    }
}
