package donnees;

import java.util.Vector;

public class Preparation {
	private Utilisateur utilisateur;
	private Entrepot destination;
	private Integer volumeChargement;//en cm3
	private Vector listeCamion;
	
	public Preparation(Utilisateur utilisateur, Entrepot destination, Integer volumeChargement, Vector listeCamion){
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.volumeChargement=volumeChargement;
		this.listeCamion=listeCamion;
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
}
