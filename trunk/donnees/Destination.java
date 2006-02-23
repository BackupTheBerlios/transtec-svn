package donnees;

import java.util.Vector;

//----- Classe regroupant tous les attributs d'une destination (couple "Entrepôt + volume de colis")

public class Destination implements Comparable{
	private Integer id;
	private Entrepot entrepot;
	private Integer volume;//en cm3
	
	// Constructeur avec tous les paramètres
	public Destination(Integer id, Entrepot entrepot, Integer volume){
		this.id=id;
		this.entrepot=entrepot;
		this.volume=volume;
	}

	// Constructeur vide
	public Destination(){
		
	}
	
	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, entrepot, volume

		v.add(id);
		v.add(entrepot);
		v.add(volume);

		return v;
	}

	
	/****** Méthodes d'écriture ******/

	//----- Insérer l'id -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération de l'entrepôt -----//
	public void setEntrepot(Entrepot entrepot){
		this.entrepot=entrepot;
	}
	
	//----- Récupération du volume -----//
	public void setVolume(Integer volume){
		this.volume=volume;
	}
	

	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération de l'entrepôt -----//
	public Entrepot getEntrepot(){
		return this.entrepot;
	}
	
	//----- Récupération du volume -----//
	public Integer getVolume(){
		return this.volume;
	}
	
	
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'une destination : on affiche sa ville
	public String toString(){
		return entrepot.getLocalisation().getVille();
	}
	
	// Comparaison de deux destinations selon leur ID
	public boolean equals(Object o){
		boolean ret=false;
		
		if(o instanceof Destination){
			Destination destTmp=(Destination)o;
			if(this.id.equals(destTmp.getId())) ret=true;			
		}
		
		return ret;
	}
	
	
	/****** Méthode liée à l'implémentation de Comparable ******/
	
	// Permet de comparer deux objets entre eux
    public int compareTo(Object o) {
    	int ret;
    	
        Destination d = (Destination)o;
        
        // On compare les utilisateurs selon leur nom
        ret = this.entrepot.getLocalisation().getVille().compareToIgnoreCase(d.getEntrepot().getLocalisation().getVille());
        
        return ret;
   }

}
