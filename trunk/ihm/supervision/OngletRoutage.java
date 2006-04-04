package ihm.supervision;

import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.*;

import accesBDD.AccesBDD;
import accesBDD.AccesBDDRoutage;
import donnees.Route;

// Panneau de l'onglet de gestion des tables de routage
public class OngletRoutage extends Onglet implements ActionListener {
	AccesBDDRoutage tableRoutage;
	
	public OngletRoutage(AccesBDD accesBDD) {
		super("Gestion de la table de routage",accesBDD);
		this.tableRoutage = new AccesBDDRoutage(accesBDD);

		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des destinations : noms des colonnes.
		nomColonnes.add("ID");
		nomColonnes.add("Origine");
		nomColonnes.add("Destination");
		nomColonnes.add("Intermédiaire");
		nomColonnes.add("Distance");
	
		// Construction du vector de données
		listerRoutes();
		
		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();

		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
		
		// Bouton Mise à jour
		boutUpdate.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();

		try{
			// On récupère le numéro de la ligne sélectionnée
			int ligneSelect = table.getSelectedRow();
	
			// Si une ligne est sélectionnée, on peut la modifier ou la supprimer
			if (ligneSelect != -1) {
	
				// On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
				ligneActive = sorter.modelIndex(ligneSelect);
	
				// Action liée au bouton de modification d'un camion
				if (source == boutModifier) {
	
					// On récupère les données de la ligne du tableau
					Vector rVect = (Vector) modeleTab.getRow(ligneActive);
	
					Route r = new Route(rVect);
	
					// On affiche l'invite de modification
					new AjoutModifRoutage(r,this,tableRoutage,this.accesbdd);
				}
				// Suppression d'un camion
				else if (source == boutSupprimer) {
					// Suppression de la base de données
					tableRoutage.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));
	
					// Suppression de la ligne du tableau
					supprimerLigne(ligneActive);
				}
			}
			// Ajout d'un camion
			if (source == boutAjouter) {
				// On affiche l'invite de saisie d'information
				new AjoutModifRoutage(null,this,tableRoutage,this.accesbdd);
			}
			// Mise à jour de la liste
			else if(source==boutUpdate){
				
				// Suppression du tableau
				remove(scrollPane);
				
				// On vide la liste des éléments du tableau
				donnees.removeAllElements();				
				
				// Reconstructtion de la liste à afficher
				listerRoutes();
				
		        // Reconstruction du tableau
		        construireTableau();
		        
		        // Mise à jour de la fenêtre
		        updateUI();
			}
		}
		catch(Exception ex){
			System.out.println("Erreur - OngletRoutage :\n"+ex.getMessage());
		}

	}
	
	// Méthode remplissant le Vector de données du tableau par lecture dans la base de données
	private void listerRoutes(){
        try{
	        // On récupère les routes de la base de données et on les affiche
	        Vector listeRoutes = tableRoutage.lister();
	        
	        for(int i=0;i<listeRoutes.size();i++){
	        	donnees.addElement(((Route)listeRoutes.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
	}
}
