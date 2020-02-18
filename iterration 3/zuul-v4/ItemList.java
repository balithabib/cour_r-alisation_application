import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemList{
	private HashMap<String, Objet> objets;
	private int nombreObjets;
	public ItemList(){
		this.objets = new HashMap<String, Objet>();
        this.nombreObjets = 0;
	}

	public Objet getObjet(String ob) {
        return objets.get(ob);
    }

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

    public String lookObjets(){
        String strObjets = "Nombre de objets: " + nombreObjets + "\n";
        for(Map.Entry<String, Objet> entry : objets.entrySet()){ 
            String key = entry.getKey();  
            Objet obj = entry.getValue();
            strObjets += key;
            strObjets += obj;   
        }
        return strObjets; 
    }

    public void deleteObjet(String ob){
        objets.remove(ob);
        --nombreObjets;
    }

    public void addObjet(Objet obj){
        objets.put(""+nombreObjets,obj);
        nombreObjets++;
    }

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
    public boolean magic(Objet ob){
    	if(ob.getDescription().equals("Un cookie"))
    		return true;
    	return false;
    }
}