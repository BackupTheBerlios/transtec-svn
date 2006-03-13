package ihm.supervision;

import ihm.ModeleTable;

import java.awt.*;

import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;

import donnees.Camion;
import donnees.Utilisateur;
import donnees.Preparation;
import donnees.Destination;
import donnees.Entrepot;

public class Sup_OngletRepartitionFin extends JPanel{
	
	private Sup_VolumeReparti panelInfos;
	public JTable tabPreparations;
	private JScrollPane scrollPanePreparations;
	private ModeleTable modeleTabPreparations;
	private JComboBox comboDestinations,comboPreparateurs;
	private MyComboBoxRenderer col1Renderer,col3Renderer;
	private TableColumn col1,col3;
	private Vector nomColonnesPreparations = new Vector();
	public Vector donneesPreparations = new Vector();
	private Sup_OngletRepartition parent;

	public Sup_OngletRepartitionFin(Sup_OngletRepartition parent){
		super();
		
		// R�cup�ration du pointeur vers le panel p�re
		this.parent = parent;
		
		// Mise en forme
		setLayout(null);
		setOpaque(false);
		
		// Cr�ation du panel d'informations
		panelInfos = new Sup_VolumeReparti();
		panelInfos.setBounds(580,50,135,115);

		// Ajout du panel d'information au panel courant
		add(panelInfos);
		
		// Liste des pr�parations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Pr�parateur");
		
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			//parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations();
			
			// On r�cup�re les pr�parateurs
			parent.listePreparateurs = parent.tableUtilisateurs.listerParType(Utilisateur.PREPARATION);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
	}
	
	// Construction du tableau des pr�parations
	public void construireTableau(int algorithme){
		
		// Si le Vector de donn�es contient d�j� des lignes, on le vide
		if(!donneesPreparations.isEmpty()) donneesPreparations.removeAllElements();
		
		// Si le tableau est d�j� cr��, on le supprime
		if(scrollPanePreparations!=null)remove(scrollPanePreparations);
		
		// On construit le Vector de donn�es du tableau des pr�parations
		
		// Cas d'une r�partition manuelle
		if(algorithme == Sup_OngletRepartition.AUCUN){
			for(int i=0;i<parent.listeCamions.size();i++){
				Vector ligne = new Vector();

				ligne.add(new Integer(0));
				ligne.add(parent.listeCamions.get(i));
				ligne.add(new String("Choisir ..."));
				ligne.add(((Camion)parent.listeCamions.get(i)).getVolume());
				ligne.add(new String ("Choisir ..."));
				
				// On ajoute la ligne aux donn�es du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		// cas d'une r�partition assist�e par un algorithme
		else{
			for(int i=0;i<parent.resultatAlgos.size();i++){
				Vector ligne = new Vector();

				Preparation pCourante = (Preparation)parent.resultatAlgos.get(i); 
				
				ligne.add(new Integer(0));
				ligne.add(pCourante.getCamion());
				ligne.add(pCourante.getDestination());
				ligne.add(pCourante.getVolume());
				ligne.add(new String ("Choisir ..."));
				
				// On ajoute la ligne aux donn�es du tableau temporaire
				donneesPreparations.add(ligne);
			}
		}
		
		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTabPreparations = new ModeleTable(nomColonnesPreparations,donneesPreparations){			
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
		
		// Cr�ation d'un listener pour guetter les changements de lignes
	    SelectionListener listener = new SelectionListener(tabPreparations,modeleTabPreparations);
	    tabPreparations.getSelectionModel().addListSelectionListener(listener);
		tabPreparations.getColumnModel().getSelectionModel().addListSelectionListener(listener);
		
		// Suppression de la colonne des ID
		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
		// On place une liste de choix dans la colonne des destinations
	    comboDestinations = new JComboBox(parent.listeVolumesDestinations);
	    col1 = tabPreparations.getColumnModel().getColumn(1);
	    col1.setCellEditor(new DefaultCellEditor(comboDestinations));
	    col1Renderer = new MyComboBoxRenderer(parent.listeVolumesDestinations);
	    col1.setCellRenderer(col1Renderer);

		// On place une liste de choix dans la colonne des pr�parateurs
	    comboPreparateurs = new JComboBox(parent.listePreparateurs);
	    col3 = tabPreparations.getColumnModel().getColumn(3);
	    col3.setCellEditor(new DefaultCellEditor(comboPreparateurs));
	    col3Renderer = new MyComboBoxRenderer(parent.listePreparateurs);
	    col3.setCellRenderer(col3Renderer);

		// On cr�e les colonnes du tableau selon le mod�le
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		scrollPanePreparations = new JScrollPane(tabPreparations);
		
		// On d�finit les dimensions du tableau
		tabPreparations.setPreferredScrollableViewportSize(new Dimension(300,460));
		
		// On place le tableau
		scrollPanePreparations.setMaximumSize(new Dimension(300,460));
		
		// On d�finit le tableau transparent
		scrollPanePreparations.setOpaque(false);
		scrollPanePreparations.getViewport().setOpaque(false);
		scrollPanePreparations.setBounds(0,0,550,460);
		//scrollPanePreparations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout du tableau au Panel
		add(scrollPanePreparations);
	}
	
	public void publierPreparations(){
		Vector v;
		
		// On vide la liste de r�sultats des algorithmes pour la r�utiliser
		parent.resultatAlgos.removeAllElements();
		
		// On prend en compte toutes les modifications apport�es aux cellules
		//Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en cr�er des pr�parations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On cr�e un objet Preparation � partir de la ligne du tableau
			Preparation p = new Preparation();
			if(v.get(2) instanceof Entrepot) 
				p.setDestination((Entrepot)v.get(2));
			else if((v.get(2) instanceof Destination))
				p.setDestination(((Destination)v.get(2)).getEntrepot());
			p.setUtilisateur((Utilisateur)v.get(4));
			p.setOrigine(((Camion)v.get(1)).getOrigine());
			p.setVolume((Float)v.get(3));
			p.setCamion((Camion)v.get(1));
			p.setId(new Integer(0));
			p.setIdChargement(new Integer(0));
			p.setIdChargementEnCours(new Integer(0));
			
			// On ajoute la pr�paration � la liste
			parent.resultatAlgos.add(p);
		}
		/*******/
		//System.out.println(liste);
		/*******/
	}
	
	// Fonction permettant de v�rifier que tous les champs du tableau sont renseign�s
	public boolean verifierSaisie(){
		boolean ret = true;
		Vector v;
		
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			// On cr�e un objet Preparation � partir de la ligne du tableau
			if(!(v.get(2) instanceof Entrepot) && !(v.get(2) instanceof Destination))
				ret=false;
			else if(!(v.get(4) instanceof Utilisateur)) ret=false;
		}	
		
		return ret;
	}
	
	
	/****** Classes utilis�es localement ******/
	
	// Classe d�rivant de JComboBox et permettant d'afficher une liste
	// de s�lection dans une cellule de JTable
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

			// Afficher l'�l�ment s�lectionn�
			removeAllItems();
			if (value != null) {
				addItem(value);
			}
			setSelectedItem(value);
			return this;
		}
	}
	
    public class SelectionListener implements ListSelectionListener {
		JTable table;

		ModeleTable modele;

		Vector v;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		SelectionListener(JTable table, ModeleTable modele) {
			this.table = table;
			this.modele = modele;
		}

		public void valueChanged(ListSelectionEvent e) {
			// If cell selection is enabled, both row and column change events
			// are fired
			if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
				// Column selection changed
				/*int first = e.getFirstIndex();
				int last = e.getLastIndex();	*/			

				// On r�cup�re le contenu de la ligne s�lectionn�e
				v = (Vector) modele.getRow(table.getSelectedRow());

				// On affiche les informations li�es � la ligne dans le panel d'information
				String sDestination = new String("Ind�fini");
				String sVolTotal = new String("0");
				Entrepot ent = new Entrepot();
				Destination d;
				
				if(v.get(2) instanceof Entrepot){
					ent = (Entrepot)v.get(2);
					sDestination = ent.toString();
				}
				else if((v.get(2) instanceof Destination)){
					ent = ((Destination)v.get(2)).getEntrepot();
					sDestination = ent.toString();
				}
				
				for(int i=0;i<parent.listeVolumesDestinations.size();i++){
					d = (Destination)parent.listeVolumesDestinations.get(i);
					if(d.getEntrepot().equals(ent)){
						i = parent.listeVolumesDestinations.size();
						sVolTotal = d.getVolume().toString();
					}
				}

				panelInfos.setTextDestination(sDestination);
				panelInfos.setTextVolReparti(((Float)v.get(3)).toString());
				panelInfos.setTextVolTotal(sVolTotal);

			}/* else if (e.getSource() == table.getColumnModel().getSelectionModel() && table.getColumnSelectionAllowed() ) {
				// Row selection changed
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
			}

			if (e.getValueIsAdjusting()) {
				// The mouse button has not yet been released
			}*/
        }
    }

}
