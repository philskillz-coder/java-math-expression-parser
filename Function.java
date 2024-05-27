public abstract class Function extends Operator {

    @Override
    public String toString() {
        return operator;
    }

    public Function(String operator) {
        super(operator, 0);
    }

    public static class Sin extends Function {
        public Sin() {
            super("sin");
        }

        @Override
        public double apply(double... args) {
            return Math.sin(args[0]);
        }
    }

    public static class Cos extends Function {
        public Cos() {
            super("cos");
        }

        @Override
        public double apply(double... args) {
            return Math.cos(args[0]);
        }
    }

    public static class Tan extends Function {
        public Tan() {
            super("tan");
        }

        @Override
        public double apply(double... args) {
            return Math.tan(args[0]);
        }
    }

    public static class Log extends Function {
        public Log() {
            super("log");
        }

        @Override
        public double apply(double... args) {
            if (args.length > 1)
                return Math.log(args[0]) / Math.log(args[1]);

            return Math.log(args[0]);
        }
    }

    public static class Pow extends Function {
        public Pow() {
            super("pow");
        }

        @Override
        public double apply(double... args) {
            return Math.pow(args[0], args[1]);
        }
    }

    public static class Sqrt extends Function {
        public Sqrt() {
            super("sqrt");
        }

        @Override
        public double apply(double... args) {
            return Math.sqrt(args[0]);
        }
    }

    public static class Abs extends Function {
        public Abs() {
            super("abs");
        }

        @Override
        public double apply(double... args) {
            return Math.abs(args[0]);
        }
    }

    public static class Ceil extends Function {
        public Ceil() {
            super("ceil");
        }

        @Override
        public double apply(double... args) {
            return Math.ceil(args[0]);
        }
    }

    public static class Floor extends Function {
        public Floor() {
            super("floor");
        }

        @Override
        public double apply(double... args) {
            return Math.floor(args[0]);
        }
    }

    public static class Round extends Function {
        public Round() {
            super("round");
        }

        @Override
        public double apply(double... args) {
            if (args.length > 1)
                return Math.round(args[0] * Math.pow(10, args[1])) / Math.pow(10, args[1]);

            return Math.round(args[0]);
        }
    }

    public static class Max extends Function {
        public Max() {
            super("max");
        }

        @Override
        public double apply(double... args) {
            double max = args[0];
            for (int i = 1; i < args.length; i++) {
                if (args[i] > max) {
                    max = args[i];
                }
            }
            return max;
        }
    }

    public static class Min extends Function {
        public Min() {
            super("min");
        }

        @Override
        public double apply(double... args) {
            double min = args[0];
            for (int i = 1; i < args.length; i++) {
                if (args[i] < min) {
                    min = args[i];
                }
            }
            return min;
        }
    }


}
