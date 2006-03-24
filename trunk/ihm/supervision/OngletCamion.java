package ihm.supervision;

import java.util.Vector;
import java.awt.event.*;
import java.sql.*;

import accesBDD.AccesBDDCamion;

import donnees.Camion;
import donnees.Entrepot;

// Panneau de l'onglet de gestion des camions
public class OngletCamion extends Onglet implements ActionListener{
	
	private AccesBDDCamion tableCamions = new AccesBDDCamion(); 
	private Entrepot entActuel;

	public OngletCamion(Entrepot entActuel){
		super("Gestion des camions");
				
		// Transmission de l'entrepot où l'on se trouve
		this.entActuel = entActuel;
		
		// Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		// Liste des camions : noms des colonnes.
		nomColonnes.add("ID");
		nomColonnes.add("Numéro");
		nomColonnes.add("Disponibilité");
		nomColonnes.add("Largeur");
		nomColonnes.add("Hauteur");
		nomColonnes.add("Profondeur");
		nomColonnes.add("Volume");
		nomColonnes.add("Volume Disponible");
		nomColonnes.add("Origine");
		nomColonnes.add("Destination");

		// Construction du vector de données
		listerCamions();

		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();
		
		// On cache les colonnes désirées
		table.removeColumn(table.getColumnModel().getColumn(6));
		
		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
		
		// Bouton Mise à jour
		boutUpdate.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

		try{			
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
					new AjoutModifCamion(c,this,tableCamions);
					
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
				new AjoutModifCamion(null,this,tableCamions);
				
				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
			// Mise à jour de la liste
			else if(source==boutUpdate){
				
				// Suppression du tableau
				remove(scrollPane);
				
				// On vide la liste des éléments du tableau
				donnees.removeAllElements();				
				
				// Reconstructtion de la liste à afficher
				listerCamions();
				
		        // Reconstruction du tableau
		        construireTableau();
		        
		        // Mise à jour de la fenêtre
		        updateUI();
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	// Méthode remplissant le Vector de données du tableau par lecture dans la base de données
	private void listerCamions(){
        try{
	        // On récupère les camions de la base de données et on les affiche
	        Vector listeCamions = tableCamions.lister(this.entActuel);
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
	}
}
