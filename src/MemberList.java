import java.util.HashMap;
import java.util.Map;

// keeps track of the current members
public class MemberList {
        private final Map<String,Member> members;
        private int length = 0;

        MemberList(){
                members = new HashMap<String, Member>(14);
        }

        // adds a member to the list
        public void add(Member b) {
                synchronized (members){
                        members.put(b.memberNote, b);
                        members.notify();
                        length++;
                }
        }

        // searches for the member of the note
        public Member get(String s) {
                synchronized (members) {
                        while (members.size() == 0) {
                                try {
                                        members.wait();
                                } catch (InterruptedException ignored) {}
                        }
                        if(members.containsKey(s)){
                               return members.get(s);
                        }else{
                                System.err.println("System bug found -- Possible Null");
                                System.exit(-1);
                        }
                }
                // Will never reach but shall be handled.
                return new Member("Error");
        }

        // returns true of the list is empty
        public synchronized boolean isEmpty(){
                return members.size() == 0;
        }

        // returns if a note has already been assigned
        public synchronized boolean listContain(String l){
                for(int i = 0; i< length; i++ ){
                        if(members.containsKey(l)){
                                return true;
                        }
                }
                return false;
        }

        // ends all my threads
        public void endThreads(){
                for(Map.Entry<String, Member> entry: members.entrySet()){
                        entry.getValue().notesDone();
                }
        }
}