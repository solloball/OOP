package ru.nsu.romanov.tree;

import static ru.nsu.romanov.tree.IteratorType.BFS;
import static ru.nsu.romanov.tree.IteratorType.DFS;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

/**
 * Class tree.x`
 */
public class Tree<T> implements Iterable<Tree<T>> {
    private final @NotNull List<Tree<T>> child = new ArrayList<>();
    private @NotNull T val;
    private Tree<T> parent = null;
    private boolean modified = false;
    private IteratorType iteratorType = BFS;


    private void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    /**
     * Constructor of class.
     *
     * @param val set val.
     * @param parent set parent.
     */
    private Tree(@NotNull T val, @NotNull Tree<T> parent) {
        this.val = val;
        setParent(parent);
    }

    /**
     * Constructor of class.
     *
     * @param val set val.
     */
    public Tree(@NotNull T val) {
        this.val = val;
    }

    /**
     * Getter of val.
     *
     * @return val
     */
    public @NotNull T getVal() {
        return val;
    }

    /**
     * Setter of value.
     *
     * @param val set val.
     */
    public void setVal(@NotNull T val) {
        this.val = val;
    }

    /**
     * Getter of child.
     *
     * @return list child.
     */
    public @NotNull List<Tree<T>> getChildren() {
        return child;
    }

    /**
     * Added to this Tree new child.
     *
     * @param childVal value of child.
     * @return child.
     */
    public Tree<T> add(@NotNull T childVal) {
        Tree<T> child = new Tree<>(childVal, this);
        this.child.add(child);
        modified = true;
        return child;
    }

    /**
     * Added to this Tree child.
     *
     * @param child subtree we added to this tree.
     */
    public void add(@NotNull Tree<T> child) {
        child.setParent(this);
        modified = true;
        this.child.add(child);
    }

    /**
     * Remove this node and add all child to parent child.
     */
    public void remove() {
        modified = true;
        if (parent != null) {
            parent.child.remove(this);
            parent.child.addAll(this.child);
        }
        parent = null;
    }

    /**
     * Remove child from this root.
     *
     * @param node subtree we need to delete from root.
     * @return true if deleted a node, false if failed to delete a node.
     */
    public boolean removeChild(Tree<T> node) {
        boolean res = child.remove(node);
        modified = res;
        return res;
    }

    /**
     * Remove child from this root.
     *
     * @param node value of subtree we need to delete from root.
     * @return true if deleted a node, false if failed to delete a node.
     */
    public boolean removeChild(T node) {
        boolean res = child.removeIf(tree -> tree.getVal() == node);
        modified = res;
        return res;
    }

    /**
     * Override std equals.
     *
     * @param o Object with which we compare.
     * @return true if o equal to this Tree, false if not equal to Tree.
     */
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

    /**
     * Override std hashcode.
     *
     * @return hash of this Tree.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val.hashCode();

        for (Tree<T> it : child) {
            result = prime * result + it.hashCode();
        }

        return result;
    }

    /**
     * Std Iterator.
     *
     * @return Iterator.
     */
    @Override
    public @NotNull Iterator<Tree<T>> iterator() {
        if (iteratorType == BFS) {
            return new BFSIterator();
        }
        else {
            return new DFSIterator();
        }
    }

    /**
     * Setter for IteratorType.
     *
     * @param iteratorType value we set to this iteratorType.
     */
    public void setIteratorType(@NotNull IteratorType iteratorType) {
        this.iteratorType = iteratorType;
    }

    /**
     * Describing of std iterator DFS.
     */
    private class BFSIterator implements Iterator<Tree<T>> {
        private final Deque<Tree<T>> nodesToVisit = new ArrayDeque<>();

        /**
         * Constructor of DFS iterator.
         */
        private BFSIterator() {
            modified = false;
            nodesToVisit.add(Tree.this);
        }

        /**
         * override std hasNext for BFS iterator.
         *
         * @return true if there is next iterator,  false otherwise.
         */
        @Override
        public boolean hasNext() {
            if (modified) {
                throw new ConcurrentModificationException();
            }
            return !nodesToVisit.isEmpty();
        }

        /**
         * override std next.
         *
         * @return next iterator.
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (modified) {
                throw new ConcurrentModificationException();
            }
            Tree<T> nextNode = nodesToVisit.remove();
            nodesToVisit.addAll(nextNode.child);
            return nextNode;
        }
    }

    /**
     * Describing of std iterator DFS.
     */
    private @NotNull class DFSIterator implements Iterator<Tree<T>> {
        private final Stack<Tree<T>> st = new Stack<>();

        private DFSIterator() {
            modified = false;
            st.push(Tree.this);
        }

        /**
         * override std hasNext for DFS iterator.
         *
         * @return true if there is next iterator,  false otherwise.
         */
        @Override
        public boolean hasNext() {
            if (modified) {
                throw  new ConcurrentModificationException();
            }
            return !st.isEmpty();
        }

        /**
         * override std next.
         *
         * @return next iterator.
         */
        @Override
        public Tree<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (modified) {
                throw new ConcurrentModificationException();
            }
            Tree<T> nextNode = st.pop();
            for (var it: nextNode.child) {
                st.push(it);
            }
            return nextNode;
        }
    }
}