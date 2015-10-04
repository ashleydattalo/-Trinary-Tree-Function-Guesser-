import java.util.*;
import java.util.Map;
/**
 * The driver class for functionGuesser, 
 * allows a user to play a game of functionGuesser
 * 
 * @author Ashley Dattalo
 * @version May 26th, 2015
 */
public class FunctionGuesserDriver
{
    private Scanner in = new Scanner(System.in);
    private boolean gameOver = true;
    /**
     * Main method to interact with user
     */
    public static void main ( String [] args )
    {
        FunctionGuesserDriver game = new FunctionGuesserDriver();
        boolean quit = false;
        //allows a user to continue or quit playing 
        //after they won their previous game
        while(game.gameOver && !quit)
        {
            System.out.println("You are playing a game of function guesser: ");
            System.out.println("If you do NOT want to play: press q");
            System.out.println("If you DO want to play: press p");
            if( game.in.nextLine().contains("q") )
            {
                quit = true;
            }
            else
            {
                int treeHeight = game.declareHeight();
                game.beginGame( treeHeight );
            }
        }
    }
    /**
     * Starts a game of FunctionGuesser
     * @param height of Tree - the height of the tree
     * @returns true when they have guessed the
     *  correct functions
     */
    public boolean beginGame( int heightOfTree )
    {
        Node<Integer> root = new Node<Integer>();
        FunctionGuesser game = new FunctionGuesser( heightOfTree , root );
        game.coefficientGenerator();
        game.createTreeRec( root , 0);
        game.printTree( root, 0 );
        //game.printAnswers();
        
        gameOver = false;
        boolean quit = false;
        
        //allows a user to keep guessing coefficients
        //while they have not guessed the correct
        //coefficients
        while( !quit )
        {
            System.out.println( "Guess: ");
            System.out.println( "Enter branch to guess (left, center, right) ");

            String branchInput = in.nextLine();   
            //quits the loop if the user enters 'quit'
            if( branchInput.contains("quit") )
            {
                quit = true;
            }
            //prints out the answers if the user enters 'answers'
            if( branchInput.contains("answers") )
            {
                game.printAnswers();
            }
            
            //creates a map between the directions, and their values
            HashMap<String,Integer> branches = new HashMap<String, Integer>();
            branches.put("left", 2);
            branches.put("center", 1);
            branches.put("right", 0);
            
            int branch = branches.get( in.nextLine() );
            
            System.out.println( "Enter the coefficients ( a*x + b) ");
            System.out.println( "Guess a: ");
            boolean guessedCorrectA = false;
            boolean guessedCorrectB = false;
            //checks if the use entered in the correct a coefficient
            if( in.nextInt() == game.polynomial[branch][0])
            {
                System.out.println( "correct a");
                guessedCorrectA = true;
            }
            System.out.println( "Guess b: ");
            //checks if the use entered in the correct b coefficient
            if( in.nextInt() == game.polynomial[branch][1])
            {
                System.out.println( "correct b");
                guessedCorrectB = true;
            }
            
            //if both the a and b are correct, then
            //it sets that direction to true
            if( guessedCorrectA && guessedCorrectB)
            {
                game.correctGuess[branch] = true;
            }
            
            //if all directions have been guessed
            //correctly, then the game is over
            if( game.correctGuess[0] && game.correctGuess[1] && game.correctGuess[2])
            {
                System.out.println( "Congrats, you correctly guessed everything");
                quit = true;
                gameOver = true;
            }
        }
        return gameOver;
    }
    /**
     * Allows a user to enter in the tree height
     * @returns the int they entered
     */
    public int declareHeight()
    {
        System.out.println( "Please enter in the height you want the tree to be: ");
        return in.nextInt();
    }
}
