import java.util.ArrayList;
import java.util.List;

// keeps track of all the notes timing and order
public class Conductor {
    private List<String> noteOrder = new ArrayList<>();
    private List<String> noteLenOrder = new ArrayList<>(); 

    Conductor() {

    }
    
    // adds note symbol to list
    public void addNoteSym(String s) {
        noteOrder.add(s);
    }

    // adds the notelength for each 
    public void addNoteLen(String s) {
        noteLenOrder.add(s);
    }

    // retunrs the first entry of the Note Order list
    public String getNoteSym() {
        return noteOrder.remove(0);
    }

    // returns the first entry from the Note Length list
    public String getNoteLen() {
        return noteLenOrder.remove(0);
    }

    // returns total notes in song (only need one cause they both should be the same length) 
    public int getLength() {
        return noteLenOrder.size();

    }
    
}
