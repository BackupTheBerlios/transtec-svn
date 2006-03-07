package donnees;

import java.sql.Timestamp;

//----- Classe regroupant tous les attributs d'un chargement, ainsi que les m�thodes propres � la manipulation de cet objet -----//

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
	
	//----- Ins�rer l'id du chargement -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id du chargement -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'id du camion -----//
	public Camion getCamion(){
		return this.camion;
	}
	
	//----- R�cup�ration du nombre de colis -----//
	public Integer getNbColis(){
		return this.nbColis;
	}
	
	//----- R�cup�ration du volume du chargement (en cm3) -----//
	public Integer getVolChargement(){
		return this.volChargement;
	}
	
	//----- R�cup�ration de l'id du pr�parateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- R�cup�ration de la date de cr�ation du chargement -----//
	public Timestamp getDate(){
		return this.date;
	}
	
	//----- R�cup�ration du code barre -----//
	public String getCodeBarre(){
		return this.codeBarre;
	}
	
	//----- R�cup�ration de l'�tat du chargement -----//
	public int getEtat(){
		return this.etat;
	}
	
	//----- Ins�rer une nouvelle date -----//
	public void setDate(Timestamp date){
		this.date=date;
	}
	
	//----- Ajouter un volume de colis et incr�mentation de nombre de colis -----//
	public String ajouterVolumeColis(Float aAjouter){
		this.volChargement=new Integer(this.volChargement.intValue()+aAjouter.intValue());
		this.nbColis=new Integer(this.nbColis.intValue()+1);
		return this.volChargement.toString();
	}
	
	//----- Soustraire un volume de colis et d�cr�mentation du nombre de colis -----//
	public String soustraireVolumeColis(Float aAjouter){
		this.volChargement=new Integer(this.volChargement.intValue()-aAjouter.intValue());
		this.nbColis=new Integer(this.nbColis.intValue()-1);
		return this.volChargement.toString();
	}
	
	//----- Mettre l'�tat � effectu� -----//
	public void validerEtat(){
		this.etat=EFFECTUE;
	}
}