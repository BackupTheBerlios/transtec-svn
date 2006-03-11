package donnees;

import java.util.Vector;

public class Personne{
	private Integer id;
	private String nom;
	private String prenom;
	private Localisation localisation;
	private String mail;
	private String telephone;
	
	// Constructeur utilisant tous les param�tres
	public Personne(Integer id, String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=new Localisation(adresse, codePostal, ville);
		this.mail=mail;
		this.telephone=telephone;
	}
	public Personne(Integer id, String nom, String prenom,Integer id_loc,String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=new Localisation(id_loc,adresse, codePostal, ville);
		this.mail=mail;
		this.telephone=telephone;
	}
	

	// Constructeur utilisant une instance de Localisation
	public Personne(String nom, String prenom, String mail, String telephone, Localisation localisation){
		this.id=new Integer(-1);
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=localisation;
		this.mail=mail;
		this.telephone=telephone;
	}
	
	// Constructeur utilisant une instance de Localisation
	public Personne(Integer id, String nom, String prenom, String mail, String telephone, Localisation localisation){
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=localisation;
		this.mail=mail;
		this.telephone=telephone;
	}

	// Constructeur vide
	public Personne(){
		nom = new String("");
		prenom = new String("");
	}
	public Personne(Vector v){
		this.id=(Integer)v.get(0);		
		this.nom=(String)v.get(1);
		this.prenom=(String)v.get(2);
		//this.type=stringToConst((String)v.get(3));
		
		this.localisation=new Localisation((Integer)v.get(3),(String)v.get(4),(String)v.get(5),(String)v.get(6));
		this.mail=(String)v.get(7);
		this.telephone=(String)v.get(8);
		//this.personne=new Personne((Integer)v.get(8),(String)v.get(9),(String)v.get(10),(String)v.get(11),(String)v.get(12),l);
	}
	
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// id, login, motDePasse, type, nom, prenom, adresse, codePostal, ville, mail, telephone
		v.add(id);
		v.add(nom);
		v.add(prenom);
		v.add(localisation.getId());
		v.add(localisation.getAdresse());
		v.add(localisation.getCodePostal());
		v.add(localisation.getVille());
		v.add(mail);
		v.add(telephone);
		
		return v;
	}
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'Id -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer la localisation -----//
	public void setLocalisation(Localisation localisation){
		this.localisation=localisation;
	}
	
	//----- Ins�rer l'adresse mail ----//
	public void setMail(String mail){
		this.mail=mail;
	}
	
	//----- Ins�rer le nom -----//
	public void setNom(String nom){
		this.nom=nom;
	}
	
	//----- Ins�rer le pr�nom -----//
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	
	//----- Ins�rer le num�ro de t�l�phone -----//
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}

	/****** M�thodes de lecture ******/
	
	//----- R�cup�rer l'id -----//
	public Integer getId(){
		return this.id;
	}

	//----- R�cup�ration du nom -----//
	public String getNom(){
		return this.nom;
	}
	
	//----- R�cup�ration du pr�nom -----//
	public String getPrenom(){
		return this.prenom;
	}
	
	//----- R�cup�ration du num�ro de t�l�phone -----//
	public String getTelephone(){
		return this.telephone;
	}
	
	//----- R�cup�ration de l'adresse mail ----//
	public String getMail(){
		return this.mail;
	}
	
	//----- R�cup�ration de la localisation -----//
	public Localisation getLocalisation(){
		return this.localisation;
	}
}
