package ihm.supervision;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import donnees.Utilisateur;

//  Fen�tre principale du Superviseur
public class Sup_Interface extends JFrame implements ActionListener{
	JMenu menuFichier;
	JMenuItem quitter;
	
	
	public Sup_Interface(Utilisateur u) {
		super(u.getPersonne().getPrenom()+" "+u.getPersonne().getNom()+" - Superviseur");

		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);

		//Taile de la fen�tre
		setSize(800,600);

		//Construction du menu
		JMenuBar menuBar = new JMenuBar();
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
		
		//Onglets
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		Sup_OngletCamion ongletCamion = new Sup_OngletCamion();
		Sup_OngletIncident ongletIncident = new Sup_OngletIncident();
		Sup_OngletRoutage ongletRoutage = new Sup_OngletRoutage();
		Sup_OngletUtilisateur ongletUtilisateur = new Sup_OngletUtilisateur();
		onglets.addTab("Camions",null,ongletCamion,"G�rer la liste des camions : �tat, disponibilit�s, propri�t�s, ...");
		onglets.addTab("Incidents",null,ongletIncident,"G�rer les incidents : mise � jour, consultation des archives, ...");
		onglets.addTab("Table de routage",ongletRoutage);
		onglets.addTab("Utilisateurs",null,ongletUtilisateur,"G�rer la liste des utilisateurs : informations personnelles, droits, ...");

		//Ajout des onglets � la fen�tre
		getContentPane().setLayout(new GridLayout(1, 1)); 
		getContentPane().add(onglets);

		setVisible(true);
	}
	
	// Gestion des actions de l'utilisateur
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Si l'utilisateur a cliqu� sur Quitter, on ferme le programme
		if(source==quitter){
			System.exit(0);
		}
	}

	public static void main(String [] args){
		Utilisateur uTest = new Utilisateur("rochef","pass",new Integer(0),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
		JFrame frame = new Sup_Interface(uTest);		
	}
}
