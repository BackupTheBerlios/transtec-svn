package donnees;

import java.util.Vector;


/*
 * Classe regroupant tous les attributs d'un camion, ainsi que
 * les méthodes propres à la manipulation de cet objet
 */

public class Camion implements Comparable{
	private Integer id;
	private String numero;
	private Integer disponibilite;
	private Integer volume;//en cm3
	private Entrepot origine;
	private Entrepot destination;
	
	// Constantes décrivant la disponibilité du camion
	public final static int DISPONIBLE = 0;
	public final static int REPARATION = 1;
	public final static int LIVRAISON = 2;
	
	// Constructeur avec tous les paramètres
	public Camion(Integer id, String numero, Integer disponibilite,  Integer volume, Entrepot origine, Entrepot destination){
		this.id = id;
		this.numero=numero;
		this.disponibilite=disponibilite;
		this.volume=volume;
		this.origine=origine;
		this.destination=destination;
	}
	
	// Constructeur n'utilisant pas l'ID
	public Camion(String numero, Integer disponibilite,  Integer volume, Entrepot origine, Entrepot destination){
		this.numero=numero;
		this.disponibilite=disponibilite;
		this.volume=volume;
		this.origine=origine;
		this.destination=destination;
	}
	
	// Constructeur vide
	public Camion(){
		
	}
	
	// Constructeur utilisant un Vector
	public Camion(Vector v){
		this.id =(Integer)v.get(0);
		this.numero=(String)v.get(1);
		this.disponibilite=stringToConst((String)v.get(2));
		this.volume=(Integer)v.get(3);
		this.origine=(Entrepot)v.get(4);
		this.destination=(Entrepot)v.get(5);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, numero, disponibilite, volume, origine, destination
		
		v.add(id);
		v.add(numero);
		v.add(constToString(disponibilite));
		v.add(volume);
		v.add(origine);
		v.add(destination);

		return v;
	}
	
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	//----- Insérer le numéro (immatriculation) du camion -----//
	public void setNumero(String numero){
		this.numero=numero;
	}
	
	//----- Insérer la disponibilité du camion -----//
	public void setDisponibilite(Integer disponibilite){
		this.disponibilite=disponibilite;
	}
	
	//----- Récupération du volume du camion (en m3) -----//
	public void setVolume(Integer volume){
		this.volume=volume;
	}
	
	//----- Insérer l'entrepôt de destination -----//
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	//----- Insérer l'id de l'entrepôt d'origine -----//
	public void setOrigine(Entrepot origine){
		this.origine=origine;
	}
	
	
	/****** Méthodes de lecture ******/
	
	//----- Récupération de l'id du camion -----//
	public Integer getId(){
		return this.id;
	}
	
	//----- Récupérer le numéro (immatriculation) du camion -----//
	public String getNumero(){
		return this.numero;
	}
	
	//----- Récupération de la disponibilité du camion -----//
	public Integer getDisponibilite(){
		return this.disponibilite;
	}
	
	//----- Récupération du volume du camion (en m3) -----//
	public Integer getVolume(){
		return this.volume;
	}
	
	//----- Récupération de l'entrepôt de destination -----//
	public Entrepot getDestination(){
		return this.destination;
	}
	
	//----- Récupération de l'entrepôt d'origine -----//
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	
	/****** Méthodes de conversion des constantes ******/
	
	// Renvoyer le mot en fonction de la valeur de la constante
	private static String constToString(Integer i){
		String ret=null;
		
		switch(i.intValue()){
		case DISPONIBLE:
			ret=new String("Disponible");
			break;
			
		case REPARATION:
			ret=new String("En réparation");
			break;
			
		case LIVRAISON:
			ret=new String("En livraison");
			break;
		}
		
		return ret;
	}
	
	// Renvoyer la constante en fonction du mot
	private static Integer stringToConst(String s){
		Integer ret=null;
		
		if(s.equals("Disponible")) ret=new Integer(DISPONIBLE);
		else if(s.equals("En réparation")) ret=new Integer(REPARATION);
		else if(s.equals("En livraison")) ret=new Integer(LIVRAISON);

		return ret;
	}
	
	
	/****** Redéfinition de méthodes génériques ******/
	
	// Affichage d'un utilisateur : on affiche son nom
	public String toString(){
		return numero;
	}

	
	/****** Méthode liée à l'implémentation de Comparable ******/
	
	// Permet de comparer deux objets entre eux
   public int compareTo(Object o) {
        Camion c = (Camion)o;
        
        // On renvoie le résultat de la comparaison de leur numéro
        return this.numero.compareToIgnoreCase(c.getNumero());
   }
}
