package ihm;

import javax.swing.*;
import java.util.Vector;
import java.awt.*;

// Panneau de l'onglet
public class Sup_Onglet extends JPanel{
	protected JTable table;
	protected ModeleTable modeleTab;
	protected TableSorter sorter;
	protected JButton boutModifier = new JButton("Modifier");
	protected JButton boutAjouter = new JButton("Ajouter");
	protected JButton boutSupprimer = new JButton("Supprimer");
	protected Vector nomColonnes = new Vector();
	protected Vector donnees = new Vector();
	protected int ligneActive;
	
	// Permet de définir une image de fond
/*	public void paintComponent(Graphics g)	{
		ImageIcon img = new ImageIcon("images/new.jpg");
		g.drawImage(img.getImage(), 0, 0, null);
		super.paintComponent(g);
	}*/
	
	public Sup_Onglet(String s){	
		//Mise en forme initiale
		setOpaque(false);
		setLayout(null);
		
		//Titre de l'onglet
		JLabel titre = new JLabel(s);
		titre.setBounds(10,10,200,20);
		add(titre);
	}
	
	public void construireTableau(){
		
		// Création du modèle de tableau à l'aide des en-têtes de colonnes et des données 
		modeleTab = new ModeleTable(nomColonnes,donnees);
		
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleTab);
		
		// Création du tableau
		table = new JTable(sorter);
		
		// initialisation du Sorter
		sorter.setTableHeader(table.getTableHeader());
		
		// On crée les colonnes du tableau selon le modèle
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPane = new JScrollPane(table);

		// On définit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(780,150));

		// On place le tableau
		scrollPane.setBounds(10,40,760,300);

		// On définit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		add(scrollPane);

		// Bouton Ajouter
		boutAjouter.setSize(100,20);
		boutAjouter.setLocation(100,360);
		add(boutAjouter);

		// Bouton Modifier
		boutModifier.setSize(100,20);
		boutModifier.setLocation(220,360);
		add(boutModifier);

		// Bouton Supprimer
		boutSupprimer.setSize(100,20);
		boutSupprimer.setLocation(340,360);
		add(boutSupprimer);
	}
	
	// Suppression d'une ligne du tableau
	public void supprimerLigne(int ligne){
		// Suppression de la ligne
		modeleTab.removeRow(ligne);
		
		// On indique au modèle que les données ont changé
		modeleTab.fireTableDataChanged();
		
		// On redessine le tableau
		setFenetreActive(true);
		table.updateUI();
	}
	
	// Ajouter une ligne au tableau
	public void ajouterLigne(Vector v){
		
		// OBTENIR UN NOUVEL ID APRES ACCES A LA BDD
		
		// Ajout de la ligne
		modeleTab.addRow(v);
		
		// On indique au modèle que les données ont changé
		modeleTab.fireTableDataChanged();

		// On redessine le tableau
		setFenetreActive(true);
		table.updateUI();
	}
	
	// On met à jour une ligne du tableau				
	public void modifierLigne(Vector v){
		// Modification de la ligne
		modeleTab.setRow(v,ligneActive);
		
		// On redessine le tableau
		setFenetreActive(true);
		table.updateUI();	
	}
	
	// Permet d'activer ou de désactiver la fenêtre principale
	public void setFenetreActive(boolean b){
		Sup_Interface o = (Sup_Interface)this.getParent().getParent().getParent().getParent().getParent();
		o.setEnabled(b);
		
		if(b){
			o.requestFocus();
		}
	}
}
