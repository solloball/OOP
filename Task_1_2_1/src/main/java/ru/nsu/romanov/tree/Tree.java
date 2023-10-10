package ru.nsu.romanov.tree;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Class tree
 */
public class Tree<T> implements Iterable<Tree<T>> {
    private List<Tree<T>> child = new ArrayList<Tree<T>>();
    private T val = null;
    private Tree<T> parent = null;

    private void setParent(Tree<T> parent) {
        if (this == iterator()) {
            throw new ConcurrentModificationException("undefined behaviour with setVal");
        }
        this.parent = parent;
    }

    private Tree(T val, Tree<T> parent) {
        this.val = val;
        setParent(parent);
    }

    public Tree(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        if (this == iterator()) {
            throw new ConcurrentModificationException("undefined behaviour with setVal");
        }
        this.val = val;
    }

    public void add(@NotNull T childVal) {
        if (this == iterator()) {
            throw new ConcurrentModificationException("undefined behaviour with add");
        }
        Tree<T> child = new Tree<>(childVal, this);
        this.child.add(child);
    }

    public void add(@NotNull Tree<T> child) {
        child.setParent(this);
        this.child.add(child);
    }

    public void remove() {
        if (parent != null) {
            parent.child.remove(this);
            parent.child.addAll(this.child);
        }
        child = null;
        parent = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tree<?> tree = (Tree<?>) o;
        return Objects.equals(child, tree.child) && Objects.equals(val, tree.val);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val.hashCode();

        for (Tree<T> tTree : child) {
            result = prime * result + tTree.hashCode();
        }

        return result;
    }

    @Override
    public @NotNull Iterator<Tree<T>> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<Tree<T>> {
        private final LinkedList<Tree<T>> nodesToVisit = new LinkedList<>();

        private TreeIterator() {
            nodesToVisit.add(Tree.this);
        }

        @Override
        public boolean hasNext() {
            return !nodesToVisit.isEmpty();
        }

        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> nextNode = nodesToVisit.remove();
            nodesToVisit.addAll(nextNode.child);
            return nextNode;
        }

        @Override
        public void remove() {
            throw new ConcurrentModificationException("undefined behavior with remove iterator");
        }
    }
}