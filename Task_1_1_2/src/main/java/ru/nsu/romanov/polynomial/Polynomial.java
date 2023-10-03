package ru.nsu.romanov.polynomial;

import static ru.nsu.romanov.polynomial.OperationType.MULT;
import static ru.nsu.romanov.polynomial.OperationType.ADD;
import static ru.nsu.romanov.polynomial.OperationType.SUBTRACT;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * Class Polynomial which store arr of arrOfCoefficients and provide some methods.
 */
public class Polynomial {

    /**
     * array which store all node in order of value of degree.
     * For example, arr = {4, -5, 0, 7} means that polynomial looks like "4x^3 - 5x^2 + 7".
     */
    private int[] arr;

    /**
     * constructor which make arr with arrOfCoefficients length
     * and set arrOfCoefficients to arr.
     */
    public Polynomial(int @NotNull [] arrOfCoefficients) {
        arr = new int[arrOfCoefficients.length];

        setArr(arrOfCoefficients);
    }

    /**
     * setter which set new arr.
     */
    public void setArr(int @NotNull [] arrOfCoefficients) {
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
     * auxiliary function for performing operations on polynomials,
     * in this method we assume that n1 >= n2.
     */
    private Polynomial operationWithPolynomial(Polynomial polynomial, OperationType operationType) {

        Polynomial newPolynomial = new Polynomial(arr);
        int n1 = arr.length;
        int n2 = polynomial.arr.length;
        int i;
        int j;

        for (j = n2 - 1, i = n1 - 1; (j >= 0) && (i >= 0); i--, j--) {
            switch (operationType) {
                case ADD:
                    newPolynomial.arr[i] += polynomial.arr[j];
                    break;
                case MULT:
                    newPolynomial.arr[i] *= polynomial.arr[j];
                    break;
                case SUBTRACT:
                    newPolynomial.arr[i] -= polynomial.arr[j];
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operationType);
            }
        }

        if (operationType == MULT) {
            for ( ; i >= 0; i--) {
                newPolynomial.arr[i] = 0;
            }
        }

        return newPolynomial;
    }

    /**
     * add two polynomial, store value to newPolynomial and return it.
     */
    public Polynomial add(Polynomial polynomial) {
        if (polynomial == null) {
            return null;
        }
        if (arr.length < polynomial.arr.length) {
            return polynomial.add(this);
        }
        return operationWithPolynomial(polynomial, ADD);
    }

    /**
     * subtract from this element polynomial, store value to newPolynomial and return it.
     */
    public Polynomial sub(Polynomial polynomial) {
        if (polynomial == null) {
            return null;
        }
        if (arr.length < polynomial.arr.length) {
            Polynomial p = polynomial.sub(this);
            int[] newArr = Arrays.stream(p.getArr()).map(i -> -i).toArray();
            p.setArr(newArr);
            return p;
        }
        return operationWithPolynomial(polynomial, SUBTRACT);
    }

    /**
     * mlt two polynomial, store value to newPolynomial and return it.
     */
    public Polynomial mlt(Polynomial polynomial) {
        if (polynomial == null) {
            return null;
        }
        if (arr.length < polynomial.arr.length) {
            return polynomial.mlt(this);
        }

        return operationWithPolynomial(polynomial, MULT);
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

        for (int i = arr.length - 1; i >= 0; i--) {
            res += value * arr[i];
            value *= point;
        }

        return res;
    }

    @Override
    public int hashCode() {
        return  Arrays.hashCode(this.arr);
    }

    /**
     * return true if two Polynomial are equal and return false otherwise.
     */
    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Polynomial otherPol = (Polynomial) other;

        return Arrays.equals(this.arr, otherPol.arr);
    }

    /**
     * convert Polynomial to String. String looks like
     * "a1x^n (+/-) a2x^(n-1) (+/-) ... (+/-) a(m-1)x (+/-) am".
     */
    @Override
    public String toString() {
        int degree = arr.length - 1;
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < arr.length; i++, degree--) {

            // program doesn't write coefficients = 0.
            if (arr[i] == 0) {
                continue;
            }

            if (arr[i] < 0) {
                res.append(" - ");
            } else if (i != 0) {  // program doesn't write "+" ahead of polynomial.
                res.append(" + ");
            }

            res.append(Math.abs(arr[i]));
            switch (degree) {
                case 0:
                    break;
                case 1:
                    res.append("x");
                    break;
                default:
                    res.append("x^").append(degree);
                    break;
            }
        }

        return res.toString();
    }
}

