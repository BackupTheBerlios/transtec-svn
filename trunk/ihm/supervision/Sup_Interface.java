package ihm.supervision;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import donnees.Utilisateur;
//import ihm.Fenetre_login;

//  Fen�tre principale du Superviseur
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
	
	// Constructeur : n�cessite un utilisateur en param�tre
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		// Taille de la fen�tre
		setSize(1024,768);
		
		// On enl�ve les barres d'�tat
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

		// Section Poste (choix : Superviseur, Pr�parateur ou Entr�e)
		/*JMenu menuPoste = new JMenu("Poste");
		menuPoste.add("Superviseur");
		menuPoste.add("Pr�parateur");
		menuPoste.add("Entr�e");		
		menuBar.add(menuPoste);*/

		// Section Gestion (choix : Entrep�ts)
		JMenu menuGestion = new JMenu("Gestion");
		entrepots = new JCheckBoxMenuItem("Entrep�ts");
		entrepots.setState(false);
		entrepots.addActionListener(this);
		menuGestion.add(entrepots);
		menuBar.add(menuGestion);

		// Onglets : cr�ation et assemblage
		onglets = new JTabbedPane(SwingConstants.TOP);
		ongletCamion = new Sup_OngletCamion();
		ongletRepartition = new Sup_OngletRepartition();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"G�rer la liste des camions : �tat, disponibilit�s, propri�t�s, ...");
		onglets.addTab("R�partition",null,ongletRepartition,"G�rer la r�partition des colis par destination");
		onglets.addTab("Incidents",null,ongletIncident,"G�rer les incidents : mise � jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"G�rer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"G�rer la liste des utilisateurs : informations personnelles, droits, ...");

		// Ajout des onglets � la fen�tre
		getContentPane().setLayout(new GridLayout(1, 1)); 
		getContentPane().add(onglets);

		// Comportement lors de la fermeture de la fen�tre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Affichage de la fen�tre
		setVisible(true);
	}

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqu� sur Quitter, on retourne � l'invite de login
		if(source==quitter){
//			dispose();
//			JFrame fenlogin = new Fenetre_login();
//			fenlogin.setVisible(true);
			System.exit(0);
		}

		// S'il a cliqu� sur Entrep�ts, on s'occupe de l'onglet de gestion des entrep�ts
		else if(source==entrepots){
			afficherOngletEntrepots();
		}
	}
	
	// Affichage de l'onglet de gestion des entrep�ts
	public void afficherOngletEntrepots(){
		// Si l'onglet n'est pas affich�, on l'affiche et on se positionne dessus
		if(!ongletEntrepot.isDisplayable()){
			// Ajout de l'onglet
			onglets.addTab("Entrep�ts",null,ongletEntrepot,"G�rer la liste des entrep�ts");
			
			// Positionnement sur l'onglet
			onglets.setSelectedComponent(ongletEntrepot);
			
			// Activation de la checkbox de la barre de menu
			entrepots.setState(true);
		}
		// S'il est affich� on le masque
		else{
			// Suppression de l'onglet
			onglets.removeTabAt(onglets.indexOfComponent(ongletEntrepot));
			
			// D�sactivation de la checkbox de la barre de menu
			entrepots.setState(false);
		}
	}

	// Fonction principale de l'interface Superviseur
	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
		new Sup_Interface(uTest);	
	}
}
