package ru.nsu.romanov.polynomial;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TestPolynomial {

    int[] getMadeRandomArr(int size) {
        Random rd = new Random(); // creating Random object.
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = rd.nextInt(); // storing random integers in an array.
        }

        return arr;
    }

    @Test
    void checkConstructorWithArr() {
        int[] arr = getMadeRandomArr(1000000);
        Polynomial p = new Polynomial(arr);
        Assertions.assertArrayEquals(arr, p.getArr());
    }

    @Test
    void checkConstructorWithEmptyArr() {
        Polynomial p = new Polynomial(new int[] {});
        Assertions.assertEquals(0, p.getArr().length);
    }

    @Test
    void checkEqualsWithEqArr() {
        int[] arr = getMadeRandomArr(10000);
        Polynomial p1 = new Polynomial(arr);
        Polynomial p2 = new Polynomial(arr);
        Assertions.assertEquals(p2, p1);
        Assertions.assertEquals(p1, p2);
    }

    @Test
    void checkEqualsForReflexivity() {
        int[] arr = getMadeRandomArr(10000);
        Polynomial p = new Polynomial(arr);
        Assertions.assertEquals(p, p);
    }

    @Test
    void checkEqualsWithDifClass() {
        int[] arr = new int[] {};
        Polynomial p = new Polynomial(arr);
        Object obj = new Object();
        Assertions.assertNotEquals(obj, p);
    }

    @Test
    void checkEqualsWithDifferentPolynomial() {
        int[] arr1 = new int[] {1,2,3,4,5,6};
        int[] arr2 = new int[] {1,2,3,4,5};
        Polynomial p1 = new Polynomial(arr1);
        Polynomial p2 = new Polynomial(arr2);
        Assertions.assertNotEquals(p1, p2);
        Assertions.assertNotEquals(p2, p1);
    }

    @Test
    void checkHashcodeWithEqArr() {
        int[] arr = getMadeRandomArr(10000);
        Polynomial p1 = new Polynomial(arr);
        Polynomial p2 = new Polynomial(arr);
        Assertions.assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void checkHashcodeWithDifferentArr() {
        int[] arr1 = new int[] {1,2,3,4,5,6};
        int[] arr2 = new int[] {1,2,3,4,5};
        Polynomial p1 = new Polynomial(arr1);
        Polynomial p2 = new Polynomial(arr2);
        Assertions.assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void checkFunAddWithArrDifferentLength() {
        Polynomial p1 = new Polynomial(new int[] {7, 4, 5, 7, 8});
        Polynomial p2 = new Polynomial(new int[] {-5, 10, 8});
        Assertions.assertArrayEquals(new int[] {7, 4, 0, 17, 16}, p1.add(p2).getArr());
        Assertions.assertArrayEquals(new int[] {7, 4, 0, 17, 16}, p2.add(p1).getArr());
    }

    @Test
    void checkFunAddWithTwoEmptyArr() {
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = new Polynomial(new int[] {});
        Assertions.assertArrayEquals(new int[] {}, p1.add(p2).getArr());
        Assertions.assertArrayEquals(new int[] {}, p2.add(p1).getArr());
    }

    @Test
    void checkFunAddWithOneEmptyArr() {
        int[] arr = getMadeRandomArr(10000);
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = new Polynomial(arr);
        Assertions.assertArrayEquals(arr, p1.add(p2).getArr());
        Assertions.assertArrayEquals(arr, p2.add(p1).getArr());
    }

    @Test
    void checkFunMltWithArrDifferentLength() {
        Polynomial p1 = new Polynomial(new int[] {3, 5, 4, 5, 7, 8});
        Polynomial p2 = new Polynomial(new int[] {-5, 10, 8});
        Assertions.assertArrayEquals(new int[] {0, 0, 0, -25, 70, 64}, p1.mlt(p2).getArr());
        Assertions.assertArrayEquals(new int[] {0, 0, 0, -25, 70, 64}, p2.mlt(p1).getArr());
    }

    @Test
    void checkFunMltWithTwoEmptyArr() {
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = new Polynomial(new int[] {});
        Assertions.assertArrayEquals(new int[] {}, p1.mlt(p2).getArr());
        Assertions.assertArrayEquals(new int[] {}, p2.mlt(p1).getArr());
    }

    @Test
    void checkFunMltWithOneEmptyArr() {
        int size = 10000;
        int[] arr = getMadeRandomArr(size);
        int[] ZeroArr = new int[size];
        Arrays.fill(ZeroArr, 0);
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = new Polynomial(arr);
        Assertions.assertArrayEquals(ZeroArr, p1.mlt(p2).getArr());
        Assertions.assertArrayEquals(ZeroArr, p2.mlt(p1).getArr());
    }

    @Test
    void checkSubAddWithArrDifferentLength() {
        Polynomial p1 = new Polynomial(new int[] {5, 6, 4, 5, 7, 8});
        Polynomial p2 = new Polynomial(new int[] {-5, 10, 8});
        Assertions.assertArrayEquals(new int[] {5, 6, 4, 10, -3, 0}, p1.sub(p2).getArr());
        Assertions.assertArrayEquals(new int[] {-5, -6, -4, -10, 3, 0}, p2.sub(p1).getArr());
    }

    @Test
    void checkFunSubWithTwoEmptyArr() {
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = new Polynomial(new int[] {});
        Assertions.assertArrayEquals(new int[] {}, p1.mlt(p2).getArr());
        Assertions.assertArrayEquals(new int[] {}, p2.mlt(p1).getArr());
    }

    @Test
    void checkFunSubWithOneEmptyArr() {
        int size = 1000;
        int[] arr = new int[size];
        Polynomial p1 = new Polynomial(arr);
        Polynomial p2 = new Polynomial(new int[] {});
        Assertions.assertArrayEquals(arr, p1.sub(p2).getArr());
        for (int i = 0; i < size; i++) {
            arr[i] = -arr[i];
        }
        Assertions.assertArrayEquals(arr, p2.sub(p1).getArr());
    }

    @Test
    void checkDifferentiateWithDifferentDegree() {
        Polynomial p1 = new Polynomial(new int[] {4, 5, 7, 8});
        Polynomial p2 = new Polynomial(new int[] {12, 10, 7});
        Polynomial p3 = new Polynomial(new int[] {24, 10});
        Assertions.assertEquals(p3, p2.differentiate(1));
        Assertions.assertEquals(p3, p1.differentiate(2));
    }

    @Test
    void checkDifferentiateWithBigDegree() {
        Polynomial p1 = new Polynomial(getMadeRandomArr(100));
        Polynomial p2 = new Polynomial(getMadeRandomArr(10000));
        Polynomial expectedArr = new Polynomial(new int[] {});
        Assertions.assertEquals(expectedArr, p1.differentiate(10000000));
        Assertions.assertEquals(expectedArr, p2.differentiate(10000000));
    }

    @Test
    void checkEvaluate() {
        Polynomial p1 = new Polynomial(new int[] {4, 5, 7, 8});
        Polynomial p2 = new Polynomial(new int[] {0, 0, -7, 10, -25});
        Assertions.assertEquals(4 + 5 + 7 + 8, p1.evaluate(1));
        Assertions.assertEquals(4 * (-8) + 4 * 5 - 14 + 8, p1.evaluate(-2));
        Assertions.assertEquals(- 7 * 9 + 3 * 10 - 25, p2.evaluate(3));
    }

    @Test
    void checkEvaluateWithArrWithZeroCoefficients() {
        int[] arr = getMadeRandomArr(10000);
        Arrays.fill(arr, 0);
        Polynomial p = new Polynomial(arr);
        Assertions.assertEquals(0, p.evaluate(1));
    }

    @Test
    void checkToString() {
        int[] arr = new int[] {4, -5, 0, 6};
        Polynomial p = new Polynomial(arr);
        Assertions.assertEquals("4x^3 - 5x^2 + 6", p.toString());
    }

    @Test
    void checkToStringWithEmptyArr() {
        int[] arr = new int[100];
        Arrays.fill(arr, 0);
        Polynomial p = new Polynomial(arr);
        Assertions.assertEquals("", p.toString());
    }

    @Test
    void checkToStringWithZeroCoefficients() {
        int[] arr = new int[] {0, 0, 0, -5, 4, 0, 3, 0, 0};
        Polynomial p = new Polynomial(arr);
        Assertions.assertEquals(" - 5x^5 + 4x^4 + 3x^2", p.toString());
    }
}
