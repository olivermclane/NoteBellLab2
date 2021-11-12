// hold the note and starts a thread for the note
public class Member extends Thread{
    public String memberNote;
    public Thread memberThread = new Thread();
    
    // Constructor for member
    Member(String b){
        memberThread.start();
        memberNote = b;
        Thread.currentThread().setName(b);
    }

    // returns note symbol assigned to member
    public String getMName(){
        return memberNote;
    }

    // returns the bell note the member plays
    public BellNote play(String len){
        BellNote.Note NoteConversion;
        BellNote.NoteLength NoteLenConversion;
        NoteConversion = Enum.valueOf(BellNote.Note.class, memberNote);
        NoteLenConversion = Enum.valueOf(BellNote.NoteLength.class, len);
        return new BellNote(NoteConversion, NoteLenConversion);
    }

    // closes the thread for the member
    public void notesDone(){
        try {
            System.out.println("Stopping thread -- Note "+ memberNote);
            memberThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}