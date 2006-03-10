package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;
import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import accesBDD.AccesBDDColis;

import donnees.Colis;
import donnees.Utilisateur;


public class Prep_Consulter_incident extends JFrame implements ActionListener {
	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();
	private ModeleTable modeleInc;
	private TableSorter sorter;
	private JTable tab;
	private JButton quitter = new JButton("Quitter");
	private Bouton valider, annuler, rechercher;
	private JTextField tfCodeBarreColis;
	private FenetreType fenetre;
	private Colis colis;
	private Utilisateur utilisateur;
	
	public Prep_Consulter_incident(Utilisateur utilisateur) {
		//	Création graphique de la fenêtre
		setTitle("Créer un incident");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur,"images/preparation/fenetre_creerBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		
		// Mise en place des champs, boutons,...
		this.rechercher=new Bouton("","");
		this.rechercher.setBounds(10,10,10,10);
		this.fenetre.add(this.rechercher);
		this.rechercher.addActionListener(this);
		this.valider=new Bouton("images/icones/valider.png","images/icones/valider.png");
		this.valider.setBounds(10,10,10,10);
		this.fenetre.add(this.valider);
		this.valider.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler.png");
		this.annuler.setBounds(10,10,10,10);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		JLabel lCodeBarreColis=new JLabel("Code Barre : ");
		lCodeBarreColis.setBounds(10, 10, 10, 10);
		this.fenetre.add(lCodeBarreColis);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		if(source==this.rechercher){
			try{
				// On recherche le colis dans la BDD
				this.colis=new AccesBDDColis().rechercherCode_barre(new Integer(this.tfCodeBarreColis.getText()).intValue());
			}
			catch(SQLException e){
				
			}
			if(this.colis!=null){
				// mettre à jour les champs de colis
			}
			else
				JOptionPane.showMessageDialog(this,"Le colis n'existe pas!","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		else if(source==this.valider){
			// Récupération des champs
			// Sauvegarder ds la BDD
			dispose();
			Prep_Fenetre_princ fen1=new Prep_Fenetre_princ(this.utilisateur);
			fen1.setVisible(true);
		}
		else if(source==this.annuler){
			dispose();
			Prep_Fenetre_princ fen1=new Prep_Fenetre_princ(this.utilisateur);
			fen1.setVisible(true);
		}
	}

}
