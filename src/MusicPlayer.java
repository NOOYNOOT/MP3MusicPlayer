import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer {
    private Song currentSong;

    private AdvancedPlayer advancedPlayer;

    public MusicPlayer(){

    }

    public void loadSong(Song song){
        currentSong = song;

        if(currentSong != null){
            playCurrentSong();
        }
    }

    public void playCurrentSong(){
        try {
            // read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            // create advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);

            // start music
            startMusicThread();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void  startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // play music
                    advancedPlayer.play();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
