public class Player {
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

    public void deleteObjetPlayer(String ob){
        this.weight -= items.getObjet(ob).getWeight();
        this.gain -= items.getObjet(ob).getGain();
        this.items.deleteObjet(ob);
    }

    public boolean hasCles(){
        return this.items.hasCles();
    }

    public String lookObjetsPlayer(){
        return items.lookObjets(); 
    }

    private boolean weightExceed(Objet obj){
        int w = obj.getWeight() + this.weight;
        if(w > this.weightMax)
            return  true;
        return false;
    }
    
    public Objet getObjet(String ob) {
        return items.getObjet(ob);
    }

    public String toString(){
        return  this.name + " ton score :\n" + 
                "Tu peut prendre jusqu'a : " + this.weight + "\n" +
                "Gain : " + this.gain;
    }

}