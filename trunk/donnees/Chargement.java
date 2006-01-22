package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Chargement {
	private int id;
	private int idCamion;
	private int nbColis;
	private float volChargement;
	private int idUtilisateur;
	private Timestamp date;
	
	public Chargement(int idCamion, int nbColis, float volChargement, int idUtilisateur, Timestamp date){
		this.idCamion=idCamion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.idUtilisateur=idUtilisateur;
		this.date=date;
	}
	
	//----- Ins�rer l'id du chargement -----//
	public void setId(int id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id du chargement -----//
	public int getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'id du camion -----//
	public int getIdCamion(){
		return this.idCamion;
	}
	
	//----- R�cup�ration du nombre de colis -----//
	public int getNbColis(){
		return this.nbColis;
	}
	
	//----- R�cup�ration du volume du chargement -----//
	public float getVolChargement(){
		return this.volChargement;
	}
	
	//----- R�cup�ration de l'id du pr�parateur -----//
	public int getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	//----- R�cup�ration de la date de cr�ation du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
}