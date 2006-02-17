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
		titre.setText("Disponibilit�s");
		titre.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		// Panel d'en-t�te
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
        nomColonnesCamions.add("Disponibilit�");
        nomColonnesCamions.add("Volume");
        nomColonnesCamions.add("Origine");
        nomColonnesCamions.add("Destination");
        
		// Liste des destinations : noms des colonnes.
        nomColonnesDestinations.add("Destination");
        nomColonnesDestinations.add("Volume");

        try{
	        // On r�cup�re les camions de la base de donn�es et on les affiche
	        Vector listeCamions = tableCamions.lister();
	        
	        for(int i=0;i<listeCamions.size();i++){
	        	donneesCamions.addElement(((Camion)listeCamions.get(i)).toVector());
	        }
	        
	        // On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
	        /*Vector listeDestinations = tableColis.listerDestVolume();
	        
	        for(int i=0;i<listeDestinations.size();i++){
	        	donneesDestinations.addElement((listeDestinations.get(i)));
	        }*/
       }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
        
		// Construction du tableau et des fonction qui lui sont associ�es
		construireTableaux();

		// Bouton Suite
		boutSuite.addActionListener(this);

		// Bouton Retour
		boutRetour.addActionListener(this);
	}
	
	public void construireTableaux(){

		/********** Tableau des camions **********/
		
		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTabCamions = new ModeleTable(nomColonnesCamions,donneesCamions);

		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorterCamions = new TableSorter(modeleTabCamions);

		// Cr�ation du tableau
		tabCamions = new JTable(sorterCamions);

		// initialisation du Sorter
		sorterCamions.setTableHeader(tabCamions.getTableHeader());

		// On cr�e les colonnes du tableau selon le mod�le
		tabCamions.setAutoCreateColumnsFromModel(true);
		tabCamions.setOpaque(false);
		tabCamions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(0));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));

		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		JScrollPane scrollPaneCamions = new JScrollPane(tabCamions);

		// On d�finit les dimensions du tableau
		tabCamions.setPreferredScrollableViewportSize(new Dimension(300,500));

		// On place le tableau
		scrollPaneCamions.setMaximumSize(new Dimension(300,500));

		// On d�finit le tableau transparent
		scrollPaneCamions.setOpaque(false);
		scrollPaneCamions.getViewport().setOpaque(false);
		scrollPaneCamions.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		
		/********** Tableau des destinations **********/
		
		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTabDestinations = new ModeleTable(nomColonnesDestinations,donneesDestinations);

		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorterDestinations = new TableSorter(modeleTabDestinations);

		// Cr�ation du tableau
		tabDestinations = new JTable(sorterDestinations);

		// initialisation du Sorter
		sorterDestinations.setTableHeader(tabDestinations.getTableHeader());

		// On cr�e les colonnes du tableau selon le mod�le
		tabDestinations.setAutoCreateColumnsFromModel(true);
		tabDestinations.setOpaque(false);
		tabDestinations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		JScrollPane scrollPaneDestinations = new JScrollPane(tabDestinations);

		// On d�finit les dimensions du tableau
		tabDestinations.setPreferredScrollableViewportSize(new Dimension(300,500));

		// On place le tableau
		scrollPaneDestinations.setMaximumSize(new Dimension(300,500));

		// On d�finit le tableau transparent
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
		}*/
	}

}
