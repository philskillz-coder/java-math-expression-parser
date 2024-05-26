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
        }
    };

    static Map<Character, Double> variables = new HashMap<>() {
        {
            put('e', Math.E);
            put('π', Math.PI);
        }
    };

//    Map<String, Function> functions = new HashMap<>() {
//        {
//            put("sin", new Function.Sin());
//            put("cos", new Function.Cos());
//            put("tan", new Function.Tan());
//            put("log", new Function.Log());
//            put("pow", new Function.Pow());
//            put("sqrt", new Function.Sqrt());
//            put("abs", new Function.Abs());
//            put("ceil", new Function.Ceil());
//            put("floor", new Function.Floor());
//            put("round", new Function.Round());
//        }
//    };

    final String expression = "2 * e".replaceAll("\\s", "");

    List<Token<?>> getTokens() { // original
        List<Token<?>> tokens = new ArrayList<>();
        boolean numberHasFloatingPoint = false;
        StringBuilder number = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                if (c == '.') {
                    if (numberHasFloatingPoint) {
                        throw new IllegalArgumentException("Invalid number format");
                    }
                    numberHasFloatingPoint = true;
                }
                number.append(c);
            } else if (operators.containsKey(c)) {
                if (number.length() > 0) {
                    tokens.add(new Token.ImmediateValueToken(Double.parseDouble(number.toString())));
                    number.setLength(0);
                }
                tokens.add(new Token.OperatorToken(c));
            } else {
                if (!variables.containsKey(c)) {
                    throw new IllegalArgumentException("Invalid character: " + c);
                }

                tokens.add(new Token.VariableToken(c));
            }
        }

        if (number.length() > 0) {
            tokens.add(new Token.ImmediateValueToken(Double.parseDouble(number.toString())));
        }

        return tokens;
    }

    public ExpressionTree buildExpressionTree() {
        List<Token<?>> tokens = getTokens();
        Stack<Operator> operators = new Stack<>();
        Stack<ExpressionTree.Node> nodes = new Stack<>();

        for (Token<?> token : tokens) {
            if (token instanceof Token.ImmediateValueToken || token instanceof Token.VariableToken) {
                nodes.push(new ExpressionTree.Node(null, null, null, token));
            } else if (token instanceof Token.OperatorToken) {
                Token.OperatorToken operatorToken = (Token.OperatorToken) token;
                Operator operator = this.operators.get(operatorToken.getValue());

                if (operator.operator == '(') {
                    operators.push(operator);
                } else if (operator.operator == ')') {
                    while (operators.peek().operator != '(') {
                        createAndPushNode(operators, nodes);
                    }
                    operators.pop(); // Pop the '(' operator
                } else {
                    while (!operators.isEmpty() && operator.getPrecedence() <= operators.peek().getPrecedence()) {
                        createAndPushNode(operators, nodes);
                    }
                    operators.push(operator);
                }
            }
        }

        while (!operators.isEmpty()) {
            createAndPushNode(operators, nodes);
        }

        return new ExpressionTree(nodes.pop());
    }

    private void createAndPushNode(Stack<Operator> operators, Stack<ExpressionTree.Node> nodes) {
        Operator operator = operators.pop();
        ExpressionTree.Node right = nodes.pop();
        ExpressionTree.Node left = nodes.pop();
        nodes.push(new ExpressionTree.Node(operator, left, right, null));
    }

    public static void main(String[] args) {
        Main main = new Main();
        ExpressionTree expressionTree = main.buildExpressionTree();
        expressionTree.print();
        double result = expressionTree.evaluate();

        System.out.println(main.expression + " = " + result);
        System.out.println(main.getTokens());
    }
}