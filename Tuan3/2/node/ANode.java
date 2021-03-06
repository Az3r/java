package node;

import java.lang.reflect.Type;
import java.util.InputMismatchException;
import exception.MissingOperandException;

public abstract class ANode  {
    public ANode left;
    public ANode right;
    protected Float result;

    protected ANode(ANode left, ANode right) {
        this.left = left;
        this.right = right;
    }

    public abstract float evaluate() throws MissingOperandException;

    public static final class Factory {
        private Factory() {
        }

        public static ANode create(char opt, ANode left, ANode right) {
            if (opt == '-')
                return new SubtractNode(left, right);
            else if (opt == '+')
                return new PlusNode(left, right);
            else if (opt == '*')
                return new MultiplyNode(left, right);
            else if (opt == '/')
                return new DivisionNode(left, right);
            else if (opt == '(')
                return new OpenParentheseNode();
            else if (opt == ')')
                return new CloseParentheseNode();
            throw new InputMismatchException("operator is " + opt);
        }

        public static ANode create(float value){
            return new NumberNode(value);
        }

        public static ANode create(char opt) {
            return create(opt,null,null);
        }
    }
}