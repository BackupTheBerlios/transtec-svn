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
	
	// Constantes d�crivant l'�tat d'une pr�paration
	public final static int A_FAIRE=0;
	public final static int EN_COURS=1;
	
//	 Constructeur utilisant tous les param�tres
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
	
	// Constructeur utilisant tous les param�tres
	public Preparation(Integer id, Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat){
		this.id=id;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.etat=etat;
	}
	
	// Constructeur utilisant tous les param�tres sauf l'id
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
	
	
	/****** M�thodes d'�criture ******/

	//----- Ins�rer l'id de la pr�paration -----//
	public void setId(Integer id){
		this.id=id;
	}

	//----- Ins�rer l'utilisateur -----//
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	//----- Ins�rer l'origine -----//
	public void setOrigine(Entrepot origine){
		this.origine=origine;
	}
	
	//----- Ins�rer la destination -----//
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	//----- Ins�rer le volume -----//
	public void setVolume(Float volume){
		this.volume=volume;
	}
	
	//----- Ins�rer le camion -----//
	public void setCamion(Camion camion){
		this.camion=camion;
	}
	
	//----- Ins�rer l'id du chargement en cours -----//
	public void setIdChargementEnCours(Integer idChargementEnCours){
		this.idChargementEnCours=idChargementEnCours;
	}
	
	//----- Ins�rer l'id du chargement -----//
	public void setIdChargement(Integer idChargement){
		this.idChargement=idChargement;
	}	
	
	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'utilisateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- R�cup�ration de l'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	//----- R�cup�ration de la destination -----//
	public Entrepot getDestination(){
		return this.destination;
	}
	
	//----- R�cup�ration du volume -----//
	public Float getVolume(){
		return this.volume;
	}
	
	//----- R�cup�ration du camion -----//
	public Camion getCamion(){
		return this.camion;
	}
	
	//----- R�cup�ration de l'�tat -----//
	public Integer getEtat(){
		return this.etat;
	}
	
	//----- R�cup�ration de l'id du chargement en cours-----//
	public Integer getIdChargementEnCours(){
		return this.idChargementEnCours;
	}
	
	//----- R�cup�ration de l'id du chargement -----//
	public Integer getIdChargement(){
		return this.idChargement;
	}
	
		
	/****** Red�finition de m�thodes g�n�riques ******/
	
	// Affichage d'une pr�paration
	public String toString(){
		String s = new String();
		s = "{"+ destination +" | "+ utilisateur +" | "+ volume +"}";
		
		return s;
	}
}
