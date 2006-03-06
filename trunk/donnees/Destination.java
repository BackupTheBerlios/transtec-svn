package donnees;

import java.util.Vector;

//----- Classe regroupant tous les attributs d'une destination (couple "Entrepôt + volume de colis")

public class Destination implements Comparable{
	private Integer id;
	private Entrepot entrepot;
	private Float volume;//en m3
	private boolean transit;
	
	// Constructeur avec tous les paramètres
	public Destination(Integer id, Entrepot entrepot, Float volume, boolean transit){
		this.id=id;
		this.entrepot=entrepot;
		this.volume=volume;
		this.transit = transit;
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
	public void setVolume(Float volume){
		this.volume=volume;
	}
	
	//----- Insérer le type -----//
	public void setTransit(boolean transit) {
		this.transit = transit;
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
	public Float getVolume(){
		return this.volume;
	}
	
	//----- Obtenir le type -----//
	public boolean getTransit() {
		return this.transit;
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
	
	
	/****** Méthodes liées à l'implémentation de Comparable ******/
	
	// Comparaison par défaut : selon la ville de l'entrepôt
    public int compareTo(Object o) {
    	int ret;
    	
        Destination d = (Destination)o;
        
        // On compare les destinations selon leur ville
        ret = this.entrepot.getLocalisation().getVille().compareToIgnoreCase(d.getEntrepot().getLocalisation().getVille());
        
        return ret;
   }
}
