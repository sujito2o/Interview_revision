import java.util.*;

class Edge {
    int dest, weight;

    public Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int vertex, weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node other) {
        return this.weight - other.weight;
    }
}

public class PrimsPQ {

    public void primMST(List<List<Edge>> adjList, int V) {
        boolean[] inMST = new boolean[V];
        int[] minEdgeWeight = new int[V];  // Renamed from 'key'
        int[] parent = new int[V];

        PriorityQueue<Node> pq = new PriorityQueue<>();

        Arrays.fill(minEdgeWeight, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        minEdgeWeight[0] = 0;
        pq.offer(new Node(0, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;
            inMST[u] = true;

            for (Edge edge : adjList.get(u)) {
                int v = edge.dest;
                int weight = edge.weight;

                if (!inMST[v] && weight < minEdgeWeight[v]) {
                    minEdgeWeight[v] = weight;
                    pq.offer(new Node(v, minEdgeWeight[v]));
                    parent[v] = u;
                }
            }
        }

        printMST(parent, adjList);
    }

    private void printMST(int[] parent, List<List<Edge>> adjList) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < adjList.size(); i++) {
            for (Edge edge : adjList.get(i)) {
                if (edge.dest == parent[i]) {
                    System.out.println(parent[i] + " - " + i + "\t" + edge.weight);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Edge>> adjList = new ArrayList<>();

        for (int i = 0; i < V; i++)
            adjList.add(new ArrayList<>());

        // Undirected graph (add both directions)
        adjList.get(0).add(new Edge(1, 2));
        adjList.get(1).add(new Edge(0, 2));

        adjList.get(0).add(new Edge(3, 6));
        adjList.get(3).add(new Edge(0, 6));

        adjList.get(1).add(new Edge(2, 3));
        adjList.get(2).add(new Edge(1, 3));

        adjList.get(1).add(new Edge(3, 8));
        adjList.get(3).add(new Edge(1, 8));

        adjList.get(1).add(new Edge(4, 5));
        adjList.get(4).add(new Edge(1, 5));

        adjList.get(2).add(new Edge(4, 7));
        adjList.get(4).add(new Edge(2, 7));

        adjList.get(3).add(new Edge(4, 9));
        adjList.get(4).add(new Edge(3, 9));

        PrimsPQ prim = new PrimsPQ();
        prim.primMST(adjList, V);
    }
}
