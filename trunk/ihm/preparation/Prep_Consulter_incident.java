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
	private Bouton envoyer, laisser, annuler, rechercher;
	private JTextField tfCodeBarreColis, tfOrigine, tfDestination, tfPoids, tfValeur;
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
		this.rechercher=new Bouton("images/icones/rechercher.png","images/icones/rechercher_inv.png");
		this.rechercher.setBounds(250,260,121,32);
		this.fenetre.add(this.rechercher);
		this.rechercher.addActionListener(this);
		this.envoyer=new Bouton("images/icones/zoneStockage.png","images/icones/zoneStockage_inv.png");
		this.envoyer.setBounds(804,260,168,43);
		this.fenetre.add(this.envoyer);
		this.envoyer.addActionListener(this);
		this.laisser=new Bouton("images/icones/laisser.png","images/icones/laisser_inv.png");
		this.laisser.setBounds(795,315,168,43);
		this.fenetre.add(this.laisser);
		this.laisser.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		this.annuler.setBounds(807,368,105,43);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		this.tfCodeBarreColis=new JTextField("Insérer code barre");
		this.tfCodeBarreColis.setBounds(78, 266, 150, 20);
		this.fenetre.add(this.tfCodeBarreColis);
		
		JLabel lOrigine=new JLabel("Origine ");
		lOrigine.setFont(font);
		lOrigine.setBounds(78, 380, 100, 20);
		this.fenetre.add(lOrigine);
		this.tfOrigine=new JTextField();
		this.tfOrigine.setBounds(140, 380, 150, 20);
		this.tfOrigine.setEditable(false);
		this.fenetre.add(this.tfOrigine);
		
		JLabel lDestination=new JLabel("Destination ");
		lDestination.setFont(font);
		lDestination.setBounds(320, 380, 100, 20);
		this.fenetre.add(lDestination);
		this.tfDestination=new JTextField();
		this.tfDestination.setBounds(410, 380, 150, 20);
		this.tfDestination.setEditable(false);
		this.fenetre.add(this.tfDestination);
		
		JLabel lPoids=new JLabel("Poids ");
		lPoids.setFont(font);
		lPoids.setBounds(78, 410, 100, 20);
		this.fenetre.add(lPoids);
		this.tfPoids=new JTextField();
		this.tfPoids.setBounds(140, 410, 150, 20);
		this.tfPoids.setEditable(false);
		this.fenetre.add(this.tfPoids);
		
		JLabel lDate=new JLabel("Date d'envoi ");
		lDate.setFont(font);
		lDate.setBounds(320, 410, 100, 20);
		this.fenetre.add(lDate);
		this.tfDate=new JTextField();
		this.tfDate.setBounds(410, 410, 150, 20);
		this.tfDate.setEditable(false);
		this.fenetre.add(this.tfDate);
		
		JLabel lFragilite=new JLabel("Fragilité ");
		lFragilite.setFont(font);
		lFragilite.setBounds(78, 445, 100, 20);
		this.fenetre.add(lFragilite);
		this.tfFragilite=new JTextField();
		this.tfFragilite.setBounds(140, 445, 150, 20);
		this.tfFragilite.setEditable(false);
		this.fenetre.add(this.tfFragilite);
		
		JLabel lValeur=new JLabel("Valeur ");
		lValeur.setFont(font);
		lValeur.setBounds(320, 445, 100, 20);
		this.fenetre.add(lValeur);
		this.tfValeur=new JTextField();
		this.tfValeur.setBounds(410, 445, 150, 20);
		this.tfValeur.setEditable(false);
		this.fenetre.add(this.tfValeur);
			
		JLabel lHauteur=new JLabel("Hauteur ");
		lHauteur.setFont(font);
		lHauteur.setBounds(78, 480, 100, 20);
		this.fenetre.add(lHauteur);
		this.tfHauteur=new JTextField();
		this.tfHauteur.setBounds(140, 480, 150, 20);
		this.tfHauteur.setEditable(false);
		this.fenetre.add(this.tfHauteur);
		
		JLabel lProfondeur=new JLabel("Profondeur ");
		lProfondeur.setFont(font);
		lProfondeur.setBounds(320, 480, 100, 20);
		this.fenetre.add(lProfondeur);
		this.tfProfondeur=new JTextField();
		this.tfProfondeur.setBounds(410, 480, 150, 20);
		this.tfProfondeur.setEditable(false);
		this.fenetre.add(this.tfProfondeur);
		
		JLabel lLargeur=new JLabel("Largeur ");
		lLargeur.setFont(font);
		lLargeur.setBounds(78, 515, 100, 20);
		this.fenetre.add(lLargeur);
		this.tfLargeur=new JTextField();
		this.tfLargeur.setBounds(140, 515, 150, 20);
		this.tfLargeur.setEditable(false);
		this.fenetre.add(this.tfLargeur);	
		
		JLabel lVolume=new JLabel("Volume ");
		lVolume.setFont(font);
		lVolume.setBounds(320, 515, 100, 20);
		this.fenetre.add(lVolume);
		this.tfVolume=new JTextField();
		this.tfVolume.setBounds(410, 515, 150, 20);
		this.tfVolume.setEditable(false);
		this.fenetre.add(this.tfVolume);
		
		// Eventuellment faire un tableau avec les anciens incidents
		
		
		// Mise en place des champs pour l'incident
		this.tfIncident=new JTextField();
		this.tfIncident.setBounds(78, 630, 683, 83);
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
				this.tfValeur.setText(this.colis.getValeurDeclaree());
				this.tfHauteur.setText(this.colis.getModele().getHauteur().toString());
				this.tfLargeur.setText(this.colis.getModele().getLargeur().toString());
				this.tfProfondeur.setText(this.colis.getModele().getProfondeur().toString());
				this.tfVolume.setText(this.colis.getVolume().toString());
				this.tfIncident.setEditable(true);
			}
			else
				JOptionPane.showMessageDialog(this,"Le colis n'existe pas!","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		// On associe le colis à un incident mais on le laisse dans le chargement
		else if(source==this.laisser){
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
		
		// On associe au colis l'incident et on envoie en zone de stockage
		else if(source==this.envoyer){
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
