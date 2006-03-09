package ihm.supervision;

import ihm.Bouton;

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
	private Bouton boutonDeconnexion;
	
	// Constructeur : nécessite un utilisateur en paramètre
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		// On enlève les barres d'état
		setUndecorated(true);

		// Taille de la fenêtre
		setSize(1024,768);
		
		// Panel recevant le contenu de la fenêtre
		JPanel contenu = new JPanel(){
			// Permet de définir une image de fond
			public void paintComponent(Graphics g)	{
				ImageIcon img = new ImageIcon("images/supervision/bg_superviseur.png");
				g.drawImage(img.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		
		// On place contenu comme Panel principal
		setContentPane(contenu);
		
		// On active la transparence pour que l'arrière plan soit visible
		contenu.setOpaque(false);
		
		// Affichage du titre
		Font font=new Font("Verdana", Font.BOLD, 13);
		JLabel labelUtilisateur=new JLabel(u.toTitre());
		labelUtilisateur.setBounds(73,70,300,20);
		labelUtilisateur.setFont(font);
		contenu.add(labelUtilisateur);
		
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
//		ongletCamion.setOpaque(false);
		ongletRepartition = new Sup_OngletRepartition();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"Gérer la liste des camions : état, disponibilités, propriétés, ...");
		onglets.addTab("Répartition",null,ongletRepartition,"Gérer la répartition des colis par destination");
		onglets.addTab("Incidents",null,ongletIncident,"Gérer les incidents : mise à jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"Gérer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"Gérer la liste des utilisateurs : informations personnelles, droits, ...");
		onglets.addTab("Entrepôts",null,ongletEntrepot,"Gérer la liste des entrepôts");

		// Ajout des onglets à la fenêtre
		contenu.setLayout(null);
		onglets.setBounds(40,210,750,520);
		onglets.setOpaque(false);
		onglets.setBackground(new Color(0,0,0,0));
		contenu.add(onglets);
		
		//Ajout du bouton de déconnexion
		boutonDeconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		boutonDeconnexion.setBounds(866, 50, 98, 17);
		contenu.add(boutonDeconnexion);
		boutonDeconnexion.addActionListener(this);

		// Affichage de la fenêtre
		setVisible(true);
	}	

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqué sur Déconnexion, on retourne à l'invite de login
		if(source==boutonDeconnexion){
//			JFrame fenlogin = new Fenetre_login();
//			fenlogin.setVisible(true);
//			dispose();
			System.exit(0);
		}
	}
	
	// Fonction principale de l'interface Superviseur
	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","François","67 rue Jean Jaurès","94800","Villejuif","roche@efrei.fr","0871732639");
		new Sup_Interface(uTest);	
	}
}
