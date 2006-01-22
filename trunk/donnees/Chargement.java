package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les m�thodes propres � la manipulation de cet objet -----//

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
	
	//----- Ins�rer l'id du chargement -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id du chargement -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'id du camion -----//
	public Integer getIdCamion(){
		return this.idCamion;
	}
	
	//----- R�cup�ration du nombre de colis -----//
	public Integer getNbColis(){
		return this.nbColis;
	}
	
	//----- R�cup�ration du volume du chargement -----//
	public float getVolChargement(){
		return this.volChargement;
	}
	
	//----- R�cup�ration de l'id du pr�parateur -----//
	public Integer getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	//----- R�cup�ration de la date de cr�ation du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
}