package ihm;

import java.util.Vector;
import java.awt.event.*;

import accesBDD.AccesBDDCamion;

import donnees.Camion;
import donnees.Utilisateur;

// Panneau de l'onglet de gestion des camions
public class Sup_OngletCamion extends Sup_Onglet implements ActionListener{
	
	private AccesBDDCamion tableCamions = new AccesBDDCamion(); 

	public Sup_OngletCamion(){
		super("Gestion des camions");
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des camions : noms des colonnes. On n'ajoute volontairement ID qui reste ainsi cach�
		nomColonnes.add("ID");
        nomColonnes.add("Num�ro");
        nomColonnes.add("Etat");
        nomColonnes.add("Volume");
        nomColonnes.add("Chauffeur");
        nomColonnes.add("Destination");
        nomColonnes.add("Appartenance");

        // On r�cup�re les camions de la base de donn�es et on les affiche
        Vector listeCamions = tableCamions.lister();
        
        for(int i=0;i<listeCamions.size();i++){
        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
        }       
        
        
        
        // Cr�ation et ajout de donn�es (EXEMPLE, � remplacer par des acc�s � la BDD)
        /*********************************
 		donnees.addElement(new Camion(new Integer(0),"25TR76",new Integer(Camion.DISPONIBLE),new Integer(27),new Integer(0),new Integer(0),new Integer(2)).toVector());
		donnees.addElement(new Camion(new Integer(1),"1013TW78",new Integer(Camion.LIVRAISON),new Integer(12),new Integer(1),new Integer(1),new Integer(1)).toVector());
		donnees.addElement(new Camion(new Integer(2),"356LJ45",new Integer(Camion.REPARATION),new Integer(45),new Integer(0),new Integer(1),new Integer(2)).toVector());
		donnees.addElement(new Camion(new Integer(3),"654LLL1",new Integer(Camion.DISPONIBLE),new Integer(6),new Integer(2),new Integer(2),new Integer(4)).toVector());
		donnees.addElement(new Camion(new Integer(4),"M-AR1265",new Integer(Camion.LIVRAISON),new Integer(18),new Integer(1),new Integer(4),new Integer(0)).toVector());
		/*********************************/
				
		

		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();

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

			// On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
			ligneActive = sorter.modelIndex(ligneSelect);
			
			// Action li�e au bouton de modification d'un camion
			if(source==boutModifier){
			
				// On r�cup�re les donn�es de la ligne du tableau
				Vector cVect = (Vector) modeleTab.getRow(ligneActive);
				Camion c = new Camion(cVect);

				// On affiche l'invite de modification
				Sup_AjoutModifCamion modifCamion = new Sup_AjoutModifCamion(c,this,tableCamions);
				
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
			Sup_AjoutModifCamion ajoutCamion = new Sup_AjoutModifCamion(null,this,tableCamions);
			
			// On bloque l'utilisateur sur le pop-up
			setFenetreActive(false);
		}
	}
}
