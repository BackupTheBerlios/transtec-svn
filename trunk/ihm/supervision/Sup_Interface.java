package ihm.supervision;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import donnees.Utilisateur;

//  Fen�tre principale du Superviseur
public class Sup_Interface extends JFrame implements ActionListener{
	private JMenu menuFichier;
	private JMenuItem quitter,entrepots;
	private JMenuBar menuBar;
	private JTabbedPane onglets;
	private Sup_OngletCamion ongletCamion;
	private Sup_OngletIncident ongletIncident;
	private Sup_OngletRoutage ongletRoutage;
	private Sup_OngletUtilisateur ongletUtilisateur;
	private Sup_OngletEntrepot ongletEntrepot;
	private boolean ongletEntrepotActif=false;
	
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		//Taile de la fen�tre
		setSize(800,600);

		//Construction du menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//Section Fichier
		menuFichier = new JMenu("Fichier");
		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(this);
		menuFichier.add(quitter);
		menuBar.add(menuFichier);

		//Section Poste (choix : Superviseur, Pr�parateur ou Entr�e)
		JMenu menuPoste = new JMenu("Poste");
		menuPoste.add("Superviseur");
		menuPoste.add("Pr�parateur");
		menuPoste.add("Entr�e");		
		menuBar.add(menuPoste);

		//Section Gestion (choix : Entrep�ts)
		JMenu menuGestion = new JMenu("Gestion");
		entrepots = new JMenuItem("Entrep�ts");
		entrepots.addActionListener(this);
		menuGestion.add(entrepots);
		menuBar.add(menuGestion);

		//Onglets
		onglets = new JTabbedPane(SwingConstants.TOP);
		ongletCamion = new Sup_OngletCamion();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"G�rer la liste des camions : �tat, disponibilit�s, propri�t�s, ...");
		onglets.addTab("Incidents",null,ongletIncident,"G�rer les incidents : mise � jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"G�rer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"G�rer la liste des utilisateurs : informations personnelles, droits, ...");

		//Ajout des onglets � la fen�tre
		getContentPane().setLayout(new GridLayout(1, 1)); 
		getContentPane().add(onglets);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqu� sur Quitter, on ferme le programme
		if(source==quitter){
			System.exit(0);
		}
		// S'il a cliqu� sur Entrep�ts, on lui affiche l'onglet de gestion des entrep�ts
		else if(source==entrepots){
			afficherOngletEntrepots();
		}
	}
	
	// Affichage de l'onglet de gestion des entrep�ts
	public void afficherOngletEntrepots(){
		if(!ongletEntrepotActif){
			ongletEntrepot = new Sup_OngletEntrepot();
			onglets.addTab("Entrep�ts",null,ongletEntrepot,"G�rer la liste des entrep�ts");
			onglets.setSelectedComponent(ongletEntrepot);
			ongletEntrepotActif=true;
		}
	}

	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
		JFrame frame = new Sup_Interface(uTest);		
	}
}
