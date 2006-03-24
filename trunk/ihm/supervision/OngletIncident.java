package ihm.supervision;

import java.util.Vector;
import java.sql.SQLException;
import java.awt.event.*;

import ihm.Bouton;
import donnees.Incident;
import accesBDD.AccesBDDIncident;

// Panneau de l'onglet de gestion des incidents
public class OngletIncident extends Onglet implements ActionListener{
	
	private Bouton boutAfficher;
	private AccesBDDIncident tableIncidents = new AccesBDDIncident();
	
	public OngletIncident(){
		super("Gestion des incidents");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des incidents : noms des colonnes.
		nomColonnes.add("ID");
        nomColonnes.add("Colis");
        nomColonnes.add("Date");
        nomColonnes.add("Etat");
        nomColonnes.add("Description");
        nomColonnes.add("Créateur");
        nomColonnes.add("Type");
 
		// Construction du vector de données
        listerIncidents();
        
		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();
		
		remove(boutAjouter);
		
		boutAfficher = new Bouton("images/supervision/bouton_afficher.png","images/supervision/bouton_afficher_appuyer.png");
		boutAfficher.setBounds(769,60,165,41);
		
		add(boutAfficher);
		
		// Le Bouton Ajouter est transformé en bouton d'affichage
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
				//Sup_AjoutModifIncident modifIncident = new Sup_AjoutModifIncident(incid,this,tableIncidents);
				
				// On bloque l'utilisateur sur le pop-up
				//setFenetreActive(false);

				// On autorise la modification de l'état si l'incident
				// n'est pas déjà réglé
				if(incid.getEtat().intValue()<Incident.TRAITE){
					try{
						// On met à jour la base de données
						tableIncidents.changerEtat(incid);
					}
					catch(SQLException e){
					
					}
					finally{
						// On met à jour l'objet
						incid.changerEtat();
						
						// On met à jour le tableau
						modifierLigne(incid.toVector());
					}				
				}
			}
			
			// Action liée au bouton d'affichage des détails de l'incident
			else if(source==boutAfficher){
				
				// On affiche les détails de l'incident dans une boite de dialogue
				new AffichageIncident(incid,this,tableIncidents);

				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
		}
		// Mise à jour de la liste
		if(source==boutUpdate){
			
			// Suppression du tableau
			remove(scrollPane);
			
			// On vide la liste des éléments du tableau
			donnees.removeAllElements();				
			
			// Reconstructtion de la liste à afficher
			listerIncidents();
			
	        // Reconstruction du tableau
	        construireTableau();
	        
	        // Mise à jour de la fenêtre
	        updateUI();
		}

	}
	
	// Méthode remplissant le Vector de données du tableau par lecture dans la base de données
	private void listerIncidents(){
       try{
	        // On récupère les Incidents de la base de données et on les affiche
	        Vector listeIncidents = tableIncidents.lister();
	        
	        for(int i=0;i<listeIncidents.size();i++){
	        	donnees.addElement(((Incident)listeIncidents.get(i)).toVector());
	        }
        }
        catch(SQLException e){
        	
        }
	}
}
 