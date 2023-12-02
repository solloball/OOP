package ru.nsu.romanov.calculator;

public enum TypeOperation {
    ADD {
        @Override
        public int getArity() {
            return 2;
        }
    },
    SUB {
        @Override
        public int getArity() {
            return 2;
        }
    },
    MLT {
        @Override
        public int getArity() {
            return 2;
        }
    },
    DIV {
        @Override
        public int getArity() {
            return 2;
        }
    },
    SIN {
        @Override
        public int getArity() {
            return 1;
        }
    },
    COS {
        @Override
        public int getArity() {
            return 1;
        }
    },
    LOG {
        @Override
        public int getArity() {
            return 2;
        }
    },
    POW {
        @Override
        public int getArity() {
            return 2;
        }
    },
    SQRT {
        @Override
        public int getArity() {
            return 1;
        }
    };
    public int getArity() {
        return 0;
    }
}
