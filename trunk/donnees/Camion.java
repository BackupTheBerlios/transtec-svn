package donnees;

import java.util.Vector;

//----- Classe regroupant tous les attributs d'un camion, ainsi que les m�thodes propres � la manipulation de cet objet -----//

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
	
	
	// Constructeur avec tous les param�tres
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

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
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
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer le num�ro du camion -----//
	public void setNumero(String numero){
		this.numero=numero;
	}
	
	//----- Ins�rer l'�tat du camion -----//
	public void setDispo(Integer dispo){
		this.dispo=dispo;
	}
	
	//----- R�cup�ration du volume du camion -----//
	public void setVolume(Integer volume){
		this.volume=volume;
	}
	
	//----- Ins�rer l'id du cahuffeur -----//
	public void setIdChauffeur(Integer idChauffeur){
		this.idChauffeur=idChauffeur;
	}
	
	//----- Ins�rer l'id de l'entrep�t de destination -----//
	public void setIdDestination(Integer idDestination){
		this.idDestination=idDestination;
	}
	
	//----- Ins�rer l'id de l'entrep�t d'origine -----//
	public void setIdOrigine(Integer idOrigine){
		this.idOrigine=idOrigine;
	}
	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�rer le num�ro du camion -----//
	public String getNumero(){
		return this.numero;
	}
	
	//----- R�cup�ration de l'�tat du camion -----//
	public Integer getDispo(){
		return this.dispo;
	}
	
	//----- R�cup�ration du volume du camion -----//
	public Integer getVolume(){
		return this.volume;
	}
	
	//----- R�cup�ration de l'id du cahuffeur -----//
	public Integer getIdChauffeur(){
		return this.idChauffeur;
	}
	
	//----- R�cup�ration de l'id de l'entrep�t de destination -----//
	public Integer getIdDestination(){
		return this.idDestination;
	}
	
	//----- R�cup�ration de l'id de l'entrep�t d'origine -----//
	public Integer getIdOrigine(){
		return this.idOrigine;
	}
}
