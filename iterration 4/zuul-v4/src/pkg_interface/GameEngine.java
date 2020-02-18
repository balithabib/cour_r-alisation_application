package src.pkg_interface;
import src.pkg_command.*;
import src.pkg_objets.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

public class GameEngine implements Serializable
{
    private UserInterface gui;
    private Parser parser;
    private Player currentPlayer;
    private Room currentRoom;
    private Room beamerRoom;
    private Stack<Room> backRoom;
    private HashMap<String, Room> rooms;
    //private int timeLimite = 5;
    //private int time = 0;
    private double timeLimite = 360.0;
    private double time;
    private double start = (double)System.currentTimeMillis()/1000.0; 
    private double end = 0.0;
    final private String roomsData[][] = { 
            { "outside", "Vous êtes à l'entrée de la cité des pharaons...", "images/cite.gif" },
            { "palace", "Vous êtes à l'entrée du palais royal...","images/palais/palace.gif"}, 
            { "pyramid", "Vous êtes à l'entrée de la pyramide...", "images/pyramid/pyramid.gif"}, 
            { "temple", "Vous êtes à l'entrée du temple...", "images/temple/temple.gif"}, 
            { "courtyardPalace",  "Vous êtes dans la cour du palais...", "images/palais/courtyardPalace.gif"}, 
            { "throneRoom", "Vous êtes dans la salle du trône...", "images/palais/throneRoom.gif"},
            { "privateAppartements", "Vous êtes dans les appartements privés !!!!!", "images/palais/privateAppartements.gif"}, 
            { "kingRoom", "Vous êtes dans la chambre du roi...", "images/pyramid/kingRoom.gif"}, 
            { "queenRoom", "Vous êtes dans la chambre de la reine...", "images/pyramid/queenRoom.gif"}, 
            { "gallery", "Vous êtes dans la gallerie...", "images/pyramid/gallery.gif"}, 
            { "secretRoom", "Vous êtes dans la chambre secrète...", "images/pyramid/secretRoom2.gif"},
            { "goldenRoom", "Vous êtes dans la chambre du trésor...", "images/pyramid/goldenRoom.gif"}, 
            { "courtyardTemple", "Vous êtes dans la cour du temple...", "images/temple/courtyardTemple.gif"}, 
            { "sacrificiesRoom", "Vous êtes dans la salle des sacrifices !!!!!!", "images/temple/sacrificiesRoom.gif"}, 
            { "sanctuary", "Vous êtes dans le sanctuaire...", "images/temple/sanctuary.gif"}, 
            { "teleporter", "Téléporter", "images/palais/secreteRoom1.gif"} 
        };
    /**
     * Creates the game and initialise its internal map.
     */

    public GameEngine() {
        rooms = new HashMap<String, Room>();
        backRoom = new Stack<Room>();
        createRooms();
        parser = new Parser();
        currentPlayer = new Player("Habib");
    }
    
    /**
     * Sets up graphical user interface.
     */

    public void setGUI(UserInterface userInterface) {
        gui = userInterface;
        printWelcome();
    }

    /**
     * Creates all the rooms and link their exits together.
     */

    private void createRooms()
    { 

        for (String[] d : roomsData) {
            rooms.put(d[0], new Room(d[1], d[2]));
        }
        rooms.get("outside").setExits(null, rooms.get("temple"), rooms.get("palace"), rooms.get("pyramid"),null,null);
        rooms.get("palace").setExits(rooms.get("outside"), null, rooms.get("courtyardPalace"), null,null,null);
        rooms.get("courtyardPalace").setExits(rooms.get("palace"), null, rooms.get("throneRoom"), null,null,null);
        rooms.get("throneRoom").setExits(rooms.get("courtyardPalace"), rooms.get("teleporter"), rooms.get("privateAppartements"), null,null,null);
        rooms.get("privateAppartements").setExits("north",rooms.get("throneRoom"));

        rooms.get("teleporter").setTransporterRoom(true);
        
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
    }

    /**
     * Main play routine. Loops until end of play.
     */

    private void printLocalInfo() {
        gui.println("------------------------------------------------------------------------------");
        gui.println(currentRoom.getDescription());
        gui.println(currentRoom.getExitString().toString());
        gui.println("------------------------------------------------------------------------------");
    }

    /**
     * Prints out the opening message for the player.
     */

    private void printWelcome() {
        gui.print("\n");
        gui.println("Welcome to the World of Zuul!");
        gui.println("World of Zuul is a new, incredibly boring adventure game.");
        gui.println("Type 'help' if you need help.");
        gui.print("\n");
        printLocalInfo();
        gui.showImage(currentRoom.getImageName());
    }

    /**
     * Given a command, process (that is: execute) the command. If this command ends
     * the game, true is returned, otherwise false is returned.
     * @param commandLine
     * @return boolean
     */

    public boolean interpretCommand(String commandLine) {
        boolean wantToQuit = false;
        Command command = parser.getCommand(commandLine);
        CommandWord commandWord = command.getCommandWord();
        gui.clear();
        end = (double)System.currentTimeMillis()/1000.0; 
        time = end - start;
        gui.time();
        if( time > timeLimite){
            return quit();
        }

        switch(commandWord){
            case HELP:
                goRoom(command);
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
            case CHARGER:
                charger();
                break;
            case DECLANCHER:
                declancher();
                break;
            case SAVE:
                save(command);
                break;
            case LOAD:
                load(command);
                break;
            case QUIT:
                wantToQuit = quit();
                break;  
            default : {
                gui.println("I don't know what you mean...");
                return false;
            }
        }
        return wantToQuit; 
    }
    
    /**
     * Prints out some help information. Here we print some cryptic message
     * and a list of the command words.
     */

    private void printHelp() {
        gui.println("You are lost. You are alone. You wander");
        gui.println("around at the university.");
        gui.print("\n");
        gui.println("Your command words are:" + parser.showCommands());
    }

    /**
     * Tries to go to one direction. If there is an exit, enter the new room,
     * otherwise prints an error message.
     * @param command
     */

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            /** 
             * If there is no second word, we don't know where to go...
             */

            gui.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = null;
        /**
         * Try to leave current room.
         */

        if(currentRoom.isTransporterRoom()){
            nextRoom = getRandomRoom();
        }else{
            nextRoom = currentRoom.getExit(direction);
        }

        if (nextRoom == null) {
            gui.println("There is no door!");
        } else {
            if (("Vous êtes dans la chambre du trésor...".equals(nextRoom.getDescription()))
                    && !currentPlayer.hasCles()) {
                gui.println("Oops! Non il faut avoir 5 clés pour accéder à cette chambre");
                return;
            }
            backRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocalInfo();
            if (currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());
        }
    }
    /**
     * Tries to go to one direction. If there is an exit, enter the new room,
     * otherwise prints an error message.
     * @param nextRoom
     */

    private void goRoom(Room nextRoom) {
        if (nextRoom == null) {
            gui.println("There is no door!");
        } else {
            if (("Vous êtes dans la chambre du trésor...".equals(nextRoom.getDescription()))
                    && !currentPlayer.hasCles()) {
                gui.println("Oops! Non il faut avoir 5 clés pour accéder à cette chambre");
                return;
            }
            backRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocalInfo();
            if (currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());
        }
    }
    
    /**
     * Gets a room randomly.
     * @return Room
     */

    public Room getRandomRoom(){
         int id = (int)(Math.random()*15);
         return rooms.get(roomsData[id][0]);
    }

    /**
     * Tests the commands of the player
     * if the they're valid or has second word...
     * @param command
     */   
    private void test(Command command){
        if (!command.hasSecondWord()) {
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

     /**
     * Takes an object and checkes
     * if its weight isn't exceed.
     * @param command
     */ 
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();
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

    /**
     * Drop an object and checkes
     * @param command
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();

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

    /**
     * display the items the player took
     */
    private void items() {
        gui.println(currentPlayer.lookObjetsPlayer());
    }

    /**
     * Looks inside the room
     * the objects that exist.
     */
    private void look() {
        gui.println(currentRoom.lookObjetsRoom());
    }

    /**
     * Eats an object.
     * @param command
     */
    private void eat(Command command) {
        if (!command.hasSecondWord()) {
            gui.println("vous voullez manger quoi?");
            return;
        }

        String ob = command.getSecondWord();
        Objet objet = currentRoom.getObjet(ob);

        if (objet == null) {
            gui.println("tu vas manger de la merde!");
        } else {
            currentRoom.deleteObjetRoom(ob);
            currentPlayer.addObjets(objet);
            gui.println("J'ai bien mangé :");
            gui.println(objet.toString());

        }

    }

    /**
     * Backs to the previous place.
     * @param command
     */
    private void back(Command command) {
        if (command.hasSecondWord()) {
            gui.println("vous voulez quoi? j'ai pas compris?");
            return;
        }
        if(backRoom.empty()){
            gui.println("vous êtes au debut il n'y pas de lieu précédent");
            return;
        }
        if (("Vous êtes dans la chambre du trésor...".equals(backRoom.peek().getDescription()))
                    && !currentPlayer.hasCles()) {
                gui.println("ahhe Non il faut avoir 5 clés pour accéder à cette chambre");
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
     * activate the beamer
     */
    private void charger(){
        if(currentPlayer.hasBeamer()){
            gui.println("Beamer charger.");
            gui.println("Vous pouvez le declancher.");
            beamerRoom = currentRoom;
        }
    }

    /**
     * trigger the beamer
     */
    private void declancher(){
        if(currentPlayer.hasBeamer()){
            gui.println("Declanchemant du Beamer.");
            goRoom(beamerRoom);
        }
    }

    /**
     * save the state of the game
     * @param command
     */
    public void save(Command command)
    {
        if (!command.hasSecondWord()) {
            gui.println("Save where?");
            return;
        }
        try{
            String pathName = command.getSecondWord();
            System.out.println(pathName);
            FileOutputStream outputFile = new FileOutputStream(pathName);
            ObjectOutputStream output = new ObjectOutputStream(outputFile);
            output.writeObject(currentPlayer);
            output.writeObject(currentRoom);
            output.writeObject(beamerRoom);
            output.writeObject(backRoom);
            output.writeObject(rooms);
            output.writeObject(time);
            output.writeObject(start);
            output.writeObject(end);
            
            output.close();
        }catch(IOException ex){
            gui.println("File not fond.");
            gui.println(ex.getMessage());
        }
    }

    /**
     * load a game state
     * @param command
     */

    public void load(Command command)
    {
        if (!command.hasSecondWord()) {
            gui.println("Load where?");
            return;
        }
        try{
            String pathName = command.getSecondWord();
            File file = new File(pathName);

            if (!file.exists())
                return;
            
            FileInputStream inputFile = new FileInputStream(file);
            
            ObjectInputStream input = new ObjectInputStream(inputFile);
            this.currentPlayer = (Player)input.readObject();
            this.currentRoom = (Room)input.readObject();
            this.beamerRoom = (Room)input.readObject();
            this.backRoom = (Stack<Room>)input.readObject();
            this.rooms = (HashMap<String, Room>)input.readObject();
            this.time = (double)input.readObject();
            this.start = (double)input.readObject();
            this.end = (double)input.readObject();
            printLocalInfo();
            if (currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());

        }catch(IOException ex){
            gui.println("File not fond.");
            gui.println(ex.getMessage());
        }catch(ClassNotFoundException ex){
            gui.println(ex.getMessage());
        }
    }


    /**
     * Gets the time.
     * @return String
     */
    public String getTime(){
        String s = ":";    
        int t = (int)time;
        int min = t/60;
        int second = t - min * 60;
        if(second < 10)
            s = ":0";

        return "0"+min+s+second;
    }

    /** 
     * "Quit" was entered. Checks the rest of the command to see
     *  whether we really quit the game.
     * @return boolean True, if this command quits the game, false otherwise.
     */
    private boolean quit() 
    {
        gui.println("Game over! -_-");
        gui.println("Le jeu est fini, le temps s'est écoulé.");
        items();
        gui.showImage("images/end.gif");
        return true;
    }
}
