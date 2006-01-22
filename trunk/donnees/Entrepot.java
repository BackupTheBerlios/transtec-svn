package donnees;

//----- Classe regroupant tous les attributs d'un entrep�t, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Entrepot {
	private int id;
	private Localisation localisation;
	private String telephone;
	
	public Entrepot(String adresse, String codePostal, String ville, String telephone){
		localisation=new Localisation(adresse, codePostal, ville);
		this.telephone=telephone;
	}
	
	//----- Ins�rer l'id de l'entrep�t -----//
	public void setId(int id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id -----//
	public int getId(){
		return this.id;
	}
	
	//----- R�cup�ration de l'adresse -----//
	public String getAdresse(){
		return this.localisation.getAdresse();
	}
	
	//----- R�cup�ration du code postal -----//
	public String getCodePostal(){
		return this.localisation.getCodePostal();
	}
	
	//----- R�cup�ration de la ville -----//
	public String getVille(){
		return this.localisation.getVille();
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
