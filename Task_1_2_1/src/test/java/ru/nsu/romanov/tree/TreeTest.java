package ru.nsu.romanov.tree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.romanov.tree.IteratorType.BFS;
import static ru.nsu.romanov.tree.IteratorType.DFS;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Set;

import org.junit.jupiter.api.Test;


class TreeTest {

    @Test
    void checkConstructor() {
        Tree<String> tree = new Tree<>("hello");
        assertEquals("hello", tree.getVal());
        Tree<Integer> tree2 = new Tree<>(3);
        assertEquals(3, tree2.getVal());
    }

    @Test
    void checkSetter() {
        Tree<String> tree = new Tree<>("hello");
        assertEquals("hello", tree.getVal());
        tree.setVal("goodbye");
        assertEquals("goodbye", tree.getVal());
    }

    @Test
    void checkAdd() {
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> child1 = root.add(1);
        Tree<Integer> child2 = new Tree<>(3);
        root.add(child2);
        Integer[] arr = new Integer[3];
        int idx = 0;
        for (var it : root) {
            arr[idx++] = it.getVal();
        }
        assertArrayEquals(new Integer[] {0, 1, 3}, arr);
    }

    @Test
    void checkEqualsReflexivity() {
        final int size = 10000;
        Tree<Integer> root = new Tree<>(0);
        Random rd = new Random();
        for (int i = 0; i < size; i++) {
            root.add(rd.nextInt());
        }
        assertEquals(root, root);
        assertEquals(root.hashCode(), root.hashCode());
    }

    @Test
    void checkEqualsSymmetry() {
        final int size = 10000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(0);
        for (int i = 0; i < size; i++) {
            int val = rd.nextInt();
            root.add(val);
            anotherRoot.add(val);
        }
        assertEquals(root, anotherRoot);
        assertEquals(anotherRoot, root);
        assertEquals(root.hashCode(), anotherRoot.hashCode());
    }

    @Test
    void checkEqualsTransitivity() {
        final int size = 10000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(0);
        Tree<Integer> thirdRoot = new Tree<>(0);
        for (int i = 0; i < size; i++) {
            int val = rd.nextInt();
            root.add(val);
            anotherRoot.add(val);
            thirdRoot.add(val);
        }
        assertEquals(root, anotherRoot);
        assertEquals(anotherRoot, thirdRoot);
        assertEquals(root, thirdRoot);
        assertEquals(root.hashCode(), thirdRoot.hashCode());
    }

    @Test
    void checkEqualsWithDepthTrees() {
        int depth = 10;
        int size = 1000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(0);
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < size; j++) {
                int val = rd.nextInt();
                root.add(val);
                anotherRoot.add(val);
            }
        }
        assertEquals(root, anotherRoot);
        assertEquals(root.hashCode(), anotherRoot.hashCode());
    }

    @Test
    void checkEqualsWithTreeWithDifferentRoot() {
        final int size = 10000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(1);
        for (int i = 0; i < size; i++) {
            int val = rd.nextInt();
            root.add(val);
            anotherRoot.add(val);
        }
        assertNotEquals(root, anotherRoot);
        assertNotEquals(root.hashCode(), anotherRoot.hashCode());
    }

    @Test
    void checkEqualsWithDifferentLeaf() {
        int depth = 10;
        int size = 1000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(0);
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < size; j++) {
                int val = rd.nextInt();
                if (i == 9 && j == 950) {
                    root.add(val);
                    anotherRoot.add(2 * val);
                }
                root.add(val);
                anotherRoot.add(val);
            }
        }
        assertNotEquals(root, anotherRoot);
        assertNotEquals(root.hashCode(), anotherRoot.hashCode());
    }

    @Test
    void checkEqualsWithDifferentInternalNode() {
        int depth = 10;
        int size = 1000;
        Random rd = new Random();
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> anotherRoot = new Tree<>(0);
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < size; j++) {
                int val = rd.nextInt();
                if (i == 4 && j == 435) {
                    root.add(val);
                    anotherRoot.add(2 * val);
                }
                root.add(val);
                anotherRoot.add(val);
            }
        }
        assertNotEquals(root, anotherRoot);
        assertNotEquals(root.hashCode(), anotherRoot.hashCode());
    }

    @Test
    void checkEqualsWithTreeDifferentDepth() {
        Tree<String> parent1 = new Tree<>("A");
        Tree<String> parent2 = new Tree<>("A");
        Tree<String> child1 = new Tree<>("B");
        Tree<String> child11 = new Tree<>("C");
        parent1.add(child1);
        child1.add(child11);
        assertNotEquals(parent1.hashCode(), parent2.hashCode());
        assertNotEquals(parent1, parent2);
    }

    @Test
    void checkEqualsWithSingleNode() {
        Tree<String> tree1 = new Tree<>("A");
        Tree<String> parent = new Tree<>("Par");
        parent.add(tree1);
        Tree<String> tree2 = new Tree<>("A");
        assertEquals(tree2.hashCode(), tree1.hashCode());
        assertEquals(tree2, tree1);
    }

    @Test
    void checkEqualsWithNull() {
        Tree<String> tree = new Tree<>("A");
        assertNotEquals(tree, null);
    }

    @Test
    void checkEqualsWithDifferentOrder() {
        int size = 100;
        Tree<Integer> root1 = new Tree<>(0);
        Tree<Integer> root2 = new Tree<>(0);
        for (int i = 0; i < size; i++) {
            root1.add(i);
            root2.add(size - i - 1);
        }
        assertEquals(root1, root2);
    }

    @Test
    void checkEqualsWithDifferentStructure() {
        Tree<Integer> root1 = new Tree<>(0);
        Tree<Integer> root2 = new Tree<>(0);
        root1.add(1);
        root2.add(1);
        Tree<Integer> child2 = root2.add(2);
        Tree<Integer> child1 = root1.add(2);
        child2.add(0);
        child1.add(0);
        assertEquals(child1, child2);
    }

    @Test
    void checkRemoveNonexistentChild() {
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = parent.add("3");
        boolean res  = parent.removeChild("5");
        assertFalse(res);
    }

    @Test
    void checkRemoveNonexistentChildArgAsTree() {
        Tree<String> parent = new Tree<>("1");
        Tree<String> anotherChild = new Tree<>("1");
        boolean res  = parent.removeChild(anotherChild);
        assertFalse(res);
    }

    @Test
    void checkBasicRemove() {
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = parent.add("3");
        boolean res  = parent.removeChild("3");
        Set<Tree<String>> set = parent.getChildren();
        assertEquals(0, set.size());
        assertTrue(res);
    }

    @Test
    void checkRemoveChild() {
        Tree<Integer> root = new Tree<>(0);
        Tree<Integer> child = root.add(1);
        Set<Tree<Integer>> expectedSet = new HashSet<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            child.add(i);
            expectedSet.add(new Tree<Integer>(i));
        }
        assertEquals(1, root.getChildren().size());
        child.remove();
        var set = root.getChildren();
        assertEquals(expectedSet.size(), set.size());
    }

    @Test
    void checkBasicRemoveWithTreeArgument() {
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = parent.add("3");
        boolean res  = parent.removeChild(child);
        Set<Tree<String>> list = parent.getChildren();
        assertEquals(0, list.size());
        assertTrue(res);
    }

    @Test
    void checkGetChildren() {
        Tree<Integer> root = new Tree<>(0);
        Set<Tree<Integer>> expectedSet = new HashSet<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            root.add(i);
            expectedSet.add(new Tree<Integer>(i));
        }
        Set<Tree<Integer>> set = root.getChildren();
        assertEquals(expectedSet, set);
    }

    @Test
    void checkBasicBfsIteration() {
        Tree<Integer> parent = new Tree<>(1);
        parent.setIteratorType(BFS);
        var it = parent.iterator();
        assertEquals(1, it.next().getVal());
    }

    @Test
    void checkBasicDfsIteration() {
        Tree<Integer> parent = new Tree<>(1);
        parent.setIteratorType(DFS);
        var it = parent.iterator();
        assertEquals(1, it.next().getVal());
    }

    @Test
    void checkBfsIteration() {
        String[] arr = new String[4];
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = new Tree<>("3");
        parent.add("2");
        parent.add(child);
        child.add("4");
        parent.setIteratorType(BFS);

        int idx = 0;
        for (var it : parent) {
            arr[idx++] = it.getVal();
        }
        assertArrayEquals(new String[] {"1", "2", "3", "4"}, arr);
    }

    @Test
    void checkBfsIteratorWithAdd_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(BFS);
        assertThrows(
            ConcurrentModificationException.class,
            () -> {
                for (var it : parent) {
                    it.add("newChild");
                }
            }
        );
    }

    @Test
    void checkBfsIteratorWithRemove_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(BFS);
        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (var it : parent) {
                        it.remove();
                    }
                }
        );
    }

    @Test
    void checkDfsIteration() {
        String[] arr = new String[4];
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = new Tree<>("3");
        parent.add("2");
        parent.add(child);
        child.add("4");
        parent.setIteratorType(DFS);

        int idx = 0;
        for (var it : parent) {
            arr[idx++] = it.getVal();
        }
        assertArrayEquals(new String[] {"1", "3", "4", "2"}, arr);
    }

    @Test
    void checkDfsIteratorWithAdd_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(DFS);
        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (var it : parent) {
                        it.add("newChild");
                    }
                }
        );
    }

    @Test
    void checkDfsIteratorWithRemove_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(DFS);
        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (var it : parent) {
                        it.remove();
                    }
                }
        );
    }

    @Test
    void checkSubIteratorDfs() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(DFS);
        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (var it : parent) {
                        it.add("hello");
                        it.iterator();
                    }
                }
        );
    }

    @Test
    void checkSubIteratorBfs() {
        Tree<String> parent = new Tree<>("parent");
        parent.add("child1");
        parent.add("child2");
        parent.setIteratorType(BFS);
        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (var it : parent) {
                        it.add("hello");
                        it.iterator();
                    }
                }
        );
    }

    @Test
    void checkDfsEmptyIterator_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.setIteratorType(DFS);
        assertThrows(
                NoSuchElementException.class,
                () -> {
                    var it = parent.iterator();
                    it.next();
                    it.next();
                }
        );
    }

    @Test
    void checkBfsEmptyIterator_shouldThrowException() {
        Tree<String> parent = new Tree<>("parent");
        parent.setIteratorType(BFS);
        assertThrows(
                NoSuchElementException.class,
                () -> {
                    var it = parent.iterator();
                    it.next();
                    it.next();
                }
        );
    }
}