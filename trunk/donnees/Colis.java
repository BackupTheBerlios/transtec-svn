package donnees;



import java.sql.Timestamp;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un colis, ainsi que les m�thodes propres � la manipulation de cet objet -----//

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
	private Localisation lieu;
	private String valeur_declaree;

	// Constructeur vide
	public Colis(){
	}
	
	/*public Colis(Integer id, String code_barre,Integer modele,Integer Entrepot,String poids,Timestamp date_envoi,String valeur_declaree,Integer fragilite,String lieu)
	{
		this.id=id;
		this.code_barre = code_barre;
		this.modele=modele;
		this.Entrepot=Entrepot;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.valeur_declaree=valeur_declaree;
		this.fragilite=fragilite;
		this.lieu=lieu;
	}*/
	
	
	
	// Constructeur utilisant des param�tres pour chaque champ
	public Colis(Integer id, String code_barre,Integer idExpediteur,Integer idDestinataire,Integer idUtilisateur,String poids , Timestamp date_envoi, Integer fragilite,Integer modele, Integer idDestination, String valeur_declaree/*,String hauteur,String largeur,String profondeur*/){
		this.id=id;
		this.code_barre = code_barre;
		
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.idExpediteur=idExpediteur;
		this.idDestinataire=idDestinataire;
		this.idUtilisateur=idUtilisateur;
		this.idDestination=idDestination;
		this.valeur_declaree=valeur_declaree;
		/*this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;*/
	}
	
	public Colis(String code_barre,Integer idExpediteur,Integer idDestinataire,Integer idUtilisateur,String poids , Timestamp date_envoi, Integer fragilite,Integer modele, Integer idDestination, String valeur_declaree/*,String hauteur,String largeur,String profondeur*/){
		this.code_barre = code_barre;
		
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.idExpediteur=idExpediteur;
		this.idDestinataire=idDestinataire;
		this.idUtilisateur=idUtilisateur;
		this.idDestination=idDestination;
		this.valeur_declaree=valeur_declaree;
		/*this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;*/
	}
	
	/*public Colis(String code_barre1, String forme1, String modele1, String poids,String fragilite1){
		this.code_barre1 = code_barre1;
		this.forme1=forme1;
		this.modele1=modele1;
		this.fragilite1=fragilite1;
		this.poids=poids;
	}*/

	// Constructeur utilisant un Vector
	public Colis(Vector v){
		this.id=(Integer)v.get(0);
		this.Entrepot=(Integer)v.get(1);
		this.code_barre=(String)v.get(2);
		this.idExpediteur=(Integer)v.get(3);
		this.idDestinataire=(Integer)v.get(4);
		this.idDestination=(Integer)v.get(5);
		this.idUtilisateur=(Integer)v.get(6);
		this.poids=(String)v.get(7);
		this.date_envoi=(Timestamp)v.get(8);
		this.fragilite=(Integer)v.get(9);
		this.modele=(Integer)v.get(10);
		this.lieu=(String)v.get(11);
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

		// ATTENTION l'ordre est tr�s important !!
		// ordre : numero, etat, volume, chauffeur, destination, origine, ID
		v.add(code_barre);
		v.add(forme);
		v.add(modele);
		v.add(poids);
		v.add(fragilite);
		v.add(date_envoi);
		v.add(exp�diteur);
		v.add(destinataire);
		v.add(largeur);
		v.add(hauteur);
		v.add(profondeur);

		return v;
	}*/
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// ordre : numero, etat, volume, chauffeur, destination, origine, ID
		v.add(id);
		v.add(Entrepot);
		v.add(code_barre);
		v.add(idExpediteur);
		v.add(idDestinataire);
		v.add(idDestination);
		v.add(idUtilisateur);
		v.add(poids);
		v.add(date_envoi);
		v.add(fragilite);
		v.add(modele);
		v.add(lieu);
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
		case 1 : temp2="pav�";
		case 2 : temp2="cylindre";
		
		}
		switch(col.modele.intValue())
		{
		case 0 : temp3="mod�le1";
		case 1 : temp3="mod�le2";
		case 2 : temp3="mod�le3";
		case 3 : temp3="personalis�";
		}
		switch(col.fragilite.intValue())
		{
		case 0 : temp4="tr�s fragile";
		case 1 : temp4="fragile";
		case 2 : temp4="pas fragile";
		
		}
		
		Colis col1 = new Colis(temp,temp2,temp3,temp1,temp4);
		return col1;
	}*/
	
	public void setId(Integer id){
		this.id=id;
	}
	
	public void setLieu(String lieu){
		this.lieu=lieu;
	}
	
	
	//----- R�cup�ration de l'id du colis -----//
	public Integer getId(){
		return id;
	}
	
	//----- R�cup�ration du poids du colis -----//
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
	
	//----- R�cup�ration de la date de cr�ation du colis -----//
	public Timestamp getDate(){
		return date_envoi;
	}
	
	//----- R�cup�ration de la fragilit� du colis -----//
	public Integer getFragilite(){
		return fragilite;
	}
	public Integer getEntrepot(){
		return Entrepot;
	}
	public Integer getModele(){
		return modele;
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
	
	//----- R�cup�ration de la valeur d�clar�e du colis -----//
	//public float getValeurDeclaree(){
		//return valeurDeclaree;
	//}
	
	//----- R�cup�ration du lieu du colis -----//
	//public int getLieu(){
		//return lieu;
	//}
	
	//----- R�cup�ration de l'id de l'exp�diteur -----//
	public Integer getIdExpediteur(){
		return this.idExpediteur;
	}
	
	//----- R�cup�ration de l'id du destinataire -----//
	public Integer getIdDestinataire(){
		return this.idDestinataire;
	}
	
	//----- R�cup�ration de l'id de l'utilisateur -----//
	public Integer getIdUtilisateur(){
		return this.idUtilisateur;
	}
	
	public Integer getIdDestination(){
		return this.idDestination;
	}
}
