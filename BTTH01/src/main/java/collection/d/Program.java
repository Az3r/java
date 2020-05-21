package collection.d;

import collection.Helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Program {

    public static void main(String[] args) {
//        int[][] graph = new int[][]{
//                {0, 0, 9, 2, 2, 0, 0, 0},
//                {0, 0, 9, 3, 0, 5, 0, 0},
//                {9, 9, 0, 0, 5, 0, 0, 0},
//                {2, 3, 0, 0, 0, 0, 0, 6},
//                {2, 0, 5, 0, 0, 0, 0, 7},
//                {0, 5, 0, 0, 0, 0, 0, 5},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 6, 7, 5, 0, 0}
//        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name:");
        Path fpath = Path.of(scanner.nextLine());
        if (Files.exists(fpath)) {
            try {
                String input = Files.readString(fpath);
                int[][] graph = createGraph(input);
                System.out.println("Initialized graph");

                System.out.println("Enter start and stop vertex. I.e: 2 3)");
                List<Integer> params = Helper.fromString(scanner.nextLine(), 2);
                GraphPath path = dijkstra(graph, params.get(0), params.get(1));

                System.out.println("Finding path...");
                if (path.empty()) System.out.printf("Cannot find path from %d to %d", params.get(0), params.get(1));
                else System.out.printf("Path found: %s%n" +
                        "Distance from source:%n%s", path.displayPath(), path.displayPathWeight());

            } catch (IOException e) {
                System.err.println("An error happened when opening file");
            } catch (IndexOutOfBoundsException iobe) {
                System.err.println("Invalid start or stop vetex");
            }
        } else System.err.println("Error path not found");

        scanner.close();
    }


    public static int getWeight(Stack<GraphVertex> path) {
        if (path.empty()) return -1;
        return path.stream().mapToInt(value -> value.weight).sum();
    }

    /**
     * create a vertices graph from a given string
     *
     * @param input the input string
     * @return vertices graph in 2D array
     */
    public static int[][] createGraph(String input) {
        if (input == null) throw new NullPointerException("input");
        Scanner scanner = new Scanner(input);

        List<int[]> graph = new ArrayList<>();

        // convert input string to int[][]
        while (scanner.hasNext()) {
            int[] ints = Arrays.stream(scanner.nextLine().split("[\\s,]+")).mapToInt(Integer::parseInt).toArray();
            if (ints.length > 0) graph.add(ints);
        }
        scanner.close();

        return graph.toArray(int[][]::new);
    }


    /**
     * find the shortest path from src to dest
     *
     * @param graph represent the weights between two vertices
     * @param src   begin vertex
     * @param dest  destination vertex
     * @return an array containing all vertices needed to reach dest
     */
    public static GraphPath dijkstra(int[][] graph, int src, int dest) {
        if (graph == null) throw new NullPointerException("graph");
        if (graph.length == 0) throw new IllegalArgumentException("graph");
        // check if graph has square shape
        for (int[] ints : graph) {
            if (graph.length != ints.length) throw new IllegalArgumentException("graph is not a square");
        }
        if (src < 0 || src >= graph.length) throw new IllegalArgumentException("src");
        if (dest < 0 || dest >= graph.length) throw new IllegalArgumentException("dest");

        // first mark all vertices as unvisited except src vertex
        boolean[] visited = new boolean[graph.length];
        Arrays.fill(visited, false);
        visited[src] = true;

        // paths[i] is a GraphPath from src to i
        List<GraphPath> paths = new ArrayList<>(graph.length);
        for (int i = 0; i < graph.length; i++) {
            paths.add(new GraphPath());
        }
        paths.get(src).add(new GraphVertex(src, 0));

        for (int i = 0; i < graph.length; ++i) {
            for (int begin = 0; begin < graph.length; begin++) {
                PriorityQueue<GraphVertex> distances = new PriorityQueue<>();
                if (visited[begin]) {
                    for (int end = 0; end < visited.length; end++) {
                        if (!visited[end] && graph[begin][end] > 0)
                            distances.offer(new GraphVertex(end, graph[begin][end]));
                    }
                }
                if (!distances.isEmpty()) {
                    GraphVertex endVertex = distances.remove();
                    visited[endVertex.vertex] = true;

                    paths.get(endVertex.vertex).addAll(paths.get(begin));
                    paths.get(endVertex.vertex).add(endVertex);
                }
            }
        }

        return paths.get(dest);
    }
}
