package donnees;

import java.util.Vector;

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

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
		// id, id localisation, adresse, code postal, ville, telephone

		v.add(id);
		v.add(localisation.getId());
		v.add(localisation.getAdresse());
		v.add(localisation.getCodePostal());
		v.add(localisation.getVille());
		v.add(telephone);

		return v;
	}

	
	/****** M�thodes d'�criture ******/

	//----- Ins�rer l'id de l'entrep�t -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- R�cup�ration du num�ro de t�l�phone -----//
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}
	
	//----- R�cup�ration du type Localisation -----//
	public void setLocalisation(Localisation localisation){
		this.localisation=localisation;
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
	
	
	/****** Red�finition de m�thodes g�n�riques ******/
	
	// Affichage d'un entrep�t : on affiche sa ville
	public String toString(){
		String ret;
		
		if(localisation.getCodePostal()==null) ret = localisation.getVille();
		else ret = localisation.getVille()+" ("+localisation.getCodePostal()+")";
		
		return ret;
	}
	
	// Comparaison de deux entrep�ts selon leurs villes
	public boolean equals(Object o){
		boolean ret=false;
		
		if(o instanceof Entrepot){
			Entrepot entTmp=(Entrepot)o;
			if(this.getLocalisation().getVille().equals(entTmp.getLocalisation().getVille())) ret=true;			
		}
		
		return ret;
	}
}
