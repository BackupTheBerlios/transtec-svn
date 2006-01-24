package donnees;

public class Localisation{
	private Integer id;
	private String adresse;
	private String codePostal;
	private String ville;
	
	// Constructeur avec tous les param�tres except� l'id
	public Localisation(String adresse, String codePostal, String ville){
		this.adresse=adresse;
		this.codePostal=codePostal;
		this.ville=ville;
	}
	
	// Constructeur avec tous les param�tres
	public Localisation(Integer id, String adresse, String codePostal, String ville){
		this.id=id;
		this.adresse=adresse;
		this.codePostal=codePostal;
		this.ville=ville;
	}	
	
	// Constructeur vide
	public Localisation(){
		
	}
	
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer l'adresse -----//
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}
	
	//----- Ins�rer le code postal -----//
	public void setCodePostal(String codePostal){
		this.codePostal=codePostal;
	}
	
	//----- Ins�rer la ville -----//
	public void setVille(String ville){
		this.ville=ville;
	}

	
	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cuperation de l'adresse -----//
	public String getAdresse(){
		return this.adresse;
	}
	
	//----- R�cup�ration du code postal -----//
	public String getCodePostal(){
		return this.codePostal;
	}
	
	//----- R�cup�ration de la ville -----//
	public String getVille(){
		return this.ville;
	}
}
