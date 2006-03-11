package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import donnees.Camion;
import donnees.Destination;

public class Sup_OngletRepartitionDebut extends JPanel implements ActionListener {

	private JButton boutUpdate = new JButton("Actualiser");
	private JTable tabDestinations,tabCamions;
	private JScrollPane scrollPaneCamions,scrollPaneDestinations;
	private ModeleTable modeleTabCamions,modeleTabDestinations;
	private TableSorter sorterCamions,sorterDestinations;
	private Vector nomColonnesDestinations = new Vector();
	private Vector donneesDestinations = new Vector();
	private Vector nomColonnesCamions = new Vector();
	private Vector donneesCamions = new Vector();
	private Sup_OngletRepartition parent;
	
	// Constructeur	
	public Sup_OngletRepartitionDebut(Sup_OngletRepartition parent) {
		super();
		
		// Récupération du pointeur vers le panel père
		this.parent = parent;
		
		// Mise en forme
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		setOpaque(false);
		
		// Création du contenu
		creerContenu();
	}
	
	private void creerContenu(){
		// On lie une action au bouton de mise à jour
		boutUpdate.addActionListener(this);
		
		/********** Tableau des camions **********/
		
		// Liste des camions : noms des colonnes.
		nomColonnesCamions.add("ID");
		nomColonnesCamions.add("Camion");
		nomColonnesCamions.add("Disponibilité");
		nomColonnesCamions.add("Largeur");
		nomColonnesCamions.add("Hauteur");
		nomColonnesCamions.add("Profondeur");
		nomColonnesCamions.add("Volume");
		nomColonnesCamions.add("Origine");
		nomColonnesCamions.add("Destination");
		
		try{
			// On récupère les camions disponibles de la base de données et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			for(int i=0;i<parent.listeCamions.size();i++){
				donneesCamions.addElement(((Camion)parent.listeCamions.get(i)).toVector());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
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
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		scrollPaneCamions = new JScrollPane(tabCamions);
		
		// On définit les dimensions du tableau
		tabCamions.setPreferredScrollableViewportSize(new Dimension(350,472));
		
		// On place le tableau
		scrollPaneCamions.setMaximumSize(new Dimension(350,472));
		
		// On définit le tableau transparent
		scrollPaneCamions.setOpaque(false);
		scrollPaneCamions.getViewport().setOpaque(false);
		scrollPaneCamions.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		
		/********** Tableau des destinations **********/
		
		// Liste des destinations : noms des colonnes.
		nomColonnesDestinations.add("ID");
		nomColonnesDestinations.add("Destination");
		nomColonnesDestinations.add("Volume");
		
		try{
			// On récupère les Destinations des colis et on les affiche avec le volume correspondant
			parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations();
			
			for(int i=0;i<parent.listeVolumesDestinations.size();i++){
				donneesDestinations.addElement(((Destination)parent.listeVolumesDestinations.get(i)).toVector());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
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
		tabDestinations.removeColumn(tabDestinations.getColumnModel().getColumn(0));
		
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		scrollPaneDestinations = new JScrollPane(tabDestinations);
		
		// On définit les dimensions du tableau
		tabDestinations.setPreferredScrollableViewportSize(new Dimension(350,472));
		
		// On place le tableau
		scrollPaneDestinations.setMaximumSize(new Dimension(350,472));
		
		// On définit le tableau transparent
		scrollPaneDestinations.setOpaque(false);
		scrollPaneDestinations.getViewport().setOpaque(false);
		scrollPaneDestinations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout des tableaux au Panel de début
		add(scrollPaneDestinations);
		//add(Box.createHorizontalGlue());
		//add(boutUpdate);
		add(Box.createHorizontalGlue());
		add(scrollPaneCamions);
	}


	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();

		if(source==boutUpdate){
			removeAll();
			
			nomColonnesDestinations.clear();
			donneesDestinations.clear();
			nomColonnesCamions.clear();
			donneesCamions.clear();
			
			creerContenu();
			
			this.updateUI();
		}
	}
}
