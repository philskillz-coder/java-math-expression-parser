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

    public void printTree() {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                } else {
                    String aa = n.token != null ? n.token.getValue().toString() : n.operator.toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    if (n.left != null) {
                        next.add(n.left);
                        nn++;
                    } else {
                        next.add(null);
                    }

                    if (n.right != null) {
                        next.add(n.right);
                        nn++;
                    } else {
                        next.add(null);
                    }
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    public void print() {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<Node>();
        List<Node> next = new ArrayList<Node>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.token != null ? n.token.getValue().toString() : n.operator.operator;
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }


    }

    public void print(String prefix, boolean isTail) {
        root.print();
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
            print("", true);
        }

        public void print(String prefix, boolean isTail) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + (operator != null ? operator : token.getValue().toString()));
            if (right != null) {
                right.print(prefix + (isTail ? "    " : "│   "), false);
            }
            if (left != null) {
                left.print(prefix + (isTail ? "    " : "│   "), true);
            }
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
