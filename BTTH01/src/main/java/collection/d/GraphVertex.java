package collection.d;

public class GraphVertex implements Comparable<GraphVertex> {
    public int vertex;
    public int weight;

    public GraphVertex(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public int compareTo(GraphVertex o) {
        if (o == null) throw new NullPointerException("o");
        return this.weight - o.weight;
    }
}
