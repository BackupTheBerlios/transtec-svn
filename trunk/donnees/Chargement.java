package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Chargement {
	public final static int EN_COURS=0;
	public final static int EFFECTUE=1;
	
	private Integer id;
	private Camion camion;
	private Integer nbColis;
	private Integer volChargement; //en cm3
	private Utilisateur utilisateur;
	private Timestamp date;
	private String codeBarre;
	private int etat;
	
	public Chargement(Camion camion, Integer nbColis, Integer volChargement, Utilisateur utilisateur, Timestamp date, String codeBarre){
		this.camion=camion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.utilisateur=utilisateur;
		this.date=date;
		this.codeBarre=codeBarre;
		this.etat=EN_COURS;
	}
	
	public Chargement(Integer id, Camion camion, Integer nbColis, Integer volChargement, Utilisateur utilisateur, Timestamp date, String codeBarre){
		this.id=id;
		this.camion=camion;
		this.nbColis=nbColis;
		this.volChargement=volChargement;
		this.utilisateur=utilisateur;
		this.date=date;
		this.codeBarre=codeBarre;
		this.etat=EN_COURS;
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
	
	//----- Récupération de l'état du chargement -----//
	public int getEtat(){
		return this.etat;
	}
	
	//----- Insérer une nouvelle date -----//
	public void setDate(Timestamp date){
		this.date=date;
	}
	
	//----- Ajouter un volume de colis et incrémentation de nombre de colis -----//
	public String ajouterVolumeColis(Float aAjouter){
		this.volChargement=new Integer(this.volChargement.intValue()+aAjouter.intValue());
		this.nbColis=new Integer(this.nbColis.intValue()+1);
		return this.volChargement.toString();
	}
	
	//----- Soustraire un volume de colis et décrémentation du nombre de colis -----//
	public String soustraireVolumeColis(Float aAjouter){
		this.volChargement=new Integer(this.volChargement.intValue()-aAjouter.intValue());
		this.nbColis=new Integer(this.nbColis.intValue()-1);
		return this.volChargement.toString();
	}
	
	//----- Mettre l'état à effectué -----//
	public void validerEtat(){
		this.etat=EFFECTUE;
	}
}