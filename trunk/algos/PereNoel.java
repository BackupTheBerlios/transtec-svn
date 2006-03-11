package algos;

import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;

import donnees.Camion;
import donnees.Destination;


// Classe permettant de répartir des camions selon des destinations en maximisant
//	la satisfaction du client
public final class PereNoel {

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
			
			//TODO : 	TRIER SELON TRANSIT OU NON
			
			// On boucle sur les camions pour remplir ceux qui peuvent l'être totalement
			for(int indiceCamions=0;indiceCamions<listeCamions.size();indiceCamions++){
				
				Camion cCourant = (Camion)listeCamions.get(indiceCamions);
				
				// On boucle sur les destinations pour le camion courant
				for(int indiceDest=0;indiceDest<listeDestinations.size();indiceDest++){
					Destination dCourant = (Destination)listeDestinations.get(indiceDest);
					
					// Si on peut remplir le camion, on le met à jour
					if(cCourant.getVolume().compareTo(dCourant.getVolume())<=0){
						cCourant.setDisponibilite(new Integer(Camion.LIVRAISON));
						cCourant.setDestination(dCourant.getEntrepot());
						
						// On ajoute ce camion à la liste
						ret.add(cCourant);
						
						// Pour arrêter la boucle
						indiceDest=listeDestinations.size();
					}
				}				
			}
			
			// On retire les camions attribués de la liste des camions
			for(int i=0;i<ret.size();i++){
				listeCamions.remove(ret.get(i));
			}
			
			// Les camions pouvant être totalement remplis le sont, on attribue 
			//	maintenant le reste des colis aux camions restants
			// On boucle sur les camions pour remplir ceux qui peuvent l'être totalement
			for(int indice=0;indice<Math.min(listeCamions.size(),listeDestinations.size());indice++){				
				Camion cCourant = (Camion)listeCamions.get(indice);
				Destination dCourant = (Destination)listeDestinations.get(indice);
				
				cCourant.setDisponibilite(new Integer(Camion.LIVRAISON));
				cCourant.setDestination(dCourant.getEntrepot());
				
				// On ajoute ce camion à la liste
				ret.add(cCourant);
			}			
		}
		// Si l'une des deux listes est vide
		else System.out.println("ERREUR\nClasse PereNoel : liste vide !");
		
		return ret;
	}
	
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
