import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer extends PlaybackListener {

    private static final Object playSignal = new Object();

    // need reference so that we can update the GUI from this class

    private MusicPlayerGUI musicPlayerGUI;

    private Song currentSong;

    public Song getCurrentSong(){
        return currentSong;
    }

    private AdvancedPlayer advancedPlayer;

    private boolean isPaused;

    private int currentFrame;

    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }

    private int currentTimeInMilli;

    public void setCurrentTimeInMilli(int timeInMilli){
        currentTimeInMilli = timeInMilli;
    }

    public MusicPlayer(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
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

            // start playback slider
            startPlaybackSliderThread();

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
                        synchronized(playSignal){
                            // update flag
                            isPaused = false;

                            playSignal.notify();
                        }

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

    private void startPlaybackSliderThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isPaused){
                    try {
                        synchronized (playSignal){
                            playSignal.wait();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                while(!isPaused){
                    try {
                        currentTimeInMilli++;

                       // System.out.println(currentTimeInMilli * 2.08);

                        // calculate into frame values
                        int calculatedFrame = (int) ((double) currentTimeInMilli * 2.08 * currentSong.getFrameRatePerMilliseconds());

                        // update GUI
                        musicPlayerGUI.setPlaybackSliderValue(calculatedFrame);

                        // mimic 1 millisecond using thread.sleep
                        Thread.sleep(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
