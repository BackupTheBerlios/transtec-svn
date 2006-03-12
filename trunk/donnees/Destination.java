package donnees;

import java.util.Vector;

//----- Classe regroupant tous les attributs d'une destination (couple "Entrep�t + volume de colis")

public class Destination implements Comparable{
	private Entrepot entrepot;
	private Float volume;//en m3
	private boolean transit;
	
	// Constructeur avec tous les param�tres
	public Destination(Entrepot entrepot, Float volume, boolean transit){
		this.entrepot=entrepot;
		this.volume=volume;
		this.transit = transit;
	}

	// Constructeur vide
	public Destination(){
		entrepot = new Entrepot();
	}
	
	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// entrepot, volume
		v.add(entrepot);
		v.add(volume);

		return v;
	}

	
	/****** M�thodes d'�criture ******/

	//----- R�cup�ration de l'entrep�t -----//
	public void setEntrepot(Entrepot entrepot){
		this.entrepot=entrepot;
	}
	
	//----- R�cup�ration du volume -----//
	public void setVolume(Float volume){
		this.volume=volume;
	}
	
	//----- Ins�rer le type -----//
	public void setTransit(boolean transit) {
		this.transit = transit;
	}	

	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'entrep�t -----//
	public Entrepot getEntrepot(){
		return this.entrepot;
	}
	
	//----- R�cup�ration du volume -----//
	public Float getVolume(){
		return this.volume;
	}
	
	//----- Obtenir le type -----//
	public boolean getTransit() {
		return this.transit;
	}
	
	
	/****** Red�finition de m�thodes g�n�riques ******/
	
	// Affichage d'une destination : on affiche sa ville
	public String toString(){
		return new String(entrepot.getLocalisation().getVille()+" ("+entrepot.getLocalisation().getCodePostal()+")");
	}
	
	// Comparaison de deux destinations selon leur ID
	public boolean equals(Object o){
		boolean ret=false;
		
		if(o instanceof Destination){
			Destination destTmp=(Destination)o;
			if(this.entrepot.getId().equals(destTmp.getEntrepot().getId())) ret=true;			
		}
		
		return ret;
	}
	
	
	/****** M�thodes li�es � l'impl�mentation de Comparable ******/
	
	// Comparaison par d�faut : selon la ville de l'entrep�t
    public int compareTo(Object o) {
    	int ret;
    	
        Destination d = (Destination)o;
        
        // On compare les destinations selon leur ville
        ret = this.entrepot.getLocalisation().getVille().compareToIgnoreCase(d.getEntrepot().getLocalisation().getVille());
        
        return ret;
   }
}
