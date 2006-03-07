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
		
		// Liste des pr�parations : noms des colonnes.
		nomColonnesPreparations.add("ID");
		nomColonnesPreparations.add("Camion");
		nomColonnesPreparations.add("Destination");
		nomColonnesPreparations.add("Volume");
		nomColonnesPreparations.add("Pr�parateur");
		nomColonnesPreparations.add("Volume Tot. (cm3)");
		
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
		add(scrollPanePreparations);
	}
	
	// Configuration du tableau des pr�parations pour que ces derni�res soient cr��es manuellement
	public void affTabPreparationsSansAlgo(){
		try{
			// On r�cup�re les camions disponibles de la base de donn�es et on les affiche
			listeCamions = tableCamions.listerParEtat(Camion.DISPONIBLE);
			
			// On r�cup�re les Destinations des colis et on les affiche avec le volume correspondant
			listeVolumesDestinations = tableColis.calculVolumesDestinations();
			
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
	
	public void publierPreparations(){
		Vector v;
		Vector liste = new Vector();
		
		// On prend en compte toutes les modifications apport�es aux cellules
		Sup_OngletRepartition.traverseAllCells(tabPreparations);
		
		// Extraction du contenu des lignes du tableau pour en cr�er des pr�parations
		for(int i=0;i<modeleTabPreparations.getRowCount();i++){
			// On extrait la ligne courante du tableau
			v = (Vector)modeleTabPreparations.getRow(0);
			
			/*******/
			System.out.println(v);
			/*******/
			
			// On cr�e un objet Preparation � partir de la ligne du tableau
			Preparation p = new Preparation(v);
			
			// On ajoute la pr�paration � la liste
			liste.add(p);
		}
	}
	
	
	/****** Classes utilis�es localement ******/
	
	// Classe d�rivant de JComboBox et permettant d'afficher une liste
	//	de s�lection dans une cellule de JTable
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
	
	// Classe d�rivant de JComboBox et permettant d'afficher une liste
	//	de s�lection dans une cellule de JTable
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
