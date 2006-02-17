package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDColis;

import javax.swing.*;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;

import donnees.Camion;

public class Sup_OngletRepartition extends JPanel implements ActionListener{
	private JTable tabDestinations, tabCamions;
	private ModeleTable modeleTabDestinations,modeleTabCamions;
	private TableSorter sorterCamions,sorterDestinations;
	private JPanel panTitre;
	private JPanel panDonnees;
	private JPanel panBoutons;
	
	private JButton boutSuite = new JButton("Suite  >");
	private JButton boutRetour = new JButton("<  Retour");

	private Vector nomColonnesDestinations = new Vector();
	private Vector donneesDestinations = new Vector();
	private Vector nomColonnesCamions = new Vector();
	private Vector donneesCamions = new Vector();
	
//	protected int ligneActive;

	private AccesBDDCamion tableCamions = new AccesBDDCamion();
	private AccesBDDColis tableColis = new AccesBDDColis();
		
	public Sup_OngletRepartition(){
		// Mise en forme initiale
		setOpaque(false);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Titre de l'onglet
		JLabel titre = new JLabel();
		titre.setText("Disponibilités");
		titre.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		// Panel d'en-tête
		panTitre= new JPanel();
		panTitre.setLayout(new BoxLayout(panTitre,BoxLayout.LINE_AXIS));
		panTitre.add(titre);
		panTitre.add(Box.createHorizontalGlue());
		panTitre.setAlignmentY(Box.TOP_ALIGNMENT);
		panTitre.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 50));
		panTitre.setOpaque(false);
		//panTitre.setBackground(Color.lightGray);
		add(panTitre);
		
		// Liste des camions : noms des colonnes.
		nomColonnesCamions.add("ID");
		nomColonnesCamions.add("Camion");
        nomColonnesCamions.add("Disponibilité");
        nomColonnesCamions.add("Volume");
        nomColonnesCamions.add("Origine");
        nomColonnesCamions.add("Destination");
        
		// Liste des destinations : noms des colonnes.
        nomColonnesDestinations.add("Destination");
        nomColonnesDestinations.add("Volume");

        try{
	        // On récupère les camions de la base de données et on les affiche
	        Vector listeCamions = tableCamions.lister();
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donneesCamions.addElement(((Camion)listeCamions.get(i)).toVector());
	        }
	        
	        // On récupère les Destinations des colis et on les affiche avec le volume correspondant
	        /*Vector listeDestinations = tableColis.listerDestVolume();
	        
	        for(int i=0;i<listeDestinations.size();i++){
	        	donneesDestinations.addElement((listeDestinations.get(i)));
	        }*/
       }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
        
		// Construction du tableau et des fonction qui lui sont associées
		construireTableaux();

		// Bouton Suite
		boutSuite.addActionListener(this);

		// Bouton Retour
		boutRetour.addActionListener(this);
	}
	
	public void construireTableaux(){

		/********** Tableau des camions **********/
		
		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTabCamions = new ModeleTable(nomColonnesCamions,donneesCamions);

		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorterCamions = new TableSorter(modeleTabCamions);

		// Création du tableau
		tabCamions = new JTable(sorterCamions);

		// initialisation du Sorter
		sorterCamions.setTableHeader(tabCamions.getTableHeader());

		// On crée les colonnes du tableau selon le modèle
		tabCamions.setAutoCreateColumnsFromModel(true);
		tabCamions.setOpaque(false);
		tabCamions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(0));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));

		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPaneCamions = new JScrollPane(tabCamions);

		// On définit les dimensions du tableau
		tabCamions.setPreferredScrollableViewportSize(new Dimension(300,500));

		// On place le tableau
		scrollPaneCamions.setMaximumSize(new Dimension(300,500));

		// On définit le tableau transparent
		scrollPaneCamions.setOpaque(false);
		scrollPaneCamions.getViewport().setOpaque(false);
		scrollPaneCamions.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		
		/********** Tableau des destinations **********/
		
		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTabDestinations = new ModeleTable(nomColonnesDestinations,donneesDestinations);

		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorterDestinations = new TableSorter(modeleTabDestinations);

		// Création du tableau
		tabDestinations = new JTable(sorterDestinations);

		// initialisation du Sorter
		sorterDestinations.setTableHeader(tabDestinations.getTableHeader());

		// On crée les colonnes du tableau selon le modèle
		tabDestinations.setAutoCreateColumnsFromModel(true);
		tabDestinations.setOpaque(false);
		tabDestinations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPaneDestinations = new JScrollPane(tabDestinations);

		// On définit les dimensions du tableau
		tabDestinations.setPreferredScrollableViewportSize(new Dimension(300,500));

		// On place le tableau
		scrollPaneDestinations.setMaximumSize(new Dimension(300,500));

		// On définit le tableau transparent
		scrollPaneDestinations.setOpaque(false);
		scrollPaneDestinations.getViewport().setOpaque(false);
		scrollPaneDestinations.setAlignmentX(Box.RIGHT_ALIGNMENT);

		// On ajoute les tableaux au Panneau principal
		panDonnees = new JPanel();
		panDonnees.setLayout(new BoxLayout(panDonnees,BoxLayout.X_AXIS));
		panDonnees.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panDonnees.setOpaque(false);
		panDonnees.add(scrollPaneDestinations);
		panDonnees.add(Box.createHorizontalGlue());
		panDonnees.add(scrollPaneCamions);
		add(panDonnees);
		
		// Mise en page du panel des boutons
		panBoutons = new JPanel();
		panBoutons.setOpaque(false);
		panBoutons.setLayout(new BoxLayout(panBoutons,BoxLayout.X_AXIS));
		panBoutons.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));

		// Bouton Retour
		boutRetour.setSize(100,20);
		boutRetour.setAlignmentX(Box.LEFT_ALIGNMENT);
		panBoutons.add(boutRetour);
		
		// Espace entre les boutons
		panBoutons.add(Box.createRigidArea(new Dimension(100,0)));
		
		// Bouton Suite
		boutSuite.setSize(100,20);
		boutSuite.setAlignmentX(Box.RIGHT_ALIGNMENT);
		panBoutons.add(boutSuite);
		
		panBoutons.setAlignmentY(Box.BOTTOM_ALIGNMENT);
		add(panBoutons);
	}
	
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();

/*		try{			
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
		}*/
	}

}
