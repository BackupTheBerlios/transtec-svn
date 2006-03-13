package donnees;

import java.util.Vector;

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
	
	// Constructeur vide
	public Entrepot(){
		localisation = new Localisation();
	}
	
	// Constructeur utilisant un Vector
	public Entrepot(Vector v){
		this.id =(Integer)v.get(0);
		this.localisation=new Localisation(
				(Integer)v.get(1),
				(String)v.get(2),
				(String)v.get(3),
				(String)v.get(4));
		this.telephone=(String)v.get(5);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, id localisation, adresse, code postal, ville, telephone

		v.add(id);
		v.add(localisation.getId());
		v.add(localisation.getAdresse());
		v.add(localisation.getCodePostal());
		v.add(localisation.getVille());
		v.add(telephone);

		return v;
	}

	
	/****** Méthodes d'écriture ******/

	//----- Insérer l'id de l'entrepôt -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Récupération du numéro de téléphone -----//
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}
	
	//----- Récupération du type Localisation -----//
	public void setLocalisation(Localisation localisation){
		this.localisation=localisation;
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
	
	
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'un entrepôt : on affiche sa ville
	public String toString(){
		String ret;
		
		if(localisation.getCodePostal()==null) ret = localisation.getVille();
		else ret = localisation.getVille()+" ("+localisation.getCodePostal()+")";
		
		return ret;
	}
	
	// Comparaison de deux entrepôts selon leurs villes
	public boolean equals(Object o){
		boolean ret=false;
		
		if(o instanceof Entrepot){
			Entrepot entTmp=(Entrepot)o;
			if(this.getLocalisation().getVille().equals(entTmp.getLocalisation().getVille())) ret=true;			
		}
		
		return ret;
	}
}
