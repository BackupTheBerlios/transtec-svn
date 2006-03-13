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
		
		// Récupération du pointeur vers le panel père
		this.parent = parent;
		
		// Mise en forme
		setLayout(null);
		setOpaque(false);
		
		// Création du panel d'informations
		panelInfos = new Sup_VolumeReparti();
		panelInfos.setBounds(580,50,135,115);

		// Ajout du panel d'information au panel courant
		add(panelInfos);
		
		// Liste des préparations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Préparateur");
		
		try{
			// On récupère les camions disponibles de la base de données et on les affiche
			parent.listeCamions = parent.tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On récupère les Destinations des colis et on les affiche avec le volume correspondant
			//parent.listeVolumesDestinations = parent.tableColis.calculVolumesDestinations();
			
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
		};
		
		// Création du tableau
		tabPreparations = new JTable(modeleTabPreparations);
		
		// Création d'un listener pour guetter les changements de lignes
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

		// On place une liste de choix dans la colonne des préparateurs
	    comboPreparateurs = new JComboBox(parent.listePreparateurs);
	    col3 = tabPreparations.getColumnModel().getColumn(3);
	    col3.setCellEditor(new DefaultCellEditor(comboPreparateurs));
	    col3Renderer = new MyComboBoxRenderer(parent.listePreparateurs);
	    col3.setCellRenderer(col3Renderer);

		// On crée les colonnes du tableau selon le modèle
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		scrollPanePreparations = new JScrollPane(tabPreparations);
		
		// On définit les dimensions du tableau
		tabPreparations.setPreferredScrollableViewportSize(new Dimension(300,460));
		
		// On place le tableau
		scrollPanePreparations.setMaximumSize(new Dimension(300,460));
		
		// On définit le tableau transparent
		scrollPanePreparations.setOpaque(false);
		scrollPanePreparations.getViewport().setOpaque(false);
		scrollPanePreparations.setBounds(0,0,550,460);
		//scrollPanePreparations.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		// Ajout du tableau au Panel
		add(scrollPanePreparations);
	}
	
	public void publierPreparations(){
		Vector v;
		
		// On vide la liste de résultats des algorithmes pour la réutiliser
		parent.resultatAlgos.removeAllElements();
		
		// On prend en compte toutes les modifications apportées aux cellules
		//Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en créer des préparations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On crée un objet Preparation à partir de la ligne du tableau
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
			
			// On ajoute la préparation à la liste
			parent.resultatAlgos.add(p);
		}
		/*******/
		//System.out.println(liste);
		/*******/
	}
	
	// Fonction permettant de vérifier que tous les champs du tableau sont renseignés
	public boolean verifierSaisie(){
		boolean ret = true;
		Vector v;
		
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(i);
			
			// On crée un objet Preparation à partir de la ligne du tableau
			if(!(v.get(2) instanceof Entrepot) && !(v.get(2) instanceof Destination))
				ret=false;
			else if(!(v.get(4) instanceof Utilisateur)) ret=false;
		}	
		
		return ret;
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

				// On récupère le contenu de la ligne sélectionnée
				v = (Vector) modele.getRow(table.getSelectedRow());

				// On affiche les informations liées à la ligne dans le panel d'information
				String sDestination = new String("Indéfini");
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
