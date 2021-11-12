import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/*
* @Author : Oliver Mclane
* This Tone Project will take a text in via args and read the sheet music.
* It will convert the string to enumerator values. The members are assigned a note
* and the thread starts, they are called by the conductor storing the note order and length. 
*/
public class Tone {

    // memberList and my conductor created
    private static MemberList memberL = new MemberList();
    private static Conductor johnWilliam = new Conductor();

    public static void main(String[] args) throws Exception {
        String Line;
        File songTxt = new File("MaryLamb.txt");
        BufferedReader br = new BufferedReader(new FileReader(songTxt));
        
        while ((Line = br.readLine()) != null){
            // split textfile into strings and notes
            String[] split = Line.split(" ", 2);
            String noteSymbol = split[0];
            String noteLen = split[1];
            
            // checks valid note symbol
            if(!doesExist(noteSymbol)){
                System.err.println("Invalid File");
                System.exit(-1);
            }

            // checks valid note length
            if(noteLen.equals("4")){
                noteLen= "QUARTER";
            }else if(noteLen.equals("8")){
                noteLen = "EIGTH";
            }else if(noteLen.equals("2")){
                noteLen = "HALF";
            }else if(noteLen.equals("1")){
                noteLen = "WHOLE";
            }else{
                System.err.println("Invalid File");
                System.exit(-1);
            }

            // adding them to the conductor
            johnWilliam.addNoteSym(noteSymbol);
            johnWilliam.addNoteLen(noteLen);

            // adds a new member with a note
            if(!memberL.listContain(noteSymbol)){
                memberL.add(new Member(noteSymbol));
            }

        }

        // closes file
        br.close();
        
        // creates audio file
        final AudioFormat af =
            new AudioFormat(BellNote.Note.SAMPLE_RATE, 8, 1, true, false);
        Tone t = new Tone(af);

        // play song
        t.playSong();

        // closes out my threads
        memberL.endThreads();
    }

    private final AudioFormat af;

    Tone(AudioFormat af) {
        this.af = af;
    }

    // plays the song
    void playSong() throws LineUnavailableException {
        try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
            line.open();
            line.start();
            // using the remove function so it continues to shirk list
            int counter = johnWilliam.getLength();
            for(int i = 0; i < counter; i+=1) {
                String s1 = johnWilliam.getNoteSym();
                String s2 = johnWilliam.getNoteLen();
                Member placer = memberL.get(s1);
                playNote(line, placer.play(s2));
            }
            line.drain();
        }

    }
    
    // plays the individual note
    public void playNote(SourceDataLine line, BellNote bn) {
        final int ms = Math.min(bn.length.timeMs(), BellNote.Note.MEASURE_LENGTH_SEC * 1000);
        final int length = BellNote.Note.SAMPLE_RATE * ms / 1000;
        line.write(bn.note.sample(), 0, length);
        line.write(BellNote.Note.REST.sample(), 0, 1000);
    }

    // check for note symbol existing
    public static boolean doesExist(String s) {
        if(s.equals(" ")){
            return false;
        }
        for(BellNote.Note n: BellNote.Note.values()){
            if(s.equals(n.name())){
                return true;
            }
        }
        return false;
    }
}