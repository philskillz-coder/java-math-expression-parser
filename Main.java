import java.util.*;

public class Main {
    Map<Character, Operator> operators = new HashMap<>(){
        {
            put('+', new Operator('+', (a, b) -> a + b, 1));
            put('-', new Operator('-', (a, b) -> a - b, 1));
            put('*', new Operator('*', (a, b) -> a * b, 2));
            put('/', new Operator('/', (a, b) -> a / b, 2));
            put('%', new Operator('%', (a, b) -> a % b, 2));
            put('^', new Operator('^', Math::pow, 3));
            put('(', new Operator('(', null, 0));
            put(')', new Operator(')', null, 0));
        }
    };

    static Map<Character, Double> variables = new HashMap<>() {
        {
            put('x', 2.0);
        }
    };

    final String expression = "1 * x".replaceAll("\\s", "");

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
                    tokens.add(new ImmediateValueToken(Double.parseDouble(number.toString())));
                    number.setLength(0);
                }
                tokens.add(new OperatorToken(c));
            } else {
                if (!variables.containsKey(c)) {
                    throw new IllegalArgumentException("Invalid character: " + c);
                }

                tokens.add(new VariableToken(c));
            }
        }

        if (number.length() > 0) {
            tokens.add(new ImmediateValueToken(Double.parseDouble(number.toString())));
        }

        return tokens;
    }

    public ExpressionTree buildExpressionTree() {
        List<Token<?>> tokens = getTokens();
        Stack<Operator> operators = new Stack<>();
        Stack<ExpressionTree.Node> nodes = new Stack<>();

        for (Token<?> token : tokens) {
            if (token instanceof ImmediateValueToken || token instanceof VariableToken) {
                nodes.push(new ExpressionTree.Node(null, null, null, token));
            } else if (token instanceof OperatorToken) {
                OperatorToken operatorToken = (OperatorToken) token;
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

//    public ExpressionTree buildExpressionTree() {
//        List<Token<?>> tokens = getTokens();
//        Stack<Operator> operators = new Stack<>();
//        Stack<ExpressionTree.Node> nodes = new Stack<>();
//
//        for (Token<?> token : tokens) {
//            if (token instanceof ImmediateValueToken) {
//                ImmediateValueToken immediateValueToken = (ImmediateValueToken) token;
//                nodes.push(new ExpressionTree.Node(null, null, null, immediateValueToken));
//            } else if (token instanceof OperatorToken) {
//                OperatorToken operatorToken = (OperatorToken) token;
//                Operator operator = this.operators.get(operatorToken.getValue());
//
//                while (!operators.isEmpty() && operator.getPrecedence() <= operators.peek().getPrecedence()) {
//                    createAndPushNode(operators, nodes);
//                }
//
//                operators.push(operator);
//            }
//        }
//
//        while (!operators.isEmpty()) {
//            createAndPushNode(operators, nodes);
//        }
//
//        return new ExpressionTree(nodes.pop());
//    }

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