import java.util.HashMap;
public class Player {
    private String name;
    private int weight;
    private int gain;
    private HashMap<String, Objet> objets;
    private int i;

    public Player(String name) {
        this.name = name;
        this.weight = 20;
        this.gain = 0;
        this.objets = new HashMap<String, Objet>();
        this.i = 0;
    }

    public void addObjets(Objet obj){
        if("Clé de la chambre à trésor".equals(obj.getDescription()))
            objets.put(""+i,obj);
        
        this.i++;
        this.weight += obj.getWeight();
        this.gain += obj.getGain();
    }

    public boolean hasCles(){
        System.out.println(" size : " + objets.size());
        if(objets.size() == 5)
            return  true;
        return false;
    }

    public String toString(){
        return  this.name + " ton score :\n" + 
                "Tu peut prendre jusqu'a : " + this.weight + "\n" +
                "Gain : " + this.gain;
    }

}