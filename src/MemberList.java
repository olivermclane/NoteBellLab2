import java.util.ArrayList;
import java.util.List;

//keeps track of the current members
public class MemberList {
        private final List<Member> members;
        private int length = 0;

        MemberList(){
                members = new ArrayList<Member>();
        }

        //adds a member to the list
        public void add(Member b) {
                synchronized (members){
                        System.out.println(b.memberNote);
                        members.add(b);
                        members.notify();
                        length++;
                }
        }

        //searchs for the member of the note
        public Member get(String s) {
                synchronized (members) {
                        while (members.size() == 0) {
                                try {
                                        members.wait();
                                } catch (InterruptedException ignored) {}
                        }
                        for(Member d: members){
                                if(d.memberNote.equals(s)){
                                        return d;
                                }else{
                                        System.err.println("Null/Bug found");
                                        System.exit(-1);
                                }
                        }
                }
                //Will never reach but shall be handled.
                return new Member("Error");
        }

        //returns the length of the list
        public synchronized int ListLength(){
                return length;
        }

        //returns true of the list is empty
        public synchronized boolean isEmpty(){
                return members.size() == 0;
        }

        //returns if a note has already been assigned
        public synchronized boolean listContain(String l){
                for(int i = 0; i< length; i++ ){
                        if(members.get(i).getMName().equals(l)){
                                return true;
                        }
                }
                return false;
        }

        //ends all my threads
        public void endThreads(){
                for(Member d: members){
                        d.notesDone();
                }
        }
}