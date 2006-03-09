package ihm.supervision;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import donnees.Utilisateur;
//import ihm.Fenetre_login;

//  Fenêtre principale du Superviseur
public class Sup_Interface extends JFrame implements ActionListener{
	private JMenu menuFichier;
	private JMenuItem quitter;
	private JCheckBoxMenuItem entrepots;
	private JMenuBar menuBar;
	private JTabbedPane onglets;
	private Sup_OngletCamion ongletCamion;
	private Sup_OngletRepartition ongletRepartition;
	private Sup_OngletIncident ongletIncident;
	private Sup_OngletRoutage ongletRoutage;
	private Sup_OngletUtilisateur ongletUtilisateur;
	private Sup_OngletEntrepot ongletEntrepot = new Sup_OngletEntrepot();
	
	// Constructeur : nécessite un utilisateur en paramètre
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		// Taille de la fenêtre
		setSize(1024,768);
		
		// On enlève les barres d'état
		setUndecorated(true);

		// Construction de la barre de menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Section Fichier
		menuFichier = new JMenu("Fichier");
		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(this);
		menuFichier.add(quitter);
		menuBar.add(menuFichier);

		// Section Poste (choix : Superviseur, Préparateur ou Entrée)
		/*JMenu menuPoste = new JMenu("Poste");
		menuPoste.add("Superviseur");
		menuPoste.add("Préparateur");
		menuPoste.add("Entrée");		
		menuBar.add(menuPoste);*/

		// Section Gestion (choix : Entrepôts)
		JMenu menuGestion = new JMenu("Gestion");
		entrepots = new JCheckBoxMenuItem("Entrepôts");
		entrepots.setState(false);
		entrepots.addActionListener(this);
		menuGestion.add(entrepots);
		menuBar.add(menuGestion);

		// Onglets : création et assemblage
		onglets = new JTabbedPane(SwingConstants.TOP);
		ongletCamion = new Sup_OngletCamion();
		ongletRepartition = new Sup_OngletRepartition();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"Gérer la liste des camions : état, disponibilités, propriétés, ...");
		onglets.addTab("Répartition",null,ongletRepartition,"Gérer la répartition des colis par destination");
		onglets.addTab("Incidents",null,ongletIncident,"Gérer les incidents : mise à jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"Gérer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"Gérer la liste des utilisateurs : informations personnelles, droits, ...");

		// Ajout des onglets à la fenêtre
		getContentPane().setLayout(new GridLayout(1, 1)); 
		getContentPane().add(onglets);

		// Comportement lors de la fermeture de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Affichage de la fenêtre
		setVisible(true);
	}

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqué sur Quitter, on retourne à l'invite de login
		if(source==quitter){
//			dispose();
//			JFrame fenlogin = new Fenetre_login();
//			fenlogin.setVisible(true);
			System.exit(0);
		}

		// S'il a cliqué sur Entrepôts, on s'occupe de l'onglet de gestion des entrepôts
		else if(source==entrepots){
			afficherOngletEntrepots();
		}
	}
	
	// Affichage de l'onglet de gestion des entrepôts
	public void afficherOngletEntrepots(){
		// Si l'onglet n'est pas affiché, on l'affiche et on se positionne dessus
		if(!ongletEntrepot.isDisplayable()){
			// Ajout de l'onglet
			onglets.addTab("Entrepôts",null,ongletEntrepot,"Gérer la liste des entrepôts");
			
			// Positionnement sur l'onglet
			onglets.setSelectedComponent(ongletEntrepot);
			
			// Activation de la checkbox de la barre de menu
			entrepots.setState(true);
		}
		// S'il est affiché on le masque
		else{
			// Suppression de l'onglet
			onglets.removeTabAt(onglets.indexOfComponent(ongletEntrepot));
			
			// Désactivation de la checkbox de la barre de menu
			entrepots.setState(false);
		}
	}

	// Fonction principale de l'interface Superviseur
	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","François","67 rue Jean Jaurès","94800","Villejuif","roche@efrei.fr","0871732639");
		new Sup_Interface(uTest);	
	}
}
