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
		titre.setText("Disponibilités");
		titre.setBounds(10,10,200,20);
		add(titre);
		
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		//Liste des camions : noms des colonnes. On n'ajoute volontairement ID qui reste ainsi caché
		nomColonnes.add("ID");
        nomColonnes.add("Numéro");
        nomColonnes.add("Disponibilité");
        nomColonnes.add("Volume");
        nomColonnes.add("Origine");
        nomColonnes.add("Destination");

        try{
	        // On récupère les camions de la base de données et on les affiche
	        Vector listeCamions = tableCamions.lister();
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donnees.addElement(((Camion)listeCamions.get(i)).toVector());
	        }       
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        
		// Construction du tableau et des fonction qui lui sont associées
		construireTableau();

		// Bouton Suite
		boutSuite.addActionListener(this);

		// Bouton Retour
		boutRetour.addActionListener(this);
	}
	
	public void construireTableau(){

		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTab = new ModeleTable(nomColonnes,donnees);

		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleTab);

		// Création du tableau
		table = new JTable(sorter);

		// initialisation du Sorter
		sorter.setTableHeader(table.getTableHeader());

		// On crée les colonnes du tableau selon le modèle
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.removeColumn(table.getColumnModel().getColumn(0));

		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPane = new JScrollPane(table);

		// On définit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(780,150));

		// On place le tableau
		scrollPane.setBounds(10,40,760,300);

		// On définit le tableau transparent
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
			// On récupère le numéro de la ligne sélectionnée
			int ligneSelect = table.getSelectedRow();
	
			// Si une ligne est sélectionnée, on peut la modifier ou la supprimer
			if(ligneSelect != -1){
	
				// On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action liée au bouton de modification d'un camion
				if(source==boutModifier){
				
					// On récupère les données de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Camion c = new Camion(cVect);
	
					// On affiche l'invite de modification
					Sup_AjoutModifCamion modifCamion = new Sup_AjoutModifCamion(c,this,tableCamions);
					
					// On bloque l'utilisateur sur le pop-up
					setFenetreActive(false);
				}
				// Suppression d'un camion
				else if(source==boutSupprimer){
					// Suppression de la base de données
					tableCamions.supprimer((Integer)modeleTab.getValueAt(ligneActive,0));
	
					// Mise à jour du tableau
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
