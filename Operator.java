import java.util.function.BiFunction;

public abstract class Operator {
    final char operator;
    final int precedence;

    public Operator(char operator, int precedence) {
        this.operator = operator;
        this.precedence = precedence;
    }

    public abstract double apply(double a, double b);

    public int getPrecedence() {
        return precedence;
    }

    public static class Add extends Operator {
        public Add() {
            super('+', 1);
        }

        @Override
        public double apply(double a, double b) {
            return a + b;
        }
    }

    public static class Subtract extends Operator {
        public Subtract() {
            super('-', 1);
        }

        @Override
        public double apply(double a, double b) {
            return a - b;
        }
    }

    public static class Multiply extends Operator {
        public Multiply() {
            super('*', 2);
        }

        @Override
        public double apply(double a, double b) {
            return a * b;
        }
    }

    public static class Divide extends Operator {
        public Divide() {
            super('/', 2);
        }

        @Override
        public double apply(double a, double b) {
            return a / b;
        }
    }

    public static class Power extends Operator {
        public Power() {
            super('^', 3);
        }

        @Override
        public double apply(double a, double b) {
            return Math.pow(a, b);
        }
    }

    public static class Modulo extends Operator {
        public Modulo() {
            super('%', 2);
        }

        @Override
        public double apply(double a, double b) {
            return a % b;
        }
    }

    public static class OpenParenthesis extends Operator {
        public OpenParenthesis() {
            super('(', 0);
        }

        @Override
        public double apply(double a, double b) {
            throw new UnsupportedOperationException("Open parenthesis cannot be applied");
        }
    }

    public static class CloseParenthesis extends Operator {
        public CloseParenthesis() {
            super(')', 0);
        }

        @Override
        public double apply(double a, double b) {
            throw new UnsupportedOperationException("Close parenthesis cannot be applied");
        }
    }

}