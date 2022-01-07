package BinaryTree;

/**
 * Last edited 1/5/2021 <br>
 * A collection of static procedures commonly used on binary search trees. <br>
 * The syntax before each method is to allow for generic typing. This allows
 * the methods to run on nodes that use any kind of comparable object.
 * @author Benjamin Friedman
 */
public class SearchTreeProcedures {

    /**
     * Finds a node matching a given key. Sets the data field of the node to its depth
     * @param root root node
     * @param key key to search for
     * @param <T> node key type
     * @return a Node matching the key or null if none exists
     */
    public static <T extends Comparable<T>> Node<T> search(Node<T> root, T key) {
        int depth = 0;
        Node<T> node = null;
        Node<T> next = root;
        while (next != null) {
            node = next;
            if (key.compareTo(node.getKey()) < 0) {
                next = node.getLeft();
            } else if (key.compareTo(node.getKey()) > 0) {
                next = node.getRight();
            } else {
                break;
            }
            depth++;
        }
        if (node != null && node.getKey() == key) {
            node.setData(depth);
            return node;
        } else {
            return null;
        }
    }

    /**
     * Finds the minimum valued Node in the tree
     * @param root root node
     * @return minimum valued node
     */
    public static <T> Node<T> minimum(Node<T> root) {
        Node<T> min = root;
        while (min.getLeft() != null) {
            min = min.getLeft();
        }
        return min;
    }

    /**
     * Finds the maximum valued Node in the tree
     * @param root root node
     * @param <T> node key type
     * @return maximum valued node
     */
    public static <T> Node<T> maximum(Node<T> root) {
        Node<T> max = root;
        while (max.getRight() != null) {
            max = max.getRight();
        }
        return max;
    }

    /**
     * Finds the predecessor of a node
     * @param node node to be operated on
     * @param <T> node key type
     * @return predecessor of the node or null if none exists
     */
    public static <T> Node<T> predecessor(Node<T> node) {
        if (node.getLeft() != null) {
            return maximum(node.getLeft());
        }
        Node<T> predecessor = node.getParent();
        Node<T> child = node;
        while (predecessor != null && child == predecessor.getLeft()) {
            child = predecessor;
            predecessor = child.getParent();
        }
        return predecessor;
    }

    /**
     * Finds the successor of a node
     * @param node node to be operated on
     * @param <T> node key type
     * @return successor of the node or null if none exists
     */
    public static <T> Node<T> successor(Node<T> node) {
        if (node.getRight() != null) {
            return minimum(node.getRight());
        }
        Node<T> successor = node.getParent();
        Node<T> child = node;
        while (successor != null && child == successor.getRight()) {
            child = successor;
            successor = child.getParent();
        }
        return successor;
    }

    /**
     * Inserts a key into the tree
     * @param root root node, should not be null
     * @param key key to insert
     * @param <T> node key type
     * @return added node or null if the key is a duplicate
     */
    public static <T extends Comparable<T>> Node<T> insert(Node<T> root, T key) {
        // using an assertion so that the condition is more explicit than letting the method throw an exception later
        assert root != null;
        // find the parent node to insert on
        Node<T> node = null;
        Node<T> next = root;
        while (next != null) {
            node = next;
            if (key.compareTo(node.getKey()) < 0) {
                next = node.getLeft();
            } else if (key.compareTo(node.getKey()) > 0) {
                next = node.getRight();
            } else {
                return null;
            }
        }
        // insert
        Node<T> insert = new Node<>(key, node, null, null);
        if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insert);
        } else {
            node.setRight(insert);
        }
        return insert;
    }

    /**
     * Delete a node, while preserving binary search properties.
     * @param node node to be removed
     * @param <T> node key type
     * @return whether the node was deleted, a false return means the node has no parent
     * in which case the method can't do anything to dereference it
     */
    public static <T> boolean delete(Node<T> node) {
        if (node.getLeft() == null) { // replace node with right child or delete node entirely
            if (node.getRight() != null) {
                node.copyData(node.getRight(), false);
            } else if (node.getParent() != null) { // if the child has no children we delete it
                if (node == node.getParent().getLeft()) {
                    node.getParent().setLeft(null);
                } else {
                    node.getParent().setRight(null);
                }
            } else { // if it has no parent then we can't properly handle it
                return false;
            }
        } else if (node.getRight() == null) { // replace node with left child
            node.copyData(node.getLeft(), false);
        } else {
            // if the node has two children, we take the successor and replace it with its single child,
            // and then replace the node with the successor's key
            Node<T> successor = successor(node);
            node.setKey(successor.getKey());
            if (successor.getRight() != null) {
                successor.copyData(successor.getRight(), false);
            } else {
                // delete successor from its parent
                Node<T> parent = successor.getParent();
                if (parent.getLeft() == successor) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            }
        }
        return true;
    }
}
