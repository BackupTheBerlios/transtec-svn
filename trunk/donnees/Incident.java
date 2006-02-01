package donnees;

import java.sql.Timestamp;
//import java.util.Date;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un Incident, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Incident {
	private Integer id;
	private Colis colis;
	private Timestamp date;
	private Integer etat;
	private String description;
	private Utilisateur utilisateur;
	private Integer type;
	
	// Constantes d�crivant l'�tat de l'incident
	public final static int NON_TRAITE = 0;
	public final static int EN_COURS = 1;
	public final static int TRAITE = 2;
	
	// Constantes d�crivant le type d'incident (endroit o� il a �t� saisi)
	public final static int ENTREE = 10;
	public final static int CHARGEMENT = 11;
	

	// Constructeur avec tous les param�tres
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
	
	// Constructeur simplifi�
	public Incident(Timestamp date,String description,Integer id)
	{
		this.description=description;
		this.date=date;
		this.id = id;
	}
	
	// Constructeur bas� sur un Vector
	public Incident(Vector v){
		
		this.id=(Integer)v.get(0);
		this.colis=(Colis)v.get(1);
		this.date=(Timestamp)v.get(2);
		this.etat=stringToConst((String)v.get(3));
		this.description=(String)v.get(4);
		this.utilisateur=(Utilisateur)v.get(5);
		this.type=stringToConst((String)v.get(6));		
	}
	
	// Cr�ation du Vector � partir des donn�es
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
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
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id de l'incident -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer l'id du colis -----//
	public void setColis(Colis colis){
		this.colis=colis;
	}
	
	//----- Ins�rer l'id du cr�ateur de la fiche -----//
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	//----- Ins�rer la description -----//
	public void setDescription(String description){
		this.description=description;
	}
	
	//----- Ins�rer le type d'incident -----//
	public void setType(Integer type){
		this.type=type;
	}
	
	//----- Ins�rer l'�tat de l'incident -----//
	public void setEtat(Integer etat){
		this.etat=etat;
	}
	
	//----- Ins�rer la date de cr�ation de l'incident -----//
	public void setDate(Timestamp date){
		this.date=date;
	}
	
	
	/****** M�thodes de lecture ******/
		
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'id du colis -----//
	public Colis getColis(){
		return this.colis;
	}
	
	//----- R�cup�ration de l'id du cr�ateur de la fiche -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- R�cup�ration de la description -----//
	public String getDescription(){
		return this.description;
	}
	
	//----- R�cup�ration du type d'incident -----//
	public Integer getType(){
		return this.type;
	}
	
	//----- R�cup�ration de l'�tat de l'incident -----//
	public Integer getEtat(){
		return this.etat;
	}
	
	//----- R�cup�ration de la date de cr�ation de l'incident -----//
	public Timestamp getDate(){
		return this.date;
	}
	
	
	/****** M�thodes diverses ******/
	
	// Changer l'�tat de traitement de l'incident
	public void changerEtat(){
		etat=new Integer(etat.intValue()+1);
	}
	
	
	/****** M�thodes de conversion des constantes ******/
	
	// Renvoyer le mot en fonction de la valeur de la constante
	public static String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case NON_TRAITE:
			ret=new String("Non tra�t�");
			break;
			
		case EN_COURS:
			ret=new String("En cours");
			break;
			
		case TRAITE:
			ret=new String("Tra�t�");
			break;
			
		case ENTREE:
			ret=new String("Entr�e");
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
		
		if(s.equals("Non tra�t�")) ret=new Integer(NON_TRAITE);
		else if(s.equals("En cours")) ret=new Integer(EN_COURS);
		else if(s.equals("Tra�t�")) ret=new Integer(TRAITE);
		else if(s.equals("Entr�e")) ret=new Integer(ENTREE);
		else if(s.equals("Chargement")) ret=new Integer(CHARGEMENT);

		return ret;
	}
}
