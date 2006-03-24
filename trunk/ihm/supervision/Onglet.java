package ihm.supervision;

import ihm.ModeleTable;
import ihm.TableSorter;
import ihm.Bouton;

import javax.swing.*;

import java.util.Vector;
import java.awt.*;


// Panneau de l'onglet
public class Onglet extends JPanel{
	protected JTable table;
	protected ModeleTable modeleTab;
	protected JScrollPane scrollPane;
	protected TableSorter sorter;
	protected Bouton boutModifier;
	protected Bouton boutAjouter;
	protected Bouton boutSupprimer;
	protected Bouton boutUpdate;
	protected Vector nomColonnes = new Vector();
	protected Vector donnees = new Vector();
	protected int ligneActive;

	public Onglet(String s){
		
		// Mise en forme initiale
		setOpaque(false);
		setLayout(null);
		setBorder(null);
				
		// Titre de l'onglet
		JLabel titre = new JLabel(s);
		titre.setBounds(10,14,300,20);
		titre.setFont(new Font("Verdana", Font.BOLD, 14));
		add(titre);
		
		boutAjouter = new Bouton("images/supervision/bouton_ajouter.png","images/supervision/bouton_ajouter_appuyer.png");
		boutModifier = new Bouton("images/supervision/bouton_modifier.png","images/supervision/bouton_modifier_appuyer.png");
		boutSupprimer = new Bouton("images/supervision/bouton_supprimer.png","images/supervision/bouton_supprimer_appuyer.png");
		boutUpdate = new Bouton("images/supervision/bouton_actualiser.png","images/supervision/bouton_actualiser_appuyer.png");
	
		// Bouton Ajouter
		boutAjouter.setBounds(770,60,165,41);
		add(boutAjouter);

		// Bouton Modifier
		boutModifier.setBounds(770,130,165,41);
		add(boutModifier);

		// Bouton Supprimer
		boutSupprimer.setBounds(770,193,165,41);
		add(boutSupprimer);
		
		// Bouton Mettre à jour
		boutUpdate.setBounds(770,263,165,41);
		add(boutUpdate);
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g)	{
		ImageIcon img = new ImageIcon("images/supervision/bg_onglet.png");
		g.drawImage(img.getImage(), 0, 0, null);
		super.paintComponent(g);
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
		scrollPane = new JScrollPane(table);

		// On définit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(732,472));

		// On place le tableau
		scrollPane.setBounds(4,45,732,472);

		// On définit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		add(scrollPane);
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
		Sup_FenetrePrincipale o = (Sup_FenetrePrincipale)this.getParent().getParent().getParent().getParent().getParent();
		o.setEnabled(b);
		
		if(b) o.requestFocus();
	}
}
