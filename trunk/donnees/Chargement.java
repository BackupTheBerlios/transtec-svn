package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Chargement {
	private Integer id;
	private Integer idCamion;
	private Integer nbColis;
	private float volChargement;
	private Integer idUtilisateur;
	private Timestamp date;
	
	public Chargement(Integer idCamion, Integer nbColis, float volChargement, Integer idUtilisateur, Timestamp date){
		this.idCamion=idCamion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.idUtilisateur=idUtilisateur;
		this.date=date;
	}
	
	//----- Insérer l'id du chargement -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération de l'id du chargement -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération de l'id du camion -----//
	public Integer getIdCamion(){
		return this.idCamion;
	}
	
	//----- Récupération du nombre de colis -----//
	public Integer getNbColis(){
		return this.nbColis;
	}
	
	//----- Récupération du volume du chargement -----//
	public float getVolChargement(){
		return this.volChargement;
	}
	
	//----- Récupération de l'id du préparateur -----//
	public Integer getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	//----- Récupération de la date de création du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
}