import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer extends PlaybackListener {
    private Song currentSong;

    private AdvancedPlayer advancedPlayer;

    private boolean isPaused;

    private int currentFrame;
    public MusicPlayer(){

    }

    public void loadSong(Song song){
        currentSong = song;

        if(currentSong != null){
            playCurrentSong();
        }
    }

    public void  pauseSong(){
        if(advancedPlayer != null){
            // update isPaused
            isPaused = true;

            // stops the player
            stopSong();
        }
    }

    public void stopSong(){
        if (advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void playCurrentSong(){
        if(currentSong == null) return;
        try {
            // read mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            // create advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

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
                    if (isPaused){
                        // resume music from last frame
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);

                    }else {
                        // play music
                        advancedPlayer.play();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {

        System.out.println("Playback Started");
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Playback Finished");

        if (isPaused){
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
        }
    }
}
