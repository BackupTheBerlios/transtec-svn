package donnees;

import java.util.Vector;

public class Preparation {
	private Integer id;
	private Utilisateur utilisateur;
	private Entrepot origine;
	private Entrepot destination;
	private Float volume;
	private Camion camion;
	private Integer etat;
	private Integer idChargementEnCours;
	private Integer idChargement;
	
	// Constantes décrivant l'état d'une préparation
	public final static int A_FAIRE=0;
	public final static int EN_COURS=1;
	
//	 Constructeur utilisant tous les paramètres
	public Preparation(Integer id, Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat, Integer idChargementEnCours, Integer idChargement){
		this.id=id;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.etat=etat;
		this.idChargementEnCours=idChargementEnCours;
		this.idChargement=idChargement;
	}
	
	// Constructeur utilisant tous les paramètres
	public Preparation(Integer id, Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat){
		this.id=id;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.etat=etat;
	}
	
	// Constructeur utilisant tous les paramètres sauf l'id
	public Preparation(Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat){
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.etat=etat;
	}
	
	// Constructeur utilisant un Vector
	public Preparation(Vector v){
		this.id=(Integer)v.get(0);
		this.utilisateur=(Utilisateur)v.get(1);
		this.origine=(Entrepot)v.get(2);
		this.destination=(Entrepot)v.get(3);
		this.volume=(Float)v.get(4);
		this.camion=(Camion)v.get(5);
		this.etat=(Integer)v.get(6);
	}
	
	// Constructeur vide
	public Preparation(){
		this.etat = new Integer(A_FAIRE);
	}
	
	// Permet de transformer un objet en un Vector
	public Vector toVector(){
		Vector v=new Vector();
		
		v.add(this.id);
		v.add(this.utilisateur);
		v.add(this.origine);
		v.add(this.destination);
		v.add(this.volume);
		v.add(this.camion);
		v.add(this.etat);
		
		return v;
	}
	
	
	/****** Méthodes d'écriture ******/

	//----- Insérer l'id de la préparation -----//
	public void setId(Integer id){
		this.id=id;
	}

	//----- Insérer l'utilisateur -----//
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	//----- Insérer l'origine -----//
	public void setOrigine(Entrepot origine){
		this.origine=origine;
	}
	
	//----- Insérer la destination -----//
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	//----- Insérer le volume -----//
	public void setVolume(Float volume){
		this.volume=volume;
	}
	
	//----- Insérer le camion -----//
	public void setCamion(Camion camion){
		this.camion=camion;
	}
	
	//----- Insérer l'id du chargement en cours -----//
	public void setIdChargementEnCours(Integer idChargementEnCours){
		this.idChargementEnCours=idChargementEnCours;
	}
	
	//----- Insérer l'id du chargement -----//
	public void setIdChargement(Integer idChargement){
		this.idChargement=idChargement;
	}	
	
	
	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération de l'utilisateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- Récupération de l'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	//----- Récupération de la destination -----//
	public Entrepot getDestination(){
		return this.destination;
	}
	
	//----- Récupération du volume -----//
	public Float getVolume(){
		return this.volume;
	}
	
	//----- Récupération du camion -----//
	public Camion getCamion(){
		return this.camion;
	}
	
	//----- Récupération de l'état -----//
	public Integer getEtat(){
		return this.etat;
	}
	
	//----- Récupération de l'id du chargement en cours-----//
	public Integer getIdChargementEnCours(){
		return this.idChargementEnCours;
	}
	
	//----- Récupération de l'id du chargement -----//
	public Integer getIdChargement(){
		return this.idChargement;
	}
	
		
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'une préparation
	public String toString(){
		String s = new String();
		s = "{"+ destination +" | "+ utilisateur +" | "+ volume +"}";
		
		return s;
	}
}
