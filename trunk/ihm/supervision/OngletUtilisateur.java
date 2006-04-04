package ihm.supervision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import accesBDD.AccesBDD;
import accesBDD.AccesBDDUtilisateur;
import donnees.Utilisateur;

// Panneau de l'onglet de gestion des utilisateurs
public class OngletUtilisateur extends Onglet implements ActionListener{

	private AccesBDDUtilisateur tableUtilisateurs;

	public OngletUtilisateur(AccesBDD accesBDD){
		super("Gestion des utilisateurs",accesBDD);
		this.tableUtilisateurs = new AccesBDDUtilisateur(accesBDD); 
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des utilisateurs : noms des colonnes.
        nomColonnes.add("ID");
        nomColonnes.add("Login");
        nomColonnes.add("Mot de passe");
        nomColonnes.add("Type");
        nomColonnes.add("ID");
        nomColonnes.add("Adresse");
        nomColonnes.add("Code Postal");
        nomColonnes.add("Ville");
        nomColonnes.add("ID");
        nomColonnes.add("Nom");
        nomColonnes.add("Pr�nom");
        nomColonnes.add("E-mail");
        nomColonnes.add("T�l�phone");
        
		// Construction du vector de donn�es
        listerUtilisateurs();
        
		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();
		
		// On cache les colonnes contenant les ID
		table.removeColumn(table.getColumnModel().getColumn(3));
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

		// On r�cup�re le num�ro de la ligne s�lectionn�e
		int ligneSelect = table.getSelectedRow();

		// Si une ligne est s�lectionn�e, on peut la modifier ou la supprimer
		if(ligneSelect != -1){
			try{
				//On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action li�e au bouton de modification d'un utilisateur
				if(source==boutModifier){
				
					// On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Utilisateur u = new Utilisateur(cVect);

					// On affiche l'invite de modification
					new AjoutModifUtilisateur(u,this,tableUtilisateurs);

					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un utilisateur
				else if(source==boutSupprimer){
					// Suppression de la base de donn�es
					tableUtilisateurs.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));

					// Mise � jour du tableau
					supprimerLigne(ligneActive);
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}			
		}
		// Ajout d'un utilisateur
		if(source==boutAjouter){
			// On affiche l'invite de saisie d'information
			new AjoutModifUtilisateur(null,this,tableUtilisateurs);

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
			listerUtilisateurs();
			
	        // Reconstruction du tableau
	        construireTableau();
	        
			// On cache les colonnes contenant les ID
			table.removeColumn(table.getColumnModel().getColumn(3));
			table.removeColumn(table.getColumnModel().getColumn(6));
	        
	        // Mise � jour de la fen�tre
	        updateUI();
		}
	}
	
	// M�thode remplissant le Vector de donn�es du tableau par lecture dans la base de donn�es
	private void listerUtilisateurs(){
       try{
        	//On r�cup�re les utilisateurs de la base de donn�es et on les affiche
            Vector listeUtilisateurs = tableUtilisateurs.lister();
            
            for(int i=0;i<listeUtilisateurs.size();i++){
            	donnees.addElement(((Utilisateur)listeUtilisateurs.get(i)).toVector());
            }
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
	}
}
