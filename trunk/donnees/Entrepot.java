package donnees;

//----- Classe regroupant tous les attributs d'un entrepôt, ainsi que les méthodes propres à la manipulation de cet objet -----//

public class Entrepot {
	private int id;
	private Localisation localisation;
	private String telephone;
	
	public Entrepot(String adresse, String codePostal, String ville, String telephone){
		localisation=new Localisation(adresse, codePostal, ville);
		this.telephone=telephone;
	}
	
	//----- Insérer l'id de l'entrepôt -----//
	public void setId(int id){
		this.id=id;
	}
	
	//----- Récupération de l'id -----//
	public int getId(){
		return this.id;
	}
	
	//----- Récupération de l'adresse -----//
	public String getAdresse(){
		return this.localisation.getAdresse();
	}
	
	//----- Récupération du code postal -----//
	public String getCodePostal(){
		return this.localisation.getCodePostal();
	}
	
	//----- Récupération de la ville -----//
	public String getVille(){
		return this.localisation.getVille();
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
