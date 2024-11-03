import com.jsyn.*;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.swing.*;
import com.jsyn.unitgen.*;

import javax.sound.midi.Instrument;


class SynthDef{


     
    Synthesizer synth;


    LineOut lineOut;

    LineOut lineOut2;

    UnitOscillator osc;

    UnitOscillator osc2;

    UnitGenerator myFilter;

    LinearRamp lag;






    SynthDef(){

         synth = JSyn.createSynthesizer();

        //initializes synth
        synth.start();

        //oscillator

        synth.add(osc = new SineOscillator());

        synth.add(osc2 = new SineOscillator());

        //Linear Envelope

        synth.add(lag = new LinearRamp());


        //output mixer
        synth.add(lineOut = new LineOut());

        synth.add(lineOut2 = new LineOut());

        

        osc.getOutput().connect(0,lineOut.input, 0);

        osc2.getOutput().connect(0,lineOut2.input, 0);

        ///linear envelope ramp set
        lag.output.connect(osc.amplitude);
        lag.output.connect(osc2.amplitude); 
        lag.input.setup(0.0,0.5,1.0);
        lag.time.set(0.075);

      

        synth.start();
    

    }

   





    
    


        




        
    public void playNote(){   


    //spiller tone  

    lineOut.start();

    lineOut2.start();

}


public UnitOscillator getOsc(){return osc;}

public UnitOscillator getOsc2(){return osc2;}


public void stopNote(){


    //stopper tone
    lineOut.stop();
    lineOut2.stop();


}


public void end(){


    synth.stop();
}


}