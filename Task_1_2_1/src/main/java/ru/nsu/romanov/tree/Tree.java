package ru.nsu.romanov.tree;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Class tree
 */
public class Tree<T> implements Iterable<Tree<T>> {
    private final LinkedList<Tree<T>> child = new LinkedList<Tree<T>>();
    private T val = null;
    private Tree<T> parent = null;

    private void setParent(Tree<T> parent) {
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
        this.val = val;
    }

    public void add(@NotNull T childVal) {
        Tree<T> child = new Tree<>(childVal, this);
        this.child.addFirst(child);
    }

    public void add(@NotNull Tree<T> child) {
        child.setParent(this);
        this.child.addFirst(child);
    }

    public void remove() {
        if (parent != null) {
            parent.child.remove(this);
        }
        removeChild();
    }

    private void removeChild() {
        for (var it : child) {
            it.remove();
        }
        parent = null;
        child.clear();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Tree<T> tree = (Tree<T>) other;

        if (!val.equals(tree.val)) {
            return  false;
        }

        if (child.size() != tree.child.size()) {
            return false;
        }

        var it1 = child.listIterator();
        var it2 = tree.child.listIterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!it1.next().equals(it2.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val.hashCode();

        for (var it = child.listIterator(); it.hasNext(); ) {
            result = prime * result + it.next().hashCode();
        }

        return result;
    }

    @Override
    public Iterator<Tree<T>> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<Tree<T>> {
        private final LinkedList<Tree<T>> nodesToVisit = new LinkedList<>();

        private TreeIterator() {
            if (!hasNext()) {
                nodesToVisit.addFirst(Tree.this);
            }
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
    }
}