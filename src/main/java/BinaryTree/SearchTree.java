package BinaryTree;

import java.util.List;

/**
 * Last edited 1/5/2021 <br>
 * This class represents a binary search tree and allows easy, safe use of standard procedures.
 * Data is stored through associated nodes, all connected to the root node.
 * The binary search tree condition states that the key is more than the
 * left child's key and less than the right child's key. This constraint allows operations to run quickly.
 * NOTE: There is no validation that a node actually belongs to the tree.
 * Supports search, minimum, maximum, predecessor, successor, insert, delete.
 * @author Benjamin Friedman
 * @param <T> the key type of the tree's nodes
 */
public class SearchTree<T extends Comparable<T>> {

    private Node<T> root;

    /**
     * Finds a node matching a given key. Set the data field of the node to its depth.
     * @param key key to search for
     * @return a node matching the key or null if none exists
     */
    public Node<T> search(T key) {
        return SearchTreeProcedures.search(root, key);
    }

    /**
     * Finds the minimum node of the tree.
     * Throws a runtime exception if the tree is empty.
     * @return minimum node
     */
    public Node<T> minimum() {
        if (root == null) {
            throw new RuntimeException("Get minimum failed: tree is empty");
        }
        return SearchTreeProcedures.minimum(root);
    }

    /**
     * Finds the maximum node of the tree.
     * Throws a runtime exception if the tree is empty.
     * @return maximum node
     */
    public Node<T> maximum() {
        if (root == null) {
            throw new RuntimeException("Get maximum failed: tree is empty");
        }
        return SearchTreeProcedures.maximum(root);
    }

    /**
     * Finds the predecessor of a node.
     * Throws a runtime exception if the node is null.
     * @param node node to be operated on
     * @return predecessor of the node or null if none exists
     */
    public Node<T> predecessor(Node<T> node) {
        if (node == null) {
            throw new RuntimeException("Get predecessor failed: node is null");
        }
        return SearchTreeProcedures.predecessor(node);
    }

    /**
     * Finds the successor of a node.
     * Throws a runtime exception if the node is null.
     * @param node node to be operated on
     * @return successor of the node or null if none exists
     */
    public Node<T> successor(Node<T> node) {
        if (node == null) {
            throw new RuntimeException("Get successor failed: node is null");
        }
        return SearchTreeProcedures.successor(node);
    }

    /**
     * Inserts a key into the tree.
     * Throws a runtime exception if the key already exists.
     * @param key the key to be added
     * @return added node
     */
    public Node<T> insert(T key) {
        Node<T> node;
        if (root == null) {
            node = new Node<>(key, null, null, null);
            root = node;
        } else {
            node = SearchTreeProcedures.insert(root, key);
            if (node == null) { // this can only happen if the key already existed
                throw new RuntimeException("Insert failed: element already present in tree");
            }
        }
        return node;
    }

    /**
     * Deletes a node from the tree.
     * Throws a runtime exception if the node is null / does not exist.
     * @param node node to be deleted
     */
    public void delete(Node<T> node) {
        if (node == null) {
            throw new RuntimeException("Delete failed: element not present in tree");
        }
        if (!SearchTreeProcedures.delete(node)) { // something failed, see if it's the root node
            if (node == root) {
                root = null;
            }
        }
    }

    /** @return the tree's root */
    public Node<T> root() {
        return root;
    }

    /** @return the size of the tree */
    public int size() {
        return GeneralProcedures.size(root);
    }

    /** @return the depth of the tree */
    public int height() {
        return GeneralProcedures.height(root);
    }

    /** @return a sorted list of the elements in the tree */
    public List<T> sorted() {
        return GeneralProcedures.inorder(root);
    }

    /**
     * @return a visual string representation of the tree, won't work well on large trees
     */
    @Override
    public String toString() {
        return GeneralProcedures.toString(root, 4);
    }
}
