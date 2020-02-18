/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player currentPlayer;
 
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        currentPlayer = new Player("Habib");
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room 	outside, palace, pyramid, temple, courtyardPalace, throneRoom, 
        		privateAppartements, kingRoom, queenRoom, gallery, secretRoom, 
        		goldenRoom, courtyardTemple, sacrificiesRoom, sanctuary, wow;
      
        outside = new Room("Vous etes à l'entrée de la cité des pharaons...");
        palace = new Room("Vous à l'entrée de palais royale...");
        pyramid  = new Room("Vous etes à l'entrée de la pyramid...");
        temple  = new Room("Vous etes à l'entrée du temple...");
        
        courtyardPalace = new Room("Vous etes dans la cour de palais...");
        throneRoom = new Room("Vous etes dans la salle du trone...");
        privateAppartements = new Room("Vous etes dans les appartements privées !!!!!");
        
        kingRoom = new Room("Vous etes dans la chambre de roi...");
        queenRoom = new Room("Vous etes dans la chambre de la reine...");
        gallery = new Room("Vous etes dans la gallerie...");
        secretRoom = new Room("Vous etes dans la chambre secret...");
        goldenRoom = new Room("Vous etes dans la chambre du trésor...");
        
        courtyardTemple = new Room("Vous etes dans la cour du temple...");
        sacrificiesRoom = new Room("Vous etes dans la salle des sacrifices !!!!!!");
        sanctuary = new Room("Vous etes dans le sanctuaire...");
        wow = new Room("Téléport");
        
        outside.setExits(null, temple, palace, pyramid,null,null);
        palace.setExits(outside, null, courtyardPalace, null,null,null);
        courtyardPalace.setExits(palace, null, throneRoom, null,null,null);
        throneRoom.setExits(courtyardPalace, wow, privateAppartements, null,null,null);
        privateAppartements.setExits("north",throneRoom);
        wow.setExits("north", outside);
        
        temple.setExits(null, courtyardTemple, null, outside,null,null);
        courtyardTemple.setExits(null, sacrificiesRoom, null, temple,null,null);
        sacrificiesRoom.setExits(null, sanctuary, null, courtyardTemple,null,null);
        sanctuary.setExits("west", sacrificiesRoom);
        
        pyramid.setExits(gallery, outside, null, queenRoom,null,secretRoom);
        secretRoom.setExits(null, null, null, null,pyramid,null);
        queenRoom.setExits("east", pyramid);
        gallery.setExits(null, null, pyramid, null,kingRoom,null);
        kingRoom.setExits(null, gallery,null, null,goldenRoom,gallery);
        goldenRoom.setExits("up", sanctuary);
        currentRoom = outside;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printLocalInfo() {
        System.out.println(currentRoom.getDescription());
        System.out.println(currentRoom.getExitString());
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println(currentPlayer);
        System.out.println();
        printLocalInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("look"))
            look();
        else if (commandWord.equals("eat"))
            eat(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if(("Vous etes dans la chambre du trésor...".equals(nextRoom.getDescription())) && !currentPlayer.hasCles()) {
                System.out.println("ahhe Non il faut avoir 5 cle pour acceder a cette chambre");
                return;
            }
            currentRoom = nextRoom;
            printLocalInfo();
        }
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
    
    
    private void look() {
    	System.out.println(currentRoom.lookObjetsRoom());
    }

    private void eat(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();
        
        // Try to leave current room.
        Objet objet = currentRoom.getObjet(ob);
        
        if (objet == null) {
            System.out.println("tu vas manger de la merde!");
        }
        else {
            currentRoom.deleteObjetRoom(ob);
            currentPlayer.addObjets(objet);
            System.out.println("J'ai bien mangé :");
            System.out.println(objet);
            //System.out.println(currentPlayer);
            System.out.println(" nombre de cles : " + currentPlayer.hasCles());


        }
    	
    }
}
