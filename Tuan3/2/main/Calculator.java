package main;

import java.util.regex.Pattern;
import exception.SyntaxError;
import node.ANode;
import node.CloseParentheseNode;
import node.DivisionNode;
import node.MultiplyNode;
import node.NumberNode;
import node.OpenParentheseNode;
import node.PlusNode;
import node.SubtractNode;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;

public final class Calculator {
    private static char[] opts;

    public Calculator() {
        if (opts == null) {
            opts = new char[256];
            opts['+'] = 1;
            opts['-'] = 1;
            opts['*'] = 1;
            opts['/'] = 1;
        }
    }

    public float calculate(String exp) throws SyntaxError {
        String s = exp.replaceAll("\\s", "");
        if (!checkSyntax(exp))
            throw new SyntaxError();

        // convert to prefix expression
        try {
            ArrayList<ANode> infix = parse(s);
            ANode root = buildExpressionTree(infix);
            if (root == null)
                throw new SyntaxError();
            return root.evaluate();
        } catch (EmptyStackException e) {
            throw new SyntaxError();
        }
    }

    /** check if given string has correct syntax */
    private static boolean checkSyntax(String s) {
        String regex = "[^0-9+*/\\(\\)-.]";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(s);
        return !m.find();
    }

    /** parse expression string into a list of numbers and operators */
    private static ArrayList<ANode> parse(String exp) {
        String validNumbers = "0123456789.";
        ArrayList<ANode> list = new ArrayList<>();
        StringBuilder numBuiler = new StringBuilder();
        if (exp.charAt(0) == '-')
            numBuiler.append(0);
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if (validNumbers.indexOf(ch) >= 0)
                numBuiler.append(ch);
            else {
                if (numBuiler.length() > 0) {
                    float f = Float.parseFloat(numBuiler.toString());
                    list.add(new NumberNode(f));
                }
                list.add(ANode.Factory.create(ch, null, null));
                numBuiler = new StringBuilder();
            }
        }
        if (numBuiler.length() > 0) {
            float f = Float.parseFloat(numBuiler.toString());
            list.add(new NumberNode(f));
        }
        return list;
    }

    private static ANode buildExpressionTree(ArrayList<ANode> infix) throws EmptyStackException {
        Stack<ANode> operands = new Stack<>();
        Stack<ANode> operators = new Stack<>();
        for (final ANode node : infix) {
            if (node instanceof NumberNode)
                operands.push(node);
            else {

                if (node instanceof OpenParentheseNode)
                    operators.push(node);
                else if (node instanceof CloseParentheseNode) {
                    while (!(operators.empty() || operators.peek() instanceof OpenParentheseNode)) {
                        // Add operands and operator in form operator + operand1 + operand2.
                        ANode opt = operators.pop();
                        opt.right = operands.pop();
                        opt.left = operands.pop();
                        operands.push(opt);
                    }
                    // Pop opening bracket from stack.
                    operators.pop();
                }
                // If current character is an operator, then push it into operators stack after
                // popping high priority operators from operators stack and pushing result in
                // operands stack.
                else {
                    while (!(operators.empty() || getPriority(node) > getPriority(operators.peek()))) {
                        // Add operands and operator in form operator + operand1 + operand2.
                        ANode opt = operators.pop();
                        opt.right = operands.pop();
                        opt.left = operands.pop();
                        operands.push(opt);
                    }
                    operators.push(node);
                }
            }

        }

        // Pop operators from operators stack until it is empty and operation in add
        // result of each pop operands stack.
        while (!operators.empty()) {
            // Add operands and operator in form operator + operand1 + operand2.
            ANode opt = operators.pop();
            opt.right = operands.pop();
            opt.left = operands.pop();
            operands.push(opt);
        }
        return operands.peek();
    }

    // Function to find priority of given operator.
    static int getPriority(ANode ch) {
        if (ch instanceof SubtractNode || ch instanceof PlusNode)
            return 1;
        else if (ch instanceof MultiplyNode || ch instanceof DivisionNode)
            return 2;
        return 0;
    }
}