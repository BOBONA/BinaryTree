package BinaryTree;

import java.util.*;

/**
 * Last edited 1/5/2022 <br>
 * A collection of static procedures commonly used on general binary trees.
 * These are separate from the search tree procedures for clarity.<br>
 * The syntax before each method is to allow for generic typing. This allows
 * the methods to run on nodes that use any kind of comparable object.
 * @author Benjamin Friedman
 */
public class GeneralProcedures {

    private static final int SPACE_WIDTH = 1;

    /**
     * Creates a string visual representation of a node.
     * Note that the size of the string will grow exponentially with the height of the node,
     * so this works best for smallish balanced trees.
     * Also spacing works best for monospace fonts.
     * @param root root node
     * @param numLevels number of levels to print
     * @param <T> node key type
     * @return a visual representation of the root
     */
    public static <T> String toString(Node<T> root, int numLevels) {
        StringBuilder output = new StringBuilder();
        // a map is used here so that indexes can be assigned to elements without needing memory for blank spaces
        Map<Integer, String> elements = new HashMap<>();
        populateElementMap(elements, root, 1);
        int maxWidth = elements.values().stream().mapToInt(String::length).max().orElse(0);
        for (int line = 0; line < Math.min(numLevels, height(root)); line++) {
            // the padding formula looks esoteric but makes sense if you look at a diagram
            int paddingSize = ((int) Math.pow(2, numLevels - line - 1) - 1) * (maxWidth + SPACE_WIDTH);
            String padding = space(paddingSize / 2);
            int elementCount = (int) Math.pow(2, line);
            for (int i = elementCount; i < 2 * elementCount; i++) {
                String element = elements.get(i);
                output.append(padding);
                // adding some spaces is necessary depending on the parities of the widths
                if (line < numLevels - 1 && (SPACE_WIDTH + maxWidth) % 2 == 1) {
                    output.append(" ");
                }
                if (element != null) {
                    output.append(element).append(space(maxWidth - element.length()));
                } else {
                    output.append(space(maxWidth));
                }
                output.append(padding);
                if (i < 2 * elementCount - 1) {
                    output.append(space(SPACE_WIDTH));
                }
            }
            output.append('\n');
        }
        return output.toString();
    }

    /**
     * Utility method for toString
     * @param width the size of space
     * @return a string of width number of spaces
     */
    private static String space(int width) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < width; i++) {
            string.append(' ');
        }
        return string.toString();
    }

    /**
     * Utility method for toString, returns a map of node indexes for a given tree
     * @param elementMap map of node indexes and their string representations
     * @param node the root of the tree
     * @param index index of the root node of the tree (in the initial call), assumes 1 based indexing
     * @param <T> node key type
     */
    private static <T> void populateElementMap(Map<Integer, String> elementMap, Node<T> node, int index) {
        if (node != null) {
            elementMap.put(index, node.getKey().toString());
            populateElementMap(elementMap, node.getLeft(), index * 2);
            populateElementMap(elementMap, node.getRight(), index * 2 + 1);
        }
    }

    /**
     * @param root root of the tree
     * @return the depth of the tree
     */
    public static int height(Node<?> root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
        }
    }

    /**
     * @param root of the tree
     * @return the number of nodes underneath the root
     */
    public static int size(Node<?> root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + size(root.getLeft()) + size(root.getRight());
        }
    }

    /**
     * @param root root of the tree
     * @param <T> node key type
     * @return a preorder listing of the tree
     */
    public static <T> List<T> preorder(Node<T> root) {
        List<T> list = new ArrayList<>();
        preorder(list, root);
        return list;
    }

    private static <T> void preorder(List<T> list, Node<T> node) {
        if (node != null) {
            list.add(node.getKey());
            preorder(list, node.getLeft());
            preorder(list, node.getRight());
        }
    }

    /**
     * @param root root of the tree
     * @param <T> node key type
     * @return an inorder listing of the tree
     */
    public static <T> List<T> inorder(Node<T> root) {
        List<T> list = new ArrayList<>();
        inorder(list, root);
        return list;
    }

    private static <T> void inorder(List<T> list, Node<T> node) {
        if (node != null) {
            inorder(list, node.getLeft());
            list.add(node.getKey());
            inorder(list, node.getRight());
        }
    }

    /**
     * @param root root of the tree
     * @param <T> node key type
     * @return a postorder listing of the tree
     */
    public static <T> List<T> postorder(Node<T> root) {
        List<T> list = new ArrayList<>();
        postorder(list, root);
        return list;
    }

    private static <T> void postorder(List<T> list, Node<T> node) {
        if (node != null) {
            postorder(list, node.getLeft());
            postorder(list, node.getRight());
            list.add(node.getKey());
        }
    }

    /**
     * This method is a safe wrapper for the longer signature version.
     * @param preorder preorder list for the tree, no repeating elements
     * @param inorder inorder list for the tree, no repeating elements
     * @param <T> key type
     * @return The unique binary tree represented by a given preorder and inorder list.
     * @throws RuntimeException when the input lists don't share their elements or if a list has a repeating element
     */
    public static <T> Node<T> createFromLists(List<T> preorder, List<T> inorder) {
        if (preorder.size() != inorder.size()) {
            throw new RuntimeException("Create tree failed: lists don't match size");
        }
        for (T t : preorder) {
            if (!inorder.contains(t)) {
                throw new RuntimeException("Create tree failed: list elements don't match");
            }
        }
        if (new HashSet<>(preorder).size() != preorder.size() ||
            new HashSet<>(inorder).size() != inorder.size()) {
            throw new RuntimeException("Create tree failed: lists contain repeating elements");
        }
        Node<T> node = new Node<>(null, null, null, null);
        createFromLists(node, preorder, inorder);
        return node;
    }

    public static <T> void createFromLists(Node<T> node, List<T> preorder, List<T> inorder) {
        node.setKey(preorder.get(0));
        preorder.remove(0);
        int loc = -1;
        for (int i = 0; i < inorder.size(); i++) {
            if (inorder.get(i).equals(node.getKey())) {
                loc = i;
                break;
            }
        }
        if (loc == -1) {
            return;
        }
        if (loc > 0) {
            node.setLeft(new Node<>(null, node, null, null));
            createFromLists(node.getLeft(), preorder,
                    inorder.subList(0, loc));
        }
        if (loc < inorder.size() - 1) {
            node.setRight(new Node<>(null, node, null, null));
            createFromLists(node.getRight(), preorder,
                    inorder.subList(loc + 1, inorder.size()));
        }
    }
}