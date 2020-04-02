/*
 * File: HashAssign2.java
 * Author: David Hui
 * Description: Allows a user to see the emotions of a selected area in Windsor.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.*;

public class HashAssign2 extends JFrame implements ActionListener{
    Timer myTimer; // repaint timer
    AssignPanel ap; //reference to the JPanel

    public HashAssign2(HashTable<Emotion> emotions) {
        super("HashAssign 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        // set up the GUI
        myTimer = new Timer(10, this);	 // trigger every 100 ms
        myTimer.start();

        ap = new AssignPanel(emotions);
        add(ap);

        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt){
        // repaint every 100ms
        if(ap!= null && ap.ready){
            ap.repaint();
        }
    }

    public static void main(String[] arguments) {
        try{
            // Read the data file and create a hashtable of the emotions
            Scanner f = new Scanner(new BufferedReader(new FileReader("creeper.txt")));
            HashTable<Emotion> emotions = new HashTable<>();

            // populate the hashtable
            while(f.hasNextLine()){
                String[] info = f.nextLine().split(" ");
                // create a new emotion based on the info
                Emotion temp = new Emotion(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]));
                // if emotion data already exists for this locus, add it on
                if(emotions.contains(temp)){
                    emotions.get(temp).addEntry(Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]));
                }
                else{
                    emotions.add(temp);
                }
            }

            HashAssign2 frame = new HashAssign2(emotions);
        }
        catch (IOException e){
            System.err.println("Unable to open the file!");
            System.exit(1);
        }
    }
}

class AssignPanel extends JPanel{
    public boolean ready=false;

    private BufferedImage back = null; // background map
    private HashTable<Emotion> emotions; // stores the emotions
    private int mx,my; // current mouse pos
    private boolean mouseClicked = false; // whether mouse has been clicked

    private final int RADIUS = 10; // radius of the lookup area

    public AssignPanel(HashTable<Emotion> emotions){
        // setup the JPanel
        addMouseListener(new ClickListener());
        try {
            back = ImageIO.read(new File("windsor.PNG"));
        }
        catch (IOException e) {
            System.exit(1);
        }
        setSize(800,600);

        // initialize variables
        this.emotions = emotions;
        this.mx = this.my = 0;
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }

    public void paintComponent(Graphics g){
        // cast g to Graphics2D so that we can control stroke
        super.paintComponent(g);

        if(g!= null){
            // draw the map
            g.drawImage(back,0,0,this);
            if(mouseClicked){ // ensure the mouse has been clicked before drawing something
                // test all points within the rectangle of RADIUS from the mouse point where the distance of such point is lte the RADIUS
                for(int x = mx-RADIUS; x<=mx+RADIUS;x++){
                    for(int y = my-RADIUS; y <= my+RADIUS; y++){
                        if(Point2D.distance(x, y, mx, my) <= RADIUS){
                            // create a dummy obj to act as the key to see if there is something at the point
                            Emotion dummy = new Emotion(x, y, 0, 0, 0);
                            Emotion actual = emotions.get(dummy);
                            if(actual != null){
                                g.setColor(new Color(actual.getR(), actual.getG(), actual.getB()));
                                g.fillOval(x, y, 2 ,2);
                            }
                        }

                    }
                }

                // draw the enclosing circle
                g.setColor(Color.black);
                // add 1px so that there is no overlap
                g.drawOval(mx-(RADIUS+1), my-(RADIUS+1), (RADIUS+1)*2, (RADIUS+1)*2);
            }

        }

    }

    class ClickListener implements MouseListener{
        // ------------ MouseListener ------------------------------------------
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){}

        public void mousePressed(MouseEvent e){
            // set the mouse info for the program
            mouseClicked = true;
            mx = e.getX();
            my = e.getY();
        }
    }
}