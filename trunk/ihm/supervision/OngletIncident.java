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
        nomColonnes.add("Cr�ateur");
        nomColonnes.add("Type");
 
		// Construction du vector de donn�es
        listerIncidents();
        
		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();
		
		remove(boutAjouter);
		
		boutAfficher = new Bouton("images/supervision/bouton_afficher.png","images/supervision/bouton_afficher_appuyer.png");
		boutAfficher.setBounds(769,60,165,41);
		
		add(boutAfficher);
		
		// Le Bouton Ajouter est transform� en bouton d'affichage
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
				new AffichageIncident(incid,this,tableIncidents);

				// On bloque l'utilisateur sur le pop-up
				setFenetreActive(false);
			}
		}
		// Mise � jour de la liste
		if(source==boutUpdate){
			
			// Suppression du tableau
			remove(scrollPane);
			
			// On vide la liste des �l�ments du tableau
			donnees.removeAllElements();				
			
			// Reconstructtion de la liste � afficher
			listerIncidents();
			
	        // Reconstruction du tableau
	        construireTableau();
	        
	        // Mise � jour de la fen�tre
	        updateUI();
		}

	}
	
	// M�thode remplissant le Vector de donn�es du tableau par lecture dans la base de donn�es
	private void listerIncidents(){
       try{
	        // On r�cup�re les Incidents de la base de donn�es et on les affiche
	        Vector listeIncidents = tableIncidents.lister();
	        
	        for(int i=0;i<listeIncidents.size();i++){
	        	donnees.addElement(((Incident)listeIncidents.get(i)).toVector());
	        }
        }
        catch(SQLException e){
        	
        }
	}
}
 