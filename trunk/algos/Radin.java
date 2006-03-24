package algos;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import donnees.Camion;
import donnees.Destination;
//import donnees.Preparation;

//Classe permettant de répartir des camions selon des destinations en maximisant
//la satisfaction du client
public final class Radin {

	// Fonction de répartition des camions
	public final static Vector calculer(Vector listeCamions, Vector listeDestinations){
		Vector ret = new Vector();
		
		// On vérifie que les listes ne sont pas vides
		if(listeCamions.size()>0 && listeDestinations.size()>0){
			
			// On trie les listes de camions et destinations par volume.
			Collections.sort(listeDestinations,ORDRE_VOLUME_DEST);
			Collections.reverse(listeDestinations);
			Collections.sort(listeCamions,ORDRE_VOLUME_CAMION);
			Collections.reverse(listeCamions);
			
			
			
			
			
			
			

		}
		// Si l'une des deux listes est vide
		else System.out.println("ERREUR\nClasse PereNoel : liste vide !");
		
		// On renvoie la liste des Preparations créées
		return ret;
	}
	
	// Créer une préparation à partir d'un camion et d'une destination
/*	private static Preparation creerPrep(Camion c, Destination d){
		Preparation p = new Preparation();
		
		p.setDestination(d.getEntrepot());
		float volume = Math.min(c.getVolume().floatValue(),d.getVolume().floatValue());
		p.setVolume(new Float(volume));
		p.setCamion(c);
		
		return p;
	}*/
	
	// Comparateur sur les Destinations, utilisant leur Volume
	private static final Comparator ORDRE_VOLUME_DEST = new Comparator(){
		public int compare(Object o1, Object o2){
			int ret;
			
			Destination d1 = (Destination) o1;
			Destination d2 = (Destination) o2;
			
			// On renvoie le résultat de la comparaison des volumes
			ret = d1.getVolume().compareTo(d2.getVolume());
			
			return ret;
		}
	};	
	
	// Comparateur sur les Camions, utilisant leur Volume
	private static final Comparator ORDRE_VOLUME_CAMION = new Comparator(){
		public int compare(Object o1, Object o2){
			int ret;
			
			Camion c1 = (Camion) o1;
			Camion c2 = (Camion) o2;
			
			// On renvoie le résultat de la comparaison des volumes
			ret = c1.getVolume().compareTo(c2.getVolume());
			
			return ret;
		}
	};	
	
}
