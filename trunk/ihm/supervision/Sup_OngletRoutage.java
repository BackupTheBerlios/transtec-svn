package ihm.supervision;

import java.util.Vector;
import java.awt.event.*;

// Panneau de l'onglet de gestion des tables de routage
public class Sup_OngletRoutage extends Sup_Onglet implements ActionListener{

	public Sup_OngletRoutage(){
		super("Gestion de la table de routage");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des destinations : noms des colonnes. On n'ajoute volontairement pas ID qui reste ainsi caché
        nomColonnes.add("Numéro");
        nomColonnes.add("Etat");
        nomColonnes.add("Volume");
        nomColonnes.add("Chauffeur");
        nomColonnes.add("Destination");
        nomColonnes.add("Appartenance");

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
				
				//Camion c = new Camion(cVect);

				// On affiche l'invite de modification
				//Sup_AjoutModifRoutage modifRoutage = new Sup_AjoutModifRoutage(c,this);
			}
			// Suppression d'un camion
			else if(source==boutSupprimer){
				supprimerLigne(ligneActive);
			}
		}
		// Ajout d'un camion
		if(source==boutAjouter){
			// On affiche l'invite de saisie d'information
			Sup_AjoutModifRoutage ajoutRoutage = new Sup_AjoutModifRoutage(null,this);
		}
	}


}
