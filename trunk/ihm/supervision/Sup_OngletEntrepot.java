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
		super("Gestion des entrep�ts");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des entrep�t : noms des colonnes.
		nomColonnes.add("ID");
		nomColonnes.add("ID Loc.");
        nomColonnes.add("Adresse");
        nomColonnes.add("Code Postal");
        nomColonnes.add("Ville");
        nomColonnes.add("T�l�phone");

        try{
	        // On r�cup�re les entrep�t de la base de donn�es et on les affiche
	        Vector listeEntrepots = tableEntrepots.lister();
	        
	        for(int i=0;i<listeEntrepots.size();i++){
	        	donnees.addElement(((Entrepot)listeEntrepots.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        
 		// Construction du tableau et des fonctions qui lui sont associ�es
		construireTableau();
		
		// On cache les colonnes d�sir�es
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		// On cache la premi�re ligne
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
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			int ligneSelect = table.getSelectedRow();
	
			// Si une ligne est s�lectionn�e, on peut la modifier ou la supprimer
			if(ligneSelect != -1){
	
				// On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action li�e au bouton de modification d'un entrep�t
				if(source==boutModifier){
				
					// On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Entrepot ent = new Entrepot(cVect);
	
					// On affiche l'invite de modification
					Sup_AjoutModifEntrepot modifEntrepot = new Sup_AjoutModifEntrepot(ent,this,tableEntrepots);
					
					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un entrep�t
				else if(source==boutSupprimer){
					// Suppression de la base de donn�es
					tableEntrepots.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));
	
					// Mise � jour du tableau
					supprimerLigne(ligneActive);
				}
			}
			// Ajout d'un entrep�t
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
