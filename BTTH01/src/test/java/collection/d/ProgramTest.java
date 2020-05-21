package collection.d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ProgramTest {

    private static List<int[][]> validGraph() {

        return Arrays.asList(
                new int[][]{
                        {0, 0, 9, 2, 2, 0, 0, 0},
                        {0, 0, 9, 3, 0, 5, 0, 0},
                        {9, 9, 0, 0, 5, 0, 0, 0},
                        {2, 3, 0, 0, 0, 0, 0, 6},
                        {2, 0, 5, 0, 0, 0, 0, 7},
                        {0, 5, 0, 0, 0, 0, 0, 5},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 6, 7, 5, 0, 0}
                },
                new int[][]{
                        {0, 0, 7, 0, 0, 0, 0, 0},
                        {0, 0, 0, 5, 0, 3, 0, 0},
                        {7, 0, 0, 0, 6, 0, 6, 0},
                        {0, 5, 0, 0, 0, 0, 0, 0},
                        {0, 0, 6, 0, 0, 0, 2, 3},
                        {0, 3, 0, 0, 0, 0, 7, 6},
                        {0, 0, 6, 0, 2, 7, 0, 6},
                        {0, 0, 0, 0, 3, 6, 6, 0}
                },
                new int[][]{
                        {0, 2},
                        {3, 0}},
                new int[][]{
                        {0, 2},
                        {3, 0}});
    }

    private static Stream<int[][]> invalidGraphProvider() {
        return Stream.of(
                new int[][]{{0, 2, 3, 4, 5}},
                new int[][]{{0, 2, -3}}
        );
    }

    private static Stream<Arguments> validInputStringProvider() {
        return Stream.of(
                Arguments.of("", new int[][]{}),
                Arguments.of(" ,,,", new int[][]{}),
                Arguments.of("0 1 2 3 ", new int[][]{{0, 1, 2, 3}}),
                Arguments.of("-1 2 3", new int[][]{{-1, 2, 3}}),
                Arguments.of("1,2,3,4,", new int[][]{{1, 2, 3, 4}}),
                Arguments.of(
                        String.format(
                                "0, 1,,2  3%n" +
                                        "4 6 8 -13"
                        ),
                        new int[][]{
                                {0, 1, 2, 3},
                                {4, 6, 8, -13}
                        })
        );
    }

    private static Stream<String> invalidInputStringProvider() {
        return Stream.of(
                "a 1 2 3",
                "0 1 2 3f",
                "0 1.3 2 3"
        );
    }


    private static Stream<Arguments> invalidVerticeProvider() {
        return Stream.of(
                Arguments.of(-1, 1),
                Arguments.of(8, 1),
                Arguments.of(1, -1),
                Arguments.of(1, 8)
        );
    }


    // the arguments in this method is only used for first graph in validGraph() method
    private static Stream<Arguments> vertexProvider() {
        return Stream.of(
                Arguments.of(6, 6, new int[]{6}, 0),
                Arguments.of(6, 5, new int[0], Integer.MAX_VALUE),
                Arguments.of(5, 6, new int[0], Integer.MAX_VALUE),
                Arguments.of(5, 3, new int[]{5, 1, 3}, 8),
                Arguments.of(3, 5, new int[]{3, 1, 5}, 8)
        );
    }

    //=============================//=============================//=============================

    @ParameterizedTest
    @NullSource
    void graph_invalid_nullstring(String input) {
        Assertions.assertThrows(NullPointerException.class, () -> Program.createGraph(input));
    }

    @ParameterizedTest
    @MethodSource("invalidInputStringProvider")
    void graph_invalid(String input) {
        Assertions.assertThrows(NumberFormatException.class, () -> Program.createGraph(input));
    }

    @ParameterizedTest
    @MethodSource("validInputStringProvider")
    void graph(String input, int[][] expected) {
        int[][] graph = Program.createGraph(input);
        Assertions.assertEquals(expected.length, graph.length);
        for (int i = 0; i < graph.length; i++) {
            Assertions.assertArrayEquals(expected[i], graph[i]);
        }
    }

    @ParameterizedTest
    @NullSource
    void dijkstra_nullgraph(int[][] graph) {
        Assertions.assertThrows(NullPointerException.class, () -> Program.dijkstra(graph, 0, 1));
    }

    @ParameterizedTest
    @EmptySource
    void dijkstra_emptygraph(int[][] graph) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Program.dijkstra(graph, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("invalidVerticeProvider")
    void dijkstra_invalid_vertices(int src, int dest) {
        int[][] graph = validGraph().get(0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> Program.dijkstra(graph, src, dest));
    }

    @ParameterizedTest
    @MethodSource("invalidGraphProvider")
    void dijkstra(int[][] graph) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Program.dijkstra(graph, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("vertexProvider")
    void dijkstra_src_dest_test(int src, int dest, int[] expectedPath, int expectedWeight) {
        int[][] graph = validGraph().get(0);
        GraphPath result = Program.dijkstra(graph, src, dest);

        int[] path = result.path();
        int weight = result.totalWeight();

        Assertions.assertArrayEquals(expectedPath, path);
        Assertions.assertEquals(expectedWeight, weight);
    }
}