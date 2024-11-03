
import java.util.Scanner; 

class UnitSynth{


    SynthDef oscillator; 
    Notes n;


    UnitSynth(){



        //Notes file

        n = new Notes();


        //Sound engine
        oscillator = new SynthDef();



        //dummy routine for writing out frequency. (temporary)
        //for (int x = 0; x<1000;x++){System.out.println(n.notes[x].toString());}







        }


        //play note (on keypress)
        public void playNote(int noteNumb){


            int note2 = noteNumb + 1;
            
            //sets frequency
           try { oscillator.getOsc().frequency.set(n.notes[noteNumb].getFrequency());
            oscillator.getOsc2().frequency.set(n.notes[note2].getFrequency());}
            catch(Exception e){System.out.println(e);}

        oscillator.playNote();}


        //stop note (on key release)

    public void stopNote(){oscillator.stopNote();}
        

        //end osc
        //OBS!!! OBSS MUST BE CALLED AT END OF PROCESS 




        public void endSession(){oscillator.end();}
    
        




    
}


class Notes {



    String[] pitchNames = new String[12];

    public Note[] notes = new Note[1200]; 


    Notes(){

        pitchNames[0] = "C";
        pitchNames[1] = "Db";
        pitchNames[2] ="D";
        pitchNames[3] = "Eb";
        pitchNames[4] = "E";
        pitchNames[5] = "F";
        pitchNames[6] = "Gb";
        pitchNames[7] = "G";
        pitchNames[8] = "Ab";
        pitchNames[9] = "A";
        pitchNames[10] = "Bb";
        pitchNames[11] = "B";  




        //create pitches for a hundred octaves




        notes[0] = new Note(0,"C",(float)8.175799);



        int key = 0; 





        for (int octave = 0; octave < 100; octave++){


            for (int p = 0; p < 12; p++){
                if(octave == 0 && p == 0){;}else{



                    //do the math
                    // previousFrequency * 2 ^ 1/12

                    float nextFreq = notes[key-1].getFrequency() * (float)1.059463094359295;
                    
                    
                    String pitch = pitchNames[p];


                     notes[key] = new Note(octave,pitch, nextFreq);

                    
                    
                    
                    
                    



                } 

                key++; 

            } }
    }       

}
/*  

class ChromaticScale extends notes{


    int numberOfNotes; 
    int[] structure;
    


    ChromaticScale() {

        numberOfNotes = 12;

       structure = new int[numberOfNotes];



        //structure is number of semitones till next note

       structure[0] = 1;
       structure[1] = 1;
       structure[2] = 1;
       structure[3] = 1;
       structure[4] = 1;
       structure[5] = 1;
       structure[6] = 1;
       structure[7] = 1;
       structure[8] = 1;
       structure[9] = 1;
       structure[10] = 1;
       structure[11] = 1;



    }


    public void contains(){


        for (int x = 0; x < numberOfNotes; x++){





        }

    }


    public String getNextNote(String note){

        int noteNumber = 0;


        //find note







    }



    



    //Spesifisere skala 



    public boolean isIn(String note){

        if



    }



}


*/

class Note{

    int octave; 

    String pitch;

    float frequency;
    
    
    
    public Note(int octave, String pitch, float frequency){

        this.octave = octave; this.pitch = pitch; this.frequency = frequency; 
    }

    public float getFrequency(){return frequency;}




    @Override
    public String toString(){

        return "" + pitch + octave + " : " + frequency ;
    }




}






