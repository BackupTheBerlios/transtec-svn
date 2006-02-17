package ihm.supervision;

import java.util.Vector;
import java.awt.event.*;

import accesBDD.AccesBDDUtilisateur;

import donnees.Utilisateur;

// Panneau de l'onglet de gestion des utilisateurs
public class Sup_OngletUtilisateur extends Sup_Onglet implements ActionListener{

	private AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur(); 

	public Sup_OngletUtilisateur(){
		super("Gestion des utilisateurs");
		
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
        
        try{
        	//On r�cup�re les utilisateurs de la base de donn�es et on les affiche
            Vector listeUtilisateurs = tableUtilisateurs.lister();
            
            for(int i=0;i<listeUtilisateurs.size();i++){
            	donnees.addElement(((Utilisateur)listeUtilisateurs.get(i)).toVector());
            }
        }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
        
        // Cr�ation et ajout de donn�es (EXEMPLE, � remplacer par des acc�s � la BDD)
        /*********************************
		donnees.addElement(new Utilisateur("rochef","rgreg2fds",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639").toVector());
		donnees.addElement(new Utilisateur("nicola","35sd11sdu",new Integer(0),"Sengler","Nikola�","13 Place du Moustier","94800","Villejuif","sengler@efrei.fr","0146775640").toVector());
		donnees.addElement(new Utilisateur("granger","515dpldnx",new Integer(1),"Granger","Hermione","8 Albion Road","35H12S","London","hermione@potter.uk","+4414563269").toVector());
		donnees.addElement(new Utilisateur("potter","358poop853",new Integer(2),"Potter","Harry","45 Denver Strees","369HND","Irvine","harry@potter.com","+4423654878").toVector());
		/*********************************/
        
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
					Sup_AjoutModifUtilisateur modifUtilisateur = new Sup_AjoutModifUtilisateur(u,this,tableUtilisateurs);

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
			Sup_AjoutModifUtilisateur ajoutUtilisateur = new Sup_AjoutModifUtilisateur(null,this,tableUtilisateurs);

			// On bloque l'utilisateur sur le pop-up
			setFenetreActive(false);
		}
	}
}
