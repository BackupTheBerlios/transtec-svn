package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les méthodes propres à la manipulation de cet objet -----//

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
	
	//----- Insérer l'id du chargement -----//
	public void setId(int id){
		this.id=id;
	}
	
	//----- Récupération de l'id du chargement -----//
	public int getId(){
		return this.id;
	}
	
	//----- Récupération de l'id du camion -----//
	public int getIdCamion(){
		return this.idCamion;
	}
	
	//----- Récupération du nombre de colis -----//
	public int getNbColis(){
		return this.nbColis;
	}
	
	//----- Récupération du volume du chargement -----//
	public float getVolChargement(){
		return this.volChargement;
	}
	
	//----- Récupération de l'id du préparateur -----//
	public int getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	//----- Récupération de la date de création du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
}