package node;

import java.lang.reflect.Type;

import exception.MissingOperandException;

public abstract class OptNode implements INode {
    protected INode left;
    protected INode right;
    protected Float result;

    public OptNode(INode left, INode right) {
        this.left = left;
        this.right = right;
    }

    public abstract float evaluate() throws MissingOperandException;

    public static final class Factory {
        private Factory() {
        }

        public static OptNode create(Type type, INode left, INode right) {
            if (type.equals(SubtractNode.class))
                return new SubtractNode(left, right);
            else if (type.equals(PlusNode.class))
                return new PlusNode(left, right);
            else if (type.equals(MultiplyNode.class))
                return new MultiplyNode(left, right);
            else if (type.equals(DivisionNode.class))
                return new DivisionNode(left, right);
            return null;
        }
    }
}