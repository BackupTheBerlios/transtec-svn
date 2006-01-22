package donnees;

//----- Classe regroupant tous les attributs d'un camion, ainsi que les méthodes propres à la manipulation de cet objet -----//

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
	
	//----- Insérer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération de l'état du camion -----//
	public Integer getEtat(){
		return this.etat;
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
