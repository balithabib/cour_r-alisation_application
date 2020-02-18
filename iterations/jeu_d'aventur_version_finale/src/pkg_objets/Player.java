package src.pkg_objets;
import java.io.Serializable;

/**
 * Class Player
 * 
 * @author Group 1, Paris8 University
 * @version 1.0
 */
public class Player implements Serializable{
    private String name;
    private int weightMax;
    private int weight;
    private int gain;
    private ItemList items;   

    public Player(String name) {
        this.name = name;
        this.weightMax = 60;
        this.weight = 0;
        this.gain = 0;
        this.items = new ItemList();
    }

    /**
     * Adds objects.
     * @param Objet ob
     * @return boolean
     */
    public boolean addObjets(Objet ob){
        if(!weightExceed(ob)){
            this.items.addObjet(ob);
            if(items.magic(ob)){
               this.weightMax += 10; 
            }
            this.weight += ob.getWeight();
            this.gain += ob.getGain();
            System.out.println(this.weight);
            System.out.println(this.weightMax);
            return true;
        }
        int w = this.weight + ob.getWeight();
        System.out.println("ta depasser"+w);
        return false;
    }

    /**
     * Deletes an object of the player.
     * @param ob String
     */
    public void deleteObjetPlayer(String ob){
        this.weight -= items.getObjet(ob).getWeight();
        this.gain -= items.getObjet(ob).getGain();
        this.items.deleteObjet(ob);
    }

    /**
     * Checkes if the player has keys.
     * @return boolean
     */
    public boolean hasCles(){
        return this.items.hasCles();
    }

    /**
     * Gets the objects of the player.
     * @return String
     */
    public String lookObjetsPlayer(){
        return items.lookObjets(); 
    }

     /**
     * Checkes if the weight of
     * an object is exceed.
     * @param obj Objet
     * @return boolean
     */
    private boolean weightExceed(Objet obj){
        int w = obj.getWeight() + this.weight;
        if(w > this.weightMax)
            return  true;
        return false;
    }
    
    /**
     * Gets an object.
     * @param ob String
     * @return Objet
     */

    public Objet getObjet(String ob) {
        return items.getObjet(ob);
    }

    /**
     * Prints the score of the game
     * and the weight of objects allowed
     * and the gain.
     * @return String
     */
    public String toString(){
        return  this.name + " ton score :\n" + 
                "Tu peut prendre jusqu'a : " + this.weight + "\n" +
                "Gain : " + this.gain;
    }

    /**
     * Checks if he has a beamer.
     * @return boolean
     */   
    public boolean hasBeamer(){
        return items.hasBeamer();
    }

}
