import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {

    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;
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
        JLabel songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        // show song artist
        JLabel songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 315, getWidth() - 10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);
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
