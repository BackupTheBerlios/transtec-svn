package donnees;

public class Localisation{
	private Integer id;
	private String adresse;
	private String codePostal;
	private String ville;
	
	// Constructeur avec tous les paramètres excepté l'id
	public Localisation(String adresse, String codePostal, String ville){
		this.adresse=adresse;
		this.codePostal=codePostal;
		this.ville=ville;
	}
	
	// Constructeur avec tous les paramètres
	public Localisation(Integer id, String adresse, String codePostal, String ville){
		this.id=id;
		this.adresse=adresse;
		this.codePostal=codePostal;
		this.ville=ville;
	}	
	
	// Constructeur vide
	public Localisation(){
		
	}
	
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer l'adresse -----//
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}
	
	//----- Insérer le code postal -----//
	public void setCodePostal(String codePostal){
		this.codePostal=codePostal;
	}
	
	//----- Insérer la ville -----//
	public void setVille(String ville){
		this.ville=ville;
	}

	
	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récuperation de l'adresse -----//
	public String getAdresse(){
		return this.adresse;
	}
	
	//----- Récupération du code postal -----//
	public String getCodePostal(){
		return this.codePostal;
	}
	
	//----- Récupération de la ville -----//
	public String getVille(){
		return this.ville;
	}
}
