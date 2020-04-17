package main;

import java.util.regex.Pattern;
import exception.SyntaxError;
import node.ANode;

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
        exp = removeWhiteSpace(exp);
        if (!checkSyntax(exp))
            throw new SyntaxError();

        // convert to prefix expression
        try {
            ArrayList<Object> infix = parse(exp);
            Stack<Object> prefix = toPrefix(infix);
            ANode root = buildNode(prefix);
            if (root == null) throw new SyntaxError();
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
    private static ArrayList<Object> parse(String exp) {
        String validNumbers = "0123456789.";
        ArrayList<Object> list = new ArrayList<>();
        StringBuilder numBuiler = new StringBuilder();
        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if (validNumbers.indexOf(ch) >= 0)
                numBuiler.append(ch);
            else {
                if (numBuiler.length() > 0)
                    list.add(Float.parseFloat(numBuiler.toString()));
                list.add(ch);
                numBuiler = new StringBuilder();
            }
        }
        if (numBuiler.length() > 0)
            list.add(Float.parseFloat(numBuiler.toString()));
        return list;
    }

    private static Stack<Object> toPrefix(ArrayList<Object> infix) throws EmptyStackException {
        Stack<Object> output = new Stack<>();
        Stack<Float> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        Object left, right;
        for (Object o : infix) {
            if (o instanceof Float)
                operands.push((Float) o);
            else {
                Character ch = (Character) o;
                if (ch == '(')
                    operators.push(ch);
                else if (ch == ')') {
                    while (!operators.empty() && operators.peek() != '(') {

                        // Add operands and operator in form operator + operand1 + operand2.
                        right = operands.size() < 2 ? output.pop() : operands.pop();
                        left = operands.empty() ? output.pop() : operands.pop();
                        output.add(right);
                        output.add(left);
                        output.add(operators.pop());
                    }
                    // Pop opening bracket from stack.
                    operators.pop();
                }
                // If current character is an operator, then push it into operators stack after
                // popping high priority operators from operators stack and pushing result in
                // operands stack.
                else {
                    while (!operators.empty() && getPriority(ch) <= getPriority(operators.peek())) {

                        // Add operands and operator in form operator + operand1 + operand2.
                        right = operands.size() < 2 ? output.pop() : operands.pop();
                        left = operands.empty() ? output.pop() : operands.pop();
                        output.add(right);
                        output.add(left);
                        output.add(operators.pop());
                    }
                    operators.push(ch);
                }
            }

        }

        // Pop operators from operators stack until it is empty and operation in add
        // result of each pop operands stack.
        while (!operators.empty()) {
            // Add operands and operator in form operator + operand1 + operand2.
            right = operands.size() < 2 ? output.pop() : operands.pop();
            left = operands.empty() ? output.pop() : operands.pop();
            output.add(right);
            output.add(left);
            output.add(operators.pop());
        }
        return output;
    }

    /** build expression tree from prefix expression */
    private static ANode buildNode(Stack<Object> nodes) throws EmptyStackException {
        if (nodes.empty())
            return null;
        Object o = nodes.peek();
        if (o instanceof Float)
            return ANode.Factory.create((float) o);

        nodes.pop();
        ANode left = buildNode(nodes);
        nodes.pop();
        ANode right = buildNode(nodes);

        return ANode.Factory.create((char) o, left, right);
    }

    private static String removeWhiteSpace(String s) {
        return s.replaceAll("\\s", "");
    }

    // Function to find priority of given operator.
    static int getPriority(char ch) {
        if (ch == '-' || ch == '+')
            return 1;
        else if (ch == '*' || ch == '/')
            return 2;
        return 0;
    }
}