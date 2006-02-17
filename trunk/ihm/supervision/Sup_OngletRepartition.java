package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;

import javax.swing.*;

import accesBDD.AccesBDDCamion;

import java.util.Vector;
import java.awt.event.*;
import java.sql.*;

import donnees.Camion;

public class Sup_OngletRepartition extends JPanel implements ActionListener{
	private JTable table;
	private ModeleTable modeleTab;
	private TableSorter sorter;
	private JButton boutSuite = new JButton("Suite");
	private JButton boutRetour = new JButton("Retour");

	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();

//	protected int ligneActive;

	private AccesBDDCamion tableCamions = new AccesBDDCamion(); 
	
	public Sup_OngletRepartition(){
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);
		
		//Titre de l'onglet
		JLabel titre = new JLabel();
		titre.setText("Disponibilit�s");
		titre.setBounds(10,10,200,20);
		add(titre);
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des camions : noms des colonnes. On n'ajoute volontairement ID qui reste ainsi cach�
		nomColonnes.add("ID");
        nomColonnes.add("Num�ro");
        nomColonnes.add("Disponibilit�");
        nomColonnes.add("Volume");
        nomColonnes.add("Origine");
        nomColonnes.add("Destination");

        try{
	        // On r�cup�re les camions de la base de donn�es et on les affiche
	        Vector listeCamions = tableCamions.lister();
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        
		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableau();

		// Bouton Suite
		boutSuite.addActionListener(this);

		// Bouton Retour
		boutRetour.addActionListener(this);
	}
	
	public void construireTableau(){

		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTab = new ModeleTable(nomColonnes,donnees);

		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(modeleTab);

		// Cr�ation du tableau
		table = new JTable(sorter);

		// initialisation du Sorter
		sorter.setTableHeader(table.getTableHeader());

		// On cr�e les colonnes du tableau selon le mod�le
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.removeColumn(table.getColumnModel().getColumn(0));

		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		JScrollPane scrollPane = new JScrollPane(table);

		// On d�finit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(780,150));

		// On place le tableau
		scrollPane.setBounds(10,40,760,300);

		// On d�finit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		add(scrollPane);

		// Bouton Ajouter
		boutAjouter.setSize(100,20);
		boutAjouter.setLocation(100,360);
		add(boutAjouter);

		// Bouton Modifier
		boutModifier.setSize(100,20);
		boutModifier.setLocation(220,360);
		add(boutModifier);

		// Bouton Supprimer
		boutSupprimer.setSize(100,20);
		boutSupprimer.setLocation(340,360);
		add(boutSupprimer);
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
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

}
