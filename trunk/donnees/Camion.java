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
	private Entrepot origine;
	private Entrepot destination;
	
	// Constantes d�crivant la disponibilit� du camion
	public final static int DISPONIBLE = 0;
	public final static int REPARATION = 1;
	public final static int LIVRAISON = 2;
	
	// Constructeur avec tous les param�tres
	public Camion(Integer id, String numero, Integer disponibilite,  Integer volume, Entrepot origine, Entrepot destination){
		this.id = id;
		this.numero=numero;
		this.disponibilite=disponibilite;
		this.volume=volume;
		this.origine=origine;
		this.destination=destination;
	}
	
	// Constructeur n'utilisant pas l'ID
	public Camion(String numero, Integer disponibilite,  Integer volume, Entrepot origine, Entrepot destination){
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
		this.origine=new Entrepot(
				(Integer)v.get(4),
				(String)v.get(5),
				new Localisation(
						(Integer)v.get(6),
						(String)v.get(7),
						(String)v.get(8),
						(String)v.get(9)));
		this.destination=new Entrepot(
				(Integer)v.get(10),
				(String)v.get(11),
				new Localisation(
						(Integer)v.get(12),
						(String)v.get(13),
						(String)v.get(14),
						(String)v.get(15)));
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// id, numero, disponibilite, volume, origine, destination
		// Pour origine et destination :
		// id, adresse, codepostal, ville
		
		v.add(id);
		v.add(numero);
		v.add(disponibilite);
		v.add(volume);
		v.add(origine.getId());
		v.add(origine.getTelephone());
		v.add(origine.getLocalisation().getId());
		v.add(origine.getLocalisation().getAdresse());
		v.add(origine.getLocalisation().getCodePostal());
		v.add(origine.getLocalisation().getVille());
		v.add(destination.getId());
		v.add(destination.getTelephone());
		v.add(destination.getLocalisation().getId());
		v.add(destination.getLocalisation().getAdresse());
		v.add(destination.getLocalisation().getCodePostal());
		v.add(destination.getLocalisation().getVille());

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
	public void setIdDestination(Entrepot destination){
		this.destination=destination;
	}
	
	//----- Ins�rer l'id de l'entrep�t d'origine -----//
	public void setOrigine(Entrepot origine){
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
	public Entrepot getDestination(){
		return this.destination;
	}
	
	//----- R�cup�ration de l'entrep�t d'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
}
