package src.pkg_objets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

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
public class Room implements Serializable
{
    private String description;
    private HashMap<String, Room> exits;
    private ItemList items;    
    private static final String[] dirs = {
    		"north", "east", "south", "west", "up", "down"
    };
    private String imageName;
    private boolean transporterRoom = false;
    public Room(String description, String image) 
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.items = new ItemList();
        this.items.createObjets();
        this.imageName = image;
    }

    /**
     * Sets exits.
     * @param north
     * @param east
     * @param south
     * @param west
     * @param up
     * @param down
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
     * Sets the exit that the player wants.
     * @param dir String
     * @param room Room
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

    /**
     * Gets the exit that the player took.
     * @param direction String
     * @return Room
     */    
    public Room getExit(String direction) {
    	return exits.get(direction);
    }

    /**
     * Gets the objects in the room.
     * @param ob String
     * @return Objet
     */
    public Objet getObjet(String ob) {
        return items.getObjet(ob);
    }

    /**
     * return the exits 
     * @return StringBuilder
     */    
    public StringBuilder getExitString() {
    	StringBuilder strExits = new StringBuilder("Exits:");
    	Set<String> keys = exits.keySet();
    	for (String exit : keys) {
    		strExits.append(" " + exit);
    	}
    	return strExits;
    }

    /** 
     * Looks the objects in the room.
     * @return String
     */
    public String lookObjetsRoom(){
        return items.lookObjets(); 
    }

    /** 
     * deletes the object wanted from the room.
     * @param ob String
     */
    public void deleteObjetRoom(String ob){
        items.deleteObjet(ob);
    }

    /** 
     * Adds the object wanted in the room.
     * @param ob Objet
     */
    public void addObjetsRoom(Objet ob){
        items.addObjet(ob);
    }

    /** 
     * Gets the name of the image.
     * @return String
     */
    public String getImageName()
    {
        return imageName;
    }

    /** 
     * Gets the description.
     * @return String
     */    
    public String getDescription()
    {   
        return description;
    }

    /** 
     * Gets a long description.
     * @return StringBuilder
     */
    public StringBuilder getLongDescription()
    {
        return new StringBuilder(description + ".\n" + getExitString());
    }

    /** 
     * Sets the transporter room.
     * @param boolean b
     */
    public void setTransporterRoom(boolean b){
        this.transporterRoom = b;
    }

    /**
     * Checks if the room is a transporter one.
     * @return boolean 
     */
    public boolean isTransporterRoom(){
        return transporterRoom;
    }
}
