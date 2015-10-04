/**
 * Node class, with possible branches: left, center, right.
 * 
 * @author Ashley Dattalo
 * @version May 26, 2015
 */
final class Node<Integer>
{    
    Integer element;
    Node<Integer> left;
    Node<Integer> right;
    Node<Integer> center;
    Node<Integer> parent;
    
    /**
     * Constructs a node with no element.
     */
    public Node( )
    {
        this( null);
    }
    
    /**
     * Constructs a Node with an element.
     * @param theElement - element of the node.
     */
    public Node( Integer theElement )
    {
        element = theElement;
    }
    
}