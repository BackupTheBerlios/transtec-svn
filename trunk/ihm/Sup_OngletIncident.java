package ihm;

import java.util.Vector;
import java.sql.Timestamp;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

import donnees.Incident;

// Panneau de l'onglet de gestion des incidents
public class Sup_OngletIncident extends Sup_Onglet implements ActionListener{
	
	JButton boutAfficher = boutAjouter;
	
	public Sup_OngletIncident(){
		super("Gestion des incidents");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des incidents : noms des colonnes. On n'ajoute volontairement pas ID qui reste ainsi caché
		nomColonnes.add("ID");
        nomColonnes.add("Colis");
        nomColonnes.add("Date");
        nomColonnes.add("Etat");
        nomColonnes.add("Description");
        nomColonnes.add("Créateur");
        nomColonnes.add("Type");
 
        // Création et ajout de données (EXEMPLE, à remplacer par des accès à la BDD)
        /*********************************/
        long time = new Time();
		donnees.addElement(new Incident(new Integer(-1),new Integer(0),new Timestamp(),new Integer(3),"Colis non trouvé lors du chargement",new Integer(27),new Integer(0)).toVector());
		/*********************************/

		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();

		// Le Bouton Ajouter est transformé en bouton d'affichage
		boutAfficher.setText("Afficher");
		boutAfficher.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// On cache le bouton Supprimer qui n'est pas utile ici
		boutSupprimer.setVisible(false);
	}

	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

		// On récupère le numéro de la ligne sélectionnée
		int ligneSelect = table.getSelectedRow();

		// Si une ligne est sélectionnée, on peut la modifier ou la supprimer
		if(ligneSelect != -1){

			// On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
			ligneActive = sorter.modelIndex(ligneSelect);

			// On récupère les données de la ligne du tableau
			Vector cVect = (Vector) modeleTab.getRow(ligneActive);
			Incident incid = new Incident(cVect);

			// Action liée au bouton de modification d'un incident
			if(source==boutModifier){
				// On affiche l'invite de modification
				Sup_AjoutModifIncident modifIncident = new Sup_AjoutModifIncident(incid,this);

				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
			
			// Action liée au bouton d'affichage des détails de l'incident
			else if(source==boutAfficher){
				
				// On affiche les détails de l'incident dans une boite de dialogue
				Sup_AffichageIncident fenAffIncident = new Sup_AffichageIncident(incid,this);

				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
		}
	}
}
