package node;

import exception.MissingOperandException;

public final class OpenParentheseNode extends ANode {

    public OpenParentheseNode() {
        super(null, null);
    }

    @Override
    public float evaluate() throws MissingOperandException {
        return 0;
    }

}