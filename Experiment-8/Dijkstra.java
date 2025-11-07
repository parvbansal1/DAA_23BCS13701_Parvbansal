import java.util.*;

class Solution {
    @SuppressWarnings("unchecked")
    static int[] dijkstra(int V, int[][] edges, int src) {
        ArrayList<int[]>[] adj = (ArrayList<int[]>[]) new ArrayList[V];
        for (int i = 0; i < V; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
            adj[e[1]].add(new int[]{e[0], e[2]});
        }

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int d = curr[1];
            if (d > dist[node]) continue;

            for (int[] nbr : adj[node]) {
                int next = nbr[0], wt = nbr[1];
                if (dist[node] + wt < dist[next]) {
                    dist[next] = dist[node] + wt;
                    pq.add(new int[]{next, dist[next]});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int V = 3, src = 2;
        int[][] edges = {{0, 1, 1}, {1, 2, 3}, {0, 2, 6}};
        int[] ans = dijkstra(V, edges, src);
        for (int x : ans) System.out.print(x + " ");
    }
}
