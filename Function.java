public abstract class Function extends Operator {

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

    public static class Random extends Function {
        public Random() {
            super("random");
        }

        @Override
        public double apply(double... args) {
            if (args.length == 0) {
                return Math.random(); // Random number between 0 and 1
            } else if (args.length == 1) {
                return Math.random() * args[0]; // Random number between 0 and args[0]
            } else {
                return Math.random() * (args[1] - args[0]) + args[0]; // Random number between args[0] and args[1]
            }
        }
    }




    public static class BitwiseAndGate extends Function {
        public BitwiseAndGate() {
            super("band");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result &= (int)args[i];
            }
            return result;
        }
    }

    public static class BitwiseOrGate extends Function {
        public BitwiseOrGate() {
            super("bor");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result |= (int)args[i];
            }
            return result;
        }
    }

    public static class BitwiseXorGate extends Function {
        public BitwiseXorGate() {
            super("bxor");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result ^= (int)args[i];
            }
            return result;
        }
    }

    public static class BitwiseNotGate extends Function {
        public BitwiseNotGate() {
            super("bnot");
        }

        @Override
        public double apply(double... args) {
            return ~(int)args[0]; // NOT operation is only applicable to a single input
        }
    }

    public static class BitwiseNandGate extends Function {
        public BitwiseNandGate() {
            super("bnand");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result &= (int)args[i];
            }
            return ~result;
        }
    }

    public static class BitwiseNorGate extends Function {
        public BitwiseNorGate() {
            super("bnor");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result |= (int)args[i];
            }
            return ~result;
        }
    }

    public static class BitwiseXnorGate extends Function {
        public BitwiseXnorGate() {
            super("bxnor");
        }

        @Override
        public double apply(double... args) {
            int result = (int)args[0];
            for (int i = 1; i < args.length; i++) {
                result ^= (int)args[i];
            }
            return ~result;
        }
    }

    public static class LogicalAndGate extends Function {
        public LogicalAndGate() {
            super("land");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result &= args[i] != 0;
            }
            return result ? 1 : 0;
        }
    }

    public static class LogicalOrGate extends Function {
        public LogicalOrGate() {
            super("lor");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result |= args[i] != 0;
            }
            return result ? 1 : 0;
        }
    }

    public static class LogicalNotGate extends Function {
        public LogicalNotGate() {
            super("lnot");
        }

        @Override
        public double apply(double... args) {
            return (args[0] != 0) ? 0 : 1; // NOT operation is only applicable to a single input
        }
    }

    public static class LogicalNandGate extends Function {
        public LogicalNandGate() {
            super("lnand");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result &= args[i] != 0;
            }
            return !result ? 1 : 0;
        }
    }

    public static class LogicalNorGate extends Function {
        public LogicalNorGate() {
            super("lnor");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result |= args[i] != 0;
            }
            return !result ? 1 : 0;
        }
    }

    public static class LogicalXorGate extends Function {
        public LogicalXorGate() {
            super("lxor");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result ^= args[i] != 0;
            }
            return result ? 1 : 0;
        }
    }

    public static class LogicalXnorGate extends Function {
        public LogicalXnorGate() {
            super("lxnor");
        }

        @Override
        public double apply(double... args) {
            boolean result = args[0] != 0;
            for (int i = 1; i < args.length; i++) {
                result ^= args[i] != 0;
            }
            return !result ? 1 : 0;
        }
    }
}
