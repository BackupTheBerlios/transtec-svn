package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
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

import donnees.Camion;
import donnees.Utilisateur;
import accesBDD.AccesBDDCamion;

public class Prep_Choix_Dest extends JFrame implements ActionListener{
	public Prep_Choix_Dest(Utilisateur utilisateur){

		//Constructeur de la fenetre
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		Container ct = this.getContentPane();
		
		
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
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Création de la police
		Font font;
		font = new Font("Verdana", Font.BOLD, 15);
	
		// Mettre une liste déroulante pour le choix de destination
	}
}
