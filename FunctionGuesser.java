import java.util.*;
/**
 * A game that requires the user to guess
 * the coefficients of a function (in form a*x + b) 
 * where the directions of the branches of the tree are: 
 * left, center, right. 
 * 
 * @author Ashley Dattalo
 * @version May 20th, 2015
 */
public class FunctionGuesser
{
    Node<Integer> root = new Node<Integer>();
    int height;
    int numFunctions = 3;
    int numCoeff = 2;
    int [][] polynomial = new int [numFunctions][numCoeff];
    boolean [] correctGuess = new boolean[numFunctions];
    int count = 0;
    /**
     * Constructor for the FuctionGuesser game
     * @param height - the height of the tree
     * @param root - the root of the tree
     */
    public FunctionGuesser( int height , Node<Integer> root)
    {
        this.height = height;
        this.root = root;
        Random random = new Random();
        root.element = random.nextInt(9) + 1;
    }
    
    /**
     * Recursive method to create a tree 
     * @pre height - the height of the tree
     * @param node, the current node
     * @param nodeHeight - the height of the node
     */
    public void createTreeRec( Node<Integer> node, int nodeHeight )
    {
        nodeHeight++;
        if( nodeHeight - 1 == height )
        {
            return;
        }
        Node<Integer> leftNode = new Node<Integer>( function( node.element, 2) );
        Node<Integer> centerNode = new Node<Integer>( function( node.element, 1) );
        Node<Integer> rightNode = new Node<Integer>( function( node.element, 0) );
        node.left = leftNode;
        node.center = centerNode;
        node.right = rightNode;
        leftNode.parent = node;
        centerNode.parent = node;
        rightNode.parent = node;
        createTreeRec( leftNode, nodeHeight);
        createTreeRec( centerNode, nodeHeight);
        createTreeRec( rightNode, nodeHeight);
    }
    
    /**
     * Sets the node to the correct value;
     * @pre - it's parent's elemnt is passed in as x,
     *        then the function performs it's operation     
     *        on that value
     * @param x - the parent's element
     * @param row : 2 for left branch
     *              1 for center branch
     *              0 for right branch
     */
    public int function( int x, int row )
    {
        return polynomial[row][0]*x + polynomial[row][1]; 
    }
    
    /**
     * Randomly generates the coefficients
     * for the function.
     */
    public void coefficientGenerator()
    {
        Random random = new Random();
        for( int numFun = 0; numFun < numFunctions; numFun++)
        {
            polynomial[numFun][0] = random.nextInt(5) + 5;
            polynomial[numFun][1] = random.nextInt(5) + 1;
        }
    }
    
    /**
     * Recursively prints out the tree
     * @param curr - the current node
     * @param indent - the correct indent
     *  corresponding to the height of the tree
     */
    public void printTree( Node<Integer> curr , int indent )
    {
        indent++;
        if( curr == null )
        {
            return;
        } 
        String indentStr = "";
        for( int i = 0; i < indent ; i++ )
        {
            indentStr += "     ";
        }
        System.out.println( indentStr + curr.element ); 
        printTree( curr.right , indent);
        printTree( curr.center , indent);
        printTree( curr.left , indent);
    }
    
    /**
     * Prints out the the answers
     */
    public void printAnswers()
    {
        System.out.println("Answers:");
        for( int numFun = 0; numFun < polynomial.length; numFun++)
        {
            for( int numCo = 0; numCo < numCoeff; numCo++)
            {
                System.out.print( polynomial[numFun][numCo] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Returns the polynomial full of answers
     */
    public int [][] getPolynomial()
    {
        return polynomial;
    }
}
