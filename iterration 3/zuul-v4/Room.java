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
    private ItemList items;    
    private static final String[] dirs = {
    		"north", "east", "south", "west", "up", "down"
    };
    private String imageName;

    public Room(String description, String image) 
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.items = new ItemList();
        this.items.createObjets();
        this.imageName = image;
    }

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
        return items.getObjet(ob);
    }
    
    public StringBuilder getExitString() {
    	StringBuilder strExits = new StringBuilder("Exits:");
    	Set<String> keys = exits.keySet();
    	for (String exit : keys) {
    		strExits.append(" " + exit);
    	}
    	return strExits;
    }

    public String lookObjetsRoom(){
        return items.lookObjets(); 
    }

    public void deleteObjetRoom(String ob){
        items.deleteObjet(ob);
    }

    public void addObjetsRoom(Objet ob){
        items.addObjet(ob);
    }
    
    public String getImageName()
    {
        return imageName;
    }

    public String getDescription()
    {   
        return description;
    }
    
    public StringBuilder getLongDescription()
    {
        return new StringBuilder(description + ".\n" + getExitString());
    }

}
