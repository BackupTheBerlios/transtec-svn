package donnees;

//----- Classe regroupant tous les attributs d'un camion, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Camion {
	private Integer id;
	private Integer etat;
	private Integer volume;
	private Integer idChauffeur;
	private Integer idOrigine;
	private Integer idDestination;
	
	public Camion(Integer etat, Integer volume, Integer idChauffeur, Integer idOrigine, Integer idDestination){
		this.etat=etat;
		this.volume=volume;
		this.idChauffeur=idChauffeur;
		this.idOrigine=idOrigine;
		this.idDestination=idDestination;
	}
	
	//----- Ins�rer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'�tat du camion -----//
	public Integer getEtat(){
		return this.etat;
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
