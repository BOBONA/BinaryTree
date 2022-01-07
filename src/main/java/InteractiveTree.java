import BinaryTree.GeneralProcedures;
import BinaryTree.Node;
import BinaryTree.SearchTree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Last edited 1/6/2021 <br>
 * This runs an interactive tree program which uses the BinaryTree classes. This was made for my Algorithms class.
 * @author Benjamin Friedman
 */
public class InteractiveTree {

    /**
     * An interactive program for testing the binary tree's features. Not every feature is accessible through this.
     * @param args You can supply an input file path and an output file path as separate arguments.
     */
    public static void main(String[] args) {
        BufferedReader input;
        PrintWriter output;
        try {
            InputStream inputMethod = args.length > 0 ? new FileInputStream(args[0]) : System.in;
            input = new BufferedReader(new InputStreamReader(inputMethod));
            OutputStream outputMethod = args.length < 2 ? System.out : new FileOutputStream(args[1]);
            output = new PrintWriter(outputMethod);
        } catch (Exception e) {
            System.out.println("Issue using files supplied through command line arguments: ");
            System.out.println(e.getMessage());
            System.out.println("Exiting program...");
            return;
        }
        try {
            interactiveTree(input, output);
        } catch (Exception e) {
            System.out.println("Unexpected error: "); // I know the program is supposed to be robust, but you never know...
            System.out.println(e.getMessage());
            try {
                input.close();
                output.flush();
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Exiting program...");
        }
    }

    /**
     * Runs the interactive tree program
     * @param input Tree commands should be on separate lines.
     * @param output Where the program's output is sent
     * @throws Exception Could throw an IOException
     */
    public static void interactiveTree(BufferedReader input, PrintWriter output) throws Exception {
        output.println("Entering interactive tree (type MENU for help): ");
        output.flush();
        SearchTree<Integer> tree = new SearchTree<>();
        List<Integer> temp = null; // used for the C command
        String line;
        while ((line = input.readLine()) != null) {
            // parse inputs
            line = line.trim().toUpperCase();
            if (line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split(" ");
            char command = 0;
            List<String> arguments = new ArrayList<>();
            for (String token : tokens) {
                token = token.trim();
                if (token.isEmpty()) {
                    continue;
                }
                if (command == 0) {
                    command = token.charAt(0);
                } else {
                    arguments.add(token);
                }
            }
            try {
                if (temp != null && command != 'C') {
                    output.println("Expected second line, canceling C command");
                    temp = null;
                }
                switch (command) {
                    case 'I': // insert
                        int value = getSingleInteger(arguments, 0);
                        if (value < 0 || value > 99) {
                            inputError("Integer must be in the range [0, 99]");
                        }
                        tree.insert(value);
                        output.println("Inserted " + value);
                        break;
                    case 'D': // delete
                        value = getSingleInteger(arguments, 0);
                        tree.delete(tree.search(value));
                        output.println("Deleted " + value);
                        break;
                    case 'S': // search
                        value = getSingleInteger(arguments, 0);
                        Node<Integer> node = tree.search(value);
                        if (node == null) {
                            output.println("Could not find " + value);
                        } else {
                            output.println("Depth of " + value + " is " + node.getData());
                        }
                        break;
                    case 'P': // print
                        output.println(tree);
                        if (tree.height() > 4) {
                            output.println("Lower levels hidden...");
                        }
                        break;
                    case 'R': // preorder
                        output.println("Preorder list: " + GeneralProcedures.preorder(tree.root()));
                        break;
                    case 'N': // inorder
                        output.println("Inorder list: " + GeneralProcedures.inorder(tree.root()));
                        break;
                    case 'O': // postorder
                        output.println("Postorder list: " + GeneralProcedures.postorder(tree.root()));
                        break;
                    case 'C': // create from inorder and preorder
                        List<Integer> arr = new ArrayList<>();
                        for (int i = 0; i < arguments.size(); i++) {
                            arr.add(getSingleInteger(arguments, i));
                        }
                        if (temp == null) {
                            temp = arr;
                        } else {
                            output.println(GeneralProcedures.toString(GeneralProcedures.createFromLists(temp, arr), 4));
                            temp = null;
                        }
                        break;
                    case 'E':
                        output.println("Exiting interactive tree...");
                        output.flush();
                        return;
                    default:
                        String[] menu = new String[]{
                                "Invalid command. Possible commands: ",
                                "(I)nsert <int>",
                                "(D)elete <int>",
                                "(S)earch <int>",
                                "(P)rint",
                                "p(R)eorder",
                                "i(N)order",
                                "p(O)storder",
                                "(C)reate <int...>",
                                "The create command takes two lines beginning with C. " +
                                        "The first line takes the preorder listing of a tree. " +
                                        "The second line takes the inorder listing of a tree. " +
                                        "It does not replace the current tree, only prints it.",
                                "(E)xit"
                        };
                        for (String str : menu) {
                            output.println(str);
                        }
                }
            } catch (Exception e) { // intended path for user caused exceptions
                output.println(e.getMessage());
                if (command == 'C') {
                    temp = null;
                    output.println("Exiting C command");
                }
            }
            output.flush();
        }
    }

    private static Integer getSingleInteger(List<String> arguments, int index) throws Exception {
        try {
            return Integer.parseInt(arguments.get(index));
        } catch (Exception e) {
            throw new Exception("Could not parse integer");
        }
    }

    private static void inputError(String message) throws Exception {
        throw new Exception(message);
    }
}
