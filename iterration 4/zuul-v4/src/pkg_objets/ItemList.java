package src.pkg_objets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

/** 
 * Class 
 * @author Group 1, Paris8 University
 * @version 1.0
 */
public class ItemList implements Serializable{
	private HashMap<String, Objet> objets;
	private int nombreObjets;
	public ItemList(){
		this.objets = new HashMap<String, Objet>();
        this.nombreObjets = 0;
	}
    
     /**
     * Gets an object from the instance.
     * @param ob
     * @return Object
     */
	public Objet getObjet(String ob) {
        return objets.get(ob);
    }

    /**
     * Creates objects.
     */
    public void createObjets()
    {
        int min = 2;
        int max = 5;
        nombreObjets = min + (int)(Math.random() * ((max - min) + 1));// entre 2 à 5
        for(int  i = 0;i < nombreObjets ; i++){
            Objet obj = new Objet();
            objets.put(""+i, obj);
        }  
    }

    /**
     * display the list of objects in the instance.
     * @return strObjets
     */
    public String lookObjets(){
        String strObjets = "Nombre d'objets: " + nombreObjets + "\n";
        for(Map.Entry<String, Objet> entry : objets.entrySet()){ 
            String key = entry.getKey();  
            Objet obj = entry.getValue();
            strObjets += key;
            strObjets += obj;   
        }
        return strObjets; 
    }

    /**
     * Deletes an object from the instance.
     * @param ob
     */
    public void deleteObjet(String ob){
        objets.remove(ob);
        --nombreObjets;
    }


    /**
     * Adds an object.  
     * @param ob
     */
    public void addObjet(Objet obj){
        objets.put(""+nombreObjets,obj);
        nombreObjets++;
    }

    /**
     * Checks if the player has a key to 
     * enter in the room (golden room).
     * @return boolean
     */
    public boolean hasCles(){
    	int i = 0;
    	for(Map.Entry<String, Objet> entry : objets.entrySet()){  
            Objet obj = entry.getValue();
            if(obj.getDescription().equals("Clé de la chambre à trésor"))
            	i++;
        }
        if (i == 5)
        	return true;	
        return false; 
    }

    /**
     * check the availability of a magic cokier
     * @param objet ob
     * @return boolean
     */
    public boolean magic(Objet ob){
    	if(ob.getDescription().equals("Un cookie"))
    		return true;
    	return false;
    }

     /**
     * check the availability of a beamer
     * @return boolean
     */
    public boolean hasBeamer(){
        for(Map.Entry<String, Objet> entry : objets.entrySet()){  
            Objet obj = entry.getValue();
            if(obj.getDescription().equals("Un beamer"))
                return true;
        }
        return false;
    }
}
