package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import donnees.Camion;
import donnees.Destination;

public class OngletRepartitionDebut extends JPanel {

	private JTable tabDestinations,tabCamions;
	private JScrollPane scrollPaneCamions,scrollPaneDestinations;
	private ModeleTable modeleTabCamions,modeleTabDestinations;
	private TableSorter sorterCamions,sorterDestinations;
	private Vector nomColonnesDestinations = new Vector();
	private Vector donneesDestinations = new Vector();
	private Vector nomColonnesCamions = new Vector();
	private Vector donneesCamions = new Vector();
	private OngletRepartition parent;
	
	// Constructeur	
	public OngletRepartitionDebut(OngletRepartition parent) {
		super();
		
		// R�cup�ration du pointeur vers le panel p�re
		this.parent = parent;
		
		// Mise en forme
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		setOpaque(false);
		
		// Cr�ation du contenu
		creerContenu();
	}
	
	private void creerContenu(){
		/********** Tableau des camions **********/
		
		// Liste des camions : noms des colonnes.
		nomColonnesCamions.add("ID");
		nomColonnesCamions.add("Camion");
		nomColonnesCamions.add("Disponibilit�");
		nomColonnesCamions.add("Largeur");
		nomColonnesCamions.add("Hauteur");
		nomColonnesCamions.add("Profondeur");
		nomColonnesCamions.add("Volume");
		nomColonnesCamions.add("Origine");
		nomColonnesCamions.add("Destination");
		
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE,parent.entActuel);
			
			for(int i=0;i<parent.listeCamions.size();i++){
				donneesCamions.addElement(((Camion)parent.listeCamions.get(i)).toVector());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
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
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(1));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		tabCamions.removeColumn(tabCamions.getColumnModel().getColumn(2));
		
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		scrollPaneCamions = new JScrollPane(tabCamions);
		
		// On d�finit les dimensions du tableau
		tabCamions.setPreferredScrollableViewportSize(new Dimension(350,472));
		
		// On place le tableau
		scrollPaneCamions.setMaximumSize(new Dimension(350,472));
		
		// On d�finit le tableau transparent
		scrollPaneCamions.setOpaque(false);
		scrollPaneCamions.getViewport().setOpaque(false);
		scrollPaneCamions.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		
		/********** Tableau des destinations **********/
		
		// Liste des destinations : noms des colonnes.
		nomColonnesDestinations.add("Destination");
		nomColonnesDestinations.add("Volume");
		nomColonnesDestinations.add("Volume restant");
		//nomColonnesDestinations.add("Distance");
		
		try{
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations(parent.entActuel.getId());
			
			for(int i=0;i<parent.listeVolumesDestinations.size();i++){
				donneesDestinations.addElement(((Destination)parent.listeVolumesDestinations.get(i)).toVector());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
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
		tabDestinations.removeColumn(tabDestinations.getColumnModel().getColumn(2));
		//tabDestinations.removeColumn(tabDestinations.getColumnModel().getColumn(2));
		
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		scrollPaneDestinations = new JScrollPane(tabDestinations);
		
		// On d�finit les dimensions du tableau
		tabDestinations.setPreferredScrollableViewportSize(new Dimension(350,472));
		
		// On place le tableau
		scrollPaneDestinations.setMaximumSize(new Dimension(350,472));
		
		// On d�finit le tableau transparent
		scrollPaneDestinations.setOpaque(false);
		scrollPaneDestinations.getViewport().setOpaque(false);
		scrollPaneDestinations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout des tableaux au Panel de d�but
		add(scrollPaneDestinations);
		//add(Box.createHorizontalGlue());
		add(Box.createHorizontalGlue());
		add(scrollPaneCamions);
	}

	// Fonction permettant de mettre � jour les tableaux
	protected void updateTableaux(){
		// Suppression de tout le contenu
		removeAll();
		
		// On vide tous les vector de donn�es et d'en-t�tes
		nomColonnesDestinations.removeAllElements();
		donneesDestinations.removeAllElements();
		nomColonnesCamions.removeAllElements();
		donneesCamions.removeAllElements();
		
		// On recr�e le contenu
		creerContenu();
		
		// On met � jour le Panel
		this.updateUI();		
	}
}
