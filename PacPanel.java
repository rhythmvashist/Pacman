import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;


/**
 * @author : Rhythm Vashist
 *
 *This class extends jPanel it includes all the ImageIcon for the pacMan ,ghost,pellet for the user to interact with it and giving various input.
 *
 */
public class PacPanel extends JPanel {
    private ImageIcon pacMan,pellet,ghost;
    private Point pacManPt,pelletPt,clickPt,ghostPt;
    private Rectangle pacRect,ghostRect,pelletRect;

    private int dirX,dirY,t;
    private Timer time;
    private boolean mouseEnters,pelletEaten;


    /**
     * This is the constructor that creates pacMan,ghost,pellet and set their image icon and initialize and declare all the event listeners
     * and all the remaining variables.  .
     */
    public PacPanel(){
        pacMan=new ImageIcon("pacman/pacman.png");
        pellet=new ImageIcon("pacman/pellet.png");
        ghost=new ImageIcon("pacman/clyde.png");

        mouseEnters=false;
        pelletEaten=false;
        dirX=5;
        dirY=4;
        t=0;
        pacManPt=new Point(-250,-250);
        ghostPt=new Point(100+(int)Math.random()*450,100+(int)Math.random()*450);
        pelletPt=new Point((int)(Math.random()*910+Math.random()*35),(int)(Math.random()*910+Math.random()*35));

        time=new Timer(100,new TimeListener());
        time.start();
        setFocusable(true);

        addKeyListener(new IconChanger());
        addMouseListener(new MouseListenerLocation());
        addMouseMotionListener(new MouseListenerLocation());

        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(1000,1000));
    }

    /**
     * This method is used to draw the icon on the panel
     * @param g : the graphics object that stores the current state that includes font,color,etc.
     */
     public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        if(mouseEnters){
            pacManPt.x=(int)(clickPt.x-50);
            pacManPt.y=(int)(clickPt.y-50);
        }
        pacMan.paintIcon(this,g2,(int)pacManPt.x,(int)pacManPt.y);
        pellet.paintIcon(this,g2,(int)pelletPt.x,(int)pelletPt.y);
        ghost.paintIcon(this,g2,(int)ghostPt.x,(int)ghostPt.y);
    }


    /**
     * this is the inner private class that helps us to make use of the timer. it helps in the movement of the icons and also check for any collision respectively
     * it start as the program starts and stop when there is the collision between and the ghost and the pacMan and also keep a check on the 5 second countdown
     */
    private class TimeListener implements ActionListener{

        /**
         * The method to check for the collision and movement of the icons
         */
        public void actionPerformed(ActionEvent e){
            pacRect =new Rectangle(pacManPt.x+12,pacManPt.y+12,pacMan.getIconWidth()-12,pacMan.getIconHeight()-12);
            ghostRect=new Rectangle(ghostPt.x+12,ghostPt.y,ghost.getIconWidth()-12,ghost.getIconHeight()-12);
            pelletRect=new Rectangle(pelletPt.x+12,pelletPt.y+12,pellet.getIconWidth()-12,pellet.getIconHeight()-12);


            if(pelletRect.intersects(pacRect)){
                pelletEaten=true;
                pelletPt.x=-1000;
                pelletPt.y=-1000;
                ghost=new ImageIcon("pacman/blue.png");
            }

            if(t>5000){
                t=0;
                pelletEaten=false;
                pelletPt=new Point((int)(Math.random()*910+Math.random()*35),(int)(Math.random()*910+Math.random()*35));
                ghost=new ImageIcon("pacman/clyde.png");
            }

            if(ghostRect.intersects(pacRect)){
                if(pelletEaten) {
                    time.stop();
                    JOptionPane.showMessageDialog(null, "You win");
                    repaint();
                }
                else{
                    time.stop();
                    JOptionPane.showMessageDialog(null, "You Lose");
                    repaint();
                }
            }

            if(pelletEaten){
                t+=100;
            }
            if (ghostPt.x >= 910 || ghostPt.x <= 0) {
                dirX *= -1;
            }
            if (ghostPt.y >= 860 || ghostPt.y <= 0) {
                dirY *= -1;
            }
            ghostPt.x+=dirX;
            ghostPt.y+=dirY;
            repaint();
        }
    }

    /**
     * This is a private inner class that helps us in changing the icon of the ghost by triggering various key event mapped to different keys
     */
    private class IconChanger implements KeyListener {
        @Override
        /**
         * the method used to change the icon of the ghost by pressing various keys
         * @param keypress: the key event object that passes the information about the key pressed in the method.
         *
         */
        public void keyPressed(KeyEvent keypress) {
            // the icons not change if the the pacman has eaten the pellet and the pacman has turned it into the blue
            if(!pelletEaten) {
                switch (keypress.getKeyCode()) {
                    case KeyEvent.VK_P: {
                        ghost = new ImageIcon("pacman/pinky.png");
                        break;
                    }
                    case KeyEvent.VK_B: {
                        ghost = new ImageIcon("pacman/blinky.png");
                        break;
                    }
                    case KeyEvent.VK_I: {
                        ghost = new ImageIcon("pacman/inky.png");
                        break;
                    }
                    case KeyEvent.VK_C: {
                        ghost = new ImageIcon("pacman/clyde.png");
                        break;
                    }
                }
            }
        }
       
        public void keyReleased(KeyEvent arg0) {}
        public void keyTyped(KeyEvent arg0) {}
    }

    /**
     * this is the private inner class that is responsible for the movement of the pacMan inside the panel along with the mouse movement
     */
    private class MouseListenerLocation extends MouseInputAdapter {

        /**
         * This method checks if the mouse cursor is inside or outside of the any given parent component
         * @param location :The mouse event object that stores the coordinate of the mouse while the mouse movement
         */
        public void mouseEntered(MouseEvent location) {
            mouseEnters = true;
            clickPt = location.getPoint();
        }

        /**
         * This method runs when the mouse is moved inside the component and it passes the coordinates of the mouse with respect to the parent component.
         * @param locations : the mouse event object that stores the coordinate of the mouse while the mouse movement.
         */
        public void mouseMoved(MouseEvent locations) {
            clickPt = locations.getPoint();
        }
    }

}
