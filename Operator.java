import java.util.function.BiFunction;

public class Operator {
    final char operator;
    final BiFunction<Double, Double, Double> operation;
    final int precedence;

    public Operator(char operator, BiFunction<Double, Double, Double> operation, int precedence) {
        this.operator = operator;
        this.operation = operation;
        this.precedence = precedence;
    }

    public double apply(double a, double b) {
        return operation.apply(a, b);
    }

    public int getPrecedence() {
        return precedence;
    }
}