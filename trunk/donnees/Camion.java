package donnees;

import java.util.Vector;

/*
 * Classe regroupant tous les attributs d'un camion, ainsi que
 * les m�thodes propres � la manipulation de cet objet
 */

public class Camion {
	private Integer id;
	private String numero;
	private Integer disponibilite;
	private Integer volume;
	private Localisation origine;
	private Localisation destination;
	
	// Constantes d�crivant la disponibilit� du camion
	public final static int DISPONIBLE = 0;
	public final static int REPARATION = 1;
	public final static int LIVRAISON = 2;
	
	// Constructeur avec tous les param�tres
	public Camion(Integer id, String numero, Integer disponibilite,  Integer volume, Localisation origine, Localisation destination){
		this.id = id;
		this.numero=numero;
		this.disponibilite=disponibilite;
		this.volume=volume;
		this.origine=origine;
		this.destination=destination;
	}
	
	// Constructeur n'utilisant pas l'ID
	public Camion(String numero, Integer disponibilite,  Integer volume, Localisation origine, Localisation destination){
		this.numero=numero;
		this.disponibilite=disponibilite;
		this.volume=volume;
		this.origine=origine;
		this.destination=destination;
	}
	
	// Constructeur vide
	public Camion(){
		
	}
	
	// Constructeur utilisant un Vector
	public Camion(Vector v){
		this.id =(Integer)v.get(0);
		this.numero=(String)v.get(1);
		this.disponibilite=(Integer)v.get(2);
		this.volume=(Integer)v.get(3);
		this.origine=(Localisation)v.get(4);
		this.destination=(Localisation)v.get(5);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// id, numero, disponibilite, volume, chauffeur, origine, destination
		v.add(id);
		v.add(numero);
		v.add(disponibilite);
		v.add(volume);
		v.add(origine);
		v.add(destination);

		return v;
	}
	
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer le num�ro (immatriculation) du camion -----//
	public void setNumero(String numero){
		this.numero=numero;
	}
	
	//----- Ins�rer la disponibilit� du camion -----//
	public void setDisponibilite(Integer disponibilite){
		this.disponibilite=disponibilite;
	}
	
	//----- R�cup�ration du volume du camion (en m3) -----//
	public void setVolume(Integer volume){
		this.volume=volume;
	}
	
	//----- Ins�rer l'entrep�t de destination -----//
	public void setIdDestination(Localisation destination){
		this.destination=destination;
	}
	
	//----- Ins�rer l'id de l'entrep�t d'origine -----//
	public void setOrigine(Localisation origine){
		this.origine=origine;
	}
	
	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�rer le num�ro (immatriculation) du camion -----//
	public String getNumero(){
		return this.numero;
	}
	
	//----- R�cup�ration de la disponibilit� du camion -----//
	public Integer getDisponibilite(){
		return this.disponibilite;
	}
	
	//----- R�cup�ration du volume du camion (en m3) -----//
	public Integer getVolume(){
		return this.volume;
	}
	
	//----- R�cup�ration de l'entrep�t de destination -----//
	public Localisation getDestination(){
		return this.destination;
	}
	
	//----- R�cup�ration de l'entrep�t d'origine -----//
	public Localisation getOrigine(){
		return this.origine;
	}
}
