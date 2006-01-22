package donnees;

import java.util.Vector;
import java.util.Date;

public class Incident {
	public Integer id;
	public Integer idColis;
	public Date date;
	public Integer etat;
	public String description;
	public Integer idUtilisateur;
	public Integer type;
	
	// Constructeur utilisant des paramètres pour chaque champ
	public Incident(Integer id,Integer idColis, Date date, Integer etat, String description, Integer idUtilisateur, Integer type){
		this.id=id;
		this.idColis=idColis;
		this.date=date;
		this.etat=etat;
		this.description=description;
		this.idUtilisateur=idUtilisateur;
		this.type=type;
	}
	
	public Incident(Date date,String description,Integer id)
	{
		this.description=description;
		this.date=date;
		this.id = id;
	}
	
	public Incident(Vector v){
		
		this.id=(Integer)v.get(0);
		this.idColis=(Integer)v.get(1);
		this.date=(Date)v.get(2);
		this.etat=(Integer)v.get(3);
		this.description=(String)v.get(4);
		this.idUtilisateur=(Integer)v.get(5);
		this.type=(Integer)v.get(6);		
	}
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// id, idColis, date, etat, description, idUtilisateur, type	
		v.add(id);
		v.add(idColis);
		v.add(date);
		v.add(etat);
		v.add(description);
		v.add(idUtilisateur);
		v.add(type);
	
		return v;
	}
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id de l'incident -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération de l'id du colis -----//
	public Integer getIdColis(){
		return this.idColis;
	}
	
	//----- Récupération de l'id du créateur de la fiche -----//
	public Integer getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	//----- Récupération de la description -----//
	public String getDescription(){
		return this.description;
	}
	
	//----- Récupération du type d'incident -----//
	public Integer getType(){
		return this.type;
	}
	
	//----- Récupération de l'état de l'incident -----//
	public Integer getEtat(){
		return this.etat;
	}
	
	//----- Récupération de la date de création de l'incident -----//
	public Date getDate(){
		return this.date;
	}
}
