package algos;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import donnees.Camion;
import donnees.Destination;
import donnees.Preparation;

//Classe permettant de r�partir des camions selon des destinations en minimisant
//les co�ts de transport
public final class Radin {

	// Fonction de r�partition des camions
	public final static Vector calculer(Vector listeCamions, Vector listeDestinations){
		Vector ret = new Vector();
		
		// On v�rifie que les listes ne sont pas vides
		if(listeCamions.size()>0 && listeDestinations.size()>0){
			
			// On trie les listes de camions et destinations par volume.
			Collections.sort(listeDestinations,ORDRE_VOLUME_DEST);
			Collections.reverse(listeDestinations);
			Collections.sort(listeCamions,ORDRE_VOLUME_CAMION);
			Collections.reverse(listeCamions);
			
			// On boucle sur les camions pour remplir ceux qui peuvent l'�tre totalement
			for(int indiceCamions=0;indiceCamions<listeCamions.size();indiceCamions++){
				
				Camion cCourant = (Camion)listeCamions.get(indiceCamions);
				
				// On boucle sur les destinations pour le camion courant
				for(int indiceDest=0;indiceDest<listeDestinations.size();indiceDest++){
					Destination dCourant = (Destination)listeDestinations.get(indiceDest);
					
					// Si on peut remplir le camion, on le met � jour
					if(cCourant.getVolume().compareTo(dCourant.getVolumeRestant())<=0){
						cCourant.setDisponibilite(new Integer(Camion.LIVRAISON));
						cCourant.setDestination(dCourant.getEntrepot());
						cCourant.setVolumeDispo(new Float(0));
						dCourant.setVolumeRestant(new Float(dCourant.getVolumeRestant().floatValue()-cCourant.getVolume().floatValue()));
						
						// On ajoute ce camion � la liste
						ret.add(creerPrep(cCourant,dCourant));
						
						// Pour arr�ter la boucle
						indiceDest=listeDestinations.size();
					}
				}				
			}
			
			// On retire les camions attribu�s de la liste des camions
			for(int i=0;i<ret.size();i++){
				listeCamions.remove(((Preparation)ret.get(i)).getCamion());
			}
			
			// On retrie les listes en inversant celle des 
			//	camions : les plus petits sont en t�te
			Collections.sort(listeDestinations,ORDRE_VOLUME_DEST);
			Collections.sort(listeCamions,ORDRE_VOLUME_CAMION);
			Collections.reverse(listeCamions);
			
			// On tente de remplir les plus petits camions
			for(int indiceCamions=0;indiceCamions<listeCamions.size();indiceCamions++){
				
				Camion cCourant = (Camion)listeCamions.get(indiceCamions);
				
				// On boucle sur les destinations pour le camion courant
				for(int indiceDest=0;indiceDest<listeDestinations.size();indiceDest++){
					Destination dCourant = (Destination)listeDestinations.get(indiceDest);
					
					// Si on peut remplir le camion, on le met � jour
					if(cCourant.getVolume().compareTo(dCourant.getVolumeRestant())<=0){
						cCourant.setDisponibilite(new Integer(Camion.LIVRAISON));
						cCourant.setDestination(dCourant.getEntrepot());
						cCourant.setVolumeDispo(new Float(0));
						dCourant.setVolumeRestant(new Float(dCourant.getVolumeRestant().floatValue()-cCourant.getVolume().floatValue()));
						
						// On ajoute ce camion � la liste
						ret.add(creerPrep(cCourant,dCourant));
						
						// Pour arr�ter la boucle
						indiceDest=listeDestinations.size();
					}
				}				
			}
		}
		// Si l'une des deux listes est vide
		else System.out.println("ERREUR\nClasse PereNoel : liste vide !");
		
		// On renvoie la liste des Preparations cr��es
		return ret;
	}
	
	// Cr�er une pr�paration � partir d'un camion et d'une destination
	private static Preparation creerPrep(Camion c, Destination d){
		Preparation p = new Preparation();
		
		p.setDestination(d.getEntrepot());
		float volume = Math.min(c.getVolume().floatValue(),d.getVolume().floatValue());
		p.setVolume(new Float(volume));
		p.setCamion(c);
		
		return p;
	}
	
	// Comparateur sur les Destinations, utilisant leur Volume
	private static final Comparator ORDRE_VOLUME_DEST = new Comparator(){
		public int compare(Object o1, Object o2){
			int ret;
			
			Destination d1 = (Destination) o1;
			Destination d2 = (Destination) o2;
			
			// On renvoie le r�sultat de la comparaison des volumes
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
			
			// On renvoie le r�sultat de la comparaison des volumes
			ret = c1.getVolume().compareTo(c2.getVolume());
			
			return ret;
		}
	};	
	
}
