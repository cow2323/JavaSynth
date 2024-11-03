import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import java.awt.event.*;






/*
 * Class for custom GUI Knob component in Synth
 * 
 * Code based on Grant William Braught 12/4/2000 JKnob Component @ Dickinson College
 * 
 */


 class SynthKnob 
    extends JComponent
    implements MouseListener, MouseMotionListener{


        private static final int radius = 50;
        private static final int spotRadius = 10;


        private double theta;
        private Color knobColor;
        private Color spotColor; 

        private boolean pressedOnSpot;

        public boolean isChanged;

        public KnobHandler handler;

        public SynthKontroll kontroll;

        private Parameter param;



        class KnobHandler {


            //SynthKnob knob;
    
           ///KnobHandler(SynthKnob knob){this.knob = knob;}
    
    
            public void stateChanged(){

                double newValue = (getAngle() + Math.PI)/(Math.PI*2);

                System.out.println("new volumeee: " + newValue);

                //kontroll.changeVolume(newValue);


                param.changeValue(newValue);


            };


            
            
            
            }
        
        


        //Constructor

        public SynthKnob(double initTheta, Color initKnobColor, Color initSpotColor, SynthKontroll kontroll, Parameter param){



            //native variables

            this.kontroll = kontroll;

            this.param = param;


            handler = new KnobHandler();


            theta = initTheta;
            pressedOnSpot = false; 
            isChanged = false; 

            knobColor = initKnobColor;
            spotColor = initSpotColor;

            this.addMouseListener(this);
            this.addMouseMotionListener(this);


        }




        /*
         * 
         * Painting Method to construct knob
         */

         public void paint(Graphics g){

            //Draw Knob

            g.setColor(knobColor);
            g.fillOval(0,0, 2*radius, 2*radius);


            //find the center of the spot
            Point pt = getSpotCenter();
            int xc = (int)pt.getX();
            int yc = (int)pt.getY();


            //Draw Spot
            g.setColor(spotColor);
            g.fillOval(xc-spotRadius, yc-spotRadius, 2*spotRadius, 2*spotRadius);

         }


         /*
          * Return the ideal size of knob
          */

          public Dimension getPrefferedSize(){
            return new Dimension(2*radius, 2*radius); 
          }

          /**
           * 
           * Return minimum size knob can be
           */


           public Dimension getMinimumSize(){

            return new Dimension(2*radius, 2*radius);
           }

           /*
            * get current angular position of kno
            */
            public double getAngle(){
                return theta; 
            }


            /**
             * 
             * calculate the (x,y) coordinates of the center of spot
             */

             private Point getSpotCenter(){

                //Calculate the center pojnt of the spot relative to the
                //center of the circle

                int r = radius-spotRadius; 

                int xcp = (int)(r * Math.sin(theta));
                int ycp = (int)(r * Math.cos(theta));

                //Adjust the center point of the spoot so that it is offset 
                //from the center of the circle
                //(actual center point as (0,0) is upper left)

                int xc = radius + xcp;
                int yc = radius - ycp;

                //Create new point to return

                return new Point(xc,yc);

             }



             /**
              * 
              Determine if mouse click ewas on the spot or not

              */


              private boolean isOnSpot(Point pt){

                return (pt.distance(getSpotCenter()) < spotRadius);

              }

              /*
               * 
               * determine if value is changing
               */

               public boolean isChanged(){return isChanged;}

              


              //Methods from the MouseListener interface


              /**
               * 
               * 
               * Empty method because nopthing happens on click
               */

               public void mouseClicked(MouseEvent e){}


               /*
                * 
                Empty method becauce nothinf haoppens when mouse enters knob
                */

                public void mouseEntered(MouseEvent e){
                }

                /*
                 * Empty method because nothing haoppens when the mouse exits knob
                 * 
                 * 
                 */

                 public void mouseExited(MouseEvent e){}



                 /*
                  * When mouse is pressed the dragging of the spot will be enabled 
                  if the button was pressed over the spot
                  */

                  public void mousePressed(MouseEvent e){

                    Point mouseLoc = e.getPoint();
                    pressedOnSpot = isOnSpot(mouseLoc);
                  }

                  /*
                   * when button is released
                   *  the dragging of the spot is disabled 
                   */

                   public void mouseReleased(MouseEvent e){
                    pressedOnSpot = false;
                    isChanged = false; 
                   }

                   //methids from MouseMotionListener interface

                   /**
                    * Empty Method because nothing happens when the 
                    mouse is moved if it is not being dragged
                    */
    
    
                   public void mouseMoved(MouseEvent e){}

                   /*Compute the new angle for the spot and repaint the knob
                    * The new angle is computed based on new mouse position
                    */

                    public void mouseDragged(MouseEvent e){
                        if (pressedOnSpot){

                            isChanged = true; 

                            int mx = e.getX();
                            int my = e.getY();

                            //Compute the x,y position of the mouse relative
                            //to the center of the knob

                            int mxp = mx - radius;
                            int myp = radius - my;

                            //Compute the new angle og the knob from the
                            //new x abd y position of the mouse.
                            //math.atan2(..) computes the angle at which 
                            //x,y lies from the positive y axis with cw rotations
                            //being positive and ccw being negative
                            theta = Math.atan2(mxp,myp);

                            handler.stateChanged();


                            repaint();
                        }
                                        }

                    
    
                        public static void main(String[] args){

                            JFrame myFrame = new JFrame("SYnthKnob");

                            Container pane = myFrame.getContentPane();

                            //Add knob
                            //pane.add(new SynthKnob(0,Color.BLACK,Color.BLUE));

                            myFrame.addWindowListener(new WindowAdapter() {
                                public void windowClosing(WindowEvent e){
                                    System.exit(0);
                                }
                                
                            });

                            myFrame.pack();
                            myFrame.setLocationRelativeTo(null);
                            myFrame.setVisible(true);


                        }
    
                }
















                
    