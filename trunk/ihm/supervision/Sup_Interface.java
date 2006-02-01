package ihm.supervision;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import donnees.Utilisateur;

//  Fenêtre principale du Superviseur
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

		//Taile de la fenêtre
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

		//Section Poste (choix : Superviseur, Préparateur ou Entrée)
		JMenu menuPoste = new JMenu("Poste");
		menuPoste.add("Superviseur");
		menuPoste.add("Préparateur");
		menuPoste.add("Entrée");		
		menuBar.add(menuPoste);

		//Section Gestion (choix : Entrepôts)
		JMenu menuGestion = new JMenu("Gestion");
		entrepots = new JMenuItem("Entrepôts");
		entrepots.addActionListener(this);
		menuGestion.add(entrepots);
		menuBar.add(menuGestion);

		//Onglets
		onglets = new JTabbedPane(SwingConstants.TOP);
		ongletCamion = new Sup_OngletCamion();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"Gérer la liste des camions : état, disponibilités, propriétés, ...");
		onglets.addTab("Incidents",null,ongletIncident,"Gérer les incidents : mise à jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"Gérer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"Gérer la liste des utilisateurs : informations personnelles, droits, ...");

		//Ajout des onglets à la fenêtre
		getContentPane().setLayout(new GridLayout(1, 1)); 
		getContentPane().add(onglets);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqué sur Quitter, on ferme le programme
		if(source==quitter){
			System.exit(0);
		}
		// S'il a cliqué sur Entrepôts, on lui affiche l'onglet de gestion des entrepôts
		else if(source==entrepots){
			afficherOngletEntrepots();
		}
	}
	
	// Affichage de l'onglet de gestion des entrepôts
	public void afficherOngletEntrepots(){
		if(!ongletEntrepotActif){
			ongletEntrepot = new Sup_OngletEntrepot();
			onglets.addTab("Entrepôts",null,ongletEntrepot,"Gérer la liste des entrepôts");
			onglets.setSelectedComponent(ongletEntrepot);
			ongletEntrepotActif=true;
		}
	}

	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","François","67 rue Jean Jaurès","94800","Villejuif","roche@efrei.fr","0871732639");
		JFrame frame = new Sup_Interface(uTest);		
	}
}
