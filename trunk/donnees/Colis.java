package donnees;



import java.sql.Timestamp;
import java.util.Vector;

//----- Classe regroupant tous les attributs d'un colis, ainsi que les m�thodes propres � la manipulation de cet objet -----//

public class Colis {

	private Integer id;
	private Entrepot entrepot;
	private String code_barre;
	private Personne expediteur;
	private Personne destinataire;
	private Entrepot destination;
	private Utilisateur utilisateur;
	private Integer poids;// en grammes
	private Timestamp date_envoi;
	private Integer fragilite;
	private ModeleColis modele;/////////// A VOIR
	private String valeur_declaree;

	// Constructeur vide
	public Colis(){
	}
	
	// Constructeur utilisant des param�tres pour chaque champ
	public Colis(Integer id, String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,ModeleColis modele, Entrepot destination, String valeur_declaree){
		this.id=id;
		this.code_barre = code_barre;		
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.valeur_declaree=valeur_declaree;
	}
	
	public Colis(String code_barre,Personne expediteur,Personne destinataire,Utilisateur utilisateur, Integer poids , Timestamp date_envoi, Integer fragilite,ModeleColis modele, Entrepot destination, String valeur_declaree){
		this.code_barre = code_barre;
		this.modele=modele;
		this.fragilite=fragilite;
		this.poids=poids;
		this.date_envoi=date_envoi;
		this.expediteur=expediteur;
		this.destinataire=destinataire;
		this.utilisateur=utilisateur;
		this.destination=destination;
		this.valeur_declaree=valeur_declaree;
	}
	
	// Constructeur utilisant un Vector
	public Colis(Vector v){
		this.id=(Integer)v.get(0);
		this.entrepot=(Entrepot)v.get(1);
		this.code_barre=(String)v.get(2);
		this.expediteur=(Personne)v.get(3);
		this.destinataire=(Personne)v.get(4);
		this.destination=(Entrepot)v.get(5);
		this.utilisateur=(Utilisateur)v.get(6);
		this.poids=(Integer)v.get(7);
		this.date_envoi=(Timestamp)v.get(8);
		this.fragilite=(Integer)v.get(9);
		this.modele=(ModeleColis)v.get(10);
		this.valeur_declaree=(String)v.get(12);	
	}

	public Vector toVector(){
		Vector v = new Vector();

		v.add(id);
		v.add(entrepot);
		v.add(code_barre);
		v.add(expediteur);
		v.add(destinataire);
		v.add(destination);
		v.add(utilisateur);
		v.add(poids);
		v.add(date_envoi);
		v.add(fragilite);
		v.add(modele);
		v.add(valeur_declaree);
		return v;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- R�cup�ration de l'id du colis -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- R�cup�ration du poids du colis -----//
	public Integer getPoids(){
		return this.poids;
	}
	public String getCode_barre(){
		return this.code_barre;
	}
	
	
	public String getValeurDeclaree(){
		return this.valeur_declaree;
	}
	
	//----- R�cup�ration de la date de cr�ation du colis -----//
	public Timestamp getDate(){
		return this.date_envoi;
	}
	
	//----- R�cup�ration de la fragilit� du colis -----//
	public Integer getFragilite(){
		return this.fragilite;
	}
	public Entrepot getEntrepot(){
		return this.entrepot;
	}
	public ModeleColis getModele(){
		return this.modele;
	}
	
	//----- R�cup�ration de l'id de l'exp�diteur -----//
	public Personne getExpediteur(){
		return this.expediteur;
	}
	
	//----- R�cup�ration de l'id du destinataire -----//
	public Personne getDestinataire(){
		return this.destinataire;
	}
	
	//----- R�cup�ration de l'id de l'utilisateur -----//
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	
	/****** Red�finition de m�thodes g�n�riques ******/
	
	// Affichage d'un Colis : on affiche son code barre
	public String toString(){
		return code_barre;
	}

}
