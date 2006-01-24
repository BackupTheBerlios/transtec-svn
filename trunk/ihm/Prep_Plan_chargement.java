package ihm;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.*;

import donnees.Colis;

public class Prep_Plan_chargement extends JFrame implements ActionListener{

	private JTable tab;
	private ModeleTable modeleColis;
	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();
	private int ligneActive;
	private JButton quitter=new JButton("Quitter");
	private TableSorter sorter;

	
	public Prep_Plan_chargement() {
		
		super("ALBERT MUDA - Preparateur");
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
		quitter.setBounds(350,400,100,50);
	    ct.add(quitter);
	    quitter.addActionListener(this);
		
	    // Création de la première ligne
		nomColonnes.add("Ordre de chargement");
        nomColonnes.add("Numéro Colis");
        nomColonnes.add("Volume (m3)");
        nomColonnes.add("Poids (kg)");
        nomColonnes.add("Fragilité");
        nomColonnes.add("Date d'entrée");

//********************************************APPEL A LA BDD*************************************
//Il faut afficher tous les colis présents dans le camion choisi    
       Timestamp date=new Timestamp(12-12-1842);
       Colis c = new Colis(new Integer(5),"24thghyuy654",new Integer(1), new Integer(2),new Integer(2),"52", date, new Integer(1),
				new Integer(2), new Integer(2),"52445");
		c.setId(new Integer(127));
		//Vector v = new Vector(c.toVector());
		//v.add(0,c.getId());
		//donnees.addElement(v);

//***********************************************************************************************		
		
		//Création du premier tableau
		
		modeleColis = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleColis);
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
		if(source == quitter){
			dispose();
		}		
	}
	
	public static void main(String[] args) {
		Prep_Plan_chargement p = new Prep_Plan_chargement();
	}
}
