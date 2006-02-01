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
	private Entrepot destination;
	private Utilisateur utilisateur;
	private Integer poids;// en grammes
	private Timestamp date_envoi;
	private Integer fragilite;
	private Integer modele;/////////// A VOIR
	private String valeur_declaree;

	// Constructeur vide
	public Colis(){
	}
	
	// Constructeur utilisant des paramètres pour chaque champ
	public Colis(Integer id, String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,Integer modele, Entrepot destination, String valeur_declaree/*,String hauteur,String largeur,String profondeur*/){
		this.id=id;
		this.code_barre = code_barre;		
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.valeur_declaree=valeur_declaree;
	}
	
	public Colis(String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,Integer modele, Entrepot destination, String valeur_declaree/*,String hauteur,String largeur,String profondeur*/){
		this.code_barre = code_barre;
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.valeur_declaree=valeur_declaree;
	}
	
	// Constructeur utilisant un Vector
	public Colis(Vector v){
		this.id=(Integer)v.get(0);
		this.entrepot=(Entrepot)v.get(1);
		this.code_barre=(String)v.get(2);
		this.expediteur=(Personne)v.get(3);
		this.destinataire=(Personne)v.get(4);
		this.destination=(Entrepot)v.get(5);
		this.utilisateur=(Utilisateur)v.get(6);
		this.poids=(Integer)v.get(7);
		this.date_envoi=(Timestamp)v.get(8);
		this.fragilite=(Integer)v.get(9);
		this.modele=(Integer)v.get(10);
		this.valeur_declaree=(String)v.get(12);	
	}
	/*public Colis(Vector v){
		this.code_barre1=(String)v.get(0);
		this.forme1=(String)v.get(1);
		this.modele1=(String)v.get(2);
		this.fragilite1=(String)v.get(3);
		this.poids=(String)v.get(4);
		
		
	}*/
	

	// Transforme l'objet en un Vector
	/*public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// ordre : numero, etat, volume, chauffeur, destination, origine, ID
		v.add(code_barre);
		v.add(forme);
		v.add(modele);
		v.add(poids);
		v.add(fragilite);
		v.add(date_envoi);
		v.add(expéditeur);
		v.add(destinataire);
		v.add(largeur);
		v.add(hauteur);
		v.add(profondeur);

		return v;
	}*/
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// ordre : 
		v.add(id);
		v.add(entrepot);
		v.add(code_barre);
		v.add(expediteur);
		v.add(destinataire);
		v.add(destination);
		v.add(utilisateur);
		v.add(poids);
		v.add(date_envoi);
		v.add(fragilite);
		v.add(modele);
		v.add(valeur_declaree);
		return v;
	}
	
	/*public Colis changement_colis(Colis col){
		String temp,temp1,temp2="",temp3="",temp4="";
		temp = col.code_barre;
		temp1 = col.poids;
		switch(col.forme.intValue())
		{
		case 0 : temp2="cube";
		case 1 : temp2="pavé";
		case 2 : temp2="cylindre";
		
		}
		switch(col.modele.intValue())
		{
		case 0 : temp3="modèle1";
		case 1 : temp3="modèle2";
		case 2 : temp3="modèle3";
		case 3 : temp3="personalisé";
		}
		switch(col.fragilite.intValue())
		{
		case 0 : temp4="trés fragile";
		case 1 : temp4="fragile";
		case 2 : temp4="pas fragile";
		
		}
		
		Colis col1 = new Colis(temp,temp2,temp3,temp1,temp4);
		return col1;
	}*/
	
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
	public Integer getModele(){
		return this.modele;
	}
	
	/*public String getHauteur(){
		return hauteur;
	}
	public String getLargeur(){
		return largeur;
	}
	public String getProfondeur(){
		return profondeur;
	}*/
	
	//----- Récupération de la valeur déclarée du colis -----//
	//public float getValeurDeclaree(){
		//return valeurDeclaree;
	//}
	
	//----- Récupération du lieu du colis -----//
	//public int getLieu(){
		//return lieu;
	//}
	
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
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'un Colis : on affiche son code barre
	public String toString(){
		return code_barre;
	}

}
