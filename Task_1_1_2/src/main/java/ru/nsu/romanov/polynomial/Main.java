package ru.nsu.romanov.polynomial;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Main class in task 1_2_1.
 */
public class Main {

        /**
         * Class polynomial is a1*x^n1 + a2*x^n2 + ... + am*x^nt.
         */
        public static class Polynomial {

            /**
             * array which store all node in order of value of degree.
             * For example, arr = {4, -5, 0, 7} means that polynomial looks like "4x^3 - 5x^2 + 7".
             */
            private int[] arr;

            /**
             * constructor which make arr with arrOfCoefficients.length and set arrOfCoefficients to arr.
             */
            public Polynomial(int[] arrOfCoefficients) {
                arr = new int[arrOfCoefficients.length];

                setArr(arrOfCoefficients);
            }

            /**
             * setter which set new arr.
             */
            public void setArr(int[] arrOfCoefficients) {
                this.arr = new int[arrOfCoefficients.length];
                System.arraycopy(arrOfCoefficients, 0, arr, 0, arrOfCoefficients.length);
            }

            /**
             * getter which return arr.
             */
            public int[] getArr() {
                return arr;
            }

            /**
             * add two polynomial, store value to newPolynomial and return it.
             */
            public Polynomial add(Polynomial polynomial) {
                int n1 = arr.length;
                int n2 = polynomial.arr.length;

                if (n1 < n2) {
                    return polynomial.add(this);
                }

                Polynomial newPolynomial = new Polynomial(arr);

                for (int j = n2 - 1, i = n1 - 1; i >= 0 && j >= 0; i--, j--) {
                    newPolynomial.arr[i] += polynomial.arr[j];
                }

                return newPolynomial;
            }

            /**
             * subtract from this element polynomial, store value to newPolynomial and return it.
             */
            public Polynomial sub(Polynomial polynomial) {
                int n1 = arr.length;
                int n2 = polynomial.arr.length;

                if (n1 < n2) {
                    Polynomial p = polynomial.sub(this);
                    for (int i = 0; i < p.arr.length; i++) p.arr[i] *= -1;
                    return p;
                }

                Polynomial newPolynomial = new Polynomial(arr);

                for (int j = n2 - 1, i = n1 - 1; i >= 0 && j >= 0; i--, j--) {
                    newPolynomial.arr[i] -= polynomial.arr[j];
                }

                return newPolynomial;
            }

            /**
             * mlt two polynomial, store value to newPolynomial and return it.
             */
            public Polynomial mlt(Polynomial polynomial) {
                int n1 = arr.length;
                int n2 = polynomial.arr.length;

                if (n1 < n2) {
                    return polynomial.add(this);
                }

                Polynomial newPolynomial = new Polynomial(arr);

                for (int j = n2 - 1, i = n1 - 1; i >= 0 && j >= 0; i--, j--) {
                    newPolynomial.arr[i] *= polynomial.arr[j];
                }

                return newPolynomial;
            }

            /**
             * calculate derivatives of this element and return it.
             */
            public Polynomial differentiate(int degree) {
                if (degree < 0) {
                    return null;
                }

                // newLength become  arr.length - degree because of derivative property.
                int newLength = arr.length - degree;
                if (newLength < 0) {
                    return new Polynomial(new int[] {});
                }
                int[] newArr = new int[newLength];
                // greatest degree is arr.length - 1.
                int curDegree = arr.length - 1;
                System.arraycopy(arr, 0, newArr, 0, newLength);

                for (int i = 0; i < degree; i++) {
                    for (int j = 0; j < newLength; j++) {
                        newArr[j] *= (curDegree - j);
                    }
                    curDegree--;
                }

                return new Polynomial(newArr);
            }

            /**
             * calculate value Polynomial in point and return it.
             */
            public int evaluate(int point) {
                // "value" is value of x^n. in start of fun it has value = 1;
                int value = 1;
                int res = 0;

                for(int i = arr.length - 1; i >= 0; i--) {
                    res += value * arr[i];
                    value *= point;
                }

                return res;
            }

            /**
             * return true if two Polynomial are equal and return false otherwise.
             */
            public boolean isEqual(Polynomial polynomial) {
                int i, j;
                for (i = arr.length - 1, j = polynomial.arr.length - 1; i >= 0 && j >= 0; i--, j--) {
                    if (arr[i] != polynomial.arr[j]) {
                        return false;
                    }
                }
                while (i >= 0) {
                    if (arr[i--] != 0) {
                        return false;
                    }
                }
                while (j >= 0) {
                    if (polynomial.arr[j--] != 0) {
                        return false;
                    }
                }

                return true;
            }

            /**
             * convert Polynomial to String. String looks like "a1x^n (+/-) a2x^(n-1) (+/-) ... (+/-) a(m-1)x (+/-) am".
             */
            @Override
            public String toString() {
                int degree = arr.length - 1;
                StringBuilder res = new StringBuilder();

                for(int i = 0; i < arr.length; i++, degree--) {

                    // program doesn't write coefficients = 0.
                    if (arr[i] == 0) {
                        continue;
                    }

                    if (arr[i] < 0) {
                        res.append(" - ");
                    }
                    else if (i != 0) {  // program doesn't write "+" ahead of polynomial.
                        res.append(" + ");
                    }
                    res.append(Math.abs(arr[i]));
                    switch (degree) {
                        case 0 -> {
                        }
                        case 1 -> res.append("x");
                        default -> res.append("x^").append(degree);
                    }
                }

                return res.toString();
            }
        }
    public static void main(String[] args) {}
}