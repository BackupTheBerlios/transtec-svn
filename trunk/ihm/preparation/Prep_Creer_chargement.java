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
	private JButton creer=new JButton("Cr�er");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColis;
	private TableSorter sorter;
	private JTable tab;
	
	public Prep_Creer_chargement(Utilisateur utilisateur) {
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		
		Container ct = this.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		// Taille de la fen�tre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Affichage du bouton "Quitter"
		creer.setBounds(350,400,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
//	  Cr�ation de la premi�re ligne
		nomColonnes.add("Liste des colis");
		
//Cr�ation du premier tableau
		
		listeColis = new ModeleTable(nomColonnes,donnees);
		//Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(listeColis);
		// Cr�ation du tableau
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
		
		//S�lection de "Quitter"
		if(source == creer){
			dispose();
		}		
	}
}
