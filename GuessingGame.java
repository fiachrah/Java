import java.io.*;
import java.util.Scanner;

public class GuessingGame
{

    public static char direction = 'F';

    public static void main(String[] args)
    {

        // Give the player the choice between default tree and stored one

        System.out.println("\n(P: Use Default Tree, L: Load a stored tree)");

        // Take player input

        Scanner input = new Scanner(System.in);

        char option = Character.toUpperCase((input.next().charAt((0))));

        if(option == 'L') {

            // Load stored tree

            loadTree();;

        }
        else{

            // Create new default tree

            BinaryTree<String> gameTree = new BinaryTree<String>();

            createTree(gameTree);

            // Play Game

            playGame(gameTree);

        }

    }

    public static void printTree(BinaryNodeInterface node, int indent)
    {

            for (int i = 1; i <= indent; i++){

                // Print tabbed spaces depending on number of indents requested

                System.out.print("        ");

            }

            // If this Node is the first (root) node

            if(direction == 'F'){

                // Print Data

                System.out.println(node.getData());

            }

            // If this Node was a left child

            else if(direction == 'L'){

                // Print Data

                System.out.println("Yes: "+node.getData());

            }

            // If this Node was a right child

            else if(direction == 'R'){

                // Print Data

                System.out.println("No: "+node.getData());

            }

            if (node.hasLeftChild()) {

                // Set direction node was obtained from

                direction = 'L';

                // Call method on this node

                printTree(node.getLeftChild(), indent + 1);

            }
            else if (node.hasRightChild()) {

                // Fix indentation if no left child

                for (int i = 1; i <= indent+1; i++) {

                    System.out.print("        ");

                }
            }

            if (node.hasRightChild()) {

                // Set direction node was obtained from

                direction = 'R';

                // Call method on this node

                printTree(node.getRightChild(), indent + 1);

            }
            else if (node.hasLeftChild()) {

                // Fix indentation if no right child

                for (int i = 1; i <= indent+1; i++){

                    System.out.print("        ");

                }

            }
    }

    public static void saveTree(BinaryTree<String> tree){

        // File where tree is being stored

        String filename = "C:\\Users\\f14ch\\OneDrive\\Desktop\\tree.ser";

        try
        {
            // Write tree as serialised object to file

            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(tree);

            out.close();
            file.close();

            System.out.println("\nTree has been stored!");

            // Display game menu

            displayMenu(tree);

        }

        catch(IOException ignored){ }

    }

    public static void loadTree(){

        String filename = "C:\\Users\\f14ch\\OneDrive\\Desktop\\tree.ser";

        try
        {
            // Obtain serialised object from file and read as tree

            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            BinaryTree<String> tree = (BinaryTree<String>)in.readObject();

            in.close();
            file.close();

            System.out.println("\nTree has been loaded!");

            // Play Game with read tree

            playGame(tree);

        }

        catch(IOException | ClassNotFoundException ignored) {}

    }

    public static void displayMenu(BinaryTree<String> tree){

        Scanner input = new Scanner(System.in);

        System.out.println("\n(P: Play Again, S: Store this tree, L: Load a tree, Q: Quit)");

        // Take player input

        char option = Character.toUpperCase((input.next().charAt((0))));

        if(option == 'P'){

            // Play Game with same tree

            playGame(tree);

        }
        else if (option == 'S') {

            // Store this tree

            saveTree(tree);

        }
        else if (option == 'L') {

            // Load stored tree and play game with this

            loadTree();

        }
        else{

            // Quit program

            System.exit(0);

        }
    }


    public static void createTree(BinaryTree<String> tree)
    {

        // Create new Tree

        BinaryTree<String> aTree = new BinaryTree<String>();

        // Add default nodes to Tree

        aTree.setTree("Polar Bear");
        BinaryTree<String> bTree = new BinaryTree<String>("Tiger");
        BinaryTree<String> cTree = new BinaryTree<String>("Crow");
        BinaryTree<String> dTree = new BinaryTree<String>("Penguin");
        BinaryTree<String> eTree = new BinaryTree<String>("Spider");
        BinaryTree<String> fTree = new BinaryTree<String>("Fish");
        BinaryTree<String> gTree = new BinaryTree<String>("Car");
        BinaryTree<String> hTree = new BinaryTree<String>("Bicycle");
        BinaryTree<String> iTree = new BinaryTree<String>("Ireland");
        BinaryTree<String> jTree = new BinaryTree<String>("Croke Park");
        BinaryTree<String> kTree = new BinaryTree<String>("Book");
        BinaryTree<String> lTree = new BinaryTree<String>("Does it Live in a Cold Climate?", aTree, bTree);
        BinaryTree<String> mTree = new BinaryTree<String>("Can it fly?", cTree, dTree);
        BinaryTree<String> nTree = new BinaryTree<String>("Is it an insect?", eTree, fTree);
        BinaryTree<String> oTree = new BinaryTree<String>("Does it have an engine?", gTree, hTree);
        BinaryTree<String> pTree = new BinaryTree<String>("Is it a country??", iTree, jTree);
        BinaryTree<String> qTree = new BinaryTree<String>("Is it a bird?", mTree, nTree);
        BinaryTree<String> rTree = new BinaryTree<String>("Is it a place?", pTree, kTree);
        BinaryTree<String> sTree = new BinaryTree<String>("Is it a mammal?", lTree, qTree);
        BinaryTree<String> tTree = new BinaryTree<String>("Does it have wheels?", oTree, rTree);
        tree.setTree("Is it an animal?", sTree, tTree);

    }

    public static void playGame(BinaryTree<String> tree)
    {
        if (tree.isEmpty()) {

            // Alert tree is empty

            System.out.println("The tree is empty");

        }
        else {

            System.out.println("\n---------------NEW GAME---------------");

            System.out.println("\nTree being used:\n");

            // Print tree

            printTree(tree.getRootNode(), 0);

            // Obtain root node of tree and set as current Node

            BinaryNodeInterface currentNode = tree.getRootNode();

            Scanner input = new Scanner(System.in);

            while(!(currentNode.isLeaf())){

                System.out.println("\nQ: "+currentNode.getData()+" (Y/N) ");

                // Take player input

                char answer = Character.toUpperCase((input.nextLine().charAt((0))));

                if(answer == 'Y'){

                    // Change current Node to current Node's left Child

                    currentNode = currentNode.getLeftChild();

                }
                else{

                    // Change current Node to current Node's right Child

                    currentNode = currentNode.getRightChild();

                }

            }

            // Make a guess when current Node is a leaf

            System.out.println("\nMy Guess: "+currentNode.getData()+" (Y/N) ");

            // Take player input

            char answer = Character.toUpperCase((input.nextLine().charAt((0))));

            if(answer == 'Y'){

                System.out.println("\nThe tree guessed correctly!");


            }
            else{

                System.out.println("\nThe tree guessed incorrectly! Please answer the following questions to improve the tree!");

                System.out.println("\nPlease provide what you were thinking of:");

                // Take player's correct answer

                Object newGuess = input.nextLine();

                System.out.println("\nPlease provide a distinguishing question where the answer yes will indicate "+newGuess+" and the answer no will indicate "+currentNode.getData()+":");

                // Take player's distinguishing question

                String newQuestion = input.nextLine();

                // Create 2 new nodes for new left and right children

                BinaryNode<Object> left = new BinaryNode<>();

                BinaryNode<Object> right = new BinaryNode<>();

                // Set data for these 2 new Nodes

                left.setData(newGuess);

                right.setData(currentNode.getData());

                // Set current Node new data

                currentNode.setData(newQuestion);

                // Set new Nodes as children of current Node

                currentNode.setLeftChild(left);

                currentNode.setRightChild(right);

                System.out.println("\nThank you for your input!");

            }

            // Display game menu

           displayMenu(tree);

        }
    }
}