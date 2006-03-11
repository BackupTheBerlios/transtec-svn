package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDIncident;

import donnees.Colis;
import donnees.Incident;
import donnees.Utilisateur;

/*
 * Classe permettant de créer un incident associé à un colis entré par l'utilisateur via la saisie du code barre
 */

public class Prep_Consulter_incident extends JFrame implements ActionListener {
	private Bouton valider, annuler, rechercher;
	private JTextField tfCodeBarreColis, tfOrigine, tfDestination, tfPoids;
	private JTextField tfDate, tfFragilite, tfHauteur, tfLargeur, tfProfondeur, tfVolume, tfIncident;
	private FenetreType fenetre;
	private Colis colis;
	private Utilisateur utilisateur;
	
	public Prep_Consulter_incident(Utilisateur utilisateur) {
		//	Création graphique de la fenêtre
		setTitle("Créer Chargement");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur,"images/preparation/fenetre_incidentBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		
		// Création d'un police pour l'affichage des différents textes
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		// Mise en place des champs, boutons,...
		//this.rechercher=new Bouton("","");
		//this.rechercher.setBounds(10,10,10,10);
		//this.fenetre.add(this.rechercher);
		//this.rechercher.addActionListener(this);
		this.valider=new Bouton("images/icones/valider.png","images/icones/valider_inv.png");
		this.valider.setBounds(10,50,100,100);
		this.fenetre.add(this.valider);
		this.valider.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		this.annuler.setBounds(10,100,100,100);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		JLabel lCodeBarreColis=new JLabel("Code Barre : ");
		lCodeBarreColis.setBounds(10, 10, 10, 10);
		this.fenetre.add(lCodeBarreColis);
		this.tfCodeBarreColis=new JTextField("sdf");
		this.tfCodeBarreColis.setBounds(10, 200, 100, 20);
		this.fenetre.add(this.tfCodeBarreColis);
		
		JLabel lOrigine=new JLabel("Origine ");
		this.tfOrigine=new JTextField();
		this.tfOrigine.setBounds(100, 300, 100, 20);
		this.tfOrigine.setEditable(false);
		this.fenetre.add(this.tfOrigine);
		
		JLabel lDestination=new JLabel("Destination ");
		this.tfDestination=new JTextField();
		this.tfDestination.setBounds(100, 340, 100, 20);
		this.tfDestination.setEditable(false);
		this.fenetre.add(this.tfDestination);
		
		JLabel lPoids=new JLabel("Poids ");
		this.tfPoids=new JTextField();
		this.tfPoids.setBounds(100, 380, 100, 20);
		this.tfPoids.setEditable(false);
		this.fenetre.add(this.tfPoids);
		
		JLabel lDate=new JLabel("Date d'envoi ");
		this.tfDate=new JTextField();
		this.tfDate.setBounds(100, 420, 100, 20);
		this.tfDate.setEditable(false);
		this.fenetre.add(this.tfDate);
		
		JLabel lFragilite=new JLabel("Fragilité ");
		this.tfFragilite=new JTextField();
		this.tfFragilite.setBounds(100, 460, 100, 20);
		this.tfFragilite.setEditable(false);
		this.fenetre.add(this.tfFragilite);
		
		JLabel lHauteur=new JLabel("Hauteur ");
		this.tfHauteur=new JTextField();
		this.tfHauteur.setBounds(100, 500, 100, 20);
		this.tfHauteur.setEditable(false);
		this.fenetre.add(this.tfHauteur);
		
		JLabel lLargeur=new JLabel("Largeur ");
		this.tfLargeur=new JTextField();
		this.tfLargeur.setBounds(100, 540, 100, 20);
		this.tfLargeur.setEditable(false);
		this.fenetre.add(this.tfLargeur);
		
		JLabel lProfondeur=new JLabel("Profondeur ");
		this.tfProfondeur=new JTextField();
		this.tfProfondeur.setBounds(100, 580, 100, 20);
		this.tfProfondeur.setEditable(false);
		this.fenetre.add(this.tfProfondeur);
		
		JLabel lVolume=new JLabel("Volume ");
		this.tfVolume=new JTextField();
		this.tfVolume.setBounds(100, 620, 100, 20);
		this.tfVolume.setEditable(false);
		this.fenetre.add(this.tfVolume);
		
		// Eventuellment faire un tableau avec les anciens incidents
		
		
		// Mise en place des champs pour l'incident
		this.tfIncident=new JTextField();
		this.tfIncident.setBounds(100, 700, 200, 200);
		this.tfIncident.setEditable(false);
		this.fenetre.add(this.tfIncident);
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
				// On met à jour tous les champs de la fenêtre
				this.tfOrigine.setText(colis.getOrigine().getLocalisation().getVille());
				this.tfDestination.setText(this.colis.getDestination().getLocalisation().getVille());
				this.tfPoids.setText(this.colis.getPoids().toString());
				this.tfDate.setText(this.colis.getDate().toString());
				this.tfFragilite.setText(this.colis.getFragilite().toString());
				this.tfHauteur.setText(this.colis.getModele().getHauteur().toString());
				this.tfLargeur.setText(this.colis.getModele().getLargeur().toString());
				this.tfProfondeur.setText(this.colis.getModele().getProfondeur().toString());
				this.tfVolume.setText(this.colis.getVolume().toString());
				this.tfIncident.setEditable(true);
			}
			else
				JOptionPane.showMessageDialog(this,"Le colis n'existe pas!","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		else if(source==this.valider){
			// Sauvegarder ds la BDD
			try{
				new AccesBDDIncident().ajouter(new Incident(
						this.colis,
						new Timestamp(System.currentTimeMillis()),
						new Integer(0),
						this.tfIncident.getText(),
						this.utilisateur,
						new Integer(11)));
			}
			catch(SQLException e){
				
			}
				
			dispose();
			new Prep_Fenetre_princ(this.utilisateur).setVisible(true);
		}
		else if(source==this.annuler){
			dispose();
			new Prep_Fenetre_princ(this.utilisateur).setVisible(true);
		}
	}

}
