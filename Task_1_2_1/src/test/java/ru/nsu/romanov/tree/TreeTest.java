package ru.nsu.romanov.tree;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

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
        assertEquals("hello", tree.getVal());;
        tree.setVal("goodbye");
        assertEquals("goodbye", tree.getVal());
    }

    @Test
    void checkEqualsReflexivity() {
        String[] arr = new String[] {};
        Tree<String> tree1 = new Tree<>("A");
        assertEquals(tree1, tree1);
        assertEquals(tree1.hashCode(),tree1.hashCode());
    }

    @Test
    void checkEqualsWithTreeWithChild() {
        Tree<String> parent1 = new Tree<>("A");
        Tree<String> parent2 = new Tree<>("A");
        Tree<String> child1 = new Tree<>("B");
        Tree<String> child2 = new Tree<>("B");
        Tree<String> child11 = new Tree<>("C");
        Tree<String> child22 = new Tree<>("C");
        parent1.add(child1);
        parent2.add(child2);
        child1.add(child11);
        child2.add(child22);
        assertEquals(parent1.hashCode(), parent2.hashCode());
        assertEquals(parent1, parent2);
    }

    @Test
    void checkEqualsWithTreeWithDifChild() {
        Tree<String> parent1 = new Tree<>("A");
        Tree<String> parent2 = new Tree<>("A");
        Tree<String> child1 = new Tree<>("B");
        Tree<String> child2 = new Tree<>("B");
        Tree<String> child11 = new Tree<>("C");
        Tree<String> child22 = new Tree<>("C");
        parent1.add(child1);
        parent2.add(child2);
        child1.add(child11);
        parent2.add(child22);
        assertNotEquals(parent1.hashCode(), parent2.hashCode());
        assertNotEquals(parent1, parent2);
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
    void checkRemove() {
        Tree<String> parent1 = new Tree<>("A");
        Tree<String> parent2 = new Tree<>("A");
        Tree<String> child1 = new Tree<>("B");
        Tree<String> child2 = new Tree<>("B");
        Tree<String> child11 = new Tree<>("C");
        Tree<String> child22 = new Tree<>("C");
        parent1.add(child1);
        parent2.add(child2);
        child1.add(child11);
        child2.add(child22);
        assertEquals(parent1, parent2);
        child1.remove();
        assertNotEquals(parent1, parent2);
    }

    @Test
    void checkRemoveToEqTree() {
        Tree<String> parent1 = new Tree<>("A");
        Tree<String> parent2 = new Tree<>("A");
        Tree<String> child1 = new Tree<>("B");
        Tree<String> child11 = new Tree<>("C");
        Tree<String> child12 = new Tree<>("D");
        Tree<String> child21 = new Tree<>("C");
        Tree<String> child22 = new Tree<>("D");
        parent1.add(child1);
        child1.add(child11);
        child1.add(child12);
        parent2.add(child21);
        parent2.add(child22);
        assertNotEquals(parent1, parent2);
        child1.remove();
        assertEquals(parent1,parent2);
    }

    @Test
    void checkIteration() {
        String[] arr = new String[4];
        Tree<String> parent = new Tree<>("1");
        Tree<String> child = new Tree<>("3");
        parent.add("2");
        parent.add(child);
        child.add("4");

        int idx = 0;
        for (var it : parent) {
            arr[idx++] = it.getVal();
        }
        assertArrayEquals(new String[] {"1", "2", "3", "4"}, arr);
    }
}