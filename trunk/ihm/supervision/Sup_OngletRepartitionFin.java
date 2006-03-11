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
import donnees.Entrepot;

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
		
		// R�cup�ration du pointeur vers le panel p�re
		this.parent = parent;
		
		// Mise en forme
		setLayout(new GridLayout(1,1));
		setOpaque(false);
		
		// Liste des pr�parations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Pr�parateur");
		nomColonnesPreparations.add("Volume Tot. (cm3)");
		
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations();
			
			// On r�cup�re les pr�parateurs
			parent.listePreparateurs = parent.tableUtilisateurs.listerParType(Utilisateur.PREPARATION);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
	
	// Construction du tableau des pr�parations
	public void construireTableau(int algorithme){
		
		// On construit le Vector de donn�es du tableau des pr�parations
		
		// Cas d'une r�partition manuelle
		if(algorithme == Sup_OngletRepartition.AUCUN){
			for(int i=0;i<parent.listeCamions.size();i++){
				Vector ligne = new Vector();
				Utilisateur uTemp = new Utilisateur();		
				uTemp.getPersonne().setNom("choisir");
				uTemp.getPersonne().setPrenom("choisir");

				Destination dTemp = new Destination();
				dTemp.getEntrepot().getLocalisation().setVille("choisir");
				Entrepot eTemp = dTemp.getEntrepot(); 
				
				ligne.add(new Integer(0));
				ligne.add(parent.listeCamions.get(i));
				ligne.add(eTemp);
				ligne.add(((Camion)parent.listeCamions.get(i)).getVolume());
				ligne.add(uTemp);
				ligne.add(new Integer(0));
				
				// On ajoute la ligne aux donn�es du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		// cas d'une r�partition assist�e par un algorithme
		else{
			for(int i=0;i<parent.resultatAlgos.size();i++){
				Vector ligne = new Vector();
				Utilisateur uTemp = new Utilisateur();		
				uTemp.getPersonne().setNom("choisir");
				uTemp.getPersonne().setPrenom("choisir");
				Preparation pCourante = (Preparation)parent.resultatAlgos.get(i); 
				
				ligne.add(new Integer(0));
				ligne.add(pCourante.getCamion());
				ligne.add(pCourante.getDestination());
				ligne.add(pCourante.getVolume());
				ligne.add(uTemp);
				ligne.add(new Integer(0));
				
				// On ajoute la ligne aux donn�es du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		
		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTabPreparations = new ModeleTablePreparations(nomColonnesPreparations,donneesPreparations){			
			// Ajout de cette m�thode pour pouvoir afficher les ComboBox
			public boolean isCellEditable(int row, int col) {
				// Les colonnes contenant les ComboBox et le volume sont �ditables
				if (col==2 || col==3 || col==4) {
					return true;
				} else {
					return false;
				}
			}
		};
		
		// Cr�ation du tableau
		tabPreparations = new JTable(modeleTabPreparations);
		
		tabPreparations.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(texteVolume));

		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
		// On place une liste de choix dans la colonne des pr�parateurs
	    TableColumn col3 = tabPreparations.getColumnModel().getColumn(3);
	    col3.setCellEditor(new MyComboBoxEditor(parent.listePreparateurs));
	    col3.setCellRenderer(new MyComboBoxRenderer(parent.listePreparateurs));

		// On place une liste de choix dans la colonne des destinations
	    Vector listeString = new Vector();
	    for(int i=0;i<parent.listeVolumesDestinations.size();i++){
	    	listeString.add(((Destination)parent.listeVolumesDestinations.get(i)).getEntrepot().getLocalisation().getVille());
	    }
	    
	    //JComboBox cb = new JComboBox(listeString);
	    
	    TableColumn col1 = tabPreparations.getColumnModel().getColumn(1);
	    col1.setCellEditor(new MyComboBoxEditor(listeString));
	    col1.setCellRenderer(new MyComboBoxRenderer(listeString));

		// On cr�e les colonnes du tableau selon le mod�le
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		scrollPanePreparations = new JScrollPane(tabPreparations);
		
		// On d�finit les dimensions du tableau
		tabPreparations.setPreferredScrollableViewportSize(new Dimension(300,500));
		
		// On place le tableau
		scrollPanePreparations.setMaximumSize(new Dimension(300,500));
		
		// On d�finit le tableau transparent
		scrollPanePreparations.setOpaque(false);
		scrollPanePreparations.getViewport().setOpaque(false);
		scrollPanePreparations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout du tableau au Panel
		add(scrollPanePreparations);
	}
	
	public void publierPreparations(){
		Vector v;
		Vector liste = new Vector();
		
		// On prend en compte toutes les modifications apport�es aux cellules
		Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en cr�er des pr�parations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On cr�e un objet Preparation � partir de la ligne du tableau
			Preparation p = new Preparation(
								(Utilisateur)v.get(4),
								((Camion)v.get(1)).getOrigine(),
								((Destination)v.get(2)).getEntrepot(),
								(Float)v.get(3),
								(Camion)v.get(1),
								new Integer(0));
			
			// On ajoute la pr�paration � la liste
			liste.add(p);
		}
		/*******/
		//System.out.println(liste);
		/*******/
	}	
	
	
	/****** Classes utilis�es localement ******/
	
	// Classe d�rivant de JComboBox et permettant d'afficher une liste
	// de s�lection dans une cellule de JTable
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
    
	// Classe d�rivant de JComboBox et permettant d'afficher une liste
	// de s�lection dans une cellule de JTable
	public class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(Vector items) {
            super(new JComboBox(items));
        }
    }
}
