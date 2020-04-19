package node;

import exception.MissingOperandException;

public final class CloseParentheseNode extends ANode {

    public CloseParentheseNode() {
        super(null, null);
    }

    @Override
    public float evaluate() throws MissingOperandException {
        return 0;
    }

}