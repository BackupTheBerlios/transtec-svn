package donnees;

//----- Classe regroupant tous les attributs d'un entrepôt, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Entrepot {
	private Integer id;
	private Localisation localisation;
	private String telephone;
	
	// Constructeur avec tous les paramètres
	public Entrepot(String adresse, String codePostal, String ville, String telephone){
		localisation=new Localisation(adresse, codePostal, ville);
		this.telephone=telephone;
	}

	// Constructeur utilisant une instance de Localisation et une chaine de caractères
	public Entrepot(Localisation localisation, String telephone){
		this.localisation=localisation;
		this.telephone=telephone;
	}
	
	// Constructeur utilisant une instance de Localisation et une chaine de caractères
	public Entrepot(Integer id, String telephone, Localisation localisation){
		this.id=id;
		this.telephone=telephone;
		this.localisation=localisation;
	}
	
	
	/****** Méthodes d'écriture ******/

	//----- Insérer l'id de l'entrepôt -----//
	public void setId(Integer id){
		this.id=id;
	}
	

	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération du numéro de téléphone -----//
	public String getTelephone(){
		return this.telephone;
	}
	
	//----- Récupération du type Localisation -----//
	public Localisation getLocalisation(){
		return this.localisation;
	}
}
