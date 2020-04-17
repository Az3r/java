package node;

public final class NumberNode extends ANode {

    public NumberNode(float value) {
        super(null, null);
        this.result = value;
    }

    @Override
    public float evaluate() {
        return result;
    }
}