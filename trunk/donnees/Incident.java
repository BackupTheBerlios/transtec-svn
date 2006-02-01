package donnees;

import java.sql.Timestamp;
//import java.util.Date;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un Incident, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Incident {
	private Integer id;
	private Colis colis;
	private Timestamp date;
	private Integer etat;
	private String description;
	private Utilisateur utilisateur;
	private Integer type;
	
	// Constantes décrivant l'état de l'incident
	public final static int NON_TRAITE = 0;
	public final static int EN_COURS = 1;
	public final static int TRAITE = 2;
	
	// Constantes décrivant le type d'incident (endroit où il a été saisi)
	public final static int ENTREE = 10;
	public final static int CHARGEMENT = 11;
	

	// Constructeur avec tous les paramètres
	public Incident(Integer id, Colis colis, Timestamp date, Integer etat, String description, Utilisateur utilisateur, Integer type){
		this.id=id;
		this.colis=colis;
		this.date=date;
		this.etat=etat;
		this.description=description;
		this.utilisateur=utilisateur;
		this.type=type;
	}
	
	// Constructeur sans id
	public Incident(Colis colis, Timestamp date, Integer etat, String description, Utilisateur utilisateur, Integer type){
		this.colis=colis;
		this.date=date;
		this.etat=etat;
		this.description=description;
		this.utilisateur=utilisateur;
		this.type=type;
	}
	
	// Constructeur simplifié
	public Incident(Timestamp date,String description,Integer id)
	{
		this.description=description;
		this.date=date;
		this.id = id;
	}
	
	// Constructeur basé sur un Vector
	public Incident(Vector v){
		
		this.id=(Integer)v.get(0);
		this.colis=(Colis)v.get(1);
		this.date=(Timestamp)v.get(2);
		this.etat=stringToConst((String)v.get(3));
		this.description=(String)v.get(4);
		this.utilisateur=(Utilisateur)v.get(5);
		this.type=stringToConst((String)v.get(6));		
	}
	
	// Création du Vector à partir des données
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// id, colis, date, etat, description, utilisateur, type	
		v.add(id);
		v.add(colis);
		v.add(date);
		v.add(constToString(etat));
		v.add(description);
		v.add(utilisateur);
		v.add(constToString(type));
	
		return v;
	}
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id de l'incident -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer l'id du colis -----//
	public void setColis(Colis colis){
		this.colis=colis;
	}
	
	//----- Insérer l'id du créateur de la fiche -----//
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
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
	public Colis getColis(){
		return this.colis;
	}
	
	//----- Récupération de l'id du créateur de la fiche -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
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
	
	
	/****** Méthodes diverses ******/
	
	// Changer l'état de traitement de l'incident
	public void changerEtat(){
		etat=new Integer(etat.intValue()+1);
	}
	
	
	/****** Méthodes de conversion des constantes ******/
	
	// Renvoyer le mot en fonction de la valeur de la constante
	public static String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case NON_TRAITE:
			ret=new String("Non traîté");
			break;
			
		case EN_COURS:
			ret=new String("En cours");
			break;
			
		case TRAITE:
			ret=new String("Traîté");
			break;
			
		case ENTREE:
			ret=new String("Entrée");
			break;
			
		case CHARGEMENT:
			ret=new String("Chargement");
			break;
		}
		
		return ret;
	}
	
	// Renvoyer la constante en fonction du mot
	private static Integer stringToConst(String s){
		Integer ret=null;
		
		if(s.equals("Non traîté")) ret=new Integer(NON_TRAITE);
		else if(s.equals("En cours")) ret=new Integer(EN_COURS);
		else if(s.equals("Traîté")) ret=new Integer(TRAITE);
		else if(s.equals("Entrée")) ret=new Integer(ENTREE);
		else if(s.equals("Chargement")) ret=new Integer(CHARGEMENT);

		return ret;
	}
}
