package ihm.supervision;

import java.util.Vector;
import java.awt.event.*;
import java.sql.*;

import accesBDD.AccesBDDCamion;

import donnees.Camion;

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
        nomColonnes.add("Disponibilité");
        nomColonnes.add("Volume");
        nomColonnes.add("Origine");
        nomColonnes.add("Destination");

        try{
	        // On récupère les camions de la base de données et on les affiche
	        Vector listeCamions = tableCamions.lister();
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        
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
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
