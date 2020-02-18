public class Objet {
    private String description = "";
    private int weight = 0;
    private int gain = 0;
    //private static HashMap <String, {int, int, String}> types;
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     
     * @param weights 
     * @param 
     * @param description The room's description.
     */

    public Objet() {
        int id = (int)(Math.random()*4) + 1;
        //System.out.println("id ="+id);
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
    }

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     
     * @param weights 
     * @param 
     * @param description The room's description.
    */
    @Override
    public String toString(){
        return  " - " + description + " : \n" + 
                "       |weight     : " + this.weight + "\n" +
                "       |Gain       : " + this.gain + "\n";
    }

    public int getWeight(){
        return this.weight;
    }

    public int getGain(){
        return this.gain;
    }

    public String getDescription(){
        return this.description;
    }
       
}