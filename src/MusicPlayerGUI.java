import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {

    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private MusicPlayer musicPlayer;

    private JFileChooser jFileChooser;

    private JLabel songTitle, songArtist;
    private JPanel playBackBtns;
    public MusicPlayerGUI(){
        // calls JFrame constructor to configure gui and set header title to "Music Player"
    super("Music Player");

    // set width and height
    setSize(400, 600);

    // end process when app is closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // launch the app at the center of the screen
    setLocationRelativeTo(null);

    // prevent the app from being resized
    setResizable(false);

    // set layout to null witch will allow us to control the (x, y) coordinates of our components
    // and also set the height and width
    setLayout(null);

    getContentPane().setBackground(FRAME_COLOR);

    musicPlayer = new MusicPlayer();
    jFileChooser = new JFileChooser();

    jFileChooser.setCurrentDirectory(new File("src/assets"));

    // filter file chooser to only show mp3 files
    jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
    addGuiComponents();
    }

    private void addGuiComponents(){
        // add toolbar
        addToolbar();

        // load record image
        JLabel songImage = new JLabel(loadImage("src/assets/recc.jpg"));
        songImage.setBounds(0, 50, getWidth() - 20, 225);
        add(songImage);

        // Show song title
        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        // show song artist
        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 315, getWidth() - 10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        // Song slider
        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth()/2 - 300/2, 365, 300, 40);
        playbackSlider.setBackground(null);
        add(playbackSlider);

        // Buttons (i.e Back, Play, Next)
        addPlayBackBtns();
    }

    private void addToolbar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);

        // prevent toolbar from being moved
        toolBar.setFloatable(false);

        // add drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // add a song menu where the loading song option will be
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        // adding the "load song" item in the songMenu
        JMenuItem loadSong = new JMenuItem("Load Song");
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){
                    Song song = new Song(selectedFile.getPath());

                    musicPlayer.loadSong(song);

                    // update song artist and title
                    updateSongTitleAndArtist(song);

                    enablePauseButtonDisablePlayButton();
                }
            }
        });
        songMenu.add(loadSong);
        // here will we add the playlist menu
        JMenu playlist = new JMenu("Playlist");
        menuBar.add(playlist);

        // new we will add the create the items to the playlist menu
        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        playlist.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlist.add(loadPlaylist);

        add(toolBar);
    }

    private void addPlayBackBtns(){
        playBackBtns = new JPanel();
        playBackBtns.setBounds(0, 435, getWidth() - 10, 80);
        playBackBtns.setBackground(null);

        // back button
        JButton prevButton = new JButton(loadImage("src/assets/previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playBackBtns.add(prevButton);

        // play button
        JButton playButton = new JButton(loadImage("src/assets/play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePauseButtonDisablePlayButton();
                // play or resume a song
                musicPlayer.playCurrentSong();
            }
        });
        playBackBtns.add(playButton);

        // pause button
        JButton pauseButton = new JButton(loadImage("src/assets/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePlayButtonDisablePauseButton();
                // pause the song
                musicPlayer.pauseSong();
            }
        });
        playBackBtns.add(pauseButton);

        // next button
        JButton nextButton = new JButton(loadImage("src/assets/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playBackBtns.add(nextButton);

        add(playBackBtns);
    }

    private void updateSongTitleAndArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void enablePauseButtonDisablePlayButton(){

        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        // turn off play button
        playButton.setVisible(false);
        playButton.setEnabled(false);

        // turn on pause button
        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    private void enablePlayButtonDisablePauseButton(){

        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        // turn on play button
        playButton.setVisible(true);
        playButton.setEnabled(true);

        // turn off pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    private ImageIcon loadImage(String imagePath){
        try {
            // read the image file from the image path
            BufferedImage image = ImageIO.read(new File(imagePath));

            // return image icon so image can render
            return new ImageIcon(image);
        }catch (Exception e){
            e.printStackTrace();
        }
        // resource could not be found
        return null;
    }
}
