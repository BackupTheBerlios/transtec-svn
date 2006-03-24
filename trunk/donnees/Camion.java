package donnees;

import java.util.Vector;

/*
 * Classe regroupant tous les attributs d'un camion, ainsi que
 * les méthodes propres à la manipulation de cet objet
 */

public class Camion implements Comparable {
	private Integer id;
	private String numero;
	private Integer disponibilite;
	private Float largeur, hauteur, profondeur, volume, volumeDispo;
	private Entrepot origine, destination;

	// Constantes décrivant la disponibilité du camion
	public final static int DISPONIBLE = 0;
	public final static int REPARATION = 1;
	public final static int LIVRAISON = 2;

	// Constructeur avec tous les paramètres sauf le volume
	public Camion(Integer id, String numero, Integer disponibilite,
			Float largeur, Float hauteur, Float profondeur, Entrepot origine, Entrepot destination) {
		this.id = id;
		this.numero = numero;
		this.disponibilite = disponibilite;
		this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;
		this.volume = new Float(largeur.floatValue()*hauteur.floatValue()*profondeur.floatValue());
		this.volumeDispo=this.volume;
		this.origine = origine;
		this.destination = destination;
	}
	
	// Constructeur avec tous les paramètres, volume compris
	public Camion(Integer id, String numero, Integer disponibilite,
			Float largeur, Float hauteur, Float profondeur, Float volume, Float volumeDispo, Entrepot origine, Entrepot destination) {
		this.id = id;
		this.numero = numero;
		this.disponibilite = disponibilite;
		this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;
		this.volume = volume;
		this.origine = origine;
		this.destination = destination;
		this.volumeDispo=volumeDispo;
	}

	// Constructeur n'utilisant pas l'ID
	public Camion(String numero, Integer disponibilite, Float largeur, Float hauteur, Float profondeur,
			Entrepot origine, Entrepot destination) {
		this.numero = numero;
		this.disponibilite = disponibilite;
		this.largeur=largeur;
		this.hauteur=hauteur;
		this.profondeur=profondeur;
		this.volume = new Float(largeur.floatValue()*hauteur.floatValue()*profondeur.floatValue());
		this.origine = origine;
		this.destination = destination;
		this.volumeDispo=this.volume;
	}

	// Constructeur vide
	public Camion() {

	}

	// Constructeur utilisant un Vector
	public Camion(Vector v) {
		this.id = (Integer) v.get(0);
		this.numero = (String) v.get(1);
		this.disponibilite = stringToConst((String) v.get(2));
		this.largeur=(Float) v.get(3);
		this.hauteur=(Float) v.get(4);
		this.profondeur=(Float) v.get(5);
		this.volume = (Float) v.get(6);
		this.volumeDispo=(Float) v.get(7);
		this.origine = (Entrepot) v.get(8);
		this.destination = (Entrepot) v.get(9);
	}

	// Transforme l'objet en un Vector
	public Vector toVector() {
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, numero, disponibilite, volume, origine, destination

		v.add(id);
		v.add(numero);
		v.add(constToString(disponibilite));
		v.add(largeur);
		v.add(hauteur);
		v.add(profondeur);
		v.add(volume);
		v.add(volumeDispo);
		v.add(origine);
		v.add(destination);

		return v;
	}

	/****** Méthodes d'écriture ******/

	// ----- Insérer l'id du camion -----//
	public void setId(Integer id) {
		this.id = id;
	}

	// ----- Insérer le numéro (immatriculation) du camion -----//
	public void setNumero(String numero) {
		this.numero = numero;
	}

	// ----- Insérer la disponibilité du camion -----//
	public void setDisponibilite(Integer disponibilite) {
		this.disponibilite = disponibilite;
	}
	
	// ----- Insérer la largeur du camion -----//
	public void setLargeur(Float largeur) {
		this.largeur = largeur;
	}
	
	// ----- Insérer la hauteur du camion -----//
	public void setHauteur(Float hauteur) {
		this.hauteur = hauteur;
	}
	
	// ----- Insérer la profondeur du camion -----//
	public void setProfondeur(Float profondeur) {
		this.profondeur = profondeur;
	}
	
	// ----- Insérer le volume du camion -----//
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	
	// ----- Insérer le volume disponible dans le camion -----//
	public void setVolumeDispo(Float volumeDispo) {
		this.volumeDispo = volumeDispo;
	}
	
	// ----- Insérer l'entrepôt de destination -----//
	public void setDestination(Entrepot destination) {
		this.destination = destination;
	}

	// ----- Insérer l'entrepôt d'origine -----//
	public void setOrigine(Entrepot origine) {
		this.origine = origine;
	}

	/****** Méthodes de lecture ******/

	// ----- Récupération de l'id du camion -----//
	public Integer getId() {
		return this.id;
	}

	// ----- Récupérer le numéro (immatriculation) du camion -----//
	public String getNumero() {
		return this.numero;
	}

	// ----- Récupération de la disponibilité du camion -----//
	public Integer getDisponibilite() {
		return this.disponibilite;
	}

	// ----- Récupération du volume du camion -----//
	public Float getVolume() {
		return this.volume;
	}

	// ----- Récupération du volume du camion -----//
	public Float getVolumeDispo() {
		return this.volumeDispo;
	}

	// ----- Récupération de l'entrepôt de destination -----//
	public Entrepot getDestination() {
		return this.destination;
	}

	// ----- Récupération de l'entrepôt d'origine -----//
	public Entrepot getOrigine() {
		return this.origine;
	}
	
	// ----- Récupération de la hauteur du camion -----//
	public Float getHauteur(){
		return this.hauteur;
	}
	
	// ----- Récupération de la largeur du camion -----//
	public Float getLargeur(){
		return this.largeur;
	}
	
	// ----- Récupération de la profondeur du camion -----//
	public Float getProfondeur(){
		return this.profondeur;
	}
	
	/****** Méthode de calcul interne ******/
	public void calculerVolume(){
		this.volume = new Float(largeur.floatValue()*hauteur.floatValue()*profondeur.floatValue());
	}
	
	/****** Méthodes de conversion des constantes ******/

	// Renvoyer le mot en fonction de la valeur de la constante
	private static String constToString(Integer i) {
		String ret = null;

		switch (i.intValue()) {
		case DISPONIBLE:
			ret = new String("Disponible");
			break;

		case REPARATION:
			ret = new String("En réparation");
			break;

		case LIVRAISON:
			ret = new String("En livraison");
			break;
		}

		return ret;
	}

	// Renvoyer la constante en fonction du mot
	private static Integer stringToConst(String s) {
		Integer ret = null;

		if (s.equals("Disponible"))
			ret = new Integer(DISPONIBLE);
		else if (s.equals("En réparation"))
			ret = new Integer(REPARATION);
		else if (s.equals("En livraison"))
			ret = new Integer(LIVRAISON);

		return ret;
	}

	/****** Redéfinition de méthodes génériques ******/

	// Affichage d'un utilisateur : on affiche son nom
	public String toString() {
		return numero;
	}

	/****** Méthode liée à l'implémentation de Comparable ******/

	// Permet de comparer deux objets entre eux
	public int compareTo(Object o) {
		Camion c = (Camion) o;

		// On renvoie le résultat de la comparaison de leur numéro
		return this.numero.compareToIgnoreCase(c.getNumero());
	}
}
