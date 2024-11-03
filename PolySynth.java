
import com.jsyn.*;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.swing.*;
import com.jsyn.unitgen.*;


import javax.sound.midi.Instrument;

class PolySynth extends Notes{

    Synthesizer synth;
    
    
    //Synthesizer Settings
    double volum;

    int oscCounter;
    //holds oscillator objects and manipilates them

    Oscillator[] oscList = new Oscillator[8];

    LineOut line;

    ADSR envelope;

    FilterFourPoles filter;


    //needs implementation
    PassThrough mixer;

    PolySynth(){
        synth = JSyn.createSynthesizer();
        oscCounter = 0;




        envelope = new ADSR();
        line = new LineOut();

        filter = new FilterFourPoles();


        synth.add(line);

        synth.add(filter);


        


        


        synth.start();

        line.start();

        filter.output.connect(line);


        changeVolum(0.5);


        //filter.frequency.setup(40.0, 400.0, 6000.0);

       
    }




    //Filter Knob Setup
    public void setFilter(double newRange){

        filter.frequency.setup(newRange - 100, newRange + 100, newRange);

        filter.frequency.setDefault(newRange);


        filter.frequency.set(newRange);



    }
    
    private int polyManager(){
        
        //checks array of notes and decides which is next open array

        int activeOsc = 0;


        for (Oscillator x: oscList ){

            
            

            // case if osc is off or not activated
            if ( x == null || !x.isOn){return activeOsc;}
            //check if next available osc 
            else if (activeOsc >= 7){return 0;}
            else{activeOsc++;}
        }
        return activeOsc;


}


    public void setNull(int id){oscList[id] = null;}


    
/*
 * 
 * Controller methods
 * 
 * 
 * 
 */
    public void noteDown(int noteNumb, char character){


            /*main public method takes in note and produces new sound

            */


       try 
       
       { boolean created = false;

          for (Oscillator x: oscList){
            //null check
              if (x != null )
                   {
                      if(x.isOn && character == x.getId()){created = true;}}}       //checks if similar oscillator already created

        
       if(!created){ 

        int assignedId = polyManager(); // temporarily keeps track of list
        
        oscList[assignedId] = new Oscillator(this, notes[noteNumb], character, assignedId, volum);}}

        catch(Exception e){System.out.println(e);}

    }

    


    public void noteUp(char character){

        Oscillator turnOff = oscList[0];

        for(Oscillator x: oscList){
            
            
            if (x != null) //check if there is object
            {if(x.getId() == character){turnOff = x;}}
        }

        System.out.println(turnOff.toString());
        turnOff.stop();

} 
public void changeVolum(double newVolume){
    volum = newVolume;
}

public void end(){line.stop();synth.stop();}

}


class ADSR{


    ADSR()
    
    {setAttack(0.2);

    setDecay(0.3);
   setSustain(0.2);
   setRelease(0.2); 
    }

    public double attack;


    public double decay;

    public double sustain; 

    public double release;



    
    
    public void setAttack(double attack){this.attack = attack;}
public void setDecay(double  param){this.decay = param;}
public void setSustain(double param){this.sustain = param;}
public void setRelease(double param){this.release = param;}    

}

class Oscillator{

    char id;
    int listID;

    PolySynth parent;

    Synthesizer synth;

    float frequency;

    public boolean isOn = true;

    LineOut lineOut;

    UnitOscillator osc;

    UnitGenerator myFilter;

    LinearRamp lag;
    





    Oscillator( PolySynth parent, Note note, char id, int listID, double amp){

        lineOut = new LineOut();
        

        

        this.id = id;
        this.listID = listID;

        this.parent = parent;
        
        this.frequency = note.getFrequency();
        this.synth = parent.synth;
        osc = new SineOscillator();

        



        



        /*
         * 
         * 
         * Parameter Class? 
         * 
         * Solution to how to organise ADSR envelope, filter and Volume evt effect controls
         */

        System.out.println("test method for changing synth parameter" + amp);

        /*  SynthethisProtocol  
         * 
         * 
         * 
         */
        
            //new oscilattor / Linout

        synth.add(lineOut);
        synth.add(osc);


       
        
            //new linear envelope
        synth.add(lag = new LinearRamp());

       

        



            //Routing
       // osc.getOutput().connect(0,adsr.input,0);



       
        


    

        


            //repeat to try stereo 


        //osc.getOutput().connect(0,lineOut.input , 1);


        /*Linear envelope  setup
         * 
         * similare to line~ object in MAX msp 
         * 
         */



    

         

         osc.output.connect(parent.filter.input);

         

         osc.output.connect(0, lineOut.input, 0);

         osc.output.connect(0, lineOut.input, 1);

        



         lag.output.connect(osc.amplitude);
         lag.output.connect(parent.filter.frequency);




         //Linear amp setup


         /*Params for Linerar ramp:

         Minimum, maximum, default
         
         
         */

         lag.input.setup(0.0,amp,parent.envelope.sustain);

         


         //Gets parameter from parent adsr class
         lag.time.set(parent.envelope.attack);


         

       
         

        /*lag.output.connect(osc.amplitude);
        lag.input.setup(0.0, amp,1.0);
        lag.time.set(0.075);
*/

        start(frequency);
        //lineOut.start();

    }

    public char getId(){return id;}


   public void start(float frequency){




    

    osc.frequency.set(frequency);

    lineOut.start();

    lag.start();


    System.out.println("Frequency is" + frequency);

    
    //adsr.generate();



   }

   public void stop(){
    
    lag.input.setup(0.0,0.0,0.0);

    lag.time.set(parent.envelope.release);



    this.isOn = false;
    parent.setNull(listID);
}

public void turnOff(){
    //lineOut.stop();
    isOn = false;}
}




