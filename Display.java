import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import java.applet.Applet;
import java.awt.Font;
import java.util.Map;

public class Display extends JApplet implements MouseListener, MouseMotionListener{
    double lenScreen = 1200;
    double midpoint = lenScreen/2;
    Node<Integer> root;
    FunctionGuesser game;
    int [][] answers;
    int x, y;
    int [] rectDim = { 50, 50, 150, 50 };
    int [] answersRect = { 50,120, 150, 50 };
    boolean showAnswers = false;
    Graphics2D g;
    public void init(){
      addMouseListener(this);
      addMouseMotionListener(this);
      root = new Node<Integer>();
      game = new FunctionGuesser(2, root);
      startGame( game );
    }
    
    public void startGame( FunctionGuesser newGame)
    {
        showAnswers = false;
        answers = newGame.getPolynomial();
        newGame.coefficientGenerator();
        newGame.createTreeRec(root, 0);
        newGame.printTree(root, 0);
        repaint();
    }
    
    public void paint(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      g.clearRect(0, 0, 2000, 2000);
      Font font = new Font("Courier", Font.BOLD,15);
      g.setFont( font );
      
      //draws out the reset game button
      Rectangle reset = new Rectangle( rectDim[0], rectDim[1], rectDim[2], rectDim[3]);
      g2.draw(reset);
      g2.drawString( "New Game", rectDim[0] + (int)((double)rectDim[2]/4.0) , rectDim[1] + (int)((double)rectDim[3]/2.0) );
      
      //prints out the answers button
      Rectangle showAnswer = new Rectangle( answersRect[0], answersRect[1], answersRect[2], answersRect[3]);
      g2.draw(showAnswer);
      g2.drawString( "Show Answers", answersRect[0] + (int)((double)answersRect[2]/5.0) , answersRect[1] + (int)((double)answersRect[3]/2.0) );
      
      
      //prints out a 'ruler' on the board
      for(int x = 0; x < 2000; x = x + 50)
      {
          g.drawString("-"+ x, 0, x);
          g.drawString("|", x, 10);
          g.drawString(""+x, x, 20);
      }
      
      
      //calls the recursive function to draw out the tree
      drawTree( g, root, (double)0 , midpoint, DIR.CENTER);
      
      //calls the method to print out the answers
      if( showAnswers )
      {
          printAnswers(g);
        }
    }
    
    /**
     * Prints out the the answers
     */
    public void printAnswers(Graphics g)
    {
        System.out.println("Answers:");
        int [][] polynomial = game.getPolynomial();
        int x = 800;
        int y = 100; 
        
        //creates a map between the coefficents
        HashMap<Integer,String> coeff = new HashMap<Integer, String>();
        coeff.put(0, "ab" );
        coeff.put(1, "cd" );
        coeff.put(2, "ef" );
        
        for( int numFun = 0; numFun < polynomial.length; numFun++)
        {
            for( int numCo = 0; numCo < 2; numCo++)
            {
                char [] charArr = coeff.get(numFun).toCharArray();
                g.drawString( "" + charArr[numCo] + ": " +  polynomial[numFun][numCo] , 800 + numCo*60, 100 + numFun*30 );
            }
        }
    }
    
    public void drawTree( Graphics g , Node<Integer> curr, double height, double centerDis, DIR dir)
    {
        Graphics2D g2 = (Graphics2D) g;
        height++;
        if( curr == null )
        {
            return;
        } 
        double heightGraph = height * 100;
        double buffer = 0;
        String string = "";
        //left
        if( dir == DIR.LEFT )
        {
            buffer = -1*(3500.0 / (Math.pow(3,height) ) ) ;
            g2.setColor( Color.orange );
            string = "a*" + curr.parent.element + "+b=";
        }
        //center
        else if( dir == DIR.CENTER )
        {
            buffer = 0;
            g2.setColor( Color.cyan );
            if( curr.parent != null )
            {
                string = "c*" + curr.parent.element + "+d=";
            }
        }
        //right
        else if( dir == DIR.RIGHT )
        {
            buffer = (3500.0 / (Math.pow(3,height)) ) ;
            g2.setColor( Color.green );
            string = "e*" + curr.parent.element + "+f=";
        }
        
        
        Rectangle rect = new Rectangle( (int) (centerDis + buffer) , (int)heightGraph,40,40);
        g2.fill(rect);
        
        g2.setColor( Color.black );
        g2.drawString(string, (int) (centerDis + buffer) , (int)heightGraph );
        g2.drawString("" + curr.element, (int) (centerDis + buffer) + 15,(int)heightGraph  + 25);
        
        drawTree( g, curr.right , height , centerDis + buffer , DIR.LEFT);
        drawTree( g, curr.center , height , centerDis + buffer , DIR.CENTER);
        drawTree( g, curr.left , height , centerDis + buffer , DIR.RIGHT);
    }
    
    public void mouseMoved(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    
    public void mouseClicked(MouseEvent e) {
          x=e.getX();
          y=e.getY();
          
          if( x >= rectDim[0] && y >= rectDim[1] && x<=rectDim[0] + rectDim[2] && y <= rectDim[1] + rectDim[3])
          {
              root = new Node<Integer>();
              game = new FunctionGuesser(2, root);
              startGame( game );
          }
          
          if( x >= answersRect[0] && y >= answersRect[1] && x<=answersRect[0] + answersRect[2] && y <= answersRect[1] + answersRect[3])
          {
              showAnswers = true;
              repaint();
          }
        }
    
    /**
     * Creates an enum, DIR, to specify 
     * which direction for the tree to go.
     */
    public enum DIR
    {
        RIGHT,
        CENTER,
        LEFT
    }
}