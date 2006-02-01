package ihm.supervision;

import java.util.Vector;
import java.sql.SQLException;
import java.awt.event.*;
import javax.swing.*;

import donnees.Incident;
import accesBDD.AccesBDDIncident;

// Panneau de l'onglet de gestion des incidents
public class Sup_OngletIncident extends Sup_Onglet implements ActionListener{
	
	JButton boutAfficher = boutAjouter;
	AccesBDDIncident tableIncidents = new AccesBDDIncident();
	
	public Sup_OngletIncident(){
		super("Gestion des incidents");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des incidents : noms des colonnes. On n'ajoute volontairement pas ID qui reste ainsi cach�
		nomColonnes.add("ID");
        nomColonnes.add("Colis");
        nomColonnes.add("Date");
        nomColonnes.add("Etat");
        nomColonnes.add("Description");
        nomColonnes.add("Cr�ateur");
        nomColonnes.add("Type");
 
        try{
	        // On r�cup�re les Incidents de la base de donn�es et on les affiche
	        Vector listeIncidents = tableIncidents.lister();
	        
	        for(int i=0;i<listeIncidents.size();i++){
	        	donnees.addElement(((Incident)listeIncidents.get(i)).toVector());
	        }
        }
        catch(SQLException e){
        	
        }

        // Cr�ation et ajout de donn�es (EXEMPLE, � remplacer par des acc�s � la BDD)
        /*********************************
		donnees.addElement(new Incident(new Integer(-1),new Integer(0),new Timestamp(System.currentTimeMillis()),new Integer(0),"Colis non trouv� lors du chargement",new Integer(27),new Integer(0)).toVector());
		donnees.addElement(new Incident(new Integer(-1),new Integer(1),new Timestamp(System.currentTimeMillis()),new Integer(1),"Colis non trouv� lors du chargement",new Integer(27),new Integer(0)).toVector());
		donnees.addElement(new Incident(new Integer(-1),new Integer(0),new Timestamp(System.currentTimeMillis()),new Integer(2),"Colis non trouv� lors du chargement",new Integer(27),new Integer(0)).toVector());
		/*********************************/
        
        
        
        

		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();

		// Le Bouton Ajouter est transform� en bouton d'affichage
		boutAfficher.setText("Afficher");
		boutAfficher.addActionListener(this);

		// Bouton Modifier
		boutModifier.addActionListener(this);

		// On cache le bouton Supprimer qui n'est pas utile ici
		boutSupprimer.setVisible(false);
	}

	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

		// On r�cup�re le num�ro de la ligne s�lectionn�e
		int ligneSelect = table.getSelectedRow();

		// Si une ligne est s�lectionn�e, on peut la modifier ou la supprimer
		if(ligneSelect != -1){

			// On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
			ligneActive = sorter.modelIndex(ligneSelect);

			// On r�cup�re les donn�es de la ligne du tableau
			Vector cVect = (Vector) modeleTab.getRow(ligneActive);
			Incident incid = new Incident(cVect);

			// Action li�e au bouton de modification d'un incident
			if(source==boutModifier){
				// On affiche l'invite de modification
				//Sup_AjoutModifIncident modifIncident = new Sup_AjoutModifIncident(incid,this,tableIncidents);
				
				// On bloque l'utilisateur sur le pop-up
				//setFenetreActive(false);

				// On autorise la modification de l'�tat si l'incident
				// n'est pas d�j� r�gl�
				if(incid.getEtat().intValue()<Incident.TRAITE){
					try{
						// On met � jour la base de donn�es
						tableIncidents.changerEtat(incid);
					}
					catch(SQLException e){
					
					}
					finally{
						// On met � jour l'objet
						incid.changerEtat();
						
						// On met � jour le tableau
						modifierLigne(incid.toVector());
					}				
				}
			}
			
			// Action li�e au bouton d'affichage des d�tails de l'incident
			else if(source==boutAfficher){
				
				// On affiche les d�tails de l'incident dans une boite de dialogue
				Sup_AffichageIncident fenAffIncident = new Sup_AffichageIncident(incid,this,tableIncidents);

				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
		}
	}
}
 