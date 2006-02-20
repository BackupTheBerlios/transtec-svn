package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Chargement {
	private Integer id;
	private Camion camion;
	private Integer nbColis;
	private Integer volChargement; //en cm3
	private Utilisateur utilisateur;
	private Timestamp date;
	private String codeBarre;
	
	public Chargement(Camion camion, Integer nbColis, Integer volChargement, Utilisateur utilisateur, Timestamp date, String codeBarre){
		this.camion=camion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.utilisateur=utilisateur;
		this.date=date;
		this.codeBarre=codeBarre;
	}
	
	public Chargement(Integer id, Camion camion, Integer nbColis, Integer volChargement, Utilisateur utilisateur, Timestamp date, String codeBarre){
		this.id=id;
		this.camion=camion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.utilisateur=utilisateur;
		this.date=date;
		this.codeBarre=codeBarre;
	}
	public Chargement(){
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
	public Camion getCamion(){
		return this.camion;
	}
	
	//----- Récupération du nombre de colis -----//
	public Integer getNbColis(){
		return this.nbColis;
	}
	
	//----- Récupération du volume du chargement (en cm3) -----//
	public Integer getVolChargement(){
		return this.volChargement;
	}
	
	//----- Récupération de l'id du préparateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- Récupération de la date de création du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
	
	//----- Récupération du code barre -----//
	public String getCodeBarre(){
		return this.codeBarre;
	}
}