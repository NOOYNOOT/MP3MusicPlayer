import javax.swing.*;

public class MusicPlayerGUI extends JFrame {
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

    addGuiComponents();
    }

    private void addGuiComponents(){
        // add toolbar
        addToolbar();
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
}
