import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.*;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003)
 */
public class UserInterface implements ActionListener
{
    private GameEngine engine;
    private JFrame myFrame;
    private JTextField entryField;
    private JTextArea log;
    private JLabel image;
    private Button buttonLook, buttonNorth, buttonEast, buttonSouth, buttonWest, buttonUp, buttonDown, buttonBack, buttonItems;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param gameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface(GameEngine gameEngine)
    {
        engine = gameEngine;
        createGUI();
    }

    /**
     * Print out some text into the text area.
     */
    public void print(String text)
    {
        log.append(text);
        log.setCaretPosition(log.getDocument().getLength());
    }

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println(String text)
    {
        log.append(text + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }
     

    public void clear()
    {
        log.setText("");
    }
    


    /**
     * Show an image file in the interface.
     */
    public void showImage(String imageName)
    {
        URL imageURL = this.getClass().getClassLoader().getResource(imageName);
        if(imageURL == null)
            System.out.println("image not found");
        else {
            ImageIcon icon = new ImageIcon(imageURL);
            image.setIcon(icon);
            myFrame.pack();
        }
    }

    /**
     * Enable or disable input in the input field.
     */
    public void enable(boolean on)
    {
        entryField.setEditable(on);
        if(!on)
            entryField.getCaret().setBlinkRate(0);
    }

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        myFrame = new JFrame(" la cité des pharaons.");
        entryField = new JTextField(34);
        buttonLook = new Button("look");
        buttonNorth = new Button("go north");
        buttonEast = new Button("go east");
        buttonSouth = new Button("go south");
        buttonWest = new Button("go west");
        buttonUp = new Button("go up");
        buttonDown = new Button("go down");
        buttonBack = new Button("back");
        buttonItems = new Button("items");
        log = new JTextArea();
        log.setEditable(false);
        JScrollPane listScroller = new JScrollPane(log);
        listScroller.setPreferredSize(new Dimension(200, 200));
        listScroller.setMinimumSize(new Dimension(100,100));

        JPanel panel = new JPanel();
        JPanel panelBis = new JPanel();
        panelBis.setLayout(new GridLayout(3, 3));
        panelBis.add(buttonUp);
        panelBis.add(buttonNorth);
        panelBis.add(buttonItems);
        panelBis.add(buttonWest);
        panelBis.add(buttonLook);
        panelBis.add(buttonEast);
        panelBis.add(buttonBack);
        panelBis.add(buttonSouth);
        panelBis.add(buttonDown);
        
        image = new JLabel();

        panel.setLayout(new BorderLayout());
        panel.add(image, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(entryField, BorderLayout.SOUTH);
        panel.add(panelBis,BorderLayout.EAST);
        myFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // add some event listeners to some components
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        entryField.addActionListener(this);
        buttonUp.addActionListener(this);
        buttonNorth.addActionListener(this);
        buttonWest.addActionListener(this);
        buttonLook.addActionListener(this);
        buttonEast.addActionListener(this);
        buttonDown.addActionListener(this);
        buttonSouth.addActionListener(this);
        buttonBack.addActionListener(this);
        buttonItems.addActionListener(this);
        myFrame.pack();
        myFrame.setVisible(true);
        entryField.requestFocus();
    }

    /**
     * Actionlistener interface for entry textfield.
     */
    public void actionPerformed(ActionEvent e) 
    {
        // no need to check the type of action at the moment.
        // there is only one possible action: text entry
        String composant = e.getActionCommand();

        processCommand(composant);
    }

    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     */
    private void processCommand(String composant)
    {
        String input = composant;
        
        boolean finished = false;

        if(finished == true){
            input = entryField.getText();
            entryField.setText("");
        }
        engine.interpretCommand(input);
    }
}