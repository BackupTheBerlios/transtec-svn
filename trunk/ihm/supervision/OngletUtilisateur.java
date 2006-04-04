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
        nomColonnes.add("Prénom");
        nomColonnes.add("E-mail");
        nomColonnes.add("Téléphone");
        
		// Construction du vector de données
        listerUtilisateurs();
        
		// Construction du tableau et des fonction qui lui sont associées
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
		
		// Bouton Mise à jour
		boutUpdate.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

		// On récupère le numéro de la ligne sélectionnée
		int ligneSelect = table.getSelectedRow();

		// Si une ligne est sélectionnée, on peut la modifier ou la supprimer
		if(ligneSelect != -1){
			try{
				//On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action liée au bouton de modification d'un utilisateur
				if(source==boutModifier){
				
					// On récupère les données de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Utilisateur u = new Utilisateur(cVect);

					// On affiche l'invite de modification
					new AjoutModifUtilisateur(u,this,tableUtilisateurs);

					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un utilisateur
				else if(source==boutSupprimer){
					// Suppression de la base de données
					tableUtilisateurs.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));

					// Mise à jour du tableau
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
		// Mise à jour de la liste
		else if(source==boutUpdate){
			
			// Suppression du tableau
			remove(scrollPane);
			
			// On vide la liste des éléments du tableau
			donnees.removeAllElements();				
			
			// Reconstructtion de la liste à afficher
			listerUtilisateurs();
			
	        // Reconstruction du tableau
	        construireTableau();
	        
			// On cache les colonnes contenant les ID
			table.removeColumn(table.getColumnModel().getColumn(3));
			table.removeColumn(table.getColumnModel().getColumn(6));
	        
	        // Mise à jour de la fenêtre
	        updateUI();
		}
	}
	
	// Méthode remplissant le Vector de données du tableau par lecture dans la base de données
	private void listerUtilisateurs(){
       try{
        	//On récupère les utilisateurs de la base de données et on les affiche
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
