import java.util.function.BiFunction;

public abstract class Operator {
    final String operator;
    final int precedence;


    @Override
    public String toString() {
        return operator;
    }

    public Operator(String operator, int precedence) {
        this.operator = operator;
        this.precedence = precedence;
    }

    public Operator(char operator, int precedence) {
        this(String.valueOf(operator), precedence);
    }

    public abstract double apply(double... args);

    public int getPrecedence() {
        return precedence;
    }

    public static class Add extends Operator {
        public Add() {
            super('+', 1);
        }

        @Override
        public double apply(double... args) {
            return args[0] + args[1];
        }
    }

    public static class Subtract extends Operator {
        public Subtract() {
            super('-', 1);
        }

        @Override
        public double apply(double... args) {
            return args[0] - args[1];
        }
    }

    public static class Multiply extends Operator {
        public Multiply() {
            super('*', 2);
        }

        @Override
        public double apply(double... args) {
            return args[0] * args[1];
        }
    }

    public static class Divide extends Operator {
        public Divide() {
            super('/', 2);
        }

        @Override
        public double apply(double... args) {
            return args[0] / args[1];
        }
    }

    public static class Power extends Operator {
        public Power() {
            super('^', 3);
        }

        @Override
        public double apply(double... args) {
            return Math.pow(args[0], args[1]);
        }
    }

    public static class Modulo extends Operator {
        public Modulo() {
            super('%', 2);
        }

        @Override
        public double apply(double... args) {
            return args[0] % args[1];
        }
    }

}