package node;

public final class NumberNode implements INode {
    float value;

    public NumberNode(float value) {
        this.value = value;
    }

    @Override
    public float evaluate() {
        return value;
    }
}