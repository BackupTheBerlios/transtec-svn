package ihm.preparation;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ihm.Bouton;
import ihm.FenetreType;
import ihm.entree.AffichageImage;

import javax.swing.*;

import donnees.Utilisateur;

public class Prep_Plan_chargement extends JFrame implements ActionListener{
	private FenetreType fenetre;
	private Bouton imprimer, annuler;
	private Utilisateur utilisateur;
	private Image image;
	
	public Prep_Plan_chargement(Utilisateur utilisateur) {
		// Création graphique de la fenêtre
		setTitle("Plan de chargement");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur,"images/preparation/fenetre_planBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		
		// Mise en place du menu
		this.imprimer=new Bouton("images/icones/imprimer.png","images/icones/imprimer_inv.png");
		this.imprimer.setBounds(805, 265, 116, 46);
		this.fenetre.add(this.imprimer);
		this.imprimer.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		this.annuler.setBounds(800, 331, 116, 46);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		
		// Mise en place des vues
		AffichageImage image = new AffichageImage("images/preparation/1.JPG");
		image.setBounds(247,251,257,129);
		this.fenetre.add(image);
		
		image = new AffichageImage("images/preparation/2.JPG");
		image.setBounds(247,424,257,129);
		this.fenetre.add(image);
		
		image = new AffichageImage("images/preparation/3.JPG");
		image.setBounds(247,597,257,129);
		this.fenetre.add(image);
		
		image = new AffichageImage("images/preparation/4.JPG");
		image.setBounds(524,251,257,129);
		this.fenetre.add(image);
		
		image = new AffichageImage("images/preparation/5.JPG");
		image.setBounds(524,424,257,129);
		this.fenetre.add(image);
		
		image = new AffichageImage("images/preparation/6.JPG");
		image.setBounds(524,597,257,129);
		this.fenetre.add(image);
		
		// Mise en place de la liste des colis pour le chargement
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		
		if(source==this.imprimer){
			//Impression
			dispose();
			new FenetrePrincipale(this.utilisateur).setVisible(true);
		}
		else if(source==this.annuler){
			dispose();
			new FenetrePrincipale(this.utilisateur).setVisible(true);
		}
	}
}
