import java.util.ArrayList;
import java.util.List;

public class SongList {
        private final List<BellNote> currentSong;

        ReaderBlockList(){
                oranges = new ArrayList<Orange>();
        }

        public void add(BellNote b) {
                synchronized (currentSong){
                        currentSong.add(b);
                        currentSong.notify();
                }
        }

        public Orange get() {
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