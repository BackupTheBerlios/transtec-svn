package donnees;



import java.sql.Timestamp;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un colis, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Colis {

	private Integer id;
	private Integer Entrepot;
	private String code_barre;
	private Integer idExpediteur;
	private Integer idDestinataire;
	private Integer idUtilisateur;
	private String poids;
	private Timestamp date_envoie;
	private Integer fragilite;
	private Integer forme;
	private Integer modele;
	/*private String hauteur;
	private String largeur;
	private String profondeur;
	private String forme1;
	private String modele1;
	private String fragilite1;
	private String code_barre1;*/
	private String lieu;
	private String valeur_declaree;

	// Constructeur vide
	/*public Colis(){
	}*/
	
	public Colis(String code_barre,Integer modele,Integer Entrepot,String poids,Timestamp date_envoie,String valeur_declaree,Integer fragilite,String lieu)
	{
		this.code_barre = code_barre;
		this.modele=modele;
		this.Entrepot=Entrepot;
		this.poids=poids;
		this.date_envoie=date_envoie;
		this.valeur_declaree=valeur_declaree;
		this.fragilite=fragilite;
		this.lieu=lieu;
		
		
	}
	
	// Constructeur utilisant des paramètres pour chaque champ
	/*public Colis(String code_barre,Integer idExpediteur,Integer idDestinataire,Integer idUtilisateur,String poids , Timestamp date_envoie, Integer fragilite, Integer forme, Integer modele,String hauteur,String largeur,String profondeur){
		this.code_barre = code_barre;
		this.forme=forme;
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoie=date_envoie;
		this.idExpediteur=idExpediteur;
		this.idDestinataire=idDestinataire;
		this.idUtilisateur=idUtilisateur;
		this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;
	}*/
	
	/*public Colis(String code_barre1, String forme1, String modele1, String poids,String fragilite1){
		this.code_barre1 = code_barre1;
		this.forme1=forme1;
		this.modele1=modele1;
		this.fragilite1=fragilite1;
		this.poids=poids;
	}*/

	// Constructeur utilisant un Vector
	/*public colis(Vector v){
		this.code_barre=(String)v.get(0);
		this.forme=(Integer)v.get(1);
		this.modele=(Integer)v.get(2);
		this.fragilite=(Integer)v.get(3);
		this.poids=(String)v.get(4);
		this.date_envoie=(String)v.get(5);
		this.expéditeur = (String)v.get(6);
		this.destinataire = (String)v.get(7);
		this.largeur = (String)v.get(8);
		this.hauteur = (String)v.get(9);
		this.profondeur = (String)v.get(10);
		
	}*/
	public Colis(Vector v){
		this.code_barre1=(String)v.get(0);
		this.forme1=(String)v.get(1);
		this.modele1=(String)v.get(2);
		this.fragilite1=(String)v.get(3);
		this.poids=(String)v.get(4);
		
		
	}
	

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
		v.add(date_envoie);
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
		// ordre : numero, etat, volume, chauffeur, destination, origine, ID
		v.add(code_barre1);
		v.add(forme1);
		v.add(modele1);
		v.add(poids);
		v.add(fragilite1);
		
		return v;
	}
	
	public Colis changement_colis(Colis col){
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
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	
	
	//----- Récupération de l'id du colis -----//
	public Integer getId(){
		return id;
	}
	
	//----- Récupération du poids du colis -----//
	public String getPoids(){
		return poids;
	}
	public String getCode_barre(){
		return code_barre;
	}
	
	
	public String getValeurDeclaree(){
		return valeur_declaree;
	}
	
	public String getLieu(){
		return lieu;
	}
	
	//----- Récupération de la date de création du colis -----//
	public Timestamp getDate(){
		return date_envoie;
	}
	
	//----- Récupération de la fragilité du colis -----//
	public Integer getFragilite(){
		return fragilite;
	}
	public Integer getEntrepot(){
		return Entrepot;
	}
	public Integer getModele(){
		return modele;
	}
	public Integer getForme(){
		return forme;
	}
	public String getHauteur(){
		return hauteur;
	}
	public String getLargeur(){
		return largeur;
	}
	public String getProfondeur(){
		return profondeur;
	}
	
	//----- Récupération de la valeur déclarée du colis -----//
	//public float getValeurDeclaree(){
		//return valeurDeclaree;
	//}
	
	//----- Récupération du lieu du colis -----//
	//public int getLieu(){
		//return lieu;
	//}
	
	//----- Récupération de l'id de l'expéditeur -----//
	public Integer getIdExpediteur(){
		return this.idExpediteur;
	}
	
	//----- Récupération de l'id du destinataire -----//
	public Integer getIdDestinataire(){
		return this.idDestinataire;
	}
	
	//----- Récupération de l'id de l'utilisateur -----//
	public Integer getIdUtilisateur(){
		return this.idUtilisateur;
	}
}
