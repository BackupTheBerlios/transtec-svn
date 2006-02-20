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
import accesBDD.AccesBDDPreparation;

public class Prep_Choix_Dest extends JFrame implements ActionListener{
	private Vector donnees_preparation = new Vector();
	private ModeleTable modelePrep;
	private JTable tab_preparation;
	private JButton preparer=new JButton("Préparer");
	private JButton quitter=new JButton("Quitter");
	private Utilisateur utilisateur=null;
	
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
		
		//Création du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		//Section Fichier	
		menuBar.add(menuFichier);
		menuFichier.add("Quitter");
		//Construction du menu
		setJMenuBar(menuBar);

		//Taile de la fenêtre
		setBounds(400,220,380,370);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Création de la police
		Font font = new Font("Verdana", Font.BOLD, 15);
		
		//Ajout du texte
		JLabel texte1 = new JLabel("Bonjour "+utilisateur.getPersonne().getPrenom()+",");
		texte1.setBounds(120, 10, 300, 20);
		ct.add(texte1);
		JLabel texte2=new JLabel("veuillez choisir la destination que vous préparerez : ");
		texte2.setBounds(45, 30, 300, 20);
		ct.add(texte2);
		
		//Ajout du bouton Préparer
		preparer.setBounds(80, 250, 100, 30);
		preparer.addActionListener(this);
		ct.add(preparer);
		
		// Ajout du bouton "Quitter"
		quitter.setBounds(200, 250, 100, 30);
		quitter.addActionListener(this);
		ct.add(quitter);

		//Création de la première ligne
		Vector nomColonnes_preparation = new Vector();
		nomColonnes_preparation.add("idPreparation");
		nomColonnes_preparation.add("Destination");
		nomColonnes_preparation.add("Etat de la préparation");
		nomColonnes_preparation.add("Volume");
		
		//Remplissage du tableau
		try{
			donnees_preparation=new AccesBDDPreparation().listerDestAPreparer(utilisateur.getId());
		}
		catch(SQLException e){
			
		}
				
		//Création du tableau
        modelePrep = new ModeleTable(nomColonnes_preparation,donnees_preparation);
		// Création du TableSorter qui permet de réordonner les lignes à volonté
        TableSorter sorter = new TableSorter(modelePrep);
		// Création du tableau
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
		scrollPane.setBounds(40,80,300,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		setVisible(true);
	
	}

	//A Revoir : Bouton inactif
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int ligneActive = tab_preparation.getSelectedRow();
		try{
			//Selection de "Gérer le chargement"
			if (source == preparer) {
				//Si une ligne est selectionnée
				if (ligneActive != -1){
					//On récupère les données de la ligne du tableau
					Vector cVect = (Vector) modelePrep.getRow(ligneActive);
					Preparation preparation=new AccesBDDPreparation().rechercher((Integer) cVect.get(0));
					preparation.setUtilisateur(this.utilisateur);
					Prep_Fenetre_princ fen1 = new Prep_Fenetre_princ(preparation);
					fen1.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(this,"Veuillez sélectionner une préparation","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
			
			// Sélection du bouton quitter 
			else if(quitter==source){
				dispose();
			}
		}
		catch(SQLException ev){
			JOptionPane.showMessageDialog(this,ev,"Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
	}
}
