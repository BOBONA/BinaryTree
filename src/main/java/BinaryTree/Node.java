package BinaryTree;

/**
 * Last edited 12/22/2021 <br>
 * A node in a binary tree contains a key, a reference to its parent node,
 * a left child node, and a right child node.
 * This class comes with public getters and protected setters for all fields.
 * There's also an additional data field for operations to give additional data.
 * @author Benjamin Friedman
 * @param <T> the type of the key
 */
public class Node<T> {
    
    private T key;
    private int data;
    private Node<T> parent;
    private Node<T> left;
    private Node<T> right;

    public Node(T key, Node<T> parent, Node<T> left, Node<T> right) {
        this.key = key;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public T getKey() {
        return key;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    protected void setKey(T key) {
        this.key = key;
    }

    protected void setParent(Node<T> parent) {
        this.parent = parent;
    }

    protected void setLeft(Node<T> left) {
        this.left = left;
    }

    protected void setRight(Node<T> right) {
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    /**
     * Copies a nodes data to this node.
     * @param node node to copy from
     * @param copyParent whether the parent field should be copied
     */
    public void copyData(Node<T> node, boolean copyParent) {
        key = node.getKey();
        if (copyParent) {
            parent = node.getParent();
        }
        left = node.getLeft();
        right = node.getRight();
    }

    /** @return a string representation of the key */
    @Override
    public String toString() {
        return key.toString();
    }
}
