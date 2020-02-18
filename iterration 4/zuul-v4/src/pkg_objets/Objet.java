package src.pkg_objets;
import java.io.Serializable;

public class Objet implements Serializable{
    private String description = "";
    private int weight = 0;
    private int gain = 0;
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */

    public Objet() {
        int id = (int)(Math.random()*6) + 1;
        if(id == 1){
            this.weight = 0;
            this.gain = 0;
            this.description = "Clé de la chambre à trésor";
        }
        if(id == 2){
            this.weight = 4;
            this.gain = 10;
            this.description = "Une pomme";
        }
        if(id == 3){
            this.weight = 15;
            this.gain = 10;
            this.description = "Une carote";
        }
        if(id == 4){
            this.weight = 10;
            this.gain = -10;
            this.description = "Un poison";
        }
        if(id == 5){
            this.weight = -5;
            this.gain = 0;
            this.description = "Un cookie";
        }
        if(id == 6){
            this.weight = 60;
            this.gain = 0;
            this.description = "Un beamer";
        }
    }

    /**
     * Gets the weight and the gain of 
     * the object eaten.
     * @return String 
    */

    @Override
    public String toString(){
        return  " - " + description + " : \n" + 
                "       |weight     : " + this.weight + "\n" +
                "       |Gain       : " + this.gain + "\n";
    }

    /**
     * Gets the weight.
     * @return weight 
     */

    public int getWeight(){
        return this.weight;
    }
    /**
     * Gets the gain.
     * @return gain 
     */

    public int getGain(){
        return this.gain;
    }
    /**
     * Gets the description.
     * @return description 
     */
    
    public String getDescription(){
        return this.description;
    }
       
}
