package collection.d;

import java.util.Stack;

public class GraphPath extends Stack<GraphVertex> implements Comparable<GraphPath> {

    public GraphPath() {
    }


    public int totalWeight() {
        if (this.isEmpty()) return Integer.MAX_VALUE;
        return this.stream().mapToInt(value -> value.weight).sum();
    }

    public int[] path() {
        return this.stream().mapToInt(value -> value.vertex).toArray();
    }

    public String displayPathWeight() {
        StringBuilder builder = new StringBuilder();

        int sum = 0;
        for (int i = 0; i < size() - 1; i++) {
            sum += get(i + 1).weight;
            builder.append(String.format("%d -> %d: %d%n", get(i).vertex, get(i + 1).vertex, sum));
        }
        return builder.toString();
    }

    public String displayPath() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size() - 1; i++) {
            builder.append(String.format("%d -> ", get(i).vertex));
        }
        builder.append(get(size() - 1).vertex);
        return builder.toString();
    }

    @Override
    public int compareTo(GraphPath o) {
        if (o == null) throw new NullPointerException("o");
        return this.totalWeight() - o.totalWeight();
    }
}
