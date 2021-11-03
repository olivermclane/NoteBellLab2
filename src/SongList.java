import java.util.ArrayList;
import java.util.List;

public class SongList {
        private final List<BellNote> currentSong;

        SongList(){
                currentSong = new ArrayList<BellNote>();
        }

        public void add(BellNote b) {
                synchronized (currentSong){
                        currentSong.add(b);
                        currentSong.notify();
                }
        }

        public BellNote get() {
                synchronized (currentSong) {
                        while (currentSong.size() == 0) {
                                try {
                                        currentSong.wait();
                                } catch (InterruptedException ignored) {}
                        }
                       return currentSong.remove(0);
                }
        }

        public synchronized boolean isEmpty(){
                return currentSong.size() == 0;
        }
}