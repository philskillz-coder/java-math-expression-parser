import java.util.*;

public class Main {
    Map<Character, Operator> operators = new HashMap<>(){
        {
            put('+', new Operator.Add());
            put('-', new Operator.Subtract());
            put('*', new Operator.Multiply());
            put('/', new Operator.Divide());
            put('%', new Operator.Modulo());
            put('^', new Operator.Power());
            put('~', new Function("Harmonic mean"){
                @Override
                public double apply(double... args) {
                    double a = args[0];
                    double b = args[1];
                    return 2*a*b/(a+b);
                }
            });
            put('!', new Operator('!', 4) {
                @Override
                public double apply(double... args) {
                    return -args[1];
                }
            });
        }
    };

    static Map<Character, Double> variables = new HashMap<>() {
        {
            put('e', Math.E);
            put('π', Math.PI);
        }
    };

    Map<String, Function> functions = new HashMap<>() {
        {
            put("sin", new Function.Sin());
            put("cos", new Function.Cos());
            put("tan", new Function.Tan());
            put("log", new Function.Log());
            put("pow", new Function.Pow());
            put("sqrt", new Function.Sqrt());
            put("abs", new Function.Abs());
            put("ceil", new Function.Ceil());
            put("floor", new Function.Floor());
            put("round", new Function.Round());
            put("min", new Function.Min());
            put("max", new Function.Max());
        }
    };



    final String expression = "4*(1-cos(2*53)/2) - log(sin(30))".replaceAll("\\s", "");

    List<Token<?>> tokenize() {
        List<Token<?>> tokens = new ArrayList<>();
        boolean numberHasFloatingPoint = false;
        StringBuilder numberOrFunctionOrVariable = new StringBuilder();
        int openParenthesisCount = 0;
        boolean lastTokenIsFunction = false;

        for (char c : expression.toCharArray()) {
            if (c == ',') {
                assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                if (numberOrFunctionOrVariable.length() > 0) {
                    String str = numberOrFunctionOrVariable.toString();
                    if (numberHasFloatingPoint || Character.isDigit(str.charAt(0))){
                        tokens.add(new Token.ImmediateValueToken(Double.parseDouble(str)));
                    } else if (functions.containsKey(str)) {
                        tokens.add(new Token.FunctionToken(functions.get(str)));
                        lastTokenIsFunction = true;
                    } else {
                        tokens.add(new Token.VariableToken(str.charAt(0)));
                    }
                    numberOrFunctionOrVariable.setLength(0);
                    numberHasFloatingPoint = false;
                }
                tokens.add(new Token.CommaToken(c));
            } else if (Character.isDigit(c) || c == '.') {
                assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                if (c == '.') {
                    if (numberHasFloatingPoint) {
                        throw new IllegalArgumentException("Invalid number format");
                    }
                    numberHasFloatingPoint = true;
                }
                numberOrFunctionOrVariable.append(c);
            } else if (Character.isLetter(c)) {
                assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                numberOrFunctionOrVariable.append(c);
            } else {
                if (numberOrFunctionOrVariable.length() > 0) {
                    assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                    String str = numberOrFunctionOrVariable.toString();
                    if (numberHasFloatingPoint || Character.isDigit(str.charAt(0))){
                        tokens.add(new Token.ImmediateValueToken(Double.parseDouble(str)));
                    } else if (functions.containsKey(str)) {
                        lastTokenIsFunction = true;
                        tokens.add(new Token.FunctionToken(functions.get(str)));
                    } else {
                        tokens.add(new Token.VariableToken(str.charAt(0)));
                    }
                    numberOrFunctionOrVariable.setLength(0);
                    numberHasFloatingPoint = false;
                }
                if (operators.containsKey(c)) {
                    assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                    tokens.add(new Token.OperatorToken(c));
                } else if (c == '(' || c == ')') {
                    switch (c) {
                        case '(':
                            lastTokenIsFunction = false;
                            openParenthesisCount++;
                            break;
                        case ')':
                            assert !lastTokenIsFunction : "Token after function should be a parenthesis";
                            assert openParenthesisCount > 0 : "Unmatched parenthesis";
                            openParenthesisCount--;
                            break;
                    }
                    tokens.add(new Token.ParenthesisToken(c));
                }
            }
        }

        if (numberOrFunctionOrVariable.length() > 0) {
            String str = numberOrFunctionOrVariable.toString();
            if (numberHasFloatingPoint || Character.isDigit(str.charAt(0))) {
                tokens.add(new Token.ImmediateValueToken(Double.parseDouble(str)));
            } else if (functions.containsKey(str)) {
                assert false : "Function without body";
            } else {
                tokens.add(new Token.VariableToken(str.charAt(0)));
            }
        }

        assert openParenthesisCount == 0 : "Unmatched parenthesis";

        return tokens;
    }

    private int buildExpressionTreeRecursive(List<Token<?>> tokens, int index, Stack<Operator> operators, Stack<ExpressionTree.Node> nodes) {
        for (; index < tokens.size(); index++) {
            Token<?> token = tokens.get(index);

            if (token instanceof Token.ImmediateValueToken || token instanceof Token.VariableToken) {
                nodes.push(new ExpressionTree.Node(null, null, null, token));
            } else if (token instanceof Token.OperatorToken) {
                Token.OperatorToken operatorToken = (Token.OperatorToken) token;
                Operator operator = this.operators.get(operatorToken.getValue());

                while (!operators.isEmpty() && operator.getPrecedence() <= operators.peek().getPrecedence()) {
                    createAndPushNode(operators, nodes);
                }

                operators.push(operator);
            } else if (token instanceof Token.ParenthesisToken) {
                Token.ParenthesisToken parenthesisToken = (Token.ParenthesisToken) token;

                if (parenthesisToken.getValue() == '(') {
                    index = buildExpressionTreeRecursive(tokens, index + 1, operators, nodes);
                } else {
                    while (operators.peek().operator != "(") {
                        createAndPushNode(operators, nodes);
                    }
                    operators.pop(); // Pop the '(' operator

                    // If the top of the stack is a function, handle it specially
//                    if (!operators.isEmpty() && operators.peek() instanceof Operator.FunctionOp) {
//                        Operator functionOp = operators.pop();
//                        ExpressionTree.Node functionNode = nodes.pop();
//                        createFunctionNode(functionOp, functionNode, nodes);
//                    }

                    return index;
                }
            } else if (token instanceof Token.FunctionToken) {
                nodes.push(new ExpressionTree.Node(null, null, null, token));
//                operators.push(new Operator.FunctionOp(((Token.FunctionToken) token).getValue()));
            } else if (token instanceof Token.CommaToken) {
                while (operators.peek().operator != "(") {
                    createAndPushNode(operators, nodes);
                }
            }
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            createAndPushNode(operators, nodes);
        }

        return index;
    }

    public Stack<ExpressionTree.Node> buildExpressionTreeRecursiveNew(List<Token<?>> tokens) {
        Stack<ExpressionTree.Node> nodes = new Stack<>();
        Stack<Operator> operators = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token<?> token = tokens.get(i);

            if (token instanceof Token.ImmediateValueToken || token instanceof Token.VariableToken) {
                nodes.push(new ExpressionTree.Node(null, null, null, token));
            } else if (token instanceof Token.OperatorToken) {
                Token.OperatorToken operatorToken = (Token.OperatorToken) token;
                Operator operator = this.operators.get(operatorToken.getValue());

                while (!operators.isEmpty() && operator.getPrecedence() <= operators.peek().getPrecedence()) {
                    createAndPushNode(operators, nodes);
                }

                operators.push(operator);
            } else if (token instanceof Token.ParenthesisToken) {
                Token.ParenthesisToken parenthesisToken = (Token.ParenthesisToken) token;

                if (parenthesisToken.getValue() == '(') {
                    // Recursive call to build expression tree for the sub-expression
                    // find the matching closing parenthesis
                    int startPosition = i + 1; // start from the next token
                    int endPosition = findClosingParenthesis(tokens, startPosition);

                    List<Token<?>> subExpressionTokens = tokens.subList(startPosition, endPosition);
                    Stack<ExpressionTree.Node> subExpressionTree = buildExpressionTreeRecursiveNew(subExpressionTokens);
                    nodes.push(subExpressionTree.pop());
                    i = endPosition; // skip the sub-expression tokens
                } // you cant get a closing parenthesis here because it is handled in the recursive call
            } else if (token instanceof Token.FunctionToken) {
                // similar to parenthesis, we need to build the function node and its parameter list
                // after the function token, there should be a parenthesis
                // find the matching closing parenthesis
                int startPosition = i + 2; // start from the next token after the par token
                int endPosition = findClosingParenthesis(tokens, startPosition);

                // find all parameters separated by commas between the parenthesis
                List<List<Token<?>>> parameters = new ArrayList<>();
                List<Token<?>> currentParameter = new ArrayList<>();
                for (int j=startPosition; j<endPosition; j++) {
                    if (tokens.get(j) instanceof Token.CommaToken) {
                        parameters.add(currentParameter);
                        currentParameter = new ArrayList<>();
                    } else {
                        currentParameter.add(tokens.get(j));
                    }
                }

                if (!currentParameter.isEmpty()) {
                    parameters.add(currentParameter);
                }

                // create parameter nodes
                List<ExpressionTree.Node> rootParameterNodes = new ArrayList<>();
                for (List<Token<?>> parameter : parameters) {
                    Stack<ExpressionTree.Node> parameterNodes = buildExpressionTreeRecursiveNew(parameter);
                    rootParameterNodes.add(parameterNodes.pop());
                }

                // Make the root parameter nodes into a single node (reversed: first parameter is the root node)
                // Left side: next parameter
                ExpressionTree.Node firstParamNode = null;


                Token<?> paramToken = new Token.ParamToken('p'); // Adjust this based on your Token class

                for (int p = rootParameterNodes.size() - 1; p >= 0; p--) {
                    ExpressionTree.Node currentNode = rootParameterNodes.get(p);
                    if (firstParamNode == null) {
                        firstParamNode = new ExpressionTree.Node(null, null, currentNode, paramToken);
                    } else {
                        // Create a new node with firstParamNode as the left child and currentNode as the right child
                        firstParamNode = new ExpressionTree.Node(null, firstParamNode, currentNode, paramToken);
                    }
                }

                // create function node
                Function func = ((Token.FunctionToken) token).getValue();
                ExpressionTree.FunctionRootNode functionNode = new ExpressionTree.FunctionRootNode(func, firstParamNode, null, new Token.FunctionToken(func));

                // push the function node to the nodes stack
                nodes.push(functionNode);
                i = endPosition; // skip the function and its parameters
            }
        }

        while (!operators.isEmpty()) {
            createAndPushNode(operators, nodes);
        }

        return nodes;
    }

    /**
     * Find the closing parenthesis for the given opening parenthesis
     * @param tokens list of tokens
     * @param startPosition index of the opening parenthesis
     * @return index of the closing parenthesis
     */
    private static int findClosingParenthesis(List<Token<?>> tokens, int startPosition) {
        int endPosition = -1;
        int openParenthesisCount = 1;

        for (int j = startPosition; j < tokens.size(); j++) {
            if (tokens.get(j) instanceof Token.ParenthesisToken) {
                Token.ParenthesisToken token1 = (Token.ParenthesisToken) tokens.get(j);
                if (token1.getValue() == '(') {
                    openParenthesisCount++;
                } else if (token1.getValue() == ')') {
                    openParenthesisCount--;
                    if (openParenthesisCount == 0) {
                        endPosition = j;
                        break;
                    }
                }
            }
        }

        if (endPosition == -1) { // should never happen because tokenizer should catch this
            throw new IllegalArgumentException("Unmatched parenthesis");
        }
        return endPosition;
    }

    public ExpressionTree buildExpressionTreeNew() {
        List<Token<?>> tokens = tokenize();
        Stack<ExpressionTree.Node> nodes = buildExpressionTreeRecursiveNew(tokens);

        return new ExpressionTree(nodes.pop());
    }

    private void createAndPushNode(Stack<Operator> operators, Stack<ExpressionTree.Node> nodes) {
        Operator operator = operators.pop();
        ExpressionTree.Node rightNode = nodes.pop();
        ExpressionTree.Node leftNode = nodes.pop();
        nodes.push(new ExpressionTree.Node(operator, leftNode, rightNode, null));
    }



    public static void main(String[] args) {
        Main main = new Main();
        ExpressionTree expressionTree = main.buildExpressionTreeNew();
        expressionTree.print();
        double result = expressionTree.evaluate();

        System.out.println(main.expression + " = " + result);
        System.out.println(main.tokenize());
    }
}