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
		
		// Taille de la fen�tre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Affichage du bouton "Quitter"
		quitter.setBounds(350,400,100,50);
	    ct.add(quitter);
	    quitter.addActionListener(this);
		
	    // Cr�ation de la premi�re ligne
		nomColonnes.add("Ordre de chargement");
        nomColonnes.add("Num�ro Colis");
        nomColonnes.add("Volume (m3)");
        nomColonnes.add("Poids (kg)");
        nomColonnes.add("Fragilit�");
        nomColonnes.add("Date d'entr�e");

//********************************************APPEL A LA BDD*************************************
//Il faut afficher tous les colis pr�sents dans le camion choisi        
        
        //Cr�ation des autres lignes
        Colis c = new Colis(new Integer(3),new Integer(22),new Integer(2),new Integer(13),new Integer(34),new Timestamp(2006,10,21,16,33,14,0),new Integer(1),new Integer(35));
		c.setId(new Integer(123));
		Vector v = new Vector(c.toVector());
		//v.add(0,c.getId());
		donnees.addElement(v);
		
		c = new Colis(new Integer(2),new Integer(22),new Integer(2),new Integer(13),new Integer(34),new Timestamp(2006,10,21,16,33,14,0),new Integer(1),new Integer(35));
		c.setId(new Integer(127));
		v = new Vector(c.toVector());
		//v.add(0,c.getId());
		donnees.addElement(v);

//***********************************************************************************************		
		
		//Cr�ation du premier tableau
		
		modeleColis = new ModeleTable(nomColonnes,donnees);
		//Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(modeleColis);
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
		if(source == quitter){
			dispose();
		}		
	}
	
	public static void main(String[] args) {
		Prep_Plan_chargement p = new Prep_Plan_chargement();
	}
}
