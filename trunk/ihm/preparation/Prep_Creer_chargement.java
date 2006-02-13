package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import donnees.Utilisateur;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColis;
	private TableSorter sorter;
	private JTable tab;
	
	public Prep_Creer_chargement(Utilisateur utilisateur) {
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		
		Container ct = this.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		// Taille de la fenêtre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Affichage du bouton "Quitter"
		creer.setBounds(350,400,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
//	  Création de la première ligne
		nomColonnes.add("Liste des colis");
		
//Création du premier tableau
		
		listeColis = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(listeColis);
		// Création du tableau
		tab = new JTable(sorter);
		// initialisation du Sorter
		sorter.setTableHeader(tab.getTableHeader());
	
		tab.setAutoCreateColumnsFromModel(true);
		tab.setOpaque(false);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane.setBounds(700,50,500,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) {
		
		Object source = ev.getSource();
		
		//Sélection de "Quitter"
		if(source == creer){
			dispose();
		}		
	}
}
