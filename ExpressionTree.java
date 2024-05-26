public class ExpressionTree {
    private final Node root;

    public ExpressionTree(Node root) {
        this.root = root;
    }

    public double evaluate() {
        return root.evaluate();
    }

    public void print() {
        root.print();
        System.out.println();
    }

    public static class Node {
        private final Operator operator;
        private final Node left;
        private final Node right;
        private final Token<?> token;

        public Node(Operator operator, Node left, Node right, Token<?> token) {
            this.operator = operator;
            this.left = left;
            this.right = right;
            this.token = token;
        }

        public double evaluate() {
            if (operator == null) {
                double value;
                if (token instanceof Token.VariableToken) {
                    if (!Main.variables.containsKey(((Token.VariableToken) token).getValue()))
                        throw new IllegalArgumentException("Variable " + ((Token.VariableToken) token).getValue() + " is not defined");

                    value = Main.variables.get(((Token.VariableToken) token).getValue());
                } else {
                    value = (double) token.getValue();
                }

                return value;
            }

            double leftValue = left.evaluate();
            double rightValue = right.evaluate();

            return operator.apply(leftValue, rightValue);
        }

        public void print() {
            if (operator == null) {
                System.out.print(token.getValue());
                return;
            }

            System.out.print("(");
            left.print();
            System.out.print(" " + operator.operator + " ");
            right.print();
            System.out.print(")");
        }
    }

}
