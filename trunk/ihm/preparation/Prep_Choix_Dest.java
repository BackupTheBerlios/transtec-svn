package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;

import donnees.Preparation;
import donnees.Utilisateur;
import accesBDD.AccesBDDEntrepot;
import accesBDD.AccesBDDPreparation;

public class Prep_Choix_Dest extends JFrame implements ActionListener{
	
	private Vector nomColonnes_preparation = new Vector();
	private Vector donnees_preparation = new Vector();
	private ModeleTable modelePrep;
	private JTable tab_preparation;
	private int ligneActive;
	private TableSorter sorter;
	private JButton preparer=new JButton("Pr�parer");
	private Utilisateur utilisateur=null;
	private Preparation preparation=null;
	
	public Prep_Choix_Dest(Utilisateur utilisateur){
		
		//Constructeur de la fenetre
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		Container ct = this.getContentPane();
		
		// Conservation de l'utilisateur en cours
		this.utilisateur=utilisateur;
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);		
		
		//Cr�ation du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		//Section Fichier	
		menuBar.add(menuFichier);
		menuFichier.add("Quitter");
		//Construction du menu
		setJMenuBar(menuBar);

		//Taile de la fen�tre
		setBounds(200,100,500,550);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Cr�ation de la police
		Font font;
		font = new Font("Verdana", Font.BOLD, 15);
		
		//Ajout du texte
		JLabel texte = new JLabel("Bonjour " + utilisateur.getPersonne().getPrenom()+", veuillez choisir votre destination: ");
		texte.setBounds(30, 10, 300, 20);
		ct.add(texte);
		
		//Ajout du bouton Pr�parer
		preparer.setBounds(190, 300, 100, 40);
		preparer.addActionListener(this);
		ct.add(preparer);

		//Cr�ation de la premi�re ligne
		nomColonnes_preparation.add("idPreparation");
		nomColonnes_preparation.add("Destination");
		nomColonnes_preparation.add("Etat de la pr�paration");
		nomColonnes_preparation.add("Volume");
		
		//Remplissage du tableau
		try{
			donnees_preparation=new AccesBDDPreparation().listerDestAPreparer(utilisateur.getId());
		}
		catch(SQLException e){
			
		}
		
		
		
		//Cr�ation du tableau
        modelePrep = new ModeleTable(nomColonnes_preparation,donnees_preparation);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(modelePrep);
		// Cr�ation du tableau
		tab_preparation = new JTable(sorter);
		// initialisation du Sorter
		sorter.setTableHeader(tab_preparation.getTableHeader());
     
		//Aspect du tableau
		tab_preparation.setAutoCreateColumnsFromModel(true);
		tab_preparation.setOpaque(false);
		tab_preparation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab_preparation.removeColumn(tab_preparation.getColumnModel().getColumn(0));
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(tab_preparation);
		tab_preparation.setPreferredScrollableViewportSize(new Dimension(300,150));
		scrollPane.setBounds(80,140,320,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		setVisible(true);
	
	}

	//A Revoir : Bouton inactif
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		ligneActive = tab_preparation.getSelectedRow();
		try{
			//Selection de "G�rer le chargement"
			if (source == preparer) {
				//Si une ligne est selectionn�e
				if (ligneActive != -1){
					//On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) modelePrep.getRow(ligneActive);
					this.preparation=new AccesBDDPreparation().rechercher((Integer) cVect.get(0));
					this.preparation.setUtilisateur(this.utilisateur);
					Prep_Fenetre_princ fen1 = new Prep_Fenetre_princ(preparation);
					fen1.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(this,"Veuillez s�lectionner une pr�paration","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		catch(SQLException ev){
			JOptionPane.showMessageDialog(this,ev,"Message d'avertissement",JOptionPane.ERROR_MESSAGE);

		}
		
	}
}
