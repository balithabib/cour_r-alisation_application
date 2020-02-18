/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class.
 * 
 *  This main class creates the necessary implementation objects and starts the game off.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2.0 (Jan 2003)
 */
import src.pkg_interface.*;

public class Game
{
    private UserInterface gui;
    private GameEngine engine;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        engine = new GameEngine();
        gui = new UserInterface(engine);
    }

    /**
     * Methode paly
     */
    public void play(){
        engine.setGUI(gui);
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.play();  
    }
}