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


public class Prep_Consulter_incident extends JFrame implements ActionListener {

	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();
	private ModeleTable modeleInc;
	private TableSorter sorter;
	private JTable tab;
	private JButton quitter = new JButton("Quitter");
	
	public Prep_Consulter_incident() {
		
		//Constructeur de la fenetre
		super("ALBERT MUDA - Preparateur");
		Container ct = this.getContentPane();
		
		//Création du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		//taille de la fenêtre
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Initialisation du Layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Ajout du bouton "Quitter"
		quitter.setBounds(350,400,100,50);
	    ct.add(quitter);
	    quitter.addActionListener(this);
		
	    //Titre des colonnes
		nomColonnes.add("ID");
        nomColonnes.add("Colis");
        nomColonnes.add("Date");
        nomColonnes.add("Etat");
        nomColonnes.add("Description");
        nomColonnes.add("Créateur");
        nomColonnes.add("Type");
 
 //**********************************************APPEL A LA BDD*******************************************
 //Chercher tous les incidents pour la destination affecté au préparateur
    	// Création et ajout de données
        /*Incident i = new Incident(new Integer(-1), new Integer(2),new Timestamp(System.currentTimeMillis()),new Integer(2),"Colis non trouvé lors du chargement",new Integer(2),new Integer(13));
		i.setId(new Integer(123));
		Vector v = new Vector(i.toVector());
		donnees.addElement(v);*/
		
//***********************************************************************************************
		
		modeleInc = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleInc);
		//Création du tableau
		tab = new JTable(sorter);
		//Initialisation du Sorter
		sorter.setTableHeader(tab.getTableHeader());
		
		//Aspect du tableau
		tab.setAutoCreateColumnsFromModel(true);
		tab.setOpaque(false);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setPreferredScrollableViewportSize(new Dimension(800,150));
		scrollPane.setBounds(80,50,600,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Prep_Consulter_incident g = new Prep_Consulter_incident();		
	}

	public void actionPerformed(ActionEvent ev) {
		
		Object source = ev.getSource();
		
		//Sélection de "Quitter"
		if(source == quitter){
			dispose();
		}
	}

}
