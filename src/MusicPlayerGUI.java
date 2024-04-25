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
        add(toolBar);
    }
}
