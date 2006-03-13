package donnees;



import java.sql.Timestamp;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un colis, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Colis {

	private Integer id;
	private Entrepot entrepot;
	private String code_barre;
	private Personne expediteur;
	private Personne destinataire;
	private Entrepot origine;
	private Entrepot destination;
	private Utilisateur utilisateur;
	private Integer poids;// en grammes
	private Timestamp date_envoi;
	private Integer fragilite;
	private ModeleColis modele;/////////// A VOIR
	private String valeur_declaree;
	private Integer volume, numeroDsCharg;

	// Constructeur vide
	public Colis(){
	}
	
	// Constructeur utilisant des paramètres pour chaque champ
	public Colis(Integer id, String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,ModeleColis modele, Entrepot origine, Entrepot destination, Entrepot entrepot, String valeur_declaree, Integer volume){
		this.id=id;
		this.code_barre = code_barre;		
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.entrepot=entrepot;
		this.valeur_declaree=valeur_declaree;
		this.volume=volume;
	}
	
	public Colis(String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,ModeleColis modele, Entrepot origine, Entrepot destination, Entrepot entrepot, String valeur_declaree, Integer volume){
		this.code_barre = code_barre;
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.entrepot=entrepot;
		this.valeur_declaree=valeur_declaree;
		this.volume=volume;
	}
	
	// Constructeur utilisant un Vector
	public Colis(Vector v){
		this.id=(Integer)v.get(0);
		this.code_barre=(String)v.get(1);
		this.expediteur=(Personne)v.get(2);
		this.destinataire=(Personne)v.get(3);
		this.origine=(Entrepot)v.get(4);
		this.destination=(Entrepot)v.get(5);
		this.entrepot=(Entrepot)v.get(6);
		this.utilisateur=(Utilisateur)v.get(7);
		this.poids=(Integer)v.get(8);
		this.date_envoi=(Timestamp)v.get(9);
		this.fragilite=stringToConst((String)v.get(10));
		this.modele=(ModeleColis)v.get(11);
		
		//A Supprimer ou à garder ?
		ModeleColis m=new ModeleColis(FormeToConst((String)v.get(12)),ModeleToConst((String)v.get(13)));
		
		this.valeur_declaree=(String)v.get(14);	
		this.volume=(Integer)v.get(15);	
		this.numeroDsCharg=(Integer)v.get(16);
	}

	public Vector toVector(){
		Vector v = new Vector();

		v.add(id);
		v.add(code_barre);
		v.add(expediteur);
		v.add(destinataire);
		v.add(this.origine);
		v.add(destination);
		v.add(this.entrepot);
		v.add(utilisateur);
		v.add(poids);
		v.add(date_envoi);
		v.add(constToString(fragilite));
		v.add(modele);
		v.add(constToForme(modele.getForme()));
		//v.add(constToModele(modele.getModele()));
		v.add(valeur_declaree);
		v.add(volume);
		v.add(this.numeroDsCharg);
		return v;
	}
	
	private static Integer ModeleToConst(String s){
		Integer ret=null;
		
		if(s.equals("Modèle 1")) ret=new Integer(0);
		else if(s.equals("Modèle 2")) ret=new Integer(1);
		else if(s.equals("Modèle 3")) ret=new Integer(2);
		else if(s.equals("Personalisé")) ret=new Integer(2);

		return ret;
	}
	private static Integer FormeToConst(String s){
		Integer ret=null;
		
		if(s.equals("Cube")) ret=new Integer(0);
		else if(s.equals("Pavé")) ret=new Integer(1);
		else if(s.equals("Cylindre")) ret=new Integer(2);
		
		return ret;
	}
	
	private static Integer stringToConst(String s){
		Integer ret=null;
		
		if(s.equals("Peu")) ret=new Integer(0);
		else if(s.equals("Moyen")) ret=new Integer(1);
		else if(s.equals("Trés")) ret=new Integer(2);
		

		return ret;
	}
	public static String constToForme (Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case 0:
			ret=new String("Cube");
			break;
			
		case 1:
			ret=new String("Pavé");
			break;
			
		case 2:
			ret=new String("Cylindre");
			break;
		
			
			
		}
		
		return ret;
	}
	
	public static String constToModele (Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case 0:
			ret=new String("Modèle 1");
			break;
			
		case 1:
			ret=new String("Modèle 2");
			break;
			
		case 2:
			ret=new String("Modèle 3");
			break;
		case 3:
			ret=new String("Personalisé");
			break;
			
			
		}
		
		return ret;
	}
	
	public static String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case 0:
			ret=new String("Peu");
			break;
			
		case 1:
			ret=new String("Moyen");
			break;
			
		case 2:
			ret=new String("Trés");
			break;
			
		}
		
		return ret;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération de l'id du colis -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération du poids du colis -----//
	public Integer getPoids(){
		return this.poids;
	}
	
	public String getCode_barre(){
		return this.code_barre;
	}	
	
	public String getValeurDeclaree(){
		return this.valeur_declaree;
	}
	
	//----- Récupération de la date de création du colis -----//
	public Timestamp getDate(){
		return this.date_envoi;
	}
	
	//----- Récupération de la fragilité du colis -----//
	public Integer getFragilite(){
		return this.fragilite;
	}
	public Entrepot getEntrepot(){
		return this.entrepot;
	}
	public ModeleColis getModele(){
		return this.modele;
	}
	
	//----- Récupération de l'id de l'expéditeur -----//
	public Personne getExpediteur(){
		return this.expediteur;
	}
	
	//----- Récupération de l'id du destinataire -----//
	public Personne getDestinataire(){
		return this.destinataire;
	}
	
	//----- Récupération de l'id de l'utilisateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	//----- Récupération de l'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	//----- Récupération de la destination -----//
	public Entrepot getDestination(){
		return this.destination;
	}
	
	// Récupération du volume du colis
	public Integer getVolume(){
		return this.volume;
	}	
	
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'un Colis : on affiche son code barre
	public String toString(){
		return code_barre;
	}
	
	public void setNumeroDsCharg(Integer numeroDsCharg){
		this.numeroDsCharg=numeroDsCharg;
	}
	
	public Integer getNumeroDsCharg(){
		return this.numeroDsCharg;
	}
}
