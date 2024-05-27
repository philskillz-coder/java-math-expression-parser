public abstract class Token<T> {
    private final T value;

    public Token(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static class ImmediateValueToken extends Token<Double> {
        public ImmediateValueToken(Double value) {
            super(value);
        }
    }

    public static class OperatorToken extends Token<Character> {
        public OperatorToken(Character value) {
            super(value);
        }
    }

    public static class VariableToken extends Token<Character> {
        public VariableToken(Character value) {
            super(value);
        }
    }

    public static class FunctionToken extends Token<Function> {
        public FunctionToken(Function value) {
            super(value);
        }
    }

    public static class FunctionEndToken extends Token<Character> {
        public FunctionEndToken(Character value) {
            super(value);
        }
    }

    public static class ParenthesisToken extends Token<Character> {
        public ParenthesisToken(Character value) {
            super(value);
        }
    }

    public static class CommaToken extends Token<Character> {
        public CommaToken(Character value) {
            super(value);
        }
    }

    public static class ParamToken extends Token<Character> {
        public ParamToken(Character value) {
            super(value);
        }
    }
}