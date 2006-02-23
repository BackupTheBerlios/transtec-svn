package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDPreparation;
import accesBDD.AccesBDDUtilisateur;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.event.*;
import java.awt.*;
import java.util.Vector;
import java.util.EventObject;

import java.awt.event.*;

import donnees.Camion;
import donnees.Utilisateur;
import donnees.Destination;

public class Sup_OngletRepartition extends JPanel implements ActionListener{
	private JLabel titre;
	private JTable tabDestinations,tabCamions,tabPreparations;
	private JScrollPane scrollPaneCamions,scrollPaneDestinations,scrollPanePreparations;
	private ModeleTable modeleTabCamions,modeleTabDestinations,modeleTabPreparations;
	private TableSorter sorterCamions,sorterDestinations,sorterPreparations;
	private JPanel panTitre;
	private CardLayout layoutPanDonnees;
	private JPanel panDonnees,panDonneesDebut,panDonneesChoix,panDonneesFin;
	private JPanel panBoutons;
	private ButtonGroup groupeRadio;
	private JRadioButton radioAucun,radioRadin,radioPerenoel;
	private JButton boutSuite = new JButton("Suite  >");
	private JButton boutRetour = new JButton("<  Retour");

	private Vector nomColonnesDestinations = new Vector();
	private Vector donneesDestinations = new Vector();
	private Vector nomColonnesCamions = new Vector();
	private Vector donneesCamions = new Vector();
	private Vector nomColonnesPreparations = new Vector();
	private Vector donneesPreparations = new Vector();
	
	private Vector listePreparateurs,listeDestinations,listeVolumesDestinations,listeCamions;
	
	private AccesBDDCamion tableCamions = new AccesBDDCamion();
	private AccesBDDColis tableColis = new AccesBDDColis();
	private AccesBDDPreparation tablePreparations = new AccesBDDPreparation();
	private AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur();

	private JComboBox comboPreparateurs,comboDestinations;


	private final static int DEBUT = 0;
	private final static int CHOIX = 1;
	private final static int FIN = 2;
	
	private final static int AUCUN = 0;
	private final static int RADIN = 1;
	private final static int PERENOEL = 2;

	private int ecranActuel = DEBUT;	

	public Sup_OngletRepartition(){
		// Mise en forme initiale
		setOpaque(false);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Titre de l'onglet
		titre = new JLabel();
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
		nomColonnesCamions.add("Volume (m3)");
		nomColonnesCamions.add("Origine");
		nomColonnesCamions.add("Destination");
		
		// Liste des destinations : noms des colonnes.
		nomColonnesDestinations.add("ID");
		nomColonnesDestinations.add("Destination");
		nomColonnesDestinations.add("Volume (cm3)");
		
		// Liste des pr�parations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume (cm3)");
		nomColonnesPreparations.add("Pr�parateur");
		nomColonnesPreparations.add("Volume Tot. (cm3)");
		
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			listeCamions = tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			for(int i=0;i<listeCamions.size();i++){
				donneesCamions.addElement(((Camion)listeCamions.get(i)).toVector());
			}
			
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			listeVolumesDestinations = tableColis.volumeDestination();
			
			for(int i=0;i<listeVolumesDestinations.size();i++){
				donneesDestinations.addElement(((Destination)listeVolumesDestinations.get(i)).toVector());
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		// Construction des tableaux et des fonction qui leur sont associ�es
		creerPanelDebut();
		
		// On ajoute les tableaux au panel de d�but
		panDonneesDebut = new JPanel();
		panDonneesDebut.setLayout(new BoxLayout(panDonneesDebut,BoxLayout.X_AXIS));
		panDonneesDebut.setOpaque(false);
		panDonneesDebut.add(scrollPaneDestinations);
		panDonneesDebut.add(Box.createHorizontalGlue());
		panDonneesDebut.add(scrollPaneCamions);
		
		// Cr�ation du panel central
		panDonnees = new JPanel(new CardLayout(10,10));
		panDonnees.add(panDonneesDebut,"Disponibilit�s");
		creerPanelChoix();
		panDonnees.add(panDonneesChoix,"Choix d'un algorithme de r�partition");
		creerPanelFin();
		panDonnees.add(panDonneesFin,"R�partition");
		panDonnees.setAlignmentY(Box.CENTER_ALIGNMENT);
		panDonnees.setOpaque(false);
		layoutPanDonnees = (CardLayout)panDonnees.getLayout();
		add(panDonnees);
		
		// Mise en page du panel des boutons
		panBoutons = new JPanel();
		panBoutons.setOpaque(false);
		panBoutons.setLayout(new BoxLayout(panBoutons,BoxLayout.X_AXIS));
		panBoutons.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));
		
		// Bouton Retour
		boutRetour.setSize(100,20);
		boutRetour.setAlignmentX(Box.LEFT_ALIGNMENT);
		boutRetour.addActionListener(this);
		boutRetour.setVisible(false);
		panBoutons.add(boutRetour);
		
		// Espace entre les boutons
		panBoutons.add(Box.createRigidArea(new Dimension(100,0)));
		
		// Bouton Suite
		boutSuite.setSize(100,20);
		boutSuite.setAlignmentX(Box.RIGHT_ALIGNMENT);
		boutSuite.addActionListener(this);
		panBoutons.add(boutSuite);
		
		panBoutons.setAlignmentY(Box.BOTTOM_ALIGNMENT);
		add(panBoutons);
	}
	
	// Construction des du premier panel
	public void creerPanelDebut(){
		
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
		scrollPaneCamions = new JScrollPane(tabCamions);
		
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
		tabDestinations.removeColumn(tabDestinations.getColumnModel().getColumn(0));
		
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		scrollPaneDestinations = new JScrollPane(tabDestinations);
		
		// On d�finit les dimensions du tableau
		tabDestinations.setPreferredScrollableViewportSize(new Dimension(300,500));
		
		// On place le tableau
		scrollPaneDestinations.setMaximumSize(new Dimension(300,500));
		
		// On d�finit le tableau transparent
		scrollPaneDestinations.setOpaque(false);
		scrollPaneDestinations.getViewport().setOpaque(false);
		scrollPaneDestinations.setAlignmentX(Box.RIGHT_ALIGNMENT);
	}
	
	// Cr�ation du panel de choix d'algorithme
	public void creerPanelChoix(){
		// Mise en page et taille du panel
		panDonneesChoix = new JPanel();
		panDonneesChoix.setLayout(new GridLayout(3,0));
		panDonneesChoix.setOpaque(false);
		panDonneesChoix.setSize(new Dimension(600,200));
		
		// Cr�ation des boutons radios
		radioAucun = new JRadioButton("Pas d'algorithme");
		radioAucun.setOpaque(false);
		radioAucun.setSelected(true);
		radioRadin = new JRadioButton("Minimisation des co�ts (radin)");
		radioRadin.setOpaque(false);
		radioPerenoel = new JRadioButton("Maximisation de la satisfaction (P�re No�l)");
		radioPerenoel.setOpaque(false);
		
		// Regroupement des boutons radio
		groupeRadio = new ButtonGroup();
		groupeRadio.add(radioAucun);
		groupeRadio.add(radioRadin);
		groupeRadio.add(radioPerenoel);
		
		// Ajout des boutons au panel
		panDonneesChoix.add(radioAucun);
		panDonneesChoix.add(radioRadin);
		panDonneesChoix.add(radioPerenoel);
	}
	
	// Cr�ation du panel d'affichage des pr�parations
	public void creerPanelFin(){
		panDonneesFin = new JPanel();
		panDonneesFin.setLayout(new GridLayout(1,1));
		panDonneesFin.setOpaque(false);
		
		// Cr�ation du mod�le de tableau � l'aide des en-t�tes de colonnes et des donn�es 
		modeleTabPreparations = new ModeleTable(nomColonnesPreparations,donneesPreparations){
			
			// Ajout de cette m�thode pour pouvoir afficher la ComboBox
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
		
		// On cr�e les colonnes du tableau selon le mod�le
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
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
		panDonneesFin.add(scrollPanePreparations);		
	}
	
	// R�partition des chargements � l'aide de l'algorithme s�lectionn�
	public void repartirChargements(){
		int algorithme=AUCUN;
		
		// On r�cup�re l'algorithme choisi
		if(radioAucun.isSelected()) affTabPreparationsSansAlgo();
		else if(radioRadin.isSelected()) algorithme=RADIN;
		else if(radioPerenoel.isSelected()) algorithme=PERENOEL;
		
		/*** DEBUG ****
		System.out.println(algorithme);
		/*** DEBUG ***/
		
		// On lance l'algorithme voulu par le superviseur
		// repartitionEnLangageC(algorithme);		
	}
	
	// Configuration du tableau des pr�parations pour que ces derni�res soient cr��es manuellement
	public void affTabPreparationsSansAlgo(){
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			listeCamions = tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			listeVolumesDestinations = tableColis.volumeDestination();
			
			// On r�cup�re les pr�parateurs
			listePreparateurs = tableUtilisateurs.listerParType(Utilisateur.PREPARATION);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		// On construit le Vector de donn�es du tableau des pr�parations
		for(int i=0;i<listeCamions.size();i++){
			Vector ligne = new Vector();
			ligne.add(new Integer(0));
			ligne.add(listeCamions.get(i));
			ligne.add(new String("Choisir..."));
			ligne.add(((Camion)listeCamions.get(i)).getVolume());
			ligne.add(new String("Choisir..."));
			ligne.add(new Integer(0));
			
			// On ajoute la ligne aux donn�es du tableau temporaire
			donneesPreparations.add(ligne);
		}
		
		JTextField texteVolume = new JTextField();
		tabPreparations.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(texteVolume));
		
		// On place une liste de choix dans la colonne des pr�parateurs
		comboPreparateurs = new JComboBox(listePreparateurs);
		
		comboRenderer rendPreparateurs = new comboRenderer(listePreparateurs);
		tabPreparations.getColumnModel().getColumn(3).setCellRenderer(rendPreparateurs);
		
		comboEditor editPreparateurs = new comboEditor(listePreparateurs);
		tabPreparations.getColumnModel().getColumn(3).setCellEditor(editPreparateurs);
		
		// On place une liste de choix dans la colonne des destinations
		comboDestinations = new JComboBox(listeVolumesDestinations);
		
		comboRenderer rendDestinations = new comboRenderer(listeVolumesDestinations);
		tabPreparations.getColumnModel().getColumn(1).setCellRenderer(rendDestinations);
		
		comboEditor editDestinations = new comboEditor(listeVolumesDestinations);
		tabPreparations.getColumnModel().getColumn(1).setCellEditor(editDestinations);
		
		// Prise en compte et affichage des modifications dans le tableau
		modeleTabPreparations.fireTableDataChanged();
		tabPreparations.updateUI();	
	}
	
	// Gestion des actions li�es aux boutons
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Passage � l'�cran suivant
		if(source==boutSuite){			
			// On d�finit les actions selon l'�cran actuel
			switch(ecranActuel){
			// Ecran de d�but
			case DEBUT:
				ecranActuel++;
				boutRetour.setVisible(true);
				layoutPanDonnees.next(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				break;
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel++;
				boutSuite.setText("Publier");
				boutRetour.setEnabled(false);
				layoutPanDonnees.next(panDonnees);
				titre.setText("R�partition");
				
				// R�partition des chargements � l'aide de l'algorithme choisi
				repartirChargements();
				break;
				// Ecran d'affichage final
			case FIN:
				// On publie la liste des chargements r�partis
				traverseAllCells(tabPreparations);
				Vector v = (Vector)modeleTabPreparations.getRow(0);
				System.out.println(v);
				break;
			}				
		}
		// Retour � l'�cran pr�c�dent
		else if(source==boutRetour){			
			// On d�finit les actions selon l'�cran actuel
			switch(ecranActuel){
			// Ecran de d�but
			case DEBUT:
				break;
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel--;
				boutRetour.setVisible(false);
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Disponibilit�s");
				break;
				// Ecran d'affichage final
			case FIN:
				ecranActuel--;
				boutSuite.setText("Suite  >");
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				break;
			}			
		}	
	}
	
	public void traverseAllCells(JTable table)
	{
		//	 gives to focus to cell editor and jtable does not retains the focus
		//	 so the editor component keep the focus and lostfocus method
		//	 will be called when the next cell is focused by editCell(x, y) method call
		table.setSurrendersFocusOnKeystroke(true);
		for (int x = 0; x < table.getRowCount(); x++)
		{
			for (int y = 0; y < table.getColumnCount(); y++)
			{
				table.editCellAt(x ,y);
			}
		}
	}


	public class comboRenderer extends JComboBox implements TableCellRenderer {		
		public comboRenderer(Vector v) {
			super(v);
		}		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				super.setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			} 
			
			setSelectedItem(value);
			return this;
		} 		
	}
	
	public class comboEditor extends JComboBox implements TableCellEditor {		
		protected EventListenerList listenerList = new EventListenerList();
		protected ChangeEvent changeEvent = new ChangeEvent(this);
		
		public comboEditor(Vector v) {
			super(v);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					fireEditingStopped();
				} 
			});
		}
		
		public void addCellEditorListener(CellEditorListener listener) {
			listenerList.add(CellEditorListener.class, listener);
		} 
		
		public void removeCellEditorListener(CellEditorListener listener) {
			listenerList.remove(CellEditorListener.class, listener);
		} 
		
		protected void fireEditingStopped() {
			CellEditorListener listener;
			Object[] listeners = listenerList.getListenerList();
			for (int i = 0; i < listeners.length; i++) {
				if (listeners[i] == CellEditorListener.class) {
					listener = (CellEditorListener) listeners[i + 1];
					listener.editingStopped(changeEvent);
				} 
			} 
		} 
		
		protected void fireEditingCanceled() {
			CellEditorListener listener;
			Object[] listeners = listenerList.getListenerList();
			for (int i = 0; i < listeners.length; i++) {
				if (listeners[i] == CellEditorListener.class) {
					listener = (CellEditorListener) listeners[i + 1];
					listener.editingCanceled(changeEvent);
				} 
			} 
		} 
		
		public void cancelCellEditing() {
			fireEditingCanceled();
		} 
		
		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		} 
		
		public boolean isCellEditable(EventObject event) {
			return true;
		} 
		
		public boolean shouldSelectCell(EventObject event) {
			return true;
		} 
		
		public Object getCellEditorValue() {
			return getSelectedItem();
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
			setSelectedItem(value);
			return this;
		}		
	}
}
