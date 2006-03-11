package donnees;

import java.util.Vector;


public class Utilisateur implements Comparable{
	private String login;
	private String motDePasse;
	private Personne personne;
	private Integer type;
	private Integer id;
	
	// Constantes d�crivant les droits de l'utilisateur
	public final static int ENTREE = 0;
	public final static int PREPARATION = 1;
	public final static int SUPERVISION = 2;
	
	//Constructeur recevant toutes les donn�es en param�tres
	public Utilisateur(Integer id, String login, String motDePasse, Integer type, String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone){
		this.id=id;
		this.login=login;
		this.motDePasse=motDePasse;
		this.type=type;
		
		// Creation d'une instance de Personne
		this.personne=new Personne(new Integer(-1), nom, prenom, adresse, codePostal, ville, mail, telephone);
	}
	
	//Constructeur recevant toutes les donn�es en param�tres
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

		// ATTENTION l'ordre est tr�s important !!
		// l'ordre doit �tre :
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
	
	/****** M�thodes d'�criture ******/
	
	//----- Ins�rer l'id d'un utilisateur -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Ins�rer le login d'un utilisateur -----//
	public void setLogin(String login){
		this.login=login;
	}
	
	//----- Ins�rer le mot de passe d'un utilisateur -----//
	public void setMotDePasse(String motdePasse){
		this.motDePasse=motdePasse;
	}
	
	//----- Ins�rer le type d'un utilisateur -----//
	public void setType(Integer type){
		this.type=type;
	}
	
	//----- Ins�rer la personne li�e � un utilisateur -----//
	public void setPersonne(Personne personne){
		this.personne=personne;
	}
	

	/****** M�thodes de lecture ******/
	
	//----- R�cup�ration de l'id -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration du login -----//
	public String getLogin(){
		return this.login;
	}
	
	//----- R�cup�ration du mot de passe -----//
	public String getMotDePasse(){
		return this.motDePasse;
	}
	
	//----- R�cup�ration du type de poste -----//
	public Integer getType(){
		return this.type;
	}
	
	//----- R�cup�ration d'un type Personne -----//
	public Personne getPersonne(){
		return this.personne;
	}


	/****** Red�finition de m�thodes g�n�riques ******/
	
	// Affichage d'un utilisateur : on affiche son nom
	public String toString(){
		return new String(personne.getNom()+" "+personne.getPrenom());
	}
	
	public String toTitre(){
		return new String(personne.getNom()+" "+personne.getPrenom()+" - "+this.constToString(this.type));
	}


	/****** M�thodes priv�es de conversion des constantes ******/
	
	// Renvoyer le mot en fonction de la valeur de la constante
	private String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case ENTREE:
			ret=new String("Entr�e");
			break;
			
		case PREPARATION:
			ret=new String("Pr�paration");
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
		
		if(s.equals("Entr�e")) ret=new Integer(ENTREE);
		else if(s.equals("Pr�paration")) ret=new Integer(PREPARATION);
		else if(s.equals("Supervision")) ret=new Integer(SUPERVISION);

		return ret;
	}
	
	
	/****** M�thode li�e � l'impl�mentation de Comparable ******/
	
	// Permet de comparer deux objets entre eux
    public int compareTo(Object o) {
    	int ret;
    	
        Utilisateur u = (Utilisateur)o;
        
        // On compare les utilisateurs selon leur nom
        ret = this.personne.getNom().compareToIgnoreCase(u.getPersonne().getNom());
        
        // Si les noms sont �gaux, on compare les pr�noms
        if(ret==0)ret = this.personne.getPrenom().compareToIgnoreCase(u.getPersonne().getPrenom());
        
        return ret;
   }
}
