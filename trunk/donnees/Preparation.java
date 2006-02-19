package donnees;

import java.util.Vector;

public class Preparation {
	private Utilisateur utilisateur;
	private Entrepot destination;
	private Integer volumeChargement;//en cm3
	private Vector listeCamion;
	private Camion aCharger;
	private Integer VolumeColis;
	
	public Preparation(Utilisateur utilisateur, Entrepot destination, Integer volumeChargement, Vector listeCamion){
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.volumeChargement=volumeChargement;
		this.listeCamion=listeCamion;
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	public Integer getVolumeChargement(){
		return this.volumeChargement;
	}
	
	public Vector getListeCamion(){
		return this.listeCamion;
	}
	
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	public void setVolumeChargement(Integer volumeChargement){
		this.volumeChargement=volumeChargement;
	}
	
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	public void setListeCamion(Vector listeCamion){
		this.listeCamion=listeCamion;
	}
	
	public void setCamionACharger(Camion aCharger){
		this.aCharger=aCharger;
	}
	
	public Camion getCamionACharger(){
		return this.aCharger;
	}
	
	public void ajouterVolumeColis(Integer Volume){
		// Peut �tre pb avec addition
		this.VolumeColis+=Volume;
	}
	
	public void soustraireVolumeColis(Integer Volume){
		// Peut etre faux
		this.VolumeColis-=Volume;
	}
	
	public Integer getVolumeColis(){
		return this.VolumeColis;
	}
	
	public void initializeChargement(){
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
	}
}
