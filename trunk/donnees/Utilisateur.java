package donnees;

import java.util.Vector;


public class Utilisateur implements Comparable{
	private String login;
	private String motDePasse;
	private Personne personne;
	private Integer type;
	private Integer id;
	
	// Constantes décrivant les droits de l'utilisateur
	public final static int ENTREE = 0;
	public final static int PREPARATION = 1;
	public final static int SUPERVISION = 2;
	
	//Constructeur recevant toutes les données en paramètres
	public Utilisateur(Integer id, String login, String motDePasse, Integer type, String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=id;
		this.login=login;
		this.motDePasse=motDePasse;
		this.type=type;
		
		// Creation d'une instance de Personne
		this.personne=new Personne(new Integer(-1), nom, prenom, adresse, codePostal, ville, mail, telephone);
	}
	
	//Constructeur recevant toutes les données en paramètres
	public Utilisateur(Integer id, String login, String motDePasse, Integer type, Personne personne){
		this.id=id;
		this.login=login;
		this.motDePasse=motDePasse;
		this.type=type;
		
		// Creation d'une instance de Personne
		this.personne=personne;
	}
	
	// Constructeur vide
	public Utilisateur(){
		personne = new Personne();
	}
	
	// Construction utilisant un Vector
	public Utilisateur(Vector v){
		this.id=(Integer)v.get(0);		
		this.login=(String)v.get(1);
		this.motDePasse=(String)v.get(2);
		this.type=stringToConst((String)v.get(3));
		Localisation l=new Localisation((Integer)v.get(4),(String)v.get(5),(String)v.get(6),(String)v.get(7));
		this.personne=new Personne((Integer)v.get(8),(String)v.get(9),(String)v.get(10),(String)v.get(11),(String)v.get(12),l);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, login, motDePasse, type, nom, prenom, adresse, codePostal, ville, mail, telephone
		v.add(id);
		v.add(login);
		v.add(motDePasse);
		v.add(constToString(type));
		v.add(personne.getLocalisation().getId());
		v.add(personne.getLocalisation().getAdresse());
		v.add(personne.getLocalisation().getCodePostal());
		v.add(personne.getLocalisation().getVille());
		v.add(personne.getId());
		v.add(personne.getNom());
		v.add(personne.getPrenom());
		v.add(personne.getMail());
		v.add(personne.getTelephone());
		
		return v;
	}
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id d'un utilisateur -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer le login d'un utilisateur -----//
	public void setLogin(String login){
		this.login=login;
	}
	
	//----- Insérer le mot de passe d'un utilisateur -----//
	public void setMotDePasse(String motdePasse){
		this.motDePasse=motdePasse;
	}
	
	//----- Insérer le type d'un utilisateur -----//
	public void setType(Integer type){
		this.type=type;
	}
	
	//----- Insérer la personne liée à un utilisateur -----//
	public void setPersonne(Personne personne){
		this.personne=personne;
	}
	

	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupération du login -----//
	public String getLogin(){
		return this.login;
	}
	
	//----- Récupération du mot de passe -----//
	public String getMotDePasse(){
		return this.motDePasse;
	}
	
	//----- Récupération du type de poste -----//
	public Integer getType(){
		return this.type;
	}
	
	//----- Récupération d'un type Personne -----//
	public Personne getPersonne(){
		return this.personne;
	}


	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'un utilisateur : on affiche son nom
	public String toString(){
		return new String(personne.getNom()+" "+personne.getPrenom());
	}
	
	public String toTitre(){
		return new String(personne.getNom()+" "+personne.getPrenom()+" - "+this.constToString(this.type));
	}


	/****** Méthodes privées de conversion des constantes ******/
	
	// Renvoyer le mot en fonction de la valeur de la constante
	private String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case ENTREE:
			ret=new String("Entrée");
			break;
			
		case PREPARATION:
			ret=new String("Préparation");
			break;
			
		case SUPERVISION:
			ret=new String("Supervision");
			break;
		}
		
		return ret;
	}
	
	// Renvoyer la constante en fonction du mot
	private Integer stringToConst(String s){
		Integer ret=null;
		
		if(s.equals("Entrée")) ret=new Integer(ENTREE);
		else if(s.equals("Préparation")) ret=new Integer(PREPARATION);
		else if(s.equals("Supervision")) ret=new Integer(SUPERVISION);

		return ret;
	}
	
	
	/****** Méthode liée à l'implémentation de Comparable ******/
	
	// Permet de comparer deux objets entre eux
    public int compareTo(Object o) {
    	int ret;
    	
        Utilisateur u = (Utilisateur)o;
        
        // On compare les utilisateurs selon leur nom
        ret = this.personne.getNom().compareToIgnoreCase(u.getPersonne().getNom());
        
        // Si les noms sont égaux, on compare les prénoms
        if(ret==0)ret = this.personne.getPrenom().compareToIgnoreCase(u.getPersonne().getPrenom());
        
        return ret;
   }
}
