package ihm.supervision;

import ihm.ModeleTable;

import java.awt.*;
import java.awt.event.*;

import java.util.Vector;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;

import donnees.Camion;
import donnees.Utilisateur;
import donnees.Preparation;
import donnees.Destination;

public class Sup_OngletRepartitionFin extends JPanel{
	
	public JTable tabPreparations;
	private JScrollPane scrollPanePreparations;
	private ModeleTable modeleTabPreparations;
	private Vector nomColonnesPreparations = new Vector();
	public Vector donneesPreparations = new Vector();
	private Sup_OngletRepartition parent;

	public Sup_OngletRepartitionFin(Sup_OngletRepartition parent){
		super();
		
		// Récupération du pointeur vers le panel père
		this.parent = parent;
		
		// Mise en forme
		setLayout(new GridLayout(1,1));
		setOpaque(false);
		
		// Liste des préparations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Préparateur");
		nomColonnesPreparations.add("Volume Total");
		
		try{
			// On récupère les camions disponibles de la base de données et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On récupère les Destinations des colis et on les affiche avec le volume correspondant
			parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations();
			
			// On récupère les préparateurs
			parent.listePreparateurs = parent.tableUtilisateurs.listerParType(Utilisateur.PREPARATION);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
	
	// Construction du tableau des préparations
	public void construireTableau(int algorithme){
		
		// Si le Vector de données contient déjà des lignes, on le vide
		if(!donneesPreparations.isEmpty()) donneesPreparations.removeAllElements();
		
		// Si le tableau est déjà créé, on le supprime
		if(scrollPanePreparations!=null)remove(scrollPanePreparations);
		
		// On construit le Vector de données du tableau des préparations
		
		// Cas d'une répartition manuelle
		if(algorithme == Sup_OngletRepartition.AUCUN){
			for(int i=0;i<parent.listeCamions.size();i++){
				Vector ligne = new Vector();

				ligne.add(new Integer(0));
				ligne.add(parent.listeCamions.get(i));
				ligne.add(new String("Choisir ..."));
				ligne.add(((Camion)parent.listeCamions.get(i)).getVolume());
				ligne.add(new String ("Choisir ..."));
				ligne.add(new Integer(0));
				
				// On ajoute la ligne aux données du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		// cas d'une répartition assistée par un algorithme
		else{
			for(int i=0;i<parent.resultatAlgos.size();i++){
				Vector ligne = new Vector();

				Preparation pCourante = (Preparation)parent.resultatAlgos.get(i); 
				
				ligne.add(new Integer(0));
				ligne.add(pCourante.getCamion());
				ligne.add(pCourante.getDestination());
				ligne.add(pCourante.getVolume());
				ligne.add(new String ("Choisir ..."));
				ligne.add(new Integer(0));
				
				// On ajoute la ligne aux données du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		
		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTabPreparations = new ModeleTable(nomColonnesPreparations,donneesPreparations){			
			// Ajout de cette méthode pour pouvoir afficher les ComboBox
			public boolean isCellEditable(int row, int col) {
				// Les colonnes contenant les ComboBox et le volume sont éditables
				if (col==2 || col==3 || col==4) {
					return true;
				} else {
					return false;
				}
			}
			
		    /*public Class getColumnClass(int c) {
		    	Class ret;
		    	
		    	if(getColumnName(c).equals("Camion")) ret = Camion.class;
		    	else if(getColumnName(c).equals("Destination")) ret = Destination.class;
		    	else if(getColumnName(c).equals("Préparateur")) ret = Utilisateur.class;
		    	else ret = getValueAt(0, c).getClass();
		    	
		        return ret;
		    }*/
		};
		
		// Création du tableau
		tabPreparations = new JTable(modeleTabPreparations);
		
		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
		// On place une liste de choix dans la colonne des destinations
	    JComboBox cb = new JComboBox();
	    
	    TableColumn col1 = tabPreparations.getColumnModel().getColumn(1);
	    col1.setCellEditor(new DefaultCellEditor(cb));
	    MyComboBoxRenderer col1Renderer = new MyComboBoxRenderer(parent.listeVolumesDestinations);
	    col1.setCellRenderer(col1Renderer);
	    for(int i=0;i<parent.listeVolumesDestinations.size();i++){
	    	cb.addItem(parent.listeVolumesDestinations.get(i));
	    }

		// On place une liste de choix dans la colonne des préparateurs
	    JComboBox cbp = new JComboBox();

	    TableColumn col3 = tabPreparations.getColumnModel().getColumn(3);
	    col3.setCellEditor(new DefaultCellEditor(cbp));
	    MyComboBoxRenderer col2Renderer = new MyComboBoxRenderer(parent.listePreparateurs);
	    col3.setCellRenderer(col2Renderer);
	    for(int i=0;i<parent.listePreparateurs.size();i++){
	    	cbp.addItem(parent.listePreparateurs.get(i));
	    }

		// On crée les colonnes du tableau selon le modèle
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		scrollPanePreparations = new JScrollPane(tabPreparations);
		
		// On définit les dimensions du tableau
		tabPreparations.setPreferredScrollableViewportSize(new Dimension(300,500));
		
		// On place le tableau
		scrollPanePreparations.setMaximumSize(new Dimension(300,500));
		
		// On définit le tableau transparent
		scrollPanePreparations.setOpaque(false);
		scrollPanePreparations.getViewport().setOpaque(false);
		scrollPanePreparations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout du tableau au Panel
		add(scrollPanePreparations);
	}
	
	public void publierPreparations(){
		Vector v;
		
		// On vide la liste de résultats des algorithmes pour la réutiliser
		parent.resultatAlgos.removeAllElements();
		
		// On prend en compte toutes les modifications apportées aux cellules
		Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en créer des préparations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On crée un objet Preparation à partir de la ligne du tableau
			Preparation p = new Preparation();
			p.setDestination(((Destination)v.get(2)).getEntrepot());
			p.setUtilisateur((Utilisateur)v.get(4));
			p.setOrigine(((Camion)v.get(1)).getOrigine());
			p.setVolume((Float)v.get(3));
			p.setCamion((Camion)v.get(1));
			p.setId(new Integer(0));
			p.setIdChargement(new Integer(0));
			p.setIdChargementEnCours(new Integer(0));
			
			// On ajoute la préparation à la liste
			parent.resultatAlgos.add(p);
		}
		/*******/
		//System.out.println(liste);
		/*******/
	}	
	
	
	/****** Classes utilisées localement ******/
	
	// Classe dérivant de JComboBox et permettant d'afficher une liste
	// de sélection dans une cellule de JTable
	public class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
		
		// Constructeur
		public MyComboBoxRenderer(Vector items) {
			for (int i = 0; i < items.size(); i++)
				addItem(items.get(i));
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (isSelected && hasFocus
					&& table.getModel().isCellEditable(row, column)) {
				setBackground(Color.white);
				setForeground(Color.black);
			} else if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(Color.white);
				setForeground(Color.black);
			}

			// Afficher l'élément sélectionné
			removeAllItems();
			if (value != null) {
				addItem(value);
			}
			setSelectedItem(value);
			return this;
		}
	}
}
