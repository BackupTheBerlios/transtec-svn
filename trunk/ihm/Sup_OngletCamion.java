package ihm;

import java.util.Vector;
import java.awt.event.*;

import accesBDD.AccesBDDCamion;

import donnees.Camion;
import donnees.Utilisateur;

// Panneau de l'onglet de gestion des camions
public class Sup_OngletCamion extends Sup_Onglet implements ActionListener{
	
	private AccesBDDCamion tableCamions = new AccesBDDCamion(); 

	public Sup_OngletCamion(){
		super("Gestion des camions");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des camions : noms des colonnes. On n'ajoute volontairement ID qui reste ainsi caché
		nomColonnes.add("ID");
        nomColonnes.add("Numéro");
        nomColonnes.add("Etat");
        nomColonnes.add("Volume");
        nomColonnes.add("Chauffeur");
        nomColonnes.add("Destination");
        nomColonnes.add("Appartenance");

        // On récupère les camions de la base de données et on les affiche
        Vector listeCamions = tableCamions.lister();
        
        for(int i=0;i<listeCamions.size();i++){
        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
        }       
        
        
        
        // Création et ajout de données (EXEMPLE, à remplacer par des accès à la BDD)
        /*********************************
 		donnees.addElement(new Camion(new Integer(0),"25TR76",new Integer(Camion.DISPONIBLE),new Integer(27),new Integer(0),new Integer(0),new Integer(2)).toVector());
		donnees.addElement(new Camion(new Integer(1),"1013TW78",new Integer(Camion.LIVRAISON),new Integer(12),new Integer(1),new Integer(1),new Integer(1)).toVector());
		donnees.addElement(new Camion(new Integer(2),"356LJ45",new Integer(Camion.REPARATION),new Integer(45),new Integer(0),new Integer(1),new Integer(2)).toVector());
		donnees.addElement(new Camion(new Integer(3),"654LLL1",new Integer(Camion.DISPONIBLE),new Integer(6),new Integer(2),new Integer(2),new Integer(4)).toVector());
		donnees.addElement(new Camion(new Integer(4),"M-AR1265",new Integer(Camion.LIVRAISON),new Integer(18),new Integer(1),new Integer(4),new Integer(0)).toVector());
		/*********************************/
				
		

		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();

		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

		// On récupère le numéro de la ligne sélectionnée
		int ligneSelect = table.getSelectedRow();

		// Si une ligne est sélectionnée, on peut la modifier ou la supprimer
		if(ligneSelect != -1){

			// On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
			ligneActive = sorter.modelIndex(ligneSelect);
			
			// Action liée au bouton de modification d'un camion
			if(source==boutModifier){
			
				// On récupère les données de la ligne du tableau
				Vector cVect = (Vector) modeleTab.getRow(ligneActive);
				Camion c = new Camion(cVect);

				// On affiche l'invite de modification
				Sup_AjoutModifCamion modifCamion = new Sup_AjoutModifCamion(c,this,tableCamions);
				
				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
			// Suppression d'un camion
			else if(source==boutSupprimer){
				// Suppression de la base de données
				tableCamions.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));

				// Mise à jour du tableau
				supprimerLigne(ligneActive);
			}
		}
		// Ajout d'un camion
		if(source==boutAjouter){
			// On affiche l'invite de saisie d'information
			Sup_AjoutModifCamion ajoutCamion = new Sup_AjoutModifCamion(null,this,tableCamions);
			
			// On bloque l'utilisateur sur le pop-up
			setFenetreActive(false);
		}
	}
}
