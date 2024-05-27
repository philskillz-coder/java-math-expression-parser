import java.util.ArrayList;
import java.util.List;

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
        protected final Operator operator;
        protected Node left;
        protected final Node right;
        protected final Token<?> token;

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
            if (left != null) {
                left.print();
            } else {
                System.out.print("null");
            }

            System.out.print(" " + operator.operator + " ");
            if (right != null) {
                right.print();
            } else {
                System.out.print("null");
            }
            System.out.print(")");
        }
    }

    public static class FunctionRootNode extends Node {

        public FunctionRootNode(Operator operator, Node left, Node right, Token<?> token) {
            super(operator, left, right, token);
        }

        public static List<Node> parseTreeNodes(Node root) {
            List<Node> nodes = new ArrayList<>();
            collectNodes(root, nodes);
            return nodes;
        }

        private static void collectNodes(Node node, List<Node> nodes) {
            if (node == null) {
                return;
            }
            nodes.add(node);
            collectNodes(node.left, nodes);
            collectNodes(node.right, nodes);
        }

        @Override
        public double evaluate() {
            // parameter nodes:
            // left side: next parameter
            // right side: parameter value node

            List<Node> parameters = new ArrayList<>();
            Node current = left;
            while (current != null) {
                parameters.add(current.right);
                current = current.left;
            }

            double[] evaluated = parameters.stream().mapToDouble(Node::evaluate).toArray();

            return operator.apply(evaluated);
        }
    }

}
