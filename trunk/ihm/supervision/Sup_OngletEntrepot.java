package ihm.supervision;

import java.util.Vector;
import java.awt.event.*;
import java.sql.*;

import accesBDD.AccesBDDEntrepot;

import donnees.Entrepot;

// Panneau de l'onglet de gestion des camions
public class Sup_OngletEntrepot extends Sup_Onglet implements ActionListener{
	
	private AccesBDDEntrepot tableEntrepots = new AccesBDDEntrepot(); 

	public Sup_OngletEntrepot(){
		super("Gestion des entrepôts");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des entrepôt : noms des colonnes.
		nomColonnes.add("ID");
		nomColonnes.add("ID Loc.");
        nomColonnes.add("Adresse");
        nomColonnes.add("Code Postal");
        nomColonnes.add("Ville");
        nomColonnes.add("Téléphone");

        try{
	        // On récupère les entrepôt de la base de données et on les affiche
	        Vector listeEntrepots = tableEntrepots.lister();
	        
	        for(int i=0;i<listeEntrepots.size();i++){
	        	donnees.addElement(((Entrepot)listeEntrepots.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        
 		// Construction du tableau et des fonctions qui lui sont associées
		construireTableau();
		
		// On cache les colonnes désirées
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		// On cache la première ligne
		modeleTab.removeRow(0);
		
		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
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
				
				// Action liée au bouton de modification d'un entrepôt
				if(source==boutModifier){
				
					// On récupère les données de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Entrepot ent = new Entrepot(cVect);
	
					// On affiche l'invite de modification
					Sup_AjoutModifEntrepot modifEntrepot = new Sup_AjoutModifEntrepot(ent,this,tableEntrepots);
					
					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un entrepôt
				else if(source==boutSupprimer){
					// Suppression de la base de données
					tableEntrepots.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));
	
					// Mise à jour du tableau
					supprimerLigne(ligneActive);
				}
			}
			// Ajout d'un entrepôt
			if(source==boutAjouter){
				// On affiche l'invite de saisie d'information
				Sup_AjoutModifEntrepot ajoutEntrepot = new Sup_AjoutModifEntrepot(null,this,tableEntrepots);
				
				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
