package donnees;

import java.util.Vector;

//----- Classe regroupant tous les attributs d'un camion, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Camion {
	private Integer id;
	private String numero;
	private Integer dispo;
	private Integer volume;
	private Integer idChauffeur;
	private Integer idOrigine;
	private Integer idDestination;
	
	public final static int DISPONIBLE = 0;
	public final static int REPARATION = 1;
	public final static int LIVRAISON = 2;
	
	
	// Constructeur avec tous les paramètres
	public Camion(Integer id, String numero, Integer dispo,  Integer volume, Integer idChauffeur, Integer idOrigine, Integer idDestination){
		this.id = id;
		this.numero=numero;
		this.dispo=dispo;
		this.volume=volume;
		this.idChauffeur=idChauffeur;
		this.idOrigine=idOrigine;
		this.idDestination=idDestination;
	}
	
	// Constructeur n'utilisant pas l'ID
	public Camion(String numero, Integer dispo,  Integer volume, Integer idChauffeur, Integer idOrigine, Integer idDestination){
		this.numero=numero;
		this.dispo=dispo;
		this.volume=volume;
		this.idChauffeur=idChauffeur;
		this.idOrigine=idOrigine;
		this.idDestination=idDestination;
	}
	
	// Constructeur vide
	public Camion(){
		
	}
	
	// Constructeur utilisant un Vector
	public Camion(Vector v){
		this.id = (Integer)v.get(0);
		this.numero=(String)v.get(1);
		this.dispo=(Integer)v.get(2);
		this.volume=(Integer)v.get(3);
		this.idChauffeur=(Integer)v.get(4);
		this.idOrigine=(Integer)v.get(5);
		this.idDestination=(Integer)v.get(6);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, numero, dispo, volume, chauffeur, origine, destination
		v.add(id);
		v.add(numero);
		v.add(dispo);
		v.add(volume);
		v.add(idChauffeur);
		v.add(idOrigine);
		v.add(idDestination);

		return v;
	}
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer le numéro du camion -----//
	public void setNumero(String numero){
		this.numero=numero;
	}
	
	//----- Insérer l'état du camion -----//
	public void setDispo(Integer dispo){
		this.dispo=dispo;
	}
	
	//----- Récupération du volume du camion -----//
	public void setVolume(Integer volume){
		this.volume=volume;
	}
	
	//----- Insérer l'id du cahuffeur -----//
	public void setIdChauffeur(Integer idChauffeur){
		this.idChauffeur=idChauffeur;
	}
	
	//----- Insérer l'id de l'entrepôt de destination -----//
	public void setIdDestination(Integer idDestination){
		this.idDestination=idDestination;
	}
	
	//----- Insérer l'id de l'entrepôt d'origine -----//
	public void setIdOrigine(Integer idOrigine){
		this.idOrigine=idOrigine;
	}
	
	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupérer le numéro du camion -----//
	public String getNumero(){
		return this.numero;
	}
	
	//----- Récupération de l'état du camion -----//
	public Integer getDispo(){
		return this.dispo;
	}
	
	//----- Récupération du volume du camion -----//
	public Integer getVolume(){
		return this.volume;
	}
	
	//----- Récupération de l'id du cahuffeur -----//
	public Integer getIdChauffeur(){
		return this.idChauffeur;
	}
	
	//----- Récupération de l'id de l'entrepôt de destination -----//
	public Integer getIdDestination(){
		return this.idDestination;
	}
	
	//----- Récupération de l'id de l'entrepôt d'origine -----//
	public Integer getIdOrigine(){
		return this.idOrigine;
	}
}
