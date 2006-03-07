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
	
	// Constantes décrivant l'état d'une préparation
	public final static int A_FAIRE=0;
	public final static int EN_COURS=1;
	
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
	
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	public void setVolumeChargement(Float volume){
		this.volume=volume;
	}
	
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	public Float getVolume(){
		return this.volume;
	}
	
	public Camion getCamion(){
		return this.camion;
	}
	
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	public Integer getEtat(){
		return this.etat;
	}
}
