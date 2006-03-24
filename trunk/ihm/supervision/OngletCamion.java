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
				
		// Transmission de l'entrepot o� l'on se trouve
		this.entActuel = entActuel;
		
		// Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		// Liste des camions : noms des colonnes.
		nomColonnes.add("ID");
		nomColonnes.add("Num�ro");
		nomColonnes.add("Disponibilit�");
		nomColonnes.add("Largeur");
		nomColonnes.add("Hauteur");
		nomColonnes.add("Profondeur");
		nomColonnes.add("Volume");
		nomColonnes.add("Volume Disponible");
		nomColonnes.add("Origine");
		nomColonnes.add("Destination");

		// Construction du vector de donn�es
		listerCamions();

		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();
		
		// On cache les colonnes d�sir�es
		table.removeColumn(table.getColumnModel().getColumn(6));
		
		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
		
		// Bouton Mise � jour
		boutUpdate.addActionListener(this);
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
				
				// Action li�e au bouton de modification d'un camion
				if(source==boutModifier){
				
					// On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Camion c = new Camion(cVect);
	
					// On affiche l'invite de modification
					new AjoutModifCamion(c,this,tableCamions);
					
					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un camion
				else if(source==boutSupprimer){
					// Suppression de la base de donn�es
					tableCamions.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));
	
					// Mise � jour du tableau
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
			// Mise � jour de la liste
			else if(source==boutUpdate){
				
				// Suppression du tableau
				remove(scrollPane);
				
				// On vide la liste des �l�ments du tableau
				donnees.removeAllElements();				
				
				// Reconstructtion de la liste � afficher
				listerCamions();
				
		        // Reconstruction du tableau
		        construireTableau();
		        
		        // Mise � jour de la fen�tre
		        updateUI();
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	// M�thode remplissant le Vector de donn�es du tableau par lecture dans la base de donn�es
	private void listerCamions(){
        try{
	        // On r�cup�re les camions de la base de donn�es et on les affiche
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
