package ihm.supervision;

import ihm.ModeleTable;

import java.awt.*;
import java.awt.event.*;

import java.util.EventObject;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.event.*;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDPreparation;
import accesBDD.AccesBDDUtilisateur;

import donnees.Camion;
import donnees.Utilisateur;
import donnees.Preparation;

public class Sup_OngletRepartitionFin extends JPanel{
	
	private JTable tabPreparations;
	private JScrollPane scrollPanePreparations;
	private ModeleTable modeleTabPreparations;
	
	private Vector listePreparateurs,listeDestinations,listeVolumesDestinations,listeCamions;
	
	private AccesBDDCamion tableCamions = new AccesBDDCamion();
	private AccesBDDColis tableColis = new AccesBDDColis();
	private AccesBDDPreparation tablePreparations = new AccesBDDPreparation();
	private AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur();

	private JComboBox comboPreparateurs,comboDestinations;

	private Vector nomColonnesPreparations = new Vector();
	private Vector donneesPreparations = new Vector();

	public Sup_OngletRepartitionFin(){
		super();
		
		setLayout(new GridLayout(1,1));
		setOpaque(false);
		
		// Liste des préparations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Préparateur");
		nomColonnesPreparations.add("Volume Tot. (cm3)");
		
		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTabPreparations = new ModeleTable(nomColonnesPreparations,donneesPreparations){			
			// Ajout de cette méthode pour pouvoir afficher la ComboBox
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
		
		// On crée les colonnes du tableau selon le modèle
		tabPreparations.setAutoCreateColumnsFromModel(true);
		tabPreparations.setOpaque(false);
		tabPreparations.removeColumn(tabPreparations.getColumnModel().getColumn(0));
		
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
	
	// Configuration du tableau des préparations pour que ces dernières soient créées manuellement
	public void affTabPreparationsSansAlgo(){
		try{
			// On récupère les camions disponibles de la base de données et on les affiche
			listeCamions = tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On récupère les Destinations des colis et on les affiche avec le volume correspondant
			listeVolumesDestinations = tableColis.calculVolumesDestinations();
			
			// On récupère les préparateurs
			listePreparateurs = tableUtilisateurs.listerParType(Utilisateur.PREPARATION);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		// On construit le Vector de données du tableau des préparations
		for(int i=0;i<listeCamions.size();i++){
			Vector ligne = new Vector();
			ligne.add(new Integer(0));
			ligne.add(listeCamions.get(i));
			ligne.add(new String("Choisir..."));
			ligne.add(((Camion)listeCamions.get(i)).getVolume());
			ligne.add(new String("Choisir..."));
			ligne.add(new Integer(0));
			
			// On ajoute la ligne aux données du tableau temporaire
			donneesPreparations.add(ligne);
		}
		
		JTextField texteVolume = new JTextField();
		tabPreparations.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(texteVolume));
		
		// On place une liste de choix dans la colonne des préparateurs
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
	
	public void publierPreparations(){
		Vector v;
		Vector liste = new Vector();
		
		// On prend en compte toutes les modifications apportées aux cellules
		Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en créer des préparations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(0);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On crée un objet Preparation à partir de la ligne du tableau
			Preparation p = new Preparation(v);
			
			// On ajoute la préparation à la liste
			liste.add(p);
		}
	}
	
	
	/****** Classes utilisées localement ******/
	
	// Classe dérivant de JComboBox et permettant d'afficher une liste
	//	de sélection dans une cellule de JTable
	private class comboRenderer extends JComboBox implements TableCellRenderer {		
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
	
	// Classe dérivant de JComboBox et permettant d'afficher une liste
	//	de sélection dans une cellule de JTable
	private class comboEditor extends JComboBox implements TableCellEditor {		
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
