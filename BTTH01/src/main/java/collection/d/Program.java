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
                Stack<GraphVertex> path = dijkstra(graph, params.get(0), params.get(1));

                System.out.println("Finding path...");
                if (path.empty()) System.out.printf("Cannot find path from %d to %d", params.get(0), params.get(1));
                else System.out.printf("Path found!%n%s", display(path));

            } catch (IOException e) {
                System.err.println("An error happened when opening file");
            } catch (IndexOutOfBoundsException iobe) {
                System.err.println("Invalid start or stop vetex");
            }
        } else System.err.println("Error path not found");

        scanner.close();
    }

    private static String display(Stack<GraphVertex> path) {
        StringBuilder builder = new StringBuilder();
        int sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            sum += path.get(i + 1).weight;
            builder.append(String.format("%d -> %d: %d%n", path.get(i).vertex, path.get(i + 1).vertex, sum));
        }
        return builder.toString();
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
    public static Stack<GraphVertex> dijkstra(int[][] graph, int src, int dest) {
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

        Stack<GraphVertex> state = new Stack<>();
        state.push(new GraphVertex(src, 0));

        return find(graph, dest, visited, state);
    }

    /**
     * @apiNote method uses greedy algorithm and {@link PriorityQueue} to find shortest distance
     */
    private static Stack<GraphVertex> find(int[][] graph, int dest, boolean[] visited, Stack<GraphVertex> state) {
        if (state.size() == 0) return state;
        int src = state.peek().vertex;
        if (src == dest) return state;

        PriorityQueue<GraphVertex> distances = new PriorityQueue<>();
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i] && graph[src][i] > 0) {
                distances.add(new GraphVertex(i, graph[src][i]));
            }
        }

        // can't find any vertex to go so we backtrack here
        if (distances.isEmpty()) {
            GraphVertex vertex = state.pop();
            return find(graph, dest, visited, state);
        }

        // get the vertext with shortest distance
        GraphVertex vertex = distances.remove();
        src = vertex.vertex;
        visited[src] = true;
        state.push(vertex);

        return find(graph, dest, visited, state);
    }
}
