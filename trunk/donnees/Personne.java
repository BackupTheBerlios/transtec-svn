package donnees;

public class Personne{
	private Integer id;
	private String nom;
	private String prenom;
	private Localisation localisation;
	private String mail;
	private String telephone;
	
	// Constructeur utilisant des paramètres pour chaque champ
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
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'Id -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer la localisation -----//
	public void setLocalisation(Localisation localisation){
		this.localisation=localisation;
	}
	
	//----- Insérer l'adresse mail ----//
	public void setMail(String mail){
		this.mail=mail;
	}
	
	//----- Insérer le nom -----//
	public void setNom(String nom){
		this.nom=nom;
	}
	
	//----- Insérer le prénom -----//
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	
	//----- Insérer le numéro de téléphone -----//
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}

	/****** Méthodes de lecture ******/
	
	//----- Récupérer l'id -----//
	public Integer getId(){
		return this.id;
	}

	//----- Récupération du nom -----//
	public String getNom(){
		return this.nom;
	}
	
	//----- Récupération du prénom -----//
	public String getPrenom(){
		return this.prenom;
	}
	
	//----- Récupération du numéro de téléphone -----//
	public String getTelephone(){
		return this.telephone;
	}
	
	//----- Récupération de l'adresse mail ----//
	public String getMail(){
		return this.mail;
	}
	
	//----- Récupération de la localisation -----//
	public Localisation getLocalisation(){
		return this.localisation;
	}
}
