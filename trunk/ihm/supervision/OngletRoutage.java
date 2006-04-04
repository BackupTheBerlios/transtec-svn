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
		nomColonnes.add("Interm�diaire");
		nomColonnes.add("Distance");
	
		// Construction du vector de donn�es
		listerRoutes();
		
		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();

		// Bouton Ajouter
		boutAjouter.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// Bouton Supprimer
		boutSupprimer.addActionListener(this);
		
		// Bouton Mise � jour
		boutUpdate.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();

		try{
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			int ligneSelect = table.getSelectedRow();
	
			// Si une ligne est s�lectionn�e, on peut la modifier ou la supprimer
			if (ligneSelect != -1) {
	
				// On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
				ligneActive = sorter.modelIndex(ligneSelect);
	
				// Action li�e au bouton de modification d'un camion
				if (source == boutModifier) {
	
					// On r�cup�re les donn�es de la ligne du tableau
					Vector rVect = (Vector) modeleTab.getRow(ligneActive);
	
					Route r = new Route(rVect);
	
					// On affiche l'invite de modification
					new AjoutModifRoutage(r,this,tableRoutage,this.accesbdd);
				}
				// Suppression d'un camion
				else if (source == boutSupprimer) {
					// Suppression de la base de donn�es
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
			// Mise � jour de la liste
			else if(source==boutUpdate){
				
				// Suppression du tableau
				remove(scrollPane);
				
				// On vide la liste des �l�ments du tableau
				donnees.removeAllElements();				
				
				// Reconstructtion de la liste � afficher
				listerRoutes();
				
		        // Reconstruction du tableau
		        construireTableau();
		        
		        // Mise � jour de la fen�tre
		        updateUI();
			}
		}
		catch(Exception ex){
			System.out.println("Erreur - OngletRoutage :\n"+ex.getMessage());
		}

	}
	
	// M�thode remplissant le Vector de donn�es du tableau par lecture dans la base de donn�es
	private void listerRoutes(){
        try{
	        // On r�cup�re les routes de la base de donn�es et on les affiche
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
