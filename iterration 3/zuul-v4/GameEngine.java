import java.util.HashMap;
import java.util.Stack; 
import java.util.Scanner; 
import java.io.*; 
import java.util.*;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class GameEngine {
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> backRoom;
    private Player currentPlayer;
    private UserInterface gui;
    private HashMap<String, Room> rooms;
    final private String roomsData[][] = { 
            { "outside", "Vous etes à l'entrée de la cité des pharaons...", "cite.gif" },
            { "palace", "Vous à l'entrée de palais royale...","palais/palace.gif"}, 
            { "pyramid", "Vous etes à l'entrée de la pyramid...", "pyramid/pyramid.gif"}, 
            { "temple", "Vous etes à l'entrée du temple...", "temple/temple.gif"}, 
            { "courtyardPalace",  "Vous etes dans la cour de palais...", "palais/courtyardPalace.gif"}, 
            { "throneRoom", "Vous etes dans la salle du trone...", "palais/throneRoom.gif"},
            { "privateAppartements", "Vous etes dans les appartements privées !!!!!", "pyramid/secreteRoom2.gif"}, 
            { "kingRoom", "Vous etes dans la chambre de roi...", "pyramid/kingRoom.gif"}, 
            { "queenRoom", "Vous etes dans la chambre de la reine...", "pyramid/queenRoom.gif"}, 
            { "gallery", "Vous etes dans la gallerie...", "pyramid/gallery.gif"}, 
            { "secretRoom", "Vous etes dans la chambre secret...", "pyramid/secretRoom2.gif"},
            { "goldenRoom", "Vous etes dans la chambre du trésor...", "pyramid/goldenRoom.gif"}, 
            { "courtyardTemple", "Vous etes dans la cour du temple...", "temple/courtyardTemple.gif"}, 
            { "sacrificiesRoom", "Vous etes dans la salle des sacrifices !!!!!!", "temple/sacrificiesRoom.gif"}, 
            { "sanctuary", "Vous etes dans le sanctuaire...", "temple/sanctuary.gif"}, 
            { "wow", "Téléport", "palais/secretRoom1.gif"} 
        };
    /**
     * , C,reate the game and initialise its internal map.
     */
    public GameEngine() {
        rooms = new HashMap<String, Room>();
        backRoom = new Stack<Room>();
        createRooms();
        parser = new Parser();
        currentPlayer = new Player("Habib");
    }

    public void setGUI(UserInterface userInterface) {
        gui = userInterface;
        printWelcome();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    { 

        for (String[] d : roomsData) {
            rooms.put(d[0], new Room(d[1], d[2]));
        }
        
        rooms.get("outside").setExits(null, rooms.get("temple"), rooms.get("palace"), rooms.get("pyramid"),null,null);
        rooms.get("palace").setExits(rooms.get("outside"), null, rooms.get("courtyardPalace"), null,null,null);
        rooms.get("courtyardPalace").setExits(rooms.get("palace"), null, rooms.get("throneRoom"), null,null,null);
        rooms.get("throneRoom").setExits(rooms.get("courtyardPalace"), rooms.get("wow"), rooms.get("privateAppartements"), null,null,null);
        rooms.get("privateAppartements").setExits("north",rooms.get("throneRoom"));
        rooms.get("wow").setExits("north", rooms.get("outside"));
        
        rooms.get("temple").setExits(null, rooms.get("courtyardTemple"), null, rooms.get("outside"),null,null);
        rooms.get("courtyardTemple").setExits(null, rooms.get("sacrificiesRoom"), null, rooms.get("temple"),null,null);
        rooms.get("sacrificiesRoom").setExits(null, rooms.get("sanctuary"), null, rooms.get("courtyardTemple"),null,null);
        rooms.get("sanctuary").setExits("west", rooms.get("sacrificiesRoom"));

        rooms.get("pyramid").setExits(rooms.get("gallery"), rooms.get("outside"), null, rooms.get("queenRoom"),null,rooms.get("secretRoom"));
        rooms.get("secretRoom").setExits(null, null, null, null,rooms.get("pyramid"),null);
        rooms.get("queenRoom").setExits("east", rooms.get("pyramid"));
        rooms.get("gallery").setExits(null, null, rooms.get("pyramid"), null,rooms.get("kingRoom"),null);
        rooms.get("kingRoom").setExits(null, null,null, null,rooms.get("goldenRoom"),rooms.get("gallery"));
        rooms.get("goldenRoom").setExits("up", rooms.get("sanctuary"));
        currentRoom = rooms.get("outside");
        //backRoom.push(currentRoom);
    }

    /**
     * Main play routine. Loops until end of play.
     */
    /*
     * public void play() { printWelcome();
     * 
     * // Enter the main command loop. Here we repeatedly read commands and //
     * execute them until the game is over.
     * 
     * boolean finished = false; while (! finished) { Command command =
     * parser.getCommand(); finished = processCommand(command); }
     * System.out.println("Thank you for playing.  Good bye."); }
     */

    private void printLocalInfo() {
        gui.println("------------------------------------------------------------------------------");
        gui.println(currentRoom.getDescription());
        gui.println(currentRoom.getExitString().toString());
        gui.println("------------------------------------------------------------------------------");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        //gui.clear();
        gui.print("\n");
        gui.println("Welcome to the World of Zuul!");
        gui.println("World of Zuul is a new, incredibly boring adventure game.");
        gui.println("Type '" + CommandWord.HELP + "' if you need help.");
        // gui.println(currentPlayer.toString());
        gui.print("\n");
        printLocalInfo();
        // gui.println(currentRoom.getLongDescription().toString());
        gui.showImage(currentRoom.getImageName());
    }

    /**
     * Given a command, process (that is: execute) the command. If this command ends
     * the game, true is returned, otherwise false is returned.
     */
    public boolean interpretCommand(String commandLine) {
        //gui.println(commandLine);
        //Command command = parser.getCommand(commandLine);
        boolean wantToQuit = false;
        Command command = parser.getCommand(commandLine);
        CommandWord commandWord = command.getCommandWord();
        gui.clear();

        switch(commandWord){
            case HELP:
                printHelp();
                break;
            case LOOK:
                look();
                break;
            case EAT:
                eat(command);
                break;
            case BACK:
                back(command);
                break;
            case GO:
                goRoom(command);
                break;
            case TEST:
                test(command);
                break;
            case TAKE:
                take(command);
                break;
            case DROP:
                drop(command);
                break;
            case ITEMS:
                items();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;  
            default : {
                gui.println("I don't know what you mean...");
                return false;
            }
        }
        return wantToQuit; 
    }

    // implementations of user commands:

    /**
     * Print out some help information. Here we print some stupid, cryptic message
     * and a list of the command words.
     */
    private void printHelp() {
        gui.println("You are lost. You are alone. You wander");
        gui.println("around at the university.");
        gui.print("\n");
        gui.println("Your command words are:" + parser.showCommands());
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            gui.println("There is no door!");
        } else {
            if (("Vous etes dans la chambre du trésor...".equals(nextRoom.getDescription()))
                    && !currentPlayer.hasCles()) {
                gui.println("ahhe Non il faut avoir 5 cle pour acceder a cette chambre");
                return;
            }
            backRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocalInfo();
            if (currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());
        }
    }

    private void test(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Whate is the name of the file ?");
            return;
        }
        try
        {
          String nameFile = "test.txt";      
            nameFile = command.getSecondWord();
            Scanner sc = new Scanner(new File(nameFile));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                //gui.println(line);
                interpretCommand(line);
            }
            sc.close();
        }
        catch (FileNotFoundException ex)  
        {
            gui.println("File not fond.");
            ex.printStackTrace();
        }
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();

        // Try to leave current room.
        Objet objet = currentRoom.getObjet(ob);

        if (objet == null) {
            gui.println("tu vas rien manger!");
        } else if(currentPlayer.addObjets(objet)) {
            currentRoom.deleteObjetRoom(ob);
            gui.println("take :");
            gui.println(objet.toString());
        }else{
            gui.println("weight Exceed! choose an other object.");
        }
    }


    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();

        // Try to leave current room.
        Objet objet = currentPlayer.getObjet(ob);

        if (objet == null) {
            gui.println("tu vas rien manger!");
        } else{
            currentRoom.addObjetsRoom(objet);
            currentPlayer.deleteObjetPlayer(ob);
            gui.println("drop :");
            gui.println(objet.toString());
        }
    }
    
    private void items() {
        gui.println(currentPlayer.lookObjetsPlayer());
    }

    private void look() {
        gui.println(currentRoom.lookObjetsRoom());
    }

    private void eat(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();

        // Try to leave current room.
        Objet objet = currentRoom.getObjet(ob);

        if (objet == null) {
            gui.println("tu vas manger de la merde!");
        } else {
            currentRoom.deleteObjetRoom(ob);
            currentPlayer.addObjets(objet);
            gui.println("J'ai bien mangé :");
            gui.println(objet.toString());
            // gui.println(currentPlayer);
            // gui.println(" nombre de cles : " + currentPlayer.hasCles());

        }

    }

    private void back(Command command) {
        if (command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("vous voullez quoi j'ai pas compris?");
            return;
        }
        if(backRoom.empty()){
            gui.println("vous ete ou debut y pas de lieu précedent");
            return;
        }
        if (("Vous etes dans la chambre du trésor...".equals(backRoom.peek().getDescription()))
                    && !currentPlayer.hasCles()) {
                gui.println("ahhe Non il faut avoir 5 cle pour acceder a cette chambre");
                return;
            }
            Room tmpRoom = currentRoom;
            currentRoom = backRoom.peek();
            backRoom.pop();

            printLocalInfo();
            if (currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());

    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
