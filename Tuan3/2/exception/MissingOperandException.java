package exception;
public final class MissingOperandException extends SyntaxError {
    private static final long serialVersionUID = 1L;

    public MissingOperandException() {
        this(MissingOperandException.class.getName());
    }

    public MissingOperandException(String message) {
        super(message);
    }
}