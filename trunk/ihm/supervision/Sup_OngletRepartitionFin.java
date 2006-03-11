package ihm.supervision;

import ihm.ModeleTable;

import java.awt.*;

import java.util.Vector;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;

import donnees.Camion;
import donnees.Utilisateur;
import donnees.Preparation;
import donnees.Destination;

public class Sup_OngletRepartitionFin extends JPanel{
	
	JTextField texteVolume = new JTextField();
	private JTable tabPreparations;
	private JScrollPane scrollPanePreparations;
	private ModeleTable modeleTabPreparations;
	private Vector nomColonnesPreparations = new Vector();
	private Vector donneesPreparations = new Vector();
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
		nomColonnesPreparations.add("Volume Tot. (cm3)");
		
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
		
		// On construit le Vector de données du tableau des préparations
		for(int i=0;i<parent.listeCamions.size();i++){
			Vector ligne = new Vector();
			Destination dTemp = new Destination();
			dTemp.getEntrepot().getLocalisation().setVille("choisir");
			Utilisateur uTemp = new Utilisateur();		
			uTemp.getPersonne().setNom("choisir");
			uTemp.getPersonne().setPrenom("choisir");
			
			ligne.add(new Integer(0));
			ligne.add(parent.listeCamions.get(i));
			ligne.add(dTemp);
			ligne.add(((Camion)parent.listeCamions.get(i)).getVolume());
			ligne.add(uTemp);
			ligne.add(new Integer(0));
			
			// On ajoute la ligne aux données du tableau temporaire
			donneesPreparations.add(ligne);
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
		};
		
		// Création du tableau
		tabPreparations = new JTable(modeleTabPreparations);
		
		tabPreparations.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(texteVolume));

		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
		// On place une liste de choix dans la colonne des préparateurs
	    TableColumn col3 = tabPreparations.getColumnModel().getColumn(3);
	    col3.setCellEditor(new MyComboBoxEditor(parent.listePreparateurs));
	    col3.setCellRenderer(new MyComboBoxRenderer(parent.listePreparateurs));

		// On place une liste de choix dans la colonne des destinations
	    TableColumn col1 = tabPreparations.getColumnModel().getColumn(1);
	    col1.setCellEditor(new MyComboBoxEditor(parent.listeVolumesDestinations));
	    col1.setCellRenderer(new MyComboBoxRenderer(parent.listeVolumesDestinations));

		// On crée les colonnes du tableau selon le modèle
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		
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
		Vector liste = new Vector();
		
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
			Preparation p = new Preparation(
								(Utilisateur)v.get(4),
								((Camion)v.get(1)).getOrigine(),
								((Destination)v.get(2)).getEntrepot(),
								(Float)v.get(3),
								(Camion)v.get(1),
								new Integer(0));
			
			// On ajoute la préparation à la liste
			liste.add(p);
		}
		/*******/
		//System.out.println(liste);
		/*******/
	}	
	
	
	/****** Classes utilisées localement ******/
	
	// Classe dérivant de JComboBox et permettant d'afficher une liste
	// de sélection dans une cellule de JTable
	public class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public MyComboBoxRenderer(Vector items) {
            super(items);
        }
    
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
    
            // Select the current value
            setSelectedItem(value);
            return this;
        }
    }
    
	// Classe dérivant de JComboBox et permettant d'afficher une liste
	// de sélection dans une cellule de JTable
	public class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(Vector items) {
            super(new JComboBox(items));
        }
    }
}
