package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import accesBDD.AccesBDDColis;

import donnees.Colis;
import donnees.Preparation;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer");
	private JButton ajouter=new JButton("->");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColis;
	private TableSorter sorter;
	private JTable tab;
	private Vector donnees = new Vector();
	
	public Prep_Creer_chargement(Preparation preparation) {
		super(preparation.getUtilisateur().getPersonne().getNom()+" "+preparation.getUtilisateur().getPersonne().getPrenom()+" - Preparateur");
		
		Container ct = this.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		// Taille de la fenêtre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Affichage du bouton "Créer"
		creer.setBounds(350,400,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
	    // Affichage du bouton "->"
	   	creer.setBounds(350,400,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
//	  Création de la première ligne
		nomColonnes.add("id");
		nomColonnes.add("entrepot");
		nomColonnes.add("code_barre");
		nomColonnes.add("expediteur");
		nomColonnes.add("destinataire");
		nomColonnes.add("destination");
		nomColonnes.add("utilisateur");
		nomColonnes.add("poids");
		nomColonnes.add("date_envoi");
		nomColonnes.add("modele");
		nomColonnes.add("valeur_declaree");
		
		
		// Acces BDD pour récupération liste des colis pour la destination donnée
		AccesBDDColis bddColis=new AccesBDDColis();
		try{
			Vector listeColisBDD=bddColis.listerDest(preparation.getDestination().getId());
			for(int i=0;i<listeColisBDD.size();i++){
				donnees.addElement(((Colis)listeColisBDD.get(i)).toVector());
			}
		}
		catch(SQLException SQLe){
			
		}
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
		
		//Sélection de "Créer"
		if(source == creer){
			dispose();
		}		
	}
}
