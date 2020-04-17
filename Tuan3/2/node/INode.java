package node;

import exception.MissingOperandException;

public interface INode {
    float evaluate() throws MissingOperandException;
}