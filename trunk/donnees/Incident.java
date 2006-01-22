package donnees;

import java.sql.Timestamp;
//import java.util.Date;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un Incident, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Incident {
	private Integer id;
	private Integer idColis;
	private Timestamp date;
	private Integer etat;
	private String description;
	private Integer idUtilisateur;
	private Integer type;
	
	public Incident(Integer id,Integer idColis, Timestamp date, Integer etat, String description, Integer idUtilisateur, Integer type){
		this.id=id;
		this.idColis=idColis;
		this.date=date;
		this.etat=etat;
		this.description=description;
		this.idUtilisateur=idUtilisateur;
		this.type=type;
	}
	
	public Incident(Timestamp date,String description,Integer id)
	{
		this.description=description;
		this.date=date;
		this.id = id;
	}
	
	public Incident(Vector v){
		
		this.id=(Integer)v.get(0);
		this.idColis=(Integer)v.get(1);
		this.date=(Timestamp)v.get(2);
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
	
	//----- Insérer l'id du colis -----//
	public void setIdColis(Integer idColis){
		this.idColis=idColis;
	}
	
	//----- Insérer l'id du créateur de la fiche -----//
	public void setIdUtilisateur(Integer idUtilisateur){
		this.idUtilisateur=idUtilisateur;
	}
	
	//----- Insérer la description -----//
	public void setDescription(String description){
		this.description=description;
	}
	
	//----- Insérer le type d'incident -----//
	public void setType(Integer type){
		this.type=type;
	}
	
	//----- Insérer l'état de l'incident -----//
	public void setEtat(Integer etat){
		this.etat=etat;
	}
	
	//----- Insérer la date de création de l'incident -----//
	public void setDate(Timestamp date){
		this.date=date;
	}
	
	
	/****** Méthodes de lecture ******/
		
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
	public Timestamp getDate(){
		return this.date;
	}
}
