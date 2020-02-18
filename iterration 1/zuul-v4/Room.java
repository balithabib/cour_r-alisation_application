import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Objet> objets;
    private int nombreObjets;    
    private static final String[] dirs = {
    		"north", "east", "south", "west", "up", "down"
    };
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.objets = new HashMap<String, Objet>();
        this.nombreObjets = 0;
        this.createObjets();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param up The up exit.
     * @param down The down exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
        	exits.put("east", east);
        if(south != null)
        	exits.put("south", south);
        if(west != null)
        	exits.put("west", west);
        if(up != null)
        	exits.put("up", up);
        if(down != null)
        	exits.put("down", down);
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param dir direction of the exit.
     * @param room the room where leads.
     */
    public void setExits(String dir, Room room) 
    {
    	for (String s : dirs) {
    		if (s.equals(dir)) {
    			exits.put(dir, room);
    			return;
    		}
    	}
    	
    }
    
    public Room getExit(String direction) {
    	return exits.get(direction);
    }

    public Objet getObjet(String ob) {
        return objets.get(ob);
    }
    
    /*
     * Return a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString() {
    	String strExits = "Exits:";
    	Set<String> keys = exits.keySet();
    	for (String exit : keys) {
    		strExits += " " + exit;
    	}
    	return strExits;
    }
    
    /**
     * create  all objects from the room
     */
    private void createObjets()
    {
        int min = 2;
        int max = 5;
        nombreObjets = min + (int)(Math.random() * ((max - min) + 1));// entre 2 à 5
        for(int  i = 0;i < nombreObjets ; i++){
            Objet obj = new Objet();
            objets.put(""+i, obj);
        }  
    }

    /*
     * Return a description of the room's objects,
     * 
     * @return A description of the available objetcs.
     */
    public String lookObjetsRoom(){
        String strObjets = "Nombre de objets: " + nombreObjets + "\n";
        for(Map.Entry<String, Objet> entry : objets.entrySet()){ 
            String key = entry.getKey();  
            Objet obj = entry.getValue();
            strObjets += key;
            strObjets += obj;   
        }
        return strObjets; 
    }

    public void deleteObjetRoom(String ob){
        objets.remove(ob);
        --nombreObjets;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {   
        return description;
    }
    
    /**
     * @return The long description of the room.
     */
    public String getLongDescription()
    {
        return "Actuellement  " + description + ".\n" + getExitString();
    }

}
