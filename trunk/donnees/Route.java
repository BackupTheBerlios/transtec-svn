package donnees;

import java.util.Vector;

/*
 * Classe repr�sentant une route de la table de routage
 */ 

public class Route {
	private Integer id;
	private Entrepot origine;
	private Entrepot destination;
	private Entrepot intermediaire;
	private Float distance;
	
	// Constructeur utilisant tous les param�tres
	public Route(Integer id,Entrepot origine,Entrepot destination,Entrepot intermediaire,Float distance){
		this.id=id;
		this.origine=origine;
		this.destination=destination;
		this.intermediaire=intermediaire;
		this.distance=distance;	
	}
	
	// Constructeur vide
	public Route(){		
	}
	
	// Constructeur utilisant un vector
	public Route(Vector v){
		this.id=(Integer)v.get(0);
		this.origine=(Entrepot)v.get(1);
		this.destination=(Entrepot)v.get(2);
		this.intermediaire=(Entrepot)v.get(3);
		this.distance=(Float)v.get(4);
	}
	
	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// id, origine, destination, intermediaire, distance
		v.add(id);
		v.add(origine);
		v.add(destination);
		v.add(intermediaire);
		v.add(distance);
		
		return v;
	}


	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id d'un utilisateur -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	// ----- Ins�rer l'entrep�t d'origine -----//
	public void setOrigine(Entrepot origine) {
		this.origine = origine;
	}
	
	// ----- Ins�rer l'entrep�t de destination -----//
	public void setDestination(Entrepot destination) {
		this.destination = destination;
	}
	
	// ----- Ins�rer l'entrep�t interm�diaire -----//
	public void setIntermediaire(Entrepot intermediaire) {
		this.intermediaire = intermediaire;
	}
	
	// ----- Ins�rer la longueur de la route -----//
	public void setDistance(Float distance) {
		this.distance = distance;
	}

	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}

	//----- R�cup�ration de l'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	//----- R�cup�ration de la destination -----//
	public Entrepot getDestination(){
		return this.destination;
	}
	
	//----- R�cup�ration de l'entrep�t interm�diaire -----//
	public Entrepot getIntermediaire(){
		return this.intermediaire;
	}
	
	// ----- R�cup�rer la longueur de la route -----//
	public Float getDistance() {
		return this.distance;
	}
}
