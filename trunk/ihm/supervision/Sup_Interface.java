package ihm.supervision;

import ihm.Bouton;

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
	private Bouton boutonDeconnexion;
	
	// Constructeur : n�cessite un utilisateur en param�tre
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		// On enl�ve les barres d'�tat
		setUndecorated(true);

		// Taille de la fen�tre
		setSize(1024,768);
		
		// Panel recevant le contenu de la fen�tre
		JPanel contenu = new JPanel(){
			// Permet de d�finir une image de fond
			public void paintComponent(Graphics g)	{
				ImageIcon img = new ImageIcon("images/supervision/bg_superviseur.png");
				g.drawImage(img.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		
		// On place contenu comme Panel principal
		setContentPane(contenu);
		
		// On active la transparence pour que l'arri�re plan soit visible
		contenu.setOpaque(false);
		
		// Affichage du titre
		Font font=new Font("Verdana", Font.BOLD, 13);
		JLabel labelUtilisateur=new JLabel(u.toTitre());
		labelUtilisateur.setBounds(73,70,300,20);
		labelUtilisateur.setFont(font);
		contenu.add(labelUtilisateur);
		
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
//		ongletCamion.setOpaque(false);
		ongletRepartition = new Sup_OngletRepartition();
		ongletIncident = new Sup_OngletIncident();
		ongletRoutage = new Sup_OngletRoutage();
		ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"G�rer la liste des camions : �tat, disponibilit�s, propri�t�s, ...");
		onglets.addTab("R�partition",null,ongletRepartition,"G�rer la r�partition des colis par destination");
		onglets.addTab("Incidents",null,ongletIncident,"G�rer les incidents : mise � jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"G�rer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"G�rer la liste des utilisateurs : informations personnelles, droits, ...");
		onglets.addTab("Entrep�ts",null,ongletEntrepot,"G�rer la liste des entrep�ts");

		// Ajout des onglets � la fen�tre
		contenu.setLayout(null);
		onglets.setBounds(40,210,750,520);
		onglets.setOpaque(false);
		onglets.setBackground(new Color(0,0,0,0));
		contenu.add(onglets);
		
		//Ajout du bouton de d�connexion
		boutonDeconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		boutonDeconnexion.setBounds(866, 50, 98, 17);
		contenu.add(boutonDeconnexion);
		boutonDeconnexion.addActionListener(this);

		// Affichage de la fen�tre
		setVisible(true);
	}	

	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqu� sur D�connexion, on retourne � l'invite de login
		if(source==boutonDeconnexion){
//			JFrame fenlogin = new Fenetre_login();
//			fenlogin.setVisible(true);
//			dispose();
			System.exit(0);
		}
	}
	
	// Fonction principale de l'interface Superviseur
	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
		new Sup_Interface(uTest);	
	}
}
