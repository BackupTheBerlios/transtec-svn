package donnees;

public class Personne{
	private Integer id;
	private String nom;
	private String prenom;
	private Localisation localisation;
	private String mail;
	private String telephone;
	
	// Constructeur utilisant des param�tres pour chaque champ
	public Personne(String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=new Integer(-1);
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=new Localisation(adresse, codePostal, ville);
		this.mail=mail;
		this.telephone=telephone;
	}
	
	public Personne(Integer id, String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.localisation=new Localisation(adresse, codePostal, ville);
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
