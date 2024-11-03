class SynthKontroll{


    SynthGui gui;

    PolySynth lyd;
    


    public void start(){

        gui = new SynthGui(this); 
        lyd = new PolySynth();

    }


    public void changeVolume(double volum){

        lyd.changeVolum(volum);

    }


    public void changeAttack(double param){

        lyd.envelope.setAttack(param);


    }

    public void changeDecay(double param){

        lyd.envelope.setDecay(param);


    }


    public void changeRelease(double param){

        lyd.envelope.setRelease(param);


    }


    public void changeSustain(double param){

        lyd.envelope.setSustain(param);

    }


    


    public void noteStart(int noteNumb, char character){lyd.noteDown(noteNumb, character);}
    public void noteEnd(char id){lyd.noteUp(id);}
    public void exit(){lyd.end();}



}





abstract class Parameter{

    public String name;

    public double value;

    SynthKontroll kontroll;

    Parameter(SynthKontroll kontroll){
        
        this.kontroll = kontroll;
    
    
    }



    public void changeValue(double newValue){

        value = newValue;
    }
    
}


class Volume extends Parameter{


    Volume(SynthKontroll kontroll){

        super(kontroll);

       
        name = "volume";

        /*
         * 
         * 
         * Should find way to initialize parameter as following:
         */
        //changeValue(0.5);
}


@Override
public void changeValue(double newValue){kontroll.changeVolume(newValue);}


}


class Filter extends Parameter{


    Filter(SynthKontroll kontroll){

        super(kontroll);
    }

    @Override 
    public void changeValue(double newValue){

        newValue = newValue * 12000.0; 

        System.out.println("Filter value : " + newValue);

        kontroll.lyd.setFilter(newValue);

    }
}


class Attack extends Parameter{

    Attack(SynthKontroll kontroll){

        super(kontroll);
    }


    @Override
    public void changeValue(double newValue) {

        kontroll.changeAttack(newValue);
        
    }

}


class Decay extends Parameter{

    Decay(SynthKontroll kontroll){

        super(kontroll);
    }

    @Override
    public void changeValue(double newValue){

        kontroll.changeDecay(newValue);
    }

}



class Sustain extends Parameter{

    Sustain(SynthKontroll kontroll){

        super(kontroll);
    }

    @Override
    public void changeValue(double newValue){

        kontroll.changeSustain(newValue * 10);
    }

}


class Release extends Parameter{

    Release(SynthKontroll kontroll){

        super(kontroll);
    }

    @Override
    public void changeValue(double newValue){

        kontroll.changeRelease(newValue);
    }

}