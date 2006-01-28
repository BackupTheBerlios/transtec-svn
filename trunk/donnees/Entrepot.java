package donnees;

//----- Classe regroupant tous les attributs d'un entrep�t, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Entrepot {
	private Integer id;
	private Localisation localisation;
	private String telephone;
	
	// Constructeur avec tous les param�tres
	public Entrepot(String adresse, String codePostal, String ville, String telephone){
		localisation=new Localisation(adresse, codePostal, ville);
		this.telephone=telephone;
	}

	// Constructeur utilisant une instance de Localisation et une chaine de caract�res
	public Entrepot(Localisation localisation, String telephone){
		this.localisation=localisation;
		this.telephone=telephone;
	}
	
	// Constructeur utilisant une instance de Localisation et une chaine de caract�res
	public Entrepot(Integer id, String telephone, Localisation localisation){
		this.id=id;
		this.telephone=telephone;
		this.localisation=localisation;
	}
	
	
	/****** M�thodes d'�criture ******/

	//----- Ins�rer l'id de l'entrep�t -----//
	public void setId(Integer id){
		this.id=id;
	}
	

	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration du num�ro de t�l�phone -----//
	public String getTelephone(){
		return this.telephone;
	}
	
	//----- R�cup�ration du type Localisation -----//
	public Localisation getLocalisation(){
		return this.localisation;
	}
}
