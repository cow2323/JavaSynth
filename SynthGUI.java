import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
class SynthGui {

   //white keys
    private KeyButton keyA;
    private KeyButton keyW;
    private KeyButton keyS;
    private KeyButton keyE; 
    private KeyButton keyD;


    private KeyButton keyF;
    private KeyButton keyT;
    private KeyButton keyG;
    private KeyButton keyY; 
    private KeyButton keyH;
    private KeyButton keyU;
    private KeyButton keyJ;
    private KeyButton keyI;


    private KeyButton keyK;
    private KeyButton keyL;
    private KeyButton keyO;
    private KeyButton keyP;
    private SynthKnob knob;

    private KeyButton keyHyphone;

    SynthKnob volumKnob;
    
    //ADSR Envelope Knobs
    SynthKnob attackKnob;
    SynthKnob decayKnob;
    SynthKnob releaseKnob;
    SynthKnob sustainKnob;

    SynthKnob filterKnob;



    


 
    SynthKontroll kontroll;  



    //Internal class to process knob change elements
    

    SynthGui(SynthKontroll kontroll){

        

        this.kontroll = kontroll; 



        try { UIManager.setLookAndFeel(
            "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {System.exit(1);}




                    //Opretter vindu
                    JFrame vindu = new JFrame("Synth Window");

                    
                    
                    vindu.setPreferredSize(new Dimension(1800, 1200));
                    
                    vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    



                    //Setting overarching structure for GUI

                    JPanel hovedPanel = new JPanel();
                    JPanel keyPanel = new JPanel();
                    JPanel parameterPanel = new JPanel();

                   

                    

                    // Sizing
                   
                    keyPanel.setPreferredSize(new Dimension(1800,600));
                    parameterPanel.setPreferredSize(new Dimension(1800,600)); 
                    
                    // layout setting
                    
                    hovedPanel.setLayout(new BorderLayout());

                    parameterPanel.setLayout(new GridLayout());
            



                    //layout style 
                    keyPanel.setLayout(new GridBagLayout());


                    



                    
                    
                    
                    



                    
                    


                    /*

                    Key Setup 

                    KeyButton er en klasse som spiller paa jbutton
                
                
                    */
                    keyA = new KeyButton(false, keyPanel,'a',this);

                    

                    keyW = new KeyButton(true, keyPanel,'w',this);

                    keyS = new KeyButton(false, keyPanel,'s',this);

                    keyE = new KeyButton(true, keyPanel,'e',this);


                    keyD = new KeyButton(false, keyPanel, 'd',this);


                    addFiller(keyPanel, keyD.getPos() + 1); //add whitespace

                    keyF = new KeyButton(false, keyPanel,'f',this);
                    
        
                    keyT = new KeyButton(true, keyPanel,'t',this);

                    keyG = new KeyButton(false, keyPanel,'g',this);

                    keyY = new KeyButton(true, keyPanel,'y',this);
                    keyH = new KeyButton(false, keyPanel,'h',this);
                    keyU  = new KeyButton(true, keyPanel,'u',this);
                    keyJ  = new KeyButton(false, keyPanel,'j',this);
                    //keyI  = new KeyButton(true, keyPanel,'i',this);
                    addFiller(keyPanel, keyJ.getPos() + 1); //add whitespace
                    //keyI  = new KeyButton(true, keyPanel,'i',this);
                    keyK  = new KeyButton(false, keyPanel,'k',this);
                    keyO  = new KeyButton(true, keyPanel,'o',this);

                    keyL  = new KeyButton(false, keyPanel,'l',this);
                    keyP  = new KeyButton(true, keyPanel,'p',this); 
                    
                    keyHyphone  = new KeyButton(false, keyPanel,';',this);
                    
                    
                    volumKnob = new SynthKnob(0,Color.BLACK,Color.BLUE, kontroll, new Volume(kontroll));



                    attackKnob = new SynthKnob(0,Color.GRAY,Color.BLUE,kontroll, new Attack(kontroll) );
                    decayKnob = new SynthKnob(0,Color.GRAY,Color.BLUE,kontroll, new Decay(kontroll) );
                    releaseKnob = new SynthKnob(0,Color.GRAY,Color.BLUE,kontroll, new Release(kontroll) );
                    sustainKnob  = new SynthKnob(0,Color.GRAY,Color.BLUE,kontroll, new Sustain(kontroll) );

                    //filterKnob = new SynthKnob(0, Color.RED, Color.BLUE, kontroll, new Filter(kontroll));
                

                    parameterPanel.add(volumKnob);
parameterPanel.add(attackKnob);
parameterPanel.add(decayKnob);                    
parameterPanel.add(releaseKnob);                
parameterPanel.add(sustainKnob);

//parameterPanel.add(filterKnob);


                    hovedPanel.add(keyPanel, BorderLayout.SOUTH);
                    hovedPanel.add(parameterPanel, BorderLayout.NORTH);
                    vindu.add(hovedPanel);
                    

                   
    
                    
                    
                    
                    vindu.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e){

                            kontroll.exit();
                            e.getWindow().dispose();
                        }
                        
                    });

                    






                    

                    vindu.pack(); 
                    vindu.setLocationRelativeTo(null);
                    vindu.setVisible(true);

                    



                    
                    System.out.println(keyA);}
                
                


                    

    



                

                  


                    
                    
                    
    //define action listener for exit event (needed for ending protocol)


                    



    


    


    public void addListener(KeyButton pressed, char letter){


        //adds listener to KeyButton object

        int keyCode = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(letter);

        System.out.println("keyCode is "+keyCode);
        String onCall = letter + " on";
        String offCall = letter + " off"; 



        pressed.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, false),onCall);
        
        pressed.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, true), offCall);

        pressed.getActionMap().put(onCall, new anAction(pressed, kontroll, letter));
        pressed.getActionMap().put(offCall, new deAction(pressed, kontroll, letter));
    }



    private void addFiller(JPanel panel, int xPos){

        JPanel filler = new JPanel();




        filler.setPreferredSize(new Dimension(40,40));

        GridBagConstraints layout = new GridBagConstraints();


        layout.ipady = 40;
        layout.gridx = xPos;
        layout.gridy = 1;
        layout.ipadx = 40;


        panel.add(filler, layout);


    }


}





class KeyButton extends JButton{


    /*
    
    
    extension on JButton to define certain special behaviour 
    
    
    */

    
    static int number = 0;

    private int xId;

    Color color;
    GridBagConstraints layout;


    char key;




    KeyButton(boolean isBlack, JPanel panel, char keyPress, SynthGui gui){

        xId = number;

        this.key = keyPress;
        



        

        
        
        
        //specific layout for GBC layout manager

        layout = new GridBagConstraints();



        layout.fill = GridBagConstraints.CENTER;
        layout.ipady = 40;
        layout.gridx = xId;
        layout.ipadx = 40;

        


        if (isBlack){ color = Color.BLACK; 
            
            layout.gridy = 0; //sets top row for black keys
        }
        else{color = Color.WHITE;

            
            layout.gridy = 1; //bottom row for white keys
            
            
            //set extra space for better positioning in relation to black notes
            number++;
        }


        setBackground(color);



        //add to GUI panel
        panel.add(this, layout);
        
        gui.addListener(this, key);

        number++;

        


    }

    public Color getColor(){return color;}

    public int getPos(){return xId;}


}
class anAction extends AbstractAction{


    /*
     * Handler class for button down
     * 
     * 
     */


     char id;


    static int created = 0;

    protected int noteNumb;


    //handles type actions


    JButton pressed;

    SynthKontroll kontroller;

    public anAction(KeyButton pressed, SynthKontroll kontroller, char id){

        this.id = id;

        System.out.println("constrcuted : " + created);

        noteNumb = created + 59; 

        this.pressed = pressed;

        created++;

        this.kontroller = kontroller;


    }


    public void actionPerformed(ActionEvent e){


        //specifies down action


        kontroller.noteStart(noteNumb, id);


       

        pressed.setBackground(Color.BLUE);
        pressed.doClick();

        
        
        
        System.out.println("press");
    }

}



class deAction extends AbstractAction{


    /*
     * Handler class for button release
     * 
     * 
     * 
     */


    //handles release actions

    KeyButton pressed;
    SynthKontroll kontroller;

    char id;


    public deAction(KeyButton pressed, SynthKontroll kontroller, char id){

        this.id = id;


        this.pressed = pressed;
        this.kontroller = kontroller;

    }


    public void actionPerformed(ActionEvent e){


        

        pressed.setBackground(pressed.getColor());

        kontroller.noteEnd(id);
        
        System.out.println("'release'");
        

    }

}







 