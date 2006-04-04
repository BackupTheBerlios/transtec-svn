package ihm.supervision;

import ihm.Bouton;
import ihm.Fenetre_login;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import accesBDD.AccesBDD;
import accesBDD.AccesBDDEntrepot;
import donnees.Entrepot;
import donnees.Utilisateur;

//  Fen�tre principale du Superviseur
public class Sup_FenetrePrincipale extends JFrame implements ActionListener{
	private JTabbedPane onglets;
	private OngletCamion ongletCamion;
	private OngletRepartition ongletRepartition;
	private OngletIncident ongletIncident;
	private OngletRoutage ongletRoutage;
	private OngletUtilisateur ongletUtilisateur;
	private OngletEntrepot ongletEntrepot;
	private Bouton boutonDeconnexion;
	private Entrepot entActuel; 
	private AccesBDD accesbdd;

	
	// Constructeur : n�cessite un utilisateur en param�tre
	public Sup_FenetrePrincipale(Utilisateur u, AccesBDD accesbdd) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");
		this.accesbdd = accesbdd;
		this.ongletEntrepot = new OngletEntrepot(this.accesbdd);
		
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
		
		
		/***************/
		try {
			entActuel = new AccesBDDEntrepot(this.accesbdd).rechercher(new Integer(1));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		/***************/

		
		
		// Onglets : cr�ation et assemblage
		onglets = new JTabbedPane(SwingConstants.TOP);
		onglets.setUI(new CustomTabbedPane());
		ongletCamion = new OngletCamion(entActuel,this.accesbdd);
		ongletRepartition = new OngletRepartition(entActuel, this.accesbdd);
		ongletIncident = new OngletIncident(this.accesbdd);
		ongletRoutage = new OngletRoutage(this.accesbdd);
		ongletUtilisateur = new OngletUtilisateur(this.accesbdd);
		onglets.addTab("Camions",null,ongletCamion,"G�rer la liste des camions : �tat, disponibilit�s, propri�t�s, ...");
		onglets.addTab("R�partition",null,ongletRepartition,"G�rer la r�partition des colis par destination");
		onglets.addTab("Incidents",null,ongletIncident,"G�rer les incidents : mise � jour, consultation des archives, ...");
		onglets.addTab("Table de routage",null,ongletRoutage,"G�rer les tables de routage");
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"G�rer la liste des utilisateurs : informations personnelles, droits, ...");
		onglets.addTab("Entrep�ts",null,ongletEntrepot,"G�rer la liste des entrep�ts");

		// Ajout des onglets � la fen�tre
		contenu.setLayout(null);
		onglets.setBounds(51,191,926,541);
		onglets.setOpaque(false);
		onglets.setBorder(null);
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
			JFrame fenlogin = new Fenetre_login();
			fenlogin.setVisible(true);
			dispose();
			try {
				accesbdd.deconnecter();
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			System.exit(0);
		}
	}
	
	// Fonction principale de l'interface Superviseur
/*	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur(new Integer(-1),"rochef","pass",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
		new Sup_FenetrePrincipale(uTest,accesbdd);	
	}*/
}
